package com.hb.lovebaby.activity.growth;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hb.lovebaby.R;


public class GrawthRecordActivity extends FragmentActivity  implements View.OnClickListener {
    //声明ViewPager
    private ViewPager mViewPager;
    //适配器
    private FragmentPagerAdapter mAdapter;
    //装载Fragment的集合
    private List<Fragment> mFragments;

    //四个Tab对应的布局
    private LinearLayout growthrecord;
    private LinearLayout heightcurve;
    private LinearLayout weightcurve;

    //四个Tab对应的ImageButton
    private ImageButton mImgGrowthrecord;
    private ImageButton mImgheightcurve;
    private ImageButton mImgweightcurve ,addrecord;
    private TextView title;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_grawth_record);

        initViews();//初始化控件
        initEvents();//初始化事件
        initDatas();//初始化数据
    }

    //初始化控件
    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        addrecord =(ImageButton)findViewById(R.id.addrecord);
        growthrecord = (LinearLayout) findViewById(R.id.id_tab_growth);
        heightcurve = (LinearLayout) findViewById(R.id.id_tab_heightcurve);
        weightcurve = (LinearLayout) findViewById(R.id.id_tab_weightcurve);
        mImgGrowthrecord = (ImageButton) findViewById(R.id.id_tab_growth_img);
        mImgheightcurve = (ImageButton) findViewById(R.id.id_tab_height_img);
        mImgweightcurve = (ImageButton) findViewById(R.id.id_tab_weight_img);

        title=(TextView) findViewById(R.id.title);

        mImgGrowthrecord.setImageResource(R.drawable.icon_growthrecord_1s);

    }
    @Override
    public void onClick(View v) {
        //ImageButton置为灰色
        resetImgs();

        //根据点击的Tab切换不同的页面及设置对应的ImageButton为绿色
        switch (v.getId()) {
            case R.id.id_tab_growth:
                selectTab(0);
                break;
            case R.id.id_tab_heightcurve:
                selectTab(1);
                break;
            case R.id.id_tab_weightcurve:
                selectTab(2);
                break;
            case R.id.addrecord:
                Intent intent=new Intent(GrawthRecordActivity.this, AddRecord.class);
                startActivity(intent);
                break;

        }
    }
    private void initDatas() {
        mFragments = new ArrayList<Fragment>();

        mFragments.add(new GrowthFragment());
        mFragments.add(new HeightCurveFragment());
        mFragments.add(new WeightCurveFragment());

        //初始化适配器
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {//从集合中获取对应位置的Fragment
                return mFragments.get(position);
            }

            @Override
            public int getCount() {//获取集合中Fragment的总数
                return mFragments.size();
            }

        };
        //不要忘记设置ViewPager的适配器
        mViewPager.setAdapter(mAdapter);
        //设置ViewPager的切换监听
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            //页面滚动事件
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //页面选中事件
            @Override
            public void onPageSelected(int position) {
                //设置position对应的集合中的Fragment
                mViewPager.setCurrentItem(position);
                resetImgs();
                selectTab(position);
            }

            @Override
            //页面滚动状态改变事件
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void initEvents() {
        //Tab的点击事件
        growthrecord.setOnClickListener(this);
        heightcurve.setOnClickListener(this);
        weightcurve.setOnClickListener(this);
        addrecord.setOnClickListener(this);

    }
    private void selectTab(int i) {
        //根据点击的Tab设置对应的ImageButton为绿色
        switch (i) {
            case 0:
                title.setText("Body growth record");
                mImgGrowthrecord.setImageResource(R.drawable.icon_growthrecord_1s);
                break;
            case 1:
                title.setText("Body height curve");
                mImgheightcurve.setImageResource(R.drawable.icon_height2s);
                break;
            case 2:
                title.setText("Body weight curve");
                mImgweightcurve.setImageResource(R.drawable.icon_weight3s);
                break;
        }
        //设置当前点击的Tab所对应的页面
        mViewPager.setCurrentItem(i);
    }
    //将四个ImageButton设置为灰色
    private void resetImgs() {
        mImgGrowthrecord.setImageResource(R.drawable.icon_growthrecord_1);
        mImgheightcurve.setImageResource(R.drawable.icon_height2);
        mImgweightcurve.setImageResource(R.drawable.icon_weight3);
    }

}
