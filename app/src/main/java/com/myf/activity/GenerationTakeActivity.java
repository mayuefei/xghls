package com.myf.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.myf.adapter.CourierCompanyAdapter;
import com.myf.base.BaseActivity;
import com.xihls.R;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GenerationTakeActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    AutoLinearLayout mIvBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.rl_title_bg)
    AutoRelativeLayout mRlTitleBg;
    @Bind(R.id.tv_the_consignee_name)
    TextView mTvTheConsigneeName;
    @Bind(R.id.tv_the_consignee_phone_number)
    TextView mTvTheConsigneePhoneNumber;
    @Bind(R.id.tv_shipping_address)
    TextView mTvShippingAddress;
    @Bind(R.id.ll_shipping_address_info)
    AutoLinearLayout mLlShippingAddressInfo;
    @Bind(R.id.ll_choose_address)
    AutoLinearLayout mLlChooseAddress;
    @Bind(R.id.et_pickup_code)
    EditText mEtPickupCode;
    @Bind(R.id.et_choose_courier_company)
    EditText mEtChooseCourierCompany;
    @Bind(R.id.et_note_information)
    EditText mEtNoteInformation;
    @Bind(R.id.et_the_delivery_time)
    EditText mEtTheDeliveryTime;
    @Bind(R.id.tv_money)
    TextView mTvMoney;
    @Bind(R.id.btn_submit_orders)
    Button mBtnSubmitOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generation_take);
        ButterKnife.bind(this);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mRlTitleBg.setBackgroundColor(Color.parseColor("#fe8cab"));
        mTvTitle.setText("我要代取");
    }

    private void initData() {

    }

    private void initEvent() {

        //选择快递公司
        mEtChooseCourierCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChooseExpressCompanyAlertDialog(view);
            }
        });

        //配送时间
        mEtTheDeliveryTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTheDeliveryTimeADialog(view);
            }
        });
    }

    /**
     * 选择快递公司
     */
    private AlertDialog chooseExpressCompanyAlertDialog; //单选框
    private ArrayList<Integer>list;
    //学校Dialog列表
    public void showChooseExpressCompanyAlertDialog(View view){
        list = new ArrayList<>();
        list.add(R.mipmap.user_baishi);
        list.add(R.mipmap.user_jingdong);
        list.add(R.mipmap.user_kuaijie);
        list.add(R.mipmap.user_quanfeng);
        list.add(R.mipmap.user_shentong);
        list.add(R.mipmap.user_shunfeng);
        list.add(R.mipmap.user_suningyigou);
        list.add(R.mipmap.user_tianmao);
        list.add(R.mipmap.user_tiantian);
        list.add(R.mipmap.user_yuantong);
        list.add(R.mipmap.user_yunda);
        list.add(R.mipmap.user_zhongtong);
        final String[] name = {"百世汇通","京东","快捷快递","全峰快递","申通快递","顺丰速运","苏宁易购","天猫","天天快递","圆通速递","韵达快递","中通速递"};
        CourierCompanyAdapter adapter = new CourierCompanyAdapter(list,GenerationTakeActivity.this);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mEtChooseCourierCompany.setText(name[i]);
                chooseExpressCompanyAlertDialog.dismiss();
            }
        });
        chooseExpressCompanyAlertDialog = alertBuilder.create();
        chooseExpressCompanyAlertDialog.show();
    }

    /**
     * 配送时间弹出框
     */
    private AlertDialog theDeliveryTimeAlertDialog; //单选框
    //学校Dialog列表
    public void showTheDeliveryTimeADialog(View view){
        final String[] items = {"上午", "中午", "晚上"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mEtTheDeliveryTime.setText(items[i]);
                theDeliveryTimeAlertDialog.dismiss();
            }
        });
        theDeliveryTimeAlertDialog = alertBuilder.create();
        theDeliveryTimeAlertDialog.show();
    }
}
