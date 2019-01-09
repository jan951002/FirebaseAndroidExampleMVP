package com.jan.firebaseandroidexample.view.base;

public interface BasePresenter<V extends BaseView> {
    void attachView(V view);

    void detachView();
}
