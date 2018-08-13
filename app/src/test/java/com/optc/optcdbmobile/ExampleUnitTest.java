package com.optc.optcdbmobile;

import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.database.entities.BoosterEvolverLocation;
import com.optc.optcdbmobile.data.database.entities.FortnightLocation;
import com.optc.optcdbmobile.data.database.entities.Location;
import com.optc.optcdbmobile.data.database.entities.LocationChallengeData;
import com.optc.optcdbmobile.data.database.entities.LocationDrops;
import com.optc.optcdbmobile.data.database.entities.RaidLocation;
import com.optc.optcdbmobile.data.database.entities.SpecialLocation;
import com.optc.optcdbmobile.data.database.entities.TrainingForestLocation;
import com.optc.optcdbmobile.data.database.entities.TreasureLocation;
import com.optc.optcdbmobile.data.optcdb.API;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {

    @Test
    public void getVersion() {
        try {
            String v = (String) API.getData(Constants.APIType.VERSION_TYPE);
            Double d = Double.valueOf(v);
            Integer i = Integer.valueOf(v);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            return equals((Pair<K, V>) obj);
        }

        public boolean equals(Pair<K, V> obj) {
            return ((key == obj.key) && (value == obj.value));
        }

        @Override
        public String toString() {
            return String.format("[%s, %s]", key, value);
        }
    }

    @Test
    public void testDuplicate() {

        List<Object> evos = (List<Object>) API.getData(Constants.APIType.DROPS_TYPE);


        List<Object> checked = new ArrayList<>();
        List<Object> exclude = new ArrayList<>();

        for (Object o : evos) {
            if (o instanceof Location) {
                Integer id = ((Location) o).getId();
                if (!checked.contains(id)) {
                    checked.add(o);
                } else {
                    exclude.add(o);
                }
            }
            if (o instanceof BoosterEvolverLocation) {
                Integer id = ((BoosterEvolverLocation) o).getLocationId();
                if (!checked.contains(id)) {
                    checked.add(o);
                } else {
                    exclude.add(o);
                }
            }
            if (o instanceof RaidLocation) {
                Integer id = ((RaidLocation) o).getLocationId();
                if (!checked.contains(id)) {
                    checked.add(o);
                } else {
                    exclude.add(o);
                }
            }
            if (o instanceof FortnightLocation) {
                Integer id = ((FortnightLocation) o).getLocationId();
                if (!checked.contains(id)) {
                    checked.add(o);
                } else {
                    exclude.add(o);
                }
            }
            if (o instanceof TreasureLocation) {
                Integer id = ((TreasureLocation) o).getLocationId();
                if (!checked.contains(id)) {
                    checked.add(o);
                } else {
                    exclude.add(o);
                }
            }
            if (o instanceof TrainingForestLocation) {
                Integer id = ((TrainingForestLocation) o).getLocationId();
                if (!checked.contains(id)) {
                    checked.add(o);
                } else {
                    exclude.add(o);
                }
            }
            if (o instanceof SpecialLocation) {
                Integer id = ((SpecialLocation) o).getLocationId();
                if (!checked.contains(id)) {
                    checked.add(o);
                } else {
                    exclude.add(o);
                }
            }
            if (o instanceof LocationDrops) {
                Integer id = ((LocationDrops) o).getLocationId();
                if (!checked.contains(id)) {
                    checked.add(o);
                } else {
                    exclude.add(o);
                }
            }
            if (o instanceof LocationChallengeData) {
                Integer id = ((LocationChallengeData) o).getLocationId();
                if (!checked.contains(id)) {
                    checked.add(o);
                } else {
                    exclude.add(o);
                }
            }
        }

        for (Object o : exclude) {

            System.out.println(o);

        }


    }


    private class Triplet<A, B, C> {
        private A a;
        private B b;
        private C c;

        public Triplet(A a, B b, C c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public boolean equals(Object obj) {
            Triplet<A, B, C> t = (Triplet<A, B, C>) obj;
            return ((a == t.a) && (b == t.b) && (c == t.c));
        }

        @Override
        public String toString() {
            return String.format("[%s, %s, %s]", a, b, c);
        }
    }

}