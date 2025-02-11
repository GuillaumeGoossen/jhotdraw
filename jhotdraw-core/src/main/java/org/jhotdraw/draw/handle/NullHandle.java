/*
 * @(#)NullHandle.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.handle;

import java.awt.*;
import java.util.*;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.locator.Locator;
import org.jhotdraw.draw.locator.RelativeLocator;

/**
 * A handle that doesn't change the owned figure. Its only purpose is to show feedback that a figure
 * is selected.
 */
public class NullHandle extends LocatorHandle {

  public NullHandle(Figure owner, Locator locator) {
    super(owner, locator);
  }

  @Override
  public Cursor getCursor() {
    return Cursor.getDefaultCursor();
  }

  /** Creates handles for each lead of a figure and adds them to the provided collection. */
  public static void addLeadHandles(Figure f, Collection<Handle> handles) {
    handles.add(new NullHandle(f, new RelativeLocator(0f, 0f)));
    handles.add(new NullHandle(f, new RelativeLocator(0f, 1f)));
    handles.add(new NullHandle(f, new RelativeLocator(1f, 0f)));
    handles.add(new NullHandle(f, new RelativeLocator(1f, 1f)));
  }

  /** Draws this handle. Null Handles are drawn as unfilled rectangles. */
  @Override
  public void draw(Graphics2D g) {
    drawRectangle(
        g,
        getEditor().getHandleAttribute(HandleAttributeKeys.NULL_HANDLE_FILL_COLOR),
        getEditor().getHandleAttribute(HandleAttributeKeys.NULL_HANDLE_STROKE_COLOR));
  }
}
