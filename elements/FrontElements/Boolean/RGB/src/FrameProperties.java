// *****************************************************************************
// * Element of MyOpenLab Library                                              *
// *                                                                           *
// * Copyright (C) 2004 Carmelo Salafia (cswi@gmx.de)                         *
// *                                                                           *
// * This library is free software; you can redistribute it and/or modify      *
// * it under the terms of the GNU Lesser General Public License as published  *
// * by the Free Software Foundation; either version 2.1 of the License,       *
// * or (at your option) any later version.                                    *
// * http://www.gnu.org/licenses/lgpl.html                                     *
// *                                                                           *
// * This library is distributed in the hope that it will be useful,           *
// * but WITHOUTANY WARRANTY; without even the implied warranty of             *
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.                      *
// * See the GNU Lesser General Public License for more details.               *
// *                                                                           *
// * You should have received a copy of the GNU Lesser General Public License  *
// * along with this library; if not, write to the Free Software Foundation,   *
// * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110, USA                  *
// *****************************************************************************

import java.util.*;

public class FrameProperties extends javax.swing.JDialog {

  public FrameProperties(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();
  }

  private void initComponents() {
    jLabel1 = new javax.swing.JLabel();
    jTextField1 = new javax.swing.JTextField();
    jLabel2 = new javax.swing.JLabel();
    jTextField2 = new javax.swing.JTextField();
    jLabel3 = new javax.swing.JLabel();
    jTextField3 = new javax.swing.JTextField();
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();

    getContentPane().setLayout(new java.awt.GridLayout(4, 2));

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    jLabel1.setText("Min");
    getContentPane().add(jLabel1);
    getContentPane().add(jTextField1);

    jLabel2.setText("Max");
    getContentPane().add(jLabel2);
    getContentPane().add(jTextField2);

    String strLocale = Locale.getDefault().toString();

    if (strLocale.equalsIgnoreCase("de_DE")) {
      jLabel3.setText("Abschnitte");
      jButton2.setText("Abbrechen");
    }

    if (strLocale.equalsIgnoreCase("en_US")) {
      jLabel3.setText("Sections");
      jButton2.setText("Abort");
    }

    if (strLocale.equalsIgnoreCase("es_ES")) {
      jLabel3.setText("Secciones");
      jButton2.setText("Abortar");
    }

    if (strLocale.equalsIgnoreCase("pt_BR")) {
      jLabel3.setText("Seções");
      jButton2.setText("Abortar");
    }

    getContentPane().add(jLabel3);
    getContentPane().add(jTextField3);

    jButton1.setMnemonic('O');
    jButton1.setText("OK");
    jButton1.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
          }
        });

    getContentPane().add(jButton1);

    jButton2.setMnemonic('b');
    jButton2.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
          }
        });

    getContentPane().add(jButton2);

    java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    setBounds((screenSize.width - 208) / 2, (screenSize.height - 134) / 2, 208, 134);
  }

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
    try {
      min = Double.parseDouble(jTextField1.getText());
      max = Double.parseDouble(jTextField2.getText());
      sections = Double.parseDouble(jTextField3.getText());
      result = true;
      this.dispose();
    } catch (NumberFormatException ex) {
      System.out.println(ex);
      result = false;
    }
  }

  private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
    result = false;
    this.dispose();
  }

  public static void execute(double min, double max, double sections) {
    FrameProperties frm = new FrameProperties(null, true);

    frm.jTextField1.setText("" + min);
    frm.jTextField2.setText("" + max);
    frm.jTextField3.setText("" + sections);

    frm.setVisible(true);
  }

  public static boolean result = false;
  public static double min = 0;
  public static double max = 100;
  public static double sections = 3;

  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JTextField jTextField1;
  private javax.swing.JTextField jTextField2;
  private javax.swing.JTextField jTextField3;
}
