package com.hb.lovebaby.adapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.hb.lovebaby.R;

public class VideoNewCutAdapter extends RecyclerView.Adapter<VideoNewCutAdapter.ViewHolder> {
	/** 数据�?*/
	private ArrayList<String> list;
	/** 点击事件 */
	private MyItemClickListener onClickListener;

	public VideoNewCutAdapter(ArrayList<String> list) {
		this.list = list;
	}

	public MyItemClickListener getOnClickListener() {
		return onClickListener;
	}

	public void setOnClickListener(MyItemClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position) {
		Bitmap bitmap = getLoacalBitmap(list.get(position));
		if (bitmap != null) {
			viewHolder.img.setScaleType(ScaleType.CENTER_CROP);
			viewHolder.img.setImageBitmap(bitmap);
		}
	}

	public Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
		// 创建�?��View，简单起见直接使用系统提供的布局，就是一个TextView
		View view = View.inflate(viewGroup.getContext(), R.layout.item_video_new_cut, null);
		// 创建�?��ViewHolder
		ViewHolder holder = new ViewHolder(view, onClickListener);

		return holder;
	}

	public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

		public ImageView img;
		private MyItemClickListener listener;

		public ViewHolder(View itemView, MyItemClickListener listener) {
			super(itemView);
			this.listener = listener;
			img = (ImageView) itemView.findViewById(R.id.item_video_new_img);
			img.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			if (listener != null) {
				listener.onItemClick(img, getPosition());
			}
		}
	}

	public interface MyItemClickListener {
		public void onItemClick(View view, int position);
	}
}
