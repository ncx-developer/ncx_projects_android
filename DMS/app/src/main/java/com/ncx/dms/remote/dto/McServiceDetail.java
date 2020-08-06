package com.ncx.dms.remote.dto;

public class McServiceDetail {
    public String InvoiceNo ;
    public String PartNo ;
    public String FullDescription ;
    public String ShortDescription ;
    public double UnitPrice ;
    public int Quantity;
    public int DiscountPercentage ;
    public double DiscountAmount ;
    public double GrossAmount ;
    public double NetAmount ;

    public McServiceDetail() {
    }

    public McServiceDetail(String invoiceNo, String partNo, String fullDescription, String shortDescription, double unitPrice, int quantity, int discountPercentage, double discountAmount, double grossAmount, double netAmount) {
        InvoiceNo = invoiceNo;
        PartNo = partNo;
        FullDescription = fullDescription;
        ShortDescription = shortDescription;
        UnitPrice = unitPrice;
        Quantity = quantity;
        DiscountPercentage = discountPercentage;
        DiscountAmount = discountAmount;
        GrossAmount = grossAmount;
        NetAmount = netAmount;
    }

    public String getInvoiceNo() {
        return InvoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        InvoiceNo = invoiceNo;
    }

    public String getPartNo() {
        return PartNo;
    }

    public void setPartNo(String partNo) {
        PartNo = partNo;
    }

    public String getFullDescription() {
        return FullDescription;
    }

    public void setFullDescription(String fullDescription) {
        FullDescription = fullDescription;
    }

    public String getShortDescription() {
        return ShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        ShortDescription = shortDescription;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getDiscountPercentage() {
        return DiscountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        DiscountPercentage = discountPercentage;
    }

    public double getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        DiscountAmount = discountAmount;
    }

    public double getGrossAmount() {
        return GrossAmount;
    }

    public void setGrossAmount(double grossAmount) {
        GrossAmount = grossAmount;
    }

    public double getNetAmount() {
        return NetAmount;
    }

    public void setNetAmount(double netAmount) {
        NetAmount = netAmount;
    }
}
