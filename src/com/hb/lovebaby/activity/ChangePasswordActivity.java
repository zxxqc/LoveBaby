package com.hb.lovebaby.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.hb.lovebaby.LBApplication;
import com.hb.lovebaby.R;
import com.hb.lovebaby.share.ShareUserInfo;
import com.hb.lovebaby.util.OkManager;
import com.hb.lovebaby.util.OkManager.Func4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePasswordActivity extends Activity {

	private TextView tvTitle;
	private ImageButton btnBack;
	private TextView tvSave;

	private EditText edtOldPassword, edtNewPassword, edtConfirmPassword;
	
	private OkManager okManager;
	ShareUserInfo shareUserInfo ;
	String accesskey ;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		okManager=OkManager.getInstance(this);
		shareUserInfo = new ShareUserInfo(this);
		accesskey=shareUserInfo.getAccessKey();
		initViews();
		initData();
		initClick();
	}

	private void initViews() {
		tvTitle = (TextView) findViewById(R.id.tv_title_activity);
		btnBack = (ImageButton) findViewById(R.id.btn_back_activity);
		tvSave = (TextView) findViewById(R.id.tv_title_send);

		edtOldPassword = (EditText) findViewById(R.id.edt_change_password_old_password);
		edtNewPassword = (EditText) findViewById(R.id.edt_change_password_new_password);
		edtConfirmPassword = (EditText) findViewById(R.id.edt_change_password_confirm_password);
	}

	private void initData() {
		tvTitle.setText(getString(R.string.bind_new_email_title));
		tvSave.setText(getString(R.string.change_password_save));
		tvSave.setVisibility(View.VISIBLE);
	}

	private void initClick() {
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		tvSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				String oldPassword=edtOldPassword.getText().toString();
				String newPassword=edtNewPassword.getText().toString();
				final String confirmPassword=edtConfirmPassword.getText().toString();
				if(oldPassword==null||oldPassword.equals("")||newPassword==null||newPassword.equals("")||confirmPassword==null||confirmPassword.equals("")){
					Toast.makeText(ChangePasswordActivity.this, "Input cannot be null", Toast.LENGTH_SHORT).show();
				}else{
					if(!newPassword.equals(confirmPassword)){
						Toast.makeText(ChangePasswordActivity.this, "Two input inconsistency", Toast.LENGTH_SHORT).show();
					}else{
						Map<String,String> map=new HashMap<String,String>();
						map.put("new_password", newPassword);
						map.put("old_password", oldPassword);
						okManager.postKeyValuePairAsync(LBApplication.url+"/account/password", map, new Func4() {
							
							@Override
							public void onResponse(JSONObject jsonObject) {
								// TODO Auto-generated method stub
								try {
									String ret=jsonObject.getString("ret");
									if(ret.equals("SUCCESS")){
										ShareUserInfo shareUserInfo = new ShareUserInfo(ChangePasswordActivity.this);
										shareUserInfo.setPassword(confirmPassword);
//										shareUserInfo.setUserName("");
										Intent intent=new Intent(ChangePasswordActivity.this,LoginActivity.class);
										ChangePasswordActivity.this.startActivity(intent);
										ChangePasswordActivity.this.finish();
									}else{
										Toast.makeText(ChangePasswordActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								Log.i("tag", jsonObject.toString());
//								Toast.makeText(ChangePasswordActivity.this, jsonObject.toString(), Toast.LENGTH_SHORT).show();
							}
						}, "ChangePasswordActivity", accesskey,true);
					}
				}
			}
		});
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		okManager.cancel("ChangePasswordActivity");
	}

}
