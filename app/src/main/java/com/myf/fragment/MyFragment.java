package com.myf.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.myf.base.BaseFragment;
import com.myf.util.RefreshListener;
import com.xihls.R;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyFragment extends BaseFragment implements RefreshListener {

    @Bind(R.id.iv_back)
    AutoLinearLayout mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.rl_title_bg)
    AutoRelativeLayout mRlTitleBg;
    @Bind(R.id.iv_my_image)
    ImageView mIvMyImage;
    @Bind(R.id.tv_the_order)
    TextView mTvTheOrder;
    @Bind(R.id.ll_my_the_order)
    AutoLinearLayout mLlMyTheOrder;
    @Bind(R.id.tv_my_address)
    TextView mTvMyAddress;
    @Bind(R.id.ll_my_address)
    AutoLinearLayout mLlMyAddress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mRlTitleBg.setBackgroundColor(Color.parseColor("#fe8cab"));
        mTvTitle.setText("个人中心");
    }

    private void initData() {

    }

    private void initEvent() {
        //我的订单
        mLlMyTheOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "我的订单", Toast.LENGTH_SHORT).show();
            }
        });
        //我的地址
        mLlMyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "我的地址", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void refresh() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
