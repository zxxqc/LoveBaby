package com.hb.lovebaby.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.hb.lovebaby.activity.PhotoSelectorActivity.OnLocalReccentListener;
import com.hb.lovebaby.util.ActivityRequestCode;
import com.hb.lovebaby.util.CommonUtils;
import com.honeyBaby.domain.PhotoSelectorDomain;
import com.honeyBaby.temp.PhotoModel;

public class PhotoPreviewActivity extends BasePhotoPreviewActivity implements
		OnLocalReccentListener {

	private PhotoSelectorDomain photoSelectorDomain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		photoSelectorDomain = new PhotoSelectorDomain(getApplicationContext());
		init(getIntent().getExtras());
	}

	@SuppressWarnings("unchecked")
	protected void init(Bundle extras) {
		if (extras == null)
			return;
		this.requestCode = extras.getInt("requestCode");
		this.selected = (ArrayList<PhotoModel>) extras.getSerializable("selected");
		if (this.selected == null) {
			this.selected = new ArrayList<PhotoModel>();
		}
		this.current = extras.getInt("position", 0);
		switch (this.requestCode) {
		case ActivityRequestCode.PREVIEW_ALL_PICTURE: 
		{
			String albumName = extras.getString("album"); // ���
			if (!CommonUtils.isNull(albumName)
					&& albumName.equals(PhotoSelectorActivity.RECCENT_PHOTO)) {
				photoSelectorDomain.getReccent(this);
			} else {
				photoSelectorDomain.getAlbum(albumName, this);
			}
		}
			break;
		case ActivityRequestCode.PREVIEW_SELECTED_PICTURE: 
		{
			// ֻ��ѡ�е�ͼƬ����Ԥ��
			this.photos = (List<PhotoModel>)this.selected.clone();
			bindData();
		}
			break;
		default:
			break;
		}
	}

	@Override
	public void onPhotoLoaded(List<PhotoModel> photos) {
		this.photos = photos;
		bindData(); // ���½���
	}

}
