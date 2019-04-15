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
    }
}
