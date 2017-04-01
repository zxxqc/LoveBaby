package com.hb.lovebaby.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.hb.lovebaby.R;
import com.hb.lovebaby.util.Constants;
import com.hb.lovebaby.view.MyActionBar;

public class PersonalInfoSignatureActivity extends BascActivity {
	
	private static final int MAX_SIGN_LENGTH = 60;
	
	private MyActionBar actionBar;
	
	private EditText signEditText;
	
	private TextView signCharCount;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_info_signature);
		initViews();
	}
	
	private void initViews() {
		actionBar = (MyActionBar) findViewById(R.id.action_bar);
		actionBar.setOnActionButtonClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		actionBar.setRightText(getResources().getString(R.string.save),new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String signtex=signEditText.getText().toString();
				Intent intent=new Intent();
				intent.putExtra("signtex", signtex);
				setResult(Constants.RESULTCODE, intent);
				finish();
			}
		});
		signEditText = (EditText) findViewById(R.id.signature);
		signEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				int len = signEditText.getText().length();
				signCharCount.setText(len + "/" + MAX_SIGN_LENGTH);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// do nothing
			}
		});
		
		signCharCount = (TextView) findViewById(R.id.signature_char_count);
		signCharCount.setText(signEditText.getText().length() + "/" + MAX_SIGN_LENGTH);
		
	}
}
