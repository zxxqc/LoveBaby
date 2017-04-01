package com.hb.lovebaby.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class OkManager {
	private static OkHttpClient client=null;
	private volatile static OkManager okManager = null;//为了防止多个线程同时访问而产生交互
	private final String TAG = OkManager.class.getSimpleName();//获取当前类的类名
	private Handler handler;
	private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");//提交json格式的数据
    private static final MediaType string = MediaType.parse("text/x-mardown;charset=utf-8");//提交字符串的类型
    
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public OkManager(Context context) {
        client = getOkHttpSingletonInstance();
        
      //开启响应缓存
        client.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        //设置缓存目录和大小
        int cacheSize = 10 << 20; // 10 MiB
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        client.setCache(cache);

        //设置合理的超时
        client.setConnectTimeout(15, TimeUnit.SECONDS);
        client.setReadTimeout(20, TimeUnit.SECONDS);
        client.setWriteTimeout(20, TimeUnit.SECONDS);

        //以下验证不设置，那么默认就已经设置了验证
        client.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        
        handler = new Handler(Looper.getMainLooper());
    }
    
    public static OkHttpClient getOkHttpSingletonInstance() {
        if (client == null) {
            synchronized (OkHttpClient.class) {
                if (client == null) {
                	client = new OkHttpClient();
                }
            }
        }
        return client;
    }
    
  //由于我们采用的是单例模式开发，所以我们需要定义一个getInstance方法
    public static OkManager getInstance(Context context) {
        OkManager instance = null;
        if (okManager == null) {
            synchronized (OkManager.class) {//同步代码块
                if (instance == null) {
                    instance = new OkManager(context);
                    okManager = instance;
                }
            }
        }
        return okManager;
    }
    
    interface Func1 {
        void onResponse(String jsonString);
    }

    interface Func2 {
        void onResponse(byte[] bytes);
    }

    interface Func3 {
        void onResponse(Bitmap bitmap);
    }

    public interface Func4 {
        void onResponse(JSONObject jsonObject);
    }

    interface Func5 {
        void onResponse(JSONArray jsonArray);
    }
    
    interface Func6{
    	void onResponse(InputStream input);
    }
    
  //做完了这么多我们就可以进行相应方法的封装了
    //请求返回的结果是json的字符串
    private void onSuccessJsonString(final String jsonString, final Func1 callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onResponse(jsonString);
                }
            }
        });
    }
    
    //请求的结果是Bitmap对象
    private void onSuccessBitmap(final Bitmap bitmap, final Func3 callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onResponse(bitmap);
                }
            }
        });
    }
    
  //请求的结果是JSONObject类型
    private void onSuccessJsonObject(final String jsonString, final Func4 callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                	try {
						callBack.onResponse(new JSONObject(jsonString));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
        });
    }
    
  //请求的结果是JSONArray类型
    private void onSuccessJsonArray(final String jsonString, final Func5 callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    try {
                        callBack.onResponse(new JSONArray(jsonString));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    
  //请求返回的结果是byte[]
    private void onSuccessBytes(final byte[] data, final Func2 callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onResponse(data);
                }
            }
        });
    }
    
    //在OkHttp中他提供了请求的方法一共分为两大类,分别是同步请求和异步请求,一般在手机开发中不要用同步请求,因为他会阻塞UI线程，造成页面卡死
    //大部分是用的异步请求
    //同步:(get)
    public String syncByURL(String url) {//这个方法时同步请求，我们要将他放在子线程中去完成
        try {
            Request request = new Request.Builder().get().url(url).build();
            Response response = null;
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //get的异步处理
  //异步:1.返回json的字符串
    public void asyncJSON(String url, final Func1 callBack,Object tag) {
    	Request.Builder builder=new Request.Builder();
    	builder.tag(tag);
        Request request = builder.url(url).build();
        client.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Response arg0) throws IOException {
				// TODO Auto-generated method stub
				if (arg0 != null && arg0.isSuccessful()) {
		            onSuccessJsonString(arg0.body().string(), callBack);
		        }
			}
			
			@Override
			public void onFailure(Request arg0, IOException arg1) {
				// TODO Auto-generated method stub
				Log.i(TAG, "jsonString失败原因:"+arg1.getMessage());
			}
		});
        
    }
    
  //异步:2.返回byte数组
    public void asyncByte(String url, final Func2 callBack,Object tag) {
    	Request.Builder builder=new Request.Builder();
    	builder.tag(tag);
        Request request = builder.url(url).build();
        client.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Response arg0) throws IOException {
				// TODO Auto-generated method stub
				if (arg0 != null && arg0.isSuccessful()) {
                    onSuccessBytes(arg0.body().bytes(), callBack);
                }
			}
			
			@Override
			public void onFailure(Request arg0, IOException arg1) {
				// TODO Auto-generated method stub
				Log.i(TAG, "byte[]失败原因:"+arg1.getMessage());
			}
		});
    }
    
  //异步:3.返回Bitmap对象
    public void asyncBitmap(String url, final Func3 callBack,Object tag) {
    	Request.Builder builder=new Request.Builder();
    	builder.tag(tag);
        Request request = builder.url(url).build();
        client.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Response arg0) throws IOException {
				// TODO Auto-generated method stub
				if (arg0 != null && arg0.isSuccessful()) {
                    byte[] bytes = arg0.body().bytes();
                    Bitmap bitmap = new CropSquareTrans().transform(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                    onSuccessBitmap(bitmap, callBack);
                }
			}
			
			@Override
			public void onFailure(Request arg0, IOException arg1) {
				// TODO Auto-generated method stub
				Log.i(TAG, "bitmap失败原因:"+arg1.getMessage());
			}
		});
    }
    
  //异步:4.返回JSONObject对象
    public void asyncJsonObject(String url, final Func4 callBack,Object tag,String accessKey) {
    	Request.Builder builder=new Request.Builder();
    	builder.tag(tag);
    	builder.addHeader("Authorization", accessKey + ":");
        Request request = builder.url(url).build();
        client.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Response arg0) throws IOException {
				// TODO Auto-generated method stub
				if (arg0 != null && arg0.isSuccessful()) {
                    onSuccessJsonObject(arg0.body().string(), callBack);
                }
			}
			
			@Override
			public void onFailure(Request arg0, IOException arg1) {
				// TODO Auto-generated method stub
				Log.i(TAG, "JSONObject失败原因:"+arg1.getMessage());
			}
		});
    }
    
  //异步:5.返回JSONArray对象
    public void asyncJsonArray(String url, final Func5 callBack,Object tag) {
    	Request.Builder builder=new Request.Builder();
    	builder.tag(tag);
        Request request = builder.url(url).build();
        client.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Response arg0) throws IOException {
				// TODO Auto-generated method stub
				if (arg0 != null && arg0.isSuccessful()) {
					onSuccessJsonArray(arg0.body().string(), callBack);
                }
			}
			
			@Override
			public void onFailure(Request arg0, IOException arg1) {
				// TODO Auto-generated method stub
				Log.i(TAG, "JSONArray失败原因:"+arg1.getMessage());
			}
		});
    }
    
    
    /**
     * 作用：POST提交键值对，再返回相应的数据
     *
     * @param urlString ：访问网络的url地址
     * @param map       ：访问url时，需要传递给服务器的键值对数据。
     */
    public void postKeyValuePairAsync(String urlString, Map<String, String> map, final Func4 callback, Object tag,String accesskey
    		,boolean isKey) {
        //往FormEncodingBuilder对象中放置键值对
        FormEncodingBuilder formBuilder = new FormEncodingBuilder();
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                formBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        //生成请求体对象
        RequestBody requestBody = formBuilder.build();
        //将请求提放置到请求对象中
        Request request = buildPostRequest(urlString, requestBody, tag,accesskey,isKey);
        if (callback == null) {//表示我们不关心返回结果
            new Callback() {

                @Override
                public void onFailure(Request request, IOException e) {
                	Log.i(TAG, "JSONArray失败原因:"+e.getMessage());
                }

                @Override
                public void onResponse(Response response) throws IOException {

                }
            };
        }
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
            	
            }

            @Override
            public void onResponse(Response response) throws IOException {
            	if (response != null && response.isSuccessful()) {
            		onSuccessJsonObject(response.body().string(), callback);
            	}else{
            		onSuccessJsonObject("{\"error\":\"error\"}", callback);
            	}
            }
        });
    }
    
    /**
     * 基方法，返回Request对象
     *
     * @param urlString
     * @param tag
     * @return
     */
    private Request buildPostRequest(String urlString, RequestBody requestBody, Object tag,String accesskey,boolean isKey) {
        Request.Builder builder = new Request.Builder();
        builder.url(urlString).post(requestBody);
        if(isKey){
        	builder.addHeader("Authorization", accesskey + ":");
        }
        if (tag != null) {
            builder.tag(tag);
        }
        return builder.build();
    }
    
    /**
     * 作用：POST提交Json字符串，再返回相应的数据
     *
     * @param urlString  ：访问网络的url地址
     * @param jsonString ：访问url时，需要传递给服务器的json字符串
     * @return byte[]
     */
    public void postJsonString(String urlString, String jsonString, Func4 callback,Object tag,String accesskey
    		,boolean isKey) {
        //定义mimetype对象
        /*String MEDIA_TYPE_STREAM = "application/octet-stream;charset=utf-8";
        String MEDIA_TYPE_STRING = "text/plain;charset=utf-8";*/
        String MEDIA_TYPE_JSON = "application/json;charset=utf-8";
        MediaType JSON = MediaType.parse(MEDIA_TYPE_JSON);
        RequestBody requestBody = RequestBody.create(JSON, jsonString);
        postRequestBody(urlString, requestBody,callback, tag,accesskey,isKey);
    }
    
    /**
     * 作用：post提交数据，返回服务器端返回的字节数组
     *
     * @param urlString ：访问网络的url地址
     * @return byte[]
     */
    private void postRequestBody(String urlString, RequestBody requestBody,final Func4 callback, Object tag,String accesskey
    		,boolean isKey) {
        Request request = buildPostRequest(urlString, requestBody, tag,accesskey,isKey);
        client.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Response response) throws IOException {
				// TODO Auto-generated method stub
				if (response != null && response.isSuccessful()) {
            		onSuccessJsonObject(response.body().string(), callback);
            	}else{
            		onSuccessJsonObject("{\"error\":\"error\"}", callback);
            	}
			}
			
			@Override
			public void onFailure(Request arg0, IOException arg1) {
				// TODO Auto-generated method stub
				
			}
		});
    }
    
    //取消队列中的请求
    public void cancel(Object tag){
    	if(client!=null){
    		client.cancel(tag);
    	}
    }

}
