package com.jan.firebaseandroidexample.view.uploadphoto;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jan.firebaseandroidexample.BuildConfig;
import com.jan.firebaseandroidexample.R;
import com.jan.firebaseandroidexample.utils.DateUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadPhotoFragment extends Fragment implements UploadPhotoContract.View {

    private static final int GALLERY_REQUEST_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;

    @BindView(R.id.pick_img)
    ImageView imageView;

    private String cameraFilePath;
    private UploadPhotoContract.Presenter presenter;

    public UploadPhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_photo, container, false);
        ButterKnife.bind(this, view);
        presenter = new UploadPhotoPresenter();
        presenter.attachView(this);
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

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = DateUtil.getFileDate(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        cameraFilePath = "file://" + image.getAbsolutePath();
        return image;
    }

    private void captureFromCamera() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(Objects.requireNonNull(getContext()),
                    BuildConfig.APPLICATION_ID + ".provider", createImageFile()));
            Objects.requireNonNull(getActivity()).startActivityForResult(intent, CAMERA_REQUEST_CODE);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @OnClick({R.id.btn_pick_from_gallery, R.id.btn_pick_from_camera, R.id.btn_upload_photo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_pick_from_gallery:
                pickFromGallery();
                break;

            case R.id.btn_pick_from_camera:
                if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
                        Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                == PackageManager.PERMISSION_GRANTED) {
                    captureFromCamera();
                }
                break;

            case R.id.btn_upload_photo:
                uploadImage();
                break;
        }
    }

    private void uploadImage() {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        presenter.uploadPhoto(data);

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
                case CAMERA_REQUEST_CODE:
                    imageView.setImageURI(Uri.parse(cameraFilePath));
                    break;
            }
    }

    @Override
    public void showMessage(int resStringId) {

    }
}
