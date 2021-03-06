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

public class DialogLanguage extends javax.swing.JDialog {

  /** Creates new form DialogLanguage */
  public DialogLanguage(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();

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

    java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT
   * modify this code. The content of this method is always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jComboBox1 = new javax.swing.JComboBox();
    jButton1 = new javax.swing.JButton();
    jLabel1 = new javax.swing.JLabel();
    jButton2 = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("Choice Language");
    setResizable(false);

    jComboBox1.setModel(
        new javax.swing.DefaultComboBoxModel(
            new String[] {"Deutsch", "English", "Espanol", "Portuguese"}));

    jButton1.setText("OK");
    jButton1.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
          }
        });

    jLabel1.setText("Language");

    java.util.ResourceBundle bundle =
        java.util.ResourceBundle.getBundle("VisualLogic/DialogLanguage");
    jButton2.setText(bundle.getString("cancel"));
    jButton2.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
          }
        });

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                org.jdesktop.layout.GroupLayout.TRAILING,
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(
                        jLabel1,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        74,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(
                                org.jdesktop.layout.GroupLayout.TRAILING,
                                layout
                                    .createSequentialGroup()
                                    .add(
                                        jButton1,
                                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                        70,
                                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(jButton2))
                            .add(jComboBox1, 0, 175, Short.MAX_VALUE))
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
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel1)
                            .add(
                                jComboBox1,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(
                        org.jdesktop.layout.LayoutStyle.RELATED,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE)
                    .add(
                        layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jButton2)
                            .add(jButton1))
                    .addContainerGap()));

    pack();
  }

  private void jButton2ActionPerformed(
      java.awt.event.ActionEvent evt) // GEN-FIRST:event_jButton2ActionPerformed
      { // GEN-HEADEREND:event_jButton2ActionPerformed
    dispose();
  } // GEN-LAST:event_jButton2ActionPerformed

  public void execute(String language) {

    if (language.equalsIgnoreCase("de_DE")) {
      jComboBox1.setSelectedIndex(0);
    }

    if (language.equalsIgnoreCase("en_US")) {
      jComboBox1.setSelectedIndex(1);
    }

    if (language.equalsIgnoreCase("es_ES")) {
      jComboBox1.setSelectedIndex(2);
    }

    if (language.equalsIgnoreCase("pt_BR")) {
      jComboBox1.setSelectedIndex(3);
    }

    result = language;

    setVisible(true);
  }

  private void jButton1ActionPerformed(
      java.awt.event.ActionEvent evt) { // GEN-FIRST:event_jButton1ActionPerformed
    int index = jComboBox1.getSelectedIndex();

    switch (index) {
      case 0:
        result = "de_DE";
        break;
      case 1:
        result = "en_US";
        break;
      case 2:
        result = "es_ES";
        break;
      case 3:
        result = "pt_BR";
        break;
    }

    dispose();
  } // GEN-LAST:event_jButton1ActionPerformed

  public static String result = "en_US";

  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JComboBox jComboBox1;
  private javax.swing.JLabel jLabel1;
}
