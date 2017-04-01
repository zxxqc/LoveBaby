package com.hb.lovebaby.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.hb.lovebaby.R;
import com.hb.lovebaby.adapter.BabyDynamicAdapter;
import com.hb.lovebaby.modle.BabyDynamic;

public class AlertsParentingRemindBabyDaynamicActivity extends Activity {

	private TextView tvTitle;
	private ImageButton btnBack;

	private ListView lvBaby;

	private BabyDynamicAdapter babyDynamicAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alerts_parenting_remind_baby_dynamic);
		initViews();
		initData();
		initClick();
	}

	private void initViews() {
		tvTitle = (TextView) findViewById(R.id.tv_title_activity);
		btnBack = (ImageButton) findViewById(R.id.btn_back_activity);

		lvBaby = (ListView) findViewById(R.id.lv_alerts_parenting_remind_baby_dynamic);
	}

	private void initData() {
		tvTitle.setText(getString(R.string.alerts_aprenting_remind_baby_dynamic_title));
		adapter();
	}

	private void adapter() {
		List<BabyDynamic> lists = new ArrayList<BabyDynamic>();
		BabyDynamic b = new BabyDynamic("", "baby", "boy", "close");
		lists.add(b);
		BabyDynamic c = new BabyDynamic("", "baby", "girl", "open");
		lists.add(c);
		babyDynamicAdapter = new BabyDynamicAdapter(lists, this);
		lvBaby.setAdapter(babyDynamicAdapter);
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
