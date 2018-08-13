package com.optc.optcdbmobile.data.optcdb.api_parser;

import com.optc.optcdbmobile.data.optcdb.BaseParser;

public class VersionParser extends BaseParser<Integer> {
    @Override
    public Integer parse(Object jsParsed) {
        return toType(jsParsed, Integer.class);
    }
}
