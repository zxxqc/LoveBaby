package com.hb.lovebaby.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hb.lovebaby.R;
import com.hb.lovebaby.modle.AlbumItem;
import com.hb.lovebaby.modle.AlbumRealItem;
import com.hb.lovebaby.modle.AlbumSeparator;
import com.squareup.picasso.Picasso;

public class AlbumListAdapter extends ArrayAdapter<AlbumItem> {
	
	private LayoutInflater layoutInflater;
	
	public AlbumListAdapter(Context context, int textViewResourceId, List<AlbumItem> objects) {
		super(context, textViewResourceId, objects);
		layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	// ��д getItemViewType �� getViewTypeCount�������� view
	@Override
	public int getItemViewType(int position) {
		AlbumItem it = getItem(position);
		if (it instanceof AlbumRealItem) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		AlbumItem item = getItem(position);
		if (item instanceof AlbumRealItem) {
			if (shouldCreateView(convertView, item)) {
				view = layoutInflater.inflate(R.layout.album_list_real_item, parent, false);
			}
			
			AlbumRealItem realItem = (AlbumRealItem) item;
			
			// title
			TextView title = (TextView) view.findViewById(R.id.album_item_title);
			title.setText(realItem.getName());
			title.setTextColor(getContext().getResources().getColor(realItem.getTitleColor()));
			
			// thumbnail
			ImageView imgView = (ImageView) view.findViewById(R.id.album_thumbnail_imageview);
			Picasso.with(getContext())
				.load(R.drawable.ps1_1)
				.into(imgView);
			
			// desc
			TextView desc = (TextView) view.findViewById(R.id.albumn_item_desc);
			if (realItem.getDesc() == null || realItem.getDesc().isEmpty()) {
				desc.setVisibility(View.GONE);
			} else {
				desc.setText(realItem.getDesc());
				desc.setVisibility(View.VISIBLE);
			}
			
			// count str
			TextView countStr = (TextView) view.findViewById(R.id.albumn_item_photo_count);
			if (realItem.getCountStr() == null || realItem.getCountStr().isEmpty()) {
				countStr.setVisibility(View.GONE);
			} else {
				countStr.setText(realItem.getCountStr());
				countStr.setVisibility(View.VISIBLE);
			}
			
		} else {
			if (shouldCreateView(convertView, item)) {
				view = layoutInflater.inflate(R.layout.album_list_separator, parent, false);
			}
			AlbumSeparator separator = (AlbumSeparator) item;
			TextView tv = (TextView) view.findViewById(R.id.album_sperator_title);
			tv.setText(separator.getName());
		}

		return view;
	}
	
	private boolean shouldCreateView(View convertView, AlbumItem item) {
		if (convertView == null) {
			return true;
		}
		// view ���������� item ���Ͳ�ƥ��ʱ�������µ� view
		ImageView imgView = (ImageView) convertView.findViewById(R.id.album_thumbnail_imageview);
		return imgView != null ^ (item instanceof AlbumRealItem);
	}
}
