package com.ncx.dms.core;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ncx.dms.CallActivityMethod;
import com.ncx.dms.remote.App;
import com.ncx.dms.remote.dto.ResultDto;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import dmax.dialog.SpotsDialog;
import retrofit2.Response;

public abstract class BaseFragment extends Fragment implements CallActivityMethod, IActivity {

    protected View view;
    protected App app;
    protected CommonHelper commonHelper;
    private AlertDialog spotsDialog;

    IActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = new MyActivity(view.getContext());
        app = (App)getActivity().getApplication();
        commonHelper = new CommonHelper();

        spotsDialog = new SpotsDialog.Builder()
                .setContext(view.getContext())
                .build();

        initLoad();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //region IActivity


    @Override
    public void initLoad() {
        initService();
        findViewById();
        initListener();
        initFillData();
    }

    public void initDatePicker(EditText editText){
        activity.initDatePicker(editText);
    }

    public void updateDateValue(EditText editText, Calendar calendar){
        activity.updateDateValue(editText, calendar);
    }

    public void showShortToast(String message) {
        activity.showShortToast(message);
    }

    public void showLongToast(String message) {
        activity.showLongToast(message);
    }

    //endregion

    //region CallActivityMethod
    @Override
    public void showSpotsDialog() {
        spotsDialog.show();
    }

    @Override
    public void dismissSpotsDialog() {
        spotsDialog.dismiss();
    }

    @Override
    public abstract void apiResult(int requestId, Response result) ;

    @Override
    public abstract void apiError(int requestId, String result) ;

    //endregion
}
