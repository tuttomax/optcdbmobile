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

package com.optc.optcdbmobile.data.optcdb.entities;

import com.optc.optcdbmobile.data.database.entities.Family;

import java.util.List;

public class FamilyContainer {

    private Family family;
    private List<Integer> unitIds;

    public FamilyContainer(Family family, List<Integer> unitIds) {
        this.family = family;
        this.unitIds = unitIds;
    }

    public Family getFamily() {
        return family;
    }

    public List<Integer> getUnitIds() {
        return unitIds;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public void setUnitIds(List<Integer> unitIds) {
        this.unitIds = unitIds;
    }
}
