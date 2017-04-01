package com.hb.lovebaby.activity;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hb.lovebaby.R;

public class SettingsActivity extends Activity {

	private TextView tvTitle;
	private ImageButton btnBack;

	private View accountInfo;

	private View alerts;

	private View privacy;

	private View recommendToFriends;

	private View about;

	private View clearCache;

	private View videoSettings;

	private View wifiOnly;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		initViews();
		initClick();
		initData();
	}

	private void initViews() {

		tvTitle = (TextView) findViewById(R.id.tv_title_activity);
		btnBack = (ImageButton) findViewById(R.id.btn_back_activity);
		accountInfo = findViewById(R.id.account_info);
		alerts = findViewById(R.id.alerts);
		privacy = findViewById(R.id.privacy);
		recommendToFriends = findViewById(R.id.recommend_to_friends);
		about = findViewById(R.id.about);
		clearCache = findViewById(R.id.clear_cache);
		videoSettings = findViewById(R.id.video_settings);
		wifiOnly = findViewById(R.id.wifi_only);
	}

	private void initData() {
		tvTitle.setText(getString(R.string.settings_title));
	}

	private void initClick() {
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		accountInfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingsActivity.this,
						AccountInfoActivity.class);//密码，邮箱，第三方相关修改
				startActivity(intent);
			}
		});
		alerts.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingsActivity.this,
						AlertsActivity.class);
				startActivity(intent);
			}
		});
		privacy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingsActivity.this,
						PrivacyActivity.class);
				startActivity(intent);
			}
		});
		recommendToFriends.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog();
			}
		});
		about.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingsActivity.this,
						AboutSalamBabyActivity.class);
				startActivity(intent);
			}
		});
		clearCache.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		videoSettings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingsActivity.this,
						VideoSettingsActivity.class);
				startActivity(intent);
			}
		});
		wifiOnly.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
	}

	private Dialog setHeadDialog;
	private View mDialogView;

	public void showDialog() {
		setHeadDialog = new Builder(this).create();
		setHeadDialog.show();
		mDialogView = View.inflate(getApplicationContext(),
				R.layout.share_bottom_dialog, null);

		setHeadDialog.getWindow().setContentView(mDialogView);
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels; // 屏幕宽度（像素）
		int height = metric.heightPixels; // 屏幕高度（像素）
		WindowManager.LayoutParams params = setHeadDialog.getWindow()
				.getAttributes();
		params.width = width;
		params.x = 0;
		params.y = height - params.height;
		setHeadDialog.getWindow().setAttributes(params);
		setHeadDialog.setCanceledOnTouchOutside(true);
		bindDialogEvent();

	}

	private void bindDialogEvent() {
		LinearLayout llOne = (LinearLayout) mDialogView
				.findViewById(R.id.ll_share_1);// Facebook
		LinearLayout llTwo = (LinearLayout) mDialogView
				.findViewById(R.id.ll_share_2);// twitter
		LinearLayout llThree = (LinearLayout) mDialogView
				.findViewById(R.id.ll_share_3);// google
		LinearLayout llFour = (LinearLayout) mDialogView
				.findViewById(R.id.ll_share_4);// message
		LinearLayout llFive = (LinearLayout) mDialogView
				.findViewById(R.id.ll_share_5);// whatsapp
		Button btnCancle = (Button) mDialogView
				.findViewById(R.id.btn_share_cancle);
		btnCancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setHeadDialog.dismiss();
			}
		});

	}
}
