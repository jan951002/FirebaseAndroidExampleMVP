package com.jan.firebaseandroidexample.view.login;

import android.app.Activity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.google.firebase.auth.FirebaseAuth;
import com.jan.firebaseandroidexample.view.base.BasePresenter;
import com.jan.firebaseandroidexample.view.base.BaseView;

public interface LoginContract {

    interface View extends BaseView {

        Activity getActivity();

        void goToMain();

    }

    interface Presenter extends BasePresenter<View> {

        void addAuthStateListener();

        void removeAuthStateListener();

        void signInWithEmail(String email, String password);

        void signUpWithEmail(String email, String password);

        void signInWithFacebook(AccessToken accessToken);
    }

}
