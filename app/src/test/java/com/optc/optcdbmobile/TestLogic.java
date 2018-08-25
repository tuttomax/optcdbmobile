package com.optc.optcdbmobile;

import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.database.entities.Unit;
import com.optc.optcdbmobile.data.optcdb.API;

import org.junit.Test;

import java.util.List;

public class TestLogic {


    @Test
    public void test() {
        List<Unit> list = (List<Unit>) API.getData(Constants.API.UNITS_TYPE);

    }
}