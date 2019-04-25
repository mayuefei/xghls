package com.myf.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.myf.base.BaseActivity;
import com.myf.model.LoginRespones;
import com.myf.okhttp.OkHttpApi;
import com.myf.util.LogUtil;
import com.myf.util.SharedPreferencesUtil;
import com.myf.util.ToastUtil;
import com.myf.util.UserUtil;
import com.xghls.R;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

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
                    String exit = (String) SharedPreferencesUtil.getSharedPreferences(SplashActivity.this, PersonalCenterActivity.EXIT_KEY, "");
                    if (TextUtils.isEmpty(exit)) {
                        String token = (String) SharedPreferencesUtil.getSharedPreferences(SplashActivity.this,"PREF_USER_ID_KEY","");
                        String account = (String) SharedPreferencesUtil.getSharedPreferences(SplashActivity.this, LoginActivity.PREF_ROLE_ACCOUNT_KEY, "");
                        String password = (String) SharedPreferencesUtil.getSharedPreferences(SplashActivity.this, LoginActivity.PREF_ROLE_PASSWORD_KEY, "");
                        //获取账户密码自动登录进入主界面
                        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)){
                            showLoadingDialog(SplashActivity.this,true);
                            goLogin(account,password);
                            finish();
                        }else {
                            //否则进入登录界面
                            LoginActivity.actionStart(SplashActivity.this);
                            finish();
                        }
                    }else {
                        SharedPreferencesUtil.putSharedPreferences(SplashActivity.this,PersonalCenterActivity.EXIT_KEY,"");
                        LoginActivity.actionStart(SplashActivity.this);
                        finish();
                    }
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

    /**
     * 执行登录
     */
    private LoginRespones mLoginRespones;
    private void goLogin(String username,String password){
        OkHttpApi.getInstance().getLoginRespones(username, password, "android", new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                closeLoadingDialog();
                ToastUtil.showToast(SplashActivity.this,"联网错误",Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(String response, int id) {
                closeLoadingDialog();
                LogUtil.e(TAG,response);
                Gson gson = new Gson();
                mLoginRespones = gson.fromJson(response,LoginRespones.class);
                if (mLoginRespones != null){
                    if (mLoginRespones.code.equals("1")){
                        //登录成功
                        UserUtil.loginIn(mLoginRespones);
                        //跳转主页
                        GenerationTakeAndToMailActivity.actionStart(SplashActivity.this);
                        finish();
                    }else {
                        ToastUtil.showToast(SplashActivity.this,mLoginRespones.msg,Toast.LENGTH_SHORT);
                    }
                }else {
                    ToastUtil.showToast(SplashActivity.this,"解析失败",Toast.LENGTH_SHORT);
                }
            }
        },TAG);
    }
}
