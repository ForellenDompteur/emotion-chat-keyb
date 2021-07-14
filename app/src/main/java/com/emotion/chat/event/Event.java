/*
 * Copyright (C) 2012 The Android Open Source Project
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

import com.emotion.chat.latin.common.Constants;
import com.emotion.chat.latin.common.StringUtils;


public class Event {
    final public static int EVENT_TYPE_NOT_HANDLED = 0;
    final public static int EVENT_TYPE_INPUT_KEYPRESS = 1;
    final public static int EVENT_TYPE_TOGGLE = 2;
    final public static int EVENT_TYPE_MODE_KEY = 3;
    final public static int EVENT_TYPE_SOFTWARE_GENERATED_STRING = 6;
    final public static int EVENT_TYPE_CURSOR_MOVE = 7;


    final public static int NOT_A_CODE_POINT = -1;

    final public static int NOT_A_KEY_CODE = 0;

    final private static int FLAG_NONE = 0;
    final private static int FLAG_REPEAT = 0x2;
    final private static int FLAG_CONSUMED = 0x4;

    final private int mEventType;
    final public int mCodePoint;

    final public CharSequence mText;

    final public int mKeyCode;

    final public int mX;
    final public int mY;

    final private int mFlags;

    // The next event, if any. Null if there is no next event yet.
    final public Event mNextEvent;

    // This method is private - to create a new event, use one of the create* utility methods.
    private Event(final int type, final CharSequence text, final int codePoint, final int keyCode,
            final int x, final int y, final int flags,
            final Event next) {
        mEventType = type;
        mText = text;
        mCodePoint = codePoint;
        mKeyCode = keyCode;
        mX = x;
        mY = y;
        mFlags = flags;
        mNextEvent = next;
    }

    public static Event createSoftwareKeypressEvent(final int codePoint, final int keyCode,
            final int x, final int y, final boolean isKeyRepeat) {
        return new Event(EVENT_TYPE_INPUT_KEYPRESS, null, codePoint, keyCode, x, y,
                isKeyRepeat ? FLAG_REPEAT : FLAG_NONE, null);
    }

    public static Event createSoftwareTextEvent(final CharSequence text, final int keyCode) {
        return new Event(EVENT_TYPE_SOFTWARE_GENERATED_STRING, text, NOT_A_CODE_POINT, keyCode,
                Constants.NOT_A_COORDINATE, Constants.NOT_A_COORDINATE,
                FLAG_NONE, null /* next */);
    }

    public boolean isFunctionalKeyEvent() {
        return NOT_A_CODE_POINT == mCodePoint;
    }

    public boolean isKeyRepeat() {
        return 0 != (FLAG_REPEAT & mFlags);
    }

    public boolean isConsumed() { return 0 != (FLAG_CONSUMED & mFlags); }

    public CharSequence getTextToCommit() {
        if (isConsumed()) {
            return ""; // A consumed event should input no text.
        }
        switch (mEventType) {
        case EVENT_TYPE_MODE_KEY:
        case EVENT_TYPE_NOT_HANDLED:
        case EVENT_TYPE_TOGGLE:
        case EVENT_TYPE_CURSOR_MOVE:
            return "";
        case EVENT_TYPE_INPUT_KEYPRESS:
            return StringUtils.newSingleCodePointString(mCodePoint);
        case EVENT_TYPE_SOFTWARE_GENERATED_STRING:
            return mText;
        }
        throw new RuntimeException("Unknown event type: " + mEventType);
    }
}
