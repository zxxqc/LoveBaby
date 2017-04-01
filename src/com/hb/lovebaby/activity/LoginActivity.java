package com.hb.lovebaby.activity;

import com.hb.lovebaby.R;
import com.hb.lovebaby.httppost.LoginPostHttp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginActivity extends Activity {
	// 登录按钮
	private Button login_bt;
	// 注册按钮
	private Button regist_bt;
	// f登录
	private ImageButton login_f_bt;
	// fg登录
	private ImageButton login_fg_bt;
	// g登录
	private ImageButton login_g_bt;
	
	private EditText edt_registered_account;
	private EditText edt_registered_the_password;
	
	private Context mContext = this;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		initClick();
	}

	private void initView() {
		login_bt = (Button) findViewById(R.id.login_bt);
		regist_bt = (Button) findViewById(R.id.regist_bt);
		login_f_bt = (ImageButton) findViewById(R.id.login_f_bt);
		login_fg_bt = (ImageButton) findViewById(R.id.login_fg_bt);
		login_g_bt = (ImageButton) findViewById(R.id.login_g_bt);
		edt_registered_account=(EditText) findViewById(R.id.edt_registered_account);
		edt_registered_the_password=(EditText) findViewById(R.id.edt_registered_the_password);
	}

	private void initClick() {
		// 登录
		login_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String name=edt_registered_account.getText().toString();
				String paw=edt_registered_the_password.getText().toString();
				if(name==null||name.equals("")||paw==null||paw.equals("")){
					Toast.makeText(LoginActivity.this, "Input cannot be null", Toast.LENGTH_SHORT).show();
				}else{
					initLogin(name,paw);
				}
			}
		});

		// 注册
		regist_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);
			}
		});

		// f
		login_f_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, "f登录", 1000).show();
			}
		});

		// fg
		login_fg_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, "fg登录", 1000).show();
			}
		});

		// g
		login_g_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, "g登录", 1000).show();
			}
		});
	}
	private void initLogin(String email,String password) {
		LoginPostHttp loginHttp = new LoginPostHttp(this);
		loginHttp.doPostAsync(email, password);
	}
}
