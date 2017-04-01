package com.hb.lovebaby.modle;

public class MemoItem {

	public int imgResId;
	
	public int iconResId;
	
	public String title;
	
	public String desc;
	
	public MemoItem(int imgResId, int iconResId, String title, String desc) {
		this.imgResId = imgResId;
		this.iconResId = iconResId;
		this.title = title;
		this.desc = desc;
	}
}
