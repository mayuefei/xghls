package com.myf.util;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.youth.banner.loader.ImageLoader;

/**
 *banner图片加载器
 */
public class GlideImageLoader implements ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
//        Picasso.with(context).load((String)path)
//                .error(R.mipmap.loaderror)
        Picasso.with(context).load((Integer) path)
                .into(imageView);
    }
}
