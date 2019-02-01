package com.jan.firebaseandroidexample.view.uploadphoto;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jan.firebaseandroidexample.data.prefs.PreferencesHelper;
import com.jan.firebaseandroidexample.utils.DateUtil;

import java.util.Date;
import java.util.Objects;

public class UploadPhotoPresenter implements UploadPhotoContract.Presenter {

    private FirebaseStorage firebaseStorage;
    private UploadPhotoContract.View view;

    public UploadPhotoPresenter() {
        this.firebaseStorage = FirebaseStorage.getInstance();
    }

    @Override
    public void uploadPhoto(byte[] data) {
        StorageReference imagesStorageReference = firebaseStorage
                .getReference().child("images/img-" + DateUtil.getFileDate(new Date())
                        + Objects.requireNonNull(PreferencesHelper.getUser(view.getContext())).getEmail() + ".jpg");
        UploadTask uploadTask = imagesStorageReference.putBytes(data);
        uploadTask.addOnFailureListener(exception -> {
            Log.i("INFO-UPLOAD-IMAGE", "Photo Uploader filed");
        }).addOnSuccessListener(taskSnapshot -> {
            Log.i("INFO-UPLOAD-IMAGE", "Photo Uploaded");
        });

    }

    @Override
    public void attachView(UploadPhotoContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
