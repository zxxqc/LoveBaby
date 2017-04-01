package com.hb.lovebaby.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hb.lovebaby.R;
import com.hb.lovebaby.util.StringUtils;
import com.hb.lovebaby.view.MyActionBar;

public class RelativesRelationshipActivity extends BascActivity {
	
	private ListView relationList;
	private MyActionBar actionbar;
	private RelationListAdapter adapter;
	private String select_type;//关系和区域共用
	private List<String> list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.relatives_relationship);
		list = new ArrayList<String>();
		initViews();
		populateData();
	}
	
	private void initViews() {
		select_type=getIntent().getStringExtra("select_type");
		relationList = (ListView) findViewById(R.id.relatives_relationship_list);
		adapter = new RelationListAdapter(this, list);
		relationList.setAdapter(adapter);
		actionbar=(MyActionBar) findViewById(R.id.action_bar);
		relationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position == list.size() - 1) {
					Intent intent = new Intent(RelativesRelationshipActivity.this, RelativesCustomRelationshipActivity.class);
					startActivity(intent);
				} else {
					finish();
				}
			}
		});
	}
	
	private void populateData() {
		if (!StringUtils.isEmpty(select_type)&&
				select_type.equals("select_area")) {
			actionbar.setTitle(getResources().getString(R.string.a_total_of));
			String []array_one  =getResources().getStringArray(R.array.area_list);
			for (String it : array_one) {
				list.add(it);
			}
		}else{
			String []arrays_two = {
				getResources().getString(R.string.mom),
				getResources().getString(R.string.dad),
				getResources().getString(R.string.grandpa),
				getResources().getString(R.string.grandma),
				getResources().getString(R.string.uncle),
				getResources().getString(R.string.aunt),
				getResources().getString(R.string.adopted_mother),
				getResources().getString(R.string.adopted_father),
				getResources().getString(R.string.elder_sister),
				getResources().getString(R.string.elder_brother),
				getResources().getString(R.string.younger_sister),
				getResources().getString(R.string.younger_brother),
				getResources().getString(R.string.other),
				getResources().getString(R.string.custom)
		};
			for (String it : arrays_two) {
				list.add(it);
			}
		}
	}
	
	private class RelationListAdapter extends ArrayAdapter<String> {
		
		private LayoutInflater inflater = LayoutInflater.from(RelativesRelationshipActivity.this);

		public RelationListAdapter(Context context, List<String> objects) {
			super(context, -1, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = inflater.inflate(R.layout.selection_list_item, parent, false);
			TextView tv = (TextView) view.findViewById(R.id.relationship);
			tv.setText(getItem(position));
			return view;
		}
		
		
	}
}
