/*
 * Copyright 2019 FIX Protocol Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 */

package io.fixprotocol.text;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Text formatting utilities
 * 
 * @author Don Mendelson
 *
 */
public final class TextUtils {

  private static final String WORD_SEPARATOR = " ";
  private static final String WHITESPACE_PATTERN = "\\s";

  /**
   * Converts text to title case, that is, capitalized first character of each word
   * 
   * @param text a String to format
   * @return a new String in the requested format
   */
  public static String convertToTitleCase(String text) {
    if (text == null || text.isEmpty()) {
      return text;
    }

    return Arrays.stream(text.split(WHITESPACE_PATTERN))
        .map(word -> word.length() < 2 ? word
            : Character.toTitleCase(word.charAt(0)) + word.substring(1).toLowerCase())
        .collect(Collectors.joining(WORD_SEPARATOR));
  }

}
