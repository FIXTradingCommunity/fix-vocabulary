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
  public void endBold(Appendable appendable) throws IOException {
    appendable.append("**");
  }

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
  public void link(Appendable appendable, String url, String text) throws IOException {
    appendable.append(String.format("[%s](%s)", text, url));
  }

  @Override
  public void startBold(Appendable appendable) throws IOException {
    appendable.append("**");
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
  public void render(Appendable appendable, String text) throws IOException {
    appendable.append(text);
  }

}
