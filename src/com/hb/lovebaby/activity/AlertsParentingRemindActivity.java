package com.hb.lovebaby.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hb.lovebaby.R;

public class AlertsParentingRemindActivity extends Activity {

	private TextView tvTitle;
	private ImageButton btnBack;

	private View llBabyDynamic, llParentingRemind;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alerts_parenting_remind);
		initViews();
		initData();
		initClick();
	}

	private void initViews() {
		tvTitle = (TextView) findViewById(R.id.tv_title_activity);
		btnBack = (ImageButton) findViewById(R.id.btn_back_activity);

		llBabyDynamic = findViewById(R.id.ll_alerts_parenting_remind_baby_dynamic);
		llParentingRemind = findViewById(R.id.ll_alerts_parenting_remind_parenting_remind);
	}

	private void initData() {
		tvTitle.setText(getString(R.string.alerts_parenting_remind_title));
	}

	private void initClick() {
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		llBabyDynamic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(AlertsParentingRemindActivity.this,
						AlertsParentingRemindBabyDaynamicActivity.class);
				startActivity(intent);
			}
		});
		llParentingRemind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(AlertsParentingRemindActivity.this,
						AlertsParentingRemindParentingRemindActivity.class);
				startActivity(intent);
			}
		});
	}
}
