/**
 * 
 */
package com.hb.lovebaby.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.annotation.SuppressLint;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * @author 左明洪
 *
 *         2016年11月16日
 */
public class MyUtil {

	/**
	 * 设置ListView的高度 让ListView不显示滚动条 直接显示全部内容
	 * 
	 * @param lv
	 */
	public static void setListViewHeight(ListView lv) {
		ListAdapter la = lv.getAdapter();
		if (null == la) {
			return;
		}
		// calculate height of all items.
		int h = 0;
		final int cnt = la.getCount();
		for (int i = 0; i < cnt; i++) {
			View item = la.getView(i, null, lv);
			item.measure(0, 0);
			h += item.getMeasuredHeight();
		}
		// reset ListView height
		ViewGroup.LayoutParams lp = lv.getLayoutParams();
		lp.height = h + (lv.getDividerHeight() * (cnt - 1));
		lv.setLayoutParams(lp);
	}

	@SuppressLint("SimpleDateFormat")
	public static String formatDateEh(long date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);
		int number = calendar.get(Calendar.DAY_OF_WEEK);// 星期表示1-7，是从星期日开始，
		String[] str = { "", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", };
		String stt = sdf.format(calendar.getTime()) + " On " + str[number];
		return stt;
	}

	public static String longToMinute(long time) {
		// 1m = 1000毫秒 Date date = new Date(time);
		String str = "";
		long hour = time / (60 * 60 * 1000);
		long minute = (time - hour * 60 * 60 * 1000) / (60 * 1000);
		String mt = minute + "";
		if (minute < 10) {
			mt = "0" + mt;
		}
		long second = (time - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000;
		String sd = second + "";
		if (second < 10) {
			sd = "0" + sd;
		}
		if (hour > 0) {
			str = hour + ":" + mt + ":" + sd;
		} else {
			str = mt + ":" + sd;
		}
		return str;
	}

	/**
	 * 获取sd卡根目录
	 * @return
	 */
	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		}
		String dir = sdDir.toString();
		return dir;

	}
	
	/**
	 * 创建路径
	 * @param dir
	 */
	public static void makeDir(File dir) {
		if(! dir.getParentFile().exists()) {
			makeDir(dir.getParentFile());
		}
		dir.mkdir();
	}
}
