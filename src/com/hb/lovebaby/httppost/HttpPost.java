package com.hb.lovebaby.httppost;

import java.io.IOException;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.hb.lovebaby.LBApplication;
import com.hb.lovebaby.share.ShareUserInfo;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class HttpPost {

	OkHttpClient mOkHttpClient = new OkHttpClient();

	private Activity activity;

	private String url = "";

	public HttpPost(Activity activity) {
		super();
		this.activity = activity;
	}

	public void doPostAsync() {
		// TODO Auto-generated method stub
		RequestBody formBody = new FormEncodingBuilder()
				.add("principal", "52677626@qq.com")
				.add("password", "hu123456").build();
		LBApplication lbApplication = (LBApplication) activity.getApplication();
		ShareUserInfo shareUserInfo = new ShareUserInfo(activity);
		String accesskey = shareUserInfo.getAccessKey();
		Request request = new Request.Builder()
				.url(lbApplication.getUrl() + url)
				.addHeader("Authorization", accesskey + ":").post(formBody)
				.build();

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
					String body = response.body().string(); // 返回值
					System.out.println(body);
					String result = response.code() + ""; // 返回码
					mHandler.sendEmptyMessage(MSG_WHAT);
				}

			}
		});

	}

	final int MSG_WHAT = 1;
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_WHAT:
				// textView1.setText(result);
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		};
	};

}
