package com.hb.lovebaby.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hb.lovebaby.R;
import com.hb.lovebaby.util.AnimationUtil;
import com.hb.lovebaby.view.PhotoPreview;
import com.honeyBaby.temp.PhotoModel;

public class BasePhotoPreviewActivity extends Activity implements OnPageChangeListener, OnClickListener, OnCheckedChangeListener {

	private ViewPager mViewPager;
	private RelativeLayout layoutTop;
	private CheckBox checkBox;
	private ImageButton btnBack;
	private TextView tvPercent;
	protected List<PhotoModel> photos; // ����ͼƬ
	protected ArrayList<PhotoModel> selected; // ѡ�е�ͼƬ
	protected int current;
	protected int requestCode;
	
	private RelativeLayout layoutButtom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		setContentView(R.layout.activity_photopreview);
		layoutTop = (RelativeLayout) findViewById(R.id.layout_top_app);
		checkBox = (CheckBox)findViewById(R.id.photo_preview_checkbox);
		btnBack = (ImageButton) findViewById(R.id.btn_back_app);
		tvPercent = (TextView) findViewById(R.id.tv_percent_app);
		mViewPager = (ViewPager) findViewById(R.id.vp_base_app);
		
		layoutButtom = (RelativeLayout) findViewById(R.id.layout_buttom_app);
		TextView complete = (TextView)findViewById(R.id.tv_preview_ar);
		complete.setOnClickListener(this);
		
		btnBack.setOnClickListener(this);
		mViewPager.setOnPageChangeListener(this);
		checkBox.setOnCheckedChangeListener(this);
		overridePendingTransition(R.anim.activity_alpha_action_in, 0); // ����Ч��

	}

	/** �����ݣ����½��� */
	protected void bindData() {
		this.updatePercent();
		this.updateCheckState();
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setCurrentItem(current);
	}

	private PagerAdapter mPagerAdapter = new PagerAdapter() {

		@Override
		public int getCount() {
			if (photos == null) {
				return 0;
			} else {
				return photos.size();
			}
		}

		@Override
		public View instantiateItem(final ViewGroup container, final int position) {
			PhotoPreview photoPreview = new PhotoPreview(getApplicationContext());
			((ViewPager) container).addView(photoPreview);
			photoPreview.loadImage(photos.get(position));
			photoPreview.setOnClickListener(photoItemClickListener);
			return photoPreview;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	};
	protected boolean isUp;

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.btn_back_app){
			finish();
		} else if (view.getId() == R.id.tv_preview_ar) {
			Intent data = new Intent();
			Bundle bundle = new Bundle();
			bundle.putSerializable("selected", selected);
			data.putExtras(bundle);
			setResult(RESULT_OK, data);
			finish();
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		current = arg0;
		updatePercent();
		
		if (this.photos != null && this.photos.size() > current) {
			PhotoModel photoModel = this.photos.get(current);
			if (photoModel != null) {
				String currentPath = photoModel.getOriginalPath();
				for (PhotoModel selectPhotoModel : this.selected) {
					String path = selectPhotoModel.getOriginalPath();
					if (path != null) {
						if (path.equals(currentPath)) {
							checkBox.setChecked(true);
							return;
						}
					}
				}
			}
		}
		checkBox.setChecked(false);
	}

	protected void updatePercent() {
		tvPercent.setText((current + 1) + "/" + photos.size());
	}
	
	protected void updateCheckState(){
		for (int i = 0; i < this.selected.size(); i++) {
			if (i == current) {
				checkBox.setChecked(true);
			}
		}
	}

	/** ͼƬ����¼��ص� */
	private OnClickListener photoItemClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (!isUp) {
				new AnimationUtil(getApplicationContext(), R.anim.translate_up)
						.setInterpolator(new LinearInterpolator()).setFillAfter(true).startAnimation(layoutTop);
				isUp = true;
				new AnimationUtil(getApplicationContext(), R.anim.translate_down)
				.setInterpolator(new LinearInterpolator()).setFillAfter(true).startAnimation(layoutButtom);
			} else {
				new AnimationUtil(getApplicationContext(), R.anim.translate_down_current)
						.setInterpolator(new LinearInterpolator()).setFillAfter(true).startAnimation(layoutTop);
				isUp = false;
				new AnimationUtil(getApplicationContext(), R.anim.translate_up_current)
				.setInterpolator(new LinearInterpolator()).setFillAfter(true).startAnimation(layoutButtom);
			}
		}
	};

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isCheck) {
		PhotoModel photoModel = photos.get(current);
		if (photoModel != null) {
			if (isCheck) {
				if (!this.containPhoto(photoModel.getOriginalPath())) {
					this.selected.add(photoModel);
				}
			} else {
				PhotoModel tempModel = null;
				for (PhotoModel model : this.selected) {
					String originalPath = model.getOriginalPath();
					if (originalPath == null) {
						continue;
					}
					if (photoModel.getOriginalPath().equals(originalPath)) {
						tempModel = model;
						break;
					}
				}
				this.selected.remove(tempModel);
			}
			
		}
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

}
