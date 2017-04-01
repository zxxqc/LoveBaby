package com.hb.lovebaby.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hb.lovebaby.R;
import com.hb.lovebaby.util.Constants;

/**
 * @author pei.zhu
 *体重身高
 */
public class HWActivity extends Activity {
	private Button main_back_bt,right_btn;
	private TextView title_tv;
	private HWActivity _this;
	private EditText height_et,weight_et;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hw);
		_this=this;
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		main_back_bt=(Button) findViewById(R.id.main_back_bt);
		main_back_bt.setText(getResources().getString(R.string.cancel));
		right_btn=(Button) findViewById(R.id.right_btn);
		right_btn.setVisibility(View.VISIBLE);
		right_btn.setText(getResources().getString(R.string.save));
		title_tv=(TextView) findViewById(R.id.title_tv);
		title_tv.setText(getResources().getString(R.string.add_growth_record));
		weight_et=(EditText) findViewById(R.id.weight_et);
		height_et=(EditText) findViewById(R.id.height_et);
		main_back_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		right_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.putExtra("height", height_et.getText().toString().trim());
				intent.putExtra("weight", weight_et.getText().toString().trim());
				setResult(Constants.RequestCode, intent);
				finish();
			}
		});
	}

}
