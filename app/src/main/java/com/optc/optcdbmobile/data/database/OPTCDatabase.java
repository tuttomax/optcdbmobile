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

package com.optc.optcdbmobile.data.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.optc.optcdbmobile.data.database.data_access_object.AliasDAO;
import com.optc.optcdbmobile.data.database.data_access_object.BoosterEvolverLocationDAO;
import com.optc.optcdbmobile.data.database.data_access_object.CaptainDAO;
import com.optc.optcdbmobile.data.database.data_access_object.CaptainDescriptionDAO;
import com.optc.optcdbmobile.data.database.data_access_object.ColiseumLocationDAO;
import com.optc.optcdbmobile.data.database.data_access_object.EvolutionDAO;
import com.optc.optcdbmobile.data.database.data_access_object.FamilyDAO;
import com.optc.optcdbmobile.data.database.data_access_object.FamilyUnitDAO;
import com.optc.optcdbmobile.data.database.data_access_object.FortnightLocationDAO;
import com.optc.optcdbmobile.data.database.data_access_object.LimitDAO;
import com.optc.optcdbmobile.data.database.data_access_object.LocationChallengeDataDAO;
import com.optc.optcdbmobile.data.database.data_access_object.LocationDAO;
import com.optc.optcdbmobile.data.database.data_access_object.LocationDropsDAO;
import com.optc.optcdbmobile.data.database.data_access_object.PotentialDAO;
import com.optc.optcdbmobile.data.database.data_access_object.PotentialDescriptionDAO;
import com.optc.optcdbmobile.data.database.data_access_object.RaidLocationDAO;
import com.optc.optcdbmobile.data.database.data_access_object.SailorDAO;
import com.optc.optcdbmobile.data.database.data_access_object.SailorDescriptionDAO;
import com.optc.optcdbmobile.data.database.data_access_object.SpecialDAO;
import com.optc.optcdbmobile.data.database.data_access_object.SpecialDescriptionDAO;
import com.optc.optcdbmobile.data.database.data_access_object.SpecialLocationDAO;
import com.optc.optcdbmobile.data.database.data_access_object.StoryLocationDAO;
import com.optc.optcdbmobile.data.database.data_access_object.TagDAO;
import com.optc.optcdbmobile.data.database.data_access_object.TrainingForestLocationDAO;
import com.optc.optcdbmobile.data.database.data_access_object.TreasureLocationDAO;
import com.optc.optcdbmobile.data.database.data_access_object.UnitDAO;
import com.optc.optcdbmobile.data.database.data_access_object.UnitTagDAO;
import com.optc.optcdbmobile.data.database.entities.Alias;
import com.optc.optcdbmobile.data.database.entities.BoosterEvolverLocation;
import com.optc.optcdbmobile.data.database.entities.Captain;
import com.optc.optcdbmobile.data.database.entities.CaptainDescription;
import com.optc.optcdbmobile.data.database.entities.ColiseumLocation;
import com.optc.optcdbmobile.data.database.entities.Evolution;
import com.optc.optcdbmobile.data.database.entities.Family;
import com.optc.optcdbmobile.data.database.entities.FamilyUnit;
import com.optc.optcdbmobile.data.database.entities.FortnightLocation;
import com.optc.optcdbmobile.data.database.entities.Limit;
import com.optc.optcdbmobile.data.database.entities.Location;
import com.optc.optcdbmobile.data.database.entities.LocationChallengeData;
import com.optc.optcdbmobile.data.database.entities.LocationDrops;
import com.optc.optcdbmobile.data.database.entities.Potential;
import com.optc.optcdbmobile.data.database.entities.PotentialDescription;
import com.optc.optcdbmobile.data.database.entities.RaidLocation;
import com.optc.optcdbmobile.data.database.entities.Sailor;
import com.optc.optcdbmobile.data.database.entities.SailorDescription;
import com.optc.optcdbmobile.data.database.entities.Special;
import com.optc.optcdbmobile.data.database.entities.SpecialDescription;
import com.optc.optcdbmobile.data.database.entities.SpecialLocation;
import com.optc.optcdbmobile.data.database.entities.StoryLocation;
import com.optc.optcdbmobile.data.database.entities.Tag;
import com.optc.optcdbmobile.data.database.entities.TrainingForestLocation;
import com.optc.optcdbmobile.data.database.entities.TreasureLocation;
import com.optc.optcdbmobile.data.database.entities.Unit;
import com.optc.optcdbmobile.data.database.entities.UnitTag;

@Database(entities =
        {Alias.class, BoosterEvolverLocation.class, Captain.class, CaptainDescription.class, ColiseumLocation.class,
                Evolution.class, Family.class, FamilyUnit.class, FortnightLocation.class, Limit.class, LocationChallengeData.class, Location.class,
                LocationDrops.class, Potential.class, PotentialDescription.class, RaidLocation.class, Sailor.class, SailorDescription.class,
                Special.class, SpecialDescription.class, SpecialLocation.class, StoryLocation.class, Tag.class,
                TrainingForestLocation.class, TreasureLocation.class, Unit.class, UnitTag.class},
        version = 1, exportSchema = false)
public abstract class OPTCDatabase extends RoomDatabase {

    private final static OPTCDatabaseMigrationStrategy MIGRATION_1_2 = new OPTCDatabaseMigrationStrategy(1, 2);

    private static OPTCDatabase INSTANCE = null;

    public static OPTCDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (OPTCDatabase.class) {
                if (INSTANCE == null)
                    INSTANCE = Room.databaseBuilder(context, OPTCDatabase.class, "optc_database")
                            .fallbackToDestructiveMigration()
                            .build();
            }
        }

        return INSTANCE;
    }

    //region DATA ACCESS OBJECT

    public abstract AliasDAO aliasDAO();

    public abstract BoosterEvolverLocationDAO boosterEvolverLocationDAO();

    public abstract CaptainDAO captainDAO();

    public abstract CaptainDescriptionDAO captainDescriptionDAO();

    public abstract ColiseumLocationDAO coliseumLocationDAO();

    public abstract EvolutionDAO evolutionDAO();

    public abstract FamilyDAO familyDAO();

    public abstract FamilyUnitDAO familyUnitDAO();

    public abstract FortnightLocationDAO fortnightLocationDAO();

    public abstract LimitDAO limitDAO();

    public abstract LocationChallengeDataDAO locationChallengeDataDAO();

    public abstract LocationDAO locationDAO();

    public abstract LocationDropsDAO locationDropsDAO();

    public abstract PotentialDAO potentialDAO();

    public abstract PotentialDescriptionDAO potentialDescriptionDAO();

    public abstract RaidLocationDAO raidLocationDAO();

    public abstract SailorDAO sailorDAO();

    public abstract SailorDescriptionDAO sailorDescriptionDAO();

    public abstract SpecialDAO specialDAO();

    public abstract SpecialDescriptionDAO specialDescriptionDAO();

    public abstract SpecialLocationDAO specialLocationDAO();

    public abstract StoryLocationDAO storyLocationDAO();

    public abstract TagDAO tagDAO();

    public abstract TrainingForestLocationDAO trainingForestLocationDAO();

    public abstract TreasureLocationDAO treasureLocationDAO();

    public abstract UnitDAO unitDAO();

    public abstract UnitTagDAO unitTagDAO();
    //endregion


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
