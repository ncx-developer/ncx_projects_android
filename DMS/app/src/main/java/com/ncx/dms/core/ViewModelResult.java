package com.ncx.dms.core;

public class ViewModelResult<T> {
    private T resultObject;
    private String errorMessage;

    public ViewModelResult(T resultObject) {
        this.resultObject = resultObject;
    }

    public ViewModelResult(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getResultObject() {
        return resultObject;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setResultObject(T resultObject) {
        this.resultObject = resultObject;
    }

    public void setErrorObject(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
