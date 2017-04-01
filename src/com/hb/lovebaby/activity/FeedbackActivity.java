package com.hb.lovebaby.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.hb.lovebaby.LBApplication;
import com.hb.lovebaby.R;
import com.hb.lovebaby.share.ShareUserInfo;
import com.hb.lovebaby.util.FileStorage;
import com.hb.lovebaby.util.OkManager;
import com.hb.lovebaby.util.OkManager.Func4;
import com.hb.lovebaby.view.IOSAlertDialog;
import com.hb.lovebaby.view.MyDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class FeedbackActivity extends FragmentActivity implements PermissionListener {

	private TextView tvTitle;
	private ImageButton btnBack;
	private TextView tvSend;

	private EditText edtMessage;
	private TextView tvLength;
	private EditText edtContact;
	private LinearLayout llAddPhoto;
	private LinearLayout lin_addhs;

	private HorizontalScrollView hScrollView;

	private List<File> list;
	private List<String> filePath;
	private File iconPath = null;
	private File cropPath = null;

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	private static final int REQUEST_PICK_IMAGE = 1; // 相册选取
	private static final int REQUEST_CAPTURE = 2; // 拍照
	private static final int REQUEST_PICTURE_CUT = 3; // 剪裁图片
	private static final int REQUEST_CODE_PERMISSION_SD = 100;
	private static final int REQUEST_CODE_PERMISSION_OTHER = 101;
	private static final int REQUEST_CODE_SETTING = 300;

	@SuppressLint("InlinedApi")
	static final String[] PERMISSIONS = new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA };

	private Uri imageUri;// 原图保存地址
	private boolean isClickCamera = false;// 是否拍照
	private String imagePath;

	private MyDialog dialog;
	private ImageLoader loader;
	private OkManager okManager;
	private OSSClient oss;

	ShareUserInfo shareUserInfo;
	String accesskey;

	private String ACCESS_ID = "";
	private String ACCESS_KEY = "";
	private String OSS_ENDPOINT = "";
	private String BUCKET_NAME = "";

	private int number = 0;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_feedback);
		okManager = OkManager.getInstance(this);
		shareUserInfo = new ShareUserInfo(this);
		accesskey = shareUserInfo.getAccessKey();
		dialog = new MyDialog(this);
		loader = ImageLoader.getInstance();
		list = new ArrayList<File>();
		filePath=new ArrayList<String>();

		initOssInfo();
		initView();
		initData();
		initClick();

	}

	private void initOss() {
		// TODO Auto-generated method stub
		// ACCESS_ID,ACCESS_KEY是在阿里云申请的
		OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(ACCESS_ID, ACCESS_KEY);
		ClientConfiguration conf = new ClientConfiguration();
		conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
		conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
		conf.setMaxConcurrentRequest(8); // 最大并发请求数，默认5个
		conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次

		// oss为全局变量，OSS_ENDPOINT是一个OSS区域地址
		oss = new OSSClient(getApplicationContext(), OSS_ENDPOINT, credentialProvider, conf);
//		Toast.makeText(FeedbackActivity.this, "初始化成功", Toast.LENGTH_SHORT).show();
	}

	public void ossUpload(int i) {

		// 判断图片是否全部上传
		// 如果已经是最后一张图片上传成功，则跳出
//		number++;
//		if (number == list.size()) {
//			// 结束的处理逻辑，并退出该方法
//			return;
//		}
		// 指定数据类型，没有指定会自动根据后缀名判断
		ObjectMetadata objectMeta = new ObjectMetadata();
		objectMeta.setContentType("image/jpeg");

		// 构造上传请求
		// 这里的objectKey其实就是服务器上的路径，即目录+文件名
		// 因为目录命名逻辑涉及公司信息，被我删去，造成不知道这个objectKey不知为何物，如下是我们公司的大致命名逻辑
		// String objectKey = keyPath + "/" + carArr[times] + ".jpg";
		filePath.add("user_" + shareUserInfo.getID() + "/"
				+ format.format(new Date()) + "/" + UUID.randomUUID().toString() + ".jpg");
		PutObjectRequest put = new PutObjectRequest(BUCKET_NAME, "user_" + shareUserInfo.getID() + "/"
				+ format.format(new Date()) + "/" + UUID.randomUUID().toString() + ".jpg",
				list.get(i).getAbsolutePath());
		put.setMetadata(objectMeta);
		try {
			PutObjectResult putObjectResult = oss.putObject(put);
		} catch (ClientException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		// 异步上传时可以设置进度回调
		put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
			@Override
			public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
				// 在这里可以实现进度条展现功能
				Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
			}
		});
		@SuppressWarnings("rawtypes")
		OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
			@Override
			public void onSuccess(PutObjectRequest request, PutObjectResult result) {
				Log.d("PutObject", "UploadSuccess");
				Log.d("ETag", result.getETag());
				Log.d("RequestId", result.getRequestId());
				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(FeedbackActivity.this, "图片上传成功!", Toast.LENGTH_SHORT).show();
					}
				});
				// 这里进行递归单张图片上传，在外面判断是否进行跳出
				if (number <= list.size() - 1) {
					// upOss(list.get(number));
				}
			}

			@Override
			public void onFailure(PutObjectRequest request, ClientException clientExcepion,
					ServiceException serviceException) {
				// 请求异常
				if (clientExcepion != null) {
					// 本地异常如网络异常等
					clientExcepion.printStackTrace();
				}
				if (serviceException != null) {
					// 服务异常
					Log.e("ErrorCode", serviceException.getErrorCode());
					Log.e("RequestId", serviceException.getRequestId());
					Log.e("HostId", serviceException.getHostId());
					Log.e("RawMessage", serviceException.getRawMessage());
				}
				Toast.makeText(FeedbackActivity.this, "图片上传失败，请重新上传", Toast.LENGTH_SHORT).show();
				return;
			}
		});
	}

	private void initOssInfo() {
		// TODO Auto-generated method stub
		okManager.asyncJsonObject(LBApplication.url + "/account/ossInfo?expiration=10000", new Func4() {

			@Override
			public void onResponse(JSONObject jsonObject) {
				// TODO Auto-generated method stub
				Log.i("tag", jsonObject.toString());
				// Toast.makeText(FeedbackActivity.this, jsonObject.toString(),
				// Toast.LENGTH_SHORT).show();
				try {
					String ret = jsonObject.getString("ret");
					if (ret.equals("SUCCESS")) {
						JSONObject data = jsonObject.getJSONObject("data");
						JSONObject oss = data.getJSONObject("oss");
						String region = oss.getString("region");
						OSS_ENDPOINT = oss.getString("endpoint");
						BUCKET_NAME = oss.getString("bucket");
						JSONObject token = data.getJSONObject("token");
						String type = token.getString("type");
						ACCESS_ID = token.getString("access_key_id");
						ACCESS_KEY = token.getString("access_key_secret");
						initOss();
					} else {
						Toast.makeText(FeedbackActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}, "FeedbackActivityOSS", accesskey);
	}

	private void initView() {
		tvTitle = (TextView) findViewById(R.id.tv_title_activity);
		btnBack = (ImageButton) findViewById(R.id.btn_back_activity);
		tvSend = (TextView) findViewById(R.id.tv_title_send);
		edtMessage = (EditText) findViewById(R.id.edt_feedback_message);
		tvLength = (TextView) findViewById(R.id.tv_feed_lengh);
		edtContact = (EditText) findViewById(R.id.edt_feedback_contact);
		llAddPhoto = (LinearLayout) findViewById(R.id.ll_feedback_add_photo);
		hScrollView = (HorizontalScrollView) findViewById(R.id.hScrollView);
		lin_addhs = (LinearLayout) findViewById(R.id.lin_addhs);
	}

	private void initData() {
		tvSend.setVisibility(View.VISIBLE);
		tvSend.setText(getString(R.string.feedback_send));
		tvTitle.setText(getString(R.string.feedback_title));
		// addPhoto("",null);
		tvLength.setText("0/400");
		edtMessage.addTextChangedListener(textWatcher);

	}

	private void addPhoto(String imgPath, Uri imgUri) {
		ImageView imageView = new ImageView(this);
		imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		imageView.getLayoutParams().width = LBApplication.screenWidth / 4;
		imageView.getLayoutParams().height = LBApplication.screenWidth / 4;
		imageView.setPadding(10, 10, 10, 10);
		if (imgUri != null) {
			list.add(iconPath);
			Picasso.with(this).load(imgUri).noFade().placeholder(R.drawable.icon).error(R.drawable.ic_error_photo)
					.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(imageView);
		} else {
			list.add(cropPath);
			Picasso.with(this).load(imageUri).noFade().placeholder(R.drawable.icon).error(R.drawable.ic_error_photo)
					.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(imageView);
		}
		imageView.setScaleType(ScaleType.FIT_CENTER);
		llAddPhoto.addView(imageView);

	}

	private void initClick() {
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		tvSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Intent intent = new Intent(AddMyBabyActivity.this,
				// AddMyBabyInvitationCodeActivity.class);
				// startActivity(intent);
				if (list != null && list.size() > 0) {
					if (!ACCESS_ID.equals("") || !ACCESS_KEY.equals("") || !BUCKET_NAME.equals("")) {
						String msg=edtMessage.getText().toString();
						String con=edtContact.getText().toString();
						if(msg==null||msg.equals("")||con==null||con.equals("")||list.size()==0){
							Toast.makeText(FeedbackActivity.this, "Input cannot be null", Toast.LENGTH_SHORT).show();
						}else{
							for (int i = 0; i < list.size(); i++) {
								ossUpload(i);
							}
							postFeedback(msg,con);
						}
					}else{
						Toast.makeText(FeedbackActivity.this, "File initialization failed, please contact customer service", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(FeedbackActivity.this, "Please select upload file", Toast.LENGTH_SHORT).show();
				}
			}
		});

		lin_addhs.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TextView tv1 = dialog.getTakePhone();
				TextView tv2 = dialog.getSystemphone();
				tv1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						if (Build.VERSION.SDK_INT >= 23) {
							AndPermission.with(FeedbackActivity.this).requestCode(REQUEST_CODE_PERMISSION_SD)
									.permission(PERMISSIONS).rationale(new RationaleListener() {

										@Override
										public void showRequestPermissionRationale(int requestCode,
												Rationale rationale) {
											// 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
											AndPermission.rationaleDialog(FeedbackActivity.this, rationale).show();
										}
									}).send();
						} else {
							openCamera();
						}
						isClickCamera = true;
					}
				});
				tv2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						if (Build.VERSION.SDK_INT >= 23) {
							AndPermission.with(FeedbackActivity.this).requestCode(REQUEST_CODE_PERMISSION_OTHER)
									.permission(PERMISSIONS).rationale(new RationaleListener() {

										@Override
										public void showRequestPermissionRationale(int requestCode,
												Rationale rationale) {
											// 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
											AndPermission.rationaleDialog(FeedbackActivity.this, rationale).show();
										}
									}).send();
						} else {
							// Toast.makeText(FeedbackActivity.this,
							// "FeedbackActivity", Toast.LENGTH_SHORT).show();
							selectFromAlbum();
						}
						isClickCamera = false;
					}
				});
				dialog.show();
			}
		});
	}

	protected void postFeedback(String msg,String con) {
		// TODO Auto-generated method stub
		Map<String,String> map=new HashMap<String,String>();
		JSONObject object=new JSONObject();
		try {
			object.put("text", msg);
			object.put("contact", con);
			JSONArray array=new JSONArray();
			for (int i = 0; i < filePath.size(); i++) {
				array.put(filePath.get(i));
			}
			object.put("files", array);
			okManager.postJsonString(LBApplication.url+"/feedback", object.toString(), new Func4() {
				
				@Override
				public void onResponse(JSONObject jsonObject) {
					// TODO Auto-generated method stub
					try {
						String ret=jsonObject.getString("ret");
						if(ret.equals("SUCCESS")){
							Toast.makeText(FeedbackActivity.this, "Upload success", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(FeedbackActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}, "FeedbackActivity", accesskey, true);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			tvLength.setText(s.length() + "/400");
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			//

		}

		@Override
		public void afterTextChanged(Editable s) {
			//
		}
	};

	/**
	 * 打开系统相机(这个方法已经适配android7.0中的私有目录被限制访问权限)
	 * 从Android7.0开始在应用间共享文件时已经不允许使用intent来传递file://URI类型的uri了
	 * 只允许传递content://URI类型的uri
	 */
	private void openCamera() {
		File file = new FileStorage().createIconFile();
		iconPath = file;
		// 判断当前的SDK版本是否在7.0及以上
		// 是的话就使用最新的文件传递机制，否则就使用老的文件传递机制
		if (Build.VERSION.SDK_INT >= 24) {
			// 通过FileProvider创建一个content类型的Uri
			imageUri = FileProvider.getUriForFile(FeedbackActivity.this, "com.hb.lovebaby.fileprovider", file);
		} else {
			imageUri = Uri.fromFile(file);
		}
		Intent intent = new Intent();
		if (Build.VERSION.SDK_INT >= 24) {
			// 添加这一句表示对目标应用临时授权该Uri所代表的文件(授予 URI 临时访问权限)
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		}
		intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);// 设置Action为拍照
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);// 将拍取的照片保存到指定URI
		startActivityForResult(intent, REQUEST_CAPTURE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_PICK_IMAGE:// 从相册选择
				if (Build.VERSION.SDK_INT >= 19) {
					handleImageOnKitKat(data);
				} else {
					handleImageBeforeKitKat(data);
				}
				break;
			case REQUEST_CAPTURE:// 拍照
				// cropPhoto();
				// img_phone.setImageURI(imageUri);
				addPhoto("", imageUri);
				// Toast.makeText(FeedbackActivity.this, imageUri.getPath(),
				// Toast.LENGTH_SHORT).show();
				break;
			case REQUEST_PICTURE_CUT:// 裁剪完成
				// Bitmap bitmap = null;
				try {
					// if (isClickCamera) {
					// bitmap =
					// BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
					// } else {
					// bitmap = BitmapFactory.decodeFile(imagePath);
					// }
					// img_phone.setImageBitmap(bitmap);
					// Toast.makeText(FeedbackActivity.this, imageUri.getPath(),
					// Toast.LENGTH_SHORT).show();
					addPhoto(imagePath, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case REQUEST_CODE_SETTING:
				Toast.makeText(this, "从设置返回", Toast.LENGTH_LONG).show();
				break;
			}
		}
	}

	@TargetApi(19)
	private void handleImageOnKitKat(Intent data) {
		imagePath = null;
		imageUri = data.getData();
		if (DocumentsContract.isDocumentUri(this, imageUri)) {
			// 如果是document类型的uri,则通过document id处理
			String docId = DocumentsContract.getDocumentId(imageUri);
			if ("com.android.providers.media.documents".equals(imageUri.getAuthority())) {
				String id = docId.split(":")[1];// 解析出数字格式的id
				String selection = MediaStore.Images.Media._ID + "=" + id;
				imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
			} else if ("com.android.downloads.documents".equals(imageUri.getAuthority())) {
				Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
						Long.valueOf(docId));
				imagePath = getImagePath(contentUri, null);
			}
		} else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
			// 如果是content类型的Uri，则使用普通方式处理
			imagePath = getImagePath(imageUri, null);
		} else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
			// 如果是file类型的Uri,直接获取图片路径即可
			imagePath = imageUri.getPath();
		}
		cropPhoto();
	}

	private void handleImageBeforeKitKat(Intent intent) {
		imageUri = intent.getData();
		imagePath = getImagePath(imageUri, null);
		cropPhoto();
	}

	private String getImagePath(Uri uri, String selection) {
		String path = null;
		// 通过Uri和selection老获取真实的图片路径
		Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
			}
			cursor.close();
		}
		return path;
	}

	/**
	 * 从相册选择
	 */
	private void selectFromAlbum() {
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		// intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
		// "image/*");
		startActivityForResult(intent, REQUEST_PICK_IMAGE);
	}

	/**
	 * 裁剪
	 */
	private void cropPhoto() {
		File file = new FileStorage().createCropFile();
		cropPath = file;
		Uri outputUri = Uri.fromFile(file);// 缩略图保存地址
		Intent intent = new Intent("com.android.camera.action.CROP");
		if (Build.VERSION.SDK_INT >= 24) {
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		}
		intent.setDataAndType(imageUri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, REQUEST_PICTURE_CUT);
	}

	@Override
	public void onFailed(int arg0, List<String> arg1) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case REQUEST_CODE_PERMISSION_SD: {
			// Toast.makeText(this, "请打开必要权限", Toast.LENGTH_SHORT).show();
			showTipsDialog();
			break;
		}
		case REQUEST_CODE_PERMISSION_OTHER: {
			// Toast.makeText(this, "请打开必要权限", Toast.LENGTH_SHORT).show();
			showTipsDialog();
			break;
		}
		}
		// 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
		if (AndPermission.hasAlwaysDeniedPermission(this, arg1)) {
		}
	}

	@Override
	public void onSucceed(int arg0, List<String> arg1) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case REQUEST_CODE_PERMISSION_SD:
			openCamera();
			break;
		case REQUEST_CODE_PERMISSION_OTHER:
			selectFromAlbum();
			break;
		}
	}

	@SuppressLint({ "Override", "NewApi" })
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
			@NonNull int[] grantResults) {
		// TODO Auto-generated method stub
		// super.onRequestPermissionsResult(requestCode, permissions,
		// grantResults);
		AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
	}

	/**
	 * 显示提示对话框
	 */
	private void showTipsDialog() {
		IOSAlertDialog dialog = new IOSAlertDialog(this);
		dialog.builder().setTitle("提示").setMessage("当前应用缺少相机权限。\n请到\"设置\"-\"权限\"-打开所需权限。")
				.setPositiveButton("确定", new OnClickListener() {
					@Override
					public void onClick(View v) {
					}

				}).show();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		okManager.cancel("FeedbackActivityOSS");
		okManager.cancel("FeedbackActivity");
	}

}
