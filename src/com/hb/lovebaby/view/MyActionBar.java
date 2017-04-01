package com.hb.lovebaby.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hb.lovebaby.R;

@SuppressLint("NewApi")
public class MyActionBar extends RelativeLayout {
	
	// Attributes
	private String title;
	
	private String actionText;
	
	private Drawable actionDrawable;
	
	private boolean hasBackBtn;
	
	private String backBtnText;
	
	private boolean backBtnIcon;
	
	// Views
	private Button actionBtn;
	
	private Button backBtn;
	private Button rightBtn;
	private	TextView titleTextView ;
	public MyActionBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		setAttrs(context, attrs);
		
		initViews();
	}
	
	public void setOnActionButtonClickListener(View.OnClickListener listener) {
		actionBtn.setOnClickListener(listener);
	}

	private void setAttrs(Context context, AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyActionBar);
		
		this.title = a.getString(R.styleable.MyActionBar_title);
		this.actionText = a.getString(R.styleable.MyActionBar_action_text);
		this.actionDrawable = a.getDrawable(R.styleable.MyActionBar_action_drawable);
		this.hasBackBtn = a.getBoolean(R.styleable.MyActionBar_has_back_btn, true);
		this.backBtnIcon = a.getBoolean(R.styleable.MyActionBar_back_btn_icon, true);
		this.backBtnText = a.getString(R.styleable.MyActionBar_back_btn_text);
		
		a.recycle();
	}
	
	@SuppressLint("NewApi")
	private void initViews() {
		// Back button
		backBtn = new Button(getContext());
		backBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
		if (backBtnText == null || backBtnText.isEmpty()) {
			backBtnText=getResources().getString(R.string.back);
			backBtn.setText(backBtnText);
		} else {
			backBtn.setText(backBtnText);
		}
		if (backBtnIcon) {
			backBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.back_bn_style, 0, 0, 0);
		}
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_START, 3);
		backBtn.setLayoutParams(params);
		if (hasBackBtn) {
			backBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Context context = getContext();
					if (context instanceof Activity) {
						((Activity) context).finish();
					}
				}
			});
		} else {
			backBtn.setVisibility(INVISIBLE);
		}
		backBtn.setPadding(0, 0, 0, 0);
		addView(backBtn);
		
		// Title
		titleTextView= new TextView(getContext());
		titleTextView.setBackgroundColor(getResources().getColor(R.color.transparent));
		titleTextView.setText(title);
		titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		titleTextView.setTextColor(getResources().getColor(R.color.white));
		
		params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT, -1);
		titleTextView.setLayoutParams(params);
		addView(titleTextView);
		
		// Action button
		actionBtn = new Button(getContext());
		actionBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
		if (actionDrawable != null) {
			actionBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, actionDrawable, null);
		}
		if (actionText != null && ! actionText.isEmpty()) {
			actionBtn.setText(actionText);
		}
		if ((actionText == null || actionText.isEmpty()) && actionDrawable == null ) {
			actionBtn.setVisibility(INVISIBLE);
		}
		params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_END);
		params.addRule(RelativeLayout.CENTER_VERTICAL);
		actionBtn.setLayoutParams(params);
		actionBtn.setPadding(0, 0, 0, 0);
		addView(actionBtn);
	}
	
	public void setRightText(String text,OnClickListener click){
		rightBtn = new Button(getContext());
		rightBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
		rightBtn.setText(text);
			backBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.back_bn_style, 0, 0, 0);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 3);
		rightBtn.setLayoutParams(params);
		rightBtn.setOnClickListener(click);
		rightBtn.setPadding(0, 0, 0, 0);
		addView(rightBtn);
	}
	public void setTitle(String titlt_txt){
		titleTextView.setText(titlt_txt);
	}
	
}
