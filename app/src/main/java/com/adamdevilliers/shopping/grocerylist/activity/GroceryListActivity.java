package com.adamdevilliers.shopping.grocerylist.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.adamdevilliers.shopping.grocerylist.R;
import com.adamdevilliers.shopping.grocerylist.fragment.GroceryListFragment;

public class GroceryListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pick_flow_activity);

        getSupportFragmentManager().beginTransaction().replace(R.id.screen_content, new GroceryListFragment()).commit();
    }
}
