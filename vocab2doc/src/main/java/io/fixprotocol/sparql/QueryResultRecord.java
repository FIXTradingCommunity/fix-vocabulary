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

import java.util.Iterator;

public interface QueryResultRecord {
  
  /**
   * Returns the value of a variable as a String
   * @param variableName name of the variable to retrieve
   * @return variable value as a String
   */
  public String getValue(String variableName);
  
  /** Return true if the named variable is in this QueryResultRecord */
  public boolean contains(String variableName);

  /** Iterate over the variable names (strings) in this QueryResultRecord.
   * @return Iterator of strings
   */ 
  public Iterator<String> variableNames() ;

}
