package com.jan.firebaseandroidexample.view.login;

import android.util.Log;

import com.facebook.AccessToken;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jan.firebaseandroidexample.data.db.model.User;
import com.jan.firebaseandroidexample.data.prefs.PreferencesHelper;

public class LoginPresenter implements LoginContract.Presenter {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private LoginContract.View view;

    public LoginPresenter() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                Log.i("LOG-INFO", "Login successful " + user.getUid());
            } else {
                Log.i("LOG-INFO", "Logout");
            }
        };
    }

    @Override
    public void addAuthStateListener() {
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void removeAuthStateListener() {
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    public void signInWithEmail(String email, String password) {
        firebaseAuth.
                signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(view.getActivity(), task -> {
                    if (task.isSuccessful()) {
                        User user = new User();
                        user.setEmail(email);
                        user.setPassword(password);
                        PreferencesHelper.saveUser(view.getContext(), user);
                        view.goToMain();
                    } else {
                        Log.i("LOG-INFO", "Login unsuccessfully");
                    }
                });
    }

    @Override
    public void signUpWithEmail(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(view.getActivity(), task -> {
                    if (task.isSuccessful()) {
                        Log.i("LOG-INFO", "Sign up successful");
                    } else {
                        Log.i("LOG-INFO", "Sign up unsuccessfully");
                    }
                });
    }

    @Override
    public void signInWithFacebook(AccessToken accessToken) {
        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(view.getActivity(), task -> {
                    if (task.isSuccessful()) {
                        PreferencesHelper.saveUser(view.getContext(), new User());
                        view.goToMain();
                    } else {
                        Log.i("LOG-INFO", "Login unsuccessfully");
                    }
                });
    }

    @Override
    public void attachView(LoginContract.View view) {
        this.view = view;

    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
