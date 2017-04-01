package com.hb.lovebaby.modle;

public abstract class AlbumItem {
	
	protected String name;
	
	public AlbumItem(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
