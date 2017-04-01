package com.hb.lovebaby.http;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.hb.lovebaby.LBApplication;
import com.hb.lovebaby.R;
import com.hb.lovebaby.activity.MainActivity;
import com.hb.lovebaby.share.ShareUserInfo;
import com.hb.lovebaby.util.StreamTools;

public class LoginHttp {
	Activity activity;
	private String url = "/login/withCredential";

	private JSONObject mJsonObject;

	private ProgressDialog mDialog;

	private int code;
	private String email, password;
	public LoginHttp(Activity activity) {
		super();
		this.activity = activity;
	}

	public void login(final String d_email, final String d_password) {
		email=d_email;
		password=d_password;
		new LoginAsy().execute();
	}
	class LoginAsy extends AsyncTask<Void, Void, Void>{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mDialog = ProgressDialog.show(activity,
					activity.getString(R.string.http_prompt),
					activity.getString(R.string.http_access_to_inforamtion));
			mDialog.show();
		}
		@Override
		protected Void doInBackground(Void... arg0) {
			getMessage(email, password);// 测试
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mDialog.dismiss();
		}
	}


	private void getMessage(String email, String password) {
		this.email = email;
		this.password = password;
		LBApplication cApplication = (LBApplication) activity.getApplication();
		// 1.创建 HttpClient 的实例
		HttpClient client = new DefaultHttpClient();
		// 2. 创建某种连接方法的实例，在这里是HttpPost。在 HttpPost 的构造函数中传入待连接的地址
		HttpPost httpPost = new HttpPost(cApplication.getUrl() + url);
		try {
			// 封装传递参数的集合
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			// 往这个集合中添加你要传递的参数
			parameters.add(new BasicNameValuePair("principal", email));
			parameters.add(new BasicNameValuePair("password", password));
			// 创建传递参数封装 实体对象
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters,
					"UTF-8");// 设置传递参数的编码
			// 把实体对象存入到httpPost对象中
			httpPost.setEntity(entity);
			// 3. 调用第一步中创建好的实例的 execute 方法来执行第二步中创建好的 method 实例
			HttpResponse response = client.execute(httpPost); // HttpUriRequest的后代对象
																// //在浏览器中敲一下回车
			// 4. 读 response
			code = response.getStatusLine().getStatusCode();
			InputStream is = response.getEntity().getContent();// 获取内容
			String result = StreamTools.streamToStr(is); // 通过工具类转换文本
			try {
				mJsonObject = new JSONObject(result);
				mHandler.obtainMessage().sendToTarget();
			} catch (JSONException e) {
				e.printStackTrace();
				Toast.makeText(activity,
						activity.getString(R.string.http_data_interface_error),
						Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6. 释放连接。无论执行方法是否成功，都必须释放连接
			client.getConnectionManager().shutdown();
			
		}

	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		// 重写handleMessage() 方法，此方法在UI线程运行
		public void handleMessage(Message msg) {
			try {// {"ret":"SUCCESS",
					// "data":{"id":"58be25aaa23f4d06a1cda87d","access_key":"salumbaby.897886338@qq.com.d239d480030311e7a2e581107cc1f538",
					// "access_secret":"d05b48a09e939de77b948c0acc892a472d35de4c0bac419adcfe9e592256cdf4"}}
				if (code == 200) {
					if (mJsonObject.getString("ret").equals("SUCCESS")) {
						JSONObject temp = mJsonObject.getJSONObject("data");
						saveUserInfo(temp.getString("access_key"),
								temp.getString("access_secret"),
								temp.getString("id"));
						Intent intent = new Intent(activity, MainActivity.class);
						activity.startActivity(intent);
					}
				} else {
					Toast.makeText(activity, mJsonObject.getString("msg"),
							Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				Toast.makeText(activity,
						activity.getString(R.string.http_data_parsing_errors),
						Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
		}
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
