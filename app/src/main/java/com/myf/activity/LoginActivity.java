package com.myf.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import butterknife.OnClick;
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
    private String mUserName;
    private String mPassword;
    private String registrationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        JPushInterface.init(getApplicationContext());
        ActivityManager.getInstance().addActivity(LoginActivity.this);
        initView();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        String account = (String) SharedPreferencesUtil.getSharedPreferences(LoginActivity.this, PREF_ROLE_ACCOUNT_KEY, "");
        if (!TextUtils.isEmpty(account)) {
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
        registrationID = JPushInterface.getRegistrationID(LoginActivity.this);
    }

    /**
     * 执行登录
     */
    private void goLogin(String username, String password,String registrationId) {
        OkHttpApi.getInstance().getLoginRespones(username, password,registrationId, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                closeLoadingDialog();
                ToastUtil.showToast(LoginActivity.this, getString(R.string.netError), Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(String response, int id) {
                closeLoadingDialog();
                LogUtil.e(TAG, response);
                Gson gson = new Gson();
                LoginRespones mLoginRespones = gson.fromJson(response, LoginRespones.class);
                if (mLoginRespones != null) {
                    if (mLoginRespones.code.equals("1")) {
                        //登录成功
                        UserUtil.loginIn(mLoginRespones);
                        //保存账户名称
                        SharedPreferencesUtil.putSharedPreferences(LoginActivity.this, PREF_ROLE_ACCOUNT_KEY, mUserName);
                        //保存账户密码
                        SharedPreferencesUtil.putSharedPreferences(LoginActivity.this, PREF_ROLE_PASSWORD_KEY, mPassword);
                        //跳转主页
                        GenerationTakeAndToMailActivity.actionStart(LoginActivity.this);
                        finish();
                    } else {
                        ToastUtil.showToast(LoginActivity.this, mLoginRespones.msg, Toast.LENGTH_SHORT);
                    }
                } else {
                    ToastUtil.showToast(LoginActivity.this, "解析失败", Toast.LENGTH_SHORT);
                }
            }
        }, TAG);
    }

    @OnClick({R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                mUserName = mEtLoginUsername.getText().toString().trim();
                mPassword = mEtLoginPassword.getText().toString().trim();
                if (TextUtils.isEmpty(mUserName)) {
                    ToastUtil.showToast(LoginActivity.this, "请输入您的账号", Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtils.isEmpty(mPassword)) {
                    ToastUtil.showToast(LoginActivity.this, "请输入您的密码", Toast.LENGTH_SHORT);
                    return;
                }
                if (mPassword.length() < 6) {
                    ToastUtil.showToast(LoginActivity.this, "请输入正确的密码", Toast.LENGTH_SHORT);
                    return;
                }
                showLoadingDialog(LoginActivity.this, true);
                goLogin(mUserName, mPassword,registrationID);
                break;
        }
    }
}
