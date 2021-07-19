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

package tools;

import java.awt.*;
import javax.swing.*;

public class GatterProperties extends javax.swing.JDialog {

  public GatterProperties(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();
  }

  private void initComponents() {
    jPanel1 = new javax.swing.JPanel();
    jcmdOK = new javax.swing.JButton();
    jcmdCancel = new javax.swing.JButton();
    jPanel2 = new javax.swing.JPanel();
    jPanel3 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    jSpinner1 = new javax.swing.JSpinner();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("Eigenschaften");
    setResizable(false);
    jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

    jcmdOK.setMnemonic('O');
    jcmdOK.setText("OK");
    jcmdOK.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jcmdOKActionPerformed(evt);
          }
        });

    jPanel1.add(jcmdOK);

    jcmdCancel.setMnemonic('b');
    jcmdCancel.setText("Abbrechen");
    jcmdCancel.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jcmdCancelActionPerformed(evt);
          }
        });

    jPanel1.add(jcmdCancel);

    getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

    jPanel2.setLayout(new java.awt.BorderLayout());

    jPanel3.setLayout(null);

    jLabel1.setText("Eing\u00e4nge (2-10)");
    jLabel1.setAutoscrolls(true);
    jPanel3.add(jLabel1);
    jLabel1.setBounds(20, 50, 100, 14);

    jSpinner1.setModel(new SpinnerNumberModel(2.0, 2.0, 10.0, 1.0));
    jSpinner1.addPropertyChangeListener(
        new java.beans.PropertyChangeListener() {
          public void propertyChange(java.beans.PropertyChangeEvent evt) {
            jSpinner1PropertyChange(evt);
          }
        });
    jSpinner1.addChangeListener(
        new javax.swing.event.ChangeListener() {
          public void stateChanged(javax.swing.event.ChangeEvent evt) {
            jSpinner1StateChanged(evt);
          }
        });
    jSpinner1.addMouseListener(
        new java.awt.event.MouseAdapter() {
          public void mouseClicked(java.awt.event.MouseEvent evt) {
            jSpinner1MouseClicked(evt);
          }

          public void mousePressed(java.awt.event.MouseEvent evt) {
            jSpinner1MousePressed(evt);
          }
        });
    jSpinner1.addVetoableChangeListener(
        new java.beans.VetoableChangeListener() {
          public void vetoableChange(java.beans.PropertyChangeEvent evt)
              throws java.beans.PropertyVetoException {
            jSpinner1VetoableChange(evt);
          }
        });

    jPanel3.add(jSpinner1);
    jSpinner1.setBounds(130, 50, 90, 20);

    jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

    getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

    java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    setBounds((screenSize.width - 251) / 2, (screenSize.height - 152) / 2, 251, 152);
  }

  private void jcmdOKActionPerformed(java.awt.event.ActionEvent evt) {

    double val = ((Double) jSpinner1.getValue()).intValue();

    if (val >= 2.0 && val <= 10.0) {
      value = (int) val;
      dispose();
    }
  }

  private void jSpinner1MouseClicked(java.awt.event.MouseEvent evt) {}

  private void jSpinner1MousePressed(java.awt.event.MouseEvent evt) {
    // TODO add your handling code here:
  }

  private void jSpinner1StateChanged(javax.swing.event.ChangeEvent evt) {
    // TODO add your handling code here:
  }

  private void jSpinner1VetoableChange(java.beans.PropertyChangeEvent evt)
      throws java.beans.PropertyVetoException {}

  private void jSpinner1PropertyChange(java.beans.PropertyChangeEvent evt) {}

  private void jcmdCancelActionPerformed(java.awt.event.ActionEvent evt) {
    value = -1;
    dispose();
  }

  public static int showDialog(Frame frame) {
    GatterProperties frm = new GatterProperties(frame, true);
    frm.setVisible(true);

    return value;
  }

  private static int value;

  private javax.swing.JLabel jLabel1;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JSpinner jSpinner1;
  private javax.swing.JButton jcmdCancel;
  private javax.swing.JButton jcmdOK;
}
