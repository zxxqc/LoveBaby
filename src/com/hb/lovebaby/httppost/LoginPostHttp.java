package com.hb.lovebaby.httppost;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.hb.lovebaby.LBApplication;
import com.hb.lovebaby.activity.MainActivity;
import com.hb.lovebaby.share.ShareUserInfo;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class LoginPostHttp {

	OkHttpClient mOkHttpClient = new OkHttpClient();

	private Activity activity;
	
	private JSONObject mJsonObject; //返回值解析成json

	private String code; //返回码

	final int MSG_WHAT = 1; 
	
	private String email,password;

	public LoginPostHttp(Activity activity) {
		super();
		this.activity = activity;
	}
	
	public void doPostAsync(String email,String password) {
		this.email = email;
		this.password = password;
		// TODO Auto-generated method stub
		RequestBody formBody = new FormEncodingBuilder()  //添加值
				.add("principal", email)
				.add("password", password).build();  
		LBApplication lbApplication = (LBApplication) activity.getApplication();
		Request request = new Request.Builder()
				.url(lbApplication.getUrl() + "/login/withCredential")
				.post(formBody).build();

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

					String body = response.body().string(); // 返回值
					try {
						mJsonObject = new JSONObject(body);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					code = response.code() + ""; // 返回码
					mHandler.sendEmptyMessage(MSG_WHAT);
				}

			}
		});

	}

	
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_WHAT:
				try {
					if (code.equals("200")) { // 返回值正确获取信息
						if (mJsonObject.getString("ret").equals("SUCCESS")) {
//							Toast.makeText(activity, mJsonObject.toString(), Toast.LENGTH_SHORT).show();
//							Log.i("tag", mJsonObject.toString());
							JSONObject temp = mJsonObject.getJSONObject("data");
							saveUserInfo(temp.getString("access_key"),
									temp.getString("access_secret"),
									temp.getString("id"));
							Intent intent = new Intent(activity, MainActivity.class);
							activity.startActivity(intent);
						}else{
							Toast.makeText(activity, mJsonObject.getString("msg"),
									Toast.LENGTH_SHORT).show(); // 返回值错误
						}
					} else {
						Toast.makeText(activity, mJsonObject.getString("msg"),
								Toast.LENGTH_SHORT).show(); // 返回值错误
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			default:
				break;
			}
			super.handleMessage(msg);
		};
	};
	
	private void saveUserInfo(String accessKey, String access_secret, String id) {
		ShareUserInfo shareUserInfo = new ShareUserInfo(activity);
		shareUserInfo.setUserName(email);
		shareUserInfo.setPassword(password);
		shareUserInfo.setAccessKey(accessKey);
		shareUserInfo.setAccessSecret(access_secret);
		shareUserInfo.setID(id);
	}

}
