package com.ncx.dms.ui.services;

import androidx.annotation.Nullable;

import com.ncx.dms.core.BaseFormState;

public class McServiceDetailFormState extends BaseFormState {

    @Nullable
    private String partNoError;
    @Nullable
    private String priceError;
    @Nullable
    private String quantityError;

    public McServiceDetailFormState(@Nullable String partNoError, @Nullable String priceError, @Nullable String quantityError) {
        this.partNoError = partNoError;
        this.priceError = priceError;
        this.quantityError = quantityError;
    }

    public McServiceDetailFormState(boolean isDataValid) {
        partNoError = null;
        priceError = null;
        quantityError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    public String getPartNoError() {
        return partNoError;
    }

    public void setPartNoError(@Nullable String partNoError) {
        this.partNoError = partNoError;
    }

    @Nullable
    public String getPriceError() {
        return priceError;
    }

    public void setPriceError(@Nullable String priceError) {
        this.priceError = priceError;
    }

    @Nullable
    public String getQuantityError() {
        return quantityError;
    }

    public void setQuantityError(@Nullable String quantityError) {
        this.quantityError = quantityError;
    }
}
