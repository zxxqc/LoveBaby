package com.hb.lovebaby.activity;



import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.hb.lovebaby.R;
import com.hb.lovebaby.adapter.BasePagerAdapter.OnItemChangeListener;
import com.hb.lovebaby.adapter.UrlPagerAdapter;
import com.hb.lovebaby.view.GalleryViewPager;


public class ZoomActivity extends Activity {
	  private GalleryViewPager mViewPager;
	    private TextView titlebar_title;
	    private  List<String> list_imgs;
	    private int my_position=0;
	    @SuppressWarnings("unchecked")
		public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.zoom);

	        Intent intent=getIntent();
	        titlebar_title=(TextView) findViewById(R.id.titlebar_title);
	        list_imgs = (List<String>) intent.getSerializableExtra("photos");
	        my_position=intent.getIntExtra("tag", my_position);

	        UrlPagerAdapter pagerAdapter = new UrlPagerAdapter(ZoomActivity.this, list_imgs);
	        pagerAdapter.setOnItemChangeListener(new OnItemChangeListener()
			{
				@Override
				public void onItemChange(int currentPosition)
				{
					titlebar_title.setText((currentPosition + 1) + " / " + list_imgs.size());
				}
			});

	        titlebar_title.setText((my_position + 1) + " / " + list_imgs.size());
	        mViewPager = (GalleryViewPager)findViewById(R.id.viewer);
	        mViewPager.setOffscreenPageLimit(1);
	        mViewPager.setAdapter(pagerAdapter);
	        mViewPager.setCurrentItem(my_position, true);
	        
	    }

}