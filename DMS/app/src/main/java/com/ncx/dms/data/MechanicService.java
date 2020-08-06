package com.ncx.dms.data;

import com.ncx.dms.CallActivityMethod;
import com.ncx.dms.core.BaseService;
import com.ncx.dms.remote.App;

public class MechanicService extends BaseService {



    public MechanicService(App app, CallActivityMethod callActivityMethod) {
        super(app, callActivityMethod);
    }

    public void getMechanicByDealerCode(int requestId, String dealerCode){
        showSpotsDialog();
        this.requestId = requestId;
        app.getApiService().getMechanicByDealerCode(dealerCode).enqueue(this);

       /* showSpotsDialog();
        app.getApiService().getMechanicByDealerCode(dealerCode).enqueue(new Callback<ResultDto<List<MechanicDto>>>() {
            @Override
            public void onResponse(Call<ResultDto<List<MechanicDto>>> call, Response<ResultDto<List<MechanicDto>>> response) {
                ResultDto<List<MechanicDto>> result = response.body();
                dismissSpotsDialog();
                callActivityMethod.apiResult(requestId, result);
            }

            @Override
            public void onFailure(Call<ResultDto<List<MechanicDto>>> call, Throwable t) {
                dismissSpotsDialog();
                callActivityMethod.apiError(requestId, t.getMessage());
            }
        });*/
    }

    public void getAllDealers(int requestId){
        showSpotsDialog();
        this.requestId = requestId;
        app.getApiService().GetAllDealers().enqueue(this);
    }
}
