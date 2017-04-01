package com.hb.lovebaby.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.hb.lovebaby.LBApplication;
import com.hb.lovebaby.R;
import com.hb.lovebaby.language.LanguageSettingUtil;
import com.hb.lovebaby.language.SetLanguageUtil;
import com.hb.lovebaby.language.SwitchLanguageObservable;

public class SetLanguage extends Activity implements OnClickListener {

	private LanguageSettingUtil languageSetting;
	private TextView switchCh, switchEn;
	private SwitchLanguageObservable obs;

	private SetLanguageUtil setLanguageUtil;
	LBApplication chartShopApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_language);
		chartShopApplication = (LBApplication) getApplication();
		languageSetting = chartShopApplication.getLanguageSetting();
		obs = chartShopApplication.getSwitchLangObs();

		switchCh = (TextView) findViewById(R.id.switch_ch);
		switchEn = (TextView) findViewById(R.id.switch_zh_TW);
		switchCh.setOnClickListener(this);
		switchEn.setOnClickListener(this);
		initLanguage();
	}

	private void initLanguage() {
		setLanguageUtil = new SetLanguageUtil(this);
		setLanguageUtil.setLanguage(switchCh,
				R.string.tv_set_language_simplified_chinese);
		setLanguageUtil = new SetLanguageUtil(this);
		setLanguageUtil.setLanguage(switchEn,
				R.string.tv_set_language_traditional_chinese);
	}

	@Override
	protected void onDestroy() {
		if (setLanguageUtil.getObs() != null
				&& setLanguageUtil.getObser() != null)
			setLanguageUtil.getObs().deleteObserver(setLanguageUtil.getObser());
		super.onDestroy();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.switch_ch:
			languageSetting.saveLanguage("zh");
			LanguageSettingUtil.get().refreshLanguage();// 英语
			obs.notifyObservers();
			finish();
			break;

		case R.id.switch_zh_TW:
			languageSetting.saveLanguage("tw");
			LanguageSettingUtil.get().refreshLanguage();// 阿拉伯
			obs.notifyObservers();
			finish();
			break;

		default:
			break;
		}
	}

}

