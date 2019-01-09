package com.jan.firebaseandroidexample.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.jan.firebaseandroidexample.data.db.model.User;

public class PreferencesHelper {

    private final static String NAME_PREFERENCES = "NAME_PREFERENCES";

    public static void saveUser(Context context, User user) {
        SharedPreferences preferences = context.getSharedPreferences(NAME_PREFERENCES, 0);
        SharedPreferences.Editor editor = preferences.edit();
        if (user != null) {
            editor.putString("user", user.toJson());
        } else {
            editor.putString("user", null);
        }
        editor.apply();
    }

    public static User getUser(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(NAME_PREFERENCES, 0);
        String json = preferences.getString("user", null);
        return (json != null) ? User.jsonToObject(json) : null;
    }

}
