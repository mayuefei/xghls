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
import android.widget.TextView;

import com.myf.R;
import com.myf.base.BaseActivity;
import com.myf.fragment.GenerationTakeFragment;
import com.myf.fragment.ToMailFragment;
import com.myf.listener.HintTwoSelectListener;
import com.myf.util.Constant;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 代取代寄界面
 */
public class GenerationTakeAndToMailActivity extends BaseActivity {
    private static final int TAB_POSITION_DAIQU = 0;//代取
    private static final int TAB_POSITION_DAIJI = 1; //代寄
    @Bind(R.id.iv_back)
    AutoLinearLayout mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.rl_title_bg)
    AutoRelativeLayout mRlTitleBg;
    @Bind(R.id.tv_to_take_part_name)
    TextView mTvToTakePartName;
    @Bind(R.id.tv_xiaoge_daiqu_name)
    TextView mTvXiaogeDaiquName;
    @Bind(R.id.tv_daiqu_number)
    TextView mTvDaiquNumber;
    @Bind(R.id.v_xgdq_bg)
    View mVXgdqBg;
    @Bind(R.id.rl_xgdq)
    AutoRelativeLayout mRlXgdq;
    @Bind(R.id.tv_xiaoge_daiji_name)
    TextView mTvXiaogeDaijiName;
    @Bind(R.id.tv_daiji_number)
    TextView mTvDaijiNumber;
    @Bind(R.id.v_xgdj_bg)
    View mVXgdjBg;
    @Bind(R.id.rl_xgdj)
    AutoRelativeLayout mRlXgdj;
    @Bind(R.id.rl_container)
    AutoRelativeLayout mRlContainer;
    private LoginStateChangeReceiver mLoginStateChangeReceiver;
    private GenerationTakeFragment mGenerationTakeFragment;//代取
    private ToMailFragment mToMailFragment;//代寄
    private int mCurrentTabPosition = -1;//当前所在界面的标记
    private int mTargetTabPosition = TAB_POSITION_DAIQU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generation_take_and_to_mail);
        ButterKnife.bind(this);
        //注册需要的广播
        registerReceivers();
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        //代取
        mRlXgdq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTabClick(TAB_POSITION_DAIQU);
            }
        });
        //代寄
        mRlXgdj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTabClick(TAB_POSITION_DAIJI);
            }
        });
    }

    private void initData() {

    }

    private void initView() {
        mRlTitleBg.setBackgroundColor(Color.parseColor("#fe8cab"));
        mTvTitle.setText("山西传媒学院");
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
            case TAB_POSITION_DAIQU:
                mTvXiaogeDaiquName.setTextColor(Color.parseColor("#fe8cab"));
                mTvDaiquNumber.setTextColor(Color.parseColor("#fe8cab"));
                mVXgdqBg.setBackgroundColor(Color.parseColor("#fe8cab"));
                break;
            case TAB_POSITION_DAIJI:
                mTvXiaogeDaijiName.setTextColor(Color.parseColor("#fe8cab"));
                mTvDaijiNumber.setTextColor(Color.parseColor("#fe8cab"));
                mVXgdjBg.setBackgroundColor(Color.parseColor("#fe8cab"));
                break;
        }
    }

    /**
     * 清除所有被点击的按钮，设置为默认灰色
     */
    private void clearAllTabState() {
        mTvXiaogeDaiquName.setTextColor(Color.parseColor("#000000"));
        mTvDaiquNumber.setTextColor(Color.parseColor("#000000"));
        mVXgdqBg.setBackgroundColor(Color.parseColor("#ffffff"));
        mTvXiaogeDaijiName.setTextColor(Color.parseColor("#000000"));
        mTvDaijiNumber.setTextColor(Color.parseColor("#000000"));
        mVXgdjBg.setBackgroundColor(Color.parseColor("#ffffff"));
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
            case TAB_POSITION_DAIQU://代取
                if (mGenerationTakeFragment == null) {
                    mGenerationTakeFragment = new GenerationTakeFragment();
                    transaction.add(R.id.rl_container, mGenerationTakeFragment);
                } else {
                    mGenerationTakeFragment.refresh();
                    transaction.show(mGenerationTakeFragment);
                }
                break;
            case TAB_POSITION_DAIJI://代寄
                if (mToMailFragment == null) {
                    mToMailFragment = new ToMailFragment();
                    transaction.add(R.id.rl_container, mToMailFragment);
                } else {
                    mToMailFragment.refresh();
                    transaction.show(mToMailFragment);
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
        if (mGenerationTakeFragment != null) {
            transaction.hide(mGenerationTakeFragment);
        }
        if (mToMailFragment != null) {
            transaction.hide(mToMailFragment);
        }
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
            if (mGenerationTakeFragment != null) {
                mGenerationTakeFragment.refresh();
            }
            if (mToMailFragment != null) {
                mToMailFragment.refresh();
            }
        }
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
