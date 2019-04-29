package com.myf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myf.model.UserInfoRespones;
import com.xghls.R;

import java.util.ArrayList;

public class CourierCompanyAdapter extends BaseAdapter {
    private Context mContext;
//    private ArrayList<Integer> list;
    private ArrayList<UserInfoRespones.DataBean.ExpressDataBean> mExpressDataBeans;
    LayoutInflater mLayoutInflater;

    public CourierCompanyAdapter (ArrayList<UserInfoRespones.DataBean.ExpressDataBean> mExpressDataBeans,Context context){
        this.mExpressDataBeans = mExpressDataBeans;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mExpressDataBeans.size();
    }

    @Override
    public Object getItem(int i) {
        if (i == getCount() || mExpressDataBeans == null){
            return null;
        }
        return mExpressDataBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            view = mLayoutInflater.inflate(R.layout.item_courier_company_list,null);
            holder.mImageView = view.findViewById(R.id.iv_courier_company_picture);
            holder.mTextView = view.findViewById(R.id.tv_courier_company_picture);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
//        holder.mImageView.setImageResource((Integer) getItem(i));
        holder.mTextView.setText(mExpressDataBeans.get(i).express_name);
        return view;
    }

    public static class ViewHolder {
        ImageView mImageView;
        TextView mTextView;
    }
}
