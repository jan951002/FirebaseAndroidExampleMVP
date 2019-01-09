package com.jan.firebaseandroidexample.view.main;

import com.jan.firebaseandroidexample.data.db.model.Artist;
import com.jan.firebaseandroidexample.view.base.BasePresenter;
import com.jan.firebaseandroidexample.view.base.BaseView;

import java.util.List;

public interface MainContract {

    interface View extends BaseView {

        void setData(String email, String uid);

        void goToLoginActivity();

        void showArtists(List<Artist> artists);

    }

    interface Presenter extends BasePresenter<View> {

        void signOut();

        void setData();

        void addAuthStateListener();

        void removeAuthStateListener();

        void getArtists();

        void removeArtist(String id);
    }

}
