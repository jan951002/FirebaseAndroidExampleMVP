package com.jan.firebaseandroidexample.view.main;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;

    public MainPresenter() {
    }

    @Override
    public void attachView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void showArtistView() {
        view.showArtistView();
    }

    @Override
    public void showUserDetail() {
        view.showUserDetail();
    }
}
