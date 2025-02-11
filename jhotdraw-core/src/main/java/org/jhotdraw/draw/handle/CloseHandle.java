/*
 * @(#)CloseHandle.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.handle;

import java.awt.*;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.locator.Locator;
import org.jhotdraw.draw.locator.RelativeLocator;

/** A {@link Handle} which requests to remove its owning figure when clicked. */
public class CloseHandle extends LocatorHandle {

  private boolean pressed;

  public CloseHandle(Figure owner) {
    this(owner, new RelativeLocator(1.0, 0.0));
  }

  public CloseHandle(Figure owner, Locator locator) {
    super(owner, locator);
  }

  @Override
  protected int getHandlesize() {
    return 9;
  }

  /** Draws this handle. */
  @Override
  public void draw(Graphics2D g) {
    drawRectangle(g, (pressed) ? Color.orange : Color.white, Color.black);
    Rectangle r = getBounds();
    g.drawLine(r.x, r.y, r.x + r.width, r.y + r.height);
    g.drawLine(r.x + r.width, r.y, r.x, r.y + r.height);
  }

  /** Returns a cursor for the handle. */
  @Override
  public Cursor getCursor() {
    return Cursor.getDefaultCursor();
  }

  @Override
  public void trackEnd(Point anchor, Point lead, int modifiersEx) {
    pressed = basicGetBounds().contains(lead);
    if (pressed) {
      getOwner().requestRemove();
    }
    fireAreaInvalidated(getDrawingArea());
  }

  @Override
  public void trackStart(Point anchor, int modifiersEx) {
    pressed = true;
    fireAreaInvalidated(getDrawingArea());
  }

  @Override
  public void trackStep(Point anchor, Point lead, int modifiersEx) {
    boolean oldValue = pressed;
    pressed = basicGetBounds().contains(lead);
    if (oldValue != pressed) {
      fireAreaInvalidated(getDrawingArea());
    }
  }
}
