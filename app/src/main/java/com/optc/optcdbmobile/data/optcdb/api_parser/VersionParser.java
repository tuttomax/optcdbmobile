package com.optc.optcdbmobile.data.optcdb.api_parser;

import com.optc.optcdbmobile.data.optcdb.BaseParser;

public class VersionParser extends BaseParser<String> {
    @Override
    public String parse(Object jsParsed) {
        return String.format("%s", jsParsed);
    }
}
