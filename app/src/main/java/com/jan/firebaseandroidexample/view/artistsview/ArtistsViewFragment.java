package com.jan.firebaseandroidexample.view.artistsview;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jan.firebaseandroidexample.R;
import com.jan.firebaseandroidexample.data.db.model.Artist;
import com.jan.firebaseandroidexample.utils.IntentHelper;
import com.jan.firebaseandroidexample.view.saveartist.SaveArtistActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistsViewFragment extends Fragment implements ArtistsViewContract.View,
        ArtistsAdapter.OnItemClickListener {

    @BindView(R.id.artists_recycler)
    RecyclerView artistsRecyclerView;

    private ArtistsAdapter artistsAdapter;
    private ArtistsViewContract.Presenter presenter;

    public ArtistsViewFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artists_view, container, false);
        ButterKnife.bind(this, view);
        artistsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        presenter = new ArtistsViewPresenter();
        presenter.attachView(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getArtists();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                presenter.getArtists();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("SAVE-ARTIST", "CANCELED");
            }
        }
    }

    @OnClick(R.id.btn_go_to_add_artist)
    public void onClickGoToAddArtist(View view) {
        Intent intent = new Intent(getActivity(), SaveArtistActivity.class);
        getActivity().startActivityForResult(intent, 1);
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
        IntentHelper.goToAddArtistActivity(extras, getActivity());
    }

    @Override
    public void onItemLongClick(Artist item, int position) {
        new AlertDialog.Builder(Objects.requireNonNull(getContext()))
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
