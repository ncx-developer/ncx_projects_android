package com.ncx.dms.remote.dto;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.UUID;

public class WarrantyRegistrationRequest {
    @NotNull
    private String dealerCode;
    @NotNull
    private String frameNo;
    private String registrationNo;
    @NotNull
    private Date purchasedDate;
    @NotNull
   // @SerializedName("mechanicGuid")
    private String mechanicCode;
    private String customerName;
    private String customerAddress;
    private String customerPhoneNo;
    private boolean isWholesale;
    private String wholesaleName;
    private String wholesaleAddress;
    private String wholesalePhoneNo;

    public WarrantyRegistrationRequest() {
    }

    public WarrantyRegistrationRequest(@NotNull String dealerCode,@NotNull String frameNo,@NotNull String registrationNo,@NotNull Date purchasedDate,@NotNull String mechanicCode, String customerName, String customerAddress, String customerPhoneNo, boolean isWholesale, String wholesaleName, String wholesaleAddress, String wholesalePhoneNo) {
        this.dealerCode = dealerCode;
        this.frameNo = frameNo;
        this.registrationNo = registrationNo;
        this.purchasedDate = purchasedDate;
        this.mechanicCode = mechanicCode;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPhoneNo = customerPhoneNo;
        this.isWholesale = isWholesale;
        this.wholesaleName = wholesaleName;
        this.wholesaleAddress = wholesaleAddress;
        this.wholesalePhoneNo = wholesalePhoneNo;
    }

    @NotNull
    public String getDealerCode() {
        return dealerCode;
    }

    @NotNull
    public String getFrameNo() {
        return frameNo;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    @NotNull
    public Date getPurchasedDate() {
        return purchasedDate;
    }

    @NotNull
    public String getMechanicCode() {
        return mechanicCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getCustomerPhoneNo() {
        return customerPhoneNo;
    }

    public boolean isWholesale() {
        return isWholesale;
    }

    public String getWholesaleName() {
        return wholesaleName;
    }

    public String getWholesaleAddress() {
        return wholesaleAddress;
    }

    public String getWholesalePhoneNo() {
        return wholesalePhoneNo;
    }

    public void setDealerCode(@NotNull String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public void setFrameNo(@NotNull String frameNo) {
        this.frameNo = frameNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public void setPurchasedDate(@NotNull Date purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public void setMechanicCode(@NotNull String mechanicCode) {
        this.mechanicCode = mechanicCode;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public void setCustomerPhoneNo(String customerPhoneNo) {
        this.customerPhoneNo = customerPhoneNo;
    }

    public void setWholesale(boolean wholesale) {
        isWholesale = wholesale;
    }

    public void setWholesaleName(String wholesaleName) {
        this.wholesaleName = wholesaleName;
    }

    public void setWholesaleAddress(String wholesaleAddress) {
        this.wholesaleAddress = wholesaleAddress;
    }

    public void setWholesalePhoneNo(String wholesalePhoneNo) {
        this.wholesalePhoneNo = wholesalePhoneNo;
    }
}
