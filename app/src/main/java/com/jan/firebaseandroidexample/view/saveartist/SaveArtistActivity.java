package com.jan.firebaseandroidexample.view.saveartist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jan.firebaseandroidexample.R;
import com.jan.firebaseandroidexample.data.db.model.Artist;
import com.jan.firebaseandroidexample.utils.IntentHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SaveArtistActivity extends AppCompatActivity implements SaveArtistContract.View {

    @BindView(R.id.txt_artist_name)
    EditText txtArtistName;
    @BindView(R.id.txt_artist_genre)
    EditText txtArtistGenre;
    @BindView(R.id.btn_save_artist)
    Button btnSaveArtist;

    private SaveArtistContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_artist);
        ButterKnife.bind(this);
        presenter = new SaveArtistPresenter();
        presenter.attachView(this);
        Artist artist = (Artist) getIntent().getSerializableExtra(IntentHelper.KEY_OBJECT_SAVE_ARTIST);
        if (artist != null) {
            btnSaveArtist.setText(getResources().getString(R.string.lab_update_artist));
            txtArtistGenre.setText(artist.getGenre());
            txtArtistName.setText(artist.getName());
            presenter.setArtist(artist);
        } else {
            btnSaveArtist.setText(getResources().getString(R.string.lab_add_artist));
            presenter.setArtist(null);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        returnToMainActivity(false);

    }

    @OnClick(R.id.btn_save_artist)
    public void addArtist(View view) {
        if (presenter.getCurrentArtist() == null) {
            presenter.createArtist();
        } else {
            presenter.updateArtist();
        }
        returnToMainActivity(true);
        clearForm();
    }

    private void returnToMainActivity(boolean isSucceed) {
        if (isSucceed) {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
        } else {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
        }
        finish();
    }

    @Override
    public String getName() {
        return txtArtistName.getText().toString();
    }

    @Override
    public String getGenre() {
        return txtArtistGenre.getText().toString();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showMessage(int resStringId) {

    }

    private void clearForm() {
        txtArtistName.setText("");
        txtArtistGenre.setText("");
    }
}
