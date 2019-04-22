package com.myf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myf.base.BaseActivity;
import com.myf.util.ToastUtil;
import com.xihls.R;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
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
                Intent intent = new Intent(LoginActivity.this,GenerationTakeAndToMailActivity.class);
                startActivity(intent);

            }
        });
    }
}
