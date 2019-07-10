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

package io.fixprotocol.sparql;

import java.io.IOException;
import java.util.Iterator;
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
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.core.Var;

/**
 * Executes a SPARQL query
 * 
 * @author Don Mendelson
 *
 */
public final class Query2Doc {


  private class QueryResultRecordImpl implements QueryResultRecord {

    private final QuerySolution solution;

    QueryResultRecordImpl(QuerySolution solution) {
      this.solution = solution;
    }

    @Override
    public boolean contains(String varName) {
      return solution.contains(varName);
    }

    @Override
    public String getValue(String variableName) {
      final RDFNode node = solution.get(variableName);
      String value = "";
      if (node == null) {
        // empty cell
      } else if (node.isLiteral()) {
        Literal literal = (Literal) node;
        value = literal.getLexicalForm();
      } else if (node.isResource()) {
        Resource resource = (Resource) node;
        value = resource.toString();
      }
      return value;
    }

    @Override
    public Iterator<String> variableNames() {
      return solution.varNames();
    }

  }

  private class QueryResultSetImpl implements QueryResultSet {

    private final ResultSet results;
    private final List<String> variableNames;
    private final QueryExecution qexec;

    QueryResultSetImpl(QueryExecution qexec, ResultSet results, List<String> variableNames) {
      this.qexec = qexec;
      this.results = results;
      this.variableNames = variableNames;
    }

    @Override
    public List<String> getVariableNames() {
      return variableNames;
    }

    @Override
    public boolean hasNext() {
      return results.hasNext();
    }

    @Override
    public QueryResultRecord next() {
      QuerySolution solution = results.next();
      return new QueryResultRecordImpl(solution);
    }

    @Override
    public void close() {
      qexec.close();
    }

  }

  /**
   * Returns the result of a SPARQL select query
   * 
   * @param sourceUri identifier of source
   * @param queryString a SPARQL select query
   * @return a result set
   * @throws IOException If an I/O error occurs
   */
  public QueryResultSet executeSelect(String sourceUri, String queryString) throws IOException {

    final Query query = QueryFactory.create(queryString);

    final List<Var> queryVars = query.getProjectVars();
    final List<String> varNames =
        queryVars.stream().map(Node_Variable::getName).collect(Collectors.toList());


    final Dataset dataset = DatasetFactory.create(sourceUri);

    QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
      final ResultSet results = qexec.execSelect();
      return new QueryResultSetImpl(qexec, results, varNames);
  }
}
