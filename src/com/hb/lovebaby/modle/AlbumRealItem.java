package com.hb.lovebaby.modle;

public class AlbumRealItem extends AlbumItem {
	
	private String desc;
	
	private String countStr;
	
	private int titleColor;
	
	public AlbumRealItem(String name, String desc, String countStr, int titleColor) {
		super(name);
		this.desc = desc;
		this.countStr = countStr;
		this.titleColor = titleColor;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCountStr() {
		return countStr;
	}

	public void setCountStr(String countStr) {
		this.countStr = countStr;
	}

	public int getTitleColor() {
		return titleColor;
	}

	public void setTitleColor(int titleColor) {
		this.titleColor = titleColor;
	}
	
	
}
