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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class AddMyBabyInvitationCodeActivity extends Activity implements OnClickListener{

	private TextView tvTitle;
	private ImageButton btnBack;
	private EditText edt_add_my_baby_invitation_code_code;
	private Button btn_add_my_baby_invitation_code_validation;
	private OkManager okManager;
	
	ShareUserInfo shareUserInfo ;
	String accesskey ;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_my_baby_invitation_code);
		okManager=OkManager.getInstance(this);
		shareUserInfo = new ShareUserInfo(this);
		accesskey=shareUserInfo.getAccessKey();
		initView();
		initData();
		initClick();
	}

	private void initView() {
		tvTitle = (TextView) findViewById(R.id.tv_title_activity);
		btnBack = (ImageButton) findViewById(R.id.btn_back_activity);
		edt_add_my_baby_invitation_code_code=(EditText) findViewById(R.id.edt_add_my_baby_invitation_code_code);
		btn_add_my_baby_invitation_code_validation=(Button) findViewById(R.id.btn_add_my_baby_invitation_code_validation);

	}
	
	private void initData() {
		tvTitle.setText(getString(R.string.add_my_baby_enter_invitation_code_title));
	}

	private void initClick() {
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		btn_add_my_baby_invitation_code_validation.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_add_my_baby_invitation_code_validation:
			String code=edt_add_my_baby_invitation_code_code.getText().toString();
			if(code==null||code.equals("")){
				Toast.makeText(AddMyBabyInvitationCodeActivity.this, "Input cannot be null", Toast.LENGTH_SHORT).show();
			}else{
				postCode(code);
			}
			break;

		default:
			break;
		}
	}

	private void postCode(String code) {
		// TODO Auto-generated method stub
		Map<String,String> map=new HashMap<String,String>();
		map.put("invitation_code", code);
		okManager.postKeyValuePairAsync(LBApplication.url+"/relatives/acceptInvitation", map, new Func4() {
			
			@Override
			public void onResponse(JSONObject jsonObject) {
				// TODO Auto-generated method stub
				try {
//					String ret=jsonObject.getString("ret");
					Log.i("tag", jsonObject.getString("ret"));
					Toast.makeText(AddMyBabyInvitationCodeActivity.this, jsonObject.getString("ret"), Toast.LENGTH_SHORT).show();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, "AddMyBabyInvitationCodeActivity", accesskey, true);
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		okManager.cancel("AddMyBabyInvitationCodeActivity");
	}
}
