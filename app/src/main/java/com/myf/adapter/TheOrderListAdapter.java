package com.myf.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.myf.model.OrderListsRespones;
import com.xghls.R;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TheOrderListAdapter extends BaseAdapter {
    public static final String TAG = TheOrderListAdapter.class.getSimpleName();
    private List<OrderListsRespones.DataBean> data;
    private LayoutInflater mLayoutInflater;

    public TheOrderListAdapter(Context context, List<OrderListsRespones.DataBean> data) {
        //实例化布局导入器
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //传递数据源
        if (data != null) {
            this.data = data;
        } else {
            this.data = new ArrayList<>();
        }
    }

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
            view = mLayoutInflater.inflate(R.layout.item_delivery_order, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
            AutoUtils.autoSize(view);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //加载数据
        if (data != null && data.size() > 0){
            if (!TextUtils.isEmpty(data.get(i).pickCode)){
                holder.mTvTakeANumber.setText(data.get(i).pickCode);
            }
            if (!TextUtils.isEmpty(data.get(i).expressName)){
                holder.mTvCourierCompany.setText(data.get(i).expressName);
            }
            if (!TextUtils.isEmpty(data.get(i).contactsName)){
                holder.mTvContactInformationName.setText(data.get(i).contactsName);
            }
            if (!TextUtils.isEmpty(data.get(i).contactsPhone)){
                holder.mTvContactInformationPhone.setText(data.get(i).contactsPhone);
            }
            if (!TextUtils.isEmpty(data.get(i).schoolName)){
                holder.mTvAddressInformation.setText(data.get(i).schoolName);
            }
            if (!TextUtils.isEmpty(data.get(i).buildingName)){
                holder.mTvDormitoryBuilding.setText(data.get(i).buildingName);
            }
            if (!TextUtils.isEmpty(data.get(i).dromNum)){
                holder.mTvTheDormitory.setText(data.get(i).dromNum);
            }
            if (!TextUtils.isEmpty(data.get(i).createTime)){
                holder.mTvPaymentTime.setText(data.get(i).createTime);
            }
            if (!TextUtils.isEmpty(data.get(i).expressTimeType)){
                if (data.get(i).expressTimeType.equals("1")){
                    holder.mTvTheDeliveryTime.setText("中午");
                }else {
                    holder.mTvTheDeliveryTime.setText("晚上");
                }
            }
            if (!TextUtils.isEmpty(data.get(i).remark)){
                holder.mTvNoteInfo.setText(data.get(i).remark);
            }
            if (!TextUtils.isEmpty(data.get(i).status)){
                if (data.get(i).status.equals("1")){
                    holder.mLlTheOrderStatus.setVisibility(View.GONE);
                    holder.mRlTakeLayout.setVisibility(View.VISIBLE);
                    holder.mBtnNotToTake.setText("未取到件");
                }else if (data.get(i).status.equals("2")){
                    holder.mLlTheOrderStatus.setVisibility(View.VISIBLE);
                    holder.mRlTakeLayout.setVisibility(View.GONE);
                    holder.mTvTheOrderStatus.setText("待揽收");
                }else if (data.get(i).status.equals("6")){
                    holder.mLlTheOrderStatus.setVisibility(View.GONE);
                    holder.mRlTakeLayout.setVisibility(View.VISIBLE);
                    holder.mBtnTake.setVisibility(View.GONE);
                    holder.mBtnNotToTake.setText("关闭订单");
                }
            }
        }
        return view;
    }

    public static class ViewHolder {
        @Bind(R.id.tv_take_a_number)
        TextView mTvTakeANumber;//取件编号
        @Bind(R.id.tv_courier_company)
        TextView mTvCourierCompany;//快递公司
        @Bind(R.id.tv_contact_information_name)
        TextView mTvContactInformationName;//联系人姓名
        @Bind(R.id.tv_contact_information_phone)
        TextView mTvContactInformationPhone;//联系人手机号
        @Bind(R.id.tv_address_information)
        TextView mTvAddressInformation;//地址信息
        @Bind(R.id.tv_dormitory_building)
        TextView mTvDormitoryBuilding;//楼号
        @Bind(R.id.tv_the_dormitory)
        TextView mTvTheDormitory;//楼层
        @Bind(R.id.tv_payment_time)
        TextView mTvPaymentTime;//支付日期
        @Bind(R.id.tv_the_delivery_time)
        TextView mTvTheDeliveryTime;//派送时间
        @Bind(R.id.tv_note_info)
        TextView mTvNoteInfo;//备注
        @Bind(R.id.ll_the_order_status)
        AutoLinearLayout mLlTheOrderStatus;//订单状态布局
        @Bind(R.id.tv_the_order_status)
        TextView mTvTheOrderStatus;//订单状态
        @Bind(R.id.btn_take)
        Button mBtnTake;//取件
        @Bind(R.id.btn_not_to_take)
        Button mBtnNotToTake;//未取到件
        @Bind(R.id.rl_take_layout)
        AutoRelativeLayout mRlTakeLayout;//状态按钮布局

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
