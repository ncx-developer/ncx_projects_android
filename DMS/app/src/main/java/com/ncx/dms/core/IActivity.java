package com.ncx.dms.core;

import android.widget.EditText;

import com.ncx.dms.remote.dto.DealerDto;
import com.ncx.dms.remote.dto.McSparePart;
import com.ncx.dms.remote.dto.UserDto;

import java.util.Calendar;
import java.util.List;

public interface IActivity {

    void initLoad();

    void initService();

    void findViewById();

    void initListener();

    void initFillData();

    void initDatePicker(final EditText editText);

    void updateDateValue(EditText editText, Calendar calendar);

    void showShortToast(String message);

    void showLongToast(String message);

    UserDto getCurrentUser();

    List<DealerDto> getCurrentDealers();

    List<McSparePart> getMcSpareParts();
}
