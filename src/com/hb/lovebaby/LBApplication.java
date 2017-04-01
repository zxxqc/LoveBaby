package com.hb.lovebaby;

import com.hb.lovebaby.language.LanguageSettingUtil;
import com.hb.lovebaby.language.SwitchLanguageObservable;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import android.graphics.Bitmap;

import android.app.Application;
import android.util.DisplayMetrics;

public class LBApplication extends Application {
	
	/**
	 * 屏幕的宽
	 */
	public static int screenWidth;

	/**
	 * 屏幕的高
	 */
	public static int screenHeight;
	
	/**
	 * 屏幕的密度
	 */
	public static float screenDensity;

	/**
	 * 语言设置工具类
	 * */
	private LanguageSettingUtil languageSetting;

	/**
	 * 语言设置 被观察对象
	 * */
	private SwitchLanguageObservable switchLangObs;

	public static String url = "http://121.42.160.48:3000";

	@Override
	public void onCreate() {
		super.onCreate();
		iniData();
		// 注册 切换语言 工具类
		LanguageSettingUtil.init(this);// 初始化
		languageSetting = LanguageSettingUtil.get();// 检查是否已经初始化
		switchLangObs = new SwitchLanguageObservable();
		initPicasso();
		configImageLoader();

	}

	private void configImageLoader() {

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheInMemory().cacheOnDisk(true)
				.showImageOnLoading(R.drawable.loading)
				.showImageOnFail(R.drawable.loading).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.memoryCacheExtraOptions(480, 800)
				// max width, max height锛屽嵆淇濆瓨鐨勬瘡涓紦瀛樻枃浠剁殑鏈�澶ч暱瀹�
				.threadPoolSize(3)
				// 绾跨▼姹犲唴鍔犺浇鐨勬暟閲�
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
				// You can pass your own memory cache
				// implementation/浣犲彲浠ラ�氳繃鑷繁鐨勫唴瀛樼紦瀛樺疄鐜�
				.memoryCacheSize(2 * 1024 * 1024)
				.discCacheSize(50 * 1024 * 1024)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// 灏嗕繚瀛樼殑鏃跺�欑殑URI鍚嶇О鐢∕D5 鍔犲瘑
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCacheFileCount(100) // 缂撳瓨鐨勬枃浠舵暟閲�
				.defaultDisplayImageOptions(options)
				.imageDownloader(
						new BaseImageDownloader(getApplicationContext(),
								5 * 1000, 30 * 1000)) // connectTimeout (5 s),
														// readTimeout (30
														// s)瓒呮椂鏃堕棿
				.writeDebugLogs() // Remove for release app
				.build();// 寮�濮嬫瀯寤�
		ImageLoader.getInstance().init(config);
	}

	public String getUrl() {
		return url;
	}

	public LanguageSettingUtil getLanguageSetting() {
		return languageSetting;
	}

	public SwitchLanguageObservable getSwitchLangObs() {
		return switchLangObs;
	}
	
	private void iniData() {
		// TODO Auto-generated method stub
		/*
		 * 收集收集数据
		 */
		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		screenDensity = dm.density;
		//
	}
	
	private void initPicasso() {
        Picasso picasso=new Picasso.Builder(this)
                .memoryCache(new LruCache(10<<20))
                .defaultBitmapConfig(Bitmap.Config.RGB_565)
                .downloader(new OkHttpDownloader(getCacheDir(),20*1024*1024))
                .build();
        Picasso.setSingletonInstance(picasso);
    }

}
