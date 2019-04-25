package com.myf.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.myf.base.BaseActivity;
import com.myf.util.ActivityManager;
import com.myf.util.SharedPreferencesUtil;
import com.xghls.R;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

/**
 * 个人中心界面
 */
public class PersonalCenterActivity extends BaseActivity {

    private static final String TAG = PersonalCenterActivity.class.getSimpleName();
    @Bind(R.id.iv_back)
    AutoLinearLayout mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.ll_set_up_the)
    AutoLinearLayout mLlSetUpThe;
    @Bind(R.id.rl_title_bg)
    AutoRelativeLayout mRlTitleBg;
    @Bind(R.id.iv_remind)
    ImageView mIvRemind;
    @Bind(R.id.btn_log_out)
    Button mBtnLogOut;
    @Bind(R.id.iv_to_take_part_head_portrait)
    ImageView mIvToTakePartHeadPortrait;
    @Bind(R.id.tv_to_take_part_name)
    TextView mTvToTakePartName;
    @Bind(R.id.ll_statistical)
    AutoLinearLayout mLlStatistical;

    //提醒标记
    private boolean remindTag = false;
    //退出程序标记
    public static final String EXIT_KEY = "EXIT_KEY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        ButterKnife.bind(this);
        ActivityManager.getInstance().addActivity(PersonalCenterActivity.this);
        initView();
        initDada();
        initEvent();

    }

    private void initView() {
        boolean remind = (boolean) SharedPreferencesUtil.getSharedPreferences(PersonalCenterActivity.this, "remindTag", false);
        if (remind){
            mIvRemind.setImageResource(R.mipmap.user_open);
        }else {
            mIvRemind.setImageResource(R.mipmap.user_close);
        }
        Log.e(TAG, "initView: "+remind );
        remindTag = remind;
        mTvTitle.setText("个人中心");
        mTvTitle.setTextColor(Color.parseColor("#ffffff"));
        mRlTitleBg.setBackgroundColor(Color.parseColor("#fe8cab"));
    }

    private void initDada() {


    }

    private void initEvent() {
        //统计
        mLlStatistical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //代寄提醒
        mIvRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!remindTag) {
                    remindTag = true;
                    mIvRemind.setImageResource(R.mipmap.user_open);
                    JPushInterface.init(getApplicationContext());
                }else {
                    remindTag = false;
                    mIvRemind.setImageResource(R.mipmap.user_close);
                    JPushInterface.stopPush(getApplicationContext());   //关闭推送的方法
                }
                SharedPreferencesUtil.putSharedPreferences(PersonalCenterActivity.this,"remindTag",remindTag);
            }
        });
        //退出登录
        mBtnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.actionStart(PersonalCenterActivity.this);
                ActivityManager.getInstance().exit();
                SharedPreferencesUtil.putSharedPreferences(PersonalCenterActivity.this,EXIT_KEY,"exit");
                finish();
            }
        });
    }

}
