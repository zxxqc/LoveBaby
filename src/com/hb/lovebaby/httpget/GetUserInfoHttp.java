package com.hb.lovebaby.httpget;

import java.io.IOException;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;

import com.hb.lovebaby.LBApplication;
import com.hb.lovebaby.share.ShareUserInfo;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * 
 * @author hufangda 获取用户信息
 * 
 */
public class GetUserInfoHttp {
	Activity activity;
	private String url = "/account/userInfo";

	private JSONObject mJsonObject;

	private ProgressDialog mDialog;

	private int code;

	public GetUserInfoHttp(Activity activity) {
		super();
		this.activity = activity;
	}

	OkHttpClient mOkHttpClient = new OkHttpClient();

	public void doGetAsync() {
		// TODO Auto-generated method stub
		LBApplication cApplication = (LBApplication) activity.getApplication();
		// 1：url对象
		ShareUserInfo shareUserInfo = new ShareUserInfo(activity);
		String accesskey = shareUserInfo.getAccessKey();
		Request request = new Request.Builder()
				.url(cApplication.getUrl() + url)
				.addHeader("Authorization", accesskey + ":").build();
		// enqueue
		mOkHttpClient.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Request request, IOException e) {

			}

			@Override
			public void onResponse(Response response) throws IOException {
				// NOT UI Thread
				if (response.isSuccessful()) {
					System.out.println(response.code());
					// System.out.println(response.body().string());
					String body = response.body().string();
					Message msg = Message.obtain();
					getHandler.sendMessage(msg);
				}
			}
		});
	}

	private Handler getHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (code == 200) {

			}
		};
	};
}
