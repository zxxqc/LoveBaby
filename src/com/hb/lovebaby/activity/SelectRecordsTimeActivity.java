package com.hb.lovebaby.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hb.lovebaby.R;

public class SelectRecordsTimeActivity extends Activity {

	private TextView tvTitle;
	private ImageButton btnBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_records_ime);
		initView();
		initData();
		initClick();
	}

	private void initView() {
		tvTitle = (TextView) findViewById(R.id.tv_title_activity);
		btnBack = (ImageButton) findViewById(R.id.btn_back_activity);
	}

	private void initData() {
		tvTitle.setText(getString(R.string.recording_time_title));
	}

	private void initClick() {
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	}

}
