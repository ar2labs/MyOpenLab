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

import MyParser.OpenVariable;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

/** @author Salafia */
public class DialogVariableWatcher extends javax.swing.JDialog {

  public ArrayList variablenListe = new ArrayList();
  private DefaultListModel model = new DefaultListModel();

  /** Creates new form DialogVariableWatcher */
  public DialogVariableWatcher(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();

    jList1.setModel(model);
    model.clear();
  }

  public void refreshList() {
    OpenVariable node;

    model.clear();
    for (int i = 0; i < variablenListe.size(); i++) {
      node = (OpenVariable) variablenListe.get(i);

      model.addElement(node.name + "=" + node.value);
    }
  }

  public void execute(ArrayList variablenListe) {
    this.variablenListe = variablenListe;
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT
   * modify this code. The content of this method is always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jScrollPane1 = new javax.swing.JScrollPane();
    jList1 = new javax.swing.JList();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    java.util.ResourceBundle bundle =
        java.util.ResourceBundle.getBundle("VisualLogic/DialogVariableWatcher");
    setTitle(bundle.getString("Title"));

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
                        jScrollPane1,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        270,
                        Short.MAX_VALUE)
                    .addContainerGap()));
    layout.setVerticalGroup(
        layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .add(
                        jScrollPane1,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        167,
                        Short.MAX_VALUE)
                    .addContainerGap()));

    java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    setBounds((screenSize.width - 298) / 2, (screenSize.height - 216) / 2, 298, 216);
  }

  private javax.swing.JList jList1;
  private javax.swing.JScrollPane jScrollPane1;
}