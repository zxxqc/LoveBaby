package com.hb.lovebaby.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hb.lovebaby.R;
import com.hb.lovebaby.adapter.BabyGrowthAdapter;
import com.hb.lovebaby.modle.BabyDynamic;
import com.hb.lovebaby.util.Constants;

/**
 * @author pei.zhu
 *
 */
public class BabyGrowthRecordActivity extends Activity {
	private Button main_back_bt,right_btn;
	private TextView title_tv;
	private BabyGrowthRecordActivity _this;
	private ListView mListView;
	private ArrayList<BabyDynamic> babys=new ArrayList<BabyDynamic>();
	private BabyGrowthAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baby_growth_record);
		_this=this;
		initViews();
	}
	private void initViews() {
		// TODO Auto-generated method stub
		main_back_bt=(Button) findViewById(R.id.main_back_bt);
		right_btn=(Button) findViewById(R.id.right_btn);
		mListView=(ListView) findViewById(R.id.common_lv);
		right_btn.setVisibility(View.VISIBLE);
		right_btn.setText("");
		Drawable drawable= getResources().getDrawable(R.drawable.add);
		/// 这一步必须要做,否则不会显示.
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		right_btn.setCompoundDrawables(null,null,drawable,null);
		title_tv=(TextView) findViewById(R.id.title_tv);
		title_tv.setText(getResources().getString(R.string.add_growth_record));
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
				Intent intent=new Intent(_this,HWActivity.class);
				startActivityForResult(intent, Constants.RequestWeight);
			}
		});
		for(int i=0;i<5;i++){
			BabyDynamic record=new BabyDynamic("6"+i,"16"+i,"2017-3-1"+i);
			babys.add(record);
		}
		setAdapter();
	}
private  void setAdapter(){
	if(null==mAdapter){
		mAdapter=new BabyGrowthAdapter(babys, _this);
		mListView.setAdapter(mAdapter);
	}else{
		mAdapter.refresh(babys);
	}
	
}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==Constants.RequestWeight) {
			String height=data.getExtras().getString("height");
			String weight=data.getExtras().getString("weight");
			BabyDynamic baby=new BabyDynamic(weight, height, "");
			babys.add(baby);
			setAdapter();
		}
	}

}
