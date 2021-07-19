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

package SimulatorSocket;

public class FrameMain extends javax.swing.JFrame implements MyOpenLabOwnerIF {

  private Server server;

  public FrameMain() {
    initComponents();

    server = new Server(this);
    server.start();
  }

  private void initComponents() {

    jButton1 = new javax.swing.JButton();
    jTextField1 = new javax.swing.JTextField();
    jScrollPane1 = new javax.swing.JScrollPane();
    jTextArea1 = new javax.swing.JTextArea();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    addWindowListener(
        new java.awt.event.WindowAdapter() {
          public void windowClosed(java.awt.event.WindowEvent evt) {
            formWindowClosed(evt);
          }
        });

    jButton1.setText("jButton1");
    jButton1.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
          }
        });

    jTextField1.setText("jTextField1");

    jTextArea1.setColumns(20);
    jTextArea1.setRows(5);
    jScrollPane1.setViewportView(jTextArea1);

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
                            .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(
                                javax.swing.GroupLayout.Alignment.LEADING,
                                layout
                                    .createSequentialGroup()
                                    .addComponent(
                                        jTextField1,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        184,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton1)))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
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
                            .addComponent(jButton1))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(
                        jScrollPane1,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        169,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

    pack();
  }

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
    server.sendCmd(jTextField1.getText());
  }

  private void formWindowClosed(java.awt.event.WindowEvent evt) {
    if (server != null) server.close();
  }

  public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(
        new Runnable() {
          public void run() {
            new FrameMain().setVisible(true);
          }
        });
  }

  public void ownerMessage(String message) {
    jTextArea1.append(message);
  }

  private javax.swing.JButton jButton1;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JTextArea jTextArea1;
  private javax.swing.JTextField jTextField1;
}
