package com.hb.lovebaby.activity;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hb.lovebaby.R;
import com.hb.lovebaby.emoji.SelectFaceHelper;
import com.hb.lovebaby.emoji.SelectFaceHelper.OnFaceOprateListener;
import com.hb.lovebaby.modle.PhotoModel;
import com.hb.lovebaby.util.ActivityRequestCode;

public class BabyNewChangesActivity extends Activity {

	private TextView tvTitle;
	private ImageButton btnBack;

	private ImageButton imgBtnEmoji;

	private EditText edtContent;

	private GridView noScrollgridview;
	private GridAdapter adapter;
	public static Bitmap bimap;
	private int num = 8;
	private int max = 0;
	private ArrayList<PhotoModel> selected;

	private View recordingTimeLayout, visualRangeLayout, locationLayout;

	private Dialog dialog;
	private View inflate;

	private SelectFaceHelper mFaceHelper;
	private View addFaceToolView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baby_new_changes);
		this.initView();
		initData();
		this.initAddPhotoView();
		initClick();

	}

	private void initView() {
		tvTitle = (TextView) findViewById(R.id.tv_title_activity);
		btnBack = (ImageButton) findViewById(R.id.btn_back_activity);

		edtContent = (EditText) findViewById(R.id.babynewchanges_textview);
		imgBtnEmoji = (ImageButton) findViewById(R.id.babynewchanges_emojiview);

		recordingTimeLayout = (View) findViewById(R.id.recording_time);
		visualRangeLayout = (View) findViewById(R.id.visual_range);
		locationLayout = (View) findViewById(R.id.location);
	}

	private void initData() {
		tvTitle.setText(getString(R.string.baby_new_changes_title));
	}

	private void initClick() {
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		recordingTimeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(BabyNewChangesActivity.this,
						SelectRecordsTimeActivity.class);
				startActivity(intent);
			}
		});
		imgBtnEmoji.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				show();
			}
		});
		
		visualRangeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(BabyNewChangesActivity.this,
						VisualRangeActivity.class);
				startActivity(intent);
			}
		});
	}

	private boolean isVisbilityFace;

	public void show() {
		dialog = new Dialog(this, R.style.ActionSheetDialogStyle);

		// 填充对话框的布局
		inflate = LayoutInflater.from(this).inflate(R.layout.biaoqing_dialog,
				null);
		addFaceToolView = (View) inflate.findViewById(R.id.add_tool);
		// 初始化控件
		if (null == mFaceHelper) {
			mFaceHelper = new SelectFaceHelper(BabyNewChangesActivity.this,
					addFaceToolView);
			mFaceHelper.setFaceOpreateListener(mOnFaceOprateListener);
		}
		if (isVisbilityFace) {
			isVisbilityFace = false;
			addFaceToolView.setVisibility(View.GONE);
		} else {
			isVisbilityFace = true;
			addFaceToolView.setVisibility(View.VISIBLE);
			hideInputManager(BabyNewChangesActivity.this);
		}
		// 将布局设置给Dialog
		dialog.setContentView(inflate);
		// 获取当前Activity所在的窗体
		Window dialogWindow = dialog.getWindow();
		// 设置Dialog从窗体底部弹出
		dialogWindow.setGravity(Gravity.BOTTOM);
		// 获得窗体的属性
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.y = 0;// 设置Dialog距离底部的距离
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

		int width = wm.getDefaultDisplay().getWidth();
		lp.width = width;
		// 将属性设置给窗体
		dialogWindow.setAttributes(lp);
		dialog.show();// 显示对话框
	}

	OnFaceOprateListener mOnFaceOprateListener = new OnFaceOprateListener() {
		@Override
		public void onFaceSelected(SpannableString spanEmojiStr) {
			if (null != spanEmojiStr) {
				edtContent.append(spanEmojiStr);
			}
		}

		@Override
		public void onFaceDeleted() {
			int selection = edtContent.getSelectionStart();
			String text = edtContent.getText().toString();
			if (selection > 0) {
				String text2 = text.substring(selection - 1);
				if ("]".equals(text2)) {
					int start = text.lastIndexOf("[");
					int end = selection;
					edtContent.getText().delete(start, end);
					return;
				}
				edtContent.getText().delete(selection - 1, selection);
			}
		}

	};

	// 隐藏软键盘
	public void hideInputManager(Context ct) {
		try {
			((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(((Activity) ct).getCurrentFocus()
							.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
		}
	}

	private void initAddPhotoView() {
		if (selected == null) {
			selected = new ArrayList<PhotoModel>();
		}
		noScrollgridview = (GridView) findViewById(R.id.custom_album_gridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				Bundle bundle = new Bundle();
				bundle.putSerializable("selected", selected);
				if (position == BabyNewChangesActivity.this.selected.size()) {
					// Intent intent = new Intent(BabyNewChangesActivity.this,
					// PhotoSelectorActivity.class);
					// intent.putExtras(bundle);
					// intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					// startActivityForResult(intent,
					// ActivityRequestCode.CHOOSE_PICTURE);
				} else {
					// Intent intent = new Intent(BabyNewChangesActivity.this,
					// PhotoPreviewActivity.class);
					// intent.putExtra("position", position);
					// intent.putExtras(bundle);
					// intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					// startActivityForResult(intent,
					// ActivityRequestCode.EDIT_PICTURE);
				}
			}
		});
	}

	public void initList() {

		locationLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// Intent intent = new Intent(BabyNewChangesActivity.this,
				// SelectAddressActivity.class);
				// startActivity(intent);
			}
		});
	}

	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			if (BabyNewChangesActivity.this.selected.size() == num) {
				return num;
			}
			return (BabyNewChangesActivity.this.selected.size() + 1);
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_add_photo, parent,
						false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_add_photo_img);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == BabyNewChangesActivity.this.selected.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == num) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				try {
					holder.image
							.setImageBitmap(BabyNewChangesActivity.this.selected
									.get(position).revitionImageSize());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			if (BabyNewChangesActivity.this.selected == null) {
				return;
			}
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (BabyNewChangesActivity.this.max == BabyNewChangesActivity.this.selected
								.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							BabyNewChangesActivity.this.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
				}
			}).start();
		}
	}

	@SuppressWarnings("unchecked")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			return;
		}
		switch (requestCode) {
		case ActivityRequestCode.CHOOSE_PICTURE:
			Bundle bundle = data.getExtras();
			if (bundle != null) {
				selected = (ArrayList<PhotoModel>) bundle
						.getSerializable("selected");
				adapter.update();
			}
			break;
		}
	}
}
