package com.hb.lovebaby.modle;

public class BabyDynamicMessage {
	private String id;

	public String headImgUrl;

	public String from;

	public String content;

	public String nMinAgo;

	public String tailImgUrl;

	public BabyDynamicMessage(String id, String headImgResId, String from,
			String content, String nMinAgo, String tailImgResId) {
		super();
		this.id = id;
		this.headImgUrl = headImgResId;
		this.from = from;
		this.content = content;
		this.nMinAgo = nMinAgo;
		this.tailImgUrl = tailImgResId;
	}
}
