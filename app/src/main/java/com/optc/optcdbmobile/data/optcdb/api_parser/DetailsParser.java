package com.optc.optcdbmobile.data.optcdb.api_parser;

import com.optc.optcdbmobile.data.database.entities.Captain;
import com.optc.optcdbmobile.data.database.entities.CaptainDescription;
import com.optc.optcdbmobile.data.database.entities.Limit;
import com.optc.optcdbmobile.data.database.entities.Potential;
import com.optc.optcdbmobile.data.database.entities.PotentialDescription;
import com.optc.optcdbmobile.data.database.entities.Sailor;
import com.optc.optcdbmobile.data.database.entities.SailorDescription;
import com.optc.optcdbmobile.data.database.entities.Special;
import com.optc.optcdbmobile.data.database.entities.SpecialDescription;
import com.optc.optcdbmobile.data.optcdb.BaseParser;
import com.optc.optcdbmobile.data.optcdb.entities.Detail;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DetailsParser extends BaseParser<List<Detail>> {
    @Override
    public List<Detail> parse(Object jsParsed) {

        List<Detail> list = new ArrayList<>();

        NativeObject temp = (NativeObject) jsParsed;

        for (Map.Entry<Object, Object> obj : temp.entrySet()) {

            final Detail detail = new Detail();

            Integer id = toType(obj.getKey(), Integer.class);

            NativeObject internal_obj = (NativeObject) obj.getValue();

            parseCaptain(detail, internal_obj, id);

            parseSpecial(detail, internal_obj, id);

            parseSailor(detail, internal_obj, id);

            parseLimit(detail, internal_obj, id);

            parsePotential(detail, internal_obj, id);


            list.add(detail);
        }

        return list;


    }

    private void parseCaptain(Detail detail, NativeObject internal_obj, Integer id) {
        Captain captainAbility;
        CaptainDescription captainDescription;


        Object captain = internal_obj.get("captain");

        if (captain != null) {
            String description, notes;
            Integer level = 0;
            notes = toType(internal_obj.get("captainNotes"), String.class);

            if (captain instanceof String) {
                description = (String) captain;

                captainDescription = new CaptainDescription(id, 0, description);
                captainAbility = new Captain(id, notes);
                detail.getCaptainList().add(captainAbility);
                detail.getCaptainDescriptionList().add(captainDescription);

            } else if (captain instanceof NativeObject) {
                NativeObject captainObj = (NativeObject) captain;

                captainAbility = new Captain(id, notes);
                detail.getCaptainList().add(captainAbility);

                for (Map.Entry<Object, Object> entry : captainObj.entrySet()) {
                    description = (entry.getValue().toString());
                    captainDescription = new CaptainDescription(id, level++, description);

                    detail.getCaptainDescriptionList().add(captainDescription);

                }
            }
        }
    }

    private void parseLimit(Detail detail, NativeObject internal_obj, Integer id) {
        NativeArray limitObj = (NativeArray) internal_obj.get("limit");
        if (limitObj != null) {
            for (int index = 0; index < limitObj.size(); index++) {
                NativeObject limitElement = (NativeObject) limitObj.get(index);
                String limitDescription = toType(limitElement.get("description"), String.class);
                Limit limit = new Limit(id, index, limitDescription);
                detail.getLimitList().add(limit);
            }
        }
    }

    private void parsePotential(Detail detail, NativeObject internal_obj, Integer id) {
        NativeArray potentialArray = (NativeArray) internal_obj.get("potential");
        if (potentialArray != null) {
            for (int index = 0; index < potentialArray.size(); index++) {
                NativeObject potentialObj = (NativeObject) potentialArray.get(index);
                String potentialName = toType(potentialObj.get("Name"), String.class);
                NativeArray potentialElements = (NativeArray) potentialObj.get("description");

                Potential potential = new Potential(id, index, potentialName);
                detail.getPotentialList().add(potential);

                for (int arrayIndex = 0; arrayIndex < potentialElements.size(); arrayIndex++) {
                    String potentialDescr = toType(potentialElements.get(arrayIndex), String.class);
                    PotentialDescription potentialDescription = new PotentialDescription(id, index, arrayIndex, potentialDescr);
                    detail.getPotentialDescriptionList().add(potentialDescription);
                }
            }
        }
    }

    private void parseSpecial(Detail detail, NativeObject internal_obj, Integer id) {
        String specialName = toType(internal_obj.get("specialName"), String.class);
        String specialNotes = toType(internal_obj.get("specialNotes"), String.class);
        int min = -1, max = -1;

        Special special;
        SpecialDescription specialDescription;

        Object specialObj = internal_obj.get("special");
        if (specialObj != null) {
            if (specialObj instanceof String) {
                String specialDesc = toType(specialObj, String.class);
                special = new Special(id, specialName, specialNotes);
                specialDescription = new SpecialDescription(id, 0, specialDesc, min, max);
                // INFO : cooldown will be update during database building

                detail.getSpecialList().add(special);
                detail.getSpecialDescriptionList().add(specialDescription);

            } else if (specialObj instanceof NativeArray) {
                NativeArray specialArray = (NativeArray) specialObj;

                special = new Special(id, specialName, specialNotes);
                detail.getSpecialList().add(special);

                for (int index = 0; index < specialArray.size(); index++) {

                    NativeObject specialElement = (NativeObject) specialArray.get(index);
                    String specialDesc = toType(specialElement.get("description"), String.class);
                    NativeArray cooldownsArray = (NativeArray) specialElement.get("cooldown");

                    if (cooldownsArray != null) {
                        min = toType(cooldownsArray.get(0), Integer.class);
                        max = toType(cooldownsArray.get(1), Integer.class);
                    }

                    specialDescription = new SpecialDescription(id, index, specialDesc, min, max);

                    detail.getSpecialDescriptionList().add(specialDescription);
                }
            }
        }
    }

    private void parseSailor(Detail detail, NativeObject internal_obj, Integer id) {
        Sailor sailor;
        SailorDescription sailorDescription;

        Object sailorObj = internal_obj.get("sailor");
        if (sailorObj != null) {
            if (sailorObj instanceof String) {
                String sailorDescr = toType(sailorObj, String.class);
                sailor = new Sailor(id);
                sailorDescription = new SailorDescription(id, 0, sailorDescr);
                detail.getSailorList().add(sailor);
                detail.getSailorDescriptionList().add(sailorDescription);
            } else if (sailorObj instanceof NativeObject) {
                NativeObject sailorElement = (NativeObject) sailorObj;
                int sailorLevel = 0;
                sailor = new Sailor(id);
                detail.getSailorList().add(sailor);
                for (Map.Entry<Object, Object> entry : sailorElement.entrySet()) {
                    String sailorDescr = toType(entry.getValue(), String.class);
                    sailorDescription = new SailorDescription(id, sailorLevel++, sailorDescr);
                    detail.getSailorDescriptionList().add(sailorDescription);
                }
            }
        }
    }
}
