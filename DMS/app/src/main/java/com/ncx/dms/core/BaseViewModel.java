package com.ncx.dms.core;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.internal.$Gson$Preconditions;
import com.ncx.dms.CallActivityMethod;
import com.ncx.dms.remote.App;

public class BaseViewModel<T> extends ViewModel {
    protected App app;
    protected CommonHelper commonHelper;
    protected CallActivityMethod callActivityMethod;
    protected MutableLiveData<ViewModelResult> viewModelResult = new MutableLiveData<>();
    protected MutableLiveData<T> formState = new MutableLiveData<>();

    //protected CompositeDisposable compositeDisposable = new CompositeDisposable();
    public BaseViewModel(App app){
        this.app = app;
        this.commonHelper = new CommonHelper();
    }

    public void setCallActivityMethod(CallActivityMethod callActivityMethod){
        this.callActivityMethod = callActivityMethod;
    }

    public LiveData<ViewModelResult> getViewModelResult() {
        return viewModelResult;
    }

    public LiveData<T> getFormState() {
        return formState;
    }

    protected void showSpotsDialog(){
        if (this.callActivityMethod != null)
            callActivityMethod.showSpotsDialog();
    }

    protected void dismissSpotsDialog(){
        if (this.callActivityMethod != null)
            callActivityMethod.dismissSpotsDialog();
    }
}
