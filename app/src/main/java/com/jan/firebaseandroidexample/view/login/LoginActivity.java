package com.jan.firebaseandroidexample.view.login;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.jan.firebaseandroidexample.R;
import com.jan.firebaseandroidexample.utils.IntentHelper;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    @BindView(R.id.txt_email)
    AutoCompleteTextView txtEmail;
    @BindView(R.id.txt_password)
    EditText txtPassword;
    @BindView(R.id.btn_facebook_sign_in)
    LoginButton loginFacebookButton;

    private LoginContract.Presenter presenter;
    private CallbackManager facebookCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        facebookCallbackManager = CallbackManager.Factory.create();
        presenter = new LoginPresenter();
        presenter.attachView(this);
        txtPassword.setOnEditorActionListener((textView, id, keyEvent)
                -> id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL);
        loginFacebookButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        loginFacebookButton.registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                presenter.signInWithFacebook(loginResult.getAccessToken());
                loginFacebookButton.setEnabled(false);
            }

            @Override
            public void onCancel() {
                Log.i("LOG-INFO", "Facebook sign in cancel");
                loginFacebookButton.setEnabled(true);
            }

            @Override
            public void onError(FacebookException error) {
                Log.i("LOG-INFO", "Facebook sign in error: " + error.getMessage());
                loginFacebookButton.setEnabled(true);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.addAuthStateListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.removeAuthStateListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.btn_email_sign_in)
    public void signInOnClick(View view) {
        presenter.signInWithEmail(txtEmail.getText().toString(), txtPassword.getText().toString());
    }

    @OnClick(R.id.btn_email_sign_up)
    public void signUpOnClick(View view) {
        presenter.signUpWithEmail(txtEmail.getText().toString(), txtPassword.getText().toString());
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void showMessage(int resStringId) {

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void goToMain() {
        IntentHelper.goToMainActivity(null, this);
        finish();
    }
}

