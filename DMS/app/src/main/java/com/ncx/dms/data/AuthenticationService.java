package com.ncx.dms.data;

import com.ncx.dms.CallActivityMethod;
import com.ncx.dms.core.BaseService;
import com.ncx.dms.remote.App;

public class AuthenticationService extends BaseService {

    public AuthenticationService(App app, CallActivityMethod callActivityMethod) {
        super(app, callActivityMethod);
    }

    public void login(int requestId, String userName, String password){
        showSpotsDialog();
        this.requestId = requestId;
        app.getApiService().login(userName, password).enqueue(this);
        /*app.getApiService().login(userName, password).enqueue(new Callback<ResultDto<UserDto>>() {
            @Override
            public void onResponse(Call<ResultDto<UserDto>> call, Response<ResultDto<UserDto>> response) {
                ResultDto<UserDto> result = response.body();
                dismissSpotsDialog();
                callActivityMethod.apiResult(requestId, response);
            }

            @Override
            public void onFailure(Call<ResultDto<UserDto>> call, Throwable t) {
                dismissSpotsDialog();
                callActivityMethod.apiError(requestId, t.getMessage());
            }
        });*/
    }
}
