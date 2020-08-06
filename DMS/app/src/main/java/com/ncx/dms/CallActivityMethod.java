package com.ncx.dms;

import com.ncx.dms.remote.dto.ResultDto;

import retrofit2.Response;

public interface CallActivityMethod<T> {
    void showSpotsDialog();

    void dismissSpotsDialog();

    void apiResult(int requestId, Response<T> result);

    void apiError(int requestId, String message);
}
