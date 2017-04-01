package com.hb.lovebaby.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.lovebaby.R;
import com.hb.lovebaby.dialog.PromptDialog;
import com.hb.lovebaby.dialog.PromptDialog.OnDialogClickListener;
import com.hb.lovebaby.util.StringUtils;
import com.hb.lovebaby.view.MyActionBar;

public class RelativesInfoActivity extends BascActivity  {
	
	private View relationship;
	
	private View nickname;
	
	private View personalInfo;
	
	private View permission;
	
	private View delete;
	private String type;
	private TextView relationship_tv;
	private MyActionBar actionbar;
	private RelativesInfoActivity _this;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.relatives_info);
		_this=this;
		initViews();
	}
	
	private void initViews() {		
		relationship = findViewById(R.id.relationship);
		actionbar=(MyActionBar) findViewById(R.id.action_bar);
		relationship_tv=(TextView) findViewById(R.id.relationship_tv);
		actionbar.setRightText(getResources().getString(R.string.alter),new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		type=getIntent().getStringExtra("type");
		relationship.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RelativesInfoActivity.this, RelativesRelationshipActivity.class);
				startActivity(intent);
			}
		});
		
		nickname = findViewById(R.id.nickname);
		nickname.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RelativesInfoActivity.this, RelativesNicknameActivity.class);
				startActivity(intent);
			}
		});
		
		personalInfo = findViewById(R.id.personal_info);
		personalInfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RelativesInfoActivity.this, PersonalInfoActivity.class);
				startActivity(intent);
			}
		});
		
		permission = findViewById(R.id.permission);
		permission.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(RelativesInfoActivity.this, PermissionsActivity.class);
				startActivity(intent);
			}
		});
		delete = findViewById(R.id.delete);
		if (!StringUtils.isEmpty(type)&&(type.equals("mother")||type.equals("father"))) {
			delete.setVisibility(View.GONE);
		}else{
			delete.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					PromptDialog dialog=new PromptDialog(_this, getResources().getString(R.string.prompt), 
							getResources().getString(R.string.sure_delete_relation_left)+relationship_tv.getText().toString().trim()
							+getResources().getString(R.string.sure_delete_relation_right), "");
					dialog.show();
					dialog.setOnClickListener(new OnDialogClickListener() {
						
						@Override
						public void onClickConfirm(PromptDialog dialog) {
							// TODO Auto-generated method stub
							dialog.cancel();
							Toast.makeText(_this, "sure", Toast.LENGTH_SHORT).show();
							finish();
						}
						
						@Override
						public void onClickCancel(PromptDialog dialog) {
							// TODO Auto-generated method stub
							dialog.cancel();
							Toast.makeText(_this, "cancel", Toast.LENGTH_SHORT).show();
							finish();
						}
					});
				}
			});
		}
	}
	
}
