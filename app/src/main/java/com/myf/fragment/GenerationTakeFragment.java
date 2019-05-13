package com.myf.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.myf.adapter.CourierCompanyAdapter;
import com.myf.base.BaseFragment;
import com.myf.listener.KeyBoardShowListener;
import com.myf.model.EditStatusRespones;
import com.myf.model.OrderListsRespones;
import com.myf.model.UserInfoRespones;
import com.myf.okhttp.OkHttpApi;
import com.myf.util.DialogUtil;
import com.myf.util.InitComm;
import com.myf.util.LogUtil;
import com.myf.util.RefreshListener;
import com.myf.util.ToastUtil;
import com.myf.view.JZBKLoadingHandler;
import com.xghls.R;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.Call;

/**
 * 代取界面
 */
@SuppressLint("ValidFragment")
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
    @Bind(R.id.scrollView)
    ScrollView mScrollView;
    @Bind(R.id.bt_gotop)
    Button mBtGotop;

    private String orderBy1 = "1";
    private String orderBy2 = "1";
    private String orderBy3 = "1";
    private UserInfoRespones mUserInfoRespones;
    private ArrayList<UserInfoRespones.DataBean.ExpressDataBean> list = new ArrayList<>();
    private TheOrderListAdapter mTheOrderListAdapter;
    private List<OrderListsRespones.DataBean.OrderDataBean> data = new ArrayList<>();
    private long lastClickTime = 0;//最后点击时间
    // 屏蔽连续的点击事件
    public static final int MIN_CLICK_DELAY_TIME = 500;

    public GenerationTakeFragment(UserInfoRespones userInfoRespones) {
        mUserInfoRespones = userInfoRespones;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.generation_take_fragment, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initfindId();
        initView();
        initData();
        initEvent();
    }

    private void initfindId() {
        new KeyBoardShowListener(getActivity()).setKeyboardListener(new KeyBoardShowListener.OnKeyboardVisibilityListener() {

            @Override
            public void onVisibilityChanged(boolean visible) {
                if (visible) {
//	                    ToastUtil.showToast(InvestProductQueryActivity.this,"监听到软键盘弹起...1", Toast.LENGTH_SHORT);
                } else {
//	                    ToastUtil.showToast(InvestProductQueryActivity.this,"监听到软件盘关闭...2",Toast.LENGTH_SHORT);
                    if (TextUtils.isEmpty(mEtPleaseEnterNumber.getText().toString().trim())) {
                        data.clear();
                        mTheOrderListAdapter.notifyDataSetChanged();
                        getExpressLists(expressTimeType, status, "", "", "", "", "");
                    } else {
                        startSeek();
                    }
                }

            }
        }, getActivity());

    }

    private void initView() {
        mScrollView.smoothScrollTo(0, 0);
        setEditTextInhibitInputSpaChat(mEtPleaseEnterNumber);
        list.clear();
        list.addAll(mUserInfoRespones.data.expressData);
//        //默认上午单
//        mRbForenoonSingle.setChecked(true);
//        mRbForenoonSingle.setTextColor(Color.parseColor("#ffffff"));
//        mRbForenoonSingle.setTextSize(TypedValue.COMPLEX_UNIT_PX, 36);
//        mRbForenoonSingle.getPaint().setFakeBoldText(true);
//        //默认待取件
        mTvDqj.setTextColor(Color.parseColor("#fe8cab"));
        mTvPickUpNumber.setTextColor(Color.parseColor("#fe8cab"));
//        //默认宿舍楼号正序
//        mLlOrderList.setBackgroundResource(R.drawable.bg_time_color);
//        mTvDormitoryBuilding.setTextColor(Color.parseColor("#ffffff"));
//        mIvDormitoryBuilding.setImageResource(R.mipmap.user_baiseshang);
        if (mTheOrderListAdapter == null) {
            mTheOrderListAdapter = new TheOrderListAdapter();
        }
        mLvTheOrderList.setAdapter(mTheOrderListAdapter);
    }

    private void initData() {
        getExpressLists(expressTimeType, status, "", "", "", "", "");
    }

    private void initEvent() {
        //初始化
        initPtrFrameLayout();

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
                        mTvDqj.setTextColor(Color.parseColor("#fe8cab"));
                        mTvPickUpNumber.setTextColor(Color.parseColor("#fe8cab"));
                        data.clear();
                        mTheOrderListAdapter.notifyDataSetChanged();
                        expressTimeType = "1";
                        getExpressLists(expressTimeType, status, "", "", "", "", "");
                        break;
                    case R.id.rb_afternoon_single://下午单
                        removeStagesTabState();
                        removesStagesTakeTabState();
                        removesStagesTimeTabState();
                        mRbAfternoonSingle.setTextColor(Color.parseColor("#ffffff"));
                        mRbAfternoonSingle.setTextSize(TypedValue.COMPLEX_UNIT_PX, 36);
                        mRbAfternoonSingle.getPaint().setFakeBoldText(true);
                        mTvDqj.setTextColor(Color.parseColor("#fe8cab"));
                        mTvPickUpNumber.setTextColor(Color.parseColor("#fe8cab"));
                        data.clear();
                        mTheOrderListAdapter.notifyDataSetChanged();
                        expressTimeType = "2";
                        getExpressLists(expressTimeType, status, "", "", "", "", "");
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
                data.clear();
                mTheOrderListAdapter.notifyDataSetChanged();
                status = "1";
                getExpressLists(expressTimeType, status, "", "", "", "", "");
            }
        });
        //未取件
        mLlUncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removesStagesTakeTabState();
                mTvUncheckNumber.setTextColor(Color.parseColor("#fe8cab"));
                mTvWqdj.setTextColor(Color.parseColor("#fe8cab"));
                data.clear();
                mTheOrderListAdapter.notifyDataSetChanged();
                status = "6";
                getExpressLists(expressTimeType, status, "", "", "", "", "");
            }
        });
        //已取件
        mLlHaveTaken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removesStagesTakeTabState();
                mTvHaveTakenNumber.setTextColor(Color.parseColor("#fe8cab"));
                mTvYqj.setTextColor(Color.parseColor("#fe8cab"));
                data.clear();
                mTheOrderListAdapter.notifyDataSetChanged();
                status = "2";
                getExpressLists(expressTimeType, status, "", "", "", "", "");
            }
        });
        //搜索按钮
        mIvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //当前时间
                long currentTime = Calendar.getInstance().getTimeInMillis();
                if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                    lastClickTime = currentTime;
                    /* 隐藏软键盘 */
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager.isActive()) {
                        inputMethodManager.hideSoftInputFromWindow(
                                view.getApplicationWindowToken(), 0);
                    }
                    startSeek();
                }
            }
        });
        //回车键监听
        mEtPleaseEnterNumber.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (KeyEvent.KEYCODE_ENTER == i && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    /* 隐藏软键盘 */
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager.isActive()) {
                        inputMethodManager.hideSoftInputFromWindow(
                                view.getApplicationWindowToken(), 0);
                    }
                    String s = mEtPleaseEnterNumber.getText().toString();
                    if (TextUtils.isEmpty(s)) {
                        ToastUtil.showToast(getActivity(),
                                "请输入姓名或取件编号", Toast.LENGTH_SHORT);
                        return false;
                    }
                    startSeek();
                    return true;
                }
                return false;
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
                data.clear();
                mTheOrderListAdapter.notifyDataSetChanged();
                if (orderBy1.equals("1")) {
                    orderBy1 = "2";
                    mIvDormitoryBuilding.setImageResource(R.mipmap.user_baiseshang);
                    getExpressLists(expressTimeType, status, "", "", "1", "", "");
                } else {
                    mIvDormitoryBuilding.setImageResource(R.mipmap.user_baisexia);
                    orderBy1 = "1";
                    getExpressLists(expressTimeType, status, "", "", "2", "", "");
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
                data.clear();
                mTheOrderListAdapter.notifyDataSetChanged();
                if (orderBy2.equals("1")) {
                    orderBy2 = "2";
                    mIvPaymentTime.setImageResource(R.mipmap.user_baiseshang);
                    getExpressLists(expressTimeType, status, "", "", "1", "", "");
                } else {
                    orderBy2 = "1";
                    mIvPaymentTime.setImageResource(R.mipmap.user_baisexia);
                    getExpressLists(expressTimeType, status, "", "", "2", "", "");
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
                data.clear();
                mTheOrderListAdapter.notifyDataSetChanged();
                if (orderBy3.equals("1")) {
                    orderBy3 = "2";
                    mIvStateTime.setImageResource(R.mipmap.user_baiseshang);
                    getExpressLists(expressTimeType, status, "", "", "1", "", "");
                } else {
                    orderBy3 = "1";
                    mIvStateTime.setImageResource(R.mipmap.user_baisexia);
                    getExpressLists(expressTimeType, status, "", "", "2", "", "");
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
//        final String[] items = {"圆通速递", "百世汇通", "申通快递", "中通快递", "韵达速递", "天天快递", "顺丰速运", "京东", "苏宁易购", "唯品会", "天猫", "快捷快递", "邮政", "全峰快递", "建华快递", "当当网"};
        CourierCompanyAdapter adapter = new CourierCompanyAdapter(list, getActivity());
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        alertBuilder.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mEtPleaseSelect.setText(list.get(i).express_name);
                chooseExpressCompanyAlertDialog.dismiss();
                if (!TextUtils.isEmpty(mEtPleaseSelect.getText().toString().trim())) {
                    data.clear();
                    mTheOrderListAdapter.notifyDataSetChanged();
                    getExpressLists(expressTimeType, status, list.get(i).id, "", "", "", "");
                }
            }
        });
        chooseExpressCompanyAlertDialog = alertBuilder.create();
        chooseExpressCompanyAlertDialog.show();
    }

    /**
     * 代取订单列表接口
     */
    private OrderListsRespones mOrderListsRespones;
    private String expressTimeType = "1";
    private String status = "1";

    private void getExpressLists(String expressTimeType, String status, String expressId, String keyword, String dormOrder, String payOrder, String statusOrder) {
        showLoadingDialog(getActivity(), false);
        OkHttpApi.getInstance().getExpressListsRespones(InitComm.init().userToken, InitComm.init().userRoleId, expressTimeType, status, expressId, "", "", keyword, dormOrder, payOrder, statusOrder, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                //刷新完成
                mPflFrameLayout.refreshComplete();
                closeLoadingDialog();
                ToastUtil.showToast(getActivity(), getString(R.string.netError), Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(String response, int id) {
                //刷新完成
                mPflFrameLayout.refreshComplete();
                closeLoadingDialog();
                LogUtil.e(TAG, response);
                Gson gson = new Gson();
                mOrderListsRespones = gson.fromJson(response, OrderListsRespones.class);
                if (mOrderListsRespones != null) {
                    if (mOrderListsRespones.code.equals("1")) {
                        //代取快递总数
                        if (!TextUtils.isEmpty(mOrderListsRespones.data.expressTypeCount)) {
                            mFragmentCallBack.setValue(mOrderListsRespones.data.expressTypeCount);
                        }
                        //待取件总数
                        if (!TextUtils.isEmpty(mOrderListsRespones.data.pickCount)) {
                            mTvPickUpNumber.setText(mOrderListsRespones.data.pickCount);
                        }
                        //未取到件总数
                        if (!TextUtils.isEmpty(mOrderListsRespones.data.notfindCount)) {
                            mTvUncheckNumber.setText(mOrderListsRespones.data.notfindCount);
                        }
                        //已取件总数
                        if (!TextUtils.isEmpty(mOrderListsRespones.data.distributeCount)) {
                            mTvHaveTakenNumber.setText(mOrderListsRespones.data.distributeCount);
                        }
                        if (mOrderListsRespones.data.orderData != null && mOrderListsRespones.data.orderData.size() > 0) {
                            for (OrderListsRespones.DataBean.OrderDataBean bean : mOrderListsRespones.data.orderData) {
                                data.add(bean);
                            }
                        }
                    } else {
                        ToastUtil.showToast(getActivity(), mOrderListsRespones.msg, Toast.LENGTH_SHORT);
                    }

                } else {
                    ToastUtil.showToast(getActivity(), "解析失败", Toast.LENGTH_SHORT);
                }
                mTheOrderListAdapter.notifyDataSetChanged();
            }
        }, TAG);
    }

    @Override
    public void refresh() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 初始化ptrFrameLayout
     */
    private void initPtrFrameLayout() {
        mPflFrameLayout.addPtrUIHandler(new JZBKLoadingHandler(mPflFrameLayout));
        mPflFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            //刷新开始
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //主线程中改变UI，设置数据
                        refreshData();
                    }
                }, 1000);
            }
        });
    }

    private void refreshData() {
        //加载的条目
        data.clear();
        getExpressLists(expressTimeType, status, "", "", "", "", "");
    }

    /**
     * 开始搜索
     */
    private String proCode;

    private void startSeek() {
        proCode = mEtPleaseEnterNumber.getText().toString().trim();
        if (proCode != null && !TextUtils.isEmpty(proCode)) {
            data.clear();
            mTheOrderListAdapter.notifyDataSetChanged();
            getExpressLists(expressTimeType, status, "", mEtPleaseEnterNumber.getText().toString().trim(), "", "", "");
        } else {
            ToastUtil.showToast(getActivity(), "请输入姓名或取件编号", Toast.LENGTH_SHORT);
        }
    }

    @OnClick(R.id.bt_gotop)
    public void onViewClicked() {
        mScrollView.smoothScrollTo(0, 0);
    }

    class TheOrderListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data != null ? data.size() : 0;
        }

        @Override
        public Object getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (view == null) {
                //导入布局
                view = LinearLayout.inflate(getContext(), R.layout.item_delivery_order, null);
                holder = new ViewHolder();
                holder.mTvTakeANumber = (TextView) view.findViewById(R.id.tv_take_a_number);
                holder.mTvCourierCompany = (TextView) view.findViewById(R.id.tv_courier_company);
                holder.mTvContactInformationName = (TextView) view.findViewById(R.id.tv_contact_information_name);
                holder.mTvContactInformationPhone = (TextView) view.findViewById(R.id.tv_contact_information_phone);
                holder.mTvAddressInformation = (TextView) view.findViewById(R.id.tv_address_information);
                holder.mTvDormitoryBuilding = (TextView) view.findViewById(R.id.tv_dormitory_building);
                holder.mTvTheDormitory = (TextView) view.findViewById(R.id.tv_the_dormitory);
                holder.mTvPaymentTime = (TextView) view.findViewById(R.id.tv_payment_time);
                holder.mTvTheDeliveryTime = (TextView) view.findViewById(R.id.tv_the_delivery_time);
                holder.mTvNoteInfo = (TextView) view.findViewById(R.id.tv_note_info);
                holder.mLlTheOrderStatus = (AutoLinearLayout) view.findViewById(R.id.ll_the_order_status);
                holder.mTvTheOrderStatus = (TextView) view.findViewById(R.id.tv_the_order_status);
                holder.mBtnTake = (Button) view.findViewById(R.id.btn_take);
                holder.mBtnNotToTake = (Button) view.findViewById(R.id.btn_not_to_take);
                holder.mRlTakeLayout = (AutoRelativeLayout) view.findViewById(R.id.rl_take_layout);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            setItem(holder, i);
            return view;
        }
    }

    private void setItem(ViewHolder holder, final int position) {
        //加载数据
        if (data != null && data.size() > 0) {
            if (!TextUtils.isEmpty(data.get(position).pickCode)) {
                holder.mTvTakeANumber.setText(data.get(position).pickCode);
            }
            if (!TextUtils.isEmpty(data.get(position).expressName)) {
                holder.mTvCourierCompany.setText(data.get(position).expressName);
            }
            if (!TextUtils.isEmpty(data.get(position).contactsName)) {
                holder.mTvContactInformationName.setText(data.get(position).contactsName);
            }
            if (!TextUtils.isEmpty(data.get(position).contactsPhone)) {
                holder.mTvContactInformationPhone.setText(data.get(position).contactsPhone);
                holder.mTvContactInformationPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        call(data.get(position).contactsPhone);
                    }
                });
            }
            if (!TextUtils.isEmpty(data.get(position).schoolName)) {
                holder.mTvAddressInformation.setText(data.get(position).schoolName);
            }
            if (!TextUtils.isEmpty(data.get(position).buildingName)) {
                holder.mTvDormitoryBuilding.setText(data.get(position).buildingName);
            }
            if (!TextUtils.isEmpty(data.get(position).dromNum)) {
                holder.mTvTheDormitory.setText(data.get(position).dromNum);
            }
            if (!TextUtils.isEmpty(data.get(position).createTime)) {
                holder.mTvPaymentTime.setText(data.get(position).createTime);
            }
            if (!TextUtils.isEmpty(data.get(position).expressTimeType)) {
                if (data.get(position).expressTimeType.equals("1")) {
                    holder.mTvTheDeliveryTime.setText("中午");
                } else {
                    holder.mTvTheDeliveryTime.setText("晚上");
                }
            }
            if (!TextUtils.isEmpty(data.get(position).remark)) {
                holder.mTvNoteInfo.setText(data.get(position).remark);
            }
            if (!TextUtils.isEmpty(data.get(position).status)) {
                if (data.get(position).status.equals("1")) {
                    holder.mLlTheOrderStatus.setVisibility(View.GONE);
                    holder.mRlTakeLayout.setVisibility(View.VISIBLE);
                    holder.mBtnNotToTake.setText("未取到件");
                    //取件按钮
                    holder.mBtnTake.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getEditOrder(data.get(position).orderId, "2");
                        }
                    });
                    //未取到件按钮
                    holder.mBtnNotToTake.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getEditOrder(data.get(position).orderId, "6");
                        }
                    });
                } else if (data.get(position).status.equals("2")) {
                    holder.mLlTheOrderStatus.setVisibility(View.VISIBLE);
                    holder.mRlTakeLayout.setVisibility(View.GONE);
                    holder.mTvTheOrderStatus.setText("待揽收");
                } else if (data.get(position).status.equals("6")) {
                    holder.mLlTheOrderStatus.setVisibility(View.GONE);
                    holder.mRlTakeLayout.setVisibility(View.VISIBLE);
                    holder.mBtnTake.setVisibility(View.GONE);
                    holder.mBtnNotToTake.setText("关闭订单");
                    holder.mBtnNotToTake.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getCloseOrder(data.get(position).orderId);
                        }
                    });
                }
            }
        }
    }

    private static class ViewHolder {
        TextView mTvTakeANumber;//取件编号
        TextView mTvCourierCompany;//快递公司
        TextView mTvContactInformationName;//联系人姓名
        TextView mTvContactInformationPhone;//联系人手机号
        TextView mTvAddressInformation;//地址信息
        TextView mTvDormitoryBuilding;//楼号
        TextView mTvTheDormitory;//楼层
        TextView mTvPaymentTime;//支付日期
        TextView mTvTheDeliveryTime;//派送时间
        TextView mTvNoteInfo;//备注
        AutoLinearLayout mLlTheOrderStatus;//订单状态布局
        TextView mTvTheOrderStatus;//订单状态
        Button mBtnTake;//取件
        Button mBtnNotToTake;//未取到件
        AutoRelativeLayout mRlTakeLayout;//状态按钮布局
    }

    /**
     * 将Fragment与Activity关联，也就是谁需要Fragment的回调参数，谁实现该接口
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        mFragmentCallBack = (FragmentCallBack) context;
        super.onAttach(context);
    }

    /**
     * 代取订单更改状态接口
     */
    private EditStatusRespones mEditStatusRespones;

    private void getEditOrder(String orderId, String status1) {
        DialogUtil.showLoadingDialog(getContext(), false);
        OkHttpApi.getInstance().getEditStatusRespones(InitComm.init().userToken, orderId, status1, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(getActivity(), getString(R.string.netError), Toast.LENGTH_SHORT);
                closeLoadingDialog();
            }

            @Override
            public void onResponse(String response, int id) {
                closeLoadingDialog();
                LogUtil.e(TAG, response);
                Gson gson = new Gson();
                mEditStatusRespones = gson.fromJson(response, EditStatusRespones.class);
                if (mEditStatusRespones != null) {
                    if (mEditStatusRespones.code.equals("1")) {
                        data.clear();
                        mTheOrderListAdapter.notifyDataSetChanged();
                        initData();
                    } else {
                        ToastUtil.showToast(getActivity(), mEditStatusRespones.msg, Toast.LENGTH_SHORT);
                    }
                } else {
                    ToastUtil.showToast(getActivity(), "解析失败", Toast.LENGTH_SHORT);
                }
            }
        }, TAG);
    }

    /**
     * 调用拨号界面
     */
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    /**
     * 关闭订单接口
     */
    private void getCloseOrder(String orderId){
        showLoadingDialog(getActivity(),false);
        OkHttpApi.getInstance().getCloseOrderRespones(InitComm.init().userToken, orderId, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                closeLoadingDialog();
                ToastUtil.showToast(getActivity(),getString(R.string.netError),Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(String response, int id) {
                closeLoadingDialog();
                Gson gson = new Gson();
                LogUtil.e(TAG,response);

            }
        },TAG);
    }
}