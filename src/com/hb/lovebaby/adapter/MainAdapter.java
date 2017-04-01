package com.hb.lovebaby.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hb.lovebaby.R;
import com.hb.lovebaby.modle.BabyRecord;
import com.honeyBaby.adapter.viewholders.BabyRecordViewHolder;
import com.honeyBaby.adapter.viewholders.BabyViewHolder;

/**
 * @author 匿名
 *
 */
public class MainAdapter extends BaseAdapter {

    private final List<BabyRecord> mDataSet = new ArrayList<BabyRecord>();

    public MainAdapter( List<BabyRecord> data) {
        this.mDataSet.addAll(data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BabyViewHolder holder;
        if (convertView == null) {
            holder = createViewHolder(parent) ;
            convertView = holder.itemView;
            convertView.setTag(holder);
        } else {
            holder = (BabyViewHolder) convertView.getTag();
        }
        holder.bind(mDataSet.get(position));
        return convertView;
    }

    private BabyViewHolder createViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext()) ;
        BabyViewHolder holder = new BabyRecordViewHolder(inflater.inflate(R.layout.main_item_image1, parent, false)) ;
        return holder;
    }


    public void addAll(Collection<BabyRecord> newDatas) {
        mDataSet.addAll(newDatas) ;
        notifyDataSetChanged();
    }


    public void addItem(BabyRecord record) {
        mDataSet.add(record) ;
        notifyDataSetChanged();
    }
    
    public void addItemToHead(BabyRecord record) {
    	if ( mDataSet.size() > 0 ) {
    		mDataSet.add(0, record) ;
		} else {
			mDataSet.add(record) ;
		}
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mDataSet.size();
    }


    @Override
    public Object getItem(int position) {
        if (position < 0) {
            return null;
        }
        return mDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
