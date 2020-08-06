package com.ncx.dms.core;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ncx.dms.CallActivityMethod;
import com.ncx.dms.remote.App;
import com.ncx.dms.ui.main.MainActivity;

import java.util.Calendar;

import dmax.dialog.SpotsDialog;
import retrofit2.Response;

public abstract class BaseActivity extends AppCompatActivity implements CallActivityMethod, IActivity {

    protected App app;
    protected CommonHelper commonHelper;
    private AlertDialog spotsDialog;

    IActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        activity = new MyActivity(this);
        app = (App)this.getApplication();
        spotsDialog = new SpotsDialog.Builder()
                .setContext(this)
                .build();

        commonHelper = new CommonHelper();

        initLoad();

        super.onCreate(savedInstanceState);
    }

    protected void setDisplayHomeAsUpEnabled(boolean value){
        getSupportActionBar().setDisplayHomeAsUpEnabled(value);
    }

    protected void hideActionBar(){
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
    }

    protected void setFullScreen(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    protected void setKeepScreenOn(){
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    protected void setLandscape(){
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public void setActivityTitle(String title){
        setTitle(title);
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

    protected void callMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

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
    public abstract void apiResult(int requestId, Response result);

    @Override
    public abstract void apiError(int requestId, String result);

    //endregion
}
