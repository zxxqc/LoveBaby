package com.hb.lovebaby.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;

/**
 * @date: 2013-11-8 上午9:57:52
 * 
 * @email: tchen@raipeng.com
 * 
 * @version: V1.0
 * 
 * @description:
 * 
 */
public class FileUtils {
	private static final String TAG = "FileUtils";
	private static FileNameGenerator fileNameGenerator = null;


	public static String generatorFileName(String fileName) {
		return fileNameGenerator.generate(fileName);
	}

	/**
	 * 拷贝文件
	 * 
	 * @param fromFile
	 * @param toFile
	 * @throws IOException
	 */
	public static void copyFile(File fromFile, String toFile)
			throws IOException {

		FileInputStream from = null;
		FileOutputStream to = null;
		try {
			from = new FileInputStream(fromFile);
			to = new FileOutputStream(toFile);
			byte[] buffer = new byte[1024];
			int bytesRead;

			while ((bytesRead = from.read(buffer)) != -1)
				to.write(buffer, 0, bytesRead); // write
		} finally {
			if (from != null)
				try {
					from.close();
				} catch (IOException e) {
					Log.e(TAG, "", e);
				}
			if (to != null)
				try {
					to.close();
				} catch (IOException e) {
					Log.e(TAG, "", e);
				}
		}
	}

	/**
	 * 创建文件
	 * 
	 * @param file
	 * @return
	 */
	public static File createNewFile(File file) {

		try {

			if (file.exists()) {
				return file;
			}

			File dir = file.getParentFile();
			if (!dir.exists()) {
				dir.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e) {
			Log.e(TAG, "", e);
			return null;
		}
		return file;
	}

	/**
	 * 创建文件
	 * 
	 * @param path
	 */
	public static File createNewFile(String path) {
		File file = new File(path);
		return createNewFile(file);
	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 */
	public static int deleteFile(String path) {
		File file = new File(path);
		return deleteFile(file);
	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 */
	public static int deleteFile(File file) {
		int resultCode = -1;
		if (!file.exists()) {
			Log.i("RP", file.getAbsolutePath() + " file not exist");
			resultCode = 0;
		}
		if (file.isFile()) {
			file.delete();
			Log.i("RP", file.getAbsolutePath() + " delete success");
			resultCode = 1;
		} else if (file.isDirectory()) {
			Log.i("RP", file.getAbsolutePath() + " file is directory");
			File files[] = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteFile(files[i]);
			}
		}
		return resultCode;
		// file.delete();
	}

	/**
	 * 获得文件名
	 * 
	 * @param path
	 * @return
	 */
	public static String getFileName(String path) {
		if (StringUtils.isEmpty(path)) {
			return null;
		}
		File f = new File(path);
		String name = f.getName();
		f = null;
		return name;
	}

	/**
	 * 创建文件夹
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean createDir(File dir) {
		try {
			if (!dir.exists()) {
				dir.mkdirs();
			}
			return true;
		} catch (Exception e) {
			Log.e(TAG, "create dir error", e);
			return false;
		}
	}

	/**
	 * 判断SD卡上的文件是否存在
	 */
	public static boolean isFileExist(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}

	/**
	 * 判断文件是否小于指定大小，单位为KB
	 * 
	 * @param file
	 * @param limit
	 * @return
	 */
	public static boolean isFileLowThan(File file, int limit) {
		int fileSize = 0;
		boolean success = false;
		try {
			FileInputStream fis = new FileInputStream(file);
			fileSize = fis.available();
			fis.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (fileSize < 1024) {
			success = true;
		} else if (fileSize < 1048576) {
			if ((fileSize / 1024) < limit) {
				success = true;
			}
		}
		return success;
	}

	public static long getFileSizes(File f) {
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSizes(flist[i]);
			} else {
				size = size + getFileSize(flist[i]);
			}

		}
		return size;
	}

	/**
	 * 获取指定文件大小
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static long getFileSize(File file) {
		long size = 0;
		FileInputStream fis = null;
		try {
			if (file.exists()) {

				fis = new FileInputStream(file);
				size = fis.available();
			} else {
				file.createNewFile();
				Log.e("获取文件大小", "文件不存在!");
			}
		} catch (Exception e) {
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return size;
	}

	/**
	 * 保存bitmap至本地
	 * 
	 * @param bitmap
	 */
	public static int saveTempBitmap(String name, Bitmap bmp) {
		int resultCode = -1;
		try {
			File root = new File(Constants.BASE_UPLOAD_IMAGE_PATH);
			if (!root.exists()) {
				root.mkdirs();
			}
			File f = new File(name);
			if (f.exists()) {
				f.delete();
			}
			f.createNewFile();
			FileOutputStream out = new FileOutputStream(f);
			bmp.compress(Bitmap.CompressFormat.JPEG, 80, out);
			out.flush();
			out.close();
			resultCode = 1;
		} catch (Exception e) {
			e.printStackTrace();
			resultCode = 2;
		}
		return resultCode;
	}

	/**
	 * 保存bitmap至本地
	 * 
	 * @param bitmap
	 */
	public static int saveBitmap(String name, Bitmap bmp) {
		int resultCode = -1;
		try {
			File root = new File(Constants.BASE_UPLOAD_IMAGE_PATH);
			if (!root.exists()) {
				root.mkdirs();
			}
			File f = new File(Constants.BASE_UPLOAD_IMAGE_PATH + name);
			if (f.exists()) {
				resultCode = 0;

			} else {
				f.createNewFile();
				FileOutputStream out = new FileOutputStream(f);
				bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
				out.flush();
				out.close();
				resultCode = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultCode = 2;
		}
		return resultCode;
	}

	/**
	 * 保存bitmap至本地
	 * 
	 * @param bitmap
	 */
	public static void saveUploadBitmap(String name, Bitmap bmp) {
		try {
			File root = new File(Constants.BASE_UPLOAD_IMAGE_PATH);
			if (!root.exists()) {
				root.mkdirs();
			}
			File f = new File(Constants.BASE_UPLOAD_IMAGE_PATH + name);
			if (f.exists()) {
				f.delete();
			}
			f.createNewFile();
			FileOutputStream out = new FileOutputStream(f);
			bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存bitmap至本地
	 * 
	 * @param bitmap
	 */
	public static int saveDrawableToLocal(Context context, int drawable,
			Bitmap bmp) {
		int resultCode = -1;
		try {
			File root = new File(Constants.BASE_UPLOAD_IMAGE_PATH);
			if (!root.exists()) {
				root.mkdirs();
			}
			File f = new File(Constants.BASE_UPLOAD_IMAGE_PATH
					+ "paizhao.jpg");
			if (f.exists()) {
				resultCode = 0;

			} else {
				f.createNewFile();
				FileOutputStream out = new FileOutputStream(f);
				bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
				out.flush();
				out.close();
				resultCode = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultCode = 2;
		}
		return resultCode;
	}

	public static void saveString(String name, String str) {
		try {
			File root = new File(Constants.BASE_UPLOAD_IMAGE_PATH);
			if (!root.exists()) {
				root.mkdirs();
			}
			File f = new File(Constants.BASE_UPLOAD_IMAGE_PATH + name);
			if (f.exists()) {
				f.delete();
			} 
				f.createNewFile();
				FileOutputStream out = new FileOutputStream(f);
				// bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
				out.write(str.getBytes());
				out.flush();
				out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param name
	 *            保存的文件名
	 * @param bmp
	 *            保存的源bitmap
	 * @return resultcode 结果码 1表示成功 2表示失败
	 * 
	 */
	@SuppressLint("NewApi")
	public static int saveFilterBitmap(String name, Bitmap bmp) {
		int resultCode = -1;
		try {
			File root = new File(Constants.BASE_UPLOAD_IMAGE_PATH);
			if (!root.exists()) {
				root.mkdirs();
			}
			File f = new File(Constants.BASE_UPLOAD_IMAGE_PATH + name);
			if (f.exists()) {
				f.delete();
			}
			f.createNewFile();
			FileOutputStream out = new FileOutputStream(f);
			bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
			resultCode = 1;
		} catch (Exception e) {
			e.printStackTrace();
			resultCode = 2;
		}
		return resultCode;
	}

	public static String fileName(String filePath) {

		filePath = filePath.substring(0, filePath.lastIndexOf("/"));

		filePath = filePath.substring(filePath.lastIndexOf("/") + 1,
				filePath.length());

		return filePath;
	}

	/**
	 * 保存文件内容到/data/data/com.raipeng.cloudalbum/目录下
	 * 
	 * @param c
	 * @param fileName
	 *            文件名称
	 * @param content
	 *            内容
	 */
	public static void writeFiles(Context c, String fileName, String content,
			int mode) throws Exception {
		// 打开文件获取输出流，文件不存在则自动创建
		FileOutputStream fos = c.openFileOutput(fileName, mode);
		fos.write(content.getBytes());
		fos.close();
	}

	/**
	 * 读取/data/data/com.raipeng.cloudalbum/目录下文件内容
	 * 
	 * @param c
	 * @param fileName
	 *            文件名称
	 * @return 返回文件内容
	 */
	public static String readFiles(Context c, String fileName) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileInputStream fis = c.openFileInput(fileName);
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, len);
		}
		String content = baos.toString();
		fis.close();
		baos.close();
		return content;
	}

	// 压缩图片
	public static Bitmap comSpressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 30) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置bads即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	/************ start 把聊天发送的图片压缩转化Base64 *****************/
	public static String comp(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 100) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	private static String compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return chatBitmapToString(bitmap);
	}

	/**
	 * 把bitmap转换成String
	 * 
	 * @param bitmap
	 * @return
	 */
	private static String chatBitmapToString(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 70, baos);
		byte[] b = baos.toByteArray();
		return Base64.encodeToString(b, Base64.DEFAULT);
	}
	/************ end 把聊天发送的图片转化Base64 *****************/
public static void creatDimens(double height ,double with){
	StringBuffer sb=new StringBuffer();
	double eveHeight=height/160;
	double eveWith=with/160;
	for(int i=1;i<=160;i++){
		double value=i*eveHeight;
		String ss="<dimen name=\"height_"+i+"_160\">"+String.format("%.2f", value)+"px</dimen>"+"\n";
		sb.append(ss);
	}
	for(int i=1;i<=160;i++){
		double values=i*eveWith;
		String ss="<dimen name=\"width_"+i+"_160\">"+String.format("%.2f", values)+"px</dimen>"+"\n";
		sb.append(ss);
	}
	
	FileUtils.saveString("dimen.txt",sb.toString());
}
}
