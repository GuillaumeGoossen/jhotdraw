package org.jhotdraw.draw.tool;

import static org.jhotdraw.draw.AttributeKeys.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.event.FigureEvent;
import org.jhotdraw.draw.event.FigureListener;
import org.jhotdraw.draw.event.FigureListenerAdapter;
import org.jhotdraw.draw.figure.TextHolderFigure;

/**
 * CustomTextField is a class that provides a text field overlay for a TextHolderFigure. It is used
 * to edit the text of a TextHolderFigure directly within the drawing view.
 */
public class CustomTextField {

  private TextHolderFigure editedFigure; // The figure being edited
  private JTextField textField; // The text field overlay
  private DrawingView view; // The drawing view
  private FigureListener figureHandler = // Listener for figure changes
      new FigureListenerAdapter() {
        @Override
        public void attributeChanged(FigureEvent e) {
          updateWidget();
        }
      };

  /** Constructs a new CustomTextField with a default JTextField. */
  public CustomTextField() {
    textField = new JTextField(20);
  }

  /** Requests focus for the text field. */
  public void requestFocus() {
    textField.requestFocus();
  }

  /**
   * Creates an overlay for the given TextHolderFigure in the specified DrawingView.
   *
   * @param view the DrawingView
   * @param figure the TextHolderFigure
   */
  public void createOverlay(DrawingView view, TextHolderFigure figure) {
    view.getComponent().add(textField, 0);
    textField.setText(figure.getText());
    textField.setColumns(figure.getTextColumns());
    textField.selectAll();
    textField.setVisible(true);
    editedFigure = figure;
    editedFigure.addFigureListener(figureHandler);
    this.view = view;
    updateWidget();
  }

  /** Updates the text field overlay to match the attributes of the edited figure. */
  protected void updateWidget() {
    Font font = editedFigure.getFont();
    font = font.deriveFont(font.getStyle(), (float) (editedFigure.getFontSize()));
    textField.setFont(font);
    textField.setForeground(editedFigure.getTextColor());
    textField.setBackground(editedFigure.getFillColor());
    Rectangle2D.Double fDrawBounds = editedFigure.getBounds();
    Point2D.Double fDrawLoc = new Point2D.Double(fDrawBounds.getX(), fDrawBounds.getY());
    if (editedFigure.attr().get(TRANSFORM) != null) {
      editedFigure.attr().get(TRANSFORM).transform(fDrawLoc, fDrawLoc);
    }
    Point fViewLoc = view.drawingToView(fDrawLoc);
    Rectangle fViewBounds = view.drawingToView(fDrawBounds);
    fViewBounds.x = fViewLoc.x;
    fViewBounds.y = fViewLoc.y;
    Dimension tfDim = textField.getPreferredSize();
    Insets tfInsets = textField.getInsets();
    float fontBaseline = textField.getGraphics().getFontMetrics(font).getMaxAscent();
    double fBaseline = editedFigure.getBaseline() * view.getScaleFactor();
    textField.setBounds(
        fViewBounds.x - tfInsets.left,
        fViewBounds.y - tfInsets.top - (int) (fontBaseline - fBaseline),
        Math.max(fViewBounds.width + tfInsets.left + tfInsets.right, tfDim.width),
        Math.max(fViewBounds.height + tfInsets.top + tfInsets.bottom, tfDim.height));
  }

  /**
   * Returns the insets of the text field.
   *
   * @return the insets
   */
  public Insets getInsets() {
    return textField.getInsets();
  }

  /**
   * Adds an ActionListener to the text field.
   *
   * @param listener the ActionListener to be added
   */
  public void addActionListener(ActionListener listener) {
    textField.addActionListener(listener);
  }

  /**
   * Removes an ActionListener from the text field.
   *
   * @param listener the ActionListener to be removed
   */
  public void removeActionListener(ActionListener listener) {
    textField.removeActionListener(listener);
  }

  /**
   * Returns the text of the text field.
   *
   * @return the text
   */
  public String getText() {
    return textField.getText();
  }

  /**
   * Returns the preferred size of the text field for the specified number of columns.
   *
   * @param cols the number of columns
   * @return the preferred size
   */
  public Dimension getPreferredSize(int cols) {
    textField.setColumns(cols);
    return textField.getPreferredSize();
  }

  /** Removes the text field overlay from the drawing view and cleans up listeners. */
  public void endOverlay() {
    view.getComponent().requestFocus();
    if (textField != null) {
      textField.setVisible(false);
      view.getComponent().remove(textField);
      Rectangle bounds = textField.getBounds();
      view.getComponent().repaint(bounds.x, bounds.y, bounds.width, bounds.height);
    }
    if (editedFigure != null) {
      editedFigure.removeFigureListener(figureHandler);
      editedFigure = null;
    }
  }
}
