package com.adamdevilliers.shopping.grocerylist.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.adamdevilliers.shopping.grocerylist.GrocerySharedPrefs;
import com.adamdevilliers.shopping.grocerylist.R;
import com.adamdevilliers.shopping.grocerylist.fragment.CreatePassCodeFragment;
import com.adamdevilliers.shopping.grocerylist.fragment.EnterPassCodeFragment;

public class PickFlowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pick_flow_activity);

        if(TextUtils.isEmpty(GrocerySharedPrefs.getPassCode(this))){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.screen_content, new CreatePassCodeFragment())
                    .commit();
        }else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.screen_content, new EnterPassCodeFragment())
                    .commit();
        }
    }
}
