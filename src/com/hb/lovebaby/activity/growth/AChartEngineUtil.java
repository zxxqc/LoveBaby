package com.hb.lovebaby.activity.growth;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.View;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Administrator on 2017/3/21.
 */

public class AChartEngineUtil {


    public static List<double[]> getlist(int size, List<double[]> values) {
        List<double[]> xy = new ArrayList<double[]>();

        double[] list = new double[size];
        for (int i = 0; i < size; i++) {
            list[i] = (values.isEmpty()) ? i : Double.valueOf(i);
        }

        xy.add(list);

        return xy;
    }


    /**
     * 构建XYMultipleSeriesRenderer.
     *
     * @param colors 每个序列的颜色
     * @param styles 每个序列点的类型(可设置三角,圆点,菱形,方块等多种)
     *               ( PointStyle.CIRCLE, PointStyle.DIAMOND,PointStyle.TRIANGLE, PointStyle.SQUARE )
     * @return XYMultipleSeriesRenderer
     */
    public static XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        // 控制横纵轴的属性字大小
        renderer.setAxisTitleTextSize(40);
        // 控制横纵轴的值大小
        renderer.setChartTitleTextSize(50);
        renderer.setLabelsTextSize(40);
        renderer.setLegendTextSize(40);
        renderer.setPointSize(10f);


        renderer.setXLabels(12);
        renderer.setYLabels(10);
        renderer.setZoomButtonsVisible(false);
        renderer.setShowGrid(true);
        renderer.setChartTitleTextSize(0);
        renderer.setApplyBackgroundColor(true);
        renderer.setBackgroundColor(Color.parseColor("#F5F5DC"));
        renderer.setMarginsColor(Color.WHITE);
        renderer.setXLabelsAlign(Align.RIGHT);
        renderer.setYLabelsAlign(Align.RIGHT);
        renderer.setZoomButtonsVisible(true);


        renderer.setMargins(new int[]
                {100, 100, 100, 60});
        int length = colors.length;
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(colors[i]);
            r.setPointStyle(styles[i]);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }

    /**
     * 设置renderer的一些坐标轴属性.
     *
     * @param renderer    要设置的renderer
     * @param title       图表标题
     * @param xTitle      X轴标题
     * @param yTitle      Y轴标题
     * @param xMin        X轴最小值
     * @param xMax        X轴最大值
     * @param yMin        Y轴最小值
     * @param yMax        Y轴最大值
     * @param axesColor   X轴颜色
     * @param labelsColor Y轴颜色
     */
    public static void setChartSettings(XYMultipleSeriesRenderer renderer,
                                        String title, String xTitle, String yTitle, double xMin,
                                        double xMax, double yMin, double yMax, int axesColor,
                                        int labelsColor) {
        renderer.setChartTitle(title);
        renderer.setXTitle(xTitle);
        renderer.setYTitle(yTitle);
        renderer.setXAxisMin(xMin);
        renderer.setXAxisMax(xMax);
        renderer.setYAxisMin(yMin);
        renderer.setYAxisMax(yMax);
        renderer.setAxesColor(axesColor);
        renderer.setLabelsColor(labelsColor);
    }

    /**
     * 构建和时间有关的XYMultipleSeriesDataset,这个方法与buildDataset在参数上区别是需要List<Date[]>作参数.
     *
     * @param titles  序列图例
     * @param xValues X轴值
     * @param yValues Y轴值
     * @return XYMultipleSeriesDataset
     */
    public static XYMultipleSeriesDataset buildDataset(String[] titles,
                                                       List<double[]> xValues, List<double[]> yValues) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        int length = titles.length;
        for (int i = 0; i < length; i++) {
            XYSeries series = new XYSeries(titles[i]);
            double[] xV = xValues.get(i);
            double[] yV = yValues.get(i);
            int seriesLength = xV.length;
            for (int k = 0; k < seriesLength; k++) {
                series.add(xV[k], yV[k]);
            }
            dataset.addSeries(series);
        }
        return dataset;
    }


    public static void setchart(Context context, LinearLayout layout, String[] titles, PointStyle[] styles, int[] colors,
                                List<double[]> x ,List<double[]> value,String xname, String yname,double[] PanLimit,double yMin, double yMax) {

        XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);

        int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
            ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
        }
        setChartSettings(renderer, "Boby height curve", xname, yname,0, 12.5,yMin, yMax,
                Color.GREEN, Color.GREEN);

        renderer.setPanLimits(PanLimit);
        renderer.setZoomLimits(PanLimit);

        //构建view
        View v = ChartFactory.getLineChartView(context, buildDataset(titles, x, value), renderer);
        layout.addView(v);
    }
}
