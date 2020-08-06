package com.ncx.dms.core;

import com.ncx.dms.CallActivityMethod;
import com.ncx.dms.enums.ApiRequest;
import com.ncx.dms.remote.ApiService;
import com.ncx.dms.remote.App;
import com.ncx.dms.remote.dto.DealerDto;
import com.ncx.dms.remote.dto.UserDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseService<T> implements Callback<T> {

    protected App app;
    protected CallActivityMethod callActivityMethod;
    protected int requestId;

    public BaseService(App app, CallActivityMethod callActivityMethod){
        this.app = app;
        setCallActivityMethod(callActivityMethod);
    }

    protected void setCallActivityMethod(CallActivityMethod callActivityMethod){
        this.callActivityMethod = callActivityMethod;
    }

    protected void showSpotsDialog(){
        if (this.callActivityMethod != null)
            callActivityMethod.showSpotsDialog();
    }

    protected void dismissSpotsDialog(){
        if (this.callActivityMethod != null)
            callActivityMethod.dismissSpotsDialog();
    }

    protected UserDto getCurrentUser(){
        return app.getCurrentUser();
    }

    protected List<DealerDto> getCurrentDealers(){
        return app.getCurrentDealers();
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        dismissSpotsDialog();
        if (response.isSuccessful()) {
            callActivityMethod.apiResult(requestId, response);
        }else{
            if (callActivityMethod != null)
                callActivityMethod.apiError(requestId, response.message());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        dismissSpotsDialog();
        String url = ApiService.URL.replace("http://", "");
        url = url.replace("/","");
        String message = t.getMessage().replace(url, "");
        if (callActivityMethod != null){
            callActivityMethod.apiError(requestId, message);
        }
    }
}
