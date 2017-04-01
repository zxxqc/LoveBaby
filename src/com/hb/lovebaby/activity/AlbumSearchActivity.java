package com.hb.lovebaby.activity;

import com.hb.lovebaby.R;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class AlbumSearchActivity extends BascActivity {
	
	// 搜搜框
	private EditText searchEditText;
	// 搜索按钮
	private ImageButton searchButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_album_search);
		
	}
	
	private void initLayout(){
		
		searchEditText = (EditText)findViewById(R.id.searchEditText);
		
		searchButton = (ImageButton)findViewById(R.id.searchButton);
		searchButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// 开始搜索
			}
		});
		
		
	}

}
