package com.optc.optcdbmobile;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.optc.optcdbmobile.data.database.OPTCDatabase;
import com.optc.optcdbmobile.data.database.entities.CaptainDescription;

import org.junit.Test;

import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        OPTCDatabase database = OPTCDatabase.getInstance(appContext);
        List<CaptainDescription> list = database.captainDescriptionDAO().searchByRegex("Boosts ATK of (STR|QCK|DEX|PSY|INT) characters");
        System.out.println(String.valueOf(list.size()));
    }
}
