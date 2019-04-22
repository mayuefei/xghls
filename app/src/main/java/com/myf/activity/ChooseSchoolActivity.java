package com.myf.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.myf.base.BaseActivity;
import com.xihls.R;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 选择学校界面
 */
public class ChooseSchoolActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    AutoLinearLayout mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.rl_title_bg)
    AutoRelativeLayout mRlTitleBg;
    @Bind(R.id.tv_school_name)
    TextView mTvSchoolName;
    @Bind(R.id.ll_school_click)
    AutoLinearLayout mLlSchoolClick;
    @Bind(R.id.btn_enter)
    Button mBtnEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_school);
        ButterKnife.bind(this);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mRlTitleBg.setBackgroundColor(Color.parseColor("#00000000"));
        mTvTitle.setText("选择学校");
        mTvTitle.setTextColor(Color.parseColor("#ff8bac"));
    }

    private void initData() {

    }

    private void initEvent() {
        //请选择学校
        mLlSchoolClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSingleAlertDialog(view);
            }
        });
        //进入按钮
        mBtnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ChooseSchoolActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }
    private AlertDialog alertDialog; //单选框
    //学校Dialog列表
    public void showSingleAlertDialog(View view){
        final String[] items = {"山西传媒学院", "山西建筑职业技术学院", "山西交通学院", "晋中学院","太原师范学院"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mTvSchoolName.setText(items[i]);
                alertDialog.dismiss();
            }
        });
        alertDialog = alertBuilder.create();
        alertDialog.show();
    }
}
