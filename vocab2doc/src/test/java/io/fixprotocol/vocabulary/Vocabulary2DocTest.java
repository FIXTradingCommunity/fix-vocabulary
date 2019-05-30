package io.fixprotocol.vocabulary;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class Vocabulary2DocTest {


  @Test
  void generateMarkdown() throws IOException {
    String sourceUri = "fix-vocabulary.ttl";
    String targetFilename = "vocabulary.md";
    String format = "md";
    Vocabulary2Doc.generate(sourceUri, targetFilename, format);
    File file = new File(targetFilename);
    assertTrue(file.exists());
  }
  
  @Test
  void generateHtml() throws IOException {
    String sourceUri = "fix-vocabulary.ttl";
    String targetFilename = "vocabulary.html";
    String format = "html";
    Vocabulary2Doc.generate(sourceUri, targetFilename, format);
    File file = new File(targetFilename);
    assertTrue(file.exists());
  }


}
