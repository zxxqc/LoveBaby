package com.hb.lovebaby.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hb.lovebaby.R;
import com.hb.lovebaby.adapter.BloodAdapter;
import com.hb.lovebaby.util.Constants;

/**
 * @author pei.zhu
 *血型，星座选择
 */
public class TypeChooseActivity extends Activity {
private int type;//1代表血型，2代表星座
private String types[];
private BloodAdapter mAdapter;
private TypeChooseActivity _this;
private ListView common_listview;
private Button main_back_bt,right_btn;
private TextView title_tv;
private Handler mHandler;
private int position;
private String content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_typechoose);
		_this=this;
		mHandler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				position=msg.what;
			}
		};
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		type=getIntent().getIntExtra("type", -1);
		content=getIntent().getStringExtra("content");
		common_listview=(ListView) findViewById(R.id.common_lv);
		main_back_bt=(Button) findViewById(R.id.main_back_bt);
		main_back_bt.setText(getResources().getString(R.string.cancel));
		main_back_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		right_btn=(Button) findViewById(R.id.right_btn);
		right_btn.setVisibility(View.VISIBLE);
		right_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent();
				intent.putExtra("result", types[position]);
				setResult(Constants.RESULTCODE, intent);
				finish();
			}
		});
		title_tv=(TextView) findViewById(R.id.title_tv);
		if (type==1) {//血型
			types=getResources().getStringArray(R.array.babyinfo_bloodtype_array);
			title_tv.setText(getResources().getString(R.string.blood));
		}else{	//星座
			types=getResources().getStringArray(R.array.constellation);
			title_tv.setText(getResources().getString(R.string.constellation));
		}
		mAdapter=new BloodAdapter(types, _this,mHandler,getPosition(content));
		common_listview.setAdapter(mAdapter);
	}
private int getPosition(String con){
	int tar=-1;
	for(int i=0;i<types.length;i++){
		if(con.equals(types[i])){
			tar=i;
		}
	}
	return tar;
}
	
}
