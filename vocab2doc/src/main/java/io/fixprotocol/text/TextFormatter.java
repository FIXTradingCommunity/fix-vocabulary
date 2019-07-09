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
  
  /**
   * Write a hyperlink
   * 
   * @param appendable An object to which char sequences and values can be appended
   * @param url a URL
   * @param text to be displayed
   * @throws IOException If an I/O error occurs
   */
  void link(Appendable appendable, String url, String text) throws IOException;
}
