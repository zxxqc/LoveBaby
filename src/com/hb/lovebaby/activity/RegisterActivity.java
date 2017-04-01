package com.hb.lovebaby.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.hb.lovebaby.LBApplication;
import com.hb.lovebaby.R;
import com.hb.lovebaby.util.OkManager;
import com.hb.lovebaby.util.OkManager.Func4;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private TextView tvTitle;
	private ImageButton btnBack;

	private EditText edtAccount, edtPssword, edtConfirmPasswrod, edtName;

	private Button btnEnter;
	private OkManager okManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		okManager=OkManager.getInstance(this);
		initViews();
		initData();
		initClick();
	}

	private void initViews() {
		tvTitle = (TextView) findViewById(R.id.tv_title_activity);
		btnBack = (ImageButton) findViewById(R.id.btn_back_activity);
		edtAccount = (EditText) findViewById(R.id.edt_registered_account);
		edtPssword = (EditText) findViewById(R.id.edt_registered_the_password);
		edtConfirmPasswrod = (EditText) findViewById(R.id.edt_registered_confirm_password);
		edtName = (EditText) findViewById(R.id.edt_registered_the_name);

		btnEnter = (Button) findViewById(R.id.btn_registered_complete_registration);

	}

	private void initData() {
		tvTitle.setText(getString(R.string.registered_title));
	}

	private void initClick() {
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		btnEnter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (checkAccount()) {
					registered();
				}
			}
		});
	}

	private void registered() {
//		RegisteredHttp registeredHttp = new RegisteredHttp(this);
//		registeredHttp.registered(edtAccount.getText().toString(), edtPssword
//				.getText().toString(), edtName.getText().toString(), "false");
		
		String email=edtAccount.getText().toString();
		String password=edtPssword.getText().toString();
		String name=edtName.getText().toString();
		String isReset="false";
		String confirmPasswrod=edtConfirmPasswrod.getText().toString();
		if(email==null||email.equals("")||password==null||password.equals("")||name==null||name.equals("")
				||confirmPasswrod==null||confirmPasswrod.equals("")){
			Toast.makeText(RegisterActivity.this, "Input cannot be null", Toast.LENGTH_SHORT).show();
		}else{
			if(!confirmPasswrod.equals(password)){
				Toast.makeText(RegisterActivity.this, "Two password input inconsistent", Toast.LENGTH_SHORT).show();
			}else{
				Map<String,String> map=new HashMap<String,String>();
				map.put("email", email);
				map.put("password", password);
				map.put("name", name);
				map.put("reset_password", isReset);
				okManager.postKeyValuePairAsync(LBApplication.url+"/register/email", map, new Func4() {
					
					@Override
					public void onResponse(JSONObject jsonObject) {
						// TODO Auto-generated method stub
						String ret;
						try {
							ret = jsonObject.getString("ret");
							if(ret.equals("SUCCESS")){
								Toast.makeText(RegisterActivity.this, "login was successful", Toast.LENGTH_SHORT).show();
								Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
								RegisterActivity.this.startActivity(intent);
								RegisterActivity.this.finish();
							}else{
								Toast.makeText(RegisterActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Log.i("tag", jsonObject.toString());
					}
				}, "RegisterActivity", "",false);
			}
		}
	}

	@SuppressLint("NewApi")
	public boolean checkAccount() {
		if (edtAccount.getText().toString().trim().isEmpty()) {
			edtAccount.setError(getString(R.string.registered_account_is_null));
			edtAccount.requestFocus();
			return false;
		}
		if (edtPssword.getText().toString().trim().isEmpty()) {
			edtPssword
					.setError(getString(R.string.registered_password_is_null));
			edtPssword.requestFocus();
			return false;
		}
		if (edtConfirmPasswrod.getText().toString().trim().isEmpty()) {
			edtConfirmPasswrod
					.setError(getString(R.string.registered_password_different));
			edtConfirmPasswrod.requestFocus();
			return false;
		}

		if (!edtPssword.getText().toString()
				.equals(edtConfirmPasswrod.getText().toString())) {
			edtConfirmPasswrod
					.setError(getString(R.string.registered_confirm_password_is_null));
			edtConfirmPasswrod.requestFocus();
			return false;
		}
		if (edtName.getText().toString().trim().isEmpty()) {
			edtName.setError(getString(R.string.registered_name_is_null));
			edtName.requestFocus();
			return false;
		}
		return true;
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
	}

}
