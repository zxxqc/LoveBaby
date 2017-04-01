package com.hb.lovebaby.http;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.hb.lovebaby.LBApplication;
import com.hb.lovebaby.R;
import com.hb.lovebaby.util.StreamTools;

public class RegisteredHttp {
	Activity activity;
	private String url = "/register/email";

	private JSONObject mJsonObject;

	private ProgressDialog mDialog;

	private int code;

	public RegisteredHttp(Activity activity) {
		super();
		this.activity = activity;
	}

	public void registered(final String email, final String password,
			final String name, final String isReset) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				mDialog = ProgressDialog.show(activity,
						activity.getString(R.string.http_prompt),
						activity.getString(R.string.http_access_to_inforamtion));
				registeredHttp(email, password, name, isReset);// 测试
			}
		}).start();
	}

	public void registeredHttp(String email, String password, String name,
			String isReset) {
		LBApplication cApplication = (LBApplication) activity.getApplication();
		// 1.创建 HttpClient 的实例
		HttpClient client = new DefaultHttpClient();
		// 2. 创建某种连接方法的实例，在这里是HttpPost。在 HttpPost 的构造函数中传入待连接的地址
		HttpPost httpPost = new HttpPost(cApplication.getUrl() + url);
		try {
			// 封装传递参数的集合
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			// 往这个集合中添加你要传递的参数
			parameters.add(new BasicNameValuePair("email", email));
			parameters.add(new BasicNameValuePair("password", password));
			parameters.add(new BasicNameValuePair("name", name));
			parameters.add(new BasicNameValuePair("reset_password", isReset));
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
			mDialog.dismiss();
		}

	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		// 重写handleMessage() 方法，此方法在UI线程运行
		public void handleMessage(Message msg) {
			try {// {"ret":"PASSWORD_INVALID","msg":"password must be of length greater than or equal to 6, and must contain a-zA-Z and digit"}
				if (code == 200) {
					if (mJsonObject.getString("ret").equals("SUCCESS")) {
						Toast.makeText(activity, mJsonObject.getString("msg"),
								Toast.LENGTH_LONG).show();
						activity.setResult(activity.RESULT_CANCELED);
						activity.finish();
					} else {
						Toast.makeText(activity, mJsonObject.getString("msg"),
								Toast.LENGTH_LONG).show();
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

	/**
	 * 将字符串转成MD5值
	 * 
	 * @param string
	 * @return
	 */
	public static String stringToMD5(String string) {
		byte[] hash;

		try {
			hash = MessageDigest.getInstance("MD5").digest(
					string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}

		return hex.toString();
	}

}
