/**
 * 
 */
package com.hb.lovebaby.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

/**
 * @author ������
 *
 * 2016��10��4��
 */
public class BascActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		super.onCreate(savedInstanceState);
	}

}
