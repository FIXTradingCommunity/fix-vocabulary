package io.fixprotocol.vocabulary;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import io.fixprotocol.sparql.Query2Doc;
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
        String queryString = queryTarget.toString(StandardCharsets.UTF_8);
        TextFormatter textFormatter = TextFormatterFactory.create(format);
        Query2Doc query2Doc = new Query2Doc();
        query2Doc.executeSelect(sourceUri, queryString, writer, textFormatter);
      }
    }
  }

}
