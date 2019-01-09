package com.jan.firebaseandroidexample.view.main;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.jan.firebaseandroidexample.R;
import com.jan.firebaseandroidexample.data.prefs.PreferencesHelper;
import com.jan.firebaseandroidexample.utils.IntentHelper;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(() -> {
            if (PreferencesHelper.getUser(this) == null) {
                IntentHelper.goToLoginActivity(null, this);
            } else {
                IntentHelper.goToMainActivity(null, this);
            }
            finish();
        }, 3000);
    }

}
