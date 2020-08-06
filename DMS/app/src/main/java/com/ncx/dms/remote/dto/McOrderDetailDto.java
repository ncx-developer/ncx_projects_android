package com.ncx.dms.remote.dto;

public class McOrderDetailDto {
    private String dealerCode;
    private String productName;
    private String frameNo;
    private String engineNo;

    public McOrderDetailDto() {
    }

    public McOrderDetailDto(String dealerCode, String productName, String frameNo, String engineNo) {
        this.dealerCode = dealerCode;
        this.productName = productName;
        this.frameNo = frameNo;
        this.engineNo = engineNo;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public String getProductName() {
        return productName;
    }

    public String getFrameNo() {
        return frameNo;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setFrameNo(String frameNo) {
        this.frameNo = frameNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }
}
