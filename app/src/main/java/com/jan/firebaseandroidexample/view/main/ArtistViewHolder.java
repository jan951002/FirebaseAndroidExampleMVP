package com.jan.firebaseandroidexample.view.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jan.firebaseandroidexample.R;
import com.jan.firebaseandroidexample.data.db.model.Artist;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArtistViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.lab_artist_name)
    TextView labArtistName;
    @BindView(R.id.lab_artist_genre)
    TextView labArtistGenre;

    public ArtistViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Artist artist, ArtistsAdapter.OnItemClickListener listener) {
        labArtistName.setText(artist.getName());
        labArtistGenre.setText(artist.getGenre());
        setOnClickListener(artist, listener);
        setOnLongClickListener(artist, listener);
    }

    private void setOnClickListener(final Artist artist, final ArtistsAdapter.OnItemClickListener listener) {
        itemView.setOnClickListener(view -> listener.onItemClick(artist));
    }

    private void setOnLongClickListener(final Artist artist, final ArtistsAdapter.OnItemClickListener listener) {
        itemView.setOnLongClickListener(view -> {
            listener.onItemLongClick(artist);
            return true;
        });
    }
}
