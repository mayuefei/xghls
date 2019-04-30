package com.myf.fragment;

import android.annotation.SuppressLint;

import com.myf.base.BaseFragment;
import com.myf.model.UserInfoRespones;
import com.myf.util.RefreshListener;

@SuppressLint("ValidFragment")
public class ToMailFragment extends BaseFragment implements RefreshListener {

    private UserInfoRespones mUserInfoRespones;

    public ToMailFragment(UserInfoRespones userInfoRespones) {
        mUserInfoRespones = userInfoRespones;
    }

    @Override
    public void refresh() {

    }
}
