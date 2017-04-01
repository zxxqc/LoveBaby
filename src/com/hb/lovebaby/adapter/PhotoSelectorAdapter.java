package com.hb.lovebaby.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.TextView;

import com.hb.lovebaby.R;
import com.hb.lovebaby.util.CommonUtils;
import com.hb.lovebaby.view.PhotoItem;
import com.hb.lovebaby.view.PhotoItem.onItemClickListener;
import com.hb.lovebaby.view.PhotoItem.onPhotoItemCheckedListener;
import com.honeyBaby.temp.PhotoModel;

public class PhotoSelectorAdapter extends MBaseAdapter<PhotoModel> {

	private int itemWidth;
	private int horizentalNum = 3;
	private onPhotoItemCheckedListener listener;
	private LayoutParams itemLayoutParams;
	private onItemClickListener mCallback;
	private OnClickListener cameraListener;

	private PhotoSelectorAdapter(Context context, ArrayList<PhotoModel> models) {
		super(context, models);
	}

	public PhotoSelectorAdapter(Context context, ArrayList<PhotoModel> models, int screenWidth,
			onPhotoItemCheckedListener listener, onItemClickListener mCallback, OnClickListener cameraListener) {
		this(context, models);
		setItemWidth(screenWidth);
		this.listener = listener;
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
		PhotoItem item = null;
		TextView tvCamera = null;
		if (position == 0 && CommonUtils.isNull(models.get(position).getOriginalPath())) { // ��ʱ��һ��ʱ����ʾ��ť
			if (convertView == null || !(convertView instanceof TextView)) {
				tvCamera = (TextView) LayoutInflater.from(context).inflate(R.layout.view_camera, null);
				tvCamera.setHeight(itemWidth);
				tvCamera.setWidth(itemWidth);
				convertView = tvCamera;
			}
			convertView.setOnClickListener(cameraListener);
		} else { // ��ʾͼƬ
			if (convertView == null || !(convertView instanceof PhotoItem)) {
				item = new PhotoItem(context, listener);
				item.setLayoutParams(itemLayoutParams);
				convertView = item;
			} else {
				item = (PhotoItem) convertView;
			}
			item.setImageDrawable(models.get(position));
			item.setSelected(models.get(position).isChecked());
			item.setOnClickListener(mCallback, position);
		}
		return convertView;
	}
	
	public void updateSelectedList(ArrayList<PhotoModel> selected){
		if (selected == null || this.models == null) {
			return;
		}
		for (PhotoModel photoModel : this.models) {
			boolean isCheckd = false;
			String photoPath = photoModel.getOriginalPath();
			for (PhotoModel selectedPhotoModel : selected) {
				String selectedPhotoPath = selectedPhotoModel.getOriginalPath();
				if (photoPath == null || selectedPhotoPath == null) {
					continue;
				}
				if (photoPath.equals(selectedPhotoPath)) {
					isCheckd = true;
					break;
				}
			}
			photoModel.setChecked(isCheckd);
		}
		this.notifyDataSetChanged();
	}
}
