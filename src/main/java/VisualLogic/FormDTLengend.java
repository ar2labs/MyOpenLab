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

import java.awt.Image;
import java.net.URL;
import java.util.Locale;

public class FormDTLengend extends javax.swing.JFrame {

  /** Creates new form FormDTLengend */
  public FormDTLengend(Image image) {
    initComponents();
    setIconImage(image);

    String filename = "types_de.htm";
    String strLocale = Locale.getDefault().toString();

    if (strLocale.equalsIgnoreCase("de_DE")) {
      filename = "types_de.html";
    } else if (strLocale.equalsIgnoreCase("en_US")) {
      filename = "types_en.html";
    } else if (strLocale.equalsIgnoreCase("es_ES")) {
      filename = "types_es.html";
    } else if (strLocale.equalsIgnoreCase("pt_BR")) {
      filename = "types_pt.html";
    }

    try {
      URL url = getClass().getResource("/Legend/" + filename);
      jTextPane1.setContentType("text/html");
      jTextPane1.setPage(url);
    } catch (Exception ex) {
      System.out.println(ex.toString());
    }

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
    jScrollPane1 = new javax.swing.JScrollPane();
    jTextPane1 = new javax.swing.JTextPane();
    jButton1 = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    java.util.ResourceBundle bundle =
        java.util.ResourceBundle.getBundle("VisualLogic/FrameCircuit");
    setTitle(bundle.getString("Datentyp-Legende"));

    jTextPane1.setEditable(false);
    jScrollPane1.setViewportView(jTextPane1);

    jButton1.setText("Close");
    jButton1.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
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
                            .add(
                                org.jdesktop.layout.GroupLayout.LEADING,
                                jScrollPane1,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                497,
                                Short.MAX_VALUE)
                            .add(jButton1))
                    .addContainerGap()));
    layout.setVerticalGroup(
        layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                org.jdesktop.layout.GroupLayout.TRAILING,
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(
                        jScrollPane1,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        279,
                        Short.MAX_VALUE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jButton1)
                    .addContainerGap()));

    java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    setBounds((screenSize.width - 525) / 2, (screenSize.height - 357) / 2, 525, 357);
  }

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
    dispose();
  }

  private javax.swing.JButton jButton1;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JTextPane jTextPane1;
}
