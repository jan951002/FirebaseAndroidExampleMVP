package com.jan.firebaseandroidexample.view.userdetail;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jan.firebaseandroidexample.R;
import com.jan.firebaseandroidexample.data.prefs.PreferencesHelper;
import com.jan.firebaseandroidexample.utils.IntentHelper;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailFragment extends Fragment implements UserDetailContract.View {

    @BindView(R.id.lab_email)
    TextView labEmail;
    @BindView(R.id.lab_uid)
    TextView labUid;

    private UserDetailContract.Presenter presenter;

    public UserDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        ButterKnife.bind(this, view);
        presenter = new UserDetailPresenter();
        presenter.attachView(this);
        presenter.setData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.addAuthStateListener();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.removeAuthStateListener();
    }

    @Override
    public void setData(String email, String uid) {
        labEmail.setText(email);
        labUid.setText(uid);
    }

    @Override
    public void goToLoginActivity() {
        IntentHelper.goToLoginActivity(null, getActivity());
        Objects.requireNonNull(getActivity()).finish();
    }

    @OnClick(R.id.btn_sign_out)
    public void onClickSignOut(View view) {
        presenter.signOut();
        PreferencesHelper.saveUser(Objects.requireNonNull(getContext()), null);
    }

    @Override
    public void showMessage(int resStringId) {

    }
}
