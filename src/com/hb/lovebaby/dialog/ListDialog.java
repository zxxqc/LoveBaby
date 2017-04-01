/**
 * 
 */
package com.hb.lovebaby.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hb.lovebaby.R;


public class ListDialog extends Dialog {
	
	private TextView dialog_list_title;
	
	private LinearLayout dialog_list_layout;
	
	private ListView dialog_list_listview;
	
	private ArrayAdapter<String> listAdapter;
	
	private OnSelectItemListener mOnSelectItemListener;

	public ListDialog(Context context, String title, String[] dates) {
		super(context);
		// TODO Auto-generated constructor stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_list);
		
		dialog_list_title = (TextView) findViewById(R.id.dialog_list_title);
		dialog_list_title.setText(title);
		dialog_list_layout = (LinearLayout) findViewById(R.id.dialog_list_layout);
		dialog_list_listview = (ListView) findViewById(R.id.dialog_list_listview);
		
		// ��ȡ��Ļ���
		Window window = getWindow();
		Display display = window.getWindowManager().getDefaultDisplay();
		@SuppressWarnings("deprecation")
		double width = (double) display.getWidth();
		LayoutParams lp = dialog_list_layout.getLayoutParams();
		lp.width = (int) (width/5.0*4.0);
		dialog_list_layout.setLayoutParams(lp);
		
		listAdapter = new ArrayAdapter<String>(context, R.layout.dialog_list_item, R.id.set_liv_name, dates);
		dialog_list_listview.setAdapter(listAdapter);
		
		dialog_list_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				ListView listview = (ListView) arg0;
				String item = (String) listview.getItemAtPosition(arg2);
				if(mOnSelectItemListener!=null){
					mOnSelectItemListener.OnSelectItem(ListDialog.this, item);
				}
			}
		});
	}
	
	public interface OnSelectItemListener {
		void OnSelectItem(Dialog dialog, String item);
	}

	/*
	 * ���⹫��������Activityʵ��
	 */
	public void setOnSelectItemListener(OnSelectItemListener callBack) {
		mOnSelectItemListener = callBack;
	}

}
