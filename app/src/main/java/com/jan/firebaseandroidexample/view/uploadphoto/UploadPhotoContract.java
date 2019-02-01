package com.jan.firebaseandroidexample.view.uploadphoto;

import com.jan.firebaseandroidexample.view.base.BasePresenter;
import com.jan.firebaseandroidexample.view.base.BaseView;

public interface UploadPhotoContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {

        void uploadPhoto(byte[] data);

    }

}
