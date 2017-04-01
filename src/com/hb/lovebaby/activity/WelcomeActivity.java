package com.hb.lovebaby.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.hb.lovebaby.R;
import com.hb.lovebaby.httppost.HttpPost;
import com.hb.lovebaby.share.ShareUserInfo;

public class WelcomeActivity extends Activity {

	Handler mHandler = new Handler(Looper.getMainLooper());

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		initData();
	//	initLogin();
	}

	private void initData() {

		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				ShareUserInfo shareUserInfo = new ShareUserInfo(
						WelcomeActivity.this);

				if (shareUserInfo.isHaveUser()) {// 有用户登录就跳到首页，没有用户登录就跳转到登录界面
					gotoMainActivity();
				} else {
					gotoLoginActivity();
				}

			}
		}, 1000);

	}

	private void gotoMainActivity() {
		Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	private void gotoLoginActivity() {
		Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

//	private void initLogin() {
//		HttpPost httpPost = new HttpPost(this);
//		httpPost.doPostAsync();
//	}
}
