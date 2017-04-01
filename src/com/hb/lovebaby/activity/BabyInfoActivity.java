package com.hb.lovebaby.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.lovebaby.R;
import com.hb.lovebaby.dialog.ListDialog;
import com.hb.lovebaby.dialog.ListDialog.OnSelectItemListener;
import com.hb.lovebaby.util.Constants;

/**
 * @author pei.zhu 宝宝信息
 */
public class BabyInfoActivity extends Activity implements OnClickListener {
	private Button main_back_bt, setcover_btn;
	private RelativeLayout thename_rel;
	private RelativeLayout gender_rel;
	private RelativeLayout birthday_rel;
	private RelativeLayout constellation_rel;
	private RelativeLayout blood_rel;
	private RelativeLayout birth_rel;
	private RelativeLayout height_rel;
	private RelativeLayout weight_rel;
	private BabyInfoActivity _this;
	private TextView thename_tv, gender_tv, blood_tv, constellation_tv;
	private TextView height_tv, weight_tv;
	private static final int REQUEST_CODE_CAPTURE = 600;
	private static final int REQUEST_CODE_CROP = 100;
	private String newIconPath;
	private String cropPath;
	private PopupWindow popSearch;// 弹出框控件
	private View addview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_babyinfo);
		newIconPath = Constants.BASE_UPLOAD_IMAGE_PATH
				+ System.currentTimeMillis() / 1000 + "avatar.png";
		_this = this;
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		main_back_bt = (Button) findViewById(R.id.main_back_bt);
		main_back_bt.setOnClickListener(this);
		thename_rel = (RelativeLayout) findViewById(R.id.thename_rel);
		gender_rel = (RelativeLayout) findViewById(R.id.gender_rel);
		birthday_rel = (RelativeLayout) findViewById(R.id.birthday_rel);
		constellation_rel = (RelativeLayout) findViewById(R.id.constellation_rel);
		blood_rel = (RelativeLayout) findViewById(R.id.blood_rel);
		birth_rel = (RelativeLayout) findViewById(R.id.birth_rel);
		height_rel = (RelativeLayout) findViewById(R.id.height_rel);
		weight_rel = (RelativeLayout) findViewById(R.id.weight_rel);
		thename_tv = (TextView) findViewById(R.id.thename_tv);
		gender_tv = (TextView) findViewById(R.id.gender_tv);
		blood_tv = (TextView) findViewById(R.id.blood_tv);
		height_tv = (TextView) findViewById(R.id.height_tv);
		weight_tv = (TextView) findViewById(R.id.weight_tv);
		setcover_btn = (Button) findViewById(R.id.setcover_btn);
		constellation_tv = (TextView) findViewById(R.id.constellation_tv);
		addview = LayoutInflater.from(_this).inflate(R.layout.select_photo,
				null);
		thename_rel.setOnClickListener(this);
		gender_rel.setOnClickListener(this);
		birthday_rel.setOnClickListener(this);
		constellation_rel.setOnClickListener(this);
		blood_rel.setOnClickListener(this);
		birth_rel.setOnClickListener(this);
		height_rel.setOnClickListener(this);
		weight_rel.setOnClickListener(this);
		setcover_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.main_back_bt:
			finish();
			break;
		case R.id.thename_rel:
			Intent intent = new Intent(_this, NickNameActivity.class);
			intent.putExtra("nickname", thename_tv.getText().toString().trim()
					+ "");
			startActivityForResult(intent, Constants.RequestCode);
			break;
		case R.id.gender_rel:
			OperationGenderDialog();
			break;
		case R.id.birthday_rel:
			break;
		case R.id.constellation_rel:// 星座
			Intent intent3 = new Intent(_this, TypeChooseActivity.class);
			intent3.putExtra("type", 2);
			intent3.putExtra("content", constellation_tv.getText().toString()
					.trim());
			startActivityForResult(intent3, Constants.RequestXingZuoCode);
			break;
		case R.id.blood_rel:// 血型
			Intent intent2 = new Intent(_this, TypeChooseActivity.class);
			intent2.putExtra("type", 1);
			intent2.putExtra("content", blood_tv.getText().toString().trim());
			startActivityForResult(intent2, Constants.RequestBloodCode);
			break;
		case R.id.birth_rel:
			break;
		case R.id.height_rel:
		case R.id.weight_rel:
			String height = height_tv.getText().toString().trim();
			String weight = weight_tv.getText().toString().trim();
			String empty = getResources().getString(R.string.not_filled);
			if (weight.equals(empty) || height.equals(empty)) {
				Intent intent4 = new Intent(_this, HWActivity.class);
				startActivityForResult(intent4, Constants.RequestWeight);
			} else {

				Intent intent5 = new Intent(_this,
						BabyGrowthRecordActivity.class);
				startActivityForResult(intent5, Constants.RequestWeight);

			}
			break;
		case R.id.setcover_btn:

			final String[] data = {
					getResources().getString(R.string.taking_pictures),
					getResources().getString(R.string.local_photo), };
			ListDialog listDialog = new ListDialog(BabyInfoActivity.this,
					getResources().getString(R.string.set_head), data);
			listDialog.show();
			listDialog.setOnSelectItemListener(new OnSelectItemListener() {

				@Override
				public void OnSelectItem(Dialog dialog, String item) {
					// TODO Auto-generated method stub
					// 关掉更多操作对话框
					dialog.cancel();
					if (item.equals(data[0])) {// 拍照
						getTaking();
					} else {
						selectGalleryOrFolder();
					}
				}
			});
			break;
		default:
			break;
		}
	}

	/**
	 * 选择pop
	 */
	private void selectGalleryOrFolder() {
		if (popSearch == null) {
			popSearch = new PopupWindow(addview,
					LinearLayout.LayoutParams.MATCH_PARENT,
					(int) getResources().getDimension(
							R.dimen.im_default_soft_key_board_height), true);
		}
		popSearch.setTouchable(true);
		popSearch.setFocusable(true);
		// 需要设置一下此参数，点击外边可消失
		popSearch.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击窗口外边窗口消失
		popSearch.setOutsideTouchable(true);
		popSearch.showAtLocation(setcover_btn, Gravity.BOTTOM, 0, 0);

		// 拍照
		final Button gallery_btn = (Button) addview
				.findViewById(R.id.gallery_btn);
		// 相册选取
		final Button folder_btn = (Button) addview
				.findViewById(R.id.folder_btn);

		gallery_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// photograph(photograph);
				popSearch.dismiss();
				fromPhoto();
			}
		});

		folder_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// album(album);
				popSearch.dismiss();
			}
		});
	}

	/**
	 * 从相册中选择图片
	 */
	private void fromPhoto() {
		// 打开图片选择程序
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, 200);// 请求码是200
	}

	/**
	 * 拍照
	 */
	private void getTaking() {
		File protraitFile = new File(newIconPath);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(protraitFile));
		startActivityForResult(intent, REQUEST_CODE_CAPTURE);
	}

	private void startCropAvatar() {

		Intent intent_avatar = new Intent();
		// intent_avatar.addCategory(Intent.CATEGORY_OPENABLE);
		intent_avatar.setAction("com.android.camera.action.CROP");
		intent_avatar.setDataAndType(Uri.fromFile(new File(newIconPath)),
				"image/*");// mUri是已经选择的图片Uri
		intent_avatar.putExtra("return-data", true);
		intent_avatar.putExtra("crop", "true");
		// 裁剪框比例
		intent_avatar.putExtra("aspectX", 2);
		intent_avatar.putExtra("aspectY", 2);
		// 图片输出大小
		intent_avatar.putExtra("outputX", 300);
		intent_avatar.putExtra("outputY", 300);
		intent_avatar.putExtra("scale", true);
		intent_avatar.putExtra("outputFormat",
				Bitmap.CompressFormat.PNG.toString());
		intent_avatar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// 启用人脸识别
		intent_avatar.putExtra("noFaceDetection", true);
		intent_avatar.putExtra(MediaStore.EXTRA_OUTPUT, getCameraTempFile());
		startActivityForResult(intent_avatar, REQUEST_CODE_CROP);

	}

	private String getCameraTempFile() {
		String storageState = Environment.getExternalStorageState();
		if (storageState.equals(Environment.MEDIA_MOUNTED)) {
			File savedir = new File(Constants.BASE_UPLOAD_IMAGE_PATH);
			if (!savedir.exists()) {
				savedir.mkdirs();
			}
		} else {
			return null;
		}
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());
		String captureName = "camera_" + timeStamp + ".jpg";
		String mImagePath = Constants.BASE_UPLOAD_IMAGE_PATH + captureName;
		// File protraitFile = new File(mImagePath);
		cropPath = mImagePath;
		// return Uri.fromFile(protraitFile);
		return mImagePath;
	}

	/**
	 * 选择性别dialog
	 */
	private void OperationGenderDialog() {
		final Dialog setHeadDialog;
		View mDialogView;

		setHeadDialog = new Builder(_this).create();
		setHeadDialog.show();
		mDialogView = View.inflate(_this, R.layout.dialog_gender_layout, null);

		setHeadDialog.getWindow().setContentView(mDialogView);

		setHeadDialog.setCanceledOnTouchOutside(true);
		RadioButton boy_check = (RadioButton) setHeadDialog
				.findViewById(R.id.boy_check);
		RadioButton girl_check = (RadioButton) setHeadDialog
				.findViewById(R.id.girl_check);
		RadioButton unknow_check = (RadioButton) setHeadDialog
				.findViewById(R.id.unknow_check);
		boy_check.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setHeadDialog.dismiss();
				gender_tv.setText(getResources().getString(R.string.boy));
			}
		});
		girl_check.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setHeadDialog.dismiss();
				gender_tv.setText(getResources().getString(R.string.girl));

			}
		});
		unknow_check.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setHeadDialog.dismiss();
				gender_tv.setText(getResources().getString(R.string.unknown));

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (null == data && requestCode != REQUEST_CODE_CAPTURE) {
			return;
		}
		if (requestCode == Constants.RequestCode) {
			String nickname = data.getStringExtra("nickname");
			thename_tv.setText(nickname);
		} else if (requestCode == Constants.RequestBloodCode) {
			String BloodType = data.getStringExtra("result");
			blood_tv.setText(BloodType);
		} else if (requestCode == Constants.RequestXingZuoCode) {
			String result = data.getStringExtra("result");
			constellation_tv.setText(result);
		} else if (requestCode == Constants.RequestWeight) {
			String height = data.getExtras().getString("height");
			String weight = data.getExtras().getString("weight");
			height_tv.setText(height);
			weight_tv.setText(weight);
		} else if (requestCode == REQUEST_CODE_CAPTURE) {
			// 拍照返回
			startCropAvatar();
		} else if (requestCode == REQUEST_CODE_CROP) {
			// Bitmap iconBmp = data.getParcelableExtra("data");
			// // iconBmp = RoundBitmap.getRoundedCornerBitmap(iconBmp);
			// FileUtils.saveUploadBitmap("avatar.png", iconBmp);
			// newIconPath = Constants.BASE_UPLOAD_IMAGE_PATH + "avatar.png";
			// cropPath 裁剪完成后的图片
			Log.i("TAG", "裁剪完成后的图片==" + cropPath);// 
		} else if (requestCode == 200) {
			// 从相册，请求码200
			if (null == data) {
				Log.d("返回数据为空", "返回数据为空!!!");
				Toast.makeText(_this, "没有选择图片", Toast.LENGTH_SHORT).show();
				return;
			}
			Uri selectedImage = data.getData();// 获取图片Uri
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			// 获取游标
			Cursor cursor1 = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			String lujing = null;
			// 为了适配不同机型，两种不同方式去获取图片路径
			if (null != cursor1) {// 第一种，从游标中获取
				cursor1.moveToFirst();
				int columnIndex = cursor1.getColumnIndex(filePathColumn[0]);

				lujing = cursor1.getString(columnIndex);
				cursor1.close();
			} else if (null != selectedImage) {// 第二种，直接从uri中获取
				String tmpPath = selectedImage.getPath();
				if (tmpPath != null
						&& (tmpPath.endsWith(".jpg")
								|| tmpPath.endsWith(".png") || tmpPath
									.endsWith(".gif"))) {
					lujing = tmpPath;
				}
			}
			Log.i("TAG", "从相册选择==" + lujing);// 从相册获取的图片路径

		}
	}

}
