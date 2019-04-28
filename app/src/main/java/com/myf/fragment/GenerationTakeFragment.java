package com.myf.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.myf.base.BaseFragment;
import com.myf.model.UserInfoRespones;
import com.myf.util.LogUtil;
import com.myf.util.RefreshListener;
import com.myf.util.ToastUtil;
import com.xghls.R;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * 代取界面
 */
public class GenerationTakeFragment extends BaseFragment implements RefreshListener {
    private static final String TAG = "GenerationTakeFragment";
    @Bind(R.id.rb_forenoon_single)
    RadioButton mRbForenoonSingle;
    @Bind(R.id.rb_afternoon_single)
    RadioButton mRbAfternoonSingle;
    @Bind(R.id.rb_that_very_day_summarizing)
    RadioButton mRbThatVeryDaySummarizing;
    @Bind(R.id.rg_day_list)
    RadioGroup mRgDayList;
    @Bind(R.id.tv_pick_up_number)
    TextView mTvPickUpNumber;
    @Bind(R.id.ll_to_take)
    AutoLinearLayout mLlToTake;
    @Bind(R.id.tv_uncheck_number)
    TextView mTvUncheckNumber;
    @Bind(R.id.ll_uncheck)
    AutoLinearLayout mLlUncheck;
    @Bind(R.id.tv_have_taken_number)
    TextView mTvHaveTakenNumber;
    @Bind(R.id.ll_have_taken)
    AutoLinearLayout mLlHaveTaken;
    @Bind(R.id.et_please_enter_number)
    EditText mEtPleaseEnterNumber;
    @Bind(R.id.iv_search)
    ImageView mIvSearch;
    @Bind(R.id.et_please_select)
    EditText mEtPleaseSelect;
    @Bind(R.id.rl_please_select)
    AutoLinearLayout mRlPleaseSelect;
    @Bind(R.id.iv_dormitory_building)
    ImageView mIvDormitoryBuilding;
    @Bind(R.id.iv_payment_time)
    ImageView mIvPaymentTime;
    @Bind(R.id.iv_state_time)
    ImageView mIvStateTime;
    @Bind(R.id.lv_the_order_list)
    ListView mLvTheOrderList;
    @Bind(R.id.pfl_frame_layout)
    PtrClassicFrameLayout mPflFrameLayout;
    @Bind(R.id.tv_dormitory_building)
    TextView mTvDormitoryBuilding;
    @Bind(R.id.ll_order_list)
    AutoLinearLayout mLlOrderList;
    @Bind(R.id.ll_payment_time)
    AutoLinearLayout mLlPaymentTime;
    @Bind(R.id.tv_state_time)
    TextView mTvStateTime;
    @Bind(R.id.ll_state_time)
    AutoLinearLayout mLlStateTime;
    @Bind(R.id.tv_payment_time)
    TextView mTvPaymentTime;
    @Bind(R.id.tv_dqj)
    TextView mTvDqj;
    @Bind(R.id.tv_wqdj)
    TextView mTvWqdj;
    @Bind(R.id.tv_yqj)
    TextView mTvYqj;

    private String orderBy1 = "1";
    private String orderBy2 = "1";
    private String orderBy3 = "1";
    private UserInfoRespones mUserInfoRespones;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mUserInfoRespones = (UserInfoRespones) bundle.getSerializable("mUserInfoRespones");
            LogUtil.e(TAG, "initView: " + mUserInfoRespones);
        }
        LogUtil.e(TAG, "initView: " + mUserInfoRespones);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.generation_take_fragment, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
//        //默认上午单
//        mRbForenoonSingle.setChecked(true);
//        mRbForenoonSingle.setTextColor(Color.parseColor("#ffffff"));
//        mRbForenoonSingle.setTextSize(TypedValue.COMPLEX_UNIT_PX, 36);
//        mRbForenoonSingle.getPaint().setFakeBoldText(true);
//        //默认待取件
//        mTvDqj.setTextColor(Color.parseColor("#fe8cab"));
//        mTvPickUpNumber.setTextColor(Color.parseColor("#fe8cab"));
//        //默认宿舍楼号正序
//        mLlOrderList.setBackgroundResource(R.drawable.bg_time_color);
//        mTvDormitoryBuilding.setTextColor(Color.parseColor("#ffffff"));
//        mIvDormitoryBuilding.setImageResource(R.mipmap.user_baiseshang);
    }

    private void initData() {

    }

    private void initEvent() {
        //当天按钮
        mRgDayList.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_forenoon_single://上午单
                        removeStagesTabState();
                        removesStagesTakeTabState();
                        removesStagesTimeTabState();
                        mRbForenoonSingle.setTextColor(Color.parseColor("#ffffff"));
                        mRbForenoonSingle.setTextSize(TypedValue.COMPLEX_UNIT_PX, 36);
                        mRbForenoonSingle.getPaint().setFakeBoldText(true);
                        break;
                    case R.id.rb_afternoon_single://下午单
                        removeStagesTabState();
                        removesStagesTakeTabState();
                        removesStagesTimeTabState();
                        mRbAfternoonSingle.setTextColor(Color.parseColor("#ffffff"));
                        mRbAfternoonSingle.setTextSize(TypedValue.COMPLEX_UNIT_PX, 36);
                        mRbAfternoonSingle.getPaint().setFakeBoldText(true);
                        break;
                    case R.id.rb_that_very_day_summarizing://当天汇总
                        removeStagesTabState();
                        removesStagesTakeTabState();
                        removesStagesTimeTabState();
                        mRbThatVeryDaySummarizing.setTextColor(Color.parseColor("#ffffff"));
                        mRbThatVeryDaySummarizing.setTextSize(TypedValue.COMPLEX_UNIT_PX, 36);
                        mRbThatVeryDaySummarizing.getPaint().setFakeBoldText(true);
                        break;
                }
            }
        });
        //待取件
        mLlToTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removesStagesTakeTabState();
                mTvPickUpNumber.setTextColor(Color.parseColor("#fe8cab"));
                mTvDqj.setTextColor(Color.parseColor("#fe8cab"));
            }
        });
        //未取件
        mLlUncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removesStagesTakeTabState();
                mTvUncheckNumber.setTextColor(Color.parseColor("#fe8cab"));
                mTvWqdj.setTextColor(Color.parseColor("#fe8cab"));
            }
        });
        //已取件
        mLlHaveTaken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removesStagesTakeTabState();
                mTvHaveTakenNumber.setTextColor(Color.parseColor("#fe8cab"));
                mTvYqj.setTextColor(Color.parseColor("#fe8cab"));
            }
        });
        //搜索按钮
        mIvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEtPleaseEnterNumber.getText().toString().trim())) {
                    ToastUtil.showToast(getActivity(), "请输入姓名或取件编号", Toast.LENGTH_SHORT);
                    return;
                }
                //调接口

            }
        });
        //选择快递公司
        mEtPleaseSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTheDeliveryTimeADialog(view);
            }
        });
        //宿舍楼号正序
        //宿舍号
        mLlOrderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removesStagesTimeTabState();
                mLlOrderList.setBackgroundResource(R.drawable.bg_time_color);
                mTvDormitoryBuilding.setTextColor(Color.parseColor("#ffffff"));
                if (orderBy1.equals("1")) {
                    orderBy1 = "2";
                    mIvDormitoryBuilding.setImageResource(R.mipmap.user_baiseshang);
                } else {
                    mIvDormitoryBuilding.setImageResource(R.mipmap.user_baisexia);
                    orderBy1 = "1";
                }
            }
        });
        //支付时间
        mLlPaymentTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removesStagesTimeTabState();
                mLlPaymentTime.setBackgroundResource(R.drawable.bg_time_color);
                mTvPaymentTime.setTextColor(Color.parseColor("#ffffff"));
                if (orderBy2.equals("1")) {
                    orderBy2 = "2";
                    mIvPaymentTime.setImageResource(R.mipmap.user_baiseshang);
                } else {
                    orderBy2 = "1";
                    mIvPaymentTime.setImageResource(R.mipmap.user_baisexia);
                }
            }
        });
        //状态时间
        mLlStateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removesStagesTimeTabState();
                mLlStateTime.setBackgroundResource(R.drawable.bg_time_color);
                mTvStateTime.setTextColor(Color.parseColor("#ffffff"));
                if (orderBy3.equals("1")) {
                    orderBy3 = "2";
                    mIvStateTime.setImageResource(R.mipmap.user_baiseshang);
                } else {
                    orderBy3 = "1";
                    mIvStateTime.setImageResource(R.mipmap.user_baisexia);
                }
            }
        });
    }

    //当天按钮清除选中效果
    private void removeStagesTabState() {
        mRbForenoonSingle.setTextColor(Color.parseColor("#000000"));
        mRbForenoonSingle.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
        mRbForenoonSingle.getPaint().setFakeBoldText(false);
        mRbAfternoonSingle.setTextColor(Color.parseColor("#000000"));
        mRbAfternoonSingle.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
        mRbAfternoonSingle.getPaint().setFakeBoldText(false);
        mRbThatVeryDaySummarizing.setTextColor(Color.parseColor("#000000"));
        mRbThatVeryDaySummarizing.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
        mRbThatVeryDaySummarizing.getPaint().setFakeBoldText(false);
    }

    //取件按钮清除选中效果
    private void removesStagesTakeTabState() {
        mTvDqj.setTextColor(Color.parseColor("#000000"));
        mTvPickUpNumber.setTextColor(Color.parseColor("#000000"));
        mTvWqdj.setTextColor(Color.parseColor("#000000"));
        mTvUncheckNumber.setTextColor(Color.parseColor("#000000"));
        mTvYqj.setTextColor(Color.parseColor("#000000"));
        mTvHaveTakenNumber.setTextColor(Color.parseColor("#000000"));
    }

    //宿舍楼号正序清除选中效果
    private void removesStagesTimeTabState() {
        mLlOrderList.setBackgroundResource(R.drawable.bg_time_no_color);
        mTvDormitoryBuilding.setTextColor(Color.parseColor("#fe8cab"));
        mIvDormitoryBuilding.setImageResource(R.mipmap.user_shangjiantou);
        mLlPaymentTime.setBackgroundResource(R.drawable.bg_time_no_color);
        mTvPaymentTime.setTextColor(Color.parseColor("#fe8cab"));
        mIvPaymentTime.setImageResource(R.mipmap.user_shangjiantou);
        mLlStateTime.setBackgroundResource(R.drawable.bg_time_no_color);
        mTvStateTime.setTextColor(Color.parseColor("#fe8cab"));
        mIvStateTime.setImageResource(R.mipmap.user_shangjiantou);
    }

    /**
     * 选择快递公司弹出框
     */
    private AlertDialog chooseExpressCompanyAlertDialog; //单选框

    //学校Dialog列表
    public void showTheDeliveryTimeADialog(View view) {
        final String[] items = {"圆通速递", "百世汇通", "申通快递", "中通快递", "韵达速递", "天天快递", "顺丰速运", "京东", "苏宁易购", "唯品会", "天猫", "快捷快递", "邮政", "全峰快递", "建华快递", "当当网"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        alertBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mEtPleaseSelect.setText(items[i]);
                chooseExpressCompanyAlertDialog.dismiss();
            }
        });
        chooseExpressCompanyAlertDialog = alertBuilder.create();
        chooseExpressCompanyAlertDialog.show();
    }

    @Override
    public void refresh() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}