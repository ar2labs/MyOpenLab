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
import VisualLogic.FrameMain;
import VisualLogic.Tools;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FormCodeEditor extends javax.swing.JFrame {

  private String elementPath;
  private String elementLocation;
  private String filename;

  /** Creates new form FormCodeEditor */
  public FormCodeEditor(FrameMain parent) {
    this.initComponents();
    try {
      setIconImage(parent.iconImage);
    } catch (Exception ex) {
    }
  }

  private void initComponents() {
    jPanel1 = new javax.swing.JPanel();
    jToolBar1 = new javax.swing.JToolBar();
    jButton4 = new javax.swing.JButton();
    jButton3 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();
    jTabbedPane1 = new javax.swing.JTabbedPane();
    jMenuBar2 = new javax.swing.JMenuBar();
    jmnuFile = new javax.swing.JMenu();
    jmniLoad = new javax.swing.JMenuItem();
    jmniSaveProject = new javax.swing.JMenuItem();
    jmniClose = new javax.swing.JMenuItem();
    jmnuEdit = new javax.swing.JMenu();
    jmniUndo = new javax.swing.JMenuItem();
    jmnuRedo = new javax.swing.JMenuItem();
    jSeparator1 = new javax.swing.JSeparator();
    jmniCut = new javax.swing.JMenuItem();
    jmniCopy = new javax.swing.JMenuItem();
    jmniPaste = new javax.swing.JMenuItem();
    jSeparator2 = new javax.swing.JSeparator();
    jmniSearch = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    addWindowListener(
        new java.awt.event.WindowAdapter() {
          public void windowClosing(java.awt.event.WindowEvent evt) {
            formWindowClosing(evt);
          }
        });

    jPanel1.setLayout(new java.awt.BorderLayout());

    jToolBar1.setFloatable(false);
    jToolBar1.setRollover(true);
    jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/open16.png")));
    java.util.ResourceBundle bundle =
        java.util.ResourceBundle.getBundle("CodeEditor/FormCodeEditor");
    jButton4.setText(bundle.getString("Load_File"));
    jButton4.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton4ActionPerformed(evt);
          }
        });

    jToolBar1.add(jButton4);

    jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/save16.png")));
    jButton3.setText(bundle.getString("save_Project"));
    jButton3.setToolTipText("Save");
    jButton3.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton3ActionPerformed(evt);
          }
        });

    jToolBar1.add(jButton3);

    jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/compile16.png")));
    jButton2.setText(bundle.getString("Compile"));
    jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    jButton2.setPreferredSize(new java.awt.Dimension(120, 25));
    jButton2.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
          }
        });

    jToolBar1.add(jButton2);

    jPanel1.add(jToolBar1, java.awt.BorderLayout.CENTER);

    getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

    getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

    jmnuFile.setText(bundle.getString("File"));
    jmnuFile.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmnuFileActionPerformed(evt);
          }
        });

    jmniLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/open16.png")));
    jmniLoad.setText(bundle.getString("Load_File"));
    jmnuFile.add(jmniLoad);

    jmniSaveProject.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/save16.png")));
    jmniSaveProject.setText(bundle.getString("save_Project"));
    jmnuFile.add(jmniSaveProject);

    jmniClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/exit16.png")));
    jmniClose.setText(bundle.getString("Close"));
    jmniClose.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmniCloseActionPerformed(evt);
          }
        });

    jmnuFile.add(jmniClose);

    jMenuBar2.add(jmnuFile);

    jmnuEdit.setText(bundle.getString("Edit"));
    jmniUndo.setAccelerator(
        javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
    jmniUndo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/edit-undo16.png")));
    jmniUndo.setText(bundle.getString("Undo"));
    jmniUndo.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmniUndoActionPerformed(evt);
          }
        });

    jmnuEdit.add(jmniUndo);

    jmnuRedo.setAccelerator(
        javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
    jmnuRedo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/edit-redo16.png")));
    jmnuRedo.setText(bundle.getString("Redo"));
    jmnuRedo.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmnuRedoActionPerformed(evt);
          }
        });

    jmnuEdit.add(jmnuRedo);

    jmnuEdit.add(jSeparator1);

    jmniCut.setAccelerator(
        javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
    jmniCut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/edit-cut16.png")));
    jmniCut.setText(bundle.getString("Cut"));
    jmniCut.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmniCutActionPerformed(evt);
          }
        });

    jmnuEdit.add(jmniCut);

    jmniCopy.setAccelerator(
        javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
    jmniCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/edit-copy16.png")));
    jmniCopy.setText(bundle.getString("Copy"));
    jmniCopy.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmniCopyActionPerformed(evt);
          }
        });

    jmnuEdit.add(jmniCopy);

    jmniPaste.setAccelerator(
        javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
    jmniPaste.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/edit-paste16.png")));
    jmniPaste.setText(bundle.getString("Paste"));
    jmniPaste.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmniPasteActionPerformed(evt);
          }
        });

    jmnuEdit.add(jmniPaste);

    jmnuEdit.add(jSeparator2);

    jmniSearch.setAccelerator(
        javax.swing.KeyStroke.getKeyStroke(
            java.awt.event.KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
    jmniSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search16.png")));
    jmniSearch.setText(bundle.getString("Search"));
    jmniSearch.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmniSearchActionPerformed(evt);
          }
        });

    jmnuEdit.add(jmniSearch);

    jMenuBar2.add(jmnuEdit);

    setJMenuBar(jMenuBar2);

    java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    setBounds((screenSize.width - 800) / 2, (screenSize.height - 600) / 2, 800, 600);
  }

  private void jmniCloseActionPerformed(java.awt.event.ActionEvent evt) {
    formWindowClosing(null);
  }

  private void jmniPasteActionPerformed(java.awt.event.ActionEvent evt) {
    PanelEditor panel = getAktuellerEditor();
    if (panel != null) {
      panel.paste();
    }
  }

  private void jmniCopyActionPerformed(java.awt.event.ActionEvent evt) {
    PanelEditor panel = getAktuellerEditor();
    if (panel != null) {
      panel.copy();
    }
  }

  private void jmniCutActionPerformed(java.awt.event.ActionEvent evt) {

    PanelEditor panel = getAktuellerEditor();
    if (panel != null) {
      panel.cut();
    }
  }

  private void jmnuRedoActionPerformed(java.awt.event.ActionEvent evt) {
    PanelEditor panel = getAktuellerEditor();

    if (panel != null) {
      panel.redo();
    }
  }

  public PanelEditor getAktuellerEditor() {
    if (jTabbedPane1.getSelectedComponent() instanceof PanelEditor) {
      return (PanelEditor) jTabbedPane1.getSelectedComponent();
    }

    return null;
  }

  private void jmniSearchActionPerformed(java.awt.event.ActionEvent evt) {

    PanelEditor panel = getAktuellerEditor();
    if (panel != null) {
      panel.openSearchDialog();
    }
  }

  private void jmniUndoActionPerformed(java.awt.event.ActionEvent evt) {

    PanelEditor panel = getAktuellerEditor();
    if (panel != null) {
      panel.undo();
    }
  }

  private void jmnuFileActionPerformed(java.awt.event.ActionEvent evt) {
    // TODO add your handling code here:
  }

  private void formWindowClosing(java.awt.event.WindowEvent evt) {
    for (int i = 0; i < jTabbedPane1.getTabCount(); i++) {
      Component comp = jTabbedPane1.getComponentAt(i);

      if (comp instanceof PanelEditor) {
        PanelEditor editor = (PanelEditor) comp;
        if (editor.onClose() == false) return;
      }
    }

    dispose();
  }

  public boolean openFile() {
    JFileChooser chooser = new JFileChooser();
    String make = elementPath + "/src";

    chooser.setCurrentDirectory(new java.io.File(make));
    chooser.addChoosableFileFilter(
        new javax.swing.filechooser.FileFilter() {
          public boolean accept(File f) {
            if (f.isDirectory()) return true;
            return f.getName().toLowerCase().endsWith(".java");
          }

          public String getDescription() {
            return "Java Files";
          }
        });

    int returnVal = chooser.showOpenDialog(this);

    if (returnVal == JFileChooser.APPROVE_OPTION) {
      loadFile(chooser.getSelectedFile().getAbsolutePath());

      return true;
    } else return false;
  }

  private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
    openFile();
  }

  private void saveProject() {
    for (int i = 0; i < jTabbedPane1.getTabCount(); i++) {
      Component comp = jTabbedPane1.getComponentAt(i);
      if (comp instanceof PanelEditor) {
        PanelEditor editor = (PanelEditor) comp;

        editor.saveFile();
      }
    }
  }

  private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
    saveProject();
  }

  private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {

    saveProject();

    for (int i = 0; i < jTabbedPane1.getTabCount(); i++) {
      Component comp = jTabbedPane1.getComponentAt(i);

      if (comp instanceof PanelEditor) {
        PanelEditor editor = (PanelEditor) comp;
        String srcFile = editor.filename;

        File file = new File(elementLocation);
        DFProperties definition_def = Tools.getPropertiesFromDefinitionFile(file);

        Tools.compileFile(elementPath, srcFile, elementLocation, definition_def.classPath);
      }
    }
  }

  public void execute(String elementPath, String elementLocation, String elementname) {
    this.elementPath = elementPath;
    this.elementLocation = elementLocation;

    File file = new File(elementLocation);
    DFProperties definition_def = Tools.getPropertiesFromDefinitionFile(file);

    String fn = elementLocation + "/src/";

    String srcPath = new File(fn).getAbsolutePath() + "/";

    jTabbedPane1.removeAll();

    if (definition_def.classcircuit.length() > 0) {
      loadFile(srcPath + definition_def.classcircuit + ".java");
    }

    if (definition_def.classfront.length() > 0) {
      loadFile(srcPath + definition_def.classfront + ".java");
    }

    setTitle(
        java.util.ResourceBundle.getBundle("CodeEditor/FormCodeEditor").getString("Code_Editor")
            + " ["
            + elementname
            + "]");
    setVisible(true);
  }

  class JButtonX extends JButton {
    public PanelEditor panel;

    public JButtonX(javax.swing.ImageIcon icon) {
      super(icon);
    }
  }

  public void loadFile(String filename) {
    PanelEditor pnl = new PanelEditor(this);
    pnl.loadFile(filename);

    String caption = new File(filename).getName();

    jTabbedPane1.add(pnl);

    final JPanel content = new JPanel();

    JPanel tab = new JPanel();
    tab.setLayout(new BorderLayout());
    tab.setOpaque(false);

    JLabel tabLabel = new JLabel();

    tabLabel.setText(caption + "  ");

    javax.swing.ImageIcon closeXIcon =
        new javax.swing.ImageIcon(getClass().getResource("/Images/cross12.png"));
    Dimension closeButtonSize;

    closeButtonSize = new Dimension(closeXIcon.getIconWidth(), closeXIcon.getIconHeight());

    JButtonX tabCloseButton = new JButtonX(closeXIcon);

    tabCloseButton.setOpaque(false);
    tabCloseButton.setContentAreaFilled(false);
    tabCloseButton.setBorderPainted(false);
    tabCloseButton.panel = pnl;

    tabCloseButton.setPreferredSize(closeButtonSize);

    ImageIcon icon = new ImageIcon(getClass().getResource("/Images/file-java16.png"));

    tabLabel.setIcon(icon);
    tabLabel.setBorder(null);

    tab.setBorder(null);
    tabCloseButton.setBorder(null);

    tabCloseButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButtonX) {
              JButtonX btn = (JButtonX) e.getSource();

              btn.panel.close();
            }
          }
        });

    tab.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

    tab.add(tabLabel, BorderLayout.WEST);
    tab.add(tabCloseButton, BorderLayout.EAST);

    jTabbedPane1.setTabComponentAt(jTabbedPane1.getTabCount() - 1, tab);
  }

  private javax.swing.JButton jButton2;
  private javax.swing.JButton jButton3;
  private javax.swing.JButton jButton4;
  private javax.swing.JMenuBar jMenuBar2;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JSeparator jSeparator1;
  private javax.swing.JSeparator jSeparator2;
  private javax.swing.JTabbedPane jTabbedPane1;
  private javax.swing.JToolBar jToolBar1;
  private javax.swing.JMenuItem jmniClose;
  private javax.swing.JMenuItem jmniCopy;
  private javax.swing.JMenuItem jmniCut;
  private javax.swing.JMenuItem jmniLoad;
  private javax.swing.JMenuItem jmniPaste;
  private javax.swing.JMenuItem jmniSaveProject;
  private javax.swing.JMenuItem jmniSearch;
  private javax.swing.JMenuItem jmniUndo;
  private javax.swing.JMenu jmnuEdit;
  private javax.swing.JMenu jmnuFile;
  private javax.swing.JMenuItem jmnuRedo;
}
