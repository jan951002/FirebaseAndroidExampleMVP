package com.jan.firebaseandroidexample.view.saveartist;

import com.jan.firebaseandroidexample.data.data.deb.repository.ArtistRepository;
import com.jan.firebaseandroidexample.data.db.model.Artist;

public class SaveArtistPresenter implements SaveArtistContract.Presenter {

    private ArtistRepository artistRepository;
    private SaveArtistContract.View view;
    private Artist currentArtist;

    public SaveArtistPresenter() {
        artistRepository = ArtistRepository.getInstance();
    }

    public void createArtist() {
        currentArtist = new Artist();
        currentArtist.setName(view.getName());
        currentArtist.setGenre(view.getGenre());
        artistRepository.createArtist(currentArtist);
    }

    @Override
    public void updateArtist() {
        currentArtist.setName(view.getName());
        currentArtist.setGenre(view.getGenre());
        artistRepository.updateArtist(currentArtist.getId(), currentArtist);
    }

    @Override
    public void setArtist(Artist artist) {
        this.currentArtist = artist;
    }

    @Override
    public Artist getCurrentArtist() {
        return currentArtist;
    }

    @Override
    public void attachView(SaveArtistContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
