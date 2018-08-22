/*
 * Copyright 2018 alessandro
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.optc.optcdbmobile.data.optcdb;

import com.google.common.base.Charsets;
import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.optcdb.api_parser.AliasParser;
import com.optc.optcdbmobile.data.optcdb.api_parser.CooldownsParser;
import com.optc.optcdbmobile.data.optcdb.api_parser.DetailsParser;
import com.optc.optcdbmobile.data.optcdb.api_parser.DropsParser;
import com.optc.optcdbmobile.data.optcdb.api_parser.EvolutionsParser;
import com.optc.optcdbmobile.data.optcdb.api_parser.FamilyParser;
import com.optc.optcdbmobile.data.optcdb.api_parser.TagParser;
import com.optc.optcdbmobile.data.optcdb.api_parser.UnitsParser;
import com.optc.optcdbmobile.data.optcdb.api_parser.VersionParser;
import com.optc.optcdbmobile.data.optcdb.entities.Skull;

import org.mozilla.javascript.Scriptable;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class API {

    private final static int ID_MAX_WIDTH = 4;
    private final static String urlThumb = "https://onepiece-treasurecruise.com/wp-content/uploads/f%s.png";
    private final static String specialThumb = "https://github.com/optc-db/optc-db.github.io/raw/master/res/%s.png";
    private final static String nullThumb = "https://onepiece-treasurecruise.com/wp-content/themes/onepiece-treasurecruise/images/noimage.png";
    private final static String urlBigImage = "https://onepiece-treasurecruise.com/wp-content/uploads/c%s.png";

    private final static HashMap<Byte, String> url_table = new HashMap<Byte, String>(6) {{
        put(Constants.API.VERSION_TYPE, "https://raw.githubusercontent.com/tuttomax/optcdbmobile/master/version.js");
        put(Constants.API.UNITS_TYPE, "https://raw.githubusercontent.com/optc-db/optc-db.github.io/master/common/data/units.js");
        put(Constants.API.EVOLUTIONS_TYPE, "https://raw.githubusercontent.com/optc-db/optc-db.github.io/master/common/data/evolutions.js");
        put(Constants.API.DROPS_TYPE, "https://raw.githubusercontent.com/optc-db/optc-db.github.io/master/common/data/drops.js");
        put(Constants.API.DETAILS_TYPE, "https://raw.githubusercontent.com/optc-db/optc-db.github.io/master/common/data/details.js");
        put(Constants.API.COOLDOWNS_TYPE, "https://raw.githubusercontent.com/optc-db/optc-db.github.io/master/common/data/cooldowns.js");
        put(Constants.API.FAMILIES_TYPE, "https://raw.githubusercontent.com/optc-db/optc-db.github.io/master/common/data/families.js");
        put(Constants.API.TAGS_TYPE, "https://raw.githubusercontent.com/optc-db/optc-db.github.io/master/common/data/flags.js");
        put(Constants.API.ALIASES_TYPE, "https://raw.githubusercontent.com/optc-db/optc-db.github.io/master/common/data/aliases.js");
    }};

    private final static HashMap<Byte, String> name_table = new HashMap<Byte, String>(6) {{
        put(Constants.API.VERSION_TYPE, "dbVersion");
        put(Constants.API.UNITS_TYPE, "units");
        put(Constants.API.EVOLUTIONS_TYPE, "evolutions");
        put(Constants.API.DROPS_TYPE, "drops");
        put(Constants.API.DETAILS_TYPE, "details");
        put(Constants.API.COOLDOWNS_TYPE, "cooldowns");
        put(Constants.API.FAMILIES_TYPE, "families");
        put(Constants.API.TAGS_TYPE, "flags");
        put(Constants.API.ALIASES_TYPE, "aliases");
    }};

    private final static HashMap<Byte, BaseParser> parser_table = new HashMap<Byte, BaseParser>(6) {{
        put(Constants.API.VERSION_TYPE, new VersionParser());
        put(Constants.API.UNITS_TYPE, new UnitsParser());
        put(Constants.API.EVOLUTIONS_TYPE, new EvolutionsParser());
        put(Constants.API.DROPS_TYPE, new DropsParser());
        put(Constants.API.DETAILS_TYPE, new DetailsParser());
        put(Constants.API.COOLDOWNS_TYPE, new CooldownsParser());
        put(Constants.API.FAMILIES_TYPE, new FamilyParser());
        put(Constants.API.TAGS_TYPE, new TagParser());
        put(Constants.API.ALIASES_TYPE, new AliasParser());
    }};


    public static String getId(int id, int width) {
        // %04d
        final String fmt = "%" + '0' + width + 'd';
        return String.format(fmt, id);
    }

    public static String getId(int id) {
        return getId(id, ID_MAX_WIDTH);
    }

    public static String getThumb(int id) {
        if (id >= Skull.MIN && id <= Skull.MAX) {
            return Skull.getThumb(id);
        }

        switch (id) {
            case 742:
                return "https://onepiece-treasurecruise.com/wp-content/uploads/f0742-2.png";
            case 2500:
                return "http://onepiece-treasurecruise.com/en/wp-content/uploads/sites/2/f5011.png";
            case 2501:
                return "http://onepiece-treasurecruise.com/en/wp-content/uploads/sites/2/f5012.png";
            case 2502:
                return "http://onepiece-treasurecruise.com/en/wp-content/uploads/sites/2/f5013.png";
            case 2503:
                return "http://onepiece-treasurecruise.com/en/wp-content/uploads/sites/2/f5013.png\"";
            case 2504:
                return "http://onepiece-treasurecruise.com/en/wp-content/uploads/sites/2/f5013.png\"";
            case 5000:
                return String.format(specialThumb, "character_10185_t1");
            case 5001:
                return String.format(specialThumb, "character_10186_t1");
            case 5002:
                return String.format(specialThumb, "character_10187_t1_int");
            case 5003:
                return String.format(specialThumb, "character_10187_t1_psy");
            case 5004:
                return String.format(specialThumb, "character_10173_t1");
            case 5005:
                return String.format(specialThumb, "character_10175_t1");
            case 5006:
                return String.format(specialThumb, "character_10174_t1");
            case 5007:
                return String.format(specialThumb, "character_10176_t1");
            case 5008:
                return String.format(specialThumb, "character_10177_t1_qck");
            case 5009:
                return String.format(specialThumb, "character_10177_t1_str");
            case 5010:
                return String.format(specialThumb, "character_10178_t1_qck");
            case 5011:
                return String.format(specialThumb, "character_10178_t1_str");
            case 5012:
                return String.format(specialThumb, "character_10181_t1");
            case 5013:
                return String.format(specialThumb, "character_10182_t1");
            case 5014:
                return String.format(specialThumb, "character_10183_t1_psy");
            case 5015:
                return String.format(specialThumb, "character_10183_t1_dex");

            case 5016:
                return String.format(specialThumb, "character_10344_t1");
            case 5017:
                return String.format(specialThumb, "character_10346_t1");
            case 5018:
                return String.format(specialThumb, "character_10345_t1");
            case 5019:
                return String.format(specialThumb, "character_10347_t1");
            case 5020:
                return String.format(specialThumb, "character_10348_t1_psy");
            case 5021:
                return String.format(specialThumb, "character_10348_t1_int");
            case 5022:
                return String.format(specialThumb, "character_10349_t1_psy");
            case 5023:
                return String.format(specialThumb, "character_10349_t1_int");
            case 5024:
                return String.format(specialThumb, "character_10496_t1");
            case 5025:
                return String.format(specialThumb, "character_10497_t1");
            case 5026:
                return String.format(specialThumb, "character_10498_t1_dex");
            case 5027:
                return String.format(specialThumb, "character_10498_t1_str");
            //TODO Update this manually

            default:
                String sId = getId(id);
                return String.format(urlThumb, sId);
        }
    }

    public static String getBigImage(int id) {
        String sId = getId(id);
        return String.format(urlBigImage, sId);
    }


    public static Object getData(byte type) {
        Object parsed = simple_parsing(url_table.get(type), name_table.get(type));
        BaseParser parser = parser_table.get(type);
        return parser.parse(parsed);
    }


    /* Thanks https://github.com/paolo-optc/optc-mobile-db ->
     https://github.com/paolo-optc/optc-mobile-db/blob/master/app/src/main/java/it/instruman/treasurecruisedatabase/MainActivity.java#L1563
     */
    private static String simple_download(String url) {
        try {
            URL fileURL = new URL(url);
            return com.google.common.io.Resources.toString(fileURL, Charsets.UTF_8);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /* Thanks https://github.com/paolo-optc/optc-mobile-db ->
    https://github.com/paolo-optc/optc-mobile-db/blob/master/app/src/main/java/it/instruman/treasurecruisedatabase/MainActivity.java#L1593
     */
    private static Object simple_parsing(String url, String objName) {
        String dump = simple_download(url);
        return parsing(dump, objName);
    }


    private static Object parsing(String dump, String objName) {


        // Every Rhino VM begins with the enter()
        // This Context is not Android's Context

        org.mozilla.javascript.Context rhino = org.mozilla.javascript.Context.enter();

        // Turn off optimization to make Rhino Android compatible

        rhino.setOptimizationLevel(-1);

        Object result = null;

        try {
            Scriptable scope = rhino.initStandardObjects();

            // Note the forth argument is 1, which means the JavaScript source has
            // been compressed to only one line using something like YUI
            rhino.evaluateString(scope, "var window = {" + objName + ":null};" + dump, "JavaScript", 1, null);
            // Get the functionName defined in JavaScriptCode
            Object x = scope.get("window", scope);
            Map<String, Object> y = (Map<String, Object>) x;
            result = y.get(objName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            org.mozilla.javascript.Context.exit();
        }
        return result;
    }
}
