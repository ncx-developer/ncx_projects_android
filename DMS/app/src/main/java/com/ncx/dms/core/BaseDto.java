package com.ncx.dms.core;

import java.util.ArrayList;

public class BaseDto implements Comparable<BaseDto>  {
    @Override
    public int compareTo(BaseDto f) {
        return toString().toUpperCase().compareTo(f.toString().toUpperCase());
    }
}
