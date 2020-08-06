package com.ncx.dms.ui.services;

import com.ncx.dms.core.BaseViewModel;
import com.ncx.dms.remote.App;

import java.util.UUID;

public class WarrantyViewModel extends BaseViewModel {

    public WarrantyViewModel(App app) {
        super(app);
    }

    public void warrantyDataChanged(String dealerCode, String mechanicId, String frameNo, String registrationNo, Object product) {
        if (!isDealerCodeValid(dealerCode)) {
            formState.setValue(new WarrantyFormState("select dealer!", null, null, null));
        } else if (!isMechanicIdValid(mechanicId)) {
            formState.setValue(new WarrantyFormState(null, "select mechanic!", null, null));
        } else if (!isFrameNoValid(frameNo)) {
            formState.setValue(new WarrantyFormState(null, null, "Require FrameNo!", null));
        } else if (!isRegistrationNoValid(registrationNo)) {
            formState.setValue(new WarrantyFormState(null, null, null, "Require registration no!"));
        } else if (product == null) {
            formState.setValue(new WarrantyFormState(false));
        } else {
            formState.setValue(new WarrantyFormState(true));
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

    private boolean isRegistrationNoValid(String registrationNo) {
        if (commonHelper.isNullOrEmpty(registrationNo)){
            return false;
        }

        return true;
    }
}
