/*
 * @(#)SlantedLiner.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.liner;

import java.awt.geom.*;
import java.util.*;
import org.jhotdraw.draw.connector.Connector;
import org.jhotdraw.draw.figure.ConnectionFigure;
import org.jhotdraw.draw.figure.LineConnectionFigure;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.geom.Geom;
import org.jhotdraw.geom.path.BezierPath;

/** SlantedLiner. */
public class SlantedLiner implements Liner {

  private double slantSize;

  public SlantedLiner() {
    this(20);
  }

  public SlantedLiner(double slantSize) {
    this.slantSize = slantSize;
  }

  @Override
  public Collection<Handle> createHandles(BezierPath path) {
    return Collections.emptyList();
  }

  @Override
  public void lineout(ConnectionFigure figure) {
    BezierPath path = ((LineConnectionFigure) figure).getBezierPath();
    Connector start = figure.getStartConnector();
    Connector end = figure.getEndConnector();
    if (start == null || end == null || path == null) {
      return;
    }

    if (figure.getStartFigure() == figure.getEndFigure()) {
      handleSameFigure(start, end, path, figure);
    } else {
      handleDifferentFigures(start, end, path, figure);
    }

    for (BezierPath.Node node : path.nodes()) {
      node.setMask(BezierPath.C0_MASK);
    }
    path.invalidatePath();
  }

  private void handleSameFigure(
      Connector start, Connector end, BezierPath path, ConnectionFigure figure) {
    adjustPathSize(path, 5);
    Point2D.Double sp = start.findStart(figure);
    Point2D.Double ep = end.findEnd(figure);
    Rectangle2D.Double sb = start.getBounds();
    Rectangle2D.Double eb = end.getBounds();
    int soutcode = determineOutcode(sb, sp, eb);
    int eoutcode = determineOutcode(eb, ep, sb);
    moveNodes(sp, ep, soutcode, eoutcode, path);
  }

  private void handleDifferentFigures(
      Connector start, Connector end, BezierPath path, ConnectionFigure figure) {
    adjustPathSize(path, 4);
    Point2D.Double sp = start.findStart(figure);
    Point2D.Double ep = end.findEnd(figure);
    Rectangle2D.Double sb = start.getBounds();
    Rectangle2D.Double eb = end.getBounds();
    int soutcode = determineOutcode(sb, sp, eb);
    int eoutcode = determineOutcode(eb, ep, sb);
    moveNodes(sp, ep, soutcode, eoutcode, path);
  }

  private void adjustPathSize(BezierPath path, int size) {
    while (path.size() < size) {
      path.add(1, new BezierPath.Node(0, 0));
    }
    while (path.size() > size) {
      path.remove(1);
    }
  }

  private int determineOutcode(
      Rectangle2D.Double bounds, Point2D.Double point, Rectangle2D.Double otherBounds) {
    int outcode = bounds.outcode(point);
    if (outcode == 0) {
      outcode = Geom.outcode(bounds, otherBounds);
    }
    return outcode;
  }

  private void moveNodes(
      Point2D.Double sp, Point2D.Double ep, int soutcode, int eoutcode, BezierPath path) {
    path.nodes().get(0).moveTo(sp);
    path.nodes().get(path.size() - 1).moveTo(ep);
    moveNode(path.nodes().get(1), sp, soutcode);
    moveNode(path.nodes().get(2), ep, eoutcode);
  }

  private void moveNode(BezierPath.Node node, Point2D.Double point, int outcode) {
    if ((outcode & Geom.OUT_RIGHT) != 0) {
      node.moveTo(point.x + slantSize, point.y);
    } else if ((outcode & Geom.OUT_LEFT) != 0) {
      node.moveTo(point.x - slantSize, point.y);
    } else if ((outcode & Geom.OUT_BOTTOM) != 0) {
      node.moveTo(point.x, point.y + slantSize);
    } else {
      node.moveTo(point.x, point.y - slantSize);
    }
  }

  // @Override
  // public void read(DOMInput in) {
  // slantSize = in.getAttribute("slant", 20d);
  // }
  //
  // @Override
  // public void write(DOMOutput out) {
  // out.addAttribute("slant", slantSize);
  // }

  @Override
  public Liner clone() {
    try {
      return (Liner) super.clone();
    } catch (CloneNotSupportedException ex) {
      InternalError error = new InternalError(ex.getMessage());
      error.initCause(ex);
      throw error;
    }
  }
}
