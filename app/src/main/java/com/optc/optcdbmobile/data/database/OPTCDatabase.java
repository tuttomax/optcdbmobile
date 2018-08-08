package com.optc.optcdbmobile.data.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.optc.optcdbmobile.data.database.data_access_object.CaptainDAO;
import com.optc.optcdbmobile.data.database.data_access_object.LimitDAO;
import com.optc.optcdbmobile.data.database.data_access_object.PotentialDAO;
import com.optc.optcdbmobile.data.database.data_access_object.SailorDAO;
import com.optc.optcdbmobile.data.database.data_access_object.SpecialDAO;
import com.optc.optcdbmobile.data.database.data_access_object.UnitDAO;

//@Database(entities = {Captain.class, Limit.class, Potential.class, Sailor.class, Special.class, Unit.class}, version = 1, exportSchema = false)
public abstract class OPTCDatabase extends RoomDatabase {

    private final static OPTCDatabaseMigrationStrategy MIGRATION_1_2 = new OPTCDatabaseMigrationStrategy(1, 2);
    private final static String TAG = "OPTCDatabase_OnCreate";
    private static OPTCDatabase INSTANCE = null;

    public static OPTCDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (OPTCDatabase.class) {
                if (INSTANCE == null)
                    INSTANCE = Room.databaseBuilder(context, OPTCDatabase.class, "optc_database")
                            .addMigrations(MIGRATION_1_2)
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    Log.i(TAG, "Database is created for the first time");
                                }
                            })
                            .build();
            }
        }
        return INSTANCE;
    }

    /* DAO */
    public abstract CaptainDAO captainDAO();

    public abstract LimitDAO limitDAO();

    public abstract PotentialDAO potentialDAO();

    public abstract SailorDAO sailorDAO();

    public abstract SpecialDAO specialDAO();

    public abstract UnitDAO unitDAO();

    /* ----- */


    private static class OPTCDatabaseMigrationStrategy extends Migration {

        /**
         * Creates a new migration between {@code startVersion} and {@code endVersion}.
         *
         * @param startVersion The start version of the database.
         * @param endVersion   The end version of the database after this migration is applied.
         */
        public OPTCDatabaseMigrationStrategy(int startVersion, int endVersion) {
            super(startVersion, endVersion);
        }

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Nothing to do now
            // Need for future update of database's schemas
        }
    }


}
