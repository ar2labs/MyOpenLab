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

package Peditor;

import VisualLogic.Element;
import VisualLogic.ElementProperty;
import VisualLogic.VMObject;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

class ElementItem {
  Element element = null;

  public ElementItem(Element element) {
    this.element = element;
  }

  public String toString() {
    return element.toString();
  }
}

public class DialogPropertiesChoice extends javax.swing.JDialog implements ListSelectionListener {
  private PropertyEditor propertyEditor;
  private VMObject vmobject;
  private boolean loading = false;
  public static ArrayList props = new ArrayList();
  private DefaultListModel model = new DefaultListModel();

  public static boolean result = false;

  public DialogPropertiesChoice(java.awt.Frame parent, boolean modal, VMObject vmobject) {
    super(parent, modal);
    initComponents();

    this.vmobject = vmobject;

    props.clear();
    jTable1.getSelectionModel().addListSelectionListener(this);
  }

  public void execute() {
    listAllElements();

    for (int i = 0; i < vmobject.propertyList.size(); i++) {
      BasisProperty prop = (BasisProperty) vmobject.propertyList.get(i);
      props.add(prop);
    }
    listProps();

    if (jComboBox1.getItemCount() > 0) jComboBox1.setSelectedIndex(0);
    setVisible(true);
  }

  private void initComponents() {
    jComboBox1 = new javax.swing.JComboBox();
    jLabel1 = new javax.swing.JLabel();
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();
    jLabel2 = new javax.swing.JLabel();
    jComboBox2 = new javax.swing.JComboBox();
    jButton3 = new javax.swing.JButton();
    jButton4 = new javax.swing.JButton();
    jScrollPane2 = new javax.swing.JScrollPane();
    jTable1 = new javax.swing.JTable();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("Properties");

    addWindowListener(
        new java.awt.event.WindowAdapter() {
          public void windowClosing(java.awt.event.WindowEvent evt) {
            formWindowClosing(evt);
          }
        });

    jComboBox1.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jComboBox1ActionPerformed(evt);
          }
        });

    jLabel1.setText("Element");

    jButton1.setText("Cancel");
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

    jLabel2.setText("Property");

    jButton3.setText("Add");
    jButton3.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton3ActionPerformed(evt);
          }
        });

    jButton4.setText("Remove");
    jButton4.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton4ActionPerformed(evt);
          }
        });

    jTable1.setModel(
        new javax.swing.table.DefaultTableModel(
            new Object[][] {
              {null, null},
              {null, null},
              {null, null},
              {null, null}
            },
            new String[] {"Element", "Property"}) {
          boolean[] canEdit = new boolean[] {false, false};

          public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit[columnIndex];
          }
        });
    jScrollPane2.setViewportView(jTable1);

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
                                jScrollPane2,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                340,
                                Short.MAX_VALUE)
                            .add(
                                org.jdesktop.layout.GroupLayout.LEADING,
                                layout
                                    .createSequentialGroup()
                                    .add(
                                        layout
                                            .createParallelGroup(
                                                org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(
                                                jLabel1,
                                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                                55,
                                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                            .add(jLabel2))
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(
                                        layout
                                            .createParallelGroup(
                                                org.jdesktop.layout.GroupLayout.TRAILING)
                                            .add(
                                                org.jdesktop.layout.GroupLayout.LEADING,
                                                jComboBox2,
                                                0,
                                                281,
                                                Short.MAX_VALUE)
                                            .add(
                                                org.jdesktop.layout.GroupLayout.LEADING,
                                                jComboBox1,
                                                0,
                                                281,
                                                Short.MAX_VALUE)
                                            .add(
                                                layout
                                                    .createSequentialGroup()
                                                    .add(
                                                        jButton3,
                                                        org.jdesktop.layout.GroupLayout
                                                            .PREFERRED_SIZE,
                                                        74,
                                                        org.jdesktop.layout.GroupLayout
                                                            .PREFERRED_SIZE)
                                                    .addPreferredGap(
                                                        org.jdesktop.layout.LayoutStyle.RELATED)
                                                    .add(jButton4))))
                            .add(
                                layout
                                    .createSequentialGroup()
                                    .addPreferredGap(
                                        org.jdesktop.layout.LayoutStyle.RELATED,
                                        202,
                                        Short.MAX_VALUE)
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
                        layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel1)
                            .add(
                                jComboBox1,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel2)
                            .add(
                                jComboBox2,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jButton4)
                            .add(jButton3))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        jScrollPane2,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        204,
                        Short.MAX_VALUE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        layout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jButton1)
                            .add(jButton2))
                    .addContainerGap()));
    java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    setBounds((screenSize.width - 368) / 2, (screenSize.height - 367) / 2, 368, 367);
  }

  private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {

    result = false;

    if (props.size() == vmobject.propertyList.size()) {
      for (int i = 0; i < vmobject.propertyList.size(); i++) {
        BasisProperty prop = (BasisProperty) vmobject.propertyList.get(i);
        BasisProperty p = (BasisProperty) props.get(i);

        if (p.elementID != prop.elementID || p.propertyIndex != prop.propertyIndex) {
          result = true;
        }
      }
    } else {
      result = true;
    }

    dispose();
  }

  private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {

    int index = jTable1.getSelectedRow();
    if (index >= 0) {
      BasisProperty p = (BasisProperty) props.get(index);
      if (p instanceof BasisProperty) {
        props.remove(p);
        listProps();
      }
    }
  }

  private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
    Element element = (Element) jComboBox1.getSelectedItem();
    int index = jComboBox2.getSelectedIndex();

    if (element != null && index > -1) {
      int elementID = element.getID();

      props.add(new BasisProperty(element.owner, elementID, index));
      listProps();
    }
  }

  private void formWindowClosing(java.awt.event.WindowEvent evt) {}

  private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {

    Element element = (Element) jComboBox1.getSelectedItem();

    if (element != null && loading == false) {
      listElementProperties(element);
    }
  }

  public void listAllElements() {
    VMObject vm = vmobject;

    loading = true;
    jComboBox1.removeAllItems();

    for (int i = 0; i < vm.getComponentCount(); i++) {
      Component comp = vm.getComponent(i);

      if (comp instanceof Element) {
        Element element = (Element) comp;

        jComboBox1.addItem(element);
      }
    }

    loading = false;
  }

  private void listProps() {

    DefaultTableModel tabellenModel = (DefaultTableModel) jTable1.getModel();

    tabellenModel.setRowCount(0);
    jTable1.setModel(tabellenModel);

    model.clear();

    for (int i = 0; i < props.size(); i++) {
      BasisProperty p = (BasisProperty) props.get(i);

      model.addElement(p);

      Object[] data = new Object[2];

      Element el = vmobject.getElementWithID(p.elementID);
      if (el != null) {
        ElementProperty elProp = (ElementProperty) el.propertyList.get(p.propertyIndex);

        data[0] = el.toString();
        data[1] = elProp.label;

        tabellenModel.addRow(data);
      }
    }
  }

  public void listElementProperties(Element element) {
    loading = true;
    jComboBox2.removeAllItems();

    for (int i = 0; i < element.propertyList.size(); i++) {
      ElementProperty elProp = (ElementProperty) element.propertyList.get(i);

      jComboBox2.addItem(elProp.label);
    }

    loading = false;
  }

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
    result = false;
    dispose();
  }

  public void valueChanged(ListSelectionEvent e) {}

  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JButton jButton3;
  private javax.swing.JButton jButton4;
  private javax.swing.JComboBox jComboBox1;
  private javax.swing.JComboBox jComboBox2;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JTable jTable1;
}
