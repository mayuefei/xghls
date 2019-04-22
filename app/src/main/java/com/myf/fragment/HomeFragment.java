package com.myf.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.myf.R;
import com.myf.activity.GenerationTakeActivity;
import com.myf.base.BaseFragment;
import com.myf.util.GlideImageLoader;
import com.myf.util.RefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment implements RefreshListener {

    @Bind(R.id.banner)
    Banner mBanner;
    @Bind(R.id.iv_daiqu)
    ImageView mIvDaiqu;
    @Bind(R.id.iv_daiji)
    ImageView mIvDaiji;
    @Bind(R.id.iv_back)
    AutoLinearLayout mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.rl_title_bg)
    AutoRelativeLayout mRlTitleBg;
    private List<Integer> mBannerPicUrlList = new ArrayList<>();//banner集合

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
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

    private void initEvent() {
        //代取按钮
        mIvDaiqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),GenerationTakeActivity.class);
                startActivity(intent);
            }
        });
        //代寄
        mIvDaiji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "我要代寄", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {

    }

    private void initView() {
        mRlTitleBg.setBackgroundColor(Color.parseColor("#fe8cab"));
        mTvTitle.setText("首页");
        initBanner();
    }

    /**
     * 设置banner
     */

    private void initBanner() {
        mBannerPicUrlList.clear();
        mBannerPicUrlList.add(R.mipmap.lunbotu1);
        mBannerPicUrlList.add(R.mipmap.lunbotu2);
        mBannerPicUrlList.add(R.mipmap.lunbotu3);
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置banner样式
        mBanner.setImageLoader(new GlideImageLoader());//设置图片加载器
        mBanner.setBannerAnimation(Transformer.Default);//设置banner动画效果
        mBanner.isAutoPlay(true);//设置自动轮播，默认为true
        mBanner.setDelayTime(3000);//设置轮播时间
        mBanner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置（当banner模式中有指示器时）
        mBanner.setImages(mBannerPicUrlList);//设置图片集合
        mBanner.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        mBanner.stopAutoPlay();
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
