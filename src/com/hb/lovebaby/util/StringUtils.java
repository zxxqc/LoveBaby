/*************************************************************************
 *  
 *  Copyright (C) 2013 SuZhou Raipeng Information Technology co., LTD.
 * 
 *                       All rights reserved.
 *
 *************************************************************************/
package com.hb.lovebaby.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @date: 2013-11-14 上午11:23:54
 * 
 * @email: tchen@raipeng.com
 * 
 * @version: V1.0
 * 
 * @description:
 * 
 */
public class StringUtils {

	public static boolean isMobilePhone(String phone) {//
		Pattern pattern = Pattern
				.compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[6-8])|(18[0-9]))\\d{8}$");
		Matcher matcher = pattern.matcher(phone);
		return matcher.matches();
	}

	public static boolean isEmpty(String str) {
		boolean isEmpty = false;
		if (null == str || str.equals("") || str.equals("null")
				|| str.trim().length() == 0) {
			isEmpty = true;
		}
		return isEmpty;
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param fileName
	 * @return 文件后缀名
	 */
	public static String getFileType(String fileName) {
		if (fileName != null) {
			int typeIndex = fileName.lastIndexOf(".");
			if (typeIndex != -1) {
				String fileType = fileName.substring(typeIndex + 1)
						.toLowerCase();
				return fileType;
			}
		}
		return "";
	}

	/**
	 * 根据后缀名判断是否是图片文件
	 * 
	 * @param type
	 * @return 是否是图片结果true or false
	 */
	public static boolean isImage(String fileName) {
		Boolean isImage = false;
		if (fileName != null) {
			int typeIndex = fileName.lastIndexOf(".");
			if (typeIndex != -1) {
				String type = fileName.substring(typeIndex + 1).toLowerCase();

				if (type != null
						&& (type.equals("jpg") || type.equals("gif")
								|| type.equals("png") || type.equals("jpeg")
								|| type.equals("bmp") || type.equals("wbmp")
								|| type.equals("ico") || type.equals("jpe"))) {
					isImage = true;

				}

			}
		}
		return isImage;

	}

	/**
	 * 判断非空字段是否相同
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEqual(String str1, String str2) {
		boolean isEqual = false;
		if (!isEmpty(str1) && !isEmpty(str2)) {
			if (str1.equals(str2)) {
				isEqual = true;
			}
		}
		return isEqual;
	}
}
