package com.jan.firebaseandroidexample.view.userdetail;

import com.jan.firebaseandroidexample.view.base.BasePresenter;
import com.jan.firebaseandroidexample.view.base.BaseView;

public interface UserDetailContract {

    interface View extends BaseView {

        void setData(String email, String uid);

        void goToLoginActivity();

    }

    interface Presenter extends BasePresenter<View> {

        void setData();

        void addAuthStateListener();

        void removeAuthStateListener();

        void signOut();

    }

}
