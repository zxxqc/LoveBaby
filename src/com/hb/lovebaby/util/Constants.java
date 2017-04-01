package com.hb.lovebaby.util;

import java.io.File;

import android.os.Environment;

public class Constants {
	public static final String BASE_SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ File.separator;
public static final int RESULTCODE=0x001;
public static final int RequestCode=0x002;
public static final int RequestBloodCode=0x003;
public static final int RequestXingZuoCode=0x004;
public static final int RequestWeight=0x005;
public static final String BASE_UPLOAD_IMAGE_PATH = BASE_SDCARD_PATH + "baby/Images/Upload/";

}
