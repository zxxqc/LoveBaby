package com.hb.lovebaby.adapter;

import android.app.Activity;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hb.lovebaby.R;

public class BloodAdapter extends BaseAdapter{
private String arrs[];
private LayoutInflater inflater;
private Handler mHandler;
private int target=-1;
	public BloodAdapter(String arrs[], Activity mContext,Handler sHandler,int tag) {
	super();
	this.arrs = arrs;
	inflater = LayoutInflater.from(mContext);
	mHandler=sHandler;
	target=tag;
}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrs.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arrs[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolderCommtents viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.adapter_blood, null);
			viewHolder = new ViewHolderCommtents();
			viewHolder.item_tv = (TextView) convertView
					.findViewById(R.id.item_tv);
			viewHolder.item_ll=(LinearLayout)convertView.findViewById(R.id.item_ll);
			viewHolder.choosed_icon=(ImageButton)convertView.findViewById(R.id.choosed_icon);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolderCommtents) convertView.getTag();
		}
		viewHolder.item_tv.setText(arrs[position]);
		viewHolder.choosed_icon.setVisibility(View.INVISIBLE);
		viewHolder.item_ll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				target=position;
				mHandler.sendEmptyMessage(position);
				notifyDataSetChanged();
			}
		});
		if (target==position) {
			viewHolder.choosed_icon.setVisibility(View.VISIBLE);
		}else{
			viewHolder.choosed_icon.setVisibility(View.INVISIBLE);

		}
		return convertView;
	}
 
	class ViewHolderCommtents {
		public TextView item_tv;
		public LinearLayout item_ll;
		public ImageButton choosed_icon;
	}
}
