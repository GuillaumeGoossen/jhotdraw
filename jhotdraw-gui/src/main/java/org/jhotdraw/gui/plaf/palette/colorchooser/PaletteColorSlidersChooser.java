/*
 * @(#)PaletteColorSlidersChooser.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.gui.plaf.palette.colorchooser;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.colorchooser.*;
import javax.swing.plaf.*;
import org.jhotdraw.gui.plaf.palette.PaletteLookAndFeel;
import org.jhotdraw.gui.plaf.palette.PalettePanelUI;

/**
 * The ColorSlidersChooser contains four individual color slider pages: gray slider, RGB sliders,
 * CMYK sliders, and HTML sliders.
 */
public class PaletteColorSlidersChooser extends AbstractColorChooserPanel implements UIResource {

  private static final long serialVersionUID = 1L;

  /**
   * We store here the name of the last selected color sliders panel. When the ColorSlidersChooser
   * is recreated multiple times in the same panel, the application 'remembers' which panel the user
   * had opened before.
   */
  private static int lastSelectedPanelIndex = 0;

  /** Creates new form. */
  public PaletteColorSlidersChooser() {}

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT
   * modify this code. The content of this method is always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    slidersComboBox = new javax.swing.JComboBox();
    slidersHolder = new javax.swing.JPanel();
    setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 6, 6, 6));
    setLayout(new java.awt.BorderLayout());
    add(slidersComboBox, java.awt.BorderLayout.NORTH);
    slidersHolder.setLayout(new java.awt.CardLayout());
    add(slidersHolder, java.awt.BorderLayout.CENTER);
  } // </editor-fold>//GEN-END:initComponents

  @Override
  protected void buildChooser() {
    initComponents();
    PaletteLookAndFeel laf = PaletteLookAndFeel.getInstance();
    setUI(PalettePanelUI.createUI(this));
    slidersHolder.setUI((PanelUI) PalettePanelUI.createUI(slidersHolder));
    slidersComboBox.setFont(laf.getFont("ColorChooser.font"));
    slidersHolder.add(new PaletteRGBChooser(), "" + laf.getString("ColorChooser.rgbSliders"));
    slidersHolder.add(new PaletteCMYKChooser(), "" + laf.getString("ColorChooser.cmykSliders"));
    slidersHolder.add(new PaletteHSBChooser(), "" + laf.getString("ColorChooser.hsbSliders"));
    DefaultComboBoxModel cbm = new DefaultComboBoxModel();
    cbm.addElement(laf.getString("ColorChooser.rgbSliders"));
    cbm.addElement(laf.getString("ColorChooser.cmykSliders"));
    cbm.addElement(laf.getString("ColorChooser.hsbSliders"));
    slidersComboBox.setModel(cbm);
    slidersComboBox.addItemListener(
        new ItemListener() {
          @Override
          public void itemStateChanged(ItemEvent evt) {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
              ((CardLayout) slidersHolder.getLayout()).show(slidersHolder, (String) evt.getItem());
              lastSelectedPanelIndex = slidersComboBox.getSelectedIndex();
            }
          }
        });
    slidersComboBox.setSelectedIndex(lastSelectedPanelIndex);
  }

  @Override
  public void installChooserPanel(JColorChooser enclosingChooser) {
    super.installChooserPanel(enclosingChooser);
    Component[] components = slidersHolder.getComponents();
    for (Component component : components) {
      AbstractColorChooserPanel ccp = (AbstractColorChooserPanel) component;
      ccp.installChooserPanel(enclosingChooser);
    }
  }

  /**
   * Invoked when the panel is removed from the chooser. If override this, be sure to call <code>
   * super</code>.
   */
  @Override
  public void uninstallChooserPanel(JColorChooser enclosingChooser) {
    Component[] components = slidersHolder.getComponents();
    for (Component component : components) {
      AbstractColorChooserPanel ccp = (AbstractColorChooserPanel) component;
      ccp.uninstallChooserPanel(enclosingChooser);
    }
    super.uninstallChooserPanel(enclosingChooser);
  }

  @Override
  public String getDisplayName() {
    PaletteLookAndFeel laf = PaletteLookAndFeel.getInstance();
    return laf.getString("ColorChooser.colorSliders");
  }

  @Override
  public Icon getLargeDisplayIcon() {
    PaletteLookAndFeel laf = PaletteLookAndFeel.getInstance();
    return laf.getIcon("ColorChooser.colorSlidersIcon");
  }

  @Override
  public Icon getSmallDisplayIcon() {
    return getLargeDisplayIcon();
  }

  @Override
  public void updateChooser() {}

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JComboBox slidersComboBox;
  private javax.swing.JPanel slidersHolder;
  // End of variables declaration//GEN-END:variables
}
