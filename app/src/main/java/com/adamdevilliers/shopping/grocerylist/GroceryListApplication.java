package com.adamdevilliers.shopping.grocerylist;

import android.app.Application;

import io.realm.Realm;

public class GroceryListApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
//        Realm.setDefaultConfiguration(realmConfiguration);
        Realm.getInstance(this);
    }

}
