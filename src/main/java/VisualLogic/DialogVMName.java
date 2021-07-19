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

public class DialogVMName extends javax.swing.JDialog {

  /** Creates new form DialogRename */
  public DialogVMName(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();

    java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
  }

  private void initComponents() {
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();
    jTextField1 = new javax.swing.JTextField();
    jLabel2 = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    java.util.ResourceBundle bundle =
        java.util.ResourceBundle.getBundle("VisualLogic/DialogVMName");
    setTitle(bundle.getString("VM_Name"));
    setResizable(false);

    jButton1.setText(bundle.getString("OK"));
    jButton1.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
          }
        });

    jButton2.setText(bundle.getString("Cancel"));
    jButton2.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
          }
        });

    jLabel2.setText(bundle.getString("Vm_Name_:"));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addGroup(
                        layout
                            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(
                                layout
                                    .createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(15, 15, 15)
                                    .addComponent(
                                        jTextField1,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        212,
                                        Short.MAX_VALUE))
                            .addGroup(
                                javax.swing.GroupLayout.Alignment.TRAILING,
                                layout
                                    .createSequentialGroup()
                                    .addComponent(jButton1)
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton2)))
                    .addContainerGap()));
    layout.setVerticalGroup(
        layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addGroup(
                        layout
                            .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(
                                jTextField1,
                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(
                        layout
                            .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton1))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

    pack();
  }

  private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
    result = false;
    dispose();
  }

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
    newName = jTextField1.getText();
    result = true;
    dispose();
  }

  public static boolean result = false;
  public static String newName = "";

  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JTextField jTextField1;
}
