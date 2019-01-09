package com.jan.firebaseandroidexample.view.main;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jan.firebaseandroidexample.data.data.deb.repository.ArtistRepository;

public class MainPresenter implements MainContract.Presenter {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private MainContract.View view;
    private ArtistRepository artistRepository;

    public MainPresenter() {
        firebaseAuth = FirebaseAuth.getInstance();
        artistRepository = ArtistRepository.getInstance();
    }

    @Override
    public void signOut() {
        firebaseAuth.signOut();
        if (LoginManager.getInstance() != null) {
            LoginManager.getInstance().logOut();
        }
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
    public void getArtists() {
        view.showArtists(artistRepository.getArtist());
    }

    @Override
    public void removeArtist(String id) {
        artistRepository.removeArtist(id);
    }

    @Override
    public void attachView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
