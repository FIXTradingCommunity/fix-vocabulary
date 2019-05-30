package io.fixprotocol.text;

import java.io.IOException;
import java.io.Writer;

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
  public void startTable(Writer writer) throws IOException {

  }

  @Override
  public void endTable(Writer writer) throws IOException {

  }

  @Override
  public void startRow(Writer writer) throws IOException {

  }

  @Override
  public void endRow(Writer writer) throws IOException {
    writer.write(" |");
    writer.write(NEWLINE);
    if (columns > 0) {
      for (int c = columns; c > 0; c--) {
        writer.write("| - ");
      }
      writer.write("|");
      writer.write(NEWLINE);
      columns = 0;
    }
  }

  @Override
  public void startColumnHeading(Writer writer) throws IOException {
    writer.write("| ");
    columns++;
  }

  @Override
  public void endColumnHeading(Writer writer) throws IOException {
    writer.write(' ');
  }

  @Override
  public void startCell(Writer writer) throws IOException {
    writer.write("| ");
  }

  @Override
  public void endCell(Writer writer) throws IOException {
    writer.write(' ');
  }

  @Override
  public void writeLink(Writer writer, String url, String text) throws IOException {
    writer.write(String.format("[%s](%s)", text, url));
  }

}
