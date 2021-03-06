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

package BasisStatus;

public class FormElementInfo extends javax.swing.JDialog {

  /** Creates new form FrameGeneralSettings */
  public FormElementInfo(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();
    java.awt.event.ActionListener actionListener =
        new java.awt.event.ActionListener() {
          @Override
          public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
            dispose();
          }
        };

    javax.swing.KeyStroke stroke =
        javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0);
    rootPane.registerKeyboardAction(
        actionListener, stroke, javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW);
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT
   * modify this code. The content of this method is always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    buttonGroup1 = new javax.swing.ButtonGroup();
    jLabel1 = new javax.swing.JLabel();
    jTxtName = new javax.swing.JTextField();
    jcmdOK = new javax.swing.JButton();
    jTextField1 = new javax.swing.JTextField();
    jLabel7 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();
    jLabel5 = new javax.swing.JLabel();
    jLabel6 = new javax.swing.JLabel();
    jLabel8 = new javax.swing.JLabel();
    jLabel9 = new javax.swing.JLabel();
    jLabel10 = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    java.util.ResourceBundle bundle =
        java.util.ResourceBundle.getBundle("BasisStatus/FormElementInfo");
    setTitle(bundle.getString("Element Info"));

    jLabel1.setText(bundle.getString("Name:"));

    jTxtName.setEditable(false);
    jTxtName.setText("jTextField1");
    jTxtName.setBorder(
        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

    jcmdOK.setMnemonic('o');
    jcmdOK.setText(bundle.getString("OK"));
    jcmdOK.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jcmdOKActionPerformed(evt);
          }
        });

    jTextField1.setEditable(false);
    jTextField1.setText("jTextField1");
    jTextField1.setBorder(
        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
    jTextField1.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField1ActionPerformed(evt);
          }
        });

    jLabel7.setText(bundle.getString("Classpath"));

    jLabel2.setText(bundle.getString("Programmiert_von_:"));

    jLabel3.setText(bundle.getString("Copyrights"));

    jLabel4.setText(bundle.getString("Other_info"));

    jLabel5.setText("jLabel5");
    jLabel5.setBorder(
        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

    jLabel6.setText("jLabel6");
    jLabel6.setBorder(
        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

    jLabel8.setText("jLabel8");
    jLabel8.setBorder(
        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

    jLabel9.setText("jLabel9");
    jLabel9.setBorder(
        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

    jLabel10.setText("ID");

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                layout
                    .createSequentialGroup()
                    .add(
                        layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(
                                layout
                                    .createSequentialGroup()
                                    .add(
                                        layout
                                            .createParallelGroup(
                                                org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(
                                                layout
                                                    .createSequentialGroup()
                                                    .add(
                                                        layout
                                                            .createParallelGroup(
                                                                org.jdesktop.layout.GroupLayout
                                                                    .LEADING)
                                                            .add(
                                                                layout
                                                                    .createSequentialGroup()
                                                                    .add(10, 10, 10)
                                                                    .add(
                                                                        jLabel2,
                                                                        org.jdesktop.layout
                                                                            .GroupLayout
                                                                            .DEFAULT_SIZE,
                                                                        95,
                                                                        Short.MAX_VALUE))
                                                            .add(
                                                                layout
                                                                    .createSequentialGroup()
                                                                    .addContainerGap()
                                                                    .add(
                                                                        jLabel10,
                                                                        org.jdesktop.layout
                                                                            .GroupLayout
                                                                            .DEFAULT_SIZE,
                                                                        95,
                                                                        Short.MAX_VALUE))
                                                            .add(
                                                                layout
                                                                    .createSequentialGroup()
                                                                    .addContainerGap()
                                                                    .add(
                                                                        jLabel1,
                                                                        org.jdesktop.layout
                                                                            .GroupLayout
                                                                            .DEFAULT_SIZE,
                                                                        95,
                                                                        Short.MAX_VALUE))
                                                            .add(
                                                                layout
                                                                    .createSequentialGroup()
                                                                    .addContainerGap()
                                                                    .add(
                                                                        jLabel7,
                                                                        org.jdesktop.layout
                                                                            .GroupLayout
                                                                            .DEFAULT_SIZE,
                                                                        95,
                                                                        Short.MAX_VALUE)))
                                                    .add(10, 10, 10))
                                            .add(
                                                layout
                                                    .createSequentialGroup()
                                                    .addContainerGap()
                                                    .add(jLabel3)
                                                    .addPreferredGap(
                                                        org.jdesktop.layout.LayoutStyle.RELATED,
                                                        org.jdesktop.layout.GroupLayout
                                                            .DEFAULT_SIZE,
                                                        Short.MAX_VALUE))
                                            .add(
                                                layout
                                                    .createSequentialGroup()
                                                    .addContainerGap()
                                                    .add(
                                                        jLabel4,
                                                        org.jdesktop.layout.GroupLayout
                                                            .DEFAULT_SIZE,
                                                        99,
                                                        Short.MAX_VALUE)
                                                    .addPreferredGap(
                                                        org.jdesktop.layout.LayoutStyle.RELATED)))
                                    .add(
                                        layout
                                            .createParallelGroup(
                                                org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(
                                                org.jdesktop.layout.GroupLayout.TRAILING,
                                                jLabel6,
                                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                            .add(
                                                org.jdesktop.layout.GroupLayout.TRAILING,
                                                jTextField1)
                                            .add(jTxtName)
                                            .add(
                                                org.jdesktop.layout.GroupLayout.TRAILING,
                                                jLabel5,
                                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                            .add(
                                                org.jdesktop.layout.GroupLayout.TRAILING,
                                                layout
                                                    .createSequentialGroup()
                                                    .add(0, 0, Short.MAX_VALUE)
                                                    .add(
                                                        jLabel8,
                                                        org.jdesktop.layout.GroupLayout
                                                            .PREFERRED_SIZE,
                                                        268,
                                                        org.jdesktop.layout.GroupLayout
                                                            .PREFERRED_SIZE))
                                            .add(
                                                jLabel9,
                                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)))
                            .add(
                                org.jdesktop.layout.GroupLayout.TRAILING,
                                layout
                                    .createSequentialGroup()
                                    .add(0, 0, Short.MAX_VALUE)
                                    .add(
                                        jcmdOK,
                                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                        90,
                                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap()));
    layout.setVerticalGroup(
        layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                layout
                    .createSequentialGroup()
                    .add(20, 20, 20)
                    .add(
                        layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(
                                jTextField1,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel7))
                    .add(10, 10, 10)
                    .add(
                        layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(
                                jTxtName,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel1))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel9)
                            .add(jLabel10))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel2)
                            .add(
                                jLabel5,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                16,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(
                                jLabel6,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                14,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel3))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel4)
                            .add(
                                jLabel8,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                76,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(
                        org.jdesktop.layout.LayoutStyle.RELATED,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE)
                    .add(jcmdOK)
                    .addContainerGap()));

    pack();
    setLocationRelativeTo(null);
  }

  private void jTextField1ActionPerformed(
      java.awt.event.ActionEvent evt) { // GEN-FIRST:event_jTextField1ActionPerformed
    // TODO add your handling code here:
  } // GEN-LAST:event_jTextField1ActionPerformed

  private void jcmdOKActionPerformed(
      java.awt.event.ActionEvent evt) { // GEN-FIRST:event_jcmdOKActionPerformed

    dispose();
  } // GEN-LAST:event_jcmdOKActionPerformed

  public void init(
      String strName, String path, String programmer, String copyrights, String other, int ID) {
    jTxtName.setText(strName);
    jTextField1.setText(path);
    jLabel5.setText(programmer);
    jLabel6.setText(copyrights);
    jLabel8.setText(other);
    jLabel9.setText("" + ID);
  }

  public static boolean cancel = true;
  public static String strName;
  public static String strBeschriftung;
  public static boolean bolBeschrAnzeigen = false;
  public static String strDescription;
  public static int intLeft;
  public static int intTop;
  public static int intWidth;
  public static int intHeight;

  private javax.swing.ButtonGroup buttonGroup1;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel10;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JLabel jLabel9;
  private javax.swing.JTextField jTextField1;
  private javax.swing.JTextField jTxtName;
  private javax.swing.JButton jcmdOK;
}
