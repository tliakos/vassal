/*
 *
 * Copyright (c) 2000-2003 by Rodney Kinney
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License (LGPL) as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, copies are available
 * at http://www.opensource.org.
 */
package VASSAL.configure;

import VASSAL.i18n.Resources;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A Window for displaying a {@link Configurer}.  The title of the window
 * changes with the name of the Configurer
 */
public class ConfigurerWindow extends JDialog {
  private static final long serialVersionUID = 1L;

  protected JButton okButton = new JButton(Resources.getString("General.ok"));
  protected JButton canButton = new JButton(Resources.getString("General.cancel"));
  protected boolean cancelled;

  public ConfigurerWindow(Configurer c) {
    this(c, true);
  }

  public ConfigurerWindow(final Configurer c, boolean modal) {
    super((JFrame) null, modal);

    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent we) {
        dispose();
        cancelled = true;
      }
    });

    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    add(c.getControls());
    c.addPropertyChangeListener(evt -> {
      if (Configurer.NAME_PROPERTY
        .equals(evt.getPropertyName())) {
        setTitle((String) evt.getNewValue());
      }
    });
    setTitle(c.getName());

    okButton.addActionListener(e -> {
      c.getValue();
      dispose();
      cancelled = false;
    });

    canButton.addActionListener(e -> {
      dispose();
      cancelled = true;
    });

    final JPanel buttonPanel = new JPanel();
    buttonPanel.add(okButton);
    buttonPanel.add(canButton);
    add(buttonPanel);
    cancelled = false;

    pack();
  }

  public boolean isCancelled() {
    return cancelled;
  }
}






