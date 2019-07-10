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

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Vocabulary2DocTest {
  
  private Vocabulary2Doc vocabulary2Doc;

  @BeforeAll
  public static void setUpOnce() {
    new File("target/test").mkdirs();
  }
  
  @BeforeEach
  public void setUp() throws Exception {
    vocabulary2Doc = new Vocabulary2Doc();
  }

  @Test
  void generateMarkdown() throws IOException {
    String sourceUri = "fix-vocabulary.ttl";
    String targetFilename = "target/test/Vocabulary.md";
    String format = "md";
    vocabulary2Doc.generate(sourceUri, targetFilename, format);
    File file = new File(targetFilename);
    assertTrue(file.exists());
  }
  
  @Test
  void generateHtml() throws IOException {
    String sourceUri = "fix-vocabulary.ttl";
    String targetFilename = "target/test/Vocabulary.html";
    String format = "html";
    vocabulary2Doc.generate(sourceUri, targetFilename, format);
    File file = new File(targetFilename);
    assertTrue(file.exists());
  }


}
