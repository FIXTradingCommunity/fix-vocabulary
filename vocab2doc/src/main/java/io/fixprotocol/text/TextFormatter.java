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
 * Formats the structure of text for a presentation protocol
 * 
 * @author Don Mendelson
 *
 */
public interface TextFormatter {
  
  /**
   * Render text in a destination encoding
   * @param appendable An object to which char sequences and values can be appended
   * @param text plain text to render
   * @throws IOException If an I/O error occurs
   */
  void render(Appendable appendable, String text) throws IOException;

  /**
   * Stop writing text in bold
   * 
   * @param appendable An object to which char sequences and values can be appended
   * @throws IOException If an I/O error occurs
   */
  void endBold(Appendable appendable) throws IOException;

  /**
   * Write the end of a table cell
   * 
   * @param appendable An object to which char sequences and values can be appended
   * @throws IOException If an I/O error occurs
   */
  void endCell(Appendable appendable) throws IOException;

  /**
   * Write the end of a table column heading
   * 
   * @param appendable An object to which char sequences and values can be appended
   * @throws IOException If an I/O error occurs
   */
  void endColumnHeading(Appendable appendable) throws IOException;

  /**
   * Write the end of a table row
   * 
   * @param appendable An object to which char sequences and values can be appended
   * @throws IOException If an I/O error occurs
   */
  void endRow(Appendable appendable) throws IOException;

  /**
   * Write the end of a table
   * 
   * @param appendable An object to which char sequences and values can be appended
   * @throws IOException If an I/O error occurs
   */
  void endTable(Appendable appendable) throws IOException;

  /**
   * Write a line break
   * 
   * @param appendable An object to which char sequences and values can be appended
   * @throws IOException If an I/O error occurs
   */
  void lineBreak(Appendable appendable) throws IOException;

  /**
   * Write a hyperlink
   * 
   * @param appendable An object to which char sequences and values can be appended
   * @param url a URL
   * @param text to be displayed
   * @throws IOException If an I/O error occurs
   */
  void link(Appendable appendable, String url, String text) throws IOException;

  /**
   * Write subsequent text in bold
   * 
   * @param appendable An object to which char sequences and values can be appended
   * @throws IOException If an I/O error occurs
   */
  void startBold(Appendable appendable) throws IOException;

  /**
   * Write the start of a table cell
   * 
   * @param appendable An object to which char sequences and values can be appended
   * @throws IOException If an I/O error occurs
   */
  void startCell(Appendable appendable) throws IOException;

  /**
   * Write the start of a table column heading
   * 
   * @param appendable An object to which char sequences and values can be appended
   * @throws IOException If an I/O error occurs
   */
  void startColumnHeading(Appendable appendable) throws IOException;

  /**
   * Write the start of a table row
   * 
   * @param appendable An object to which char sequences and values can be appended
   * @throws IOException If an I/O error occurs
   */
  void startRow(Appendable appendable) throws IOException;

  /**
   * Write the start of a table
   * 
   * @param appendable An object to which char sequences and values can be appended
   * @throws IOException If an I/O error occurs
   */
  void startTable(Appendable appendable) throws IOException;
}
