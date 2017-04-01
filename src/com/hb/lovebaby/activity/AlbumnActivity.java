package com.hb.lovebaby.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.hb.lovebaby.R;
import com.hb.lovebaby.adapter.AlbumListAdapter;
import com.hb.lovebaby.dialog.ListDialog;
import com.hb.lovebaby.dialog.ListDialog.OnSelectItemListener;
import com.hb.lovebaby.modle.AlbumItem;
import com.hb.lovebaby.modle.AlbumRealItem;
import com.hb.lovebaby.modle.AlbumSeparator;
import com.hb.lovebaby.util.CommonUtils;

public class AlbumnActivity extends BascActivity {
	
	private List<AlbumItem> albums;
	
	private ListView listView;
	
	private AlbumListAdapter adapter;
	
	private ImageButton addBtn;
	
	// 搜搜框
	private EditText searchEditText;
	// 搜索按钮
	private ImageButton searchButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.album);
		
		populateData();
		
		adapter = new AlbumListAdapter(this, -1, albums);
		listView = (ListView) findViewById(R.id.albumn_list_view);
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		searchButton = (ImageButton)findViewById(R.id.searchButton);
		searchButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(AlbumnActivity.this, AlbumSearchActivity.class);
				startActivity(intent);
			}
		});
		searchEditText = (EditText)findViewById(R.id.searchEditText);
		searchEditText.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(AlbumnActivity.this, AlbumSearchActivity.class);
				startActivity(intent);
			}
		});
		
		
		
		addBtn = (ImageButton) findViewById(R.id.albumn_add_btn);
		addBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String[] items = new String[] {
						getResources().getString(R.string.photo),
						getResources().getString(R.string.video),
						getResources().getString(R.string.cancel)
				};
				String title = getResources().getString(R.string.from_album_to_choose);
				ListDialog listDialog = new ListDialog(AlbumnActivity.this, title, items);
				listDialog.show();
				listDialog.setOnSelectItemListener(new OnSelectItemListener() {
					
					@Override
					public void OnSelectItem(Dialog dialog, String item) {
						// TODO Auto-generated method stub
						dialog.cancel();
						if(item.equals(AlbumnActivity.this.getString(R.string.photo))){
							CommonUtils.launchActivityForResult(AlbumnActivity.this, PhotoSelectorActivity.class, 0);
						}
						else if(item.equals(AlbumnActivity.this.getString(R.string.video))){
							CommonUtils.launchActivityForResult(AlbumnActivity.this, VideoSelectorActivity.class, 0);
						}
					}
				});
			}
		});
	}

	private void populateData() {
		albums = new ArrayList<AlbumItem>();
		albums.add(new AlbumRealItem(getResources().getString(R.string.recently_uploaded), "", "", R.color.gray2));
		albums.add(new AlbumRealItem(getResources().getString(R.string.all_photos), "", "", R.color.gray2));
		albums.add(new AlbumRealItem(getResources().getString(R.string.all_video), "", "", R.color.gray2));
		albums.add(new AlbumSeparator("2016"));
		for (int i = 0; i < 10; i++) {
			albums.add(new AlbumRealItem("On September", "Baby 2 months", "A total of 10 photos", R.color.baby_blue));	
		}
		albums.add(new AlbumSeparator("2015"));
		for (int i = 0; i < 10; i++) {
			albums.add(new AlbumRealItem("On September", "Baby 2 months", "A total of 10 photos", R.color.baby_blue));	
		}
		albums.add(new AlbumSeparator("2014"));
		for (int i = 0; i < 10; i++) {
			albums.add(new AlbumRealItem("On September", "Baby 2 months", "A total of 10 photos", R.color.baby_blue));	
		}
		albums.add(new AlbumSeparator("2013"));
		for (int i = 0; i < 10; i++) {
			albums.add(new AlbumRealItem("On September", "Baby 2 months", "A total of 10 photos", R.color.baby_blue));	
		}
	}
	
	
}
