package com.hb.lovebaby.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hb.lovebaby.R;

public class AboutSalamBabyActivity extends Activity {

	private TextView tvTitle;
	private ImageButton btnBack;

	private View newVersoinTesting;
	private View helpCenter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_salam_baby);
		initViews();
		initData();
		initClick();
	}

	private void initViews() {
		tvTitle = (TextView) findViewById(R.id.tv_title_activity);
		btnBack = (ImageButton) findViewById(R.id.btn_back_activity);
		newVersoinTesting = findViewById(R.id.ll_about_salam_baby_new_version_testing);
		helpCenter = findViewById(R.id.ll_about_salam_baby_help_center);
	}

	private void initData() {
		tvTitle.setText(getString(R.string.about_salam_baby_title));
	}

	private void initClick() {
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		newVersoinTesting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
			}
		});
		helpCenter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(AboutSalamBabyActivity.this,
						HelpCenterActivity.class);
				startActivity(intent);
			}
		});
		// phoneNumber.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// String[] items = new String[] {
		// getResources().getString(R.string.replace_phone_number),
		// getResources().getString(R.string.cancel) };
		// ListDialog listDialog = new ListDialog(
		// AccountInfoActivity.this, getResources().getString(
		// R.string.operation), items);
		// listDialog.setOnSelectItemListener(new OnSelectItemListener() {
		//
		// @Override
		// public void OnSelectItem(Dialog dialog, String item) {
		// // TODO Auto-generated method stub
		// dialog.cancel();
		// if (item.equals(getResources().getString(
		// R.string.replace_phone_number))) {
		// Intent intent = new Intent(
		// AccountInfoActivity.this,
		// BindNewPhoneNumberActivity.class);
		// startActivity(intent);
		// }
		// }
		// });
		// listDialog.show();
		// }
		// });
	}

}
