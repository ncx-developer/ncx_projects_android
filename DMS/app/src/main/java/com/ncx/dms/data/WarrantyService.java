package com.ncx.dms.data;

import com.ncx.dms.CallActivityMethod;
import com.ncx.dms.core.BaseService;
import com.ncx.dms.remote.App;
import com.ncx.dms.remote.dto.WarrantyRegistrationRequest;

public class WarrantyService extends BaseService {

    public WarrantyService(App app, CallActivityMethod callActivityMethod) {
        super(app, callActivityMethod);
    }

    public void verifyFrameNo(int requestId, String frameNo){
        showSpotsDialog();
        this.requestId = requestId;
        app.getApiService().verifyFrameNo(frameNo).enqueue(this);
        /*app.getApiService().verifyFrameNo(frameNo).enqueue(new Callback<ResultDto<McOrderDetailDto>>() {
            @Override
            public void onResponse(Call<ResultDto<McOrderDetailDto>> call, Response<ResultDto<McOrderDetailDto>> response) {
                //ResultDto<McOrderDetailDto> result = response.body();
                dismissSpotsDialog();
                callActivityMethod.apiResult(requestId, response);
            }

            @Override
            public void onFailure(Call<ResultDto<McOrderDetailDto>> call, Throwable t) {
                dismissSpotsDialog();
                callActivityMethod.apiError(requestId, t.getMessage());
            }
        });*/
    }

    public void getAvailableFrmeNos(int requestId, String dealerCode){
        showSpotsDialog();
        this.requestId = requestId;
        app.getApiService().getAvailableFrameNos(dealerCode).enqueue(this);
    }

    public void warrantyRegister(int requestId, WarrantyRegistrationRequest request){
        showSpotsDialog();
        this.requestId = requestId;
        app.getApiService().warrantyRegister(request).enqueue(this);
    }
}
