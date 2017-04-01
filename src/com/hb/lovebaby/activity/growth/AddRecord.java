package com.hb.lovebaby.activity.growth;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.hb.lovebaby.R;


public class AddRecord extends Activity  implements View.OnClickListener{
    private TextView  recordDate,cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        initView();
        initEvents();

    }

    private void initView(){
        recordDate=(TextView)findViewById(R.id.recorddate);
        cancel =(TextView)findViewById(R.id.cancel_action);
    }

    private void initEvents(){
        recordDate.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }
    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddRecord.this, new DatePickerDialog.OnDateSetListener() {

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

        }
    }

}
