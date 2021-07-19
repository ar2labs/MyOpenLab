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

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class VMProperties extends javax.swing.JDialog {

  /** Creates new form FrameSpeichernAlsElement */
  public VMProperties(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();

    ActionListener actionListener =
        new ActionListener() {
          public void actionPerformed(ActionEvent actionEvent) {
            cancel = true;
            dispose();
          }
        };

    KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
    rootPane.registerKeyboardAction(actionListener, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT
   * modify this code. The content of this method is always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
  private void initComponents() {
    jButton2 = new javax.swing.JButton();
    jButton1 = new javax.swing.JButton();
    jPanel1 = new javax.swing.JPanel();
    jLabel5 = new javax.swing.JLabel();
    jLabel6 = new javax.swing.JLabel();
    jLabel7 = new javax.swing.JLabel();
    jTxtAutorName = new javax.swing.JTextField();
    jTxtAutorEmail = new javax.swing.JTextField();
    jTxtAutorWWW = new javax.swing.JTextField();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("");
    setResizable(false);
    addKeyListener(
        new java.awt.event.KeyAdapter() {
          public void keyReleased(java.awt.event.KeyEvent evt) {
            formKeyReleased(evt);
          }
        });

    jButton2.setText(
        java.util.ResourceBundle.getBundle("VisualLogic/VMProperties").getString("Abbrechen"));
    jButton2.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
          }
        });

    jButton1.setText(
        java.util.ResourceBundle.getBundle("VisualLogic/VMProperties").getString("OK"));
    jButton1.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
          }
        });

    jPanel1.setLayout(null);

    jPanel1.setBorder(
        javax.swing.BorderFactory.createTitledBorder(
            java.util.ResourceBundle.getBundle("VisualLogic/VMProperties").getString("Autor")));
    jLabel5.setText(
        java.util.ResourceBundle.getBundle("VisualLogic/VMProperties").getString("Name"));
    jPanel1.add(jLabel5);
    jLabel5.setBounds(10, 30, 60, 14);

    jLabel6.setText(
        java.util.ResourceBundle.getBundle("VisualLogic/VMProperties").getString("eMail"));
    jPanel1.add(jLabel6);
    jLabel6.setBounds(10, 60, 60, 14);

    jLabel7.setText(
        java.util.ResourceBundle.getBundle("VisualLogic/VMProperties").getString("www"));
    jPanel1.add(jLabel7);
    jLabel7.setBounds(10, 90, 60, 14);

    jPanel1.add(jTxtAutorName);
    jTxtAutorName.setBounds(60, 30, 250, 20);

    jPanel1.add(jTxtAutorEmail);
    jTxtAutorEmail.setBounds(60, 60, 250, 20);

    jPanel1.add(jTxtAutorWWW);
    jTxtAutorWWW.setBounds(60, 90, 250, 20);

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                layout
                    .createSequentialGroup()
                    .add(10, 10, 10)
                    .add(
                        layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(
                                layout
                                    .createSequentialGroup()
                                    .add(
                                        jButton1,
                                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                        76,
                                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(
                                        jButton2,
                                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                        100,
                                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(
                                jPanel1,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                320,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))));
    layout.setVerticalGroup(
        layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                layout
                    .createSequentialGroup()
                    .add(10, 10, 10)
                    .add(
                        jPanel1,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        130,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(
                                jButton2,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                20,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(
                                jButton1,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                20,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    setBounds((screenSize.width - 352) / 2, (screenSize.height - 204) / 2, 352, 204);
  }

  private void formKeyReleased(java.awt.event.KeyEvent evt) { // GEN-FIRST:event_formKeyReleased
  } // GEN-LAST:event_formKeyReleased

  private void jButton2ActionPerformed(
      java.awt.event.ActionEvent evt) { // GEN-FIRST:event_jButton2ActionPerformed
    cancel = true;
    dispose();
  } // GEN-LAST:event_jButton2ActionPerformed

  private void jButton1ActionPerformed(
      java.awt.event.ActionEvent evt) { // GEN-FIRST:event_jButton1ActionPerformed
    cancel = false;
    strAutorName = jTxtAutorName.getText();
    strAutorEmail = jTxtAutorEmail.getText();
    strAutorWWW = jTxtAutorWWW.getText();

    dispose();
  } // GEN-LAST:event_jButton1ActionPerformed

  public static boolean showDialog(Frame parent, String title) {
    VMProperties frm = new VMProperties(parent, true);

    frm.jTxtAutorName.setText("" + strAutorName);
    frm.jTxtAutorEmail.setText("" + strAutorEmail);
    frm.jTxtAutorWWW.setText("" + strAutorWWW);
    frm.setTitle(title);
    frm.setVisible(true);
    return !cancel;
  }

  private static boolean cancel;

  public static String strName = "";
  public static String strAutorName = "";
  public static String strAutorEmail = "";
  public static String strAutorWWW = "";
  public static int intElementWidth = 50;
  public static int intElementHeight = 50;
  public static int intVMWidth = 1000;
  public static int intVMHeight = 1000;

  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JTextField jTxtAutorEmail;
  private javax.swing.JTextField jTxtAutorName;
  private javax.swing.JTextField jTxtAutorWWW;
}