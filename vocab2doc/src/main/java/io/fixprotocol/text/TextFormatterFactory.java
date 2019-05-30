package io.fixprotocol.text;

/**
 * Creates an instance of a {@link TextFormatter}
 * 
 * @author Don Mendelson
 *
 */
public class TextFormatterFactory {

  /**
   * Code for HTML protocol
   */
  public static final String HTML = "html";

  /**
   * Code for markdown protocol
   */
  public static final String MARKDOWN = "md";

  /**
   * Creates an instance of a {@link TextFormatter}
   * 
   * @param format a code for a text protocol
   * @return an implementation of TextFormatter
   * @throws IllegalArgumentException if {@link format} is unrecognized
   */
  public static TextFormatter create(String format) {
    switch (format) {
      case HTML:
        return new HtmlFormatter();
      case MARKDOWN:
        return new MarkdownFormatter();
      default:
        throw new IllegalArgumentException();
    }
  }

}
