package com.honeyBaby.domain;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.hb.lovebaby.activity.VideoSelectorActivity.OnLocalAlbumListener;
import com.hb.lovebaby.activity.VideoSelectorActivity.OnLocalReccentListener;
import com.honeyBaby.controller.AlbumController;
import com.honeyBaby.temp.AlbumModel;
import com.honeyBaby.temp.PhotoModel;
import com.honeyBaby.temp.VideoModel;

@SuppressLint("HandlerLeak")
public class VideoSelectorDomain {

	private AlbumController albumController;

	public VideoSelectorDomain(Context context) {
		albumController = new AlbumController(context);
	}

	public void getReccent(final OnLocalReccentListener listener) {
		final Handler handler = new Handler() {
			@SuppressWarnings("unchecked")
			@Override
			public void handleMessage(Message msg) {
				listener.onPhotoLoaded((List<VideoModel>) msg.obj);
			}
		};
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<VideoModel> photos = albumController.getVideoCurrent();
				Message msg = new Message();
				msg.obj = photos;
				handler.sendMessage(msg);
			}
		}).start();
	}

	/** ��ȡ����б� */
	public void updateAlbum(final OnLocalAlbumListener listener) {
		final Handler handler = new Handler() {
			@SuppressWarnings("unchecked")
			@Override
			public void handleMessage(Message msg) {
				listener.onAlbumLoaded((List<AlbumModel>) msg.obj);
			}
		};
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<AlbumModel> albums = albumController.getAlbums();
				Message msg = new Message();
				msg.obj = albums;
				handler.sendMessage(msg);
			}
		}).start();
	}

	/** ��ȡ��������µ�������Ƭ��Ϣ */
	public void getAlbum(final String name, final OnLocalReccentListener listener) {
		final Handler handler = new Handler() {
			@SuppressWarnings("unchecked")
			@Override
			public void handleMessage(Message msg) {
				listener.onPhotoLoaded((List<VideoModel>) msg.obj);
			}
		};
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<PhotoModel> photos = albumController.getAlbum(name);
				Message msg = new Message();
				msg.obj = photos;
				handler.sendMessage(msg);
			}
		}).start();
	}

}
