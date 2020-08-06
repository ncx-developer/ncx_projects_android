package com.ncx.dms.remote.dto;

public class MechanicDto {

    private String mechanicCode;

    private String name;

    public MechanicDto(String mechanicCode, String name) {
        this.mechanicCode = mechanicCode;
        this.name = name;
    }

    public String getMechanicCode() {
        return mechanicCode;
    }

    public void setMechanicCode(String mechanicCode) {
        this.mechanicCode = mechanicCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
