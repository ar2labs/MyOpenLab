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

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.SpinnerNumberModel;

public class DialogOptions extends javax.swing.JDialog {
  private Settings settings;
  private FrameMain frameMain;

  /** Creates new form FormOptions */
  public DialogOptions(Settings settings, FrameMain parent, boolean modal) {
    super(parent, modal);
    initComponents();

    jCheckBox5.setVisible(false);

    this.frameMain = parent;

    this.settings = settings;

    txtUserdefPath.setText(settings.getUserdefinedElementsPath());

    int rx1 = settings.getCircuitRasterX();
    int ry1 = settings.getCircuitRasterY();

    int rx2 = settings.getFrontRasterX();
    int ry2 = settings.getFrontRasterY();

    SpinnerNumberModel modelA2 = new SpinnerNumberModel(rx1, 1.0, 100.0, 1);
    SpinnerNumberModel modelB2 = new SpinnerNumberModel(ry1, 1.0, 100.0, 1);

    jSpinner5.setModel(modelA2);
    jSpinner6.setModel(modelB2);

    SpinnerNumberModel modelA = new SpinnerNumberModel(rx2, 1.0, 100.0, 1);
    SpinnerNumberModel modelB = new SpinnerNumberModel(ry2, 1.0, 100.0, 1);

    jSpinner1.setModel(modelA);
    jSpinner2.setModel(modelB);

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

  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    buttonGroup1 = new javax.swing.ButtonGroup();
    jTabbedPane1 = new javax.swing.JTabbedPane();
    jPanel3 = new javax.swing.JPanel();
    jPanel5 = new javax.swing.JPanel();
    jCheckBox2 = new javax.swing.JCheckBox();
    jCheckBox1 = new javax.swing.JCheckBox();
    jSpinner2 = new javax.swing.JSpinner();
    jSpinner1 = new javax.swing.JSpinner();
    jLabel4 = new javax.swing.JLabel();
    jLabel5 = new javax.swing.JLabel();
    jPanel6 = new javax.swing.JPanel();
    jLabel7 = new javax.swing.JLabel();
    jLabel8 = new javax.swing.JLabel();
    jSpinner5 = new javax.swing.JSpinner();
    jSpinner6 = new javax.swing.JSpinner();
    jCheckBox3 = new javax.swing.JCheckBox();
    jCheckBox4 = new javax.swing.JCheckBox();
    jPanel2 = new javax.swing.JPanel();
    jButton4 = new javax.swing.JButton();
    txtHTMLEditor = new javax.swing.JTextField();
    jLabel2 = new javax.swing.JLabel();
    jLabel6 = new javax.swing.JLabel();
    txtGraphicEditor = new javax.swing.JTextField();
    jButton6 = new javax.swing.JButton();
    jLabel13 = new javax.swing.JLabel();
    txtJavascriptEditor = new javax.swing.JTextField();
    jButton7 = new javax.swing.JButton();
    jPanel1 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    txtUserdefPath = new javax.swing.JTextField();
    jButton3 = new javax.swing.JButton();
    jButton5 = new javax.swing.JButton();
    jPanel7 = new javax.swing.JPanel();
    jCheckBox5 = new javax.swing.JCheckBox();
    jCheckBox6 = new javax.swing.JCheckBox();
    jPanel9 = new javax.swing.JPanel();
    jLabel10 = new javax.swing.JLabel();
    jLabel11 = new javax.swing.JLabel();
    jLabel12 = new javax.swing.JLabel();
    txtRepositoryURL = new javax.swing.JTextField();
    txtRepositoryLoginUser = new javax.swing.JTextField();
    txtRepositoryLoginPassword = new javax.swing.JTextField();
    jLabel3 = new javax.swing.JLabel();
    txtProxyHost = new javax.swing.JTextField();
    jLabel9 = new javax.swing.JLabel();
    txtProxyPort = new javax.swing.JTextField();
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("VisualLogic/FormOptions");
    setTitle(bundle.getString("Title"));
    setResizable(false);

    jPanel5.setBorder(
        javax.swing.BorderFactory.createTitledBorder(
            bundle.getString("DialogOptions.jPanel5.border.title")));

    jCheckBox2.setText(bundle.getString("_Align_to_Raster"));
    jCheckBox2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    jCheckBox2.setMargin(new java.awt.Insets(0, 0, 0, 0));

    jCheckBox1.setText(bundle.getString("Raster_Visible"));
    jCheckBox1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    jCheckBox1.setMargin(new java.awt.Insets(0, 0, 0, 0));

    jLabel4.setText(bundle.getString("RasterX"));
    jLabel5.setText(bundle.getString("RasterY"));

    org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
    jPanel5.setLayout(jPanel5Layout);
    jPanel5Layout.setHorizontalGroup(
        jPanel5Layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanel5Layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(
                        jPanel5Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(
                                jPanel5Layout
                                    .createSequentialGroup()
                                    .add(
                                        jCheckBox1,
                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)
                                    .add(144, 144, 144))
                            .add(
                                jPanel5Layout
                                    .createSequentialGroup()
                                    .add(
                                        jPanel5Layout
                                            .createParallelGroup(
                                                org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(
                                                jPanel5Layout
                                                    .createParallelGroup(
                                                        org.jdesktop.layout.GroupLayout.TRAILING,
                                                        false)
                                                    .add(
                                                        org.jdesktop.layout.GroupLayout.LEADING,
                                                        jPanel5Layout
                                                            .createSequentialGroup()
                                                            .add(jLabel4)
                                                            .addPreferredGap(
                                                                org.jdesktop.layout.LayoutStyle
                                                                    .RELATED)
                                                            .add(
                                                                jSpinner1,
                                                                org.jdesktop.layout.GroupLayout
                                                                    .PREFERRED_SIZE,
                                                                org.jdesktop.layout.GroupLayout
                                                                    .PREFERRED_SIZE,
                                                                org.jdesktop.layout.GroupLayout
                                                                    .PREFERRED_SIZE))
                                                    .add(
                                                        org.jdesktop.layout.GroupLayout.LEADING,
                                                        jPanel5Layout
                                                            .createSequentialGroup()
                                                            .add(jLabel5)
                                                            .addPreferredGap(
                                                                org.jdesktop.layout.LayoutStyle
                                                                    .RELATED)
                                                            .add(
                                                                jSpinner2,
                                                                org.jdesktop.layout.GroupLayout
                                                                    .PREFERRED_SIZE,
                                                                org.jdesktop.layout.GroupLayout
                                                                    .PREFERRED_SIZE,
                                                                org.jdesktop.layout.GroupLayout
                                                                    .PREFERRED_SIZE)))
                                            .add(jCheckBox2))
                                    .addContainerGap(
                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)))));
    jPanel5Layout.setVerticalGroup(
        jPanel5Layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanel5Layout
                    .createSequentialGroup()
                    .add(
                        jPanel5Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel4)
                            .add(
                                jSpinner1,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        jPanel5Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel5)
                            .add(
                                jSpinner2,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jCheckBox1)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jCheckBox2)
                    .addContainerGap(42, Short.MAX_VALUE)));

    jPanel6.setBorder(
        javax.swing.BorderFactory.createTitledBorder(
            bundle.getString("DialogOptions.jPanel6.border.title")));

    jLabel7.setText(bundle.getString("RasterX"));
    jLabel8.setText(bundle.getString("RasterY"));

    jCheckBox3.setText(bundle.getString("Raster_Visible"));
    jCheckBox3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    jCheckBox3.setMargin(new java.awt.Insets(0, 0, 0, 0));

    jCheckBox4.setText(bundle.getString("_Align_to_Raster"));
    jCheckBox4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    jCheckBox4.setMargin(new java.awt.Insets(0, 0, 0, 0));

    org.jdesktop.layout.GroupLayout jPanel6Layout = new org.jdesktop.layout.GroupLayout(jPanel6);
    jPanel6.setLayout(jPanel6Layout);
    jPanel6Layout.setHorizontalGroup(
        jPanel6Layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanel6Layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(
                        jPanel6Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jCheckBox4)
                            .add(jCheckBox3)
                            .add(
                                jPanel6Layout
                                    .createSequentialGroup()
                                    .add(
                                        jPanel6Layout
                                            .createParallelGroup(
                                                org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(jLabel7)
                                            .add(jLabel8))
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(
                                        jPanel6Layout
                                            .createParallelGroup(
                                                org.jdesktop.layout.GroupLayout.LEADING, false)
                                            .add(jSpinner6)
                                            .add(
                                                jSpinner5,
                                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                61,
                                                Short.MAX_VALUE))))
                    .addContainerGap(174, Short.MAX_VALUE)));
    jPanel6Layout.setVerticalGroup(
        jPanel6Layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanel6Layout
                    .createSequentialGroup()
                    .add(
                        jPanel6Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel7)
                            .add(
                                jSpinner5,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(10, 10, 10)
                    .add(
                        jPanel6Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel8)
                            .add(
                                jSpinner6,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jCheckBox3)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jCheckBox4)
                    .addContainerGap(38, Short.MAX_VALUE)));

    org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                org.jdesktop.layout.GroupLayout.TRAILING,
                jPanel3Layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(
                        jPanel6,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        jPanel5,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        219,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()));
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanel3Layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(
                        jPanel3Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(
                                jPanel6,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                            .add(
                                jPanel5,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE))
                    .addContainerGap()));

    jTabbedPane1.addTab(bundle.getString("DialogOptions.jPanel3.TabConstraints.tabTitle"), jPanel3);

    jButton4.setText(bundle.getString("durchsuchen"));
    jButton4.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton4ActionPerformed(evt);
          }
        });

    txtHTMLEditor.setText(bundle.getString("DialogOptions.txtHTMLEditor.text"));

    jLabel2.setText(bundle.getString("HTML_Editor"));

    jLabel6.setText(bundle.getString("imageEditor"));

    txtGraphicEditor.setText(bundle.getString("DialogOptions.txtGraphicEditor.text"));

    jButton6.setText(bundle.getString("durchsuchen"));
    jButton6.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton6ActionPerformed(evt);
          }
        });

    jLabel13.setText(bundle.getString("javascript_editor"));

    txtJavascriptEditor.setText(bundle.getString("DialogOptions.txtJavascriptEditor.text"));

    jButton7.setText(bundle.getString("DialogOptions.jButton7.text"));
    jButton7.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton7ActionPerformed(evt);
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
                            .add(jLabel2)
                            .add(jLabel6)
                            .add(jLabel13))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 16, Short.MAX_VALUE)
                    .add(
                        jPanel2Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(
                                jPanel2Layout
                                    .createSequentialGroup()
                                    .add(
                                        txtJavascriptEditor,
                                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                        347,
                                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(
                                        org.jdesktop.layout.LayoutStyle.RELATED,
                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)
                                    .add(jButton7))
                            .add(
                                jPanel2Layout
                                    .createSequentialGroup()
                                    .add(
                                        txtHTMLEditor,
                                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                        1,
                                        Short.MAX_VALUE)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(jButton4))
                            .add(
                                org.jdesktop.layout.GroupLayout.TRAILING,
                                jPanel2Layout
                                    .createSequentialGroup()
                                    .add(
                                        txtGraphicEditor,
                                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                        347,
                                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(jButton6)))
                    .addContainerGap()));
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanel2Layout
                    .createSequentialGroup()
                    .add(16, 16, 16)
                    .add(
                        jPanel2Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel2)
                            .add(
                                txtHTMLEditor,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jButton4))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        jPanel2Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jButton6)
                            .add(
                                txtGraphicEditor,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel6))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        jPanel2Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(
                                txtJavascriptEditor,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel13)
                            .add(jButton7))
                    .addContainerGap(74, Short.MAX_VALUE)));

    jTabbedPane1.addTab(bundle.getString("Editoren"), jPanel2);

    jLabel1.setText(bundle.getString("DialogOptions.jLabel1.text"));

    txtUserdefPath.setText(bundle.getString("DialogOptions.txtUserdefPath.text"));

    jButton3.setText(bundle.getString("DialogOptions.jButton3.text"));
    jButton3.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton3ActionPerformed(evt);
          }
        });

    jButton5.setText(bundle.getString("DialogOptions.jButton5.text"));
    jButton5.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton5ActionPerformed(evt);
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
                        jPanel1Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(
                                org.jdesktop.layout.GroupLayout.TRAILING,
                                jPanel1Layout
                                    .createSequentialGroup()
                                    .add(
                                        txtUserdefPath,
                                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                        457,
                                        Short.MAX_VALUE)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(jButton3))
                            .add(jLabel1)
                            .add(jButton5))
                    .addContainerGap()));
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanel1Layout
                    .createSequentialGroup()
                    .add(21, 21, 21)
                    .add(jLabel1)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        jPanel1Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jButton3)
                            .add(
                                txtUserdefPath,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(18, 18, 18)
                    .add(jButton5)
                    .addContainerGap(69, Short.MAX_VALUE)));

    jTabbedPane1.addTab(bundle.getString("DialogOptions.jPanel1.TabConstraints.tabTitle"), jPanel1);

    jCheckBox5.setText(bundle.getString("DialogOptions.jCheckBox5.text"));
    jCheckBox5.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    jCheckBox5.setMargin(new java.awt.Insets(0, 0, 0, 0));

    jCheckBox6.setText(bundle.getString("DialogOptions.jCheckBox6.text"));
    jCheckBox6.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    jCheckBox6.setMargin(new java.awt.Insets(0, 0, 0, 0));

    org.jdesktop.layout.GroupLayout jPanel7Layout = new org.jdesktop.layout.GroupLayout(jPanel7);
    jPanel7.setLayout(jPanel7Layout);
    jPanel7Layout.setHorizontalGroup(
        jPanel7Layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanel7Layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(
                        jPanel7Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jCheckBox6)
                            .add(jCheckBox5))
                    .addContainerGap(453, Short.MAX_VALUE)));
    jPanel7Layout.setVerticalGroup(
        jPanel7Layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanel7Layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(jCheckBox6)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jCheckBox5)
                    .addContainerGap(127, Short.MAX_VALUE)));

    jTabbedPane1.addTab(bundle.getString("DialogOptions.jPanel7.TabConstraints.tabTitle"), jPanel7);

    jLabel10.setText(bundle.getString("DialogOptions.jLabel10.text"));

    jLabel11.setText(bundle.getString("DialogOptions.jLabel11.text"));

    jLabel12.setText(bundle.getString("DialogOptions.jLabel12.text"));

    txtRepositoryURL.setText(bundle.getString("DialogOptions.txtRepositoryURL.text"));

    txtRepositoryLoginUser.setText(bundle.getString("DialogOptions.txtRepositoryLoginUser.text"));

    txtRepositoryLoginPassword.setText(
        bundle.getString("DialogOptions.txtRepositoryLoginPassword.text"));

    jLabel3.setText(bundle.getString("DialogOptions.jLabel3.text"));

    txtProxyHost.setText(bundle.getString("DialogOptions.txtProxyHost.text"));

    jLabel9.setText(bundle.getString("DialogOptions.jLabel9.text"));

    txtProxyPort.setText(bundle.getString("DialogOptions.txtProxyPort.text"));

    org.jdesktop.layout.GroupLayout jPanel9Layout = new org.jdesktop.layout.GroupLayout(jPanel9);
    jPanel9.setLayout(jPanel9Layout);
    jPanel9Layout.setHorizontalGroup(
        jPanel9Layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanel9Layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(
                        jPanel9Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(
                                jPanel9Layout
                                    .createParallelGroup(
                                        org.jdesktop.layout.GroupLayout.LEADING, false)
                                    .add(
                                        jPanel9Layout
                                            .createSequentialGroup()
                                            .add(jLabel12)
                                            .addPreferredGap(
                                                org.jdesktop.layout.LayoutStyle.UNRELATED)
                                            .add(
                                                txtRepositoryLoginPassword,
                                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                                458,
                                                Short.MAX_VALUE))
                                    .add(
                                        jPanel9Layout
                                            .createSequentialGroup()
                                            .add(jLabel11)
                                            .add(34, 34, 34)
                                            .add(txtRepositoryLoginUser))
                                    .add(
                                        jPanel9Layout
                                            .createSequentialGroup()
                                            .add(
                                                jPanel9Layout
                                                    .createParallelGroup(
                                                        org.jdesktop.layout.GroupLayout.LEADING)
                                                    .add(jLabel10)
                                                    .add(jLabel9))
                                            .addPreferredGap(
                                                org.jdesktop.layout.LayoutStyle.UNRELATED)
                                            .add(
                                                jPanel9Layout
                                                    .createParallelGroup(
                                                        org.jdesktop.layout.GroupLayout.LEADING)
                                                    .add(txtRepositoryURL)
                                                    .add(
                                                        jPanel9Layout
                                                            .createSequentialGroup()
                                                            .add(
                                                                txtProxyPort,
                                                                org.jdesktop.layout.GroupLayout
                                                                    .PREFERRED_SIZE,
                                                                54,
                                                                org.jdesktop.layout.GroupLayout
                                                                    .PREFERRED_SIZE)
                                                            .add(0, 0, Short.MAX_VALUE))
                                                    .add(txtProxyHost))))
                            .add(jLabel3))
                    .addContainerGap(
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    jPanel9Layout.setVerticalGroup(
        jPanel9Layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                org.jdesktop.layout.GroupLayout.TRAILING,
                jPanel9Layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(
                        jPanel9Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel3)
                            .add(
                                txtProxyHost,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        jPanel9Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel9)
                            .add(
                                txtProxyPort,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        jPanel9Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel10)
                            .add(
                                txtRepositoryURL,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        jPanel9Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel11)
                            .add(
                                txtRepositoryLoginUser,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        jPanel9Layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel12)
                            .add(
                                txtRepositoryLoginPassword,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(36, Short.MAX_VALUE)));

    jTabbedPane1.addTab(bundle.getString("DialogOptions.jPanel9.TabConstraints.tabTitle"), jPanel9);

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
                org.jdesktop.layout.GroupLayout.TRAILING,
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(
                        layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jTabbedPane1)
                            .add(
                                layout
                                    .createSequentialGroup()
                                    .add(
                                        jButton2,
                                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                        65,
                                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(jButton1)))
                    .addContainerGap()));
    layout.setVerticalGroup(
        layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(
                        jTabbedPane1,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        199,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jButton1)
                            .add(jButton2))
                    .addContainerGap(
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

    setSize(new java.awt.Dimension(603, 289));
    setLocationRelativeTo(null);
  }

  private void jButton3ActionPerformed(
      java.awt.event.ActionEvent evt) { // GEN-FIRST:event_jButton3ActionPerformed
    String str = chooseFile(txtUserdefPath.getText(), true);
    if (str != null) {
      txtUserdefPath.setText(str);
    }
  } // GEN-LAST:event_jButton3ActionPerformed

  private void jButton6ActionPerformed(
      java.awt.event.ActionEvent evt) { // GEN-FIRST:event_jButton6ActionPerformed
    String str = chooseFile(txtGraphicEditor.getText(), false);
    if (str != null) {
      txtGraphicEditor.setText(str);
    }
  } // GEN-LAST:event_jButton6ActionPerformed

  private void jButton4ActionPerformed(
      java.awt.event.ActionEvent evt) // GEN-FIRST:event_jButton4ActionPerformed
      { // GEN-HEADEREND:event_jButton4ActionPerformed
    String str = chooseFile(txtHTMLEditor.getText(), false);
    if (str != null) {
      txtHTMLEditor.setText(str);
    }
  } // GEN-LAST:event_jButton4ActionPerformed

  public String chooseFile(String path, boolean onlyDir) {
    JFileChooser chooser = new JFileChooser();

    chooser.setCurrentDirectory(new File(path));
    if (onlyDir) {
      // chooser.setDialogType (chooser.OPEN_DIALOG);
      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    int returnVal = chooser.showOpenDialog(this);

    if (returnVal == JFileChooser.APPROVE_OPTION) {
      return chooser.getSelectedFile().getAbsolutePath();
    }

    return null;
  }

  private void jButton2ActionPerformed(
      java.awt.event.ActionEvent evt) // GEN-FIRST:event_jButton2ActionPerformed
      { // GEN-HEADEREND:event_jButton2ActionPerformed

    Double circuitDX = (Double) jSpinner5.getValue();
    Double circuitDY = (Double) jSpinner6.getValue();

    Double frontDX = (Double) jSpinner1.getValue();
    Double frontDY = (Double) jSpinner2.getValue();

    settings.setProxy_host(txtProxyHost.getText());
    settings.setProxy_port(txtProxyPort.getText());

    settings.setCircuitRasterOn(jCheckBox3.isSelected());
    settings.setCircuittAlignToGrid(jCheckBox4.isSelected());
    settings.setCircuitRasterX((int) circuitDX.doubleValue());
    settings.setCircuitRasterY((int) circuitDY.doubleValue());

    settings.setFrontRasterOn(jCheckBox1.isSelected());
    settings.setFrontAlignToGrid(jCheckBox2.isSelected());
    settings.setFrontRasterX((int) frontDX.doubleValue());
    settings.setFrontRasterY((int) frontDY.doubleValue());

    settings.setCircuitCrossVisible(jCheckBox5.isSelected());

    // settings.javaEditor=txtJavaEditor.getText();
    settings.setHTMLEditor(txtHTMLEditor.getText());
    // settings.jdkBin=txtJDKBIN.getText();
    settings.setGraphicEditor(txtGraphicEditor.getText());

    settings.setElementIDVisible(jCheckBox6.isSelected());

    String userPath = txtUserdefPath.getText();
    if (!settings.getUserdefinedElementsPath().equalsIgnoreCase(userPath)) {
      frameMain.createUserElementSubDirs(txtUserdefPath.getText());
      // settings.userdefinedElementsPath=txtUserdefPath.getText();
    }

    settings.setRepository_domain(txtRepositoryURL.getText());
    settings.setRepository_login_username(txtRepositoryLoginUser.getText());
    settings.setRepository_login_password(txtRepositoryLoginPassword.getText());

    settings.setJavascript_editor(txtJavascriptEditor.getText());

    Tools.userElementPath = settings.getUserdefinedElementsPath();

    result = true;
    dispose();
  } // GEN-LAST:event_jButton2ActionPerformed

  private void jButton1ActionPerformed(
      java.awt.event.ActionEvent evt) // GEN-FIRST:event_jButton1ActionPerformed
      { // GEN-HEADEREND:event_jButton1ActionPerformed
    result = false;
    dispose();
  } // GEN-LAST:event_jButton1ActionPerformed

  private void jButton5ActionPerformed(
      java.awt.event.ActionEvent evt) { // GEN-FIRST:event_jButton5ActionPerformed
    frameMain.copyUserdefElements();
  } // GEN-LAST:event_jButton5ActionPerformed

  private void jButton7ActionPerformed(
      java.awt.event.ActionEvent evt) { // GEN-FIRST:event_jButton7ActionPerformed
    String str = chooseFile(txtJavascriptEditor.getText(), false);
    if (str != null) {
      txtJavascriptEditor.setText(str);
    }
  } // GEN-LAST:event_jButton7ActionPerformed

  public void execute(Settings settings) {
    this.settings = settings;

    txtRepositoryURL.setText(settings.getRepository_domain());
    txtRepositoryLoginUser.setText(settings.getRepository_login_username());
    txtRepositoryLoginPassword.setText(settings.getRepository_login_password());

    txtHTMLEditor.setText(settings.getHTMLEditor());
    txtGraphicEditor.setText(settings.getGraphicEditor());

    jCheckBox1.setSelected(settings.isFrontRasterOn());
    jCheckBox2.setSelected(settings.isFrontAlignToGrid());

    jCheckBox3.setSelected(settings.isCircuitRasterOn());
    jCheckBox4.setSelected(settings.isCircuittAlignToGrid());

    jCheckBox5.setSelected(settings.isCircuitCrossVisible());
    jCheckBox6.setSelected(settings.isElementIDVisible());

    txtProxyHost.setText(settings.getProxy_host());
    txtProxyPort.setText(settings.getProxy_port());

    txtJavascriptEditor.setText(settings.getJavascript_editor());

    setVisible(true);
  }

  public static boolean result = false;

  private javax.swing.ButtonGroup buttonGroup1;
  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JButton jButton3;
  private javax.swing.JButton jButton4;
  private javax.swing.JButton jButton5;
  private javax.swing.JButton jButton6;
  private javax.swing.JButton jButton7;
  private javax.swing.JCheckBox jCheckBox1;
  private javax.swing.JCheckBox jCheckBox2;
  private javax.swing.JCheckBox jCheckBox3;
  private javax.swing.JCheckBox jCheckBox4;
  private javax.swing.JCheckBox jCheckBox5;
  private javax.swing.JCheckBox jCheckBox6;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel10;
  private javax.swing.JLabel jLabel11;
  private javax.swing.JLabel jLabel12;
  private javax.swing.JLabel jLabel13;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JLabel jLabel9;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JPanel jPanel5;
  private javax.swing.JPanel jPanel6;
  private javax.swing.JPanel jPanel7;
  private javax.swing.JPanel jPanel9;
  private javax.swing.JSpinner jSpinner1;
  private javax.swing.JSpinner jSpinner2;
  private javax.swing.JSpinner jSpinner5;
  private javax.swing.JSpinner jSpinner6;
  private javax.swing.JTabbedPane jTabbedPane1;
  private javax.swing.JTextField txtGraphicEditor;
  private javax.swing.JTextField txtHTMLEditor;
  private javax.swing.JTextField txtJavascriptEditor;
  private javax.swing.JTextField txtProxyHost;
  private javax.swing.JTextField txtProxyPort;
  private javax.swing.JTextField txtRepositoryLoginPassword;
  private javax.swing.JTextField txtRepositoryLoginUser;
  private javax.swing.JTextField txtRepositoryURL;
  private javax.swing.JTextField txtUserdefPath;
}
