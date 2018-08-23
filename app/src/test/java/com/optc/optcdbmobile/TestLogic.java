package com.optc.optcdbmobile;

import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.optcdb.API;
import com.optc.optcdbmobile.data.optcdb.entities.Cooldown;

import org.junit.Test;

import java.util.List;

public class TestLogic {


    @Test
    public void test() {
        List<Cooldown> list = (List<Cooldown>) API.getData(Constants.API.COOLDOWNS_TYPE);
        for (Cooldown cooldown : list) {
            System.out.println(String.format("%d -> (%d, %d)", cooldown.getId(), cooldown.getMin(), cooldown.getMax()));
        }
    }
}