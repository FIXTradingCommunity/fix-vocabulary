package io.fixprotocol.vocabulary;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Vocabulary2DocTest {
  
  private Vocabulary2Doc vocabulary2Doc;

  @BeforeEach
  public void setUp() throws Exception {
    vocabulary2Doc = new Vocabulary2Doc();
  }

  @Test
  void generateMarkdown() throws IOException {
    String sourceUri = "fix-vocabulary.ttl";
    String targetFilename = "vocabulary.md";
    String format = "md";
    vocabulary2Doc.generate(sourceUri, targetFilename, format);
    File file = new File(targetFilename);
    assertTrue(file.exists());
  }
  
  @Test
  void generateHtml() throws IOException {
    String sourceUri = "fix-vocabulary.ttl";
    String targetFilename = "vocabulary.html";
    String format = "html";
    vocabulary2Doc.generate(sourceUri, targetFilename, format);
    File file = new File(targetFilename);
    assertTrue(file.exists());
  }


}
