package com.adamdevilliers.shopping.grocerylist.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.adamdevilliers.shopping.grocerylist.R;
import com.adamdevilliers.shopping.grocerylist.model.Items;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static com.adamdevilliers.shopping.grocerylist.fragment.AddItemToGroceryListFragment.SINGLE_GROCERY_LIST;

public class EditItemFromGroceryList extends Fragment implements Validator.ValidationListener {

    View rootView;

    @BindView(R.id.name_of_item_display)
    TextView nameOfItem;

    @NotEmpty
    @BindView(R.id.quantity_update)
    EditText quantityUpdate;

    String itemId;
    Items singleItem;

    Validator validate;

    Realm realm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        itemId = getArguments().getString(SINGLE_GROCERY_LIST);

        rootView = inflater.inflate(R.layout.items_fragment, container, false);

        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        realm = Realm.getInstance(getContext());

        singleItem = realm.where(Items.class).equalTo("id", itemId).findFirst();

        nameOfItem.setText(singleItem.getName());

        validate = new Validator(this);
        validate.setValidationListener(this);

    }

    @OnClick(R.id.change_quantity_button)
    public void onQuantityUpdateClicked() {
        validate.validate();
    }

    @OnClick(R.id.buy_item)
    public void onBuyClicked() {
        RealmQuery<Items> query = realm.where(Items.class);
        query.equalTo("id", singleItem.getId());
        final Items item = query.findFirst();
        if (item != null) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    item.setPaid(true);
                }
            });
        }
        getActivity().onBackPressed();
    }

    @OnClick(R.id.delete_item)
    public void onDeleteClicked() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Items> rows = realm.where(Items.class).equalTo("id", singleItem.getId()).findAll();
                rows.clear();
            }
        });
        getActivity().onBackPressed();
    }

    @Override
    public void onValidationSucceeded() {
        RealmQuery<Items> query = realm.where(Items.class);
        query.equalTo("id", singleItem.getId());
        final Items item = query.findFirst();
        if (item != null) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    item.setQuantity(Integer.valueOf(quantityUpdate.getText().toString()));
                }
            });
        }
        getActivity().onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();

        realm.close();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }
}
