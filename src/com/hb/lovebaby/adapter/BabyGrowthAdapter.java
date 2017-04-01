package com.hb.lovebaby.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hb.lovebaby.R;
import com.hb.lovebaby.modle.BabyDynamic;

public class BabyGrowthAdapter extends BaseAdapter{
	private ArrayList<BabyDynamic> babys;
	private LayoutInflater inflater;
	private Context sContext;
		public BabyGrowthAdapter(ArrayList<BabyDynamic> babys,Context mContext) {
		super();
		this.babys = babys;
		inflater = LayoutInflater.from(mContext);
		this.sContext=mContext;
	}
public void refresh(ArrayList<BabyDynamic> babys){
	this.babys=babys;
	notifyDataSetChanged();
}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return babys.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return babys.get(arg0);
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
				convertView = inflater.inflate(R.layout.adapter_babygrowth, null);
				viewHolder = new ViewHolderCommtents();
				viewHolder.height_tv = (TextView) convertView
						.findViewById(R.id.height_tv);
				viewHolder.weight_tv=(TextView)convertView.findViewById(R.id.weight_tv);
				viewHolder.date_tv=(TextView)convertView.findViewById(R.id.date_tv);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolderCommtents) convertView.getTag();
			}
			Spanned height=Html.fromHtml(sContext.getResources().getString(R.string.height_cap)+"<font color="+sContext.getResources().getColor(R.color.green_font)+">"+
			babys.get(position).getHeight()+"</font>"+sContext.getResources().getString(R.string.cm));
			viewHolder.height_tv.setText(height);
			Spanned weight=Html.fromHtml(sContext.getResources().getString(R.string.weight_cap)+"<font color="+sContext.getResources().getColor(R.color.green_font)+">"+
					babys.get(position).getWeight()+"</font>"+sContext.getResources().getString(R.string.kg));
					viewHolder.weight_tv.setText(weight);
			String time=babys.get(position).getRecordDatetime();
			viewHolder.date_tv.setText(time);
			return convertView;
		}
	 
		class ViewHolderCommtents {
			public TextView height_tv;
			public TextView weight_tv;
			public TextView date_tv;
		}
	}
