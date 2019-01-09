package com.jan.firebaseandroidexample.view.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jan.firebaseandroidexample.R;
import com.jan.firebaseandroidexample.data.db.model.Artist;

import java.util.List;

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistViewHolder> {

    private List<Artist> artists;
    private OnItemClickListener listener;

    public ArtistsAdapter(List<Artist> artists, OnItemClickListener listener) {
        this.artists = artists;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.artist_card_view, viewGroup, false);
        return new ArtistViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder artistViewHolder, int i) {
        artistViewHolder.bind(artists.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    private int getArtistPositionById(String id) {
        int position = -1;
        for (int i = 0; i < artists.size(); i++) {
            Artist currentArtist = artists.get(i);
            if (currentArtist.getId().equals(id)) {
                position = i;
                break;
            }
        }
        return position;
    }

    public void saveArtist(Artist artist) {
        int artistPosition = getArtistPositionById(artist.getId());
        if (artistPosition == -1) {
            artists.add(artist);
        } else {
            artists.set(artistPosition, artist);
        }
        notifyDataSetChanged();
    }

    interface OnItemClickListener {
        void onItemClick(Artist item);

        void onItemLongClick(Artist item);
    }
}
