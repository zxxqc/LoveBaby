package com.hb.lovebaby.activity;

import android.os.Bundle;
import android.view.View;

import com.hb.lovebaby.R;
import com.hb.lovebaby.view.MyActionBar;

public class RelativesCustomRelationshipActivity extends BascActivity {
	
	private MyActionBar actionBar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.relatives_custom_relationship);
		initViews();
	}
	
	private void initViews() {
		actionBar = (MyActionBar) findViewById(R.id.action_bar);
		actionBar.setOnActionButtonClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
