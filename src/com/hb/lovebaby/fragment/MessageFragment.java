package com.hb.lovebaby.fragment;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hb.lovebaby.R;
import com.hb.lovebaby.activity.BabyDynamicMessageActivity;
import com.hb.lovebaby.activity.PraiseMessageActivity;
import com.hb.lovebaby.activity.ReplyMessageActivity;
import com.hb.lovebaby.activity.SystemMessageActivity;

public class MessageFragment extends Fragment {

	private LinearLayout llSystemMessage, llBabyDynamic, llReply, llPraise;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_message, null);
		initView(view);
		initClick();
		initData();
		return view;
	}

	private void initView(View view) {
		llSystemMessage = (LinearLayout) view
				.findViewById(R.id.ll_message_system_message);
		llBabyDynamic = (LinearLayout) view
				.findViewById(R.id.ll_message_baby_dynamic);
		llReply = (LinearLayout) view.findViewById(R.id.ll_message_reply);
		llPraise = (LinearLayout) view.findViewById(R.id.ll_message_praise);
	}

	private void initData() {
	}

	private void initClick() {
		llSystemMessage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),
						SystemMessageActivity.class);
				startActivity(intent);
			}
		});
		llSystemMessage.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {
				OperationDialog();
				return false;
			}
		});
		llBabyDynamic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),
						BabyDynamicMessageActivity.class);
				startActivity(intent);
			}
		});
		llBabyDynamic.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {
				OperationDialog();
				return false;
			}
		});
		llReply.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),
						ReplyMessageActivity.class);
				startActivity(intent);
			}
		});
		llReply.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {
				OperationDialog();
				return false;
			}
		});
		llPraise.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),
						PraiseMessageActivity.class);
				startActivity(intent);
			}
		});
		llPraise.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {
				OperationDialog();
				return false;
			}
		});
	}

	private void promptDialog() {
		Dialog setHeadDialog;
		View mDialogView;

		setHeadDialog = new Builder(getActivity()).create();
		setHeadDialog.show();
		mDialogView = View.inflate(getActivity(),
				R.layout.dialog_prompt_layout, null);

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

		setHeadDialog = new Builder(getActivity()).create();
		setHeadDialog.show();
		mDialogView = View.inflate(getActivity(),
				R.layout.dialog_operation_layout, null);

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
