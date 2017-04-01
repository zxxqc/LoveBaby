package com.hb.lovebaby.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.hb.lovebaby.R;
import com.hb.lovebaby.adapter.BabyNynamicMessageAdapter;
import com.hb.lovebaby.modle.BabyDynamicMessage;

public class ReplyMessageActivity extends Activity {

	private TextView tvTitle;
	private ImageButton btnBack;

	private ListView listView;

	private List<BabyDynamicMessage> list;

	private BabyNynamicMessageAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_system_message);
		initViews();
		initData();
		initClick();
	}

	private void initViews() {
		tvTitle = (TextView) findViewById(R.id.tv_title_activity);
		btnBack = (ImageButton) findViewById(R.id.btn_back_activity);
	}

	private void initData() {
		tvTitle.setText(getString(R.string.reply_title));
		list = new ArrayList<BabyDynamicMessage>();
		populateData();
		adapter = new BabyNynamicMessageAdapter(this, list);
		listView = (ListView) findViewById(R.id.lv_system_message_listview);
		listView.setAdapter(adapter);
	}

	private void populateData() {
		for (int i = 0; i < 50; i++) {
			String content = "It's too funny";
			if (i % 5 == 0) {
				content = "New users falware haven't get, $5 carton of photo card free of charge";
			}
			BabyDynamicMessage m = new BabyDynamicMessage("id","", "community", content,
					"11 min ago", "");
			list.add(m);
		}
	}

	private void initClick() {
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				OperationDialog();
			}
		});
	}

	private void promptDialog() {
		Dialog setHeadDialog;
		View mDialogView;

		setHeadDialog = new Builder(this).create();
		setHeadDialog.show();
		mDialogView = View.inflate(this, R.layout.dialog_prompt_layout, null);

		setHeadDialog.getWindow().setContentView(mDialogView);

		setHeadDialog.setCanceledOnTouchOutside(true);

		TextView tvCancle = (TextView) setHeadDialog
				.findViewById(R.id.tv_prompt_cancle);
		TextView tvConfirmTextView = (TextView) setHeadDialog
				.findViewById(R.id.tv_prompt_confirm);
	}

	private void OperationDialog() {
		final Dialog setHeadDialog;
		View mDialogView;

		setHeadDialog = new Builder(this).create();
		setHeadDialog.show();
		mDialogView = View
				.inflate(this, R.layout.dialog_operation_layout, null);

		setHeadDialog.getWindow().setContentView(mDialogView);

		setHeadDialog.setCanceledOnTouchOutside(true);
		TextView tvEmptyMessage = (TextView) setHeadDialog
				.findViewById(R.id.tv_operation_empty_message);
		TextView tvCancle = (TextView) setHeadDialog
				.findViewById(R.id.tv_operation_cancle);
		tvEmptyMessage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setHeadDialog.dismiss();
				promptDialog();
			}
		});
	}
}
