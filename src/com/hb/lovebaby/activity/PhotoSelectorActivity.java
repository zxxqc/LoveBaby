package com.hb.lovebaby.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hb.lovebaby.R;
import com.hb.lovebaby.adapter.AlbumAdapter;
import com.hb.lovebaby.adapter.PhotoSelectorAdapter;
import com.hb.lovebaby.util.ActivityRequestCode;
import com.hb.lovebaby.util.AnimationUtil;
import com.hb.lovebaby.util.CommonUtils;
import com.hb.lovebaby.view.PhotoItem.onItemClickListener;
import com.hb.lovebaby.view.PhotoItem.onPhotoItemCheckedListener;
import com.honeyBaby.domain.PhotoSelectorDomain;
import com.honeyBaby.temp.AlbumModel;
import com.honeyBaby.temp.PhotoModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

@SuppressLint("NewApi")
public class PhotoSelectorActivity extends Activity implements
		onItemClickListener, onPhotoItemCheckedListener, OnItemClickListener,
		OnClickListener {
	
	public static final String RECCENT_PHOTO = "All photos";

	private GridView gvPhotos;
	private ListView lvAblum;
	private Button btnOk;
	private TextView tvAlbum, tvPreview, tvTitle, preview_bt;
	private PhotoSelectorDomain photoSelectorDomain;
	private PhotoSelectorAdapter photoAdapter;
	private AlbumAdapter albumAdapter;
	private RelativeLayout layoutAlbum;
	private ArrayList<PhotoModel> selected;
	// �ܵ���Ƭ��
	private int allCount;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		setContentView(R.layout.activity_photoselector);

		DisplayImageOptions defaultDisplayImageOptions = new DisplayImageOptions.Builder() //
				.considerExifParams(true) // ����ͼƬ����
				.resetViewBeforeLoading(true) // ����֮ǰ����ImageView
				.showImageOnLoading(R.drawable.ic_picture_loading) // ����ʱͼƬ����Ϊ��ɫ
				.showImageOnFail(R.drawable.ic_picture_loadfailed) // ����ʧ��ʱ��ʾ��ͼƬ
				.delayBeforeLoading(0) // ����֮ǰ���ӳ�ʱ��
				.build(); //
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.defaultDisplayImageOptions(defaultDisplayImageOptions)
				.memoryCacheExtraOptions(480, 800).threadPoolSize(5).build();
		ImageLoader.getInstance().init(config);

		photoSelectorDomain = new PhotoSelectorDomain(getApplicationContext());
		Bundle bundle = getIntent().getExtras();
		if(null!=bundle){
			selected = (ArrayList<PhotoModel>) bundle.getSerializable("selected");
		}
		if (selected == null) {
			selected = new ArrayList<PhotoModel>();
		}
		tvTitle = (TextView) findViewById(R.id.tv_title_lh);
		gvPhotos = (GridView) findViewById(R.id.gv_photos_ar);
		lvAblum = (ListView) findViewById(R.id.lv_ablum_ar);
		btnOk = (Button) findViewById(R.id.btn_right_lh);
		tvAlbum = (TextView) findViewById(R.id.tv_album_ar);
		tvPreview = (TextView) findViewById(R.id.tv_preview_ar);
		layoutAlbum = (RelativeLayout) findViewById(R.id.layout_album_ar);

		btnOk.setOnClickListener(this);
		tvAlbum.setOnClickListener(this);
		tvPreview.setOnClickListener(this);

		preview_bt = (TextView) findViewById(R.id.preview_bt);
		preview_bt.setOnClickListener(this);

		photoAdapter = new PhotoSelectorAdapter(getApplicationContext(),
				new ArrayList<PhotoModel>(), CommonUtils.getWidthPixels(this),
				this, this, this);
		gvPhotos.setAdapter(photoAdapter);

		albumAdapter = new AlbumAdapter(getApplicationContext(),
				new ArrayList<AlbumModel>());
		lvAblum.setAdapter(albumAdapter);
		lvAblum.setOnItemClickListener(this);

		findViewById(R.id.bv_back_lh).setOnClickListener(this); // ����

		photoSelectorDomain.getReccent(reccentListener); // ���������Ƭ
		photoSelectorDomain.updateAlbum(albumListener); // ���������Ϣ

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_right_lh)
			finish();
		else if (v.getId() == R.id.tv_album_ar)
			album();
		else if (v.getId() == R.id.tv_preview_ar)
			ok(); // ѡ����Ƭ
		else if (v.getId() == R.id.tv_camera_vc)
			catchPicture();
		else if (v.getId() == R.id.bv_back_lh)
			finish();
		else if (v.getId() == R.id.preview_bt)
			priview();
	}

	/** ���� */
	private void catchPicture() {
		CommonUtils.launchActivityForResult(this, new Intent(
				MediaStore.ACTION_IMAGE_CAPTURE), ActivityRequestCode.REQUEST_CAMERA);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}
		switch (resultCode) {
		case ActivityRequestCode.REQUEST_CAMERA:
		{
			PhotoModel photoModel = new PhotoModel(CommonUtils.query(
					getApplicationContext(), data.getData()));
			 selected.clear();
			if (!this.containPhoto(photoModel.getOriginalPath())) {
				selected.add(photoModel);
			}
		}
			break;
		case ActivityRequestCode.PREVIEW_ALL_PICTURE:
		{
			
		}
			break;
		case ActivityRequestCode.PREVIEW_SELECTED_PICTURE:
		{
			
		}
			break;

		default:
			break;
		}
		ok();
	}

	/** ��� */
	private void ok() {
		if (selected.isEmpty()) {
			setResult(RESULT_CANCELED);
		} else {
			Intent data = new Intent();
			Bundle bundle = new Bundle();
			bundle.putSerializable("selected", selected);
			data.putExtras(bundle);
			setResult(RESULT_OK, data);
		}
		finish();
	}

	/** Ԥ����Ƭ */
	private void priview() {
		Bundle bundle = new Bundle();
		bundle.putSerializable("selected", selected);
		bundle.putInt("requestCode", ActivityRequestCode.PREVIEW_SELECTED_PICTURE);
		Intent intent = new Intent(PhotoSelectorActivity.this,
				PhotoPreviewActivity.class);
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivityForResult(intent, ActivityRequestCode.PREVIEW_SELECTED_PICTURE);
	}

	private void album() {
		if (layoutAlbum.getVisibility() == View.GONE) {
			popAlbum();
		} else {
			hideAlbum();
		}
	}

	/** ��������б� */
	private void popAlbum() {
		layoutAlbum.setVisibility(View.VISIBLE);
		new AnimationUtil(getApplicationContext(), R.anim.translate_up_current)
				.setLinearInterpolator().startAnimation(layoutAlbum);
	}

	/** ��������б� */
	private void hideAlbum() {
		new AnimationUtil(getApplicationContext(), R.anim.translate_down)
				.setLinearInterpolator().startAnimation(layoutAlbum);
		layoutAlbum.setVisibility(View.GONE);
	}

	private boolean containPhoto(String path) {
		if (path == null) {
			return false;
		}
		for (PhotoModel model : this.selected) {
			String originalPath = model.getOriginalPath();
			if (originalPath == null) {
				continue;
			}
			if (path.equals(originalPath)) {
				return true;
			}
		}
		return false;
	}

	/** ���ѡ�е�ͼƬ */
	private void reset() {
		// selected.clear();
		tvPreview.setText("complete(" + selected.size() + "/" + allCount + ")");
		tvPreview.setEnabled(false);
		preview_bt.setEnabled(false);
		tvPreview.setBackground(getResources().getDrawable(
				R.drawable.bg_bt_disable_style));
		preview_bt.setBackground(getResources().getDrawable(
				R.drawable.bg_bt_disable_style));
	}

	@Override
	/** ����鿴��Ƭ */
	public void onItemClick(int position) {
		Bundle bundle = new Bundle();
		if (tvAlbum.getText().toString().equals(RECCENT_PHOTO))
			bundle.putInt("position", position - 1);
		else
			bundle.putInt("position", position);
		bundle.putInt("requestCode", ActivityRequestCode.PREVIEW_ALL_PICTURE);
		bundle.putString("album", tvAlbum.getText().toString());
		bundle.putSerializable("selected", selected);
		Intent intent = new Intent(PhotoSelectorActivity.this,
				PhotoPreviewActivity.class);
		intent.putExtras(bundle);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivityForResult(intent, ActivityRequestCode.PREVIEW_ALL_PICTURE);
	}

	@Override
	/** ��Ƭѡ��״̬�ı�֮�� */
	public void onCheckedChanged(PhotoModel photoModel,
			CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			if (!this.containPhoto(photoModel.getOriginalPath())) {
				selected.add(photoModel);
			}
			tvPreview.setEnabled(true);
			preview_bt.setEnabled(true);
			tvPreview.setBackground(getResources().getDrawable(
					R.drawable.bg_bt_enable_style));
			preview_bt.setBackground(getResources().getDrawable(
					R.drawable.bg_bt_enable_style));
		} else {
			selected.remove(photoModel);
		}
		tvPreview.setText("complete(" + selected.size() + "/" + allCount + ")"); // �޸�Ԥ������

		if (selected.isEmpty()) {
			tvPreview.setEnabled(false);
			tvPreview.setText("complete(" + selected.size() + "/" + allCount
					+ ")");
			tvPreview.setBackground(getResources().getDrawable(
					R.drawable.bg_bt_disable_style));
			preview_bt.setBackground(getResources().getDrawable(
					R.drawable.bg_bt_disable_style));
			preview_bt.setEnabled(false);
		}
	}

	@Override
	public void onBackPressed() {
		if (layoutAlbum.getVisibility() == View.VISIBLE) {
			hideAlbum();
		} else
			super.onBackPressed();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	/** ����б����¼� */
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		AlbumModel current = (AlbumModel) parent.getItemAtPosition(position);
		for (int i = 0; i < parent.getCount(); i++) {
			AlbumModel album = (AlbumModel) parent.getItemAtPosition(i);
			if (i == position)
				album.setCheck(true);
			else
				album.setCheck(false);
		}
		albumAdapter.notifyDataSetChanged();
		hideAlbum();
		tvAlbum.setText(current.getName());
		tvTitle.setText(current.getName());

		// ������Ƭ�б�
		if (current.getName().equals(RECCENT_PHOTO))
			photoSelectorDomain.getReccent(reccentListener);
		else
			photoSelectorDomain.getAlbum(current.getName(), reccentListener); // ��ȡѡ��������Ƭ
	}

	/** ��ȡ����ͼ����Ƭ�ص� */
	public interface OnLocalReccentListener {
		public void onPhotoLoaded(List<PhotoModel> photos);
	}

	/** ��ȡ���������Ϣ�ص� */
	public interface OnLocalAlbumListener {
		public void onAlbumLoaded(List<AlbumModel> albums);
	}

	private OnLocalAlbumListener albumListener = new OnLocalAlbumListener() {
		@Override
		public void onAlbumLoaded(List<AlbumModel> albums) {
			albumAdapter.update(albums);
		}
	};

	private OnLocalReccentListener reccentListener = new OnLocalReccentListener() {
		@Override
		public void onPhotoLoaded(List<PhotoModel> photos) {
			if (tvAlbum.getText().equals(RECCENT_PHOTO))
				photos.add(0, new PhotoModel());
			photoAdapter.update(photos);
			gvPhotos.smoothScrollToPosition(0); // ����������
			reset();
			if (photos.size() > 0) {
				allCount = photos.size() - 1;
			}
			tvPreview.setText("complete(" + selected.size() + "/" + allCount
					+ ")");
			if (selected.isEmpty()) {
				tvPreview.setEnabled(false);
				preview_bt.setEnabled(false);
				tvPreview.setBackground(getResources().getDrawable(
						R.drawable.bg_bt_disable_style));
				preview_bt.setBackground(getResources().getDrawable(
						R.drawable.bg_bt_disable_style));
			} else {
				tvPreview.setEnabled(true);
				preview_bt.setEnabled(true);
				tvPreview.setBackground(getResources().getDrawable(
						R.drawable.bg_bt_enable_style));
				preview_bt.setBackground(getResources().getDrawable(
						R.drawable.bg_bt_enable_style));
			}
		}
	};
}
