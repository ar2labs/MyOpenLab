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

import VisualLogic.DFProperties;
import VisualLogic.MyImage;
import VisualLogic.Tools;
import java.awt.BorderLayout;
import java.io.File;
import javax.swing.ImageIcon;

public class FormDefinitonDefEditor extends javax.swing.JDialog {
  public MyImage image = new MyImage();

  String elementDir = "";

  /** Creates new form FormDefinitonDefEditor */
  public FormDefinitonDefEditor(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();
    jPanel5.add(image, BorderLayout.CENTER);

    java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);

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
  }

  private void initComponents() {
    jPanel1 = new javax.swing.JPanel();
    jPanel5 = new javax.swing.JPanel();
    txtIcon = new javax.swing.JTextField();
    jLabel1 = new javax.swing.JLabel();
    jButton3 = new javax.swing.JButton();
    jButton4 = new javax.swing.JButton();
    jPanel2 = new javax.swing.JPanel();
    jLabel2 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();
    jLabel7 = new javax.swing.JLabel();
    txtDE = new javax.swing.JTextField();
    txtEN = new javax.swing.JTextField();
    txtES = new javax.swing.JTextField();
    txtPT = new javax.swing.JTextField();
    jPanel4 = new javax.swing.JPanel();
    jLabel5 = new javax.swing.JLabel();
    jLabel6 = new javax.swing.JLabel();
    txtCircuitClass = new javax.swing.JTextField();
    txtPanelClass = new javax.swing.JTextField();
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    java.util.ResourceBundle bundle =
        java.util.ResourceBundle.getBundle("CodeEditor/FormDefinitonDefEditor");

    setTitle(bundle.getString("Element_Properties"));
    setResizable(false);

    jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Icon"));

    jPanel5.setBackground(new java.awt.Color(255, 255, 255));
    jPanel5.setBorder(
        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
    jPanel5.setLayout(new java.awt.BorderLayout());

    jLabel1.setText(bundle.getString("Path_is_relitative_to_elementpath"));

    jButton3.setText(bundle.getString("set"));
    jButton3.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton3ActionPerformed(evt);
          }
        });

    jButton4.setText(bundle.getString("edit_icon"));
    jButton4.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton4ActionPerformed(evt);
          }
        });

    org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);

    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanel1Layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(
                        jPanel5,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        51,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        jPanel1Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(
                                jPanel1Layout
                                    .createSequentialGroup()
                                    .add(
                                        jPanel1Layout
                                            .createParallelGroup(
                                                org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(jLabel1)
                                            .add(jButton4))
                                    .addPreferredGap(
                                        org.jdesktop.layout.LayoutStyle.RELATED,
                                        49,
                                        Short.MAX_VALUE)
                                    .add(jButton3))
                            .add(
                                txtIcon,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                251,
                                Short.MAX_VALUE))
                    .addContainerGap()));
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanel1Layout
                    .createSequentialGroup()
                    .add(
                        jPanel1Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(
                                org.jdesktop.layout.GroupLayout.TRAILING,
                                jPanel1Layout
                                    .createSequentialGroup()
                                    .addContainerGap(46, Short.MAX_VALUE)
                                    .add(jButton4))
                            .add(
                                jPanel1Layout
                                    .createSequentialGroup()
                                    .add(
                                        jPanel1Layout
                                            .createParallelGroup(
                                                org.jdesktop.layout.GroupLayout.TRAILING, false)
                                            .add(
                                                org.jdesktop.layout.GroupLayout.LEADING,
                                                jPanel5,
                                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                            .add(
                                                jPanel1Layout
                                                    .createSequentialGroup()
                                                    .add(
                                                        txtIcon,
                                                        org.jdesktop.layout.GroupLayout
                                                            .PREFERRED_SIZE,
                                                        org.jdesktop.layout.GroupLayout
                                                            .DEFAULT_SIZE,
                                                        org.jdesktop.layout.GroupLayout
                                                            .PREFERRED_SIZE)
                                                    .addPreferredGap(
                                                        org.jdesktop.layout.LayoutStyle.RELATED)
                                                    .add(
                                                        jPanel1Layout
                                                            .createParallelGroup(
                                                                org.jdesktop.layout.GroupLayout
                                                                    .BASELINE)
                                                            .add(jButton3)
                                                            .add(jLabel1))))
                                    .add(0, 20, Short.MAX_VALUE)))
                    .addContainerGap()));

    jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("CAPTION")));

    jLabel2.setText(bundle.getString("German"));

    jLabel3.setText(bundle.getString("English"));

    jLabel4.setText(bundle.getString("Spanish"));

    jLabel7.setText(bundle.getString("Portuguese"));

    txtEN.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtENActionPerformed(evt);
          }
        });

    org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanel2Layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(
                        jPanel2Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel7)
                            .add(jLabel4)
                            .add(jLabel2)
                            .add(jLabel3))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        jPanel2Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(
                                org.jdesktop.layout.GroupLayout.TRAILING,
                                txtDE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                257,
                                Short.MAX_VALUE)
                            .add(
                                org.jdesktop.layout.GroupLayout.TRAILING,
                                txtEN,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                257,
                                Short.MAX_VALUE)
                            .add(
                                txtES,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                257,
                                Short.MAX_VALUE)
                            .add(
                                txtPT,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                257,
                                Short.MAX_VALUE))
                    .addContainerGap()));
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanel2Layout
                    .createSequentialGroup()
                    .add(
                        jPanel2Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel2)
                            .add(
                                txtDE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        jPanel2Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel3)
                            .add(
                                txtEN,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        jPanel2Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel4)
                            .add(
                                txtES,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        jPanel2Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel7)
                            .add(
                                txtPT,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

    jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("CLASS")));

    jLabel5.setText(bundle.getString("Circuit-Element_Class"));

    jLabel6.setText(bundle.getString("Panel-Element_Class"));

    org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
    jPanel4.setLayout(jPanel4Layout);
    jPanel4Layout.setHorizontalGroup(
        jPanel4Layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanel4Layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(
                        jPanel4Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel5)
                            .add(jLabel6))
                    .add(10, 10, 10)
                    .add(
                        jPanel4Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(
                                txtCircuitClass,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                188,
                                Short.MAX_VALUE)
                            .add(
                                txtPanelClass,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                188,
                                Short.MAX_VALUE))
                    .addContainerGap()));
    jPanel4Layout.setVerticalGroup(
        jPanel4Layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanel4Layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(
                        jPanel4Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel5)
                            .add(
                                txtCircuitClass,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        jPanel4Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel6)
                            .add(
                                txtPanelClass,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

    jButton1.setText(bundle.getString("Cancel"));
    jButton1.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
          }
        });

    jButton2.setText(bundle.getString("OK"));
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
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(
                        layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(
                                jPanel1,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                            .add(
                                org.jdesktop.layout.GroupLayout.TRAILING,
                                layout
                                    .createSequentialGroup()
                                    .add(
                                        jPanel4,
                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)
                                    .add(10, 10, 10))
                            .add(
                                org.jdesktop.layout.GroupLayout.TRAILING,
                                layout
                                    .createSequentialGroup()
                                    .add(
                                        jPanel2,
                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)
                                    .addContainerGap())
                            .add(
                                org.jdesktop.layout.GroupLayout.TRAILING,
                                layout
                                    .createSequentialGroup()
                                    .add(jButton2)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(jButton1)
                                    .addContainerGap()))));
    layout.setVerticalGroup(
        layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(
                        jPanel1,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        jPanel2,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        jPanel4,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jButton1)
                            .add(jButton2))
                    .addContainerGap(
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

    pack();
  }

  private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
    File file = new File(elementDir + "/" + txtIcon.getText());
    Tools.openPaint(file);
  }

  private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
    ImageIcon icon = new ImageIcon(elementDir + "/" + txtIcon.getText());
    image.setImage(icon.getImage());
  }

  private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
    DFProperties definition_def = new DFProperties();

    definition_def.iconFilename = txtIcon.getText();
    definition_def.captionDE = txtDE.getText();
    definition_def.captionEN = txtEN.getText();
    definition_def.captionES = txtES.getText();
    definition_def.captionPT = txtPT.getText();
    definition_def.classcircuit = txtCircuitClass.getText();
    definition_def.classfront = txtPanelClass.getText();

    File file = new File(elementDir);
    Tools.saveDefinitionFile(file, definition_def);

    dispose();
  }

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
    dispose();
  }

  private void txtENActionPerformed(java.awt.event.ActionEvent evt) {
    // Not implemented
  }

  public void execute(String elementDir) {
    this.elementDir = elementDir;

    File file = new File(elementDir);
    DFProperties definition_def = Tools.getPropertiesFromDefinitionFile(file);

    ImageIcon icon = new ImageIcon(elementDir + "/" + definition_def.iconFilename);
    image.setImage(icon.getImage());

    txtIcon.setText(definition_def.iconFilename);

    txtCircuitClass.setText(definition_def.classcircuit);
    txtPanelClass.setText(definition_def.classfront);

    txtDE.setText(definition_def.captionDE);
    txtEN.setText(definition_def.captionEN);
    txtES.setText(definition_def.captionES);
    txtPT.setText(definition_def.captionPT);

    setVisible(true);
  }

  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JButton jButton3;
  private javax.swing.JButton jButton4;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JPanel jPanel4;
  private javax.swing.JPanel jPanel5;
  private javax.swing.JTextField txtCircuitClass;
  private javax.swing.JTextField txtDE;
  private javax.swing.JTextField txtEN;
  private javax.swing.JTextField txtES;
  private javax.swing.JTextField txtPT;
  private javax.swing.JTextField txtIcon;
  private javax.swing.JTextField txtPanelClass;
}
