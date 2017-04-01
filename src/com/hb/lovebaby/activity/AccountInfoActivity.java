package com.hb.lovebaby.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hb.lovebaby.R;

public class AccountInfoActivity extends Activity {

	private TextView tvTitle;
	private ImageButton btnBack;

	private View email;
	private View changePassword;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_info);
		initViews();
		initData();
		initClick();
	}

	private void initViews() {
		tvTitle = (TextView) findViewById(R.id.tv_title_activity);
		btnBack = (ImageButton) findViewById(R.id.btn_back_activity);
		email = findViewById(R.id.ll_account_info_email);
		changePassword = findViewById(R.id.ll_account_info_password);
	}

	private void initData() {
		tvTitle.setText(getString(R.string.account_information_title));
	}

	private void initClick() {
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		email.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(AccountInfoActivity.this,
						BindNewEmailActivity.class);//邮箱修改
				startActivity(intent);
			}
		});
		changePassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(AccountInfoActivity.this,
						ChangePasswordActivity.class);//密码修改
				startActivity(intent);
			}
		});
		// phoneNumber.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// String[] items = new String[] {
		// getResources().getString(R.string.replace_phone_number),
		// getResources().getString(R.string.cancel) };
		// ListDialog listDialog = new ListDialog(
		// AccountInfoActivity.this, getResources().getString(
		// R.string.operation), items);
		// listDialog.setOnSelectItemListener(new OnSelectItemListener() {
		//
		// @Override
		// public void OnSelectItem(Dialog dialog, String item) {
		// // TODO Auto-generated method stub
		// dialog.cancel();
		// if (item.equals(getResources().getString(
		// R.string.replace_phone_number))) {
		// Intent intent = new Intent(
		// AccountInfoActivity.this,
		// BindNewPhoneNumberActivity.class);
		// startActivity(intent);
		// }
		// }
		// });
		// listDialog.show();
		// }
		// });
	}

}
