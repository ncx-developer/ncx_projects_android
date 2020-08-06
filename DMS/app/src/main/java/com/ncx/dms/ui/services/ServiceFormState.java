package com.ncx.dms.ui.services;

import androidx.annotation.Nullable;

import com.ncx.dms.core.BaseFormState;

public class ServiceFormState extends BaseFormState {
    @Nullable
    private String dealerCodeError;
    @Nullable
    private String mechanicGuidError;
    @Nullable
    private String frameNoError;

    public ServiceFormState(@Nullable String dealerCodeError, @Nullable String mechanicGuidError, @Nullable String frameNoError) {
        this.dealerCodeError = dealerCodeError;
        this.mechanicGuidError = mechanicGuidError;
        this.frameNoError = frameNoError;
        this.isDataValid = false;
    }

    public ServiceFormState(boolean isDataValid) {
        dealerCodeError = null;
        mechanicGuidError = null;
        frameNoError = null;
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
}
