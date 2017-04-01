package com.hb.lovebaby.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.hb.lovebaby.R;

public class PromptDialog extends Dialog {
	private String title;
	private String desc;
	protected String item;
	private TextView dialog_prompt_desc,dialog_prompt;
	private Button dialog_prompt_cancel_bt;
	private Button dialog_prompt_confirm_bt;
	private OnDialogClickListener listener;
	public PromptDialog(Context context, String title, String desc, String item) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_prompt);
		
		this.title = title;
		this.desc = desc;
		this.item = item;
		dialog_prompt=(TextView) findViewById(R.id.dialog_prompt);
		dialog_prompt_confirm_bt = (Button)findViewById(R.id.dialog_prompt_confirm_bt);
		dialog_prompt_cancel_bt = (Button)findViewById(R.id.dialog_prompt_cancel_bt);
		dialog_prompt_desc = (TextView)findViewById(R.id.dialog_prompt_desc);
		dialog_prompt_desc.setText(desc);
		dialog_prompt.setText(title);
		dialog_prompt_confirm_bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (listener != null) {
					listener.onClickConfirm(PromptDialog.this);
				}
			}
		});
		dialog_prompt_cancel_bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (listener != null) {
					listener.onClickCancel(PromptDialog.this);
				}
			}
		});
	}
	
	public PromptDialog setOnClickListener(OnDialogClickListener listener) {
		this.listener = listener;
		return this;
	}
	
	public interface OnDialogClickListener{
		/*
		 * 确认
		 */
		public void onClickConfirm(PromptDialog dialog);
		/*
		 * 取消
		 */
		public void onClickCancel(PromptDialog dialog);
	}
	

}
