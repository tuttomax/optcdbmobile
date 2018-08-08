package com.optc.optcdbmobile.data.optcdb;

import com.google.common.base.Charsets;
import com.optc.optcdbmobile.data.optcdb.api_parser.CooldownsParser;
import com.optc.optcdbmobile.data.optcdb.api_parser.DetailsParser;
import com.optc.optcdbmobile.data.optcdb.api_parser.DropsParser;
import com.optc.optcdbmobile.data.optcdb.api_parser.EvolutionsParser;
import com.optc.optcdbmobile.data.optcdb.api_parser.UnitsParser;
import com.optc.optcdbmobile.data.optcdb.api_parser.VersionParser;

import org.mozilla.javascript.Scriptable;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class API {

    private final static HashMap<Byte, String> url_table = new HashMap<Byte, String>(6) {{
        put(Constants.VERSION_TYPE, "https://raw.githubusercontent.com/optc-db/optc-db.github.io/master/common/data/version.js");
        put(Constants.UNITS_TYPE, "https://raw.githubusercontent.com/optc-db/optc-db.github.io/master/common/data/units.js");
        put(Constants.EVOLUTIONS_TYPE, "https://raw.githubusercontent.com/optc-db/optc-db.github.io/master/common/data/evolutions.js");
        put(Constants.DROPS_TYPE, "https://raw.githubusercontent.com/optc-db/optc-db.github.io/master/common/data/drops.js");
        put(Constants.DETAILS_TYPE, "https://raw.githubusercontent.com/optc-db/optc-db.github.io/master/common/data/details.js");
        put(Constants.COOLDOWNS_TYPE, "https://raw.githubusercontent.com/optc-db/optc-db.github.io/master/common/data/cooldowns.js");
    }};
    private final static HashMap<Byte, String> name_table = new HashMap<Byte, String>(6) {{
        put(Constants.VERSION_TYPE, "dbVersion");
        put(Constants.UNITS_TYPE, "units");
        put(Constants.EVOLUTIONS_TYPE, "evolutions");
        put(Constants.DROPS_TYPE, "drops");
        put(Constants.DETAILS_TYPE, "details");
        put(Constants.COOLDOWNS_TYPE, "cooldowns");
    }};

    private final static HashMap<Byte, BaseParser> parser_table = new HashMap<Byte, BaseParser>(6) {{
        put(Constants.VERSION_TYPE, new VersionParser());
        put(Constants.UNITS_TYPE, new UnitsParser());
        put(Constants.EVOLUTIONS_TYPE, new EvolutionsParser());
        put(Constants.DROPS_TYPE, new DropsParser());
        put(Constants.DETAILS_TYPE, new DetailsParser());
        put(Constants.COOLDOWNS_TYPE, new CooldownsParser());
    }};


    public static String getId(int id, int width) {
        // %04d
        final String fmt = "%" + '0' + width + 'd';
        return String.format(fmt, id);
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
