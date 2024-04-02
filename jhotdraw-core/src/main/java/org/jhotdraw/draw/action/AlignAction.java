/*
 * @(#)AlignAction.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.action;

import java.awt.geom.*;
import java.util.*;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.event.TransformEdit;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.undo.CompositeEdit;
import org.jhotdraw.util.ResourceBundleUtil;

public class AlignAction extends AbstractSelectedAction {

  private static final long serialVersionUID = 1L;
  protected ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
  private final Alignment alignment;

  public AlignAction(DrawingEditor editor, Alignment alignment) {
    super(editor);
    this.alignment = alignment;
    updateEnabledState();
  }

  @Override
  public void updateEnabledState() {
    if (getView() != null) {
      setEnabled(getView().isEnabled() && getView().getSelectionCount() > 1);
    } else {
      setEnabled(false);
    }
  }

  @Override
  public void actionPerformed(java.awt.event.ActionEvent e) {
    CompositeEdit edit = new CompositeEdit(labels.getString("edit.align.text"));
    fireUndoableEditHappened(edit);
    alignFigures(getView().getSelectedFigures(), getSelectionBounds());
    fireUndoableEditHappened(edit);
  }

  public enum Alignment {
    NORTH,
    EAST,
    WEST,
    SOUTH,
    VERTICAL,
    HORIZONTAL
  }

  protected Rectangle2D.Double getSelectionBounds() {
    Rectangle2D.Double bounds = null;
    for (Figure f : getView().getSelectedFigures()) {
      if (bounds == null) {
        bounds = f.getBounds();
      } else {
        bounds.add(f.getBounds());
      }
    }
    return bounds;
  }

  protected void alignFigures(
      Collection<Figure> selectedFigures, Rectangle2D.Double selectionBounds) {
    switch (alignment) {
      case NORTH:
        alignNorth(selectedFigures, selectionBounds);
        break;
      case EAST:
        alignEast(selectedFigures, selectionBounds);
        break;
      case WEST:
        alignWest(selectedFigures, selectionBounds);
        break;
      case SOUTH:
        alignSouth(selectedFigures, selectionBounds);
        break;
      case VERTICAL:
        alignVertical(selectedFigures, selectionBounds);
        break;
      case HORIZONTAL:
        alignHorizontal(selectedFigures, selectionBounds);
        break;
    }
  }

  protected void alignNorth(
      Collection<Figure> selectedFigures, Rectangle2D.Double selectionBounds) {
    double y = selectionBounds.y;
    for (Figure f : selectedFigures) {
      if (f.isTransformable()) {
        f.willChange();
        Rectangle2D.Double b = f.getBounds();
        AffineTransform tx = new AffineTransform();
        tx.translate(0, y - b.y);
        f.transform(tx);
        f.changed();
        fireUndoableEditHappened(new TransformEdit(f, tx));
      }
    }
  }

  protected void alignEast(Collection<Figure> selectedFigures, Rectangle2D.Double selectionBounds) {
    double x = selectionBounds.x + selectionBounds.width;
    for (Figure f : selectedFigures) {
      if (f.isTransformable()) {
        f.willChange();
        Rectangle2D.Double b = f.getBounds();
        AffineTransform tx = new AffineTransform();
        tx.translate(x - b.x - b.width, 0);
        f.transform(tx);
        f.changed();
        fireUndoableEditHappened(new TransformEdit(f, tx));
      }
    }
  }

  protected void alignWest(Collection<Figure> selectedFigures, Rectangle2D.Double selectionBounds) {
    double x = selectionBounds.x;
    for (Figure f : selectedFigures) {
      if (f.isTransformable()) {
        f.willChange();
        Rectangle2D.Double b = f.getBounds();
        AffineTransform tx = new AffineTransform();
        tx.translate(x - b.x, 0);
        f.transform(tx);
        f.changed();
        fireUndoableEditHappened(new TransformEdit(f, tx));
      }
    }
  }

  protected void alignSouth(
      Collection<Figure> selectedFigures, Rectangle2D.Double selectionBounds) {
    double y = selectionBounds.y + selectionBounds.height;
    for (Figure f : selectedFigures) {
      if (f.isTransformable()) {
        f.willChange();
        Rectangle2D.Double b = f.getBounds();
        AffineTransform tx = new AffineTransform();
        tx.translate(0, y - b.y - b.height);
        f.transform(tx);
        f.changed();
        fireUndoableEditHappened(new TransformEdit(f, tx));
      }
    }
  }

  protected void alignVertical(
      Collection<Figure> selectedFigures, Rectangle2D.Double selectionBounds) {
    double y = selectionBounds.y + selectionBounds.height / 2;
    for (Figure f : selectedFigures) {
      if (f.isTransformable()) {
        f.willChange();
        Rectangle2D.Double b = f.getBounds();
        AffineTransform tx = new AffineTransform();
        tx.translate(0, y - b.y - b.height / 2);
        f.transform(tx);
        f.changed();
        fireUndoableEditHappened(new TransformEdit(f, tx));
      }
    }
  }

  protected void alignHorizontal(
      Collection<Figure> selectedFigures, Rectangle2D.Double selectionBounds) {
    double x = selectionBounds.x + selectionBounds.width / 2;
    for (Figure f : selectedFigures) {
      if (f.isTransformable()) {
        f.willChange();
        Rectangle2D.Double b = f.getBounds();
        AffineTransform tx = new AffineTransform();
        tx.translate(x - b.x - b.width / 2, 0);
        f.transform(tx);
        f.changed();
        fireUndoableEditHappened(new TransformEdit(f, tx));
      }
    }
  }
}
