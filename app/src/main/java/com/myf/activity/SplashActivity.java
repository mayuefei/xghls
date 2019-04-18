package com.myf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;

import com.myf.R;
import com.myf.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";
    @Bind(R.id.iv_splash)
    ImageView ivSplash;

    /**
     * 定时器用的时间
     */
    private CountDownTimer mCountDownTimer;
    private final static long COUNT_DOWN_TIME_TOTAL = 2000L;
    private final static long COUNT_DOWN_TIME_TICK = 1000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        if (mCountDownTimer == null) {
            mCountDownTimer = new CountDownTimer(COUNT_DOWN_TIME_TOTAL,COUNT_DOWN_TIME_TICK) {
                @Override
                public void onTick(long l) {
                    if (!SplashActivity.this.isFinishing()) {
                        int remainTime = ((int) (l / 1000L));
                    }
                }

                @Override
                public void onFinish() {
                    Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            };
            mCountDownTimer.start();
        }
    }

    private void initData() {

    }

    private void initEvent() {

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null){
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }
}
