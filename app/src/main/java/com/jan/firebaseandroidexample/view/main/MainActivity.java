package com.jan.firebaseandroidexample.view.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jan.firebaseandroidexample.R;
import com.jan.firebaseandroidexample.data.db.model.Artist;
import com.jan.firebaseandroidexample.data.prefs.PreferencesHelper;
import com.jan.firebaseandroidexample.utils.IntentHelper;
import com.jan.firebaseandroidexample.view.saveartist.SaveArtistActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainContract.View,
        ArtistsAdapter.OnItemClickListener {

    @BindView(R.id.lab_email)
    TextView labEmail;
    @BindView(R.id.lab_uid)
    TextView labUid;
    @BindView(R.id.artists_recycler)
    RecyclerView artistsRecyclerView;

    private MainContract.Presenter presenter;
    private ArtistsAdapter artistsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        artistsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        presenter = new MainPresenter();
        presenter.attachView(this);
        presenter.setData();
        presenter.getArtists();
        presenter.getArtists();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getArtists();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.addAuthStateListener();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.removeAuthStateListener();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                presenter.getArtists();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("SAVE-ARTIST", "CANCELED");
            }
        }
    }

    @OnClick(R.id.btn_sign_out)
    public void onClickSignOut(View view) {
        presenter.signOut();
        PreferencesHelper.saveUser(this, null);
    }

    @OnClick(R.id.btn_go_to_add_artist)
    public void onClickGoToAddArtist(View view) {
        IntentHelper.goToAddArtistActivity(null, this);
    }

    @Override
    public void setData(String email, String uid) {
        labEmail.setText(email);
        labUid.setText(uid);
    }

    @Override
    public void goToLoginActivity() {
        Intent intent = new Intent(this, SaveArtistActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void showArtists(List<Artist> artists) {
        if (artistsAdapter == null) {
            artistsAdapter = new ArtistsAdapter(artists, this);
            artistsRecyclerView.setAdapter(artistsAdapter);
        } else {
            artistsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showMessage(int resStringId) {

    }

    @Subscribe
    public void onEvent(Artist artist) {
        artistsAdapter.saveArtist(artist);
    }

    @Override
    public void onItemClick(Artist item) {
        Bundle extras = new Bundle();
        extras.putSerializable(IntentHelper.KEY_OBJECT_SAVE_ARTIST, item);
        IntentHelper.goToAddArtistActivity(extras, this);
    }

    @Override
    public void onItemLongClick(Artist item, int position) {
        new AlertDialog.Builder(this)
                .setMessage(getContext().getResources().getString(R.string.msg_remove_artist))
                .setCancelable(false)
                .setPositiveButton(getContext().getResources().getString(android.R.string.yes),
                        (dialog, id) -> {
                            presenter.removeArtist(item.getId());
                            artistsAdapter.removeArtist(position);
                        }
                )
                .setNegativeButton(getContext().getResources().getString(android.R.string.cancel),
                        null)
                .show();
        presenter.getArtists();
    }
}
