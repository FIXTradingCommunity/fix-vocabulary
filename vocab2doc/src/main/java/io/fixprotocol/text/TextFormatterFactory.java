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

/**
 * Creates an instance of a {@link TextFormatter}
 * 
 * @author Don Mendelson
 *
 */
public class TextFormatterFactory {

  /**
   * Code for HTML protocol
   */
  public static final String HTML = "html";

  /**
   * Code for markdown protocol
   */
  public static final String MARKDOWN = "md";

  /**
   * Creates an instance of a {@link TextFormatter}
   * 
   * @param format a code for a text protocol
   * @return an implementation of TextFormatter
   * @throws IllegalArgumentException if {@link format} is unrecognized
   */
  public static TextFormatter create(String format) {
    switch (format) {
      case HTML:
        return new HtmlFormatter();
      case MARKDOWN:
        return new MarkdownFormatter();
      default:
        throw new IllegalArgumentException();
    }
  }

}
