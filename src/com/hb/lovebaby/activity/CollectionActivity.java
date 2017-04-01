package com.hb.lovebaby.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hb.lovebaby.R;
import com.hb.lovebaby.adapter.CollectionAdapter;

public class CollectionActivity extends Activity {

	private TextView tvTitle;
	private ImageButton btnBack;

	private GridView gvCollection;

	private List<Integer> collections;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection);
		initView();
		initData();
		initAdapter();
		initClick();
	}

	private void initView() {
		gvCollection = (GridView) findViewById(R.id.gv_collection);
		tvTitle = (TextView) findViewById(R.id.tv_title_activity);
		btnBack = (ImageButton) findViewById(R.id.btn_back_activity);
	}

	private void initData() {
		tvTitle.setText(getString(R.string.collection_title));
	}

	private void initAdapter() {
		collections = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			collections.add(R.drawable.feedback_pic_1);
		}
		CollectionAdapter collectionAdapter = new CollectionAdapter(
				collections, this);
		gvCollection.setAdapter(collectionAdapter);
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
