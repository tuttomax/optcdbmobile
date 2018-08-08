package com.optc.optcdbmobile;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.SparseArray;

import com.optc.optcdbmobile.data.optcdb.API;
import com.optc.optcdbmobile.data.optcdb.Constants;
import com.optc.optcdbmobile.data.optcdb.location.entities.Drops;

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

        SparseArray<List<Drops>> data = (SparseArray<List<Drops>>) API.getData(Constants.DROPS_TYPE);

        String fake = ";";
    }
}
