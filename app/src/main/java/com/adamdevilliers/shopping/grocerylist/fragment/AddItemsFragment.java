package com.adamdevilliers.shopping.grocerylist.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.adamdevilliers.shopping.grocerylist.R;
import com.adamdevilliers.shopping.grocerylist.model.GroceryList;
import com.adamdevilliers.shopping.grocerylist.model.Items;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;

import static com.adamdevilliers.shopping.grocerylist.fragment.GroceryListFragment.GROCERY_LIST;

public class AddItemsFragment extends Fragment implements Validator.ValidationListener {

    View rootView;

    @BindView(R.id.quantity)
    EditText quantity;

    @NotEmpty
    @BindView(R.id.name_of_item)
    EditText nameOfItem;

    RealmList<Items> items;
    Items item;

    Validator validator;

    Realm realm;
    GroceryList grocery;

    String groceryId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.grocery_list_add_items_fragment, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        groceryId = getArguments().getString(GROCERY_LIST);
        realm = Realm.getInstance(getContext());

        grocery = realm.where(GroceryList.class).equalTo("id", groceryId).findFirst();

        items = new RealmList<>();

        if (grocery.getItems() != null && grocery.getItems().size() > 0) {
            items.addAll(grocery.getItems());
        }

        item = new Items();

        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @OnClick(R.id.add_item_to_list)
    public void onAddItemClicked() {
        validator.validate();
    }

    @OnClick(R.id.finish_adding)
    public void onFinishedClicked() {
        getActivity().onBackPressed();
    }

    @Override
    public void onValidationSucceeded() {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

            }
        });

        realm.allObjects(Items.class);
        realm.beginTransaction();

        item = realm.createObject(Items.class);

        item.setId(UUID.randomUUID().toString());
        item.setName(nameOfItem.getText().toString());
        item.setPaid(false);
        item.setQuantity(TextUtils.isEmpty(quantity.getText().toString()) ? 1 : Integer.valueOf(quantity.getText().toString()));

        items.add(item);

        realm.commitTransaction();

        RealmQuery<GroceryList> query = realm.where(GroceryList.class);
        query.equalTo("id", grocery.getId());
        final GroceryList groceryList = query.findFirst();
        if (groceryList != null) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    grocery.setItems(items);
                }
            });
        }

        nameOfItem.setText("");
        quantity.setText("");
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
}
