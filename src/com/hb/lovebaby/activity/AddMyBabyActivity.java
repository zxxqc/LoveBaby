package com.hb.lovebaby.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import com.hb.lovebaby.R;
import com.hb.lovebaby.activity.myactivity.AddBadyActivity;

public class AddMyBabyActivity extends Activity implements OnClickListener{

	private TextView tvTitle;
	private ImageButton btnBack;

	private TextView tvEnterCode;
	
	
	//性别选择
	private Button btn_add_my_baby_boy;//男宝宝
	private Button btn_add_my_baby_girl;//女宝宝

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_my_baby);
		initView();
		initData();
		initClick();
	}

	private void initView() {
		tvTitle = (TextView) findViewById(R.id.tv_title_activity);
		btnBack = (ImageButton) findViewById(R.id.btn_back_activity);
		tvEnterCode = (TextView) findViewById(R.id.tv_add_my_baby_enter);
		btn_add_my_baby_boy=(Button) findViewById(R.id.btn_add_my_baby_boy);
		btn_add_my_baby_girl=(Button) findViewById(R.id.btn_add_my_baby_girl);
	}

	private void initData() {
		tvTitle.setText(getString(R.string.add_my_baby_title));
	}

	private void initClick() {
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		tvEnterCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(AddMyBabyActivity.this,
						AddMyBabyInvitationCodeActivity.class);//接受邀请
				startActivity(intent);
			}
		});
		
		btn_add_my_baby_boy.setOnClickListener(this);
		btn_add_my_baby_girl.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_add_my_baby_boy:
			Intent intent1=new Intent(AddMyBabyActivity.this,AddBadyActivity.class);
			intent1.putExtra("gender", "M");
			startActivity(intent1);
			break;
		case R.id.btn_add_my_baby_girl:
			Intent intent2=new Intent(AddMyBabyActivity.this,AddBadyActivity.class);
			intent2.putExtra("gender", "F");
			startActivity(intent2);
			break;
		default:
			break;
		}
	}
}
