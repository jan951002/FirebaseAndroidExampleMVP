package com.jan.firebaseandroidexample.view.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jan.firebaseandroidexample.R;
import com.jan.firebaseandroidexample.view.artistsview.ArtistsViewFragment;
import com.jan.firebaseandroidexample.view.userdetail.UserDetailFragment;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private ArtistsViewFragment artistsViewFragment;
    private UserDetailFragment userDetailFragment;
    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new MainPresenter();
        presenter.attachView(this);
        presenter.showArtistView();
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showMessage(int resStringId) {

    }

    @Override
    public void showArtistView() {
        artistsViewFragment = new ArtistsViewFragment();
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.fragment_content, artistsViewFragment);
        t.commit();
        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.lab_artists));
        userDetailFragment = null;
    }

    @Override
    public void showUserDetail() {
        userDetailFragment = new UserDetailFragment();
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.fragment_content, userDetailFragment);
        t.commit();
        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.lab_user));
        artistsViewFragment = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (artistsViewFragment != null) {
            artistsViewFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Optional
    @OnClick({R.id.btn_show_user_detail, R.id.btn_show_artists})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.btn_show_user_detail:
                presenter.showUserDetail();
                break;
            case R.id.btn_show_artists:
                presenter.showArtistView();
                break;
        }
    }
}
