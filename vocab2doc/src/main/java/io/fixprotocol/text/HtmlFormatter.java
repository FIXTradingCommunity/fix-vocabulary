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

import java.io.IOException;
import io.fixprotocol.text.TextRenderer.ParagraphOption;

/**
 * Formats the structure of text for HTML
 * 
 * @author Don Mendelson
 *
 */
class HtmlFormatter implements TextFormatter {

  private static final String NEWLINE = System.lineSeparator();
  
  private PlainText2HTMLRenderer renderer = new PlainText2HTMLRenderer();
  
  public HtmlFormatter() {
    renderer.setParagraphOption(ParagraphOption.USE_BREAK);
  }

  @Override
  public void endBold(Appendable appendable) throws IOException {
    appendable.append("</b>");
  }

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
  public void link(Appendable appendable, String url, String text) throws IOException {
    appendable.append(String.format("<a href=\"%s\">%s</a>", url, text));
  }

  @Override
  public void startBold(Appendable appendable) throws IOException {
    appendable.append("<b>");
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
  public void render(Appendable appendable, String text) throws IOException {
    renderer.render(text, appendable);
  }

}
