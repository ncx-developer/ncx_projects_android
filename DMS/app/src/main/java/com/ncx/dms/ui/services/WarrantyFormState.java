package com.ncx.dms.ui.services;

import androidx.annotation.Nullable;

import com.ncx.dms.core.BaseFormState;

public class WarrantyFormState extends BaseFormState {
    @Nullable
    private String dealerCodeError;
    @Nullable
    private String mechanicGuidError;
    @Nullable
    private String frameNoError;
    @Nullable
    private String registrationNoError;

    public WarrantyFormState(@Nullable String dealerCodeError, @Nullable String mechanicGuidError, @Nullable String frameNoError,@Nullable String registrationNoError) {
        this.dealerCodeError = dealerCodeError;
        this.mechanicGuidError = mechanicGuidError;
        this.frameNoError = frameNoError;
        this.registrationNoError = registrationNoError;
        this.isDataValid = false;
    }

    public WarrantyFormState(boolean isDataValid) {
        dealerCodeError = null;
        mechanicGuidError = null;
        frameNoError = null;
        registrationNoError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    public String getDealerCodeError() {
        return dealerCodeError;
    }

    @Nullable
    public String getMechanicGuidError() {
        return mechanicGuidError;
    }

    @Nullable
    public String getFrameNoError() {
        return frameNoError;
    }

    @Nullable
    public String getRegistrationNoError() {
        return registrationNoError;
    }
}
