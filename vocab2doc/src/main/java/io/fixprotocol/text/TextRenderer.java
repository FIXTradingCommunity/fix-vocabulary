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
 * Renders text in another format
 * 
 * @author Don Mendelson
 *
 */
public interface TextRenderer {

  enum ParagraphOption {
    /**
     * Use {@code <p>} paragraph open and close tags
     */
    USE_PARAGRAPH, 
    /**
     * Use {@code <br>} line break tag
     */
    USE_BREAK
  }

  /**
   * Renders text 
   * 
   * @param input text 
   * @return text as HTML
   */
  String render(String input);

  /**
   * Renders text 
   * 
   * @param input text 
   * @param output append to
   * @throws IOException If an I/O error occurs
   */
  void render(String input, Appendable output) throws IOException;

  void setParagraphOption(ParagraphOption paragraphOption);

}
