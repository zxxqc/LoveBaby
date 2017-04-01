package com.hb.lovebaby.activity.growth;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.hb.lovebaby.R;

public class ModifyActivity extends Activity implements View.OnClickListener{
    private TextView  recordDate,cancel,delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify);

        initView();
        initEvents();

    }

    private void initView(){
        recordDate=(TextView)findViewById(R.id.recorddate);
        cancel =(TextView)findViewById(R.id.cancel_action);
        delete=(TextView)findViewById(R.id.delete);
    }

    private void initEvents(){
        recordDate.setOnClickListener(this);
        cancel.setOnClickListener(this);
        delete.setOnClickListener(this);

    }
    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(ModifyActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //加一个月，bug
                recordDate.setText( monthOfYear+1 + "-" + dayOfMonth+ "-" +year);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recorddate:
                showDatePickDlg();
                break;
            case R.id.cancel_action:
                finish();
                break;
            case R.id.delete:
                showNormalDialog();
                break;

        }
    }

    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(ModifyActivity.this);
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
}
