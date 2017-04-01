package com.hb.lovebaby.activity.growth;

import java.io.Serializable;

public class Bean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String Title;
	private String Desc;  
	private String Phone;  
	private String Time;
	public  Bean(String title,String desc,String phone,String time) {
		this.Title=title;
		this.Desc=desc;
		this.Phone=phone;
		this.Time=time;
	}
	public Bean(String title,String desc,String phone) {

	}
	
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDesc() {
		return Desc;
	}
	public void setDesc(String desc) {
		Desc = desc;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}  

}
