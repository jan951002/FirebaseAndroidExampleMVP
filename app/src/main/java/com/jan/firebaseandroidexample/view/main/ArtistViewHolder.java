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

    public void bind(Artist artist, ArtistsAdapter.OnItemClickListener listener, int position) {
        labArtistName.setText(artist.getName());
        labArtistGenre.setText(artist.getGenre());
        setOnClickListener(artist, listener);
        setOnLongClickListener(artist, listener, position);
    }

    private void setOnClickListener(final Artist artist, final ArtistsAdapter.OnItemClickListener listener) {
        itemView.setOnClickListener(view -> listener.onItemClick(artist));
    }

    private void setOnLongClickListener(final Artist artist, final ArtistsAdapter.OnItemClickListener listener,
                                        final int position) {
        itemView.setOnLongClickListener(view -> {
            listener.onItemLongClick(artist, position);
            return true;
        });
    }
}
