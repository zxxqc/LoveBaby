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

public class HeightCurveFragment extends Fragment {

    private LinearLayout heightcurveshow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2, container, false);
        heightcurveshow = (LinearLayout) view.findViewById(R.id.heightcurveshow);


        String[] title = new String[]{"Boby normal max height", "Boby normal actual height", "Boby normal min height"};
        List<double[]> x = new ArrayList<double[]>();
        /*
        这个赋值三遍x轴值，为了和y轴参数一一对应
         */


       /*
       * 死的数据不要动
       * */
        List<double[]> values = new ArrayList<double[]>();
        values.add(new double[]{60.3, 62.3, 65.5, 69.8, 75.8, 82.4, 87.4, 96.4, 101.1, 106.6, 109.3, 116.2,
                120.9});
        values.add(new double[]{36.5, 40, 50, 60, 63, 68, 75, 82, 88, 95, 102, 106, 110});
        values.add(new double[]{45, 50, 55, 60, 68, 75, 82});
        int[] colors = new int[]{Color.BLUE, Color.GREEN, Color.CYAN};
        PointStyle[] styles = new PointStyle[]{PointStyle.CIRCLE, PointStyle.DIAMOND,
                PointStyle.TRIANGLE};
        for (int i = 0; i < title.length - 1; i++) {
            x.add(new double[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
        }

        /*
        * 活的数据
        * */
        //动态加载要显示的数据，小孩子的自己数据显示一览表
        double[] bobyactualheight = new double[]{0, 1, 2, 3, 4, 5, 6};
        x.add(bobyactualheight);
        double[] PanLimit = new double[]{0, 12.5, 30, 130};

        //调用工具类方法
        AChartEngineUtil.setchart(getActivity(), heightcurveshow, title, styles, colors, x, values, "Month", "Height:(cm)", PanLimit,30,120);

        return view;
    }
}
