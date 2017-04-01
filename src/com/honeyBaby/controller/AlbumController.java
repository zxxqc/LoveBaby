package com.honeyBaby.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.Images.Media;

import com.honeyBaby.temp.AlbumModel;
import com.honeyBaby.temp.PhotoModel;
import com.honeyBaby.temp.VideoModel;

public class AlbumController {

	private ContentResolver resolver;

	public AlbumController(Context context) {
		resolver = context.getContentResolver();
	}

	/** ��ȡ�����Ƭ�б� */
	public List<PhotoModel> getCurrent() {
		Cursor cursor = resolver.query(Media.EXTERNAL_CONTENT_URI, new String[] { ImageColumns.DATA,
				ImageColumns.DATE_ADDED, ImageColumns.SIZE }, null, null, ImageColumns.DATE_ADDED);
		if (cursor == null || !cursor.moveToNext())
			return new ArrayList<PhotoModel>();
		List<PhotoModel> photos = new ArrayList<PhotoModel>();
		cursor.moveToLast();
		do {
			if (cursor.getLong(cursor.getColumnIndex(ImageColumns.SIZE)) > 1024 * 10) {
				PhotoModel photoModel = new PhotoModel();
				photoModel.setOriginalPath(cursor.getString(cursor.getColumnIndex(ImageColumns.DATA)));
				photos.add(photoModel);
			}
		} while (cursor.moveToPrevious());
		return photos;
	}
	
	/**
	 * ��ȡ��Ƶ
	 * @return
	 */
	public List<VideoModel> getVideoCurrent() {
		List<VideoModel> videos = new ArrayList<VideoModel>();
		Uri originalUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
		Cursor cursor = resolver.query(originalUri, null, null, null, null);
		if (cursor == null) {
			return videos;
		}
		while (cursor.moveToNext()) {
			VideoModel bean = new VideoModel();
			bean.set_id(cursor.getLong(cursor.getColumnIndex("_ID")));
			bean.setName(cursor.getString(cursor.getColumnIndex("_display_name")));// ��Ƶ����
			bean.setPath(cursor.getString(cursor.getColumnIndex("_data")));// ·��
			bean.setWidth(cursor.getInt(cursor.getColumnIndex("width")));// ��Ƶ��
			bean.setHeight(cursor.getInt(cursor.getColumnIndex("height")));// ��Ƶ��
			bean.setDuration(cursor.getLong(cursor.getColumnIndex("duration")));// ʱ��
			videos.add(bean);

		}
//		Cursor cursor = resolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[] { ImageColumns.DATA,
//				ImageColumns.DATE_ADDED, ImageColumns.SIZE }, null, null, ImageColumns.DATE_ADDED);
//		if (cursor == null || !cursor.moveToNext())
//			return new ArrayList<PhotoModel>();
//		List<PhotoModel> photos = new ArrayList<PhotoModel>();
//		cursor.moveToLast();
//		do {
//			if (cursor.getLong(cursor.getColumnIndex(ImageColumns.SIZE)) > 1024 * 10) {
//				PhotoModel photoModel = new PhotoModel();
//				photoModel.setOriginalPath(cursor.getString(cursor.getColumnIndex(ImageColumns.DATA)));
////				photoModel.setTime(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)));
//				
//				try {
//					MediaPlayer mediaPlayer = new MediaPlayer();
//					mediaPlayer.setDataSource(photoModel.getOriginalPath());
//					mediaPlayer.prepare();
//					photoModel.setTime(mediaPlayer.getDuration());
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				photos.add(photoModel);
//			}
//		} while (cursor.moveToPrevious());
		return videos;
	}

	/** ��ȡ��������б� */
	public List<AlbumModel> getAlbums() {
		List<AlbumModel> albums = new ArrayList<AlbumModel>();
		Map<String, AlbumModel> map = new HashMap<String, AlbumModel>();
		Cursor cursor = resolver.query(Media.EXTERNAL_CONTENT_URI, new String[] { ImageColumns.DATA,
				ImageColumns.BUCKET_DISPLAY_NAME, ImageColumns.SIZE }, null, null, null);
		if (cursor == null || !cursor.moveToNext())
			return new ArrayList<AlbumModel>();
		cursor.moveToLast();
		AlbumModel current = new AlbumModel("All photos", 0, cursor.getString(cursor.getColumnIndex(ImageColumns.DATA)), true); // "�����Ƭ"���
		albums.add(current);
		do {
			if (cursor.getInt(cursor.getColumnIndex(ImageColumns.SIZE)) < 1024 * 10)
				continue;

			current.increaseCount();
			String name = cursor.getString(cursor.getColumnIndex(ImageColumns.BUCKET_DISPLAY_NAME));
			if (map.keySet().contains(name))
				map.get(name).increaseCount();
			else {
				AlbumModel album = new AlbumModel(name, 1, cursor.getString(cursor.getColumnIndex(ImageColumns.DATA)));
				map.put(name, album);
				albums.add(album);
			}
		} while (cursor.moveToPrevious());
		return albums;
	}

	/** ��ȡ��Ӧ����µ���Ƭ */
	public List<PhotoModel> getAlbum(String name) {
		Cursor cursor = resolver.query(Media.EXTERNAL_CONTENT_URI, new String[] { ImageColumns.BUCKET_DISPLAY_NAME,
				ImageColumns.DATA, ImageColumns.DATE_ADDED, ImageColumns.SIZE }, "bucket_display_name = ?",
				new String[] { name }, ImageColumns.DATE_ADDED);
		if (cursor == null || !cursor.moveToNext())
			return new ArrayList<PhotoModel>();
		List<PhotoModel> photos = new ArrayList<PhotoModel>();
		cursor.moveToLast();
		do {
			if (cursor.getLong(cursor.getColumnIndex(ImageColumns.SIZE)) > 1024 * 10) {
				PhotoModel photoModel = new PhotoModel();
				photoModel.setOriginalPath(cursor.getString(cursor.getColumnIndex(ImageColumns.DATA)));
				photos.add(photoModel);
			}
		} while (cursor.moveToPrevious());
		return photos;
	}
}
