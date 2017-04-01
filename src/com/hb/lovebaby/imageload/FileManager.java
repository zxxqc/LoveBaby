package com.hb.lovebaby.imageload;


public class FileManager {

	public static String getSaveFilePath() {
		if (CommonUtil.hasSDCard()) {
			return CommonUtil.getRootFilePath() + "mykunshan/files/";
		} else {
			return CommonUtil.getRootFilePath() + "mykunshan/files";
		}
	}
}
