package com.ncx.dms.ui.services;

import com.ncx.dms.core.BaseViewModel;
import com.ncx.dms.remote.App;

import java.util.UUID;

public class ServiceViewModel extends BaseViewModel {

    public ServiceViewModel(App app) {
        super(app);
    }

    public void serviceDataChanged(String dealerCode, UUID mechanicId, String frameNo) {
        if (!isDealerCodeValid(dealerCode)) {
            formState.setValue(new ServiceFormState("select dealer!", null, null));
        } else if (!isMechanicIdValid(mechanicId)) {
            formState.setValue(new ServiceFormState(null, "select mechanic!", null));
        } else if (!isFrameNoValid(frameNo)) {
            formState.setValue(new ServiceFormState(null, null, "Require FrameNo!"));
        } else {
            formState.setValue(new ServiceFormState(true));
        }
    }

    private boolean isDealerCodeValid(String dealerCode) {
        if (dealerCode == null) {
            return false;
        }

        return !commonHelper.isNullOrEmpty(dealerCode);
    }

    private boolean isMechanicIdValid(UUID mechanicId) {
        if (mechanicId == null){
            return false;
        }

        return true;
    }

    private boolean isFrameNoValid(String frameNo) {
        if (commonHelper.isNullOrEmpty(frameNo)){
            return false;
        }

        return true;
    }
}
