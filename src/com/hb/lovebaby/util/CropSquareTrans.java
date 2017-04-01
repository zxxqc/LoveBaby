package com.hb.lovebaby.util;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

/**
 * Created by liangliang on 2016/7/25.
 */
public class CropSquareTrans implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        int size=Math.min(source.getWidth(),source.getHeight());
        int x=(source.getWidth()-size)/2;
        int y=(source.getHeight()-size)/2;
        Bitmap bitmap= source.createBitmap(source,x,y,size,size);
        if(bitmap!=source){
            source.recycle();//如果我们得到的bitmap不等于source,就把source回收!
        }
        return bitmap;
    }

    @Override
    public String key() {
        return "success()";//这里的返回，我们需要返回一个固定的字符串
    }
}
