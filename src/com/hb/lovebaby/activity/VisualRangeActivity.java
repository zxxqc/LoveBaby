package com.hb.lovebaby.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hb.lovebaby.R;

public class VisualRangeActivity extends Activity {

	private TextView tvTitle;
	private ImageButton btnBack;

	private View vAllTheFamilyMembers, vOnlyYourSelf, vFamily, vChooseFamily;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visual_range);
		initView();
		initData();
		initClick();
	}

	private void initView() {
		tvTitle = (TextView) findViewById(R.id.tv_title_activity);
		btnBack = (ImageButton) findViewById(R.id.btn_back_activity);
		vAllTheFamilyMembers = findViewById(R.id.ll_visual_range_all_the_family_members);
		vOnlyYourSelf = findViewById(R.id.ll_visual_range_only_yourself);
		vFamily = findViewById(R.id.ll_visual_range_family);
		vChooseFamily = findViewById(R.id.ll_visual_range_choose_the_family);
	}

	private void initData() {
		tvTitle.setText(getString(R.string.visual_range_title));
	}

	private void initClick() {
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		vAllTheFamilyMembers.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
		vOnlyYourSelf.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
		vFamily.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
		vChooseFamily.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
	}
}
