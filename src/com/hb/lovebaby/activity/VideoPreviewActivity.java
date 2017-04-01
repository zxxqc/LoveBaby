package com.hb.lovebaby.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.VideoView;

import com.hb.lovebaby.R;
import com.hb.lovebaby.adapter.VideoNewCutAdapter;
import com.hb.lovebaby.adapter.VideoNewCutAdapter.MyItemClickListener;
import com.hb.lovebaby.util.DisplayUtil;
import com.hb.lovebaby.util.FUckTest;
import com.hb.lovebaby.util.MyUtil;
import com.hb.lovebaby.view.MyRecyclerView;
import com.hb.lovebaby.view.MyRecyclerView.OnItemScrollChangeListener;
import com.honeyBaby.temp.VideoModel;

public class VideoPreviewActivity extends BascActivity {
	private ImageView img_back;
	private ImageButton txt_enter;
	private VideoModel bean;
	private MyRecyclerView recyclerView;
	private ImageView img_bg;
	private ImageView img_left;
	private ImageView img_right;
	private TextView txt_time;
	private RelativeLayout relative;
	private RelativeLayout relative1;
	private VideoView videoView;
	private ArrayList<String> list;
	private VideoNewCutAdapter adapter;
	private int width;
	private String savePath;
	public static final int MIN_TIME = 5000;
	public static final int MAX_TIME = 60000;
	private float picture = 0;
	private float second_Z;
	private boolean isThread = false;
	private Button txt_left;
	private Button txt_right;

	private float DownX;

	private LayoutParams layoutParams_progress;
	private LayoutParams layoutParams_yin;
	private int width_progress = 0;
	private int Margin_progress = 0;
	private int width1_progress = 0;

	private int right_margin = 0;
	private int img_widthAll = 0;
	private int last_length = 0;
	private int left_lenth = 0;
	private int Scroll_lenth = 0;
	private String Ppath;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_videopreview);
		Ppath = MyUtil.getSDPath() + "/honeyBaby/Image/";
		findViews();
		initGetData();
		widgetListener();
		init();
	}


	
	protected void findViews() {
		img_back = (ImageView)findViewById(R.id.video_new_img_back);
		txt_enter = (ImageButton)findViewById(R.id.video_new_txt_enter);
		recyclerView = (MyRecyclerView)findViewById(R.id.recyclerview_horizontal);
		videoView = (VideoView) findViewById(R.id.video_new_cut_videoview);
		img_bg = (ImageView) findViewById(R.id.video_new_cut_img_bg);
		img_left = (ImageView) findViewById(R.id.video_new_cut_img_left);
		img_right = (ImageView) findViewById(R.id.video_new_cut_img_right);
		relative = (RelativeLayout) findViewById(R.id.video_new_cut_relative);
		txt_time = (TextView) findViewById(R.id.video_new_cut_txt_time);
		relative1 = (RelativeLayout) findViewById(R.id.video_new_cut_relative1);

		txt_left = (Button) findViewById(R.id.video_new_cut_txt_left);
		txt_right = (Button) findViewById(R.id.video_new_cut_txt_right);

		width = getWindowManager().getDefaultDisplay().getWidth();

		LayoutParams layoutParams = (LayoutParams) relative.getLayoutParams();
		layoutParams.width = width;
		layoutParams.height = width;
		relative.setLayoutParams(layoutParams);

		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		recyclerView.setLayoutManager(layoutManager);

		list = new ArrayList<String>();
		adapter = new VideoNewCutAdapter(list);
	}

	protected void initGetData() {
		if (getIntent().getExtras() != null) {
			bean = (VideoModel) getIntent().getExtras().getSerializable("video");
		}
	}

	protected void init() {
		File file = new File(Ppath);
		MyUtil.makeDir(file);
		
		recyclerView.setAdapter(adapter);

		videoView.setVideoPath(bean.getPath());
		videoView.requestFocus();

		picture = (float) MAX_TIME / (float) width;
		/** 1.66666 */
		second_Z = (float) MAX_TIME / 1000f / ((float) width / (float) DisplayUtil.dip2px(VideoPreviewActivity.this, 60));

		getBitmapsFromVideo(bean.getPath(), (int) bean.getDuration());
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {

				adapter.notifyItemInserted(msg.arg1);

				if (msg.arg1 == 0) {
					sendVideo(DisplayUtil.dip2px(VideoPreviewActivity.this, 60));
				}

			} else if (msg.what == 2) {

				img_widthAll = (int) (msg.arg1 * 1000 / picture);

				last_length = (int) (MIN_TIME / picture);

				if (img_widthAll < width) {
					right_margin = width - img_widthAll;
					LayoutParams layoutParams_right = (LayoutParams) img_right.getLayoutParams();
					layoutParams_right.width = width - img_widthAll;
					img_right.setLayoutParams(layoutParams_right);

					layoutParams_progress = (LayoutParams) relative1.getLayoutParams();
					layoutParams_progress.width = img_widthAll;
					layoutParams_progress.rightMargin = width - img_widthAll;
					relative1.setLayoutParams(layoutParams_progress);

					txt_time.setText(msg.arg1 + ".0 seconds");
				} else {
					img_widthAll = width;
					layoutParams_progress = (LayoutParams) relative1.getLayoutParams();
					layoutParams_progress.width = width;
					relative1.setLayoutParams(layoutParams_progress);

					txt_time.setText((MAX_TIME / 1000) + ".0 seconds");
				}
			} 
		};
	};

	public void getBitmapsFromVideo(final String dataPath, final int lenth) {
		new Thread(new Runnable() {

			@SuppressLint("NewApi")
			@Override
			public void run() {
				MediaMetadataRetriever retriever = new MediaMetadataRetriever();
				retriever.setDataSource(dataPath);
				int seconds = lenth / 1000;

				Message message = handler.obtainMessage();
				message.what = 2;
				message.arg1 = seconds;
				handler.sendMessage(message);
				Bitmap bitmap;
				int index = 0;
				for (float f = second_Z; f <= (float) seconds; f += second_Z) {

					if (isThread) {
						return;
					}
					bitmap = retriever.getFrameAtTime((long) (f * 1000 * 1000), MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
					
					String path = Ppath + System.currentTimeMillis() + ".jpg";
					FileOutputStream fos = null;
					try {
						fos = new FileOutputStream(path);
						bitmap.compress(CompressFormat.JPEG, 80, fos);
						fos.close();

						list.add(path);
						Message message1 = handler.obtainMessage();
						message1.what = 1;
						message1.arg1 = index;
						handler.sendMessage(message1);
						index++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	protected void widgetListener() {
		adapter.setOnClickListener(new MyItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {
				sendVideo((position + 1) * view.getWidth());
			}
		});
		img_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		txt_enter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				handler.post(runnable3);				
			}
		});
		
		relative.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (videoView.isPlaying()) {
					img_bg.setVisibility(View.VISIBLE);
					videoView.pause();

					handler.removeCallbacks(runnable);

				} else {
					videoView.setVisibility(View.VISIBLE);
					img_bg.setVisibility(View.GONE);
					videoView.start();

					layoutParams_progress = (LayoutParams) relative1.getLayoutParams();
					handler.postDelayed(runnable, (long) (layoutParams_progress.width * picture) + 500);
				}
			}
		});

		txt_left.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					DownX = event.getRawX();

					layoutParams_progress = (LayoutParams) relative1.getLayoutParams();
					layoutParams_yin = (LayoutParams) img_left.getLayoutParams();

					width_progress = layoutParams_progress.width;
					Margin_progress = layoutParams_progress.leftMargin;
					width1_progress = layoutParams_yin.width;

					break;
				case MotionEvent.ACTION_MOVE:

					LeftMoveLayout(event.getRawX() - DownX, event.getRawX());

					break;
				case MotionEvent.ACTION_UP:

					sendVideo();

					layoutParams_progress = null;
					layoutParams_yin = null;

					break;
				default:
					break;
				}
				return false;
			}
		});

		txt_right.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					DownX = event.getRawX();

					layoutParams_progress = (LayoutParams) relative1.getLayoutParams();
					layoutParams_yin = (LayoutParams) img_right.getLayoutParams();

					width_progress = layoutParams_progress.width;
					Margin_progress = layoutParams_progress.rightMargin;
					width1_progress = layoutParams_yin.width;

					break;
				case MotionEvent.ACTION_MOVE:

					RightMoveLayout(DownX - event.getRawX());

					break;
				case MotionEvent.ACTION_UP:
					layoutParams_progress = null;
					layoutParams_yin = null;
					break;

				default:
					break;
				}
				return false;
			}
		});
		videoView.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				img_bg.setVisibility(View.VISIBLE);
				handler.removeCallbacks(runnable);
			}
		});

		recyclerView.setOnItemScrollChangeListener(new OnItemScrollChangeListener() {

			@Override
			public void onChange(View view, int position) {
				Scroll_lenth = position * view.getWidth() - view.getLeft();

				if (Scroll_lenth <= 0) {
					Scroll_lenth = 0;
				}

			}

			@Override
			public void onChangeState(int state) {
				if (state == 0) {
					sendVideo();
				}
			}
		});
	}

	private Runnable runnable3 = new Runnable() {

		@Override
		public void run() {
			try {
				layoutParams_progress = (LayoutParams) relative1.getLayoutParams();

				savePath = Ppath + System.currentTimeMillis() + ".mp4";

				FUckTest.startTrim(new File(bean.getPath()), new File(savePath), (long) ((Scroll_lenth + left_lenth) * picture), (long) ((Scroll_lenth
						+ left_lenth + layoutParams_progress.width) * picture));
				
				Intent it = new Intent(VideoPreviewActivity.this,VideoPreviewActivity.class);
				it.putExtra("path", savePath);
				startActivity(it);
				
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	};


	private void LeftMoveLayout(float MoveX, float X) {
		if (layoutParams_progress != null && layoutParams_yin != null) {
			if (Margin_progress + (int) MoveX > 0 && width_progress - (int) MoveX > last_length) {
				layoutParams_progress.width = width_progress - (int) MoveX;
				layoutParams_progress.leftMargin = Margin_progress + (int) MoveX;
				layoutParams_yin.width = width1_progress + (int) MoveX;

				relative1.setLayoutParams(layoutParams_progress);
				img_left.setLayoutParams(layoutParams_yin);

				txt_time.setText((float) (Math.round((layoutParams_progress.width * picture / 1000) * 10)) / 10 + " seconds");

				left_lenth = layoutParams_yin.width;
			}
		}
	}

	private void RightMoveLayout(float MoveX) {
		if (layoutParams_progress != null && layoutParams_yin != null) {
			if (Margin_progress + (int) MoveX > right_margin && width_progress - (int) MoveX > last_length) {
				layoutParams_progress.width = width_progress - (int) MoveX;
				layoutParams_progress.rightMargin = Margin_progress + (int) MoveX;
				layoutParams_yin.width = width1_progress + (int) MoveX;

				txt_time.setText((float) (Math.round((layoutParams_progress.width * picture / 1000) * 10)) / 10 + " seconds");

				relative1.setLayoutParams(layoutParams_progress);
				img_right.setLayoutParams(layoutParams_yin);
			}
		}
	}

	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			if (!img_bg.isShown()) {
				img_bg.setVisibility(View.VISIBLE);
			}
			if (videoView.isPlaying()) {
				videoView.pause();
			}
		}
	};

	private void sendVideo() {
		if (!videoView.isShown()) {
			videoView.setVisibility(View.VISIBLE);
		}
		if (videoView.isPlaying()) {
			videoView.pause();
		}
		if (!img_bg.isShown()) {
			img_bg.setVisibility(View.VISIBLE);
		}

		handler.removeCallbacks(runnable);

		videoView.seekTo((int) ((Scroll_lenth + left_lenth) * picture));
	}

	private void sendVideo(int lenth) {
		if (!videoView.isShown()) {
			videoView.setVisibility(View.VISIBLE);
		}
		if (videoView.isPlaying()) {
			videoView.pause();
		}
		if (!img_bg.isShown()) {
			img_bg.setVisibility(View.VISIBLE);
		}

		handler.removeCallbacks(runnable);

		videoView.seekTo((int) (lenth * picture));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		isThread = true;
		deleteFile();
	}
	private void deleteFile() {
		for (int i = 0; i < list.size(); i++) {
			File file = new File(list.get(i));
			if (file.exists()) {
				file.delete();
			}
		}
	}
}
