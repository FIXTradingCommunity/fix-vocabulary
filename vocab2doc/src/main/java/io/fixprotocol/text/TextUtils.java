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
