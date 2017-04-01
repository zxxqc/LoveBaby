package com.hb.lovebaby.share;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class ShareUserInfo {
	SharedPreferences mySharedPreferences;

	public ShareUserInfo(Context context) {
		super();
		mySharedPreferences = context.getSharedPreferences("userinfo",
				Activity.MODE_PRIVATE);

	}

	public void setUserName(String userName) {
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putString("userName", userName);
		editor.commit();
	}

	public String getUserName() {
		String name = mySharedPreferences.getString("userName", "");
		return name;
	}
	
	public void setPassword(String Password) {
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putString("Password", Password);
		editor.commit();
	}

	public String getPassword() {
		String name = mySharedPreferences.getString("Password", "");
		return name;
	}

	public void setNickName(String userName) {
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putString("NickName", userName);
		editor.commit();
	}

	public String getNickName() {
		String name = mySharedPreferences.getString("NickName", "");
		return name;
	}

	public void setID(String userName) {
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putString("ID", userName);
		editor.commit();
	}

	public String getID() {
		String name = mySharedPreferences.getString("ID", "");
		return name;
	}

	public void setAccessKey(String accessKey) {
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putString("accessKey", accessKey);
		editor.commit();
	}

	public String getAccessKey() {
		String name = mySharedPreferences.getString("accessKey", "");
		return name;
	}
	
	public void setAccessSecret(String access_secret) {
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putString("access_secret", access_secret);
		editor.commit();
	}

	public String getAccessSecret() {
		String name = mySharedPreferences.getString("access_secret", "");
		return name;
	}

	public boolean isHaveUser() {
		if (getUserName() == null || getUserName().equals("")) {
			return false;
		} else {
			return true;
		}
	}

}
