package com.ncx.dms.remote.dto;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class UserDto {

    private UUID userId;
    private String userName;
    private String fullName;

    public UserDto() {
    }

    public UserDto(UUID userId, String userName, String fullName) {
        this.userId = userId;
        this.userName = userName;
        this.fullName = fullName;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
