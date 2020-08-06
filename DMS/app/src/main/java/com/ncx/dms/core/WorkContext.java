package com.ncx.dms.core;

import com.ncx.dms.remote.dto.DealerDto;
import com.ncx.dms.remote.dto.UserDto;

import java.util.List;

public class WorkContext {

    private UserDto currentCuser;
    private List<DealerDto> currentDealers;

    public WorkContext() {
    }

    public WorkContext(UserDto currentCuser, List<DealerDto> currentDealers) {
        this.currentCuser = currentCuser;
        this.currentDealers = currentDealers;
    }

    public UserDto getCurrentCuser() {
        return currentCuser;
    }

    public List<DealerDto> getCurrentDealers() {
        return currentDealers;
    }

    public void setCurrentCuser(UserDto currentCuser) {
        this.currentCuser = currentCuser;
    }

    public void setCurrentDealers(List<DealerDto> currentDealers) {
        this.currentDealers = currentDealers;
    }
}
