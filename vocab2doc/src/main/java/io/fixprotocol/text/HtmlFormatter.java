package io.fixprotocol.text;

import java.io.IOException;
import java.io.Writer;

/**
 * Formats the structure of text for HTML
 * 
 * @author Don Mendelson
 *
 */
class HtmlFormatter implements TextFormatter {

  private static final String NEWLINE = System.lineSeparator();

  @Override
  public void startTable(Writer writer) throws IOException {
    writer.write("<table>");
  }

  @Override
  public void endTable(Writer writer) throws IOException {
    writer.write("</table>");
  }

  @Override
  public void startRow(Writer writer) throws IOException {
    writer.write("<tr>");
  }

  @Override
  public void endRow(Writer writer) throws IOException {
    writer.write("</tr>");
    writer.write(NEWLINE);
  }

  @Override
  public void startColumnHeading(Writer writer) throws IOException {
    writer.write("<th>");
  }

  @Override
  public void endColumnHeading(Writer writer) throws IOException {
    writer.write("</th>");
  }

  @Override
  public void startCell(Writer writer) throws IOException {
    writer.write("<td>");
  }

  @Override
  public void endCell(Writer writer) throws IOException {
    writer.write("</td>");
  }

  @Override
  public void writeLink(Writer writer, String url, String text) throws IOException {
    writer.write(String.format("<a href=\"%s\">%s</a>", url, text));
  }

}
