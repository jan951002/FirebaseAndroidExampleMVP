package com.jan.firebaseandroidexample.data.data.deb.repository;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jan.firebaseandroidexample.data.db.model.Artist;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ArtistRepository {

    private static final String ARTISTS_NODE = "Artists";
    private DatabaseReference databaseReference;
    private static ArtistRepository instance;

    private ArtistRepository() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public synchronized static ArtistRepository getInstance() {
        if (instance == null) {
            instance = new ArtistRepository();
        }
        return instance;
    }

    public void createArtist(Artist artist) {
        artist.setId(databaseReference.push().getKey());
        databaseReference.child(ARTISTS_NODE).child(artist.getId()).setValue(artist);
    }

    public void updateArtist(String id, Artist artist) {
        databaseReference.child(ARTISTS_NODE).child(id).setValue(artist);
    }

    public void removeArtist(String id) {
        databaseReference.child(ARTISTS_NODE).child(id).removeValue();
    }

    public List<Artist> getArtist() {
        List<Artist> artists = new ArrayList<>();
        databaseReference.child(ARTISTS_NODE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Artist artist = snapshot.getValue(Artist.class);
                        EventBus.getDefault().post(artist);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return artists;
    }
}
