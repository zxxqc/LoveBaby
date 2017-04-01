package com.hb.lovebaby.modle;

public class BabyDynamic {

	private String headImageUrl,name,sex,isOpen;
	private String weight;
	private String height;
	private String recordDatetime;
	
	public BabyDynamic(String weight, String height, String recordDatetime) {
		super();
		this.weight = weight;
		this.height = height;
		this.recordDatetime = recordDatetime;
	}

	public BabyDynamic(String headImageUrl, String name, String sex,
			String isOpen) {
		super();
		this.headImageUrl = headImageUrl;
		this.name = name;
		this.sex = sex;
		this.isOpen = isOpen;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getRecordDatetime() {
		return recordDatetime;
	}

	public void setRecordDatetime(String recordDatetime) {
		this.recordDatetime = recordDatetime;
	}

	public String getHeadImageUrl() {
		return headImageUrl;
	}

	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	
}
