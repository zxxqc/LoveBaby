package com.hb.lovebaby.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.TextView;

import com.hb.lovebaby.R;
import com.hb.lovebaby.util.CommonUtils;
import com.hb.lovebaby.util.MyUtil;
import com.hb.lovebaby.view.VideoItem;
import com.hb.lovebaby.view.VideoItem.onItemClickListener;
import com.honeyBaby.temp.VideoModel;

public class VideoSelectorAdapter extends MBaseAdapter<VideoModel> {

	private int itemWidth;
	private int horizentalNum = 3;
	private LayoutParams itemLayoutParams;
	private onItemClickListener mCallback;
	private OnClickListener cameraListener;

	private VideoSelectorAdapter(Context context, ArrayList<VideoModel> models) {
		super(context, models);
	}

	public VideoSelectorAdapter(Context context, ArrayList<VideoModel> models, int screenWidth,
			onItemClickListener mCallback, OnClickListener cameraListener) {
		this(context, models);
		setItemWidth(screenWidth);
		this.mCallback = mCallback;
		this.cameraListener = cameraListener;
	}

	/** ����ÿһ��Item�Ŀ�� */
	public void setItemWidth(int screenWidth) {
		int horizentalSpace = context.getResources().getDimensionPixelSize(R.dimen.sticky_item_horizontalSpacing);
		this.itemWidth = (screenWidth - (horizentalSpace * (horizentalNum - 1))) / horizentalNum;
		this.itemLayoutParams = new LayoutParams(itemWidth, itemWidth);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		VideoItem item = null;
		TextView tvCamera = null;
		if (position == 0 && CommonUtils.isNull(models.get(position).getPath())) { // ��ʱ��һ��ʱ����ʾ��ť
			if (convertView == null || !(convertView instanceof TextView)) {
				tvCamera = (TextView) LayoutInflater.from(context).inflate(R.layout.view_camera, null);
				tvCamera.setHeight(itemWidth);
				tvCamera.setWidth(itemWidth);
				convertView = tvCamera;
			}
			convertView.setOnClickListener(cameraListener);
		} else { // ��ʾͼƬ
			if (convertView == null || !(convertView instanceof VideoItem)) {
				item = new VideoItem(context);
				item.setLayoutParams(itemLayoutParams);
				convertView = item;
			} else {
				item = (VideoItem) convertView;
			}
			Log.e("-----------", "=============");
			item.setTime(MyUtil.longToMinute(models.get(position).getDuration()));
			item.setVideoDrawable(models.get(position));
			item.setOnClickListener(mCallback, models.get(position));
		}
		return convertView;
	}
}
