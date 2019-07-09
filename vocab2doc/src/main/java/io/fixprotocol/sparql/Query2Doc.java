package io.fixprotocol.sparql;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.jena.graph.Node_Variable;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.core.Var;
import io.fixprotocol.text.TextFormatter;
import io.fixprotocol.text.TextUtils;

/**
 * Writes the result of a SPARQL query to a document
 * 
 * @author Don Mendelson
 *
 */
public final class Query2Doc {

  private static void writeCellEnd(Writer writer, TextFormatter formatter) throws IOException {
    formatter.endCell(writer);
  }

  private static void writeCellStart(Writer writer, TextFormatter formatter) throws IOException {
    formatter.startCell(writer);
  }

  private static void writeRowEnd(Writer writer, TextFormatter formatter) throws IOException {
    formatter.endRow(writer);
  }

  private static void writeRowStart(Writer writer, TextFormatter formatter) throws IOException {
    formatter.startRow(writer);
  }

  private static void writeTableEnd(Writer writer, TextFormatter formatter) throws IOException {
    formatter.endTable(writer);
  }

  private static void writeTableStart(Writer writer, TextFormatter formatter) throws IOException {
    formatter.startTable(writer);
  }

  static void writeCell(Writer writer, TextFormatter formatter, String value) throws IOException {
    writeCellStart(writer, formatter);
    writer.write(value);
    writeCellEnd(writer, formatter);
  }

  static void writeColumnTitles(List<String> colTitles, Writer writer, TextFormatter formatter)
      throws IOException {
    formatter.startRow(writer);

    for (String colTitle : colTitles) {
      formatter.startColumnHeading(writer);
      writer.write(colTitle);
      formatter.endColumnHeading(writer);
    }

    formatter.endRow(writer);
  }

  

  /**
   * Writes the result of a SPARQL select query to a document
   * 
   * @param sourceUri identifier of source
   * @param queryString a SPARQL select query
   * @param writer
   * @param formatter
   * @throws IOException If an I/O error occurs
   */
  public void executeSelect(String sourceUri, String queryString, Writer writer, TextFormatter formatter)
      throws IOException {

    final Query query = QueryFactory.create(queryString);

    final List<Var> queryVars = query.getProjectVars();
    final List<String> varNames =
        queryVars.stream().map(Node_Variable::getName).collect(Collectors.toList());
    final List<String> colTitles = queryVars.stream().map(Node_Variable::getName)
        .map(TextUtils::convertToTitleCase).collect(Collectors.toList());

    writeTableStart(writer, formatter);
    writeColumnTitles(colTitles, writer, formatter);

    final Dataset dataset = DatasetFactory.create(sourceUri);
    
    try (QueryExecution qexec = QueryExecutionFactory.create(query, dataset)) {
      final ResultSet results = qexec.execSelect();

      while (results.hasNext()) {
        final QuerySolution solution = results.nextSolution();
        writeRowStart(writer, formatter);

        for (String varName : varNames) {
          final RDFNode node = solution.get(varName);
          String value = "";
          if (node == null) {
            // empty cell
          } else if (node.isLiteral()) {
            Literal literal = (Literal) node;
            value = literal.getLexicalForm();
          } else if (node.isResource()) {
            Resource resource = (Resource) node;
            writer.write(resource.toString());
          }
          writeCell(writer, formatter, value);
        }

        writeRowEnd(writer, formatter);
      }

    }
    writeTableEnd(writer, formatter);
  }

}
