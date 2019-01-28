package com.jan.firebaseandroidexample.view.artistsview;

import com.jan.firebaseandroidexample.data.data.deb.repository.ArtistRepository;

public class ArtistsViewPresenter implements ArtistsViewContract.Presenter {

    private ArtistRepository artistRepository;
    private ArtistsViewContract.View view;

    public ArtistsViewPresenter() {
        artistRepository = ArtistRepository.getInstance();
    }

    @Override
    public void getArtists() {
        view.showArtists(artistRepository.getArtist());
    }

    @Override
    public void removeArtist(String id) {
        artistRepository.removeArtist(id);
    }

    @Override
    public void attachView(ArtistsViewContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
