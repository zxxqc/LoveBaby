package com.hb.lovebaby.view;

import com.hb.lovebaby.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class MyDialog extends Dialog {
	
	private TextView takePhone;
	private TextView systemphone;
	public MyDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	protected MyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	public MyDialog(Context context) {
		super(context,R.style.dialog);//自定义对话框的样式
		// TODO Auto-generated constructor stub
		
		setCustomDialog();
	}

	private void setCustomDialog() {
		// TODO Auto-generated method stub
		View mView = LayoutInflater.from(getContext()).inflate(R.layout.mydialoglayout1, null);
		takePhone=(TextView) mView.findViewById(R.id.takePhone);
		systemphone=(TextView) mView.findViewById(R.id.systemphone);
		setSystemphone(systemphone);
		setTakePhone(takePhone);
		
		super.setContentView(mView, (new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT)));
	}

	public TextView getTakePhone() {
		return takePhone;
	}

	public void setTakePhone(TextView takePhone) {
		this.takePhone = takePhone;
	}

	public TextView getSystemphone() {
		return systemphone;
	}

	public void setSystemphone(TextView systemphone) {
		this.systemphone = systemphone;
	}
	
	

}
