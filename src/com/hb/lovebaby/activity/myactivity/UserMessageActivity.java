package com.hb.lovebaby.activity.myactivity;

import org.json.JSONException;
import org.json.JSONObject;

import com.hb.lovebaby.LBApplication;
import com.hb.lovebaby.R;
import com.hb.lovebaby.share.ShareUserInfo;
import com.hb.lovebaby.util.OkManager;
import com.hb.lovebaby.util.OkManager.Func4;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserMessageActivity extends FragmentActivity implements OnClickListener{
	private Context mContext;
	private ImageButton btn_back_activity;
	private TextView tv_title_activity;
	
	private RelativeLayout re1;
	private RelativeLayout re2;
	private RelativeLayout re3;
	private RelativeLayout re4;
	private RelativeLayout re5;
	private RelativeLayout re6;
	
	private ImageView img_user;
	private TextView tv_name;
	private TextView tv_genter;
	private TextView tv_birthday;
	private TextView tv_region;
	private TextView tv_signature;
	
	private OkManager mOkManager;
	ShareUserInfo shareUserInfo ;
	String accesskey ;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.usermessagelayout);
		mContext=this;
		mOkManager=OkManager.getInstance(this);
		shareUserInfo = new ShareUserInfo(this);
		accesskey=shareUserInfo.getAccessKey();
		initView();
		initEvent();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		tv_title_activity.setText("Personal infomation");
		mOkManager.asyncJsonObject(LBApplication.url+"/account/userInfo?user_id="+shareUserInfo.getID(), new Func4() {
			
			@Override
			public void onResponse(JSONObject jsonObject) {
				// TODO Auto-generated method stub
				try {
					String ret=jsonObject.getString("ret");
//					Log.i("tag", jsonObject.toString());
					if(ret.equals("SUCCESS")){
						JSONObject data=jsonObject.getJSONObject("data");
						String name=data.getString("name");
						tv_name.setText(name);
						if(data.has("birthday")){
							String birthday=data.getString("birthday");
							tv_birthday.setText(birthday);
						}
						if(data.has("region")){
							String region=data.getString("region");
							tv_region.setText(region);
						}
						if(data.has("signature")){
							String signature=data.getString("signature");
							tv_signature.setText(signature);
						}
						if(data.has("avatar")){
							JSONObject object=data.getJSONObject("avatar");
							String origin=object.getString("origin");
							String originUrl=object.getString("originUrl");
							String thumbnailUrl=object.getString("thumbnailUrl");
							Picasso.with(mContext).load(originUrl).noFade().placeholder(R.drawable.acti_audio_progress_bg).error(R.drawable.acti_audio_progress_bg)
							.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(img_user);
						}
						
						tv_genter.setText("暂无");
						
					}else{
						Toast.makeText(mContext, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				Toast.makeText(mContext, jsonObject.toString(), Toast.LENGTH_SHORT).show();
			}
		}, "UserMessageActivity", accesskey);
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		btn_back_activity.setOnClickListener(this);
		re1.setOnClickListener(this);
		re2.setOnClickListener(this);
		re3.setOnClickListener(this);
		re4.setOnClickListener(this);
		re5.setOnClickListener(this);
		re6.setOnClickListener(this);
	}

	private void initView() {
		// TODO Auto-generated method stub
		btn_back_activity=(ImageButton) findViewById(R.id.btn_back_activity);
		tv_title_activity=(TextView) findViewById(R.id.tv_title_activity);
		re1=(RelativeLayout) findViewById(R.id.re1);
		re2=(RelativeLayout) findViewById(R.id.re2);
		re3=(RelativeLayout) findViewById(R.id.re3);
		re4=(RelativeLayout) findViewById(R.id.re4);
		re5=(RelativeLayout) findViewById(R.id.re5);
		re6=(RelativeLayout) findViewById(R.id.re6);
		img_user=(ImageView) findViewById(R.id.img_user);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_genter=(TextView) findViewById(R.id.tv_genter);
		tv_birthday=(TextView) findViewById(R.id.tv_birthday);
		tv_region=(TextView) findViewById(R.id.tv_region);
		tv_signature=(TextView) findViewById(R.id.tv_signature);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_back_activity:
			((Activity) mContext).finish();
			break;
		case R.id.re1:
			break;
		case R.id.re2:
			break;
		case R.id.re3:
			break;
		case R.id.re4:
			break;
		case R.id.re5:
			break;
		case R.id.re6:
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mOkManager.cancel("UserMessageActivity");
	}
}
