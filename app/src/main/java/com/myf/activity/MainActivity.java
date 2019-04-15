package com.myf.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.myf.R;
import com.myf.base.BaseActivity;
import com.myf.okhttp.OkHttpApi;
import com.myf.util.LogUtil;
import com.myf.util.ToastUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        VerifyImage();
    }
    private void VerifyImage() {
        showLoadingDialog(this, false);
        OkHttpApi.getInstance().getVerifyImageRespones("15135160068", new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                closeLoadingDialog();
                ToastUtil.showToast(MainActivity.this, "网络连接失败", Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(String response, int id) {
                closeLoadingDialog();
                LogUtil.e(TAG, "刷新验证码：" + response);
             
            }
        }, TAG);
    }
}
