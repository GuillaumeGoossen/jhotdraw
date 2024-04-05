package org.jhotdraw.draw.tool;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.*;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.event.FigureEvent;
import org.jhotdraw.draw.event.FigureListener;
import org.jhotdraw.draw.event.FigureListenerAdapter;
import org.jhotdraw.draw.figure.TextHolderFigure;

/**
 * CustomTextArea is a class that creates a JTextArea overlay for a given TextHolderFigure. It
 * provides methods to create, update, and remove the overlay.
 */
public class CustomTextArea {
  protected JScrollPane editScrollContainer;
  protected JTextArea textArea;
  protected DrawingView view;
  private TextHolderFigure editedFigure;
  private FigureListener figureHandler =
      new FigureListenerAdapter() {
        @Override
        public void attributeChanged(FigureEvent e) {
          updateWidget();
        }
      };

  /** Constructor for CustomTextArea. Initializes the JTextArea and JScrollPane. */
  public CustomTextArea() {
    textArea = new JTextArea();
    textArea.setWrapStyleWord(true);
    textArea.setLineWrap(true);
    editScrollContainer =
        new JScrollPane(
            textArea,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    editScrollContainer.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    editScrollContainer.setBorder(BorderFactory.createLineBorder(Color.black));
  }

  /**
   * Creates an overlay for the given TextHolderFigure on the given DrawingView.
   *
   * @param view The DrawingView on which to create the overlay.
   * @param figure The TextHolderFigure for which to create the overlay.
   */
  public void createOverlay(DrawingView view, TextHolderFigure figure) {
    view.getComponent().add(editScrollContainer, 0);
    editedFigure = figure;
    this.view = view;
    if (editedFigure != null) {
      editedFigure.addFigureListener(figureHandler);
      updateWidget();
    }
  }

  /** Updates the JTextArea to match the attributes of the TextHolderFigure. */
  protected void updateWidget() {
    Font f = editedFigure.getFont();
    f = f.deriveFont(f.getStyle(), (float) (editedFigure.getFontSize() * view.getScaleFactor()));
    textArea.setFont(f);
    textArea.setForeground(editedFigure.getTextColor());
    textArea.setBackground(editedFigure.getFillColor());
  }

  /**
   * Sets the bounds of the JScrollPane and sets the text of the JTextArea.
   *
   * @param r The bounds for the JScrollPane.
   * @param text The text for the JTextArea.
   */
  public void setBounds(Rectangle2D.Double r, String text) {
    textArea.setText(text);
    editScrollContainer.setBounds(view.drawingToView(r));
    editScrollContainer.setVisible(true);
    textArea.setCaretPosition(0);
    textArea.requestFocus();
  }

  /** Requests focus for the JTextArea. */
  public void requestFocus() {
    textArea.requestFocus();
  }

  /**
   * Returns the text of the JTextArea.
   *
   * @return The text of the JTextArea.
   */
  public String getText() {
    return textArea.getText();
  }

  /**
   * Returns the preferred size of the JTextArea.
   *
   * @param cols The number of columns in the JTextArea.
   * @return The preferred size of the JTextArea.
   */
  public Dimension getPreferredSize(int cols) {
    return new Dimension(textArea.getWidth(), textArea.getHeight());
  }

  /**
   * Ends the overlay by removing the JScrollPane from the DrawingView and removing the
   * FigureListener from the TextHolderFigure.
   */
  public void endOverlay() {
    view.getComponent().requestFocus();
    if (editScrollContainer != null) {
      editScrollContainer.setVisible(false);
      view.getComponent().remove(editScrollContainer);
      Rectangle bounds = editScrollContainer.getBounds();
      view.getComponent().repaint(bounds.x, bounds.y, bounds.width, bounds.height);
    }
    if (editedFigure != null) {
      editedFigure.removeFigureListener(figureHandler);
      editedFigure = null;
    }
  }
}
