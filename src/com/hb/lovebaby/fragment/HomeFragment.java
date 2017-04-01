package com.hb.lovebaby.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.lovebaby.R;
import com.hb.lovebaby.activity.AlbumnActivity;
import com.hb.lovebaby.activity.BabyInfoActivity;
import com.hb.lovebaby.activity.MemoActivity;
import com.hb.lovebaby.activity.RelativesActivity;
import com.hb.lovebaby.activity.growth.GrawthRecordActivity;
import com.hb.lovebaby.adapter.MainAdapter;
import com.hb.lovebaby.modle.BabyRecord;
import com.hb.lovebaby.util.OkManager;
import com.hb.lovebaby.view.RefreshLoadScrollView;
import com.hb.lovebaby.view.RefreshLoadScrollView.RefreshLoadScrollViewListener;
import com.hb.lovebaby.view.RoundImageView;
import com.honeyBaby.adapter.viewholders.BabyRecordViewHolder;

public class HomeFragment extends Fragment implements
		RefreshLoadScrollViewListener {
	private static final String TAG = "HomeFragment";
	protected View mView;
	protected Context mContext;

	private RefreshLoadScrollView mainListView;
	private List<BabyRecord> mDataSet = new ArrayList<BabyRecord>();
	private MainAdapter mAdapter;
	private LinearLayout mainHead;
	private RoundImageView roundImageView;

	private Button InviteFamilyButton;

	private TextView mainAlbumBt;
	private TextView mainRecordBt;
	private TextView mainRelativesBt;
	private TextView mainEventBt;
	private TextView mainBabyinfoBt;
	
	private OkManager okManager;

	Handler imgHandler = new Handler(Looper.getMainLooper());
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		okManager=OkManager.getInstance(getActivity());
		
		
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mContext = getActivity();
		mView = inflater.inflate(R.layout.fragment_home, container, false);

		mainListView = (RefreshLoadScrollView) mView
				.findViewById(R.id.main_listView);

		mainHead = (LinearLayout) inflater.inflate(R.layout.main_head,
				mainListView, false);

		mainListView.addHeaderView(mainHead, null, false);
		mainListView.setMain_head(mainHead);
		mainHead.getViewTreeObserver().addOnPreDrawListener(
				//当一个视图树将要绘制时调用这个回调函数。
				new ViewTreeObserver.OnPreDrawListener() {
					@Override
					public boolean onPreDraw() {
						// test value
						mainListView.setMain_head_height(mainHead
								.getMeasuredHeight());
						mainHead.getViewTreeObserver().removeOnPreDrawListener(
								this);
						return false;
					}
				});

		mainAlbumBt = (TextView) mainHead.findViewById(R.id.main_album_tv);
		mainAlbumBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(mContext, AlbumnActivity.class);
				startActivity(intent);
			}
		});

		// 鐢熼暱璁板綍
		mainRecordBt = (TextView) mainHead.findViewById(R.id.main_growth_tv);
		mainRecordBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(mContext, GrawthRecordActivity.class);
				startActivity(intent);
			}
		});

		//宝宝亲人
		mainRelativesBt = (TextView) mainHead
				.findViewById(R.id.main_relatives_tv);
		mainRelativesBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(mContext, RelativesActivity.class);
				startActivity(intent);
			}
		});

		mainEventBt = (TextView) mainHead.findViewById(R.id.main_event_tv);
		mainEventBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(mContext, MemoActivity.class);
				startActivity(intent);
			}
		});

		/**
		 * 瀹濆疂淇℃伅
		 */
		mainBabyinfoBt = (TextView) mainHead
				.findViewById(R.id.main_babyinfo_tv);
		mainBabyinfoBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(mContext, BabyInfoActivity.class);
				startActivity(intent);
			}
		});

		mainListView.setRefreshLoadScrollViewListener(this);

		mAdapter = new MainAdapter(mDataSet);
		mainListView.setAdapter(mAdapter);

		roundImageView = (RoundImageView) mainHead
				.findViewById(R.id.main_top_avatar);
		roundImageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
//				Intent intent = new Intent(mContext, BabyInfoActivity.class);
//				startActivity(intent);
			}
		});
		InviteFamilyButton = (Button) mainHead
				.findViewById(R.id.main_invite_family_bt);
		InviteFamilyButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
//				Intent intent = new Intent(mContext,
//						RelativesSendInvitationActivity.class);
//				startActivity(intent);
			}
		});

		mAdapter.addAll(mockInitData(1));
		return mView;
	}

	private List<BabyRecord> mockInitData(int count) {
		List<BabyRecord> records = new ArrayList<BabyRecord>();
		for (int i = 0; i < count; i++) {
			BabyRecord record = new BabyRecord();
			record.recId = "id-" + i;
			record.year = "16";
			record.date = "0" + (i + 1) + ".2016";
			record.day = "Day" + i;
			record.avatar = "http://p3.gexing.com/G1/M00/36/54/rBACE1K9LAGDQVTcAAAbC0bNnDw546_200x200_3.jpg?recache=20131108";
			record.name = "MyBaby - " + i;
			record.area = "Dubai - " + i;
			// mock photos
			for (int j = 0; j < i + 1; j++) {
				record.photos.add(BabyRecordViewHolder.TEST_IMG);
			}
			records.add(record);
		}
		return records;
	}

	@Override
	public void onRefresh() {
		roundImageView.startProgress();
		Toast.makeText(getActivity(), "onRefresh", Toast.LENGTH_SHORT).show();
		imgHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				fetchItem(false);
				mainListView.stopRefresh();//停止下拉刷新
				roundImageView.stopProgress();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		Toast.makeText(getActivity(), "onLoadMore", Toast.LENGTH_SHORT).show();
		imgHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				fetchItem(true);
				mainListView.stopLoadMore();//停止上拉刷新
				mainListView.loadFinish(true);
			}
		}, 2000);
	}

	private void fetchItem(boolean append) {
		BabyRecord record = new BabyRecord();
		record.recId = "id-load";
		record.year = "16";
		record.date = "20.2016";
		record.day = "Day9";
		record.avatar = "http://p3.gexing.com/G1/M00/36/54/rBACE1K9LAGDQVTcAAAbC0bNnDw546_200x200_3.jpg?recache=20131108";
		record.name = "MyBaby - " + new Random().nextInt(100);
		record.area = "Beijing";
		// mock photos
		for (int j = 0; j < 6; j++) {
			record.photos.add(BabyRecordViewHolder.TEST_IMG);
		}
		if (append) {
			mAdapter.addItem(record);
		} else {
			mAdapter.addItemToHead(record);
		}
	}
}
