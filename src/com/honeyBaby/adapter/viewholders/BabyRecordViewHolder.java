package com.honeyBaby.adapter.viewholders;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hb.lovebaby.R;
import com.hb.lovebaby.activity.ZoomActivity;
import com.hb.lovebaby.modle.BabyRecord;
import com.honeyBaby.ninegrid.ItemImageClickListener;
import com.honeyBaby.ninegrid.NineGridImageView;
import com.honeyBaby.ninegrid.NineGridImageViewAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;


public class BabyRecordViewHolder extends BabyViewHolder {

	public NineGridImageView<String> imageView ;
	
	public BabyRecordViewHolder(View view) {
		super(view);
		imageView = findViewById(R.id.imageview_1) ;
        imageView.setAdapter(new NineGridImageViewAdapter() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, Object o) {
                ImageLoader.getInstance().displayImage(TEST_IMG, imageView);
            }
        });
	}
	
	
	public static final String TEST_IMG = "http://f.hiphotos.baidu.com/image/pic/item/b151f8198618367a9f738e022a738bd4b21ce573.jpg";
	

    @Override
    protected void onBind(BabyRecord record) {
        super.onBind(record);
        imageView.setImagesData(record.photos);
        imageView.setItemImageClickListener(new ItemImageClickListener<String>() {
            @Override
            public void onItemImageClick(Context context, ImageView imageView, int index, List<String> list) {
                clickImageItem(imageView, list, TEST_IMG);
            }
        });
    }

    @Override
	public void hideImage() {
		imageView.setVisibility(View.GONE);
	}


    private void clickImageItem(View view, List<String> photos, String currentUrl) {
        Intent _Intent = new Intent();
        _Intent.setClass(view.getContext(), ZoomActivity.class);

        int[] location = new int[2];

        view.getLocationOnScreen(location);

        _Intent.putExtra("locationX", location[0]);
        _Intent.putExtra("locationY", location[1]);
        _Intent.putExtra("width", view.getWidth());
        _Intent.putExtra("height", view.getHeight());

        _Intent.putExtra("currentUrl", currentUrl);
        Bundle b = new Bundle();
        List<String> ps = new ArrayList<String>();
        int j = 0;
        for(String url : photos ){
            ps.add(url);
            if(url.equals(currentUrl)){
                _Intent.putExtra("tag", j);
            }
            j++;
        }
        b.putSerializable("photos", (Serializable) ps);
        _Intent.putExtras(b);
        view.getContext().startActivity(_Intent);
    }
}
