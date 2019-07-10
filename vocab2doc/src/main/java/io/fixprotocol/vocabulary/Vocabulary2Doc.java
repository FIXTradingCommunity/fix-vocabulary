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

package io.fixprotocol.vocabulary;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import io.fixprotocol.sparql.Query2Doc;
import io.fixprotocol.sparql.QueryResultRecord;
import io.fixprotocol.sparql.QueryResultSet;
import io.fixprotocol.text.TextFormatter;
import io.fixprotocol.text.TextFormatterFactory;

/**
 * Generates a document from a controlled vocabulary
 * 
 * @author Don Mendelson
 *
 */
public class Vocabulary2Doc {


  /**
   * Generates a document from a controlled vocabulary
   * 
   * @param args command line arguments
   *        <ol>
   *        <li>URI of the controlled vocabulary</li>
   *        <li>Name of file to write</li>
   *        <li>Code for output format. Defaults to HTML. See
   *        {@link io.fixprotocol.TextFormatterFactory} for valid codes.</li>
   *        </ol>
   * @throws IOException If an I/O error occurs
   * 
   */
  public static void main(String[] args) throws IOException {
    if (args.length < 2) {
      useage();
    } else {
      String uri = args[0];
      String fileName = args[1];
      String format = TextFormatterFactory.HTML;
      if (args.length > 2) {
        format = args[2];
      }
      Vocabulary2Doc vocabulary2Doc = new Vocabulary2Doc();
      vocabulary2Doc.generate(uri, fileName, format);
    }
  }

  public static void useage() {
    System.err.println(
        "Useage: java io.fixprotocol.vocabulary.Vocabulary2Doc <in-URI> <out-file> [html|md]");
  }

  static void writeCell(Appendable appendable, TextFormatter formatter, String value) throws IOException {
    formatter.startCell(appendable);
    formatter.render(appendable, value);
    formatter.endCell(appendable);
  }

  static void writeColumnTitles(List<String> colTitles, Appendable appendable, TextFormatter formatter)
      throws IOException {
    formatter.startRow(appendable);

    for (String colTitle : colTitles) {
      formatter.startColumnHeading(appendable);
      formatter.render(appendable, colTitle);
      formatter.endColumnHeading(appendable);
    }

    formatter.endRow(appendable);
  }

  /**
   * Generates a document from a controlled vocabulary
   * 
   * @param sourceUri vocabulary URI
   * @param targetFilename name of document file to write
   * @param format code for text format. See {@link io.fixprotocol.TextFormatterFactory} for valid
   *        codes.
   * @throws IOException If an I/O error occurs
   */
  public void generate(String sourceUri, String targetFilename, String format) throws IOException {
    try (Writer writer = new FileWriter(targetFilename, StandardCharsets.UTF_8)) {

      InputStream querySource = this.getClass().getClassLoader().getResourceAsStream("allterms.rq");
      if (querySource == null) {
        throw new IOException("Query resource not found");
      }
      try (ByteArrayOutputStream queryTarget = new ByteArrayOutputStream(2048)) {
        querySource.transferTo(queryTarget);
        final String queryString = queryTarget.toString(StandardCharsets.UTF_8);
        final TextFormatter formatter = TextFormatterFactory.create(format);
        final SortedMap<String, String> synonyms = getSynonyms(sourceUri, queryString);
        final Iterator<Entry<String, String>> synonymIter = synonyms.entrySet().iterator();
        Entry<String, String> synonymEntry = null;
        if (synonymIter.hasNext()) {
          synonymEntry = synonymIter.next();
        }
        
        final Query2Doc query2Doc = new Query2Doc();
        final QueryResultSet resultSet = query2Doc.executeSelect(sourceUri, queryString);
        formatter.startTable(writer);
        final List<String> colTitles = List.of("Term", "Definition");
        writeColumnTitles(colTitles, writer, formatter);

        while (resultSet.hasNext()) {
          final QueryResultRecord record = resultSet.next();
          final String term = record.getValue("term");

          while (synonymEntry != null && synonymEntry.getKey().compareTo(term) < 0) {
            formatter.startRow(writer);
            writeCell(writer, formatter, synonymEntry.getKey());
            formatter.startCell(writer);
            formatter.startBold(writer);
            formatter.render(writer, "See: ");
            formatter.endBold(writer);
            formatter.render(writer, synonymEntry.getValue());
            formatter.endCell(writer);
            formatter.endRow(writer);
            if (synonymIter.hasNext()) {
              synonymEntry = synonymIter.next();
            } else {
              synonymEntry = null;
            }
          }
          
          formatter.startRow(writer);
          writeCell(writer, formatter, term);
          String definition = record.getValue("definition");  
          String source = record.getValue("source");
          String spec = record.getValue("spec");              
          formatter.startCell(writer);
          formatter.render(writer, definition);
          if (!source.isEmpty()) {
            formatter.lineBreak(writer);
            formatter.startBold(writer);
            formatter.render(writer, "Source: ");
            formatter.endBold(writer);
            formatter.render(writer, source);
          }
          if (!spec.isEmpty()) {
            formatter.lineBreak(writer);
            formatter.startBold(writer);
            formatter.render(writer, "Specification: ");
            formatter.endBold(writer);
            formatter.render(writer, spec);
          }
          formatter.endCell(writer);
          formatter.endRow(writer);
        }
        formatter.endTable(writer);
        resultSet.close();
      }
    }
  }

  SortedMap<String, String> getSynonyms(String sourceUri, String queryString) throws IOException {
    final Query2Doc query2Doc = new Query2Doc();
    final QueryResultSet resultSetForSynonyms = query2Doc.executeSelect(sourceUri, queryString);
    try {
      SortedMap<String, String> synonyms = new TreeMap<>();
      while (resultSetForSynonyms.hasNext()) {
        final QueryResultRecord record = resultSetForSynonyms.next();
        String term = record.getValue("term");
        String synonym = record.getValue("synonym");
        if (!synonym.isEmpty()) {
          synonyms.put(synonym, term);
        }
      }
      return synonyms;
    } finally {
      resultSetForSynonyms.close();
    }
  }

}
