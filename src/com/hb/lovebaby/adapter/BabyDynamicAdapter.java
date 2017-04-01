package com.hb.lovebaby.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.hb.lovebaby.R;
import com.hb.lovebaby.modle.BabyDynamic;

public class BabyDynamicAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<BabyDynamic> lists;

	public BabyDynamicAdapter(List<BabyDynamic> lists, Context context) {
		super();
		inflater = LayoutInflater.from(context);
		this.lists = lists;
	}

	@Override
	public int getCount() {
		if (null != lists) {
			return lists.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolderBabyDynamic viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.adapter_baby_dynamic, null);
			viewHolder = new ViewHolderBabyDynamic();
			viewHolder.imgHead = (ImageView) convertView
					.findViewById(R.id.img_baby_dynamic_baby_head_image);
			viewHolder.imgHeadGirlOrBoy = (ImageView) convertView
					.findViewById(R.id.img_baby_dynamic_baby_head_girl_or_boy);
			viewHolder.tvName = (TextView) convertView
					.findViewById(R.id.tv_adatper_baby_dynamic_name);
			viewHolder.imgGirlOrBoy = (ImageView) convertView
					.findViewById(R.id.img_baby_dynamic_baby_girl_or_boy);
			viewHolder.tbtnIsOpen = (ToggleButton) convertView
					.findViewById(R.id.tbtn_adatper_baby_dynamic_is_open);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolderBabyDynamic) convertView.getTag();
		}
		viewHolder.imgHead
				.setImageResource(R.drawable.adatper_babyt_dtnamic_head_1);
		viewHolder.tvName.setText(lists.get(position).getName());
		if (lists.get(position).getSex().equals("boy")) {
			viewHolder.imgHeadGirlOrBoy
					.setImageResource(R.drawable.adapter_baby_dynamic_head_boy);
			viewHolder.imgGirlOrBoy
					.setImageResource(R.drawable.adapter_baby_dynamic_boy);
		} else {
			viewHolder.imgHeadGirlOrBoy
					.setImageResource(R.drawable.adapter_baby_dynamic_head_girl);
			viewHolder.imgGirlOrBoy
					.setImageResource(R.drawable.adapter_baby_dynamic_girl);
		}
		if (lists.get(position).getIsOpen().equals("open")) {
			viewHolder.tbtnIsOpen.setChecked(true);
		} else {
			viewHolder.tbtnIsOpen.setChecked(false);
		}

		return convertView;
	}

}

class ViewHolderBabyDynamic {
	public ImageView imgHead;
	public ImageView imgHeadGirlOrBoy;
	public TextView tvName;
	public ImageView imgGirlOrBoy;
	public ToggleButton tbtnIsOpen;

}
