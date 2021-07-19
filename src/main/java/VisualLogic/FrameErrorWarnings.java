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

import java.io.IOException;
import java.io.OutputStream;

class TextAreaStream extends OutputStream {
  private FrameErrorWarnings frm;
  private StringBuffer buffer = new StringBuffer(1024);

  public TextAreaStream(FrameErrorWarnings frm) {
    this.frm = frm;
  }

  public void write(int b) throws IOException {
    if (!frm.isVisible()) frm.setVisible(true);

    buffer.append((char) b);
    if (b == '\n') {
      frm.jTextArea1.append(buffer.toString());
      buffer.setLength(0);
    }
  }
}

public class FrameErrorWarnings extends javax.swing.JFrame {

  public FrameErrorWarnings() {
    initComponents();

    javax.swing.ImageIcon icon =
        new javax.swing.ImageIcon(getClass().getResource("/Images/warning16.png"));

    setIconImage(icon.getImage());
  }

  private void initComponents() {

    jPopupMenu1 = new javax.swing.JPopupMenu();
    jmniClear = new javax.swing.JMenuItem();
    jScrollPane1 = new javax.swing.JScrollPane();
    jTextArea1 = new javax.swing.JTextArea();

    java.util.ResourceBundle bundle =
        java.util.ResourceBundle.getBundle("VisualLogic/FrameErrorWarnings");
    jmniClear.setText(bundle.getString("CLEAR"));
    jmniClear.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmniClearActionPerformed(evt);
          }
        });

    jPopupMenu1.add(jmniClear);

    setTitle(bundle.getString("ERRORS_AND_WARNINGS"));

    jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

    jTextArea1.setColumns(20);
    jTextArea1.setRows(5);
    jTextArea1.setComponentPopupMenu(jPopupMenu1);
    jScrollPane1.setViewportView(jTextArea1);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(
                jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE));
    layout.setVerticalGroup(
        layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(
                jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE));

    setSize(new java.awt.Dimension(800, 400));

    setLocationRelativeTo(null);
  }

  private void jmniClearActionPerformed(java.awt.event.ActionEvent evt) {
    jTextArea1.setText("");
  }

  public void addMessageToConsole(String message) {
    jTextArea1.append(message);
    jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
  }

  public void clearMessages() {
    jTextArea1.setText("");
  }

  public String getMessages() {
    return jTextArea1.getText();
  }

  private javax.swing.JPopupMenu jPopupMenu1;
  private javax.swing.JScrollPane jScrollPane1;
  public javax.swing.JTextArea jTextArea1;
  private javax.swing.JMenuItem jmniClear;
}
