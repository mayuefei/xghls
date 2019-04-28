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
import android.widget.Toast;

import com.google.gson.Gson;
import com.myf.base.BaseActivity;
import com.myf.fragment.GenerationTakeFragment;
import com.myf.fragment.ToMailFragment;
import com.myf.listener.HintTwoSelectListener;
import com.myf.model.UserInfoRespones;
import com.myf.okhttp.OkHttpApi;
import com.myf.util.ActivityManager;
import com.myf.util.Constant;
import com.myf.util.InitComm;
import com.myf.util.LogUtil;
import com.myf.util.ToastUtil;
import com.xghls.R;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 代取代寄界面
 */
public class GenerationTakeAndToMailActivity extends BaseActivity {
    private static final int TAB_POSITION_DAIQU = 0;//代取
    private static final int TAB_POSITION_DAIJI = 1; //代寄
    private static final String TAG = GenerationTakeAndToMailActivity.class.getSimpleName();
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
    @Bind(R.id.ll_set_up_the)
    AutoLinearLayout mLlSetUpThe;
    private LoginStateChangeReceiver mLoginStateChangeReceiver;
    private GenerationTakeFragment mGenerationTakeFragment;//代取
    private ToMailFragment mToMailFragment;//代寄
    private int mCurrentTabPosition = -1;//当前所在界面的标记
    private UserInfoRespones mUserInfoRespones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generation_take_and_to_mail);
        ButterKnife.bind(this);
        //注册需要的广播
        registerReceivers();
        ActivityManager.getInstance().addActivity(GenerationTakeAndToMailActivity.this);
        initView();
        initData();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, GenerationTakeAndToMailActivity.class);
        context.startActivity(intent);
    }

    private void initData() {
        showLoadingDialog(GenerationTakeAndToMailActivity.this, true);
        refreshData();
    }

    private void refreshData() {
        getUserInfo();
    }

    private void initView() {
        //设置
        mLlSetUpThe.setVisibility(View.VISIBLE);
        mRlTitleBg.setBackgroundColor(Color.parseColor("#fe8cab"));
        mTvTitle.setText("山西传媒学院");
        //根据mTargetTabPosition的值显示不同的界面
        onTabClick(TAB_POSITION_DAIQU);
    }

    /**
     * 根据mTargetTabPostion的值显示不同的界面
     *
     * @param tabPosition 标签
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
     * @param tabPosition 标签
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
     * @param tabPosition 标签
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
     * @param transaction 标签
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

    @OnClick({R.id.ll_set_up_the, R.id.rl_xgdq, R.id.rl_xgdj})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_set_up_the://个人中心
                Intent intent = new Intent(GenerationTakeAndToMailActivity.this, PersonalCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_xgdq://代取
                onTabClick(TAB_POSITION_DAIQU);
                break;
            case R.id.rl_xgdj://代寄
                onTabClick(TAB_POSITION_DAIJI);
                break;
        }
    }

    /**
     * 登陆广播类
     */
    private class LoginStateChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
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

    /**
     * 骑手端员工信息
     */
    private void getUserInfo() {
        OkHttpApi.getInstance().getUserInfoRespones(InitComm.init().userToken, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                closeLoadingDialog();
                ToastUtil.showToast(GenerationTakeAndToMailActivity.this, getString(R.string.netError), Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(String response, int id) {
                closeLoadingDialog();
                LogUtil.e(TAG, response);
                Gson gson = new Gson();
                mUserInfoRespones = gson.fromJson(response, UserInfoRespones.class);
                if (mUserInfoRespones != null) {
                    if (mUserInfoRespones.code.equals("1")) {
                        mTvTitle.setText(mUserInfoRespones.data.schoolName);
                        mTvToTakePartName.setText(mUserInfoRespones.data.userName);
                        Bundle sendBundle = new Bundle();
                        sendBundle.putSerializable("mUserInfoRespones", mUserInfoRespones);
                        mGenerationTakeFragment.setArguments(sendBundle);
                    } else {
                        ToastUtil.showToast(GenerationTakeAndToMailActivity.this, mUserInfoRespones.msg, Toast.LENGTH_SHORT);
                    }
                } else {
                    ToastUtil.showToast(GenerationTakeAndToMailActivity.this, "解析失败", Toast.LENGTH_SHORT);
                }
            }
        }, TAG);
    }
}
