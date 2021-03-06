/*
MyOpenLab by Carmelo Salafia www.myopenlab.de
Copyright (C) 2004 Carmelo Salafia cswi@gmx.de
Copyright (C) 2017  Javier Vel�squez javiervelasquez125@gmail.com
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

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

/** @author Salafia */
public class DialogWait extends javax.swing.JFrame {
  private Image image = null;

  private int counter = 0;
  private int maximum = 100;
  public static boolean stop = true;

  public DialogWait() {
    initComponents();

    // Creates new form DialogWait
    this.setAlwaysOnTop(
        true); // To avoid Myopenlab Freezes if user click main Frame while this window is
    // loading
    this.setAutoRequestFocus(false);
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    this.setLocation(
        dim.width / 2 - this.getSize().width / 2, (dim.height / 2 - this.getSize().height / 2));
  }

  public void setProgress() {
    //  jProgressBar1.setValue(counter++);
  }

  public String textX;

  public void setElementName(String text) {
    // ...
  }

  public void setMaximum(int max) {
    // ...
  }

  private void initComponents() {
    javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
    label1 = new javax.swing.JLabel();
    jProgressBar1 = new javax.swing.JProgressBar();
    label2 = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    setAlwaysOnTop(true);
    setBackground(new java.awt.Color(38, 117, 191));
    setMaximumSize(new java.awt.Dimension(500, 100));
    setUndecorated(true);
    setResizable(false);

    jPanel1.setBackground(new java.awt.Color(38, 117, 191));
    jPanel1.setForeground(new java.awt.Color(40, 40, 40));
    jPanel1.setMinimumSize(new java.awt.Dimension(400, 100));
    jPanel1.setPreferredSize(new java.awt.Dimension(400, 100));

    label1.setFont(new java.awt.Font("Dialog", 1, 14));
    label1.setForeground(new java.awt.Color(255, 255, 255));
    label1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/ajax-loader.gif")));
    label1.setText("Please wait");
    label1.setAlignmentX(0.5F);

    jProgressBar1.setFont(new java.awt.Font("Dialog", 1, 14));
    jProgressBar1.setForeground(new java.awt.Color(253, 153, 0));
    jProgressBar1.setPreferredSize(new java.awt.Dimension(380, 20));
    jProgressBar1.setStringPainted(true);

    label2.setForeground(new java.awt.Color(255, 255, 255));

    org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanel1Layout
                    .createSequentialGroup()
                    .add(
                        jPanel1Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(
                                jPanel1Layout
                                    .createSequentialGroup()
                                    .add(18, 18, 18)
                                    .add(
                                        label1,
                                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                        372,
                                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(
                                jPanel1Layout
                                    .createSequentialGroup()
                                    .addContainerGap()
                                    .add(
                                        jProgressBar1,
                                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .addPreferredGap(
                        org.jdesktop.layout.LayoutStyle.RELATED,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE)));
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanel1Layout
                    .createSequentialGroup()
                    .add(5, 5, 5)
                    .add(
                        label1,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        42,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(
                        org.jdesktop.layout.LayoutStyle.RELATED,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE)
                    .add(
                        jPanel1Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(
                                jPanel1Layout
                                    .createSequentialGroup()
                                    .add(
                                        jProgressBar1,
                                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                        35,
                                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(
                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE))
                            .add(jPanel1Layout.createSequentialGroup().add(41, 41, 41)))));

    getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

    pack();
    setLocationRelativeTo(null);
  }

  private void exitForm(java.awt.event.WindowEvent evt) {
    System.exit(0);
  }

  public javax.swing.JProgressBar jProgressBar1;
  public static javax.swing.JLabel label1;
  public javax.swing.JLabel label2;
}
