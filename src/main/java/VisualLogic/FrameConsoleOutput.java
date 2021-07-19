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

public class FrameConsoleOutput extends javax.swing.JFrame {
  private Basis basis;

  /** Creates new form FrameConsoleOutput */
  public FrameConsoleOutput(Basis basis) {
    initComponents();
    this.basis = basis;

    javax.swing.ImageIcon icon =
        new javax.swing.ImageIcon(getClass().getResource("/Images/terminal16.png"));

    setIconImage(icon.getImage());
  }

  public void addMessageToConsole(String message) {
    jTextArea1.append(message);
    jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
  }

  private void initComponents() {
    jPopupMenu1 = new javax.swing.JPopupMenu();
    jmniClear = new javax.swing.JMenuItem();
    jScrollPane1 = new javax.swing.JScrollPane();
    jTextArea1 = new javax.swing.JTextArea();

    java.util.ResourceBundle bundle =
        java.util.ResourceBundle.getBundle("VisualLogic/FrameConsoleOutput");

    jmniClear.setText(bundle.getString("Clear"));
    jmniClear.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmniClearActionPerformed(evt);
          }
        });

    jPopupMenu1.add(jmniClear);

    setTitle(bundle.getString("Console_output"));
    setAlwaysOnTop(true);

    jTextArea1.setBackground(new java.awt.Color(0, 0, 0));
    jTextArea1.setColumns(20);
    jTextArea1.setComponentPopupMenu(jPopupMenu1);
    jTextArea1.setEditable(false);
    jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
    jTextArea1.setRows(5);
    jScrollPane1.setViewportView(jTextArea1);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                javax.swing.GroupLayout.Alignment.TRAILING,
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addComponent(
                        jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
                    .addContainerGap()));
    layout.setVerticalGroup(
        layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                javax.swing.GroupLayout.Alignment.TRAILING,
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addComponent(
                        jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                    .addContainerGap()));

    java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

    setBounds((screenSize.width - 640) / 2, (screenSize.height - 300) / 2, 640, 300);
  }

  private void jmniClearActionPerformed(java.awt.event.ActionEvent evt) {
    jTextArea1.setText("");
  }

  private javax.swing.JPopupMenu jPopupMenu1;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JTextArea jTextArea1;
  private javax.swing.JMenuItem jmniClear;
}
