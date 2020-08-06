package com.ncx.dms.data;

import com.ncx.dms.CallActivityMethod;
import com.ncx.dms.core.BaseService;
import com.ncx.dms.remote.App;

public class McCatalogService extends BaseService {

    public McCatalogService(App app, CallActivityMethod callActivityMethod) {
        super(app, callActivityMethod);
    }

    public void getMcSpareParts(int requestId){
        showSpotsDialog();
        this.requestId = requestId;
        app.getApiService().getMcSpareParts().enqueue(this);
    }
}
