/*
MyOpenLab by Carmelo Salafia www.myopenlab.de
Copyright (C) 2004 Carmelo Salafia cswi@gmx.de

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

package VisualLogic;

import MyParser.OpenVariable;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

/** @author Salafia */
public class DialogVariables extends javax.swing.JDialog {
  private DefaultListModel model = new DefaultListModel();
  private Basis basis;
  private ArrayList liste;

  /** Creates new form DialogVariables */
  public DialogVariables(java.awt.Frame parent, boolean modal, Basis basis) {
    super(parent, modal);
    this.basis = basis;
    initComponents();

    liste = basis.variablenListe;

    java.awt.event.ActionListener actionListener =
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
            dispose();
          }
        };

    javax.swing.KeyStroke stroke =
        javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0);
    rootPane.registerKeyboardAction(
        actionListener, stroke, javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW);

    jList1.setModel(model);
    listAll();
  }

  public void listAll() {
    model.clear();

    OpenVariable node;
    for (int i = 0; i < liste.size(); i++) {
      node = (OpenVariable) liste.get(i);
      model.addElement(node);
    }
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT
   * modify this code. The content of this method is always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jPanel1 = new javax.swing.JPanel();
    jScrollPane1 = new javax.swing.JScrollPane();
    jList1 = new javax.swing.JList();
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();
    jButton3 = new javax.swing.JButton();
    jButton4 = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    java.util.ResourceBundle bundle =
        java.util.ResourceBundle.getBundle("VisualLogic/DialogVariables");
    setTitle(bundle.getString("Title"));
    addWindowListener(
        new java.awt.event.WindowAdapter() {
          public void windowClosed(java.awt.event.WindowEvent evt) {
            formWindowClosed(evt);
          }
        });

    jButton1.setText(bundle.getString("Add"));
    jButton1.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
          }
        });

    jButton2.setText(bundle.getString("edit"));
    jButton2.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
          }
        });

    jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("variablen")));

    jList1.setModel(
        new javax.swing.AbstractListModel() {
          String[] strings = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};

          public int getSize() {
            return strings.length;
          }

          public Object getElementAt(int i) {
            return strings[i];
          }
        });
    jScrollPane1.setViewportView(jList1);

    org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanel1Layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(
                        jScrollPane1,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        184,
                        Short.MAX_VALUE)
                    .addContainerGap()));
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanel1Layout
                    .createSequentialGroup()
                    .add(
                        jScrollPane1,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        215,
                        Short.MAX_VALUE)
                    .addContainerGap()));

    jButton3.setText(bundle.getString("close"));
    jButton3.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton3ActionPerformed(evt);
          }
        });

    jButton4.setText(bundle.getString("loeschen"));
    jButton4.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton4ActionPerformed(evt);
          }
        });

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(
                        jPanel1,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(
                        layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(
                                layout
                                    .createSequentialGroup()
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(
                                        jButton3,
                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE))
                            .add(
                                layout
                                    .createSequentialGroup()
                                    .add(8, 8, 8)
                                    .add(
                                        layout
                                            .createParallelGroup(
                                                org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(
                                                org.jdesktop.layout.GroupLayout.TRAILING,
                                                jButton4,
                                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                            .add(
                                                org.jdesktop.layout.GroupLayout.TRAILING,
                                                jButton1,
                                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                            .add(
                                                org.jdesktop.layout.GroupLayout.TRAILING,
                                                jButton2,
                                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE))))
                    .addContainerGap()));
    layout.setVerticalGroup(
        layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(
                        layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(
                                jPanel1,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                            .add(
                                layout
                                    .createSequentialGroup()
                                    .add(jButton1)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(jButton2)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(jButton4)
                                    .addPreferredGap(
                                        org.jdesktop.layout.LayoutStyle.RELATED,
                                        148,
                                        Short.MAX_VALUE)
                                    .add(jButton3)))
                    .addContainerGap()));

    pack();
    setLocationRelativeTo(null);
  }

  private void formWindowClosed(java.awt.event.WindowEvent evt) // GEN-FIRST:event_formWindowClosed
      { // GEN-HEADEREND:event_formWindowClosed
    basis.getCircuitBasis().ProcessPinDataType();
  } // GEN-LAST:event_formWindowClosed

  private void jButton4ActionPerformed(
      java.awt.event.ActionEvent evt) // GEN-FIRST:event_jButton4ActionPerformed
      { // GEN-HEADEREND:event_jButton4ActionPerformed
    if (jList1.getSelectedValue() != null) {
      OpenVariable node = (OpenVariable) jList1.getSelectedValue();

      liste.remove(node);
      listAll();
    }
  } // GEN-LAST:event_jButton4ActionPerformed

  private void jButton2ActionPerformed(
      java.awt.event.ActionEvent evt) // GEN-FIRST:event_jButton2ActionPerformed
      { // GEN-HEADEREND:event_jButton2ActionPerformed
    if (jList1.getSelectedValue() != null) {
      OpenVariable node = (OpenVariable) jList1.getSelectedValue();

      DialogAddEditvariable.executeEdit(basis.getFrameMain(), node);
      if (DialogAddEditvariable.result) {
        node.name = DialogAddEditvariable.varName;
        node.datatype = DialogAddEditvariable.datatype;
        node.size1 = DialogAddEditvariable.arr_size1;
        node.size2 = DialogAddEditvariable.arr_size2;
        listAll();
      }
    }
  } // GEN-LAST:event_jButton2ActionPerformed

  private void jButton1ActionPerformed(
      java.awt.event.ActionEvent evt) // GEN-FIRST:event_jButton1ActionPerformed
      { // GEN-HEADEREND:event_jButton1ActionPerformed
    DialogAddEditvariable.executeAdd(basis.getFrameMain());

    if (DialogAddEditvariable.result) {
      if (basis.varNameExist(DialogAddEditvariable.varName) == false) {
        OpenVariable newNode = new OpenVariable();
        newNode.name = DialogAddEditvariable.varName;
        newNode.datatype = DialogAddEditvariable.datatype;
        newNode.size1 = DialogAddEditvariable.arr_size1;
        newNode.size2 = DialogAddEditvariable.arr_size2;
        liste.add(newNode);
        listAll();
      }
    }
  } // GEN-LAST:event_jButton1ActionPerformed

  private void jButton3ActionPerformed(
      java.awt.event.ActionEvent evt) // GEN-FIRST:event_jButton3ActionPerformed
      { // GEN-HEADEREND:event_jButton3ActionPerformed
    dispose();
  } // GEN-LAST:event_jButton3ActionPerformed

  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JButton jButton3;
  private javax.swing.JButton jButton4;
  private javax.swing.JList jList1;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JScrollPane jScrollPane1;
}
