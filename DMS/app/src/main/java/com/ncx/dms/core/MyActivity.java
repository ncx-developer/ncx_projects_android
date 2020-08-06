package com.ncx.dms.core;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.ncx.dms.remote.dto.DealerDto;
import com.ncx.dms.remote.dto.McSparePart;
import com.ncx.dms.remote.dto.UserDto;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MyActivity implements IActivity {
    Context context;

    public MyActivity(Context context){
        this.context = context;

        initErrorHandling();
    }

    private void initErrorHandling() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                //Catch your exception
                // Without System.exit() this will not work.

                System.exit(2);
            }
        });
    }

    @Override
    public void initLoad() {

    }

    @Override
    public void initService() {

    }

    @Override
    public void findViewById() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initFillData() {

    }

    @Override
    public void initDatePicker(final EditText editText) {
        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateDateValue(editText, myCalendar);
            }

        };

        editText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(v.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        updateDateValue(editText, myCalendar);
    }

    @Override
    public void updateDateValue(EditText editText, Calendar calendar) {
        String myFormat = CommonHelper.dateFormat;
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        editText.setText(sdf.format(calendar.getTime()));
        editText.setTag(calendar.getTime());
    }

    @Override
    public void showShortToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLongToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public UserDto getCurrentUser() {
        return null;
    }

    @Override
    public List<DealerDto> getCurrentDealers() {
        return null;
    }

    @Override
    public List<McSparePart> getMcSpareParts() {
        return null;
    }
}
