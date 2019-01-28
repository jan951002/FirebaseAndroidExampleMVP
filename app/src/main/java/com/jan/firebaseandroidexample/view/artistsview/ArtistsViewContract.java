package com.jan.firebaseandroidexample.view.artistsview;

import com.jan.firebaseandroidexample.data.db.model.Artist;
import com.jan.firebaseandroidexample.view.base.BasePresenter;
import com.jan.firebaseandroidexample.view.base.BaseView;

import java.util.List;

public interface ArtistsViewContract {

    interface View extends BaseView {

        void showArtists(List<Artist> artists);

    }

    interface Presenter extends BasePresenter<View> {

        void getArtists();

        void removeArtist(String id);

    }

}
