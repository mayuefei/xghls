package com.myf.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;

import com.myf.view.MyProgressDialog;
import com.xghls.R;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;


/**
 * Created by myf on 2017/8/8.
 */

public class BaseFragment extends Fragment {
    public int topH; // 状态栏高度
    private MyProgressDialog dialog;//加载中

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topH = getTitleTop();
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    public int getTitleTop() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = getResources().getDimensionPixelSize(x);// 状态栏高度
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }

    /**
     * 页面跳转
     *
     * @param targetActivity 目标Activity.class
     */
    protected void intent2Activity(Class<? extends Activity> targetActivity) {
        startActivity(new Intent(getActivity(), targetActivity));
    }

    /**
     * 销毁时释放资源
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void showLoadingDialog(Context context, boolean isBackDismiss) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = new MyProgressDialog(context, R.style.loading_dialog, isBackDismiss);
        dialog.show();
    }

    public void closeLoadingDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
    /**
     * 禁止EditText输入空格、特殊字符
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpaChat(EditText editText) {
        InputFilter filter_space = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" "))
                    return "";
                else
                    return null;
            }
        };
        InputFilter filter_speChat = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                String speChat = "[`~!@#_$%^&*()+=|{}':;',\\[\\].<>~！@#￥%……&*（）— +|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(charSequence.toString());
                if (matcher.find()) return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter_space, filter_speChat});
    }
    public FragmentCallBack mFragmentCallBack;
    /**
     * Activity取Fragment所传递的值时调用的回调接口
     */
    public interface FragmentCallBack{
        /**
         * 传值到Activity中
         * @param expressTypeCount
         */
        void setValue(String expressTypeCount);
    }
}
