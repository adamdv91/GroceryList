package com.adamdevilliers.shopping.grocerylist;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class GrocerySharedPrefs {

    public static final String PREFS = "com.adamdevilliers.shopping.grocerylist.PREFS";
    private static final String PASSWORD_CREATED = "PASSWORD_CREATED";

    private static SharedPreferences getStaticPrefs(@NonNull Context context) {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public static void setPassCode(String passCode, @NonNull Context context) {
        getStaticPrefs(context).edit()
                .putString(PASSWORD_CREATED, passCode)
                .apply();
    }

    public static String getPassCode(@NonNull Context context) {
            return getStaticPrefs(context).getString(PASSWORD_CREATED, "");
    }
}
