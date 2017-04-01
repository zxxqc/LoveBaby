package com.hb.lovebaby.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hb.lovebaby.R;
import com.hb.lovebaby.view.MyActionBar;

public class RelativesActivity extends BascActivity {
	
	private Button inviteBtn;
	
	private MyActionBar actionBar;
	
	private ViewGroup motherContainer;
	
	private ViewGroup fatherContainer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.relatives);
		
		initViews();

	}
	
	private void initViews() {
		actionBar = (MyActionBar) findViewById(R.id.action_bar);
		actionBar.setOnActionButtonClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RelativesActivity.this, RelativesSendInvitationActivity.class);
				intent.putExtra("type", "other");
				startActivity(intent);
			}
		});
		
		// Mother container
		motherContainer = (ViewGroup) findViewById(R.id.mother_container);
		View motherBox = getLayoutInflater().inflate(R.layout.relatives_invite_parent, motherContainer, false);
		motherContainer.addView(motherBox);
		motherContainer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RelativesActivity.this, RelativesSendInvitationActivity.class);
				intent.putExtra("type", "mother");
				startActivity(intent);
			}
		});
		
		// Father container
		fatherContainer = (ViewGroup) findViewById(R.id.father_container);
		View fatherBox = getLayoutInflater().inflate(R.layout.relatives_this_is_me, fatherContainer, false);
		fatherContainer.addView(fatherBox);
		fatherContainer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RelativesActivity.this, RelativesInfoActivity.class);
				intent.putExtra("type", "mother");
				startActivity(intent);	
			}
		});
		addOthers();
	}
	
	private void addOthers() {
		ViewGroup container = (ViewGroup) findViewById(R.id.other_relatives);
		View content_v = getLayoutInflater().inflate(R.layout.relatives_family, container, false);
		container.addView(content_v);
		String[] others = {
				"Grandmother1", "Grandma1", "Grandpa1", "Uncle1", "Aunt1", "Grandma2", "Granpa2", "Uncle2", "Aunt2",
				"Grandmother3", "Grandma3", "Grandpa3", "Uncle3", "Aunt3", "Grandma4"
		};
		
		int loopLen = (others.length - 1) / 2 + 1;
		for (int i = 0; i < loopLen; i++) {
			LinearLayout ll = new LinearLayout(this);
			
			// һ�� linear layout ��Ϊһ��
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			ll.setLayoutParams(params);
			container.addView(ll);
			
			// ���
			View v = getLayoutInflater().inflate(R.layout.relatives_other, ll, false);
			params = new LinearLayout.LayoutParams(
					0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
			v.setLayoutParams(params);
			Button btn = (Button) v.findViewById(R.id.relative_other_btn);
			final String text1 = others[i * 2];
			btn.setText(text1);
			btn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.i("debug", "button " + text1 + " clicked");
					Intent intent = new Intent(RelativesActivity.this, RelativesInfoActivity.class);
					startActivity(intent);
				}
			});
			ll.addView(v);
			
			// �ұ�
			v = getLayoutInflater().inflate(R.layout.relatives_other, ll, false);
			params = new LinearLayout.LayoutParams(
					0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
			v.setLayoutParams(params);
			ll.addView(v);
			if (i * 2 + 1 >= others.length) {
				v.setVisibility(View.INVISIBLE);
			} else {
				btn = (Button) v.findViewById(R.id.relative_other_btn);
				final String text2 = others[i * 2 + 1];
				btn.setText(text2);
				btn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Log.i("debug", "button " + text2 + " clicked");
						Intent intent = new Intent(RelativesActivity.this, RelativesSendInvitationActivity.class);
						startActivity(intent);
					}
				});
			}
			
		}
	}
}
