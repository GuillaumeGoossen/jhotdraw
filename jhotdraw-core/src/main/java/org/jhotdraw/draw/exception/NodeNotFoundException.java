package org.jhotdraw.draw.exception;

import java.awt.geom.Point2D;

public class NodeNotFoundException extends Exception {
  public NodeNotFoundException(Point2D.Double p) {
    super("No node found for point " + p);
  }
}
