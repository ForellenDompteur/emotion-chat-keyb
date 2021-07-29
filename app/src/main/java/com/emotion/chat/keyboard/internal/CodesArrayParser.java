/*
 * Copyright (C) 2013 The Android Open Source Project
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

package com.emotion.chat.keyboard.internal;

import android.text.TextUtils;

import com.emotion.chat.latin.common.Constants;
import com.emotion.chat.latin.common.StringUtils;

public final class CodesArrayParser {
    private static final char COMMA = Constants.CODE_COMMA;
    private static final String COMMA_REGEX = StringUtils.newSingleCodePointString(COMMA);
    private static final String VERTICAL_BAR_REGEX = // "\\|"
            new String(new char[] { Constants.CODE_BACKSLASH, Constants.CODE_VERTICAL_BAR });
    private static final int BASE_HEX = 16;

    private CodesArrayParser() {
     // Utility class, not publicly instantiable.
    }

    private static String getLabelSpec(final String codesArraySpec) {
        final String[] strs = codesArraySpec.split(VERTICAL_BAR_REGEX, -1);
        if (strs.length <= 1) {
            return codesArraySpec;
        }
        return strs[0];
    }

    public static String parseLabel(final String codesArraySpec) {
        final String labelSpec = getLabelSpec(codesArraySpec);
        final StringBuilder sb = new StringBuilder();
        for (final String codeInHex : labelSpec.split(COMMA_REGEX)) {
            final int codePoint = Integer.parseInt(codeInHex, BASE_HEX);
            sb.appendCodePoint(codePoint);
        }
        return sb.toString();
    }

    private static String getCodeSpec(final String codesArraySpec) {
        final String[] strs = codesArraySpec.split(VERTICAL_BAR_REGEX, -1);
        if (strs.length <= 1) {
            return codesArraySpec;
        }
        return TextUtils.isEmpty(strs[1]) ? strs[0] : strs[1];
    }

    public static int getMinSupportSdkVersion(final String codesArraySpec) {
        final String[] strs = codesArraySpec.split(VERTICAL_BAR_REGEX, -1);
        if (strs.length <= 2) {
            return 0;
        }
        try {
            return Integer.parseInt(strs[2]);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static int parseCode(final String codesArraySpec) {
        final String codeSpec = getCodeSpec(codesArraySpec);
        if (codeSpec.indexOf(COMMA) < 0) {
            return Integer.parseInt(codeSpec, BASE_HEX);
        }
        return Constants.CODE_OUTPUT_TEXT;
    }

    public static String parseOutputText(final String codesArraySpec) {
        final String codeSpec = getCodeSpec(codesArraySpec);
        if (codeSpec.indexOf(COMMA) < 0) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        for (final String codeInHex : codeSpec.split(COMMA_REGEX)) {
            final int codePoint = Integer.parseInt(codeInHex, BASE_HEX);
            sb.appendCodePoint(codePoint);
        }
        return sb.toString();
    }
}
