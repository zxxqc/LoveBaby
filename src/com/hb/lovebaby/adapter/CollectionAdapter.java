package com.hb.lovebaby.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hb.lovebaby.R;

public class CollectionAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<Integer> myCollections;

	public CollectionAdapter(List<Integer> myCollections, Context context) {
		super();
		inflater = LayoutInflater.from(context);
		this.myCollections = myCollections;
	}

	@Override
	public int getCount() {
		if (null != myCollections) {
			return myCollections.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return myCollections.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolderCommtents viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.adapter_collection, null);
			viewHolder = new ViewHolderCommtents();
			viewHolder.img = (ImageView) convertView
					.findViewById(R.id.img_collection_adapter);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolderCommtents) convertView.getTag();
		}
		viewHolder.img.setImageResource(myCollections.get(position));

		return convertView;
	}

}

class ViewHolderCommtents {
	public ImageView img;

}
