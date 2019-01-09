package com.jan.firebaseandroidexample.view.saveartist;

import com.jan.firebaseandroidexample.data.db.model.Artist;
import com.jan.firebaseandroidexample.view.base.BasePresenter;
import com.jan.firebaseandroidexample.view.base.BaseView;

public interface SaveArtistContract {

    interface View extends BaseView {
        String getName();

        String getGenre();
    }

    interface Presenter extends BasePresenter<View> {

        void createArtist();

        void updateArtist();

        void setArtist(Artist artist);

        Artist getCurrentArtist();

    }

}
