package com.hb.lovebaby.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.hb.lovebaby.LBApplication;
import com.hb.lovebaby.R;
import com.hb.lovebaby.activity.myactivity.AddBadyActivity;
import com.hb.lovebaby.share.ShareUserInfo;
import com.hb.lovebaby.util.OkManager;
import com.hb.lovebaby.util.OkManager.Func4;

public class BindNewEmailActivity extends Activity implements OnClickListener{

	private TextView tvTitle;
	private ImageButton btnBack;
	
	private EditText tv_account_info_new_email_1;
	private EditText tv_account_info_new_email_2;
	
	private Button btn_bind_new_email_confirm;
	
	private OkManager okManager;

	ShareUserInfo shareUserInfo ;
	String accesskey ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bind_new_email);
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
		tv_account_info_new_email_1=(EditText) findViewById(R.id.tv_account_info_new_email_1);
		tv_account_info_new_email_2=(EditText) findViewById(R.id.tv_account_info_new_email_2);
		btn_bind_new_email_confirm=(Button) findViewById(R.id.btn_bind_new_email_confirm);
	}

	private void initData() {
		tvTitle.setText(getString(R.string.bind_new_email_title));
	}

	private void initClick() {
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		btn_bind_new_email_confirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_bind_new_email_confirm:
			String email=tv_account_info_new_email_1.getText().toString().trim();
			String okemail=tv_account_info_new_email_2.getText().toString().trim();
			if(email==null||okemail==null||okemail.equals("")||email.equals("")){
				Toast.makeText(BindNewEmailActivity.this, "Input cannot be null", Toast.LENGTH_SHORT).show();
			}else{
				if(!email.equals(okemail)){
					Toast.makeText(BindNewEmailActivity.this, "Input inconsistency",Toast.LENGTH_SHORT).show();
				}else{
					Map<String,String> map=new HashMap<String, String>();
					map.put("email", okemail);
					okManager.postKeyValuePairAsync(LBApplication.url+"/account/email", map, new Func4() {
						
						@Override
						public void onResponse(JSONObject jsonObject) {
							// TODO Auto-generated method stub
							try {
								String ret=jsonObject.getString("ret");
								if(ret.equals("SUCCESS")){
									BindNewEmailActivity.this.finish();
									Toast.makeText(BindNewEmailActivity.this, "Modify success", Toast.LENGTH_SHORT).show();
								}else{
									Toast.makeText(BindNewEmailActivity.this, "Modify failed", Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Toast.makeText(BindNewEmailActivity.this, jsonObject.toString(), Toast.LENGTH_SHORT).show();
						}
					} , "BindNewEmailActivity",accesskey,true);
				}
			}
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		okManager.cancel("BindNewEmailActivity");
	}

}
