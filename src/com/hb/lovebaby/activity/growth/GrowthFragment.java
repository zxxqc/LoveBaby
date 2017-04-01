package com.hb.lovebaby.activity.growth;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.lovebaby.R;


public class GrowthFragment extends Fragment {
    private static PopupWindow popupWindow;

    Bean bean1 = new Bean("40.6","nice的妹子","15.0","2016年11月3日");
    Bean bean2 = new Bean("45.9","很好看的妹子","16.3","2016年11月3日");
    Bean bean3 = new Bean("58.6","你是谁呀","17.8","2016年11月3日");
    Bean bean4 = new Bean("62.0","不一样的","18.3","2016年11月3日");
    Bean bean5 = new Bean("65.3","开始的不一样的","19.3","2016年11月3日");
    Bean bean6 = new Bean("65.8","很看的不一样","20.6","2016年11月3日");
    Bean bean7 = new Bean("78.6","哎呀，不错呀","25.3","2016年11月3日");
    Bean bean8 = new Bean("90.6","你有问吗","12.3","2016年11月3日");
    private List<Bean> mDatas = new ArrayList<Bean>(
    );

    private RecyclerAdapter<Bean> TextAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1, container, false);
        RecyclerView rv_list =(RecyclerView) view.findViewById(R.id.id_lv_main);
        //间距
        rv_list.addItemDecoration(new SpaceItemDecoration(20));
        mDatas.add(bean1);
        mDatas.add(bean2);
        mDatas.add(bean3);
        mDatas.add(bean4);
        mDatas.add(bean5);
        mDatas.add(bean6);
        mDatas.add(bean7);
        mDatas.add(bean8);
        mDatas.add(bean1);
        mDatas.add(bean2);
        mDatas.add(bean3);
        mDatas.add(bean4);
        mDatas.add(bean5);
        mDatas.add(bean6);
        mDatas.add(bean7);
        mDatas.add(bean8);

        rv_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_list.setAdapter(TextAdapter = new RecyclerAdapter<Bean>(getActivity(), mDatas, R.layout.item_single_str) {
            @Override
            public void convert(RecycleHolder holder, Bean data, int position) {
                holder.setText(R.id.tv_title, data.getTitle());
                holder.setText(R.id.tv_describe, data.getDesc());
                holder.setText(R.id.tv_phone, data.getPhone());
                holder.setText(R.id.tv_time, data.getTime());

            }
        });
        TextAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(View view, int position) {
                ToastShow("点击" + position);
                showPopupWindow(view);
            }

        });

        return view;
    }

    private void showPopupWindow(View view){
        View contentView = LayoutInflater.from(getActivity()).inflate(
                R.layout.popwindow, null);
        popupWindow = new PopupWindow(contentView, 800, 600, true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
//
        TextView modify = (TextView) contentView.findViewById(R.id.modify);
        TextView delete = (TextView) contentView.findViewById(R.id.delete);
        TextView cancel = (TextView) contentView.findViewById(R.id.cancel);
        modify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                disPop();
                Intent modifyactiviity=new Intent(getActivity(),ModifyActivity.class);
                startActivity(modifyactiviity);

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                disPop();
                showNormalDialog();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                disPop();
            }
        });

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.ic_launcher));
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        backgroundAlpha(0.4f);

    }
    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getActivity());
        normalDialog.setTitle("Prompt");
        normalDialog.setMessage("Are you sure delete this recording?");

        normalDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }
    private void disPop(){
        popupWindow.dismiss();
        backgroundAlpha(1f);
    }
    private void backgroundAlpha(float f) {
        WindowManager.LayoutParams lp =getActivity().getWindow().getAttributes();
        lp.alpha = f;
        getActivity().getWindow().setAttributes(lp);
    }
    Toast toast;
    public void ToastShow(String Text) {
        if (toast == null) {
            toast = Toast.makeText(getActivity(), Text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(Text);
        }
        toast.show();
    }


}
