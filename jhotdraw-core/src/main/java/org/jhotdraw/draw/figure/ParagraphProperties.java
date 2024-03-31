package org.jhotdraw.draw.figure;

import java.text.AttributedCharacterIterator;

/**
 * The ParagraphProperties class encapsulates the properties of a paragraph that are used when
 * drawing the paragraph. This includes the styled text of the paragraph, the vertical position of
 * the paragraph, the maximum vertical position, the left and right margins, the tab stops, and the
 * tab count.
 *
 * <p>By encapsulating these properties into a single object, we can make the code that uses these
 * properties more readable and maintainable, and we can reduce the number of parameters needed by
 * methods that use these properties.
 */
public class ParagraphProperties {
  private AttributedCharacterIterator styledText;
  private float verticalPos;
  private float maxVerticalPos;
  private float leftMargin;
  private float rightMargin;
  private float[] tabStops;
  private int tabCount;

  public AttributedCharacterIterator getStyledText() {
    return styledText;
  }

  public void setStyledText(AttributedCharacterIterator styledText) {
    this.styledText = styledText;
  }

  public float getVerticalPos() {
    return verticalPos;
  }

  public void setVerticalPos(float verticalPos) {
    this.verticalPos = verticalPos;
  }

  public float getMaxVerticalPos() {
    return maxVerticalPos;
  }

  public void setMaxVerticalPos(float maxVerticalPos) {
    this.maxVerticalPos = maxVerticalPos;
  }

  public float getLeftMargin() {
    return leftMargin;
  }

  public void setLeftMargin(float leftMargin) {
    this.leftMargin = leftMargin;
  }

  public float getRightMargin() {
    return rightMargin;
  }

  public void setRightMargin(float rightMargin) {
    this.rightMargin = rightMargin;
  }

  public float[] getTabStops() {
    return tabStops;
  }

  public void setTabStops(float[] tabStops) {
    this.tabStops = tabStops;
  }

  public int getTabCount() {
    return tabCount;
  }

  public void setTabCount(int tabCount) {
    this.tabCount = tabCount;
  }
}
