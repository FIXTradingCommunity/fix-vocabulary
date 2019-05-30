package io.fixprotocol.text;

import java.io.IOException;
import java.io.Writer;

/**
 * Formats the structure of text for a presentation protocol
 * 
 * @author Don Mendelson
 *
 */
public interface TextFormatter {

  /**
   * Write the start of a table
   * 
   * @param writer a character stream
   * @throws IOException If an I/O error occurs
   */
  void startTable(Writer writer) throws IOException;

  /**
   * Write the end of a table
   * 
   * @param writer a character stream
   * @throws IOException If an I/O error occurs
   */
  void endTable(Writer writer) throws IOException;

  /**
   * Write the start of a table row
   * 
   * @param writer a character stream
   * @throws IOException If an I/O error occurs
   */
  void startRow(Writer writer) throws IOException;

  /**
   * Write the end of a table row
   * 
   * @param writer a character stream
   * @throws IOException If an I/O error occurs
   */
  void endRow(Writer writer) throws IOException;

  /**
   * Write the start of a table column heading
   * 
   * @param writer a character stream
   * @throws IOException If an I/O error occurs
   */
  void startColumnHeading(Writer writer) throws IOException;

  /**
   * Write the end of a table column heading
   * 
   * @param writer a character stream
   * @throws IOException If an I/O error occurs
   */
  void endColumnHeading(Writer writer) throws IOException;

  /**
   * Write the start of a table cell
   * 
   * @param writer a character stream
   * @throws IOException If an I/O error occurs
   */
  void startCell(Writer writer) throws IOException;

  /**
   * Write the end of a table cell
   * 
   * @param writer a character stream
   * @throws IOException If an I/O error occurs
   */
  void endCell(Writer writer) throws IOException;

  /**
   * Write a hyperlink
   * 
   * @param writer a character stream
   * @param url a URL
   * @param text to be displayed
   * @throws IOException If an I/O error occurs
   */
  void writeLink(Writer writer, String url, String text) throws IOException;
}
