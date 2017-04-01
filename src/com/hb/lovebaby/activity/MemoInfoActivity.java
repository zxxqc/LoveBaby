package com.hb.lovebaby.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hb.lovebaby.R;
import com.hb.lovebaby.emoji.SelectFaceHelper;
import com.hb.lovebaby.emoji.SelectFaceHelper.OnFaceOprateListener;

public class MemoInfoActivity extends Activity {

	private TextView tvTitle;
	private ImageButton btnBack;

	private TextView tvMessageTitle;
	private LinearLayout llPhotos;//动态添加图片
	private TextView tvContent;
	private TextView tvAddress;
	private TextView tvDate;

	private SelectFaceHelper mFaceHelper;
	private Button mFaceBtn;
	private View addFaceToolView;
	private boolean isVisbilityFace;
	private EditText mEditMessageEt;
	private Button mSentBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_memo_info);
		initView();
		initClick();
		initData();

	}

	private void initView() {
		tvTitle = (TextView) findViewById(R.id.tv_title_activity);
		btnBack = (ImageButton) findViewById(R.id.btn_back_activity);

		tvMessageTitle = (TextView) findViewById(R.id.tv_memo_info_title);
		llPhotos = (LinearLayout) findViewById(R.id.ll_memo_info_photos);
		tvContent = (TextView) findViewById(R.id.tv_memo_info_message);
		tvAddress = (TextView) findViewById(R.id.tv_memo_info_address);
		tvDate = (TextView) findViewById(R.id.tv_memo_info_date);

		addFaceToolView = (View) findViewById(R.id.add_tool);
		mEditMessageEt = (EditText) findViewById(R.id.txtMessage);
		mSentBtn = (Button) findViewById(R.id.btnSend);
		mFaceBtn = (Button) findViewById(R.id.btn_to_face);

	}

	private void initData() {
		tvTitle.setText(getString(R.string.baby_memorabilia_title));

		initAdapter();
	}

	private void initAdapter() {
		// set list adapter
	}

	private void initClick() {
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});

		mFaceBtn.setOnClickListener(faceClick);
		mEditMessageEt.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				isVisbilityFace = false;
				addFaceToolView.setVisibility(View.GONE);
				return false;
			}
		});

	}

	View.OnClickListener faceClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (null == mFaceHelper) {
				mFaceHelper = new SelectFaceHelper(MemoInfoActivity.this,
						addFaceToolView);
				mFaceHelper.setFaceOpreateListener(mOnFaceOprateListener);
			}
			if (isVisbilityFace) {
				isVisbilityFace = false;
				addFaceToolView.setVisibility(View.GONE);
			} else {
				isVisbilityFace = true;
				addFaceToolView.setVisibility(View.VISIBLE);
				hideInputManager(MemoInfoActivity.this);
			}
		}
	};

	OnFaceOprateListener mOnFaceOprateListener = new OnFaceOprateListener() {
		@Override
		public void onFaceSelected(SpannableString spanEmojiStr) {
			if (null != spanEmojiStr) {
				mEditMessageEt.append(spanEmojiStr);
			}
		}

		@Override
		public void onFaceDeleted() {
			int selection = mEditMessageEt.getSelectionStart();
			String text = mEditMessageEt.getText().toString();
			if (selection > 0) {
				String text2 = text.substring(selection - 1);
				if ("]".equals(text2)) {
					int start = text.lastIndexOf("[");
					int end = selection;
					mEditMessageEt.getText().delete(start, end);
					return;
				}
				mEditMessageEt.getText().delete(selection - 1, selection);
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

}
