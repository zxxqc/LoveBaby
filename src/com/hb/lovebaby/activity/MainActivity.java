package com.hb.lovebaby.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hb.lovebaby.R;
import com.hb.lovebaby.fragment.HomeFragment;
import com.hb.lovebaby.fragment.MessageFragment;
import com.hb.lovebaby.fragment.MyFragment;
import com.hb.lovebaby.httpget.GetUserInfoHttp;

public class MainActivity extends FragmentActivity {

	private ImageButton imgbtnHome, imgbtnMessage, imgbtnMy;

	private Fragment mCurrFragment;
	private FragmentManager fm;
	private FragmentTransaction transaction;

	private HomeFragment homeFragment;
	private MessageFragment messageFragment;
	private MyFragment myFragment;

	private static boolean isExit = false;
	private static boolean hasTask = false;

	private int page = 1;

	private boolean tag_1 = true, tag_2 = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		initData();
		initRadioButton();
		initClick();
	}

	private void initViews() {
		imgbtnHome = (ImageButton) findViewById(R.id.main_activity_rbtn_home);
		imgbtnMessage = (ImageButton) findViewById(R.id.main_activity_rbtn_message);
		imgbtnMy = (ImageButton) findViewById(R.id.main_activity_rbtn_my_info);
	}

	private void initData() {
		GetUserInfoHttp getUserInfoHttp = new GetUserInfoHttp(this);
		getUserInfoHttp.doGetAsync();
	}

	private void initClick() {
		imgbtnHome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				page = 1;
				imgbtnHome.setImageResource(R.drawable.tab_1_s);
				imgbtnMessage.setImageResource(R.drawable.tab_2);
				imgbtnMy.setImageResource(R.drawable.tab_3);
				switchContent(homeFragment);
			}
		});
			
		imgbtnMessage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				page = 2;
				if (tag_1) {
					tag_1 = false;
					messageFragment = new MessageFragment();

				}
				imgbtnHome.setImageResource(R.drawable.tab_1);
				imgbtnMessage.setImageResource(R.drawable.tab_2_s);
				imgbtnMy.setImageResource(R.drawable.tab_3);
				switchContent(messageFragment);
			}
		});
				
		imgbtnMy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				page = 3;
				if (tag_2) {
					tag_2 = false;
					myFragment = new MyFragment();

				}
				imgbtnHome.setImageResource(R.drawable.tab_1);
				imgbtnMessage.setImageResource(R.drawable.tab_2);
				imgbtnMy.setImageResource(R.drawable.tab_3_s);
				switchContent(myFragment);
			}
		});
		

	}

	public void switchContent(Fragment to) {
		if (mCurrFragment != to) {
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			if (!to.isAdded()) {
				transaction = fm.beginTransaction();
				transaction.hide(mCurrFragment).add(R.id.main_activity_content,
						to);
				transaction.addToBackStack(null);
				transaction.commitAllowingStateLoss();
			} else {
				transaction.hide(mCurrFragment).show(to)
						.commitAllowingStateLoss();
			}
		}
		mCurrFragment = to;
	}

	private void initRadioButton() {
		fm = getSupportFragmentManager();
		homeFragment = new HomeFragment();
		mCurrFragment = homeFragment;
		transaction = fm.beginTransaction();
		transaction.replace(R.id.main_activity_content, homeFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	Timer tExit = new Timer();
	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			isExit = false;
			hasTask = true;
		}
	};

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// exitDialog();
			if (isExit == false) {
				isExit = true;
				Toast.makeText(this, "再点一次退出", Toast.LENGTH_SHORT).show();
				if (!hasTask) {
					tExit.schedule(task, 2000);
				}
			} else {
				finish();
				System.exit(0);
			}

		}
		return false;
	}
}
