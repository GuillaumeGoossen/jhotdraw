package org.jhotdraw.draw.action.alignment;

import java.awt.geom.*;
import java.util.Collection;
import org.jhotdraw.draw.action.AlignAction;
import org.jhotdraw.draw.event.TransformEdit;
import org.jhotdraw.draw.figure.Figure;

public class NorthAlignmentStrategy implements AlignmentStrategy {
  private AlignAction alignAction;

  public NorthAlignmentStrategy() {}

  public void setAlignAction(AlignAction alignAction) {
    this.alignAction = alignAction;
  }

  @Override
  public void align(Collection<Figure> selectedFigures, Rectangle2D.Double selectionBounds) {
    double y = selectionBounds.y;
    for (Figure f : selectedFigures) {
      if (f.isTransformable()) {
        f.willChange();
        Rectangle2D.Double b = f.getBounds();
        AffineTransform tx = new AffineTransform();
        tx.translate(0, y - b.y);
        f.transform(tx);
        f.changed();
        alignAction.triggerUndoableEditHappened(new TransformEdit(f, tx));
      }
    }
  }
}
