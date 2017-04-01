package com.hb.lovebaby.activity;

import java.io.File;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.lovebaby.R;
import com.hb.lovebaby.dialog.ListDialog;
import com.hb.lovebaby.dialog.ListDialog.OnSelectItemListener;
import com.hb.lovebaby.imageload.ImageLoader;
import com.hb.lovebaby.util.Constants;

public class PersonalInfoActivity extends BascActivity {
	private String newIconPath;
	private static final int REQUEST_CODE_CAPTURE = 600;
	private PopupWindow popSearch;// 弹出框控件
	private View headPortrait;
	private View addview;
	private View name;
	private PersonalInfoActivity _this;
	private View gender;
	private View icon_iv;
	private View birthday;
	
	private TextView birthdayTextView,gender_tv;
	
	private View region;
	
	private View signature;
	private TextView sign_tv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_info);
		_this=this;
		initViews();
	}
	
	private void initViews() {
		headPortrait = findViewById(R.id.head_portrait);
		sign_tv=(TextView) findViewById(R.id.sign_tv);
		icon_iv=findViewById(R.id.icon_iv);
		addview = LayoutInflater.from(_this).inflate(R.layout.select_photo,
				null);
		gender_tv = (TextView) findViewById(R.id.gender_tv);
		headPortrait.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final String[] data = {
						getResources().getString(R.string.taking_pictures),
						getResources().getString(R.string.local_photo),
				};
				ListDialog listDialog = new ListDialog(PersonalInfoActivity.this, getResources().getString(R.string.set_head), data);
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
			}
		});
		
		name = findViewById(R.id.name);
		name.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonalInfoActivity.this, PersonalInfoNameActivity.class);
				startActivity(intent);
			}
		});
		
		gender = findViewById(R.id.gender);
		gender.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				OperationGenderDialog();
			}
		});
		
		birthday = findViewById(R.id.birthday);
		birthdayTextView = (TextView) findViewById(R.id.birthday_textview);
		birthday.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {/*
				DatePickerDialog dialog = new DatePickerDialog(PersonalInfoActivity.this, System.currentTimeMillis());
			
				dialog.setOnDateSetListener(new OnDateSetListener() {
					public void OnDateSet(Dialog dialog, long date) {
						SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
						String s = sdf.format(new Date(date));
						birthdayTextView.setText(s);
					}

				});
				dialog.show();
			*/
			}
		});
		
		region = findViewById(R.id.region);
		region.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonalInfoActivity.this, RelativesRelationshipActivity.class);
				intent.putExtra("select_type", "select_area");
				startActivity(intent);
			}
		});
		
		signature = findViewById(R.id.signature);
		signature.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonalInfoActivity.this, PersonalInfoSignatureActivity.class);
				startActivityForResult(intent, Constants.RequestCode);
			}
		});
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
	/**
	 * 拍照
	 */
	private void getTaking() {
		newIconPath = Constants.BASE_UPLOAD_IMAGE_PATH
				+ System.currentTimeMillis() / 1000 + "avatar.png";
		File protraitFile = new File(newIconPath);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(protraitFile));
		startActivityForResult(intent, REQUEST_CODE_CAPTURE);
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
		popSearch.showAtLocation(signature, Gravity.BOTTOM, 0, 0);

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
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		 if (requestCode == 200) {
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
//			String lujing = null;
			// 为了适配不同机型，两种不同方式去获取图片路径
			if (null != cursor1) {// 第一种，从游标中获取
				cursor1.moveToFirst();
				int columnIndex = cursor1.getColumnIndex(filePathColumn[0]);

				newIconPath = cursor1.getString(columnIndex);
				cursor1.close();
			} else if (null != selectedImage) {// 第二种，直接从uri中获取
				String tmpPath = selectedImage.getPath();
				if (tmpPath != null
						&& (tmpPath.endsWith(".jpg")
								|| tmpPath.endsWith(".png") || tmpPath
									.endsWith(".gif"))) {
					newIconPath = tmpPath;
				}
			}
			Log.i("TAG", "从相册选择==" + newIconPath);// 从相册获取的图片路径
			ImageLoader imagerLoad=new ImageLoader(_this, 480);
			imagerLoad.DisplayImage(newIconPath, (ImageView) icon_iv, false);

		}else if (requestCode == REQUEST_CODE_CAPTURE) {
			// 拍照返回
			Log.i("TAG", "从相册选择==" + newIconPath);// 从相册获取的图片路径
			ImageLoader imagerLoad=new ImageLoader(_this, 480);
			imagerLoad.DisplayImage(newIconPath, (ImageView) icon_iv, false);
		}else if(resultCode==Constants.RESULTCODE){
			String signtex=data.getStringExtra("signtex");
			sign_tv.setText(signtex);
		}
	}
}
