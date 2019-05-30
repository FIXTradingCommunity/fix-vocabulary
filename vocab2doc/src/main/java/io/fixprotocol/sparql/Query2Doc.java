package io.fixprotocol.sparql;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.jena.graph.Node_Variable;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
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

  /**
   * Writes the result of a SPARQL select query to a document
   * 
   * @param query a SPARQL select query
   * @param dataset a Jena dataset
   * @param out output stream writer
   * @param formatter formats structure of a document
   * @throws IOException IOException If an I/O error occurs
   */
  public static void executeSelect(Query query, Dataset dataset, Writer writer,
      TextFormatter formatter) throws IOException {
    List<Var> queryVars = query.getProjectVars();
    List<String> varNames =
        queryVars.stream().map(Node_Variable::getName).collect(Collectors.toList());
    List<String> colTitles = queryVars.stream().map(Node_Variable::getName)
        .map(TextUtils::convertToTitleCase).collect(Collectors.toList());

    writeTableStart(writer, formatter);
    writeColumnTitles(colTitles, writer, formatter);

    try (QueryExecution qexec = QueryExecutionFactory.create(query, dataset)) {
      ResultSet results = qexec.execSelect();

      while (results.hasNext()) {
        QuerySolution solution = results.nextSolution();
        writeRowStart(writer, formatter);

        for (String varName : varNames) {
          RDFNode node = solution.get(varName);
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
          writeCellStart(writer, formatter);
          writer.write(value);
          writeCellEnd(writer, formatter);
        }

        writeRowEnd(writer, formatter);
      }

    }
    writeTableEnd(writer, formatter);
  }

  /**
   * Writes the result of a SPARQL select query to a document
   * 
   * @param queryString a SPARQL select query as a String
   * @param dataset a Jena dataset
   * @param out output stream writer
   * @param formatter formats structure of a document
   * @throws IOException IOException If an I/O error occurs
   */
  public static void executeSelect(String queryString, Dataset dataset, Writer out,
      TextFormatter formatter) throws IOException {
    Query query = QueryFactory.create(queryString);
    executeSelect(query, dataset, out, formatter);
  }

  private static void writeCellEnd(Writer writer, TextFormatter formatter) throws IOException {
    formatter.endCell(writer);
  }

  private static void writeCellStart(Writer writer, TextFormatter formatter) throws IOException {
    formatter.startCell(writer);
  }

  private static void writeColumnTitles(List<String> colTitles, Writer writer,
      TextFormatter formatter) throws IOException {
    formatter.startRow(writer);

    for (String colTitle : colTitles) {
      formatter.startColumnHeading(writer);
      writer.write(colTitle);
      formatter.endColumnHeading(writer);
    }

    formatter.endRow(writer);
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

}
