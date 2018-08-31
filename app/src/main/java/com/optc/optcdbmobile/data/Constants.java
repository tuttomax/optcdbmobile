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

package com.optc.optcdbmobile.data;

public class Constants {

    public static class Settings {
        //UI preference
        public final static String pref_wifi_download_key = "wifi_download";
        public final static String pref_auto_download_key = "auto_download";
        public final static String pref_database_version_key = "database_version";
        public final static String pref_card_layout_key = "card_layout";
        // non-UI preference
        public final static String pref_check_done_key = "check_done";
        public static final String pref_update_available = "update_available";

        public final static String pref_first_launch = "first_launch";

    }

    public class API {
        public final static byte VERSION_TYPE = 0;
        public final static byte UNITS_TYPE = 1;
        public final static byte EVOLUTIONS_TYPE = 2;
        public final static byte DROPS_TYPE = 3;
        public final static byte DETAILS_TYPE = 4;
        public final static byte COOLDOWNS_TYPE = 5;
        public final static byte FAMILIES_TYPE = 6;
        public final static byte ALIASES_TYPE = 7;
        public final static byte TAGS_TYPE = 8;
    }

    public class DropsType {
        public final static byte GENERAL = 0;
        public final static byte GLOBAL = 1;
        public final static byte JAPAN = 2;
        public static final byte STORY_TYPE = 0;
        public static final byte BOOSTER_EVOLVER_TYPE = 1;
        public static final byte FORTNIGHT_TYPE = 2;
        public static final byte RAID_TYPE = 3;
        public static final byte COLISEUM_TYPE = 4;
        public static final byte TREASURE_MAP_TYPE = 5;
        public static final byte SPECIAL_MAP_TYPE = 6;
        public static final byte TRAINING_FOREST_TYPE = 7;
        public static final byte ALL_DIFFICULTIES = 0;
        public static final byte ROOKIE = 1;
        public static final byte VETERAN = 2;
        public static final byte ELITE = 3;
        public static final byte EXPERT = 4;
        public static final byte MASTER = 5;
        public static final byte ULTIMATE = 6;
        public static final byte MAX_STAGE_IN_STORY = 20;
        public static final byte EXHIBITION = 0;
        public static final byte UNDERGROUND = 1;
        public static final byte CHAOS = 2;
        public static final byte NEO = 3;
    }

    public static class APP {
        public final static String APP_VERSION_URL = "https://raw.githubusercontent.com/tuttomax/optcdbmobile/master/version";
        public final static String APP_DOWNLOAD_URL = "https://github.com/tuttomax/optcdbmobile/releases/download/%d/app_install.apk";
        public final static String APP_INSTALL_NAME = "app_install.apk";
        public final static String MIME_TYPE_APK = "application/vnd.android.package-archive";
    }

    public class DatabaseVersionTask {
        public final static byte NO_ACTION = 0;
        public final static byte ACTION_SHOW_UPDATE = 1;
        public final static byte ACTION_AUTOMATIC_UPDATE = 2;

    }
}
