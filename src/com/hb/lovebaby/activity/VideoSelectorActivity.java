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
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hb.lovebaby.R;
import com.hb.lovebaby.adapter.AlbumAdapter;
import com.hb.lovebaby.adapter.VideoSelectorAdapter;
import com.hb.lovebaby.util.AnimationUtil;
import com.hb.lovebaby.util.CommonUtils;
import com.hb.lovebaby.view.VideoItem.onItemClickListener;
import com.honeyBaby.domain.VideoSelectorDomain;
import com.honeyBaby.temp.AlbumModel;
import com.honeyBaby.temp.PhotoModel;
import com.honeyBaby.temp.VideoModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

@SuppressLint("NewApi")
public class VideoSelectorActivity extends Activity implements onItemClickListener,
		OnItemClickListener, OnClickListener {

	public static final int REQUEST_PHOTO = 0;
	private static final int REQUEST_CAMERA = 1;

	public static final String RECCENT_PHOTO = "All photos";

	private GridView gvPhotos;
	private ListView lvAblum;
	private Button btnOk;
	private TextView tvAlbum, tvPreview, tvTitle, preview_bt;
	private VideoSelectorDomain videoSelectorDomain;
	private VideoSelectorAdapter videoAdapter;
	private AlbumAdapter albumAdapter;
	private RelativeLayout layoutAlbum;
	private ArrayList<PhotoModel> selected;
	//�ܵ���Ƭ��
	private int allCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		setContentView(R.layout.activity_videoselector);

		DisplayImageOptions defaultDisplayImageOptions = new DisplayImageOptions.Builder() //
				.considerExifParams(true) // ����ͼƬ����
				.resetViewBeforeLoading(true) // ����֮ǰ����ImageView
				.showImageOnLoading(R.drawable.ic_picture_loading) // ����ʱͼƬ����Ϊ��ɫ
				.showImageOnFail(R.drawable.ic_picture_loadfailed) // ����ʧ��ʱ��ʾ��ͼƬ
				.delayBeforeLoading(0) // ����֮ǰ���ӳ�ʱ��
				.build(); //
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
				.defaultDisplayImageOptions(defaultDisplayImageOptions).memoryCacheExtraOptions(480, 800)
				.threadPoolSize(5).build();
		ImageLoader.getInstance().init(config);

		videoSelectorDomain = new VideoSelectorDomain(getApplicationContext());

		selected = new ArrayList<PhotoModel>();

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

		videoAdapter = new VideoSelectorAdapter(getApplicationContext(), new ArrayList<VideoModel>(), 
				CommonUtils.getWidthPixels(this), this, this);
		
		gvPhotos.setAdapter(videoAdapter);

		albumAdapter = new AlbumAdapter(getApplicationContext(), new ArrayList<AlbumModel>());
		lvAblum.setAdapter(albumAdapter);
		lvAblum.setOnItemClickListener(this);

		findViewById(R.id.bv_back_lh).setOnClickListener(this); // ����

		videoSelectorDomain.getReccent(reccentListener); // ���������Ƭ
		videoSelectorDomain.updateAlbum(albumListener); // ���������Ϣ
		
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
		else if (v.getId()==R.id.preview_bt)
			priview();
	}

	/** ¼�� */
	private void catchPicture() {
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);//����¼��ʱ��10��
		CommonUtils.launchActivityForResult(this, intent, REQUEST_CAMERA);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
			PhotoModel photoModel = new PhotoModel(CommonUtils.query(getApplicationContext(), data.getData()));
			selected.clear();
			selected.add(photoModel);
			ok();
		}
	}

	/** ��� */
	private void ok() {
		if (selected.isEmpty()) {
			setResult(RESULT_CANCELED);
		} else {
			Intent data = new Intent();
			Bundle bundle = new Bundle();
			bundle.putSerializable("photos", selected);
			data.putExtras(bundle);
			setResult(RESULT_OK, data);
		}
		finish();
	}

	/** Ԥ����Ƭ */
	private void priview() {
		Bundle bundle = new Bundle();
		bundle.putSerializable("photos", selected);
		CommonUtils.launchActivity(this, PhotoPreviewActivity.class, bundle);
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
		new AnimationUtil(getApplicationContext(), R.anim.translate_up_current).setLinearInterpolator().startAnimation(
				layoutAlbum);
	}

	/** ��������б� */
	private void hideAlbum() {
		new AnimationUtil(getApplicationContext(), R.anim.translate_down).setLinearInterpolator().startAnimation(
				layoutAlbum);
		layoutAlbum.setVisibility(View.GONE);
	}

	/** ���ѡ�е�ͼƬ */
	private void reset() {
		selected.clear();
		tvPreview.setText("complete("+selected.size()+"/"+allCount+")");
		tvPreview.setEnabled(false);
		preview_bt.setEnabled(false);
		tvPreview.setBackground(getResources().getDrawable(R.drawable.bg_bt_disable_style));
		preview_bt.setBackground(getResources().getDrawable(R.drawable.bg_bt_disable_style));
	}

	@Override
	/** ���������Ƶ */
	public void onItemClick(VideoModel video) {
		Bundle bundle = new Bundle();
		bundle.putSerializable("video", video);
		CommonUtils.launchActivity(this, VideoPreviewActivity.class, bundle);
	}


	@Override
	public void onBackPressed() {
		if (layoutAlbum.getVisibility() == View.VISIBLE) {
			hideAlbum();
		} else
			super.onBackPressed();
	}

	@Override
	/** ����б����¼� */
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
			videoSelectorDomain.getReccent(reccentListener);
		else
			videoSelectorDomain.getAlbum(current.getName(), reccentListener); // ��ȡѡ��������Ƭ
	}

	/** ��ȡ����ͼ����Ƭ�ص� */
	public interface OnLocalReccentListener {
		public void onPhotoLoaded(List<VideoModel> photos);
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
		public void onPhotoLoaded(List<VideoModel> videos) {
			if (tvAlbum.getText().equals(RECCENT_PHOTO))
				videos.add(0, new VideoModel());
			videoAdapter.update(videos);
			gvPhotos.smoothScrollToPosition(0); // ����������
			reset();
//			if(videos.size()>0){
//				allCount = videos.size()-1;
//			}
//			tvPreview.setText("complete("+selected.size()+"/"+allCount+")");
//			if (selected.isEmpty()) {
//				tvPreview.setEnabled(false);
//				preview_bt.setEnabled(false);
//				tvPreview.setBackground(getResources().getDrawable(R.drawable.bg_bt_disable_style));
//				preview_bt.setBackground(getResources().getDrawable(R.drawable.bg_bt_disable_style));
//			}else{
//				tvPreview.setEnabled(true);
//				preview_bt.setEnabled(true);
//				tvPreview.setBackground(getResources().getDrawable(R.drawable.bg_bt_enable_style));
//				preview_bt.setBackground(getResources().getDrawable(R.drawable.bg_bt_enable_style));
//			}
		}
	};
}
