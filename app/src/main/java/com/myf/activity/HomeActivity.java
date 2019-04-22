package com.myf.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.myf.R;
import com.myf.base.BaseActivity;
import com.myf.fragment.HomeFragment;
import com.myf.fragment.MyFragment;
import com.myf.listener.HintTwoSelectListener;
import com.myf.util.Constant;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

    @Bind(R.id.rl_container)
    AutoRelativeLayout mRlContainer;
    @Bind(R.id.iv_home)
    ImageView mIvHome;
    @Bind(R.id.tv_home)
    TextView mTvHome;
    @Bind(R.id.ll_home)
    AutoLinearLayout mLlHome;
    @Bind(R.id.iv_personal_center)
    ImageView mIvPersonalCenter;
    @Bind(R.id.tv_personal_center)
    TextView mTvPersonalCenter;
    @Bind(R.id.ll_personal_center)
    AutoLinearLayout mLlPersonalCenter;

    private static final int TAB_POSITION_HOME = 0;//首页
    private static final int TAB_POSITION_MY = 1; //个人中心
    @Bind(R.id.rl_home_page)
    AutoRelativeLayout mRlHomePage;
    private LoginStateChangeReceiver mLoginStateChangeReceiver;
    private HomeFragment mHomeFragment;//首页
    private MyFragment mMyFragment;//个人中心
    private int mCurrentTabPosition = -1;//当前所在界面的标记
    private int mTargetTabPosition = TAB_POSITION_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        //注册需要的广播
        registerReceivers();
        initView();
        initData();
        initEvent();
    }

    /**
     * 注册需要的广播
     */
    private void registerReceivers() {
        //注册广播过滤条件
        IntentFilter loginStateChangeFilter = new IntentFilter(Constant.BROADCAST_ACTION_LOGIN_STATE_CHANGE);
        mLoginStateChangeReceiver = new LoginStateChangeReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(mLoginStateChangeReceiver, loginStateChangeFilter);
    }

    /**
     * 登陆广播类
     */
    private class LoginStateChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //每次登陆后判断是否绑定芯盾
            if (mHomeFragment != null) {
                mHomeFragment.refresh();
            }
            if (mMyFragment != null) {
                mMyFragment.refresh();
            }
        }
    }


    private void initView() {
        //根据mTargetTabPosition的值显示不同的界面
        onTabClick(mTargetTabPosition);
    }

    /**
     * 根据mTargetTabPostion的值显示不同的界面
     *
     * @param tabPosition
     */
    private void onTabClick(int tabPosition) {
        if (mCurrentTabPosition == tabPosition) {
            //是当前的界面
            return;
        }
        //重新赋值标签
        mCurrentTabPosition = tabPosition;
        changeTabState(tabPosition);
        changeFragment(tabPosition);
    }

    /**
     * 切换底部按钮样式
     *
     * @param tabPosition
     */
    private void changeTabState(int tabPosition) {
        clearAllTabState();
        switch (tabPosition) {
            case TAB_POSITION_HOME:
                mLlHome.setBackgroundColor(Color.parseColor("#ff8bac"));
                mIvHome.setImageResource(R.mipmap.user_home_page);
                mTvHome.setTextColor(Color.parseColor("#ffffff"));
                break;
            case TAB_POSITION_MY:
                mLlPersonalCenter.setBackgroundColor(Color.parseColor("#ff8bac"));
                mIvPersonalCenter.setImageResource(R.mipmap.user_icon1);
                mTvPersonalCenter.setTextColor(Color.parseColor("#ffffff"));
                break;
        }
    }

    /**
     * 清除所有被点击的按钮，设置为默认灰色
     */
    private void clearAllTabState() {
        mIvHome.setImageResource(R.mipmap.user_home_page1);
        mTvHome.setTextColor(Color.parseColor("#ff8bac"));
        mLlHome.setBackgroundColor(Color.parseColor("#ffffff"));
        mLlPersonalCenter.setBackgroundColor(Color.parseColor("#ffffff"));
        mIvPersonalCenter.setImageResource(R.mipmap.user_icon);
        mTvPersonalCenter.setTextColor(Color.parseColor("#ff8bac"));
    }

    /**
     * 切换显示的Fragment
     *
     * @param tabPosition
     */
    private void changeFragment(int tabPosition) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        switch (tabPosition) {
            case TAB_POSITION_HOME://跳转到首页
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    transaction.add(R.id.rl_container, mHomeFragment);
                } else {
                    mHomeFragment.refresh();
                    transaction.show(mHomeFragment);
                }
                break;
            case TAB_POSITION_MY://个人中心
                if (mMyFragment == null) {
                    mMyFragment = new MyFragment();
                    transaction.add(R.id.rl_container, mMyFragment);
                } else {
                    mMyFragment.refresh();
                    transaction.show(mMyFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 隐藏所有fragment
     *
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mMyFragment != null) {
            transaction.hide(mMyFragment);
        }
    }

    private void initData() {

    }

    private void initEvent() {
        //首页
        mLlHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRlHomePage.setBackgroundResource(R.mipmap.home_page);
                onTabClick(TAB_POSITION_HOME);
            }
        });
        //个人中心
        mLlPersonalCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRlHomePage.setBackgroundResource(R.mipmap.my_background);
                onTabClick(TAB_POSITION_MY);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mLoginStateChangeReceiver);
    }

    @Override
    public void onBackPressed() {
        showHintTwo(this, "是否退出程序", new HintTwoSelectListener() {
            @Override
            public void makeSure() {
                finish();
            }

            @Override
            public void cancel() {

            }
        });
    }
}
