package com.myf.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;


import com.xghls.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by myf on 2017/6/5.
 */

public class JZBKLoadingHandler implements PtrUIHandler {

    private View mLoadingHeaderView;
    private PtrFrameLayout mPtrFrameLayout;

    private View mPullHeaderView;
    private ImageView mIvLoadingPull;
    private TextView mTvLoadingPull;
    private boolean isCanRefresh = false;
    private boolean isRefresh = false;


    public JZBKLoadingHandler(PtrFrameLayout mPtrFrame, View loadingHeaderView, View pullHeaderView) {
        if (mPtrFrame == null) {
            throw new NullPointerException("PtrFrameLayout can't be null");
        }
        mPtrFrameLayout = mPtrFrame;
        mLoadingHeaderView = loadingHeaderView;
        mPullHeaderView = pullHeaderView;
        initPtrFrameLayoutSetting();
        mIvLoadingPull = (ImageView) mPullHeaderView.findViewById(R.id.iv_loading_pull);
        mTvLoadingPull = (TextView) mPullHeaderView.findViewById(R.id.tv_loading_pull);


    }

    public JZBKLoadingHandler(PtrFrameLayout mPtrFrame) {
        this(mPtrFrame,
                LayoutInflater.from(mPtrFrame.getContext()).inflate(R.layout.header_loading, null),
                LayoutInflater.from(mPtrFrame.getContext()).inflate(R.layout.header_loading_pull, null));

    }

    private void initPtrFrameLayoutSetting() {
        //阻尼系数,默认: 1.7f，越大，感觉下拉时越吃力
        mPtrFrameLayout.setResistance(1.7f);
        //触发刷新时移动的位置比例,默认，1.2f，移动达到头部高度1.2倍时可触发刷新操作
        mPtrFrameLayout.setRatioOfHeaderHeightToRefresh(1.4f);
        //回弹延时,默认 200ms，回弹到刷新高度所用时间
        mPtrFrameLayout.setDurationToClose(200);
        //头部回弹时间,默认1000ms
        mPtrFrameLayout.setDurationToCloseHeader(500);
        //下拉时就刷新 / 释放后才刷新 ,默认为释放后才刷新
        mPtrFrameLayout.setPullToRefresh(false);
        //刷新时保持头部，默认值 true.
        mPtrFrameLayout.setKeepHeaderWhenRefresh(true);
        //设置初始headerView
        mPtrFrameLayout.setHeaderView(mPullHeaderView);
        //ViewPager滑动冲突
        mPtrFrameLayout.disableWhenHorizontalMove(true);

    }

    @Override
    public void onUIReset(PtrFrameLayout mPtrFrame) {
        //当位置回到初始位置

    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout mPtrFrame) {
        //当位置离开初始位置
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout mPtrFrame) {
        //开始刷新动画
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout mPtrFrame) {
        //刷新动画完成。刷新完成之后，开始回归初始位置
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout mPtrFrame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        //位置发生变化时此方法通知UI更新

//                LogUtil.i(TAG, "getHeaderHeight=" + ptrIndicator.getHeaderHeight());
//                LogUtil.i(TAG, "getCurrentPosY=" + ptrIndicator.getCurrentPosY());
//                LogUtil.i(TAG, "getOffsetToRefresh=" + ptrIndicator.getOffsetToRefresh());

        switch (status) {
            case 2: { //下拉状态
                if (ptrIndicator.getCurrentPosY() > ptrIndicator.getOffsetToRefresh()) {
                    //当下拉距离超过可以刷新的距离
                    if (!isCanRefresh) {
                        //松开立即刷新
//                        mIvLoadingPull.setImageResource(R.drawable.loosen_arrow);
                        RotateAnimation rotateAnimationPull = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        rotateAnimationPull.setDuration(200);
                        rotateAnimationPull.setFillAfter(true);
                        mIvLoadingPull.setAnimation(rotateAnimationPull);
                        mTvLoadingPull.setText("松开立即刷新");
                        isCanRefresh = true;
                    }

                } else {
                    if (isCanRefresh) {
                        //下拉可以刷新
//                        mIvLoadingPull.setImageResource(R.drawable.pullrefresh_arrow1);
                        RotateAnimation rotateAnimationPullBack = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        rotateAnimationPullBack.setDuration(200);
                        rotateAnimationPullBack.setFillAfter(true);
                        mIvLoadingPull.setAnimation(rotateAnimationPullBack);
                        mTvLoadingPull.setText("下拉可以刷新");
                        isCanRefresh = false;
                    }
                }
                break;
            }
            case 3: { //正在刷新
                if (!isRefresh) {
                    mLoadingHeaderView.invalidate();
                    if (mPtrFrame.getHeaderView() != mLoadingHeaderView) {
                        mPtrFrame.setHeaderView(mLoadingHeaderView);
                    }
                    isRefresh = true;
                }
                break;
            }
            case 4: { //刷新完返回顶部中
                isRefresh = false;
                isCanRefresh = false;
                mIvLoadingPull.invalidate();
                mTvLoadingPull.setText("下拉可以刷新");
                break;
            }
            case 1: { //完全返回顶部
                if (mPtrFrame.getHeaderView() != mPullHeaderView) {
                    //已经刷新过
                    mPtrFrame.setHeaderView(mPullHeaderView);
                }
                break;
            }
        }
    }
}
