package com.hb.lovebaby.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hb.lovebaby.R;

public class AlertsActivity extends Activity {

	private TextView tvTitle;
	private ImageButton btnBack;

	private View llParentingRemind;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alerts);
		initViews();
		initData();
		initClick();
	}

	private void initViews() {
		tvTitle = (TextView) findViewById(R.id.tv_title_activity);
		btnBack = (ImageButton) findViewById(R.id.btn_back_activity);
		llParentingRemind = findViewById(R.id.ll_alerts_parenting_remind);
	}

	private void initData() {
		tvTitle.setText(getString(R.string.alerts_title));
	}

	private void initClick() {
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		llParentingRemind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(AlertsActivity.this,
						AlertsParentingRemindActivity.class);
				startActivity(intent);
			}
		});
	}
}
