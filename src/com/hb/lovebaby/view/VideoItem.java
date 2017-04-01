package com.hb.lovebaby.view;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hb.lovebaby.R;
import com.honeyBaby.temp.VideoModel;

public class VideoItem extends LinearLayout implements OnClickListener {

	private ImageView ivPhoto;
	private VideoModel video;
	private onItemClickListener l;
	private VideoModel clickVideo;
	
	private TextView timeTextView;


	public VideoItem(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.layout_videoitem, this, true);

		setOnClickListener(this);
		ivPhoto = (ImageView) findViewById(R.id.iv_photo_lpsi);
		timeTextView = (TextView) findViewById(R.id.iv_video_time);

	}

	
	/** ����·���µ�ͼƬ��Ӧ������ͼ */
	public void setVideoDrawable(final VideoModel video) {
		this.video = video;
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Bitmap bitmap = null;
				bitmap = ThumbnailUtils.createVideoThumbnail(video.getPath(), MediaStore.Images.Thumbnails.MICRO_KIND);
//				bitmap = ThumbnailUtils.extractThumbnail(bitmap, weight, height,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
				ivPhoto.setImageBitmap(bitmap);
			}
		}, new Random().nextInt(10));
	}


	@Override
	public void setSelected(boolean selected) {
		if (video == null) {
			return;
		}
		
	}

	public void setOnClickListener(onItemClickListener l, VideoModel video) {
		this.l = l;
		this.clickVideo = video;
	}

	


	/** ͼƬ����¼� */
	public interface onItemClickListener {
		public void onItemClick(VideoModel video);
	}
	
	public void setTime(String time){
		timeTextView.setText(time);
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (l != null)
			l.onItemClick(video);
	}


}
