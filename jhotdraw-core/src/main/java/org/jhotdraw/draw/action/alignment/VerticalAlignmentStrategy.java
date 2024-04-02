package org.jhotdraw.draw.action.alignment;

import java.awt.geom.*;
import java.util.Collection;
import org.jhotdraw.draw.action.AlignAction;
import org.jhotdraw.draw.event.TransformEdit;
import org.jhotdraw.draw.figure.Figure;

public class VerticalAlignmentStrategy implements AlignmentStrategy {

  private AlignAction alignAction;

  public VerticalAlignmentStrategy() {}

  public void setAlignAction(AlignAction alignAction) {
    this.alignAction = alignAction;
  }

  public void align(Collection<Figure> selectedFigures, Rectangle2D.Double selectionBounds) {
    double y = selectionBounds.y + selectionBounds.height / 2;
    for (Figure f : selectedFigures) {
      if (f.isTransformable()) {
        f.willChange();
        Rectangle2D.Double b = f.getBounds();
        AffineTransform tx = new AffineTransform();
        tx.translate(0, y - b.y - b.height / 2);
        f.transform(tx);
        f.changed();
        alignAction.triggerUndoableEditHappened(new TransformEdit(f, tx));
      }
    }
  }
}
