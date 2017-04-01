package com.honeyBaby.ninegrid;

import java.util.List;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by mrsimple on 1/1/17.
 */
public interface ItemImageClickListener<T> {
    void onItemImageClick(Context context, ImageView imageView, int index, List<T> list);
}