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

import com.adamdevilliers.shopping.grocerylist.R;
import com.adamdevilliers.shopping.grocerylist.adapter.GroceryListAdapter;
import com.adamdevilliers.shopping.grocerylist.model.GroceryList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class GroceryListFragment extends Fragment implements GroceryListAdapter.GroceryClickedListener {

    public static final String GROCERY_LIST = "grocery_list";
    View rootView;

    @BindView(R.id.grocery_list_recycler)
    RecyclerView groceryListRecycler;

    GroceryListAdapter groceryListAdapter;

    Realm realm;

    RealmResults<GroceryList> groceryLists;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.grocery_list_fragment, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        realm = Realm.getInstance(getActivity());

        groceryLists = realm.allObjects(GroceryList.class);

        groceryListAdapter = new GroceryListAdapter(groceryLists, getContext());
        groceryListAdapter.setOnItemClickListener(this);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        groceryListRecycler.setLayoutManager(mLayoutManager);
        groceryListRecycler.setAdapter(groceryListAdapter);
    }

    @OnClick(R.id.new_grocery_list)
    public void onNewGroceryListClicked() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.screen_content, new AddGroceryListFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onGroceryClicked(int position) {

        Bundle bundle = new Bundle();
        bundle.putString(GROCERY_LIST, groceryLists.get(position).getId());

        AddItemToGroceryListFragment fragobj = new AddItemToGroceryListFragment();
        fragobj.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.screen_content, fragobj)
                .addToBackStack(null)
                .commit();
    }
}
