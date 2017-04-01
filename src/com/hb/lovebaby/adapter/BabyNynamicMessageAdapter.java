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
import com.hb.lovebaby.modle.BabyDynamicMessage;

public class BabyNynamicMessageAdapter  extends ArrayAdapter<BabyDynamicMessage>{
	private LayoutInflater inflater;
	
	private ImageLoader mImageLoader ;

	public BabyNynamicMessageAdapter(Context context, List<BabyDynamicMessage> objects) {
		super(context, 0, objects);
		inflater = LayoutInflater.from(context);
		this.mImageLoader = new ImageLoader(context, 40);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) {
			view = inflater.inflate(R.layout.adapter_baby_nynamic_message, parent, false);
		} else {
			view = convertView;
		}
		
		BabyNynamicMessage holder = (BabyNynamicMessage) view.getTag();
		if (holder == null) {
			holder = new BabyNynamicMessage();
			holder.headImg = (ImageView) view.findViewById(R.id.img_baby_dynamic_msg_head_img);
			holder.from = (TextView) view.findViewById(R.id.tv_baby_dynamic_from);
			holder.content = (TextView) view.findViewById(R.id.tv_baby_dynamic_content);
			holder.nMinAgo = (TextView) view.findViewById(R.id.tv_baby_dynamic_min_ago);
			holder.foodImg = (ImageView) view.findViewById(R.id.img_baby_dynamic_tail_img);
			view.setTag(holder);
		}
		
		BabyDynamicMessage msg = getItem(position);
		mImageLoader.DisplayImage(msg.headImgUrl, holder.headImg , false);
		holder.from.setText(msg.from);
		holder.content.setText(msg.content);
		holder.nMinAgo.setText(msg.nMinAgo);
		mImageLoader.DisplayImage(msg.headImgUrl, holder.foodImg , false);
		return view;
	}
}

class BabyNynamicMessage {
	ImageView headImg;
	TextView from;
	TextView content;
	TextView nMinAgo;
	ImageView foodImg;
}
