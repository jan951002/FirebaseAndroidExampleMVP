package com.jan.firebaseandroidexample.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jan.firebaseandroidexample.view.saveartist.SaveArtistActivity;
import com.jan.firebaseandroidexample.view.login.LoginActivity;
import com.jan.firebaseandroidexample.view.main.MainActivity;

public class IntentHelper {

    public final static String KEY_OBJECT_UPDATE_ARTIST = "KEY_OBJECT_UPDATE_ARTIST";

    public static void goToLoginActivity(Bundle extras, Activity fromActivity) {
        Intent intent = new Intent(fromActivity, LoginActivity.class);
        if (extras != null)
            intent.putExtras(extras);
        fromActivity.startActivity(intent);
    }

    public static void goToMainActivity(Bundle extras, Activity fromActivity) {
        Intent intent = new Intent(fromActivity, MainActivity.class);
        if (extras != null)
            intent.putExtras(extras);
        fromActivity.startActivity(intent);
    }

    public static void goToAddArtistActivity(Bundle extras, Activity fromActivity) {
        Intent intent = new Intent(fromActivity, SaveArtistActivity.class);
        if (extras != null)
            intent.putExtras(extras);
        fromActivity.startActivity(intent);
    }

}
