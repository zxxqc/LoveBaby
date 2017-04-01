package com.hb.lovebaby.activity.myactivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddBadyActivity extends Activity implements OnClickListener {
	private ImageView img_back;
	
	private Intent intent;

	private ImageView rb_1;
	private ImageView rb_2;
	private ImageView rb_3;
	
	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	
	private EditText ed_name;
	private EditText ed_birthday;
	
	private int isSelect=0;
	
	private TextView tv_send;
	
	private OkManager mOkManager;
	
	private Calendar calendar;
	private SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	
	ShareUserInfo shareUserInfo ;
	String accesskey ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_bady_activitylayout);
		mOkManager=OkManager.getInstance(this);
		shareUserInfo = new ShareUserInfo(this);
		accesskey=shareUserInfo.getAccessKey();
		calendar=Calendar.getInstance();
		intent=getIntent();
		initView();
		initEvent();
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		img_back.setOnClickListener(this);
		rb_1.setOnClickListener(this);
		rb_2.setOnClickListener(this);
		rb_3.setOnClickListener(this);
		tv_send.setOnClickListener(this);
		ed_birthday.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
//					ed_birthday.setText("");
					DatePickerDialog dialog=new DatePickerDialog(AddBadyActivity.this, new MyDate(),calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH 
));
					dialog.show();
					break;

				default:
					break;
				}
				return true;
			}
		});
	}
	
	class MyDate implements OnDateSetListener{

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			// TODO Auto-generated method stub
			ed_birthday.setText(tolong(year+"-"+(monthOfYear+1)+"-"+dayOfMonth)+"");
//			Toast.makeText(AddBadyActivity.this, year+"-"+(monthOfYear+1)+"-"+dayOfMonth, Toast.LENGTH_SHORT).show();
		}
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		img_back = (ImageView) findViewById(R.id.img_back);
		rb_1 = (ImageView) findViewById(R.id.rb_1);
		rb_2 = (ImageView) findViewById(R.id.rb_2);
		rb_3 = (ImageView) findViewById(R.id.rb_3);
		tv1=(TextView) findViewById(R.id.tv1);
		tv2=(TextView) findViewById(R.id.tv2);
		tv3=(TextView) findViewById(R.id.tv3);
		tv_send=(TextView) findViewById(R.id.tv_send);
		ed_name=(EditText) findViewById(R.id.ed_name);
		ed_birthday=(EditText) findViewById(R.id.ed_birthday);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.rb_1:
			isSelect=0;
			selectBackgroud(0);
			break;
		case R.id.rb_2:
			isSelect=1;
			selectBackgroud(1);
			break;
		case R.id.rb_3:
			isSelect=2;
			selectBackgroud(2);
			break;
		case R.id.tv_send:
			String name=ed_name.getText().toString();
			String birthday=ed_birthday.getText().toString();
			String gender=intent.getStringExtra("gender");
			if(name==null||name.equals("")||birthday==null||birthday.equals("")){
				Toast.makeText(AddBadyActivity.this, "Input cannot be null", Toast.LENGTH_SHORT).show();
			}else{
				if(isSelect==0){
					postBady(name,gender,birthday,"mother");
				}else if(isSelect==1){
					postBady(name,gender,birthday,"father");
				}else if(isSelect==2){
					postBady(name,gender,birthday,"other");
				}
				
			}
			
			break;
		default:
			break;
		}
	}

	private void postBady(String name, String gender, String birthday, String relationship) {
		// TODO Auto-generated method stub
		Map<String,String> map=new HashMap<String,String>();
		map.put("name", name);
		map.put("gender", gender);
		map.put("birthday", birthday);
		map.put("relationship", relationship);
		mOkManager.postKeyValuePairAsync(LBApplication.url, map, new Func4() {
			
			@Override
			public void onResponse(JSONObject jsonObject) {
				// TODO Auto-generated method stub
				String ret;
				try {
					ret = jsonObject.getString("ret");
					if(ret.equals("SUCCESS")){
						JSONObject data=jsonObject.getJSONObject("data");
						boolean ok=data.getBoolean("ok");
						if(ok){
							Toast.makeText(AddBadyActivity.this, "Add success", Toast.LENGTH_SHORT).show();
							AddBadyActivity.this.finish();
						}else{
							Toast.makeText(AddBadyActivity.this, "Add failed", Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(AddBadyActivity.this, "Add failed", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, "AddBadyActivity",accesskey,true);
	}

	public void selectBackgroud(int i) {
		switch (i) {
		case 0:
			rb_1.setImageResource(R.drawable.qr_checked);
			rb_2.setImageResource(R.drawable.qr_unchecked);
			rb_3.setImageResource(R.drawable.qr_unchecked);
			tv1.setTextColor(Color.parseColor("#000000"));
			tv2.setTextColor(Color.parseColor("#EFEFEF"));
			tv3.setTextColor(Color.parseColor("#EFEFEF"));
			break;
		case 1:
			rb_1.setImageResource(R.drawable.qr_unchecked);
			rb_2.setImageResource(R.drawable.qr_checked);
			rb_3.setImageResource(R.drawable.qr_unchecked);
			
			tv1.setTextColor(Color.parseColor("#EFEFEF"));
			tv2.setTextColor(Color.parseColor("#000000"));
			tv3.setTextColor(Color.parseColor("#EFEFEF"));
			break;
		case 2:
			rb_1.setImageResource(R.drawable.qr_unchecked);
			rb_2.setImageResource(R.drawable.qr_unchecked);
			rb_3.setImageResource(R.drawable.qr_checked);
			
			tv1.setTextColor(Color.parseColor("#EFEFEF"));
			tv2.setTextColor(Color.parseColor("#EFEFEF"));
			tv3.setTextColor(Color.parseColor("#000000"));
			break;

		default:
			break;
		}
	}
	
	private long tolong(String st)  
	{  
	    SimpleDateFormat  s=new SimpleDateFormat("yyyy-MM-dd");  
	    try {  
	        return s.parse(st).getTime();  
	    } catch (ParseException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	        return 0;  
	    }  
	}  
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mOkManager.cancel("AddBadyActivity");
	}
}
