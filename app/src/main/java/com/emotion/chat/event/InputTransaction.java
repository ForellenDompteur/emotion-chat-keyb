/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.emotion.chat.event;

import com.emotion.chat.latin.settings.SettingsValues;

public class InputTransaction {
    public static final int SHIFT_NO_UPDATE = 0;
    public static final int SHIFT_UPDATE_NOW = 1;
    public static final int SHIFT_UPDATE_LATER = 2;

    public final SettingsValues mSettingsValues;

    private int mRequiredShiftUpdate = SHIFT_NO_UPDATE;

    public InputTransaction(final SettingsValues settingsValues) {
        mSettingsValues = settingsValues;
    }

    public void requireShiftUpdate(final int updateType) {
        mRequiredShiftUpdate = Math.max(mRequiredShiftUpdate, updateType);
    }

    public int getRequiredShiftUpdate() {
        return mRequiredShiftUpdate;
    }
}
