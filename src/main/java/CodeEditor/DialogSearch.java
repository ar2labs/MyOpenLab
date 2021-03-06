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

package CodeEditor;

import VisualLogic.CommandIF;

public class DialogSearch extends javax.swing.JDialog {

  private CommandIF owner;

  /** Creates new form DialogSearch */
  public DialogSearch(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();

    javax.swing.KeyStroke stroke =
        javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0);
    rootPane.registerKeyboardAction(
        actionListener, stroke, javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW);
  }

  private void initComponents() {
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();
    jLabel1 = new javax.swing.JLabel();
    jTextField1 = new javax.swing.JTextField();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle(java.util.ResourceBundle.getBundle("CodeEditor/DialogSearch").getString("Search"));
    jButton1.setText(
        java.util.ResourceBundle.getBundle("CodeEditor/DialogSearch").getString("Cancel"));
    jButton1.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
          }
        });

    jButton2.setText(
        java.util.ResourceBundle.getBundle("CodeEditor/DialogSearch").getString("Search"));
    jButton2.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
          }
        });

    jLabel1.setText(
        java.util.ResourceBundle.getBundle("CodeEditor/DialogSearch")
            .getString("Search_for_Text_Containing_:"));

    jTextField1.addKeyListener(
        new java.awt.event.KeyAdapter() {
          public void keyPressed(java.awt.event.KeyEvent evt) {
            jTextField1KeyPressed(evt);
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
                        layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(
                                org.jdesktop.layout.GroupLayout.TRAILING,
                                layout
                                    .createSequentialGroup()
                                    .add(jButton2)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(jButton1))
                            .add(jLabel1)
                            .add(
                                jTextField1,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                268,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap()));
    layout.setVerticalGroup(
        layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                org.jdesktop.layout.GroupLayout.TRAILING,
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(jLabel1)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        jTextField1,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 24, Short.MAX_VALUE)
                    .add(
                        layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jButton1)
                            .add(jButton2))
                    .addContainerGap()));
    java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    setBounds((screenSize.width - 296) / 2, (screenSize.height - 136) / 2, 296, 136);
  }

  java.awt.event.ActionListener actionListener =
      new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
          dispose();
        }
      };

  private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {
    if (evt.getKeyCode() == evt.VK_ENTER) {
      jButton2ActionPerformed(null);
    }
  }

  private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
    owner.sendCommand("TEXT", jTextField1.getText());
  }

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
    dispose();
  }

  public void init(CommandIF owner, String text) {
    this.owner = owner;
    jTextField1.setText(text);
  }

  public static String result = "";

  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JTextField jTextField1;
}
