package com.ncx.dms.remote.dto;

public class ResultDto<T> {
    private boolean success;

    private String message;

    private T resultObject;

    public ResultDto() {
    }

    public ResultDto(boolean success, String message, T resultObject) {
        this.success = success;
        this.message = message;
        this.resultObject = resultObject;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResultObject(T resultObject) {
        this.resultObject = resultObject;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getResultObject() {
        return resultObject;
    }
}
