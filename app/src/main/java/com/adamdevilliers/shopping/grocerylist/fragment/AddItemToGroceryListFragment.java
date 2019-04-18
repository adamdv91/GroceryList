package com.adamdevilliers.shopping.grocerylist.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adamdevilliers.shopping.grocerylist.R;
import com.adamdevilliers.shopping.grocerylist.adapter.ItemsListAdapter;
import com.adamdevilliers.shopping.grocerylist.model.GroceryList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.adamdevilliers.shopping.grocerylist.fragment.GroceryListFragment.GROCERY_LIST;

public class AddItemToGroceryListFragment extends Fragment implements ItemsListAdapter.ItemClickedListener {

    public static final String SINGLE_GROCERY_LIST = "SINGLE_GROCERY_LIST";
    View rootView;

    String groceryId;

    GroceryList grocery;

    @BindView(R.id.name_of_list)
    TextView nameOfList;

    @BindView(R.id.display_items_in_grocery)
    RecyclerView displayItemsInGrocery;

    ItemsListAdapter groceryListAdapter;

    RealmResults<GroceryList> groceryLists;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.grocery_list_items_fragment, container, false);

        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        groceryId = getArguments().getString(GROCERY_LIST);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        Realm realm = Realm.getInstance(getActivity());

        grocery = realm.where(GroceryList.class).equalTo("id", groceryId).findFirst();


        groceryLists = realm.allObjects(GroceryList.class);

        groceryListAdapter = new ItemsListAdapter(grocery, getContext());
        groceryListAdapter.setOnItemClickListener(this);

        displayItemsInGrocery.setLayoutManager(mLayoutManager);
        displayItemsInGrocery.setAdapter(groceryListAdapter);

        nameOfList.setText(grocery.getName());
    }

    @OnClick(R.id.add_new_item)
    public void onAddNewItemClicked() {
        Bundle bundle = new Bundle();
        bundle.putString(GROCERY_LIST, groceryId);

        AddItemsFragment fragobj = new AddItemsFragment();
        fragobj.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.screen_content, fragobj)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onItemClicked(int position) {

        String id = grocery.getItems().get(position).getId();

        Bundle bundle = new Bundle();
        bundle.putSerializable(SINGLE_GROCERY_LIST, id);

        EditItemFromGroceryList fragobj = new EditItemFromGroceryList();
        fragobj.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.screen_content, fragobj)
                .addToBackStack(null)
                .commit();
    }
}
