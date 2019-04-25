package com.myf.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.myf.base.BaseActivity;
import com.myf.model.LoginRespones;
import com.myf.okhttp.OkHttpApi;
import com.myf.util.ActivityManager;
import com.myf.util.LogUtil;
import com.myf.util.SharedPreferencesUtil;
import com.myf.util.ToastUtil;
import com.myf.util.UserUtil;
import com.xghls.R;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    @Bind(R.id.iv_back)
    AutoLinearLayout mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.et_login_username)
    EditText mEtLoginUsername;
    @Bind(R.id.et_login_password)
    EditText mEtLoginPassword;
    @Bind(R.id.btn_login)
    Button mBtnLogin;

    public static final String PREF_ROLE_ACCOUNT_KEY = "PREF_ROLE_ACCOUNT_KEY";//用户账号
    public static final String PREF_ROLE_PASSWORD_KEY = "PREF_ROLE_PASSWORD_KEY";//用户密码
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        JPushInterface.init(getApplicationContext());
        ActivityManager.getInstance().addActivity(LoginActivity.this);
        initView();
        initData();
        initEvent();
    }
    public static void actionStart(Context context){
        Intent intent = new Intent();
        intent.setClass(context,LoginActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        String account = (String) SharedPreferencesUtil.getSharedPreferences(LoginActivity.this,PREF_ROLE_ACCOUNT_KEY,"");
        if (!TextUtils.isEmpty(account)){
            mEtLoginUsername.setText(account);
            mEtLoginPassword.setFocusable(true);
            mEtLoginPassword.setFocusableInTouchMode(true);
            mEtLoginPassword.requestFocus();
            mEtLoginPassword.findFocus();
        }
        //禁止EditText输入空格
        setEditTextInhibitInputSpaChat(mEtLoginUsername);
        setEditTextInhibitInputSpaChat(mEtLoginPassword);
        mTvTitle.setText("登录");
    }

    private void initData() {

    }

    private void initEvent() {
        //登录按钮
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEtLoginUsername.getText().toString().trim())) {
                    ToastUtil.showToast(LoginActivity.this,"请输入您的账号",Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtils.isEmpty(mEtLoginPassword.getText().toString().trim())) {
                    ToastUtil.showToast(LoginActivity.this,"请输入您的密码",Toast.LENGTH_SHORT);
                    return;
                }
                if (mEtLoginPassword.getText().toString().trim().length() < 6){
                    ToastUtil.showToast(LoginActivity.this,"请输入正确的密码",Toast.LENGTH_SHORT);
                    return;
                }
                showLoadingDialog(LoginActivity.this,true);
                goLogin(mEtLoginUsername.getText().toString().trim(),mEtLoginPassword.getText().toString().trim());
            }
        });
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
                ToastUtil.showToast(LoginActivity.this,"联网错误",Toast.LENGTH_SHORT);
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
                        //保存账户名称
                        SharedPreferencesUtil.putSharedPreferences(LoginActivity.this,PREF_ROLE_ACCOUNT_KEY,mEtLoginUsername.getText().toString().trim());
                        //保存账户密码
                        SharedPreferencesUtil.putSharedPreferences(LoginActivity.this,PREF_ROLE_PASSWORD_KEY,mEtLoginPassword.getText().toString().trim());
                        //跳转主页
                        GenerationTakeAndToMailActivity.actionStart(LoginActivity.this);
                        finish();
                    }else {
                        ToastUtil.showToast(LoginActivity.this,mLoginRespones.msg,Toast.LENGTH_SHORT);
                    }
                }else {
                    ToastUtil.showToast(LoginActivity.this,"解析失败",Toast.LENGTH_SHORT);
                }
            }
        },TAG);
    }
}
