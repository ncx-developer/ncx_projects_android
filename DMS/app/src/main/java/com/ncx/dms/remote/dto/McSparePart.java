package com.ncx.dms.remote.dto;

public class McSparePart {

    private String PartNo;
    private String FullDescription;
    private String ShortDescription;
    private int McPartCategoryId;

    public McSparePart() {
    }

    public McSparePart(String partNo, String fullDescription, String shortDescription, int mcPartCategoryId) {
        PartNo = partNo;
        FullDescription = fullDescription;
        ShortDescription = shortDescription;
        McPartCategoryId = mcPartCategoryId;
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

    public int getMcPartCategoryId() {
        return McPartCategoryId;
    }

    public void setMcPartCategoryId(int mcPartCategoryId) {
        McPartCategoryId = mcPartCategoryId;
    }
}
