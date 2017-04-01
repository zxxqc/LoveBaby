package com.honeyBaby.adapter.viewholders;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.TextView;

import com.hb.lovebaby.R;
import com.hb.lovebaby.modle.BabyRecord;
import com.hb.lovebaby.view.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class BabyViewHolder {
	public View itemView ;

    public TextView dayTextView;
    public TextView dateTextView;
    public TextView yearTextView;
    public TextView nameTextView;
    public TextView areaTextView;
    public RoundImageView avatarImageView ;

	
	public BabyViewHolder(View view) {
		itemView = view ;

        dayTextView = findViewById(R.id.main_item_day) ;
        dateTextView = findViewById(R.id.main_item_date) ;
        yearTextView = findViewById(R.id.main_item_year) ;
        nameTextView = findViewById(R.id.main_item_name) ;
        areaTextView = findViewById(R.id.main_item_area) ;
        avatarImageView = findViewById(R.id.main_item_avatar) ;
	}
	
	
	public final void bind(BabyRecord record) {
        if (record.photos.size() > 0) {
            onBind(record);
        } else {
            hideImage();
        }
	}

    protected void onBind(BabyRecord record) {
        dayTextView.setText(record.day);
        dateTextView.setText(record.date);
        yearTextView.setText(record.year);
        nameTextView.setText(record.name);
        areaTextView.setText(record.area);
        ImageLoader.getInstance().loadImage(record.avatar, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                avatarImageView.setImageResource(R.drawable.baby_tx);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                avatarImageView.setImageResource(R.drawable.baby_tx);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                avatarImageView.setImageBitmap(bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                avatarImageView.setImageResource(R.drawable.baby_tx);
            }
        });
//        ImageLoader.getInstance().displayImage(record.avatar, avatarImageView);
    }
	
	protected void hideImage() {
		
	}
	
	
	protected <T extends View> T  findViewById(int viewId) {
		return (T)itemView.findViewById(viewId);
	}
	
}
