package com.myf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.myf.model.LoginRespones;
import com.xghls.R;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TheOrderListAdapter extends BaseAdapter {
    public static final String TAG = TheOrderListAdapter.class.getSimpleName();
    private List<LoginRespones> data;
    private LayoutInflater mLayoutInflater;

    public TheOrderListAdapter(Context context, List<LoginRespones> data) {
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
        @Bind(R.id.tv_the_order_status)
        TextView mTvTheOrderStatus;
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
