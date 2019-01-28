package com.jan.firebaseandroidexample.view.main;

import com.jan.firebaseandroidexample.view.base.BasePresenter;
import com.jan.firebaseandroidexample.view.base.BaseView;

public interface MainContract {

    interface View extends BaseView {

        void showArtistView();

        void showUserDetail();

    }

    interface Presenter extends BasePresenter<View> {

        void showArtistView();

        void showUserDetail();

    }

}
