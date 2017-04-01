package com.hb.lovebaby.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hb.lovebaby.R;
import com.hb.lovebaby.imageload.ImageLoader;
import com.hb.lovebaby.modle.SystemMessage;

public class SystemMessageAdapter  extends ArrayAdapter<SystemMessage>{
	private LayoutInflater inflater;
	
	private ImageLoader mImageLoader ;

	public SystemMessageAdapter(Context context, List<SystemMessage> objects) {
		super(context, 0, objects);
		inflater = LayoutInflater.from(context);
		this.mImageLoader = new ImageLoader(context, 40);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) {
			view = inflater.inflate(R.layout.adapter_system_message, parent, false);
		} else {
			view = convertView;
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.headImg = (ImageView) view.findViewById(R.id.msg_head_img);
			holder.from = (TextView) view.findViewById(R.id.from);
			holder.content = (TextView) view.findViewById(R.id.content);
			holder.nMinAgo = (TextView) view.findViewById(R.id.n_min_ago);
			view.setTag(holder);
		}
		
		SystemMessage msg = getItem(position);
		mImageLoader.DisplayImage(msg.headImgUrl, holder.headImg , false);
		holder.from.setText(msg.from);
		holder.content.setText(msg.content);
		holder.nMinAgo.setText(msg.nMinAgo);
		
		return view;
	}
}

class ViewHolder {
	ImageView headImg;
	TextView from;
	TextView content;
	TextView nMinAgo;
}
