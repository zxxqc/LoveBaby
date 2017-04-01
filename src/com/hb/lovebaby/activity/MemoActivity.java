package com.hb.lovebaby.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hb.lovebaby.R;
import com.hb.lovebaby.modle.MemoItem;
import com.origamilabs.library.views.StaggeredGridView;
import com.origamilabs.library.views.StaggeredGridView.OnItemClickListener;
import com.squareup.picasso.Picasso;

public class MemoActivity extends Activity {

	private TextView tvTitle;
	private ImageButton btnBack;
	private TextView tvAdd;
	
	private StaggeredGridView gridView;

	private List<MemoItem> list;

	private MemoListAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_memo);
		initView();
		initClick();
		initData();

	}

	private void initView() {
		tvTitle = (TextView) findViewById(R.id.tv_title_activity);
		btnBack = (ImageButton) findViewById(R.id.btn_back_activity);
		tvAdd = (TextView) findViewById(R.id.tv_title_send);
		tvAdd.setVisibility(View.VISIBLE);
		
		gridView = (StaggeredGridView) findViewById(R.id.memo_gridview);
	}

	private void initData() {
		tvTitle.setText(getString(R.string.baby_memorabilia_title));
		tvAdd.setBackgroundResource(R.drawable.memo_add_bg);
		
		list = new ArrayList<MemoItem>();
		for (int i = 0; i < 40; i++) {
			list.add(new MemoItem(R.drawable.ps1, R.drawable.login_fg_press,
					"The fifth day", "The first call mom"));
		}

		initAdapter();
	}

	private void initAdapter() {
		// set list adapter
		adapter = new MemoListAdapter(this, list);

		gridView.setAdapter(adapter);
		
		adapter.notifyDataSetChanged();
	}
	
	private void initClick() {
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		
		tvAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MemoActivity.this,
						BabyNewChangesActivity.class);
				startActivity(intent);
			}
		});
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(StaggeredGridView parent, View view,
					int position, long id) {
				Intent intent = new Intent(MemoActivity.this,
						MemoInfoActivity.class);
				startActivity(intent);
			}
		});
	}

	private class MemoListAdapter extends ArrayAdapter<MemoItem> {

		private LayoutInflater inflater;

		public MemoListAdapter(Context context, List<MemoItem> objects) {
			super(context, 0, objects);
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getItemViewType(int position) {
			if (position == 0) {
				return 0;
			} else {
				return 1;
			}
		}

		@Override
		public int getViewTypeCount() {
			return 2;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v;

			if (position == 0) {
				if (convertView == null) {
					v = inflater.inflate(R.layout.memo_list_header, parent,
							false);
				} else {
					v = convertView;
				}

				ViewHolder holder = (ViewHolder) v.getTag();
				if (holder == null) {
					holder = new ViewHolder();
					v.setTag(holder);
					holder.img = (ImageView) v.findViewById(R.id.memo_img);
				}

				Picasso.with(getContext()).load(R.drawable.baby_tx)
						.into(holder.img);

			} else {
				if (convertView == null) {
					v = inflater
							.inflate(R.layout.memo_list_item, parent, false);
				} else {
					v = convertView;
				}

				ViewHolder holder = (ViewHolder) v.getTag();
				if (holder == null) {
					holder = new ViewHolder();
					v.setTag(holder);
					holder.img = (ImageView) v.findViewById(R.id.memo_img);
				}

				Picasso.with(getContext()).load(R.drawable.psd2)
						.into(holder.img);
			}

			return v;
		}
	}

	private class ViewHolder {
		public ImageView img;
	}

}
