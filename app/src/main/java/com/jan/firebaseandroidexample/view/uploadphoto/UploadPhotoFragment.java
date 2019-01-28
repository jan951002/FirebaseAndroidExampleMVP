package com.jan.firebaseandroidexample.view.uploadphoto;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jan.firebaseandroidexample.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadPhotoFragment extends Fragment {

    public static final int GALLERY_REQUEST_CODE = 101;

    @BindView(R.id.pick_img)
    ImageView imageView;

    public UploadPhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_photo, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        }
        Objects.requireNonNull(getActivity()).startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @OnClick({R.id.btn_pick_from_gallery, R.id.btn_pick_from_camera})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_pick_from_gallery:
                pickFromGallery();
                break;

            case R.id.btn_pick_from_camera:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    Uri selectedImage = data.getData();
                    imageView.setImageURI(selectedImage);
                    break;
            }
    }
}
