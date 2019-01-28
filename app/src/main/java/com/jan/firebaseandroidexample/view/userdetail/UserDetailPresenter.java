package com.jan.firebaseandroidexample.view.userdetail;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserDetailPresenter implements UserDetailContract.Presenter {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private UserDetailContract.View view;

    public UserDetailPresenter() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void setData() {
        authStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                view.setData(user.getEmail(), user.getUid());
            } else {
                view.goToLoginActivity();
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
    public void signOut() {
        firebaseAuth.signOut();
        if (LoginManager.getInstance() != null) {
            LoginManager.getInstance().logOut();
        }
    }

    @Override
    public void attachView(UserDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
