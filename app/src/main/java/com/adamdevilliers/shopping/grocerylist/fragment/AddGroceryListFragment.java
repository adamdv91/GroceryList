package com.adamdevilliers.shopping.grocerylist.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.adamdevilliers.shopping.grocerylist.R;
import com.adamdevilliers.shopping.grocerylist.model.GroceryList;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class AddGroceryListFragment extends Fragment {

    @BindView(R.id.grocery_list_name)
    EditText groceryListName;

    View rootView;
    Realm realm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.add_grocery_list_fragment, container, false);

        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        realm = Realm.getInstance(getContext());
    }

    @OnClick(R.id.my_grocery_list_adding)
    public void myGroceryListAddingClicked(){
        if(!TextUtils.isEmpty(groceryListName.getText())){

            realm.allObjects(GroceryList.class);
            realm.beginTransaction();

            GroceryList groceryList = realm.createObject(GroceryList.class);

            groceryList.setId(UUID.randomUUID().toString());
            groceryList.setName(groceryListName.getText().toString());
            groceryList.setValid(true);

            realm.commitTransaction();

            getActivity().onBackPressed();
        }else{
            Toast.makeText(getContext(), getString(R.string.error_message_no_text), Toast.LENGTH_LONG).show();
        }
    }
}
