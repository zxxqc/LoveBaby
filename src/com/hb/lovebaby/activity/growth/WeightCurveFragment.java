package com.hb.lovebaby.activity.growth;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.achartengine.chart.PointStyle;

import com.hb.lovebaby.R;

import java.util.ArrayList;
import java.util.List;



public class WeightCurveFragment extends Fragment {

    private LinearLayout weightcurveshow;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3, container, false);

        weightcurveshow = (LinearLayout) view.findViewById(R.id.weightcurveshow);


        String[] title = new String[]{"Boby normal max weight", "Boby normal actual weight", "Boby normal min weight"};
        List<double[]> x = new ArrayList<double[]>();



       /*
       * 死的数据不要动
       * */
        List<double[]> values = new ArrayList<double[]>();
        values.add(new double[]{5.3, 8.3, 9.5, 10.8, 12.8, 13.4, 15.4, 16.4, 17.1, 18.6, 19.3, 20.2,
                22.9});
        values.add(new double[]{2.5, 3.6, 4.7, 5.6, 6.5, 7.8, 8.4, 10.6, 11.5, 13.6, 14.5, 16.2, 17.5});

        int[] colors = new int[]{Color.BLUE, Color.GREEN, Color.CYAN};
        PointStyle[] styles = new PointStyle[]{PointStyle.CIRCLE, PointStyle.DIAMOND,
                PointStyle.TRIANGLE};
        for (int i = 0; i < title.length - 1; i++) {
            x.add(new double[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
        }

        /*
        *这个赋值三遍x轴值，为了和y轴参数一一对应
        * 活的数据
        * */
        //动态加载要显示的数据，小孩子的自己数据显示一览表
        double[] bobyactualheight = new double[]{0, 1, 2, 3, 4, 5, 6};
        x.add(bobyactualheight);

        values.add(new double[]{5, 6, 8, 9, 10, 11, 13});


        double[] PanLimit = new double[]{0, 12.5, 0, 45};

        //调用工具类方法
        AChartEngineUtil.setchart(getActivity(), weightcurveshow, title, styles, colors, x, values, "Month", "weight:(Kg)", PanLimit,0,45);



















        return view;
    }
}
