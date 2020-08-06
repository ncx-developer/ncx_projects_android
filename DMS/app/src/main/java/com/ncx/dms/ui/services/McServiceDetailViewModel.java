package com.ncx.dms.ui.services;

import com.ncx.dms.core.BaseViewModel;
import com.ncx.dms.core.CommonHelper;
import com.ncx.dms.remote.App;
import com.ncx.dms.remote.dto.McOrderDetailDto;
import com.ncx.dms.remote.dto.McSparePart;

import java.util.UUID;

public class McServiceDetailViewModel extends BaseViewModel {

    public McServiceDetailViewModel(App app){
        super(app);
    }

    public void mcServiceDetailDataChanged(String partNo, Double price, Integer quantity) {
        if (!isPartNoValid(partNo)) {
            formState.setValue(new McServiceDetailFormState("Required Part No!", null, null));
        } else if (!isPriceValid(price)) {
            formState.setValue(new McServiceDetailFormState(null, "Invalid price!", null));
        } else if (!isQuantityValid(quantity)) {
            formState.setValue(new McServiceDetailFormState(null, null, "Invalid quantity!"));
        } else {
            formState.setValue(new McServiceDetailFormState(true));
        }
    }

    private boolean isPartNoValid(String partNo) {

        return !commonHelper.isNullOrEmpty(partNo);
    }

    private boolean isPriceValid(Double price) {

        if (price == null)
            return false;

        return true;
    }

    private boolean isQuantityValid(Integer quantity) {

        if (quantity == null || quantity <= 0)
            return false;

        return true;
    }
}
