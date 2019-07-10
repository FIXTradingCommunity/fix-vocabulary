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
import java.util.Arrays;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;

/**
 * Renders plain test as HTML with UTF-8 encoding <br>
 * Default transformations
 * <ul>
 * <li>Treat each text block as a paragrah. That is, begin with {@code <p>} tag and end with {@code </p>} tag.
 * <li>Translate newline (linefeed or CRLF) to {@code </p>} tag. Subsequent text should start a new paragraph.</li>
 * <li>Escape ampersand, less-than, and greater-than characters.</li>
 * <li>Substitute apostrophe escape sequence {@code &apos;} for apostrophe (U+0027)</li>
 * <li>Substitute quote escape sequence {@code &lsquo;} for single left quote (U+2018)</li>
 * <li>Substitute quote escape sequence {@code &rsquo;} for single right quote (U+2019)</li>
 * <li>Substitute quote tag {@code q>} for double left quote (U+201C)</li>
 * <li>Substitute close quote tag {@code</q>} for double right quote (U+201D)</li>
 * <li>Substitute quote tag {@code <q>} for even numbered instances of double quote (U+0022), and close quote tag {@code</q>} for
 * odd numbered instances</li>
 * </ul>
 * 
 * @author Don Mendelson
 *
 */
class PlainText2HTMLRenderer implements TextRenderer {

  public class Transform implements Comparable<Transform> {
    private final String from;
    private final String to;
    private final Function<String, String> transformer;

    /**
     * Constructs a Transform that invokes a Function
     * 
     * @param from value to be substituted
     * @param transformer a Function to invoke
     */
    public Transform(String from, Function<String, String> transformer) {
      this.from = from;
      this.to = null;
      this.transformer = transformer;
    }

    /**
     * Constructs a Transform with simple substitution
     * 
     * @param from value to be substituted
     * @param to substitute value
     */
    public Transform(String from, String to) {
      this.from = from;
      this.to = to;
      this.transformer = from1 -> Transform.this.to;
    }

    /**
     * Compares {@code from} of two transforms lexically
     * 
     * Same as {@code String.compareTo()} except that longer String is favored if they are of
     * unequal length.
     * 
     * @param other
     * @return
     */
    @Override
    public int compareTo(Transform other) {
      int len1 = from.length();
      int len2 = other.from.length();
      int lim = Math.min(len1, len2);

      int k = 0;
      while (k < lim) {
        char c1 = from.charAt(k);
        char c2 = other.from.charAt(k);
        if (c1 != c2) {
          return c1 - c2;
        }
        k++;
      }
      return len2 - len1;
    }

    public String getFrom() {
      return from;
    }

    public String transform() {
      return transformer.apply(getFrom());
    }

  }

  class DoubleQuoteTransform extends Transform {

    public DoubleQuoteTransform() {
      super("\"", new Function<String, String>() {
        @Override
        public String apply(String from) {
          String to = quoteCount % 2 == 0 ? "<q>" : "</q>";
          quoteCount++;
          return to;
        }

      });
    }
  }

  class EndParagraphTransform extends Transform {

    public EndParagraphTransform(String from) {
      super(from, new Function<String, String>() {

        @Override
        public String apply(String from) {
          if (paragraphOption == ParagraphOption.USE_PARAGRAPH) {
            paragraphCount = 0;
            return "</p>";
          } else {
            return "<br/>";
          }
        }
      });
    }
  }

  /**
   * Renders text from a command line argument
   * 
   * @param args one argument is the text to be rendered
   */
  public static void main(String[] args) {
    TextRenderer renderer = new PlainText2HTMLRenderer();
    System.out.println(renderer.render(args[0]));
  }

  private int paragraphCount = 0;
  private int lastParagraphCount = -1;
  private final SortedSet<Transform> transforms;
  private ParagraphOption paragraphOption = ParagraphOption.USE_PARAGRAPH;
  private int quoteCount = 0;


  @Override
  public void setParagraphOption(ParagraphOption paragraphOption) {
    this.paragraphOption = paragraphOption;
  }

  public PlainText2HTMLRenderer() {
    this.transforms = new TreeSet<Transform>();
    addDefaultTransforms();
  }

  public PlainText2HTMLRenderer(Collection<Transform> transforms) {
    this.transforms = new TreeSet<Transform>(transforms);
  }

  public void addDefaultTransforms() {
    this.transforms.addAll(Arrays.asList(
        new EndParagraphTransform("\n"),
        new EndParagraphTransform("\r\n"), 
        new Transform("\u0026", "&amp;"),
        new Transform("\u0027", "&apos;"),
        new Transform("\u003c", "&lt;"),
        new Transform("\u003e", "&gt;"),
        new Transform("\u2018", "&lsquo;"), 
        new Transform("\u2019", "&rsquo;"),
        new Transform("\u201c", "<q>"), 
        new Transform("\u201d", "</q>"),
        new Transform("\u2013", "&#x2013;"), 
        new Transform("\u2014", "&#x2014;"),
        new Transform("\u2022", "&bull;"), 
        new DoubleQuoteTransform()));
  }

  public void addTransforms(Collection<Transform> transforms) {
    this.transforms.addAll(transforms);
  }

  @Override
  public String render(String input) {
    StringBuilder sb = new StringBuilder();

    try {
      render(input, sb);
      return sb.toString();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public void render(String input, Appendable output) throws IOException {
    int inputLen = input.length();
    if (inputLen == 0) {
      return;
    }
    int index = 0;
    lastParagraphCount = -1;
    quoteCount = 0;

    while (index < inputLen) {
      output.append(paragraph());

      boolean transformed = false;
      for (Transform transform : transforms) {
        if (transform.getFrom().regionMatches(0, input, index, transform.getFrom().length())) {
          output.append(transform.transform());
          index += transform.getFrom().length();
          transformed = true;
          break;
        }
      }
      if (!transformed) {
        output.append(input.charAt(index));
        index++;
      }
    }

    output.append(closeLastParagraph());

  }

  private String paragraph() {
    if (paragraphOption == ParagraphOption.USE_PARAGRAPH && lastParagraphCount != paragraphCount) {
      String to = paragraphCount % 2 == 0 ? "<p>" : "</p>";
      paragraphCount++;
      lastParagraphCount = paragraphCount;
      return to;
    } else {
      return "";
    }
  }

  private String closeLastParagraph() {
    if (paragraphOption == ParagraphOption.USE_PARAGRAPH) {
      return "</p>";
    } else {
      return "";
    }
  }
}
