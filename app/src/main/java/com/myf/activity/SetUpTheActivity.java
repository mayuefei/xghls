package com.myf.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.myf.base.BaseActivity;
import com.xghls.R;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 设置界面
 */
public class SetUpTheActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_the);
        ButterKnife.bind(this);
        initView();
        initDada();
        initEvent();

    }

    private void initView() {
        mTvTitle.setText("设置");
        mTvTitle.setTextColor(Color.parseColor("#fe8cab"));
        mRlTitleBg.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    private void initDada() {

    }

    private void initEvent() {
        //代取提醒
        mIvRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //退出登录
        mBtnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
