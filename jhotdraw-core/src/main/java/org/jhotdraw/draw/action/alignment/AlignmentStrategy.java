package org.jhotdraw.draw.action.alignment;

import java.awt.geom.Rectangle2D;
import java.util.Collection;
import org.jhotdraw.draw.figure.Figure;

public interface AlignmentStrategy {
  void align(Collection<Figure> selectedFigures, Rectangle2D.Double selectionBounds);
}
