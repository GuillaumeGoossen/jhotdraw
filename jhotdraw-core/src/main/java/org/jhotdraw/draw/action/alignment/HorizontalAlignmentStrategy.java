package org.jhotdraw.draw.action.alignment;

import java.awt.geom.*;
import java.util.Collection;
import org.jhotdraw.draw.action.AlignAction;
import org.jhotdraw.draw.event.TransformEdit;
import org.jhotdraw.draw.figure.Figure;

public class HorizontalAlignmentStrategy implements AlignmentStrategy {

  private AlignAction alignAction;

  public HorizontalAlignmentStrategy() {}

  public void setAlignAction(AlignAction alignAction) {
    this.alignAction = alignAction;
  }

  public void align(Collection<Figure> selectedFigures, Rectangle2D.Double selectionBounds) {
    double x = selectionBounds.x + selectionBounds.width / 2;
    for (Figure f : selectedFigures) {
      if (f.isTransformable()) {
        f.willChange();
        Rectangle2D.Double b = f.getBounds();
        AffineTransform tx = new AffineTransform();
        tx.translate(x - b.x - b.width / 2, 0);
        f.transform(tx);
        f.changed();
        alignAction.triggerUndoableEditHappened(new TransformEdit(f, tx));
      }
    }
  }
}
