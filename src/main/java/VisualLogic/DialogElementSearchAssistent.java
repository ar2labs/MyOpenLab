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

import java.awt.Cursor;
import java.io.File;
import javax.swing.DefaultListModel;

public class DialogElementSearchAssistent extends javax.swing.JDialog {

  private DefaultListModel model = new DefaultListModel();

  /** Creates new form DialogElementSearchAssistent */
  public DialogElementSearchAssistent(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();

    jList1.setModel(model);

    Tools.dialogWait.dispose();
  }

  public String searchElementRekursive(String path, String strName) {
    File file = new File(path);

    File[] files = file.listFiles();

    for (int i = 0; i < files.length; i++) {
      File f = files[i];
      if (f.isDirectory()) {
        String nm = f.getName();
        if (nm.equalsIgnoreCase(strName)) {

          model.addElement(f.getAbsolutePath());
          // return f.getAbsolutePath();

        } else {
          String res = searchElementRekursive(f.getAbsolutePath(), strName);

          // if (res.length()>0) return res;
        }
      }
    }

    return "";
  }

  public String str;
  public String elementPath;

  public DialogElementSearchAssistent frame;

  class MyThread extends Thread {

    public void run() {
      frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));

      searchElementRekursive(elementPath + "/CircuitElements", str);
      searchElementRekursive(elementPath + "/FrontElements", str);
      searchElementRekursive(Tools.userElementPath + "/CircuitElements", str);
      searchElementRekursive(Tools.userElementPath + "/FrontElements", str);

      frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
  }

  public void init(File file) {

    /*DataOutputStream dos;

    dos.wr*/

    str = file.getName();
    elementPath = Tools.elementPath;

    frame = this;

    jTextField1.setText(file.getAbsolutePath());
    jTextField2.setText(str);

    MyThread xxx = new MyThread();

    xxx.start();

    setVisible(true);
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT
   * modify this code. The content of this method is always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
  private void initComponents() {
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();
    jScrollPane1 = new javax.swing.JScrollPane();
    jList1 = new javax.swing.JList();
    jLabel1 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    jTextField1 = new javax.swing.JTextField();
    jTextField2 = new javax.swing.JTextField();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("Search Element");
    jButton1.setText("cancel");
    jButton1.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
          }
        });

    jButton2.setText("OK");
    jButton2.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
          }
        });

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

    jLabel1.setText("Element : ");

    jLabel3.setText("Path not found : ");

    jTextField1.setEditable(false);

    jTextField2.setEditable(false);

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
                    .addGroup(
                        layout
                            .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(
                                jScrollPane1,
                                javax.swing.GroupLayout.Alignment.LEADING,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                562,
                                Short.MAX_VALUE)
                            .addGroup(
                                layout
                                    .createSequentialGroup()
                                    .addComponent(jButton2)
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton1))
                            .addGroup(
                                javax.swing.GroupLayout.Alignment.LEADING,
                                layout
                                    .createSequentialGroup()
                                    .addGroup(
                                        layout
                                            .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel1))
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(
                                        layout
                                            .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(
                                                jTextField2,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                476,
                                                Short.MAX_VALUE)
                                            .addComponent(
                                                jTextField1,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                476,
                                                Short.MAX_VALUE))))
                    .addContainerGap()));
    layout.setVerticalGroup(
        layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                javax.swing.GroupLayout.Alignment.TRAILING,
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addGroup(
                        layout
                            .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(
                                jTextField1,
                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(
                        layout
                            .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(
                                jTextField2,
                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(
                        jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(
                        layout
                            .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2))
                    .addContainerGap()));
    java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    setBounds((screenSize.width - 590) / 2, (screenSize.height - 332) / 2, 590, 332);
  }

  private void jButton2ActionPerformed(
      java.awt.event.ActionEvent evt) // GEN-FIRST:event_jButton2ActionPerformed
      { // GEN-HEADEREND:event_jButton2ActionPerformed
    if (jList1.getSelectedValue() != null) {
      result = (String) jList1.getSelectedValue();
      dispose();
    }
  } // GEN-LAST:event_jButton2ActionPerformed

  private void jButton1ActionPerformed(
      java.awt.event.ActionEvent evt) // GEN-FIRST:event_jButton1ActionPerformed
      { // GEN-HEADEREND:event_jButton1ActionPerformed
    result = "";
    dispose();
  } // GEN-LAST:event_jButton1ActionPerformed

  public static String result = "";

  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JList jList1;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JTextField jTextField1;
  private javax.swing.JTextField jTextField2;
}
