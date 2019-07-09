package io.fixprotocol.text;

import java.io.IOException;

/**
 * Formats the structure of text for markdown
 * 
 * The formatting of tables is not standardized in markdown. This implementation is consistent with
 * the most common styles, including GitHub Flavored Markdown.
 * 
 * @author Don Mendelson
 *
 */
class MarkdownFormatter implements TextFormatter {
  private static final String NEWLINE = System.lineSeparator();

  private int columns = 0;

  @Override
  public void endCell(Appendable appendable) throws IOException {
    appendable.append(' ');
  }

  @Override
  public void endColumnHeading(Appendable appendable) throws IOException {
    appendable.append(' ');
  }

  @Override
  public void endRow(Appendable appendable) throws IOException {
    appendable.append(" |");
    appendable.append(NEWLINE);
    if (columns > 0) {
      for (int c = columns; c > 0; c--) {
        appendable.append("| - ");
      }
      appendable.append("|");
      appendable.append(NEWLINE);
      columns = 0;
    }
  }

  @Override
  public void endTable(Appendable appendable) throws IOException {

  }

  @Override
  public void lineBreak(Appendable appendable) throws IOException {
    appendable.append(NEWLINE);
    appendable.append(NEWLINE);
  }

  @Override
  public void startCell(Appendable appendable) throws IOException {
    appendable.append("| ");
  }

  @Override
  public void startColumnHeading(Appendable appendable) throws IOException {
    appendable.append("| ");
    columns++;
  }

  @Override
  public void startRow(Appendable appendable) throws IOException {

  }

  @Override
  public void startTable(Appendable appendable) throws IOException {

  }

  @Override
  public void link(Appendable appendable, String url, String text) throws IOException {
    appendable.append(String.format("[%s](%s)", text, url));
  }

}
