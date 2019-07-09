package io.fixprotocol.text;

import java.io.IOException;

/**
 * Formats the structure of text for HTML
 * 
 * @author Don Mendelson
 *
 */
class HtmlFormatter implements TextFormatter {

  private static final String NEWLINE = System.lineSeparator();

  @Override
  public void endCell(Appendable appendable) throws IOException {
    appendable.append("</td>");
  }

  @Override
  public void endColumnHeading(Appendable appendable) throws IOException {
    appendable.append("</th>");
  }

  @Override
  public void endRow(Appendable appendable) throws IOException {
    appendable.append("</tr>");
    appendable.append(NEWLINE);
  }

  @Override
  public void endTable(Appendable appendable) throws IOException {
    appendable.append("</table>");
  }

  @Override
  public void lineBreak(Appendable appendable) throws IOException {
    appendable.append("<br/>");
    appendable.append(NEWLINE);
  }

  @Override
  public void startCell(Appendable appendable) throws IOException {
    appendable.append("<td>");
  }

  @Override
  public void startColumnHeading(Appendable appendable) throws IOException {
    appendable.append("<th>");
  }

  @Override
  public void startRow(Appendable appendable) throws IOException {
    appendable.append("<tr>");
  }

  @Override
  public void startTable(Appendable appendable) throws IOException {
    appendable.append("<table>");
  }

  @Override
  public void link(Appendable appendable, String url, String text) throws IOException {
    appendable.append(String.format("<a href=\"%s\">%s</a>", url, text));
  }

}
