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
import org.jhotdraw.draw.action.alignment.AlignmentStrategy;
import org.jhotdraw.draw.event.TransformEdit;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.undo.CompositeEdit;
import org.jhotdraw.util.ResourceBundleUtil;

public class AlignAction extends AbstractSelectedAction {
  private final AlignmentStrategy alignmentStrategy;
  private static final long serialVersionUID = 1L;
  protected ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");

  public AlignAction(DrawingEditor editor, AlignmentStrategy alignmentStrategy) {
    super(editor);
    this.alignmentStrategy = alignmentStrategy;
    updateEnabledState();
  }

  public void triggerUndoableEditHappened(TransformEdit edit) {
    fireUndoableEditHappened(edit);
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
    alignmentStrategy.align(selectedFigures, selectionBounds);
  }
}
