package com.optc.optcdbmobile;

import com.optc.optcdbmobile.data.database.entities.Evolution;
import com.optc.optcdbmobile.data.optcdb.API;
import com.optc.optcdbmobile.data.optcdb.Constants;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {

    @Test
    public void test() {

        List<Evolution> evos = (List<Evolution>) API.getData(Constants.EVOLUTIONS_TYPE);

        List<Integer> evoIds = new ArrayList<>();
        List<Integer> uniIds = new ArrayList<>();

        Map<Integer, List<Integer>> map = new HashMap<>();
        for (Evolution evo : evos) {
            if (!map.containsKey(evo.getEvolutionId())) {
                map.put(evo.getEvolutionId(), new ArrayList<Integer>());
            }
            map.get(evo.getEvolutionId()).add(evo.getUnitId());

        }

        System.out.println(map);
    }
}