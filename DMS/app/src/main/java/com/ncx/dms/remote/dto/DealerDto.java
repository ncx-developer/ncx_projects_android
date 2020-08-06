package com.ncx.dms.remote.dto;

import androidx.annotation.NonNull;

import com.ncx.dms.core.BaseDto;

public class DealerDto extends BaseDto {
    private String dealerCode;

    private String name;

    public DealerDto() {
    }

    public DealerDto(String dealerCode, String name) {
        this.dealerCode = dealerCode;
        this.name = name;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public String getName() {
        return name;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return name.toUpperCase();
    }
}
