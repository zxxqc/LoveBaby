package com.hb.lovebaby.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hb.lovebaby.R;
import com.hb.lovebaby.activity.AddMyBabyActivity;
import com.hb.lovebaby.activity.BabyMemoActivity;
import com.hb.lovebaby.activity.CollectionActivity;
import com.hb.lovebaby.activity.FeedbackActivity;
import com.hb.lovebaby.activity.SettingsActivity;
import com.hb.lovebaby.activity.myactivity.UserMessageActivity;
import com.hb.lovebaby.httpget.GetUserInfoHttp;

public class MyFragment extends Fragment {

	private Button btnAddBaby, btnFeedback, btnSettings; // 第一行按钮
	private Button btnAddFamily, btnCollection, btnBabyMemo; // 第二行按钮
	private LinearLayout user_message;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my, null);
		initView(view);
		initClick();
		initData();
		return view;
	}

	private void initView(View view) {
		btnAddBaby = (Button) view.findViewById(R.id.btn_my_add_baby);
		btnFeedback = (Button) view.findViewById(R.id.btn_my_feedback);
		btnSettings = (Button) view.findViewById(R.id.btn_my_settings);

		btnAddFamily = (Button) view.findViewById(R.id.btn_my_add_family);
		btnCollection = (Button) view.findViewById(R.id.btn_my_collection);
		btnBabyMemo = (Button) view.findViewById(R.id.btn_my_baby_memo);
		user_message=(LinearLayout) view.findViewById(R.id.user_message);
	}

	private void initData() {
		getUserInfo();
	}

	private void initClick() {
		btnAddBaby.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),
						AddMyBabyActivity.class);//添加宝宝
				startActivity(intent);
			}
		});

		btnFeedback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),
						FeedbackActivity.class);//上传Feedback文件
				startActivity(intent);
			}
		});

		btnSettings.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),
						SettingsActivity.class);//设置
				startActivity(intent);
			}
		});

		btnAddFamily.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});

		btnCollection.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),
						CollectionActivity.class);
				startActivity(intent);
			}
		});

		btnBabyMemo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),
						BabyMemoActivity.class);
				startActivity(intent);
			}
		});
		user_message.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						UserMessageActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void getUserInfo() {
//		GetUserInfoHttp getUserInfoHttp = new GetUserInfoHttp(getActivity());
//		getUserInfoHttp.requestNetForGetUserInfo();
	}
}
