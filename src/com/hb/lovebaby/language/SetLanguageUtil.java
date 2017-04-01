package com.hb.lovebaby.language;

import android.app.Activity;
import android.widget.TextView;

import com.hb.lovebaby.LBApplication;
import com.hb.lovebaby.language.SwitchLanguageObserver.ObserverChange;

public class SetLanguageUtil {

	private SwitchLanguageObservable obs;
	private SwitchLanguageObserver obser;

	public SetLanguageUtil(Activity activity) {
		super();
		LBApplication app = (LBApplication) activity
				.getApplication();
		obs = app.getSwitchLangObs();
		obser = new SwitchLanguageObserver(obs);
	}

	public SwitchLanguageObservable getObs() {
		return obs;
	}

	public void setObs(SwitchLanguageObservable obs) {
		this.obs = obs;
	}

	public SwitchLanguageObserver getObser() {
		return obser;
	}

	public void setObser(SwitchLanguageObserver obser) {
		this.obser = obser;
	}

	public void setLanguage(final TextView tv, final int id) {
		obser.setOnObserverChange(new ObserverChange() {

			@Override
			public void observerChange() {
				tv.setText(id);
			}
		});
	}
}
