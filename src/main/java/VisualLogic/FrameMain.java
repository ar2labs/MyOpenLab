/*
MyOpenLab by Carmelo Salafia www.myopenlab.de
Copyright (C) 2004 Carmelo Salafia cswi@gmx.de
Copyright (C) 2017 Javier Velásquez (javiervelasquez125@gmail.com)

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

import BasisStatus.StatusGummiBandX;
import BasisStatus.StatusGummiBandXBackIF;
import MyOpenLab.FormUpdate;
import ParserCode.Expression;
import Peditor.PropertyEditor;
import ProjectFolder.MyNode;
import ProjectFolder.ProjectPalette;
import SimulatorSocket.MyOpenLabOwnerIF;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.SplashScreen;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileView;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

class MyButtonX extends JButton {

  public VMEditorPanel panel;

  public MyButtonX(String caption) {
    super(caption);
  }

  public MyButtonX(Icon icon) {
    super(icon);
  }
}

public class FrameMain extends javax.swing.JFrame
    implements MyOpenLabOwnerIF,
        ProjectFolder.ProjectPaletteIF,
        ElementPaletteIF,
        VMEditorPanelIF,
        StatusGummiBandXBackIF {

  private String driverPath;
  private boolean oki = false;
  public PanelDokumentation panelDoc;
  public static String elementPath;
  public String activeElement = "";
  public static FrameMain frm;
  public javax.swing.Timer timer;
  private String letztesVerzeichniss = ".";
  public static Image iconImage = null;
  public static URL userURL = null;
  public String oldCircuitDirectory = "/CircuitElements";
  public String oldPanelDirectory = "/FrontElements";
  private Element oldElement = null;
  public FrameDoc docFrame = null;
  public DialogVariableWatcher watcher = null;
  public JLabel layedLabel = new JLabel("");
  public FrameErrorWarnings errorWarnings = new FrameErrorWarnings();
  public boolean frontMode = false;
  public ArrayList<String> projects = new ArrayList<>();
  // public Console console= new Console();
  public ArrayList<Basis> desktop = new ArrayList<>();
  public boolean modePanel = false;
  public Settings settings = new Settings();
  // private FrameMain frameMain;
  private ElementPalette elementPaletteCircuit;
  private ElementPalette elementPaletteFront;
  private ProjectFolder.ProjectPalette projectPalette1;
  private PropertyEditor propertyEditor;

  private void reLoadElementPaletteDipendentFromProjectType() {
    Basis basis = getActualBasis();
    if (basis.projectPath != null && basis.projectPath.length() > 0) {

      ProjectProperties props = Tools.openProjectFile(new File(basis.projectPath));

      if (props.projectType.equalsIgnoreCase("SPS")) {
        elementPaletteCircuit.init(
            this, basis, elementPath, "/CircuitElements/MCU/StackInterpreter/");
        elementPaletteFront.init(this, basis, elementPath, "/FrontElements/MCU/Interpreter");
      } else {

        elementPaletteCircuit.init(this, basis, elementPath, "/CircuitElements");
        elementPaletteFront.init(this, basis, elementPath, "/FrontElements");
      }
    }
  }

  public void reloadElementPalettes() {
    // reLoadElementPaletteDipendentFromProjectType();
    Basis basis = getActualBasis();

    elementPaletteCircuit.init(this, basis, elementPath, elementPaletteCircuit.currentDirectory);
    elementPaletteFront.init(this, basis, elementPath, elementPaletteFront.currentDirectory);
  }

  public void projectPaletteAddNewVM(MyNode node) {
    DialogVMName frmVmName = new DialogVMName(this, true);
    frmVmName.setVisible(true);

    if (DialogVMName.result) {
      String filename =
          node.projectPath + node.relativePath + File.separator + DialogVMName.newName + ".vlogic";

      if (!new File(filename).exists()) {
        Basis basis = new Basis(this, FrameMain.elementPath);
        basis.saveToFile(filename, false);
        reloadProjectPanel();
      } else {
        Tools.showMessage(
            this,
            java.util.ResourceBundle.getBundle("VisualLogic/Messages").getString("VM already exist")
                + " : "
                + new File(filename).getName());
      }
    }
  }

  public void dirAdd(MyNode node) {
    String value =
        JOptionPane.showInputDialog(
            this,
            java.util.ResourceBundle.getBundle("VisualLogic/FrameCircuit").getString("NEW_FOLDER"),
            java.util.ResourceBundle.getBundle("VisualLogic/FrameCircuit").getString("FOLDER_NAME"),
            JOptionPane.QUESTION_MESSAGE);

    if (value != null && value.length() > 0) {
      File file = new File(node.projectPath + node.relativePath);
      String projectName = file.getPath();

      new File(projectName + File.separator + value).mkdir();
      reloadProjectPanel();
    }
  }

  public void dirDelete(MyNode node) {
    File file = new File(node.projectPath + node.relativePath);
    String projectName = file.getPath();

    if (Tools.setQuestionDialog(
        this,
        java.util.ResourceBundle.getBundle("VisualLogic/Messages")
                .getString(
                    "Do you really want to delete this Folder with any" + " subFolder and Data? :")
            + " \""
            + file.getName()
            + "\"")) {
      Tools.deleteDirectory(file);

      if (node.getLevel() == 1) {
        int pos = Collections.binarySearch(projects, file.getPath());
        if (pos > -1) {
          projects.remove(pos);
        }
      }

      reloadProjectPanel();
    }
  }

  public void dirRename(MyNode node) {

    if (node != null) {
      File file = new File(node.projectPath + node.relativePath);

      boolean res =
          setQuestionDialogYES_NO_CANCEL(
              this, "All VMs will be closed to rename this project, Do you want to" + " continue?");
      if (res) {
        closeAllVms();

        DialogRename frm = new DialogRename(this, true);
        frm.init(file.getName());
        frm.setVisible(true);

        if (frm.result && frm.newName.length() > 0) {
          String std = file.getParent() + File.separator + frm.newName;

          String newFilename = std;

          String oldFilename = file.getAbsolutePath();

          file.renameTo(new File(std));

          if (node.getLevel() == 1) {
            int pos = Collections.binarySearch(projects, oldFilename);
            if (pos > -1) {
              projects.remove(pos);
              newFilename = new File(std).getAbsolutePath();
              projects.add(newFilename);
            }
          }

          reloadProjectPanel();
        }
      }
    }
  }

  public static final int MODE_NOPE = 0;
  public static final int MODE_COPY_DIR = 1;
  public static final int MODE_CUT_DIR = 2;
  public static final int MODE_COPY_FILE = 3;
  public static final int MODE_CUT_FILE = 4;
  public int mode = MODE_NOPE;
  public String modePath = "";

  public void dirCopy(MyNode node) {
    String projectName = node.projectPath + node.relativePath;

    mode = MODE_COPY_DIR;
    modePath = projectName;
  }

  public void dirCut(MyNode node) {
    String projectName = node.projectPath + node.relativePath;

    mode = MODE_CUT_DIR;
    modePath = projectName;
  }

  public void dirFilePaste(MyNode node) {
    File destFile = new File(node.projectPath + node.relativePath);

    if (modePath.length() > 0) {
      if (mode == MODE_COPY_DIR) {
        if (node.isFolder) {
          try {
            String srcParent = new File(modePath).getParent();
            String srcDirName = modePath.substring(srcParent.length(), modePath.length());
            String dest = Tools.generateNewFileName(destFile.getAbsolutePath() + srcDirName);

            Tools.copy(new File(modePath), new File(dest));
            reloadProjectPanel();
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }
      } else if (mode == MODE_CUT_DIR) {
        if (node.isFolder) {
          try {
            String srcParent = new File(modePath).getParent();
            String srcDirName = modePath.substring(srcParent.length(), modePath.length());
            String dest = Tools.generateNewFileName(destFile.getAbsolutePath() + srcDirName);

            Tools.copy(new File(modePath), new File(dest));
            Tools.deleteDirectory(new File(modePath));
            reloadProjectPanel();
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }
      } else if (mode == MODE_COPY_FILE) {
        if (node.isFolder) {
          try {
            String extension = Tools.getExtension(new File(modePath));
            String filename = Tools.getFileNameWithoutExtension(new File(modePath));
            String destFileName =
                Tools.generateNewFileName(
                    destFile.getAbsolutePath() + File.separator + filename, extension);

            Tools.copy(new File(modePath), new File(destFileName));
            reloadProjectPanel();
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }
      } else if (mode == MODE_CUT_FILE) {
        if (node.isFolder) {
          try {
            String extension = Tools.getExtension(new File(modePath));
            String filename = Tools.getFileNameWithoutExtension(new File(modePath));
            String destFileName =
                Tools.generateNewFileName(
                    destFile.getAbsolutePath() + File.separator + filename, extension);

            Tools.copy(new File(modePath), new File(destFileName));
            new File(modePath).delete();
            reloadProjectPanel();
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }
      }
    }

    mode = MODE_NOPE;
    modePath = "";
  }

  public void fileCopy(MyNode node) {
    String projectName = node.projectPath + node.relativePath;

    mode = MODE_COPY_FILE;
    modePath = projectName;
  }

  public void fileCut(MyNode node) {
    String projectName = node.projectPath + node.relativePath;

    mode = MODE_CUT_FILE;
    modePath = projectName;
  }

  public String searchElement(File file) {
    System.out.println("Element not found : " + file.getName());
    boolean res = false;

    if (res) {
      DialogElementSearchAssistent frm = new DialogElementSearchAssistent(this, true);
      frm.init(file);

      return frm.result;
    }

    return "";
  }

  public void projectPaletteItemSelected(MyNode node) {
    String path = node.projectPath + node.relativePath;

    if (Tools.isExecutableProject(new File(path).list())) {
      openProject(new File(path));
      return;
    }

    String extension = Tools.getExtension(new File(path));
    if (extension != null
        && (extension.equalsIgnoreCase("jpg")
            || extension.equalsIgnoreCase("gif")
            || extension.equalsIgnoreCase("png"))) {
      Tools.openFileImage(this, new File(path));
    } else if (extension != null
        && (extension.equalsIgnoreCase("txt")
            || extension.equalsIgnoreCase("html")
            || extension.equalsIgnoreCase("htm"))) {
      Tools.editFile(this, new File(path));
    } else if (extension != null && extension.equalsIgnoreCase("vlogic")) {
      globalProjectPath = node.projectPath;
      globalPath = path;

      SwingWorker worker =
          new SwingWorker<Object, Object>() {

            public Object doInBackground() {
              Tools.dialogWait = new DialogWait();
              Tools.dialogWait.setVisible(true);

              Basis oBasis = addBasisToVMPanel(globalPath, globalProjectPath, true);

              if (oBasis != null) {
                jPaneVMPanels.setSelectedComponent(oBasis.ownerVMPanel);
              }

              return null;
            }

            protected void done() {
              Tools.dialogWait.dispose();
            }
          };

      worker.execute();
    }
  }

  public void projectPaletteAction(String command, MyNode node) {

    if (command.equalsIgnoreCase("OPENFILE")) {
      String str = node.projectPath + node.relativePath;

      if (str.endsWith("vlogic")) {
        projectPaletteItemSelected(node);
        return;
      }

      try {
        if (java.awt.Desktop.isDesktopSupported()) {

          File myFile = new File(str);
          java.awt.Desktop.getDesktop().open(myFile);
        }
      } catch (IOException ex) {
      }
    } else if (command.equalsIgnoreCase("EDITFILE")) {

      String str = new File(node.projectPath + node.relativePath).getAbsolutePath();

      String ext = Tools.getExtension(new File(str));

      if (str.endsWith("vlogic")) {
        projectPaletteItemSelected(node);
        return;
      }

      if (ext.equalsIgnoreCase("html") || ext.equalsIgnoreCase("htm")) {

        String htmlEditor = settings.getHTMLEditor();

        if (htmlEditor != null && htmlEditor.length() > 0 && new File(htmlEditor).exists()) {
          try {
            Runtime.getRuntime().exec(settings.getHTMLEditor() + " \"" + str + "\"");
            return;
          } catch (IOException ex) {
          }
        }
      }

      if (ext.equalsIgnoreCase("gif")
          || ext.equalsIgnoreCase("jpg")
          || ext.equalsIgnoreCase("png")) {
        if (settings.getGraphicEditor() != null
            && settings.getGraphicEditor().length() > 0
            && new File(settings.getGraphicEditor()).exists()) {
          try {
            Runtime.getRuntime().exec(settings.getGraphicEditor() + " \"" + str + "\"");
            return;
          } catch (IOException ex) {
          }
        }
      }
    } else if (command.equalsIgnoreCase("ADDVM")) {
      projectPaletteAddNewVM(node);
    } else if (command.equalsIgnoreCase("ITEMSELECTED")) {
      projectPaletteItemSelected(node);
    } else if (command.equalsIgnoreCase("FILE_COPY")) {
      fileCopy(node);
    } else if (command.equalsIgnoreCase("FILE_CUT")) {
      fileCut(node);
    } else if (command.equalsIgnoreCase("OPENFILE")) {
      reloadProjectPanel();
    } else if (command.equalsIgnoreCase("RELOAD")) {
      reloadProjectPanel();
    } else if (command.equalsIgnoreCase("DIR_ADD")) {
      dirAdd(node);
    } else if (command.equalsIgnoreCase("DIR_DELETE")) {
      dirDelete(node);
    } else if (command.equalsIgnoreCase("DIR_RENAME")) {
      dirRename(node);
    } else if (command.equalsIgnoreCase("DIR_COPY")) {
      dirCopy(node);
    } else if (command.equalsIgnoreCase("DIR_CUT")) {
      dirCut(node);
    } else if (command.equalsIgnoreCase("PASTE")) {
      dirFilePaste(node);
    } else if (command.equalsIgnoreCase("DISTRIBUTION")) {
      File file = new File(node.projectPath + node.relativePath);
      String projectPath = file.getPath();

      DialogDistributionAssistent frm =
          new DialogDistributionAssistent(this, true, file.getAbsolutePath(), driverPath);

      frm.setVisible(true);

      if (frm.result) {
        String mainVM = frm.mainVM;
        String destPath = frm.destPath;

        if (mainVM.length() > 0 && destPath.length() > 0) {
          createDistribution(projectPath, destPath, mainVM);
        }
      }
    }
  }

  public void projectPaletteProjectClose(MyNode node) {
    if (node != null) {
      File file = new File(node.projectPath + node.relativePath);
      String projectName = file.getPath();

      int pos = Collections.binarySearch(projects, projectName);
      if (pos > -1) {
        projects.remove(pos);
        reloadProjectPanel();
      }
    }
  }

  public void projectPaletteNewSubVM(MyNode node) {
    if (node != null) {
      DialogSubVMAssistent frm = new DialogSubVMAssistent(this, true);
      frm.setVisible(true);
      if (frm.result && frm.vmName.length() > 0) {
        String dir = node.projectPath + "/" + frm.vmName;

        if (!new File(dir).exists()) {
          new File(dir).mkdir();

          try {
            Tools.copy(
                new File(elementPath + File.separator + "nope.html"),
                new File(dir + File.separator + "doc.html"));
            Tools.copy(
                new File(elementPath + File.separator + "nope.html"),
                new File(dir + File.separator + "doc_en.html"));
            Tools.copy(
                new File(elementPath + File.separator + "nope.html"),
                new File(dir + File.separator + "doc_es.html"));
            Tools.copy(
                new File(elementPath + "/element.gif"), new File(dir + "/" + frm.vmName + ".gif"));
          } catch (IOException ex) {
            ex.printStackTrace();
          }

          File file = new File(dir + "/" + "subvm.vlogic");

          generateSubVM(
              node.projectPath, frm.pinsLeft, frm.pinsRight, dir + "/" + frm.vmName + ".vlogic");

          reloadProjectPanel();
        } else {
          Tools.showMessage(
              this,
              java.util.ResourceBundle.getBundle("VisualLogic/Messages")
                      .getString("Folder already exist")
                  + " : "
                  + new File(dir).getName());
        }
      }
    }
  }

  public boolean setQuestionDialogYES_NO_CANCEL(JFrame parent, String s) {
    int res =
        JOptionPane.showOptionDialog(
            parent,
            s,
            java.util.ResourceBundle.getBundle("VisualLogic/Basic").getString("attention"),
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            null,
            null);
    if (res == JOptionPane.NO_OPTION) {
      return false;
    }
    if (res == JOptionPane.CANCEL_OPTION) {
      return false;
    }
    return true;
  }

  public void projectPaletteOpenProject(MyNode node) {
    jmiOpenProjectActionPerformed(null);
  }

  public void projectPaletteDeleteProject(MyNode node) {
    if (node != null) {
      File file = new File(node.projectPath + node.relativePath);
      if (Tools.setQuestionDialog(
          this,
          java.util.ResourceBundle.getBundle("VisualLogic/Messages")
                  .getString(
                      "Do you really want to delete the Project and Remove"
                          + " all Project Files from disc ? :")
              + " \""
              + file.getName()
              + "\"")) {
        if (Tools.deleteDirectory(file)) {
          int pos = Collections.binarySearch(projects, file.getPath());
          if (pos > -1) {
            projects.remove(pos);
            reloadProjectPanel();
          }
        }
      }
    }
  }

  public void projectPaletteProjectProperties(MyNode node) {
    if (node != null) {
      File file = new File(node.projectPath + node.relativePath);

      if (Tools.isExecutableProject(file.list())) {
        return;
      }

      DialogProjectProperties.execute(this, file.getAbsolutePath());
    }
  }

  public void removeTab(String vmFileName) {
    for (int i = 0; i < jPaneVMPanels.getComponentCount(); i++) {
      Component comp = jPaneVMPanels.getComponent(i);

      System.out.println("" + comp.toString());
      if (comp instanceof VMEditorPanel) {
        VMEditorPanel pnl = (VMEditorPanel) comp;
        if (pnl.basis.fileName.equalsIgnoreCase(vmFileName)) {
          jPaneVMPanels.remove(pnl);
          desktop.remove(pnl.basis);
          return;
        }
      }
    }
  }

  public void projectPaletteDeleteVM(MyNode node) {
    if (node != null) {
      File file = new File(node.projectPath + node.relativePath);
      if (Tools.setQuestionDialog(
          this,
          java.util.ResourceBundle.getBundle("VisualLogic/Messages")
                  .getString("Do you really want to delete the VM")
              + " : \""
              + file.getName()
              + "\"")) {
        file.delete();

        removeTab(file.getPath());
        reloadProjectPanel();
      }
    }
  }

  public void projectPaletteRenameVM(MyNode node) {
    if (node != null) {
      File file = new File(node.projectPath + node.relativePath);

      DialogRename frm = new DialogRename(this, true);

      String nm = file.getName();
      String ext = Tools.getExtension(file);

      frm.init(nm.substring(0, nm.length() - ext.length() - 1));

      frm.setVisible(true);

      if (frm.result && frm.newName.length() > 0) {
        String std = file.getParent() + File.separator + frm.newName + "." + ext;

        if (!new File(std).exists()) {
          try {
            file.renameTo(new File(std));
          } catch (Exception ex) {
            Tools.showMessage(
                this,
                java.util.ResourceBundle.getBundle("VisualLogic/Messages")
                    .getString("Could not rename File!"));
            return;
          }

          Basis basis = isBasisInDesktop(file.getAbsolutePath());
          if (basis != null) {
            basis.fileName = std;
            basis.vmFilename = std;

            if (basis.panelLabel != null) {
              int index = getTabIndex(basis.ownerVMPanel);
              if (index > -1) {
                basis.panelLabel.setText(new File(std).getName());
              }
            }
          }

          reloadProjectPanel();
        } else {
          Tools.showMessage(
              java.util.ResourceBundle.getBundle("VisualLogic/Messages")
                  .getString("File already exist."));
        }
      }
    }
  }

  public void projectPaletteAddNewProject(MyNode node) {
    jmiNewProjectActionPerformed(null);
  }

  public Basis getActualBasis() {
    Component comp = jPaneVMPanels.getSelectedComponent();
    if (comp != null) {
      VMEditorPanel editor = (VMEditorPanel) jPaneVMPanels.getSelectedComponent();
      return editor.basis;
    }
    return null;
  }

  public VMObject getVMObject() {
    Component comp = jPaneVMPanels.getSelectedComponent();
    if (comp != null) {
      VMEditorPanel editor = (VMEditorPanel) jPaneVMPanels.getSelectedComponent();
      return editor.getVMObject();
    }
    return null;
  }

  public void onButtonClicken(String[] params) {
    Component comp = jPaneVMPanels.getSelectedComponent();
    if (comp != null) {
      VMEditorPanel editor = (VMEditorPanel) jPaneVMPanels.getSelectedComponent();
      editor.getVMObject().newElement = params;
    }
  }

  public void vmEditorPanelTabChanged(VMObject vmobject) {
    Basis basis = getActualBasis();
    if (basis == null) {
      return;
    }

    jSpinnerDebugDelayTime.setValue(basis.getDebugDelay());
    propertyEditor.vmobject = null;
    propertyEditor.clear();

    if (vmobject == basis.getCircuitBasis()) {
      jPanelElementPalette.removeAll();
      jPanelElementPalette.add(elementPaletteCircuit, BorderLayout.CENTER);
      elementPaletteFront.setVisible(false);
      elementPaletteCircuit.setVisible(true);
    }

    if (vmobject == basis.getFrontBasis()) {
      jPanelElementPalette.removeAll();
      jPanelElementPalette.add(elementPaletteFront, BorderLayout.CENTER);
      elementPaletteFront.setVisible(true);
      elementPaletteCircuit.setVisible(false);
    }

    reLoadElementPaletteDipendentFromProjectType();

    listAllElements();
  }

  public void closeApplication() {
    saveConfiFile();

    String fileName = getUserURL().getFile() + File.separator + "projects.file";
    Tools.saveProjectsFile(new File(fileName), projects);

    if (closeAllVms() == true) {
      System.exit(Tools.appResult);
    }
  }

  public void saveConfiFile() {
    settings.setElementSplitterPosition(jSplitPane3.getDividerLocation());
    settings.setElementSplitterHozPosition(jSplitPane1.getDividerLocation());

    settings.setRightSplitterPos(jPanelHelpWindow.getPreferredSize().width);

    settings.setDocumentation_language(panelDoc.selectedLanguage);

    settings.setStatus(getExtendedState());
    settings.setCircuitWindowLocation(getLocation());
    settings.setCircuitWindowDimension(getSize());

    if (docFrame != null) {
      settings.setDocLocation(docFrame.getLocation());
      settings.setDocDimension(docFrame.getSize());
    }

    try {
      settings.setMainFrameLocation(this.getLocation());
      settings.setMainFrameSize(this.getSize());

      String fileName_xml =
          getUserURL().getFile() + System.getProperty("file.separator") + "config.xml";

      XMLSerializer.write(settings, fileName_xml);
    } catch (Exception e) {

    }
  }

  public Basis isBasisInDesktop(String path) {
    for (int i = 0; i < desktop.size(); i++) {
      Basis basis = desktop.get(i);

      File f1 = new File(basis.fileName);
      File f2 = new File(path);

      if (f1.getAbsolutePath().equalsIgnoreCase(f2.getAbsolutePath())) {
        return basis;
      }
    }

    return null;
  }

  class XJLabel extends JLabel {

    public VMEditorPanel editor;

    public XJLabel() {
      super();
    }
  }

  public void XaddBasisToVMPanel(Basis basis) {
    VMEditorPanel pnl = new VMEditorPanel(this, basis, "", false);

    basis.propertyEditor = propertyEditor;

    String caption = new File(basis.fileName).getName();

    jPaneVMPanels.add(pnl);

    int index = jPaneVMPanels.getTabCount() - 1;
    jPaneVMPanels.setSelectedIndex(index);

    final JPanel content = new JPanel();

    JPanel tab = new JPanel();
    tab.setLayout(new BorderLayout());
    tab.setOpaque(false);

    XJLabel tabLabel = new XJLabel();
    tabLabel.editor = pnl;

    tabLabel.setText(caption + "  ");

    basis.panelLabel = tabLabel;

    javax.swing.ImageIcon closeXIcon =
        new javax.swing.ImageIcon(getClass().getResource("/Images/cross12.png"));
    Dimension closeButtonSize;

    closeButtonSize = new Dimension(closeXIcon.getIconWidth(), closeXIcon.getIconHeight());

    MyButtonX tabCloseButton = new MyButtonX(closeXIcon);
    tabCloseButton.setOpaque(false);
    tabCloseButton.setContentAreaFilled(false);
    tabCloseButton.setBorderPainted(false);

    tabCloseButton.setPreferredSize(closeButtonSize);
    tabCloseButton.panel = pnl;

    ImageIcon icon = new ImageIcon(getClass().getResource("/Images/script16.png"));
    tabLabel.setIcon(icon);
    tabLabel.setBorder(null);
    tab.setBorder(null);
    tabCloseButton.setBorder(null);

    tabCloseButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof MyButtonX) {
              MyButtonX button = (MyButtonX) e.getSource();
              if (button.panel != null) {
                closeVM(button.panel);
              } else {
                System.out.println(
                    "Null Button -"
                        + " VisualLogic.FrameMain$2.actionPerformed(FrameMain.java:898)");
              }
            }
          }
        });

    tab.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

    tab.add(tabLabel, BorderLayout.WEST);
    tab.add(tabCloseButton, BorderLayout.EAST);

    jPaneVMPanels.setTabComponentAt(jPaneVMPanels.getTabCount() - 1, tab);

    basis.saveForUndoRedo();
    basis.setChanged(false);
  }

  public boolean closeVM(VMEditorPanel panel) {
    String titel;
    int res;
    String str = new File(panel.basis.fileName).getName();
    if (panel.basis.isChanged()) {

      String MSG_SAVE_CHANGES_IN_VM =
          java.util.ResourceBundle.getBundle("VisualLogic/FrameCircuit")
              .getString("MSG_SAVE_CHANGES_IN_VM");

      titel = MSG_SAVE_CHANGES_IN_VM + " \"" + str + "\"?";
      res =
          JOptionPane.showOptionDialog(
              this,
              titel,
              java.util.ResourceBundle.getBundle("VisualLogic/Basic").getString("attention"),
              JOptionPane.YES_NO_CANCEL_OPTION,
              JOptionPane.QUESTION_MESSAGE,
              null,
              null,
              null);
      if (res == JOptionPane.CANCEL_OPTION) {
        return false;
      }

      if (res == JOptionPane.YES_OPTION) {
        panel.basis.save();
      }
    }

    if (panel.basis.isRunning()) {
      titel = "VM : \"" + str + "\" is running!\n Stop VM?";
      res =
          JOptionPane.showOptionDialog(
              this,
              titel,
              java.util.ResourceBundle.getBundle("VisualLogic/Basic").getString("attention"),
              JOptionPane.YES_NO_OPTION,
              JOptionPane.QUESTION_MESSAGE,
              null,
              null,
              null);
      if (res == JOptionPane.NO_OPTION) {
        return false;
      }
    }

    panel.basis.stop();
    panel.basis.frameCircuit = null;

    jPaneVMPanels.remove(panel);

    desktop.remove(panel.basis);

    panel.basis.close();
    panel.basis.onDispose();
    panel.basis = null;
    panel = null;

    Runtime.getRuntime().gc();
    return true;
  }

  public void addMessageToConsoleErrorWarnings(String message) {
    if (errorWarnings != null) {
      errorWarnings.setVisible(true);
      errorWarnings.addMessageToConsole(message + "\n");
    }
  }

  public Basis addBasisToVMPanel(String path, String projectPath, boolean basisPanelVisible) {
    Basis Obasis = isBasisInDesktop(path);
    if (Obasis == null) {
      timer.stop();
      propertyEditor.vmobject = null;
      propertyEditor.locked = true;
      Basis basis = new Basis(this, FrameMain.elementPath);
      basis.frameCircuit = this;
      basis.propertyEditor = propertyEditor;
      desktop.add(basis);

      basis.projectPath = projectPath;

      double time1 = System.currentTimeMillis();

      basis.loadFromFile(path, false);

      double time2 = System.currentTimeMillis() - time1;

      basis.isFileLoaded = true;

      // if (basisPanelVisible)
      {
        XaddBasisToVMPanel(basis);
      }

      propertyEditor.locked = false;

      timer.start();

      reLoadElementPaletteDipendentFromProjectType();

      return basis;
    } else {
      timer.start();
      return Obasis;
    }
  }

  public void addBasisToVMPanel(Basis basis) {
    basis.frameCircuit = this;
    basis.propertyEditor = propertyEditor;
    desktop.add(basis);

    XaddBasisToVMPanel(basis);
  }

  public void initApp() {

    jmniUpdate.setVisible(false);

    System.setErr(new PrintStream(new TextAreaStream(errorWarnings)));

    setSize(settings.getCircuitWindowDimension());

    setSize(2000, 2000);
    setPreferredSize(new Dimension(2000, 2000));
    setBackground(Color.white);

    int pos;
    pos = settings.getElementSplitterPosition();
    jSplitPane3.setDividerLocation(pos);

    pos = settings.getElementSplitterHozPosition();
    jSplitPane1.setDividerLocation(pos);

    setLocation(settings.getCircuitWindowLocation());
    setSize(settings.getCircuitWindowDimension());

    String ver = Version.strApplicationVersion + " " + Version.strStatus;

    setTitle(Version.strApplicationTitle + " Version-" + ver.trim());

    File verzeichniss = new File(getUserURL().getFile() + File.separator);

    if (verzeichniss.exists()) {
      File[] files = verzeichniss.listFiles();

      for (int i = 0; i < files.length; i++) {
        String filename = files[i].getName();

        if (filename.endsWith(".tmp")) {
          files[i].delete();
        }
        if (filename.endsWith(".vlogic")) {
          files[i].delete();
        }
      }
    }

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    if (this.getX() < 0 || this.getX() > screenSize.width) {
      this.setLocation(screenSize.width - 250, this.getY());
    }

    if (this.getY() < 0 || this.getY() > screenSize.height) {
      this.setLocation(this.getX(), screenSize.height - 250);
    }

    addWindowListener(
        new java.awt.event.WindowAdapter() {
          public void windowClosing(WindowEvent e) {
            // closeApplication();
          }
        });
  }

  private String createDefString(String path) {
    String result = "";

    result += "ISDIRECTORY     = TRUE\n";
    result += "CAPTION         = Eigene Elemente\n";
    result += "CAPTION_EN      = User defined Elements\n";
    result += "CAPTION_ES      = Elemente definido por el usario\n";
    result += "CAPTION_PR      = Elemento definido pelo usuario\n";
    result += "REDIRECT        = " + path + "\n";
    result += "ICON            = library.png\n";

    return result;
  }

  public void createUserElementSubDirs(String path) {
    path = new File(path).getAbsolutePath();

    File userdefFile1 =
        new File(
            elementPath
                + File.separator
                + "CircuitElements"
                + File.separator
                + "2user-defined"
                + File.separator
                + "definition.def");

    File userdefFile2 =
        new File(
            elementPath
                + File.separator
                + "FrontElements"
                + File.separator
                + "2user-defined"
                + File.separator
                + "definition.def");

    Tools.saveText(
        userdefFile1,
        createDefString(new File(path + File.separator + "CircuitElements").getAbsolutePath()));

    Tools.saveText(
        userdefFile2,
        createDefString(new File(path + File.separator + "FrontElements").getAbsolutePath()));

    new File(path).mkdirs();
    new File(path + File.separator + "CircuitElements").mkdirs();
    new File(path + File.separator + "FrontElements").mkdirs();

    if (!new File(path + File.separator + "CircuitElements" + File.separator + "definition.def")
        .exists()) {
      String src =
          elementPath
              + File.separator
              + "UserElementsTemplate"
              + File.separator
              + "CircuitElements"
              + File.separator
              + "definition.def";
      String dst = path + File.separator + "CircuitElements" + File.separator + "definition.def";
      try {
        Tools.copyFile(new File(src), new File(dst));
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }

    if (!new File(path + File.separator + "CircuitElements" + File.separator + "icon.png")
        .exists()) {
      String src =
          elementPath
              + File.separator
              + "UserElementsTemplate"
              + File.separator
              + "CircuitElements"
              + File.separator
              + "icon.png";
      String dst = path + File.separator + "CircuitElements" + File.separator + "icon.png";
      try {
        Tools.copyFile(new File(src), new File(dst));
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }

    if (!new File(path + File.separator + "FrontElements" + File.separator + "definition.def")
        .exists()) {
      String src =
          elementPath
              + File.separator
              + "UserElementsTemplate"
              + File.separator
              + "FrontElements"
              + File.separator
              + "definition.def";
      String dst = path + File.separator + "FrontElements" + File.separator + "definition.def";
      try {
        Tools.copyFile(new File(src), new File(dst));
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }

    if (!new File(path + File.separator + "FrontElements" + File.separator + "icon.png").exists()) {
      String src =
          elementPath
              + File.separator
              + "UserElementsTemplate"
              + File.separator
              + "FrontElements"
              + File.separator
              + "icon.png";
      String dst = path + File.separator + "FrontElements" + File.separator + "icon.png";
      try {
        Tools.copyFile(new File(src), new File(dst));
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }

    String fileName = getUserURL().getFile() + File.separator + "userdefinedElements.path";
    Tools.saveText(new File(fileName), path);
    settings.setUserdefinedElementsPath(path);
  }

  public void handleUserElementDirectory() {
    String fileName = getUserURL().getFile() + File.separator + "userdefinedElements.path";

    if (!new File(fileName).exists()) {
      String path = DialogUserdefinedElementsHome.execute(this);

      createUserElementSubDirs(path);
    }

    String fn = getUserURL().getFile() + File.separator + "userdefinedElements.path";
    String path = Tools.loadTextFile(new File(fn));
    settings.setUserdefinedElementsPath(path);
  }

  private void copyUserdefElementsRecursive(String elPath, String actualPath) {
    File elFile = new File(elPath);
    File files[] = elFile.listFiles();

    if (!(new File(actualPath).exists())) {
      new File(actualPath).mkdir();
    }
    if (files != null) {
      for (int i = 0; i < files.length; i++) {
        // Always override old Elements!!!
        // It doesn't matter if there exist or not!
        if (files[i].isDirectory()) {
          try {
            String name = files[i].getName();
            // Tools.copy(files[i], new File(actualPath + "\\" + name));
            Tools.copy(files[i], new File(actualPath + File.separator + name));
          } catch (IOException ex) {
            // Logger.getLogger(FrameMain.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
      }
    }
  }

  public void copyUserdefElements() {
    int result =
        JOptionPane.showConfirmDialog(
            this,
            java.util.ResourceBundle.getBundle("VisualLogic/Messages").getString("CopyElements"),
            java.util.ResourceBundle.getBundle("VisualLogic/Messages").getString("Important"),
            JOptionPane.YES_NO_OPTION);

    if (result == JOptionPane.OK_OPTION) {
      // Circuit Elements
      String elPath =
          elementPath + File.separator + "CircuitElements" + File.separator + "user-defined";

      String actualPath =
          Tools.userElementPath + File.separator + "CircuitElements" + File.separator;

      copyUserdefElementsRecursive(elPath, actualPath);

      // FrontPanel Elements
      String elPathFront =
          elementPath + File.separator + "FrontElements" + File.separator + "user-defined";

      String actualPathFront =
          Tools.userElementPath + File.separator + "FrontElements" + File.separator;

      copyUserdefElementsRecursive(elPathFront, actualPathFront);
    }
  }

  private ArrayList<String> listDirectory(String dir) {
    File directory = new File(dir);
    ArrayList<String> files = new ArrayList<>();
    // get all the files from a directory
    File[] fList = directory.listFiles();
    if (fList != null) {
      for (File file : fList) {

        if (!file.isDirectory()) {

          if (file.exists()) {
            files.add(file.getName());
          }
        }
      }
    }

    return files;
  }

  private void initDocs() {

    jmnuDocs.removeAll();
    new File(elementPath + "/Documentations").mkdir();

    // List all Documents in their DIrectories
    File directory = new File(elementPath + "/Documentations");

    ArrayList<String> files = new ArrayList<>();
    // get all the files from a directory
    File[] fList = directory.listFiles();
    if (fList != null) {
      for (File file : fList) {

        if (file.isDirectory()) {

          JMenu menu = new JMenu();
          String caption = Tools.getInfoXMLCaption(file);
          menu.setText(caption);
          jmnuDocs.add(menu);

          menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/book16.png")));
          ArrayList<String> files2 = listDirectory(file.getAbsolutePath());

          for (String file2 : files2) {
            MyMenuItem item = new MyMenuItem();

            if (file2.endsWith(".pdf")) {
              item.setText(file2);

              menu.add(item);
              item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/book16.png")));

              // item.path = file.getAbsolutePath() + "/" + file2;
              item.path = file.getAbsolutePath() + File.separator + file2;

              item.addActionListener(
                  new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {

                      try {
                        if (java.awt.Desktop.isDesktopSupported()) {
                          MyMenuItem item = (MyMenuItem) evt.getSource();
                          File myFile = new File(item.path);
                          java.awt.Desktop.getDesktop().open(myFile);
                        }
                      } catch (IOException ex) {
                        // no application registered for PDFs
                      }
                    }
                  });
            }
          }
        }
      }
    }
  }

  public void FormDTLengend() {
    Image image = this.getIconImage();
    setIconImage(image);

    String filename = "types_de.html";
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

    java.awt.event.ActionListener actionListener =
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
            dispose();
          }
        };

    javax.swing.KeyStroke stroke = javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
    rootPane.registerKeyboardAction(
        actionListener, stroke, javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW);
  }

  public FrameMain(String args[]) {

    try {
      iconImage =
          Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/icon16.png"));
      Image iconImage32 =
          Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/icon32.png"));
      Image iconImage64 =
          Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/icon64.png"));

      ArrayList<Image> images = new ArrayList<>();

      images.add(iconImage);
      images.add(iconImage32);
      images.add(iconImage64);

      setIconImages(images);

      // for mac os!
      // Application application = Application.getApplication();
      // application.setDockIconImage(iconImage64);

    } catch (Exception ex) {
      Tools.showMessage("Error : " + ex.toString());
    }

    driverPath = elementPath + File.separator + "Drivers";

    LoadConfigFile();

    Tools.settings = settings;

    String fileName = getUserURL().getFile() + File.separator + "projects.file";

    if (!new File(fileName).exists()) {
      try {
        new File(fileName).createNewFile();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }

    ArrayList<String> projectsX = Tools.loadProjectsFile(new File(fileName));

    projects = new ArrayList<>();

    String vms = "VirtualMachines";

    projects.add(new File(elementPath + File.separator + vms).getAbsolutePath());

    for (String project : projectsX) {
      File f = new File(project);

      if (!f.getName().equalsIgnoreCase(vms)) {
        projects.add(f.getAbsolutePath());
      }
    }

    handleUserElementDirectory();

    Tools.userElementPath = settings.getUserdefinedElementsPath();

    fileName = getUserURL().getFile() + File.separator + "myopenlab.path";

    if (!new File(fileName).exists()) {
      try {
        new File(fileName).createNewFile();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }

    String myopenlab = "";

    String value = Tools.loadTextFile(new File(fileName));
    if (value.length() > 0) {
      myopenlab = value;
    }

    String myopenlabX = new File(elementPath).getAbsolutePath();

    if (!myopenlab.equalsIgnoreCase(myopenlabX)) {
      Tools.saveText(new File(fileName), myopenlabX);
    }

    System.out.println("MyOpenLab Path=" + myopenlabX);

    try {
      String OS_arch = System.getProperty("os.arch"); // ARM Raspberry Pi
      String OS_name = System.getProperty("os.name"); // Linux Raspberry PI

      System.out.println("OS_Name=" + OS_name + "_OS_Arch=" + OS_arch);

      if (OS_arch.equalsIgnoreCase("arm")
          || OS_name.contains("Linux")
          || OS_name.contains("linux")
          || OS_name.contains("LINUX")
          || OS_name.contains("mac")
          || OS_name.contains("MAC")
          || OS_name.contains("Mac")) {
        // System.setProperty( "sun.java2d.xrender","True"); //sun.java2d.xrender=True
        // xrender Intended use: To enable the XRender-based Java 2D rendering pipeline for
        // modern
        // X11-based desktops, offering improved graphics performance.

        // System.setProperty( "sun.java2d.d3d","false"); //sun.java2d.d3d=false //d3d
        // Intended use: To turn off the Java 2D system's use of Direct3D.
        // https://docs.oracle.com/javase/7/docs/technotes/guides/2d/flags.html#xrender
      }
    } catch (Exception e) {
      System.out.println(e);
    }

    Font f = new Font("Dialog", Font.PLAIN, 13);
    UIManager.put("Menu.font", f);
    UIManager.put("MenuItem.font", f);

    initComponents();

    jButtonNewProject_A.setMnemonic(KeyEvent.VK_A);
    jButtonOpenProject_B.setMnemonic(KeyEvent.VK_B);
    jButtonSave_C.setMnemonic(KeyEvent.VK_C);
    jButtonUndo_D.setMnemonic(KeyEvent.VK_D);
    jButtonRedo_E.setMnemonic(KeyEvent.VK_E);
    jButtonRefreshVM_F.setMnemonic(KeyEvent.VK_F);
    jButtonOptions_G.setMnemonic(KeyEvent.VK_G);
    jButtonVariables_H.setMnemonic(KeyEvent.VK_H);
    jButtonWireLegends_I.setMnemonic(KeyEvent.VK_I);
    jButtonStart_J.setMnemonic(KeyEvent.VK_J);
    jButtonDebug_K.setMnemonic(KeyEvent.VK_K);
    jButtonStop_L.setMnemonic(KeyEvent.VK_L);
    jButtonPause_M.setMnemonic(KeyEvent.VK_M);
    jButtonResume_N.setMnemonic(KeyEvent.VK_N);
    jButtonStep_O.setMnemonic(KeyEvent.VK_O);
    jButtonAnalogWindow_P.setMnemonic(KeyEvent.VK_P);
    jButtonDigitalWindow_Q.setMnemonic(KeyEvent.VK_Q);
    jButtonTestPointWin_R.setMnemonic(KeyEvent.VK_R);
    jButtonConsoleOut_S.setMnemonic(KeyEvent.VK_S);
    jButtonRepository_T.setMnemonic(KeyEvent.VK_T);
    jButtonAbout_U.setMnemonic(KeyEvent.VK_U);

    panelDoc = new PanelDokumentation();
    jPanel9.add(panelDoc, BorderLayout.CENTER);

    initDocs();

    jmiSaveAsModul.setVisible(false);

    initApp();

    // Check version
    System.out.println(settings.getVersion());

    settings.setVersion(Version.strApplicationVersion);

    // Load Drivers from "Drivers" Directory"
    Tools.driverManager = new DriverManager(this);
    NewSerialDriverManager.NewDriverManager(this); // v3.12.0

    List<Path> drivers = Tools.listDrivers(driverPath);

    drivers.stream().forEach(driver -> Tools.driverManager.registerDriver(driver));

    setLocation(settings.getCircuitWindowLocation());
    setSize(settings.getCircuitWindowDimension());

    if (settings.getRightSplitterPos() < 10) {
      settings.setRightSplitterPos(10);
    }

    jPanelHelpWindow.setPreferredSize(new Dimension(settings.getRightSplitterPos(), 10));
    panelDoc.setSelectedLanguage(settings.getDocumentation_language());

    int xx = settings.getRightSplitterPos();

    if (xx < 20) {
      jButton13.setIcon(
          new javax.swing.ImageIcon(getClass().getResource("/Images/arrow-black-left.png")));
    } else {
      jButton13.setIcon(
          new javax.swing.ImageIcon(getClass().getResource("/Images/arrow-black-right.png")));
    }

    jButton13.updateUI();

    if (args.length >= 2 && args[2].equalsIgnoreCase("runtime=true")) {

    } else {
      activate_DocFrame(null);
    }

    SpinnerNumberModel model =
        new SpinnerNumberModel(
            Integer.valueOf(100), Integer.valueOf(0), Integer.valueOf(5000), Integer.valueOf(1));
    jSpinnerDebugDelayTime.setModel(model);
    JSpinner.NumberEditor editor = new JSpinner.NumberEditor(jSpinnerDebugDelayTime, "####0000");
    jSpinnerDebugDelayTime.setEditor(editor);

    JLayeredPane mnu = getLayeredPane();

    layedLabel.setFont(new Font("Dialog", 1, 13));
    layedLabel.setLocation(0, 0);
    layedLabel.setSize(0, 0);
    mnu.add(layedLabel);

    propertyEditor = new PropertyEditor(this);
    jTabPropertyEditor.addTab(
        java.util.ResourceBundle.getBundle("VisualLogic/FrameCircuit").getString("Property-Editor"),
        propertyEditor);

    projectPalette1 = new ProjectPalette(this);
    jPanelProjectExplorer.add(projectPalette1);

    elementPaletteCircuit = new ElementPalette(this);
    elementPaletteCircuit.frameCircuit = this;
    elementPaletteFront = new ElementPalette(this);
    elementPaletteFront.frameCircuit = this;

    elementPaletteCircuit.init(this, null, elementPath, "/CircuitElements");
    elementPaletteFront.init(this, null, elementPath, "/FrontElements");

    jPanelElementPalette.add(elementPaletteCircuit);

    reloadProjectPanel();

    startButtonHandler();
  }

  public void removePinDescription() {
    JLabel lbl = layedLabel;
    lbl.setLocation(0, 0);
    lbl.setSize(0, 0);
    lbl.setOpaque(false);
    lbl.setText("");
  }

  public void showPinDescription(JPin pin) {
    try {
      String desc = pin.getDescription();
      desc += " (" + VSDataType.getDataTypeShortCut(pin.dataType) + ")";
      JLabel lbl = layedLabel;

      lbl.setText(desc);

      Point p = getMousePosition();

      FontMetrics fm = lbl.getFontMetrics(lbl.getFont());
      int xx = fm.stringWidth(desc);
      lbl.setLocation(p.x + 10, p.y - fm.getHeight() * 2);
      lbl.setSize(xx, fm.getHeight());
      lbl.setOpaque(true);

      lbl.updateUI();
    } catch (Exception e) {
      System.out.println("FrameMain Line 1624 Error");
      // This error is caused on MAC OS Jdescription does not work
    }
  }

  public void startButtonHandler() {
    timer =
        new javax.swing.Timer(
            300,
            new ActionListener() {

              public void actionPerformed(ActionEvent evt) {
                // settings.rightSplitterPos=jTabbedPane1.getWidth()+15;

                Basis basis = getActualBasis();

                if (basis == null) {
                  jButtonVariables_H.setEnabled(false);
                  jmiStart.setEnabled(false);
                  jmiStop.setEnabled(false);
                  jButtonStart_J.setEnabled(false);
                  jButtonStop_L.setEnabled(false);
                  jButtonPause_M.setEnabled(false);
                  jButtonResume_N.setEnabled(false);
                  jmiPause.setEnabled(false);
                  jmiResume.setEnabled(false);

                  jButtonStep_O.setEnabled(false);
                  jmiStep.setEnabled(false);
                  jButtonRefreshVM_F.setEnabled(false);
                  jButtonUndo_D.setEnabled(false);
                  jButtonRedo_E.setEnabled(false);
                  jButtonSave_C.setEnabled(false);
                  jButtonDebug_K.setEnabled(false);

                  jButtonTestPointWin_R.setEnabled(false);
                  jButtonDigitalWindow_Q.setEnabled(false);
                  jButtonAnalogWindow_P.setEnabled(false);
                  jSpinnerDebugDelayTime.setEnabled(false);
                  jButtonConsoleOut_S.setEnabled(false);

                  // elementPaletteCircuit.setEnabled(false);
                  // elementPaletteFront.setEnabled(false);
                  jmniDefineVariables.setEnabled(false);
                  jmniPasswordProtection.setEnabled(false);
                  jmniDeletePasswordProtection.setEnabled(false);
                  jmiEigenschaten.setEnabled(false);

                  jmiUndo.setEnabled(false);
                  jmiRedo.setEnabled(false);
                  jmiCut.setEnabled(false);
                  jmiCopy.setEnabled(false);
                  jmiPaste.setEnabled(false);
                  jmiSelectAny.setEnabled(false);

                  jmiSave.setEnabled(false);
                  jmniSaveAsSingleVM.setEnabled(false);
                  // jmniSaveAs.setEnabled(false);
                  jmiSaveAsModul.setEnabled(false);
                  jmiSaveAsJPG.setEnabled(false);

                  jmiVariableWatcher.setEnabled(false);
                  jmiShowAnalogWindow.setEnabled(false);
                  jmiShowDigitalWindow.setEnabled(false);
                  jmiShowTestpointWindow.setEnabled(false);

                  return;
                }

                jmiVariableWatcher.setEnabled(true);
                jmiShowAnalogWindow.setEnabled(true);
                jmiShowDigitalWindow.setEnabled(true);
                jmiShowTestpointWindow.setEnabled(true);

                // elementPaletteCircuit.setEnabled(true);
                // elementPaletteFront.setEnabled(true);
                jmniDeletePasswordProtection.setEnabled(true);
                jmiEigenschaten.setEnabled(true);
                jmiUndo.setEnabled(true);
                jmiRedo.setEnabled(true);

                jmiCut.setEnabled(true);
                jmiCopy.setEnabled(true);
                jmiPaste.setEnabled(true);
                jmiSelectAny.setEnabled(true);

                jmiSave.setEnabled(true);
                jmniSaveAsSingleVM.setEnabled(true);
                // jmniSaveAs.setEnabled(true);
                jmiSaveAsModul.setEnabled(true);
                jmiSaveAsJPG.setEnabled(true);

                jmniDefineVariables.setEnabled(true);
                jmniPasswordProtection.setEnabled(true);

                jButtonConsoleOut_S.setEnabled(true);
                jSpinnerDebugDelayTime.setEnabled(true);
                jButtonTestPointWin_R.setEnabled(true);
                jButtonDigitalWindow_Q.setEnabled(true);
                jButtonAnalogWindow_P.setEnabled(true);

                if (basis != null) {
                  if (basis.projectPath.trim().length() > 0) {
                    ProjectProperties props = Tools.openProjectFile(new File(basis.projectPath));
                    if (props.projectType.equalsIgnoreCase("SPS")) {
                      jButtonDebug_K.setEnabled(false);
                    } else {
                      jButtonDebug_K.setEnabled(true);
                    }
                  }
                }

                jButtonVariables_H.setEnabled(true);
                jButtonRefreshVM_F.setEnabled(true);
                jButtonUndo_D.setEnabled(true);
                jButtonRedo_E.setEnabled(true);
                jButtonSave_C.setEnabled(true);

                if (basis.isLoading()) {
                  return;
                }

                basis.getFrontBasis().sortSubPanels();

                basis.getCircuitBasis().adjustAllElemnts();
                basis.getFrontBasis().adjustAllElemnts();

                basis.getCircuitBasis().checkPins();

                Element element = getVMObject().getSelectedElement();
                if (element != null) {
                  if (element != oldElement) {
                    comboIsEditing = true;
                    jComboBoxElementList.setSelectedItem(element);
                    comboIsEditing = false;
                  }
                  oldElement = element;
                }

                /*if (getVMObject().getElementCount()!=oldElementCount)
                {
                oldElementCount=getVMObject().getElementCount();
                }*/

                jButtonStart_J.setEnabled(true);

                jmiSave.setEnabled(true);
                // jmiSaveAsModul.setEnabled(true);
                jmiSelectAny.setEnabled(true);
                jmiStart.setEnabled(true);
                // jButtonDebug.setEnabled(true);
                jmiStop.setEnabled(true);

                jButtonRefreshVM_F.setEnabled(true);

                jButtonPause_M.setEnabled(true);
                jButtonResume_N.setEnabled(true);
                jmiPause.setEnabled(true);
                jmiResume.setEnabled(true);
                jmiStep.setEnabled(true);

                // jComboBox1.setEnabled(true);
                // jComboBox1.setSelectedItem(""+getVMObject().owner.getDelay());
                if (getVMObject().owner.getUndoPointer() > 1) {
                  jmiUndo.setEnabled(true);
                  jButtonUndo_D.setEnabled(true);
                } else {
                  jmiUndo.setEnabled(false);
                  jButtonUndo_D.setEnabled(false);
                }

                if (getVMObject().owner.getUndoPointer()
                    < getVMObject().owner.getUndoHistorySize()) {
                  jmiRedo.setEnabled(true);
                  jButtonRedo_E.setEnabled(true);
                } else {
                  jmiRedo.setEnabled(false);
                  jButtonRedo_E.setEnabled(false);
                }

                if (getVMObject().isRunning()) {
                  jmiStart.setEnabled(false);
                  jmiStop.setEnabled(true);
                  jButtonStart_J.setEnabled(false);
                  jButtonDebug_K.setEnabled(false);
                  jButtonStop_L.setEnabled(true);

                  jButtonStep_O.setEnabled(true);
                  jmiStep.setEnabled(true);
                  // setBarsEnabled(false);

                  jButtonRefreshVM_F.setEnabled(false);

                  if (getVMObject().isPaused()) {
                    jButtonPause_M.setEnabled(false);
                    jButtonResume_N.setEnabled(true);
                    jmiPause.setEnabled(false);
                    jmiResume.setEnabled(true);

                    jButtonStep_O.setEnabled(true);
                    jmiStep.setEnabled(true);
                  } else {
                    jmiPause.setEnabled(true);
                    jmiResume.setEnabled(false);

                    jButtonPause_M.setEnabled(true);
                    jButtonResume_N.setEnabled(false);

                    jButtonStep_O.setEnabled(false);
                    jmiStep.setEnabled(false);
                  }
                } else {
                  jmiStart.setEnabled(true);
                  jmiStop.setEnabled(false);
                  jButtonStart_J.setEnabled(true);
                  jButtonStop_L.setEnabled(false);
                  jButtonPause_M.setEnabled(false);
                  jButtonResume_N.setEnabled(false);
                  jmiPause.setEnabled(false);
                  jmiResume.setEnabled(false);

                  jButtonStep_O.setEnabled(false);
                  jmiStep.setEnabled(false);
                }
              }
            });

    timer.start();

    listAllElements();
  }

  private boolean comboIsEditing = false;

  public void listAllElements() {

    VMObject vmObject = getVMObject();
    if (vmObject != null) {
      comboIsEditing = true;
      jComboBoxElementList.removeAllItems();

      Element element = null;
      for (int i = 0; i < vmObject.getElementCount(); i++) {
        element = vmObject.getElement(i);
        jComboBoxElementList.addItem(element);
      }
      comboIsEditing = false;
    }
  }

  private ArrayList<String> selectedPaths = new ArrayList<String>();

  public void recursionSaveProjectStatus(MyNode node) {
    if (node instanceof MyNode) {
      MyNode nodex = (MyNode) node;

      if (nodex.isExpanded) {
        File file = new File(node.projectPath + node.relativePath);
        String path = file.getPath();

        selectedPaths.add(path);
        System.out.println("" + path);
      }
      recursionSaveProjectStatus((MyNode) nodex.getNextNode());
    }
  }

  public boolean isPathInSelectedPaths(String path) {
    for (int i = 0; i < selectedPaths.size(); i++) {
      String str = selectedPaths.get(i);

      if (str.equalsIgnoreCase(path)) {
        return true;
      }
    }

    return false;
  }

  public void saveProjectPanelStatus() {
    selectedPaths.clear();
    TreeModel model = projectPalette1.jTree1.getModel();
    recursionSaveProjectStatus((MyNode) model.getRoot());
  }

  public void recursionRestoreProjectStatus() {
    for (int i = 0; i < projectPalette1.jTree1.getRowCount(); i++) {
      TreePath selPath = projectPalette1.jTree1.getPathForRow(i);

      int c = selPath.getPathCount();
      String path = selPath.getPath()[c - 1].toString();

      if (isPathInSelectedPaths(path)) {
        projectPalette1.jTree1.expandRow(i);
      }
    }
  }

  public void reloadProjectPanel() {
    saveProjectPanelStatus();
    projectPalette1.init();

    Collections.sort(projects);
    for (String projectName : projects) {
      projectPalette1.listAllFilesFromProjectPath(projectName);
    }

    recursionRestoreProjectStatus();
    projectPalette1.expandRow(0);
  }

  public static void setLookAndFeel() {

    String nativeLF = UIManager.getSystemLookAndFeelClassName();

    try {
      // Flat Light Theme
      UIManager.setLookAndFeel(new FlatLightLaf());

      // Flat Dark Theme
      // UIManager.setLookAndFeel(new FlatDarkLaf());

      // Flat IntelliJ Theme
      // UIManager.setLookAndFeel(new FlatIntelliJLaf());

      // Flat Darcula
      // UIManager.setLookAndFeel(new FlatDarculaLaf());

      // UIManager.put("Panel.background", new Color(214, 217, 223));

    } catch (Exception e) {
      // If Nimbus is not available, you can set the GUI to another look and feel.
      try {

        // Get the theme from installed LookAndFeels
        // GetLookAndFeel();

        // Specify the theme to use by defining the THEME constant
        // Valid values are: "DefaultMetal", "Ocean",  and "Test"
        // MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
        // UIManager.setLookAndFeel(nativeLF);

        UIManager.setLookAndFeel(nativeLF);

      } catch (ClassNotFoundException
          | InstantiationException
          | IllegalAccessException
          | UnsupportedLookAndFeelException ex) {

      }
    }
  }

  public void removeVMPanel(VMEditorPanel panel) {
    jPaneVMPanels.remove(panel);
    desktop.remove(panel.basis);
  }

  public URL getUserURL() {
    return userURL;
  }

  public void LoadConfigFile() {

    boolean config_file_loaded = false;

    // Alte Config.xml l�schen!
    String fileName = getUserURL().getFile() + System.getProperty("file.separator") + "Config.conf";
    File fconf = new File(fileName);
    fconf.delete();

    String fileNameXML =
        getUserURL().getFile() + System.getProperty("file.separator") + "config.xml";
    File fxml = new File(fileNameXML);
    if (fxml.exists()) {
      try {
        XMLDecoder d =
            new XMLDecoder(new BufferedInputStream(new FileInputStream(fxml.getAbsolutePath())));
        settings = (Settings) d.readObject();
        d.close();

        config_file_loaded = true;
      } catch (Exception ioe) {
        choiceLanguage(this);
      }
    }

    if (!config_file_loaded) {
      choiceLanguage(this);
    }

    this.setLocation(settings.getMainFrameLocation());

    if (settings.getLanguage().equalsIgnoreCase("de_DE")) {
      Locale.setDefault(new Locale("de", "DE"));
    }
    if (settings.getLanguage().equalsIgnoreCase("en_US")) {
      Locale.setDefault(new Locale("en", "US"));
    }
    if (settings.getLanguage().equalsIgnoreCase("es_ES")) {
      Locale.setDefault(new Locale("es", "ES"));
    }

    errorWarnings = new FrameErrorWarnings();

    System.out.println("Locale=" + settings.getLanguage());
  }

  public static String arg1;

  public static String getArg1() {
    return arg1;
  }

  public static void handleLicense() {
    // String licenseFile = FrameMain.userURL.getPath() + "/Licences1.Accepted";
    String licenseFile = FrameMain.userURL.getPath() + File.separator + "Licences1.Accepted";
    File file = new File(licenseFile);

    boolean licencesAccepted = false;

    if (file.exists()) {
      licencesAccepted = true;
    } else {
      licencesAccepted = false;
    }

    if (licencesAccepted == false) {
      DialogLicence frmLicence = new DialogLicence(null, true);
      frmLicence.setVisible(true);
      if (frmLicence.result) {
        try {
          file.createNewFile();
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      } else {
        System.exit(0);
      }
    }
  }

  public static void setUIFont(javax.swing.plaf.FontUIResource f) {
    java.util.Enumeration keys = UIManager.getDefaults().keys();
    while (keys.hasMoreElements()) {
      Object key = keys.nextElement();
      Object value = UIManager.get(key);
      if (value != null && value instanceof javax.swing.plaf.FontUIResource) {
        UIManager.put(key, f);
      }
    }
  }

  /** @param args the command line arguments */
  public static void main(String args[]) {

    setUIFont(new javax.swing.plaf.FontUIResource("Dialog", Font.PLAIN, 13)); // Application Font

    SplashScreen splash = SplashScreen.getSplashScreen();
    if (splash != null) {
      Graphics2D g = (Graphics2D) splash.createGraphics();
      Rectangle size = splash.getBounds();
      g.setComposite(AlphaComposite.Clear);
      g.fillRect(0, 0, size.width, size.height);
      g.setPaintMode();
    }

    try {
      FrameMain.userURL =
          new URL(
              "file:"
                  + System.getProperty("user.home")
                  + System.getProperty("file.separator")
                  + "VisualLogic");
    } catch (MalformedURLException ex) {
      ex.printStackTrace();
    }
    setLookAndFeel();

    Tools.appResult = 0;

    File file = new File(FrameMain.userURL.getPath());

    if (!file.exists()) {
      boolean success = file.mkdir();
      if (!success) {
        Tools.showMessage(
            java.util.ResourceBundle.getBundle("VisualLogic/Messages")
                    .getString("Fehler : Verzeichniss konnte nicht erzeugt werden")
                + " : "
                + userURL.getPath());
      }
    }

    handleLicense();

    if (args.length > 0) {
      for (int i = 0; i < args.length; i++) {
        System.out.println("Arg[" + i + "]=" + args[i]);
      }

      FrameMain.elementPath = args[0];

      Tools.elementPath = FrameMain.elementPath;
    }

    frm = new FrameMain(args);

    if (args.length >= 2) {
      System.out.println("Arg[1]=" + args[1]);

      arg1 = args[1];
      java.awt.EventQueue.invokeLater(
          new Runnable() {

            public void run() {
              String projectPath = getArg1();

              ProjectProperties props = Tools.openProjectFile(new File(projectPath));
              frm.openFileAsFront(projectPath, props.mainVM);
            }
          });
    } else {
      java.awt.EventQueue.invokeLater(
          new Runnable() {

            public void run() {
              frm.setVisible(true);
            }
          });
    }
  }

  public void openFileAsFront(String projectPath, String vmName) {
    globalPath = projectPath + File.separator + vmName;
    globalProjectPath = projectPath;
    SwingWorker<Object, Object> worker =
        new SwingWorker<Object, Object>() {

          public Object doInBackground() {
            Tools.dialogWait = new DialogWait();
            Tools.dialogWait.setVisible(true);
            Tools.dialogWait.setLocation(
                Tools.dialogWait.getLocation().x, Tools.dialogWait.getLocation().y - 150);

            frontMode = true;
            Basis basis = addBasisToVMPanel(globalPath, globalProjectPath, true);

            basis.startInFrontMode = true;
            basis.panelMode = true;
            basis.getFrontBasis().sortSubPanels();

            basis.getCircuitBasis().adjustAllElemnts();
            basis.getFrontBasis().adjustAllElemnts();

            basis.getCircuitBasis().checkPins();

            basis.getCircuitBasis().ProcessPinDataType();

            basis.start(false);
            return null;
          }

          protected void done() {
            Tools.dialogWait.dispose();
          }
        };

    worker.execute();
  }

  private void getLookAndFeel() {
    for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {

      System.out.println("GetLookAndFeels: " + info.getName());

      if ("Metal".equals(info.getName())) {

        MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());

        UIManager.put("CheckBox.background", new Color(255, 255, 255));
        UIManager.put("ComboBox.background", new Color(233, 236, 242));
        UIManager.put("ComboBox.buttonBackground", Color.white);
        UIManager.put("ComboBox.buttonDarkShadow", Color.gray);
        UIManager.put("ComboBox.buttonHighlight", new Color(247, 248, 250));
        UIManager.put("ComboBox.buttonShadow", Color.darkGray);
        UIManager.put("ComboBox.disabledBackground", Color.lightGray);
        UIManager.put("ComboBox.disabledForeground", Color.white);
        UIManager.put("ComboBox.foreground", new Color(0, 0, 51));
        UIManager.put("ComboBox.selectionBackground", new Color(191, 98, 4)); // 115,164,209
        UIManager.put("ComboBox.selectionForeground", Color.WHITE);

        UIManager.put("ComboBox.font", new Font("Dialog", 1, 13));

        UIManager.put("Tree.font", new Font("Dialog", 0, 13));
        UIManager.put("ToolTip.font", new Font("Dialog", 1, 13));
        UIManager.put("TextPane.font", new Font("Dialog", 1, 13));
        UIManager.put("TextField.font", new Font("Dialog", 1, 13));
        UIManager.put("ToolTip.foreground", new Color(0, 0, 51));
        UIManager.put("TextField.foreground", new Color(0, 0, 51));
        UIManager.put("TextPane.foreground", new Color(0, 0, 51));
        UIManager.put("TextPane.background", new Color(214, 217, 223));

        UIManager.put("SplitPane.background", new Color(214, 217, 223));
        UIManager.put("Separator.foreground", new Color(214, 217, 223));

        UIManager.put("TextPane.inactiveBackground", new Color(214, 217, 223));
        UIManager.put("ToolTip.background", new Color(255, 242, 181));

        UIManager.put("controltHighlight", new Color(233, 236, 242));
        UIManager.put("controlLtHighlight", new Color(247, 248, 250));

        UIManager.put("Button.opaque", true);
        UIManager.put("ToggleButton.highlight", Color.orange);
        UIManager.put("ToggleButton.light", Color.yellow);
        UIManager.put("ToggleButton.border", BorderFactory.createRaisedBevelBorder());
        UIManager.put("Button.border", BorderFactory.createRaisedBevelBorder());
        UIManager.put(
            "RadioButton.border",
            BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(), BorderFactory.createRaisedBevelBorder()));
        UIManager.put("ScrollPane.border", BorderFactory.createRaisedBevelBorder());
        UIManager.put("ScrollPane.background", Color.WHITE);
        UIManager.put("ScrollPane.foreground", Color.WHITE);

        UIManager.put("ToolBar.border", BorderFactory.createEmptyBorder());
        UIManager.put("Panel.border", BorderFactory.createEmptyBorder());
        UIManager.put("SplitPane.border", BorderFactory.createEmptyBorder());

        UIManager.put("Tree.background", new Color(255, 255, 255)); // 214,217,223
        UIManager.put("Tree.textBackground", new Color(255, 255, 255));
        UIManager.put("Tree.font", new Font("Dialog", 0, 13));
        UIManager.put("List.font", new Font("Dialog", 1, 13));
        UIManager.put("TabbedPane.font", new Font("Dialog", 1, 13));
        UIManager.put("PopupMenu.font", new Font("Dialog", 0, 13));
        UIManager.put("MenuItem.font", new Font("Dialog", 0, 13));
        UIManager.put("Menu.font", new Font("Dialog", 0, 13));
        UIManager.put("MenuBar.font", new Font("Dialog", 0, 13));
        UIManager.put("MenuBar.disabledForeground", Color.DARK_GRAY);
        UIManager.put("Menu.selectionForeground", Color.white);
        UIManager.put("MenuBar.selectionForeground", Color.white);
        UIManager.put("MenuItem.selectionForeground", Color.white);
        UIManager.put("Menu.disabledForeground", Color.DARK_GRAY);
        UIManager.put("MenuItem.disabledForeground", Color.DARK_GRAY);

        UIManager.put("Table.foreground", new Color(0, 0, 51));
        UIManager.put("Tree.foreground", new Color(0, 0, 51));
        UIManager.put("List.background", Color.white);
        UIManager.put("ScrollBar.squareButtons", false);

        UIManager.put("TableHeader.background", new Color(214, 217, 223));
        UIManager.put("TableHeader.foreground", new Color(0, 0, 51));
        UIManager.put("TableHeader.font", new Font("Dialog", 1, 13));

        UIManager.put("Table.gridColor", new Color(51, 98, 140));

        UIManager.put("Table.background", Color.white);
        UIManager.put("List.dropLineColor", Color.red);
        UIManager.put("Table.dropLineColor", Color.red);
        UIManager.put("List.selectionBackground", new Color(115, 164, 209));
        UIManager.put("Table.selectionBackground", new Color(115, 164, 209));
        // UIManager.put("List.focusCellHighlightBorder",Color.green);
        // UIManager.put("Table.focusCellHighlightBorder",Color.green);
        UIManager.put("List.selectionForeground", Color.WHITE);
        UIManager.put("Table.selectionForeground", Color.WHITE);
        // UIManager.put("TableHeader.cellBorder",false);

        UIManager.put("Panel.opaque", true);
        UIManager.put("Panel.background", new Color(214, 217, 223));
        UIManager.put("Panel.foreground", new Color(214, 217, 223));
        UIManager.put("Separator.foreground", new Color(214, 217, 223));
        // UIManager.put("TabbedPane.tabsOverlapBorder",true);
        UIManager.put("TabbedPane.focus", new Color(51, 98, 140));
        UIManager.put("TabbedPane.shadow", Color.black);
        UIManager.put("TabbedPane.highlight", new Color(51, 98, 140));
        UIManager.put("TabbedPane.light", Color.WHITE);
        // UIManager.put("TabbedPane.leftTabInsets",new Color(51,98,140));
        // UIManager.put("TabbedPane.darkShadow",new Color(191,98,4));
        UIManager.put(
            "TabbedPane.background",
            new Color(115, 164, 209)); // Relleno de la Pesta�a Deshabilitada

        // UIManager.put("ScrollBar.background",new Color(214,217,223));
        UIManager.put("ScrollBar.background", new Color(233, 236, 242));
        UIManager.put("ScrollBar.foreground", Color.DARK_GRAY);
        UIManager.put("ScrollBar.thumbDarkShadow", Color.black);
        UIManager.put("ScrollBar.width", 16);
        UIManager.put("ScrollBar.thumbHighlight", Color.lightGray);
        UIManager.put("ScrollBar.thumbShadow", Color.gray);

        // SwingUtilities.updateComponentTreeUI(this);
        // pack();

        break;
      }
    }
  }

  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jPopupMenu1 = new javax.swing.JPopupMenu();
    jmiClearWindow = new javax.swing.JMenuItem();
    jmiCopyToClip = new javax.swing.JMenuItem();
    jPopupMenu2 = new javax.swing.JPopupMenu();
    jmniCloseAllVMs = new javax.swing.JMenuItem();
    jPanelMainToolsMenuAndDebugTime = new javax.swing.JPanel();
    jToolBar_MainToolbar = new javax.swing.JToolBar();
    jButtonNewProject_A = new javax.swing.JButton();
    jButtonOpenProject_B = new javax.swing.JButton();
    jButtonSave_C = new javax.swing.JButton();
    jButtonUndo_D = new javax.swing.JButton();
    jButtonRedo_E = new javax.swing.JButton();
    jButtonRefreshVM_F = new javax.swing.JButton();
    jButtonOptions_G = new javax.swing.JButton();
    jButtonVariables_H = new javax.swing.JButton();
    jButtonWireLegends_I = new javax.swing.JButton();
    jButtonStart_J = new javax.swing.JButton();
    jButtonDebug_K = new javax.swing.JButton();
    jButtonStop_L = new javax.swing.JButton();
    jButtonPause_M = new javax.swing.JButton();
    jButtonResume_N = new javax.swing.JButton();
    jButtonStep_O = new javax.swing.JButton();
    jButtonAnalogWindow_P = new javax.swing.JButton();
    jButtonDigitalWindow_Q = new javax.swing.JButton();
    jButtonTestPointWin_R = new javax.swing.JButton();
    jButtonConsoleOut_S = new javax.swing.JButton();
    jButtonRepository_T = new javax.swing.JButton();
    jButtonAbout_U = new javax.swing.JButton();
    jPanelDebugTimeSettings = new javax.swing.JPanel();
    jLabelDebugDelay = new javax.swing.JLabel();
    jSpinnerDebugDelayTime = new javax.swing.JSpinner();
    jSplitPane3 = new javax.swing.JSplitPane();
    jPanelLeft = new javax.swing.JPanel();
    jSplitPane1 = new javax.swing.JSplitPane();
    jPanelPropertyEditor = new javax.swing.JPanel();
    jTabPropertyEditor = new javax.swing.JTabbedPane();
    jPanelElementList = new javax.swing.JPanel();
    jComboBoxElementList = new javax.swing.JComboBox();
    jPanelProjectExplorer = new javax.swing.JPanel();
    jPanelCenter = new javax.swing.JPanel();
    jPanelElementPalette = new javax.swing.JPanel();
    jPanelVMsWorkSpace = new javax.swing.JPanel();
    jPaneVMPanels = new javax.swing.JTabbedPane();
    jPanelHelpWindow = new javax.swing.JPanel();
    jPanel8 = new javax.swing.JPanel();
    jButton13 = new javax.swing.JButton();
    jPanel9 = new javax.swing.JPanel();
    jMenuBar_MainMenuBar = new javax.swing.JMenuBar();
    jmnuDatei = new javax.swing.JMenu();
    jmiNewProject = new javax.swing.JMenuItem();
    jmiOpenProject = new javax.swing.JMenuItem();
    jmnuOpenSingleVM = new javax.swing.JMenuItem();
    jmiSave = new javax.swing.JMenuItem();
    jmniSaveAsSingleVM = new javax.swing.JMenuItem();
    jmiSaveAsModul = new javax.swing.JMenuItem();
    jmniPrintVM = new javax.swing.JMenuItem();
    jmiSaveAsJPG = new javax.swing.JMenuItem();
    jSeparator1 = new javax.swing.JSeparator();
    jmiCloseWindow = new javax.swing.JMenuItem();
    jmnuEdit = new javax.swing.JMenu();
    jmiUndo = new javax.swing.JMenuItem();
    jmiRedo = new javax.swing.JMenuItem();
    jSeparator2 = new javax.swing.JSeparator();
    jmiCut = new javax.swing.JMenuItem();
    jmiCopy = new javax.swing.JMenuItem();
    jmiPaste = new javax.swing.JMenuItem();
    jmiSelectAny = new javax.swing.JMenuItem();
    jmnuVM = new javax.swing.JMenu();
    jmiStart = new javax.swing.JMenuItem();
    jmiStop = new javax.swing.JMenuItem();
    jmiPause = new javax.swing.JMenuItem();
    jmiResume = new javax.swing.JMenuItem();
    jmiStep = new javax.swing.JMenuItem();
    jmiEigenschaten = new javax.swing.JMenuItem();
    jmniDefineVariables = new javax.swing.JMenuItem();
    jmniPasswordProtection = new javax.swing.JMenuItem();
    jmniDeletePasswordProtection = new javax.swing.JMenuItem();
    jmnuExtras = new javax.swing.JMenu();
    jmniOptions = new javax.swing.JMenuItem();
    jmniChooseLanguage = new javax.swing.JMenuItem();
    jmniCreateSubVM = new javax.swing.JMenuItem();
    jmniCreateNewJavaComponent = new javax.swing.JMenuItem();
    jmniUpdater = new javax.swing.JMenuItem();
    jmnuWindow = new javax.swing.JMenu();
    jmiLegend = new javax.swing.JMenuItem();
    jmiVariableWatcher = new javax.swing.JMenuItem();
    jmiShowAnalogWindow = new javax.swing.JMenuItem();
    jmiShowDigitalWindow = new javax.swing.JMenuItem();
    jmiShowTestpointWindow = new javax.swing.JMenuItem();
    jmniShowErrorsAndWarnings = new javax.swing.JMenuItem();
    jmniCloseAllVms = new javax.swing.JMenuItem();
    jmnuHelp = new javax.swing.JMenu();
    jmnuDocs = new javax.swing.JMenu();
    jmniUpdate = new javax.swing.JMenuItem();
    jmiInfo = new javax.swing.JMenuItem();

    java.util.ResourceBundle bundle =
        java.util.ResourceBundle.getBundle("VisualLogic/FrameCircuit");
    jmiClearWindow.setText(bundle.getString("Fenster_bereinigen"));
    jmiClearWindow.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiClearWindowActionPerformed(evt);
          }
        });
    jPopupMenu1.add(jmiClearWindow);

    jmiCopyToClip.setText(bundle.getString("Kopieren"));
    jmiCopyToClip.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiCopyToClipActionPerformed(evt);
          }
        });
    jPopupMenu1.add(jmiCopyToClip);

    jmniCloseAllVMs.setText("null");
    jmniCloseAllVMs.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmniCloseAllVMsActionPerformed(evt);
          }
        });
    jPopupMenu2.add(jmniCloseAllVMs);

    setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    setExtendedState(6);
    setLocationByPlatform(true);
    addWindowFocusListener(
        new java.awt.event.WindowFocusListener() {
          public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            formWindowGainedFocus(evt);
          }

          public void windowLostFocus(java.awt.event.WindowEvent evt) {
            formWindowLostFocus(evt);
          }
        });
    addWindowListener(
        new java.awt.event.WindowAdapter() {
          public void windowActivated(java.awt.event.WindowEvent evt) {
            formWindowActivated(evt);
          }

          public void windowClosing(java.awt.event.WindowEvent evt) {
            formWindowClosing(evt);
          }
        });
    addComponentListener(
        new java.awt.event.ComponentAdapter() {
          public void componentMoved(java.awt.event.ComponentEvent evt) {
            formComponentMoved(evt);
          }

          public void componentResized(java.awt.event.ComponentEvent evt) {
            formComponentResized(evt);
          }

          public void componentShown(java.awt.event.ComponentEvent evt) {
            formComponentShown(evt);
          }
        });
    addFocusListener(
        new java.awt.event.FocusAdapter() {
          public void focusGained(java.awt.event.FocusEvent evt) {
            formFocusGained(evt);
          }
        });
    addWindowStateListener(
        new java.awt.event.WindowStateListener() {
          public void windowStateChanged(java.awt.event.WindowEvent evt) {
            formWindowStateChanged(evt);
          }
        });

    jPanelMainToolsMenuAndDebugTime.setPreferredSize(new java.awt.Dimension(100, 38));

    jToolBar_MainToolbar.setBorder(null);
    jToolBar_MainToolbar.setRollover(true);
    jToolBar_MainToolbar.setBorderPainted(false);
    jToolBar_MainToolbar.setOpaque(false);
    jToolBar_MainToolbar.setPreferredSize(new java.awt.Dimension(70, 35));

    jButtonNewProject_A.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/document-new.png")));
    jButtonNewProject_A.setToolTipText(bundle.getString("NewProject"));
    jButtonNewProject_A.setBorderPainted(false);
    jButtonNewProject_A.setPreferredSize(new java.awt.Dimension(28, 25));
    jButtonNewProject_A.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonNewProject_AActionPerformed(evt);
          }
        });
    jToolBar_MainToolbar.add(jButtonNewProject_A);

    jButtonOpenProject_B.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/open-project.png")));
    jButtonOpenProject_B.setToolTipText(bundle.getString("openProject"));
    jButtonOpenProject_B.setBorderPainted(false);
    jButtonOpenProject_B.setPreferredSize(new java.awt.Dimension(28, 25));
    jButtonOpenProject_B.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonOpenProject_BActionPerformed(evt);
          }
        });
    jToolBar_MainToolbar.add(jButtonOpenProject_B);

    jButtonSave_C.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/save.png")));
    jButtonSave_C.setToolTipText(bundle.getString("VM_Speichern"));
    jButtonSave_C.setBorderPainted(false);
    jButtonSave_C.setPreferredSize(new java.awt.Dimension(28, 25));
    jButtonSave_C.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonSave_CActionPerformed(evt);
          }
        });
    jToolBar_MainToolbar.add(jButtonSave_C);

    jButtonUndo_D.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/edit-undo.png")));
    jButtonUndo_D.setToolTipText(bundle.getString("Rueckgaengig"));
    jButtonUndo_D.setBorderPainted(false);
    jButtonUndo_D.setPreferredSize(new java.awt.Dimension(28, 25));
    jButtonUndo_D.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonUndo_DActionPerformed(evt);
          }
        });
    jToolBar_MainToolbar.add(jButtonUndo_D);

    jButtonRedo_E.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/edit-redo.png")));
    jButtonRedo_E.setToolTipText(bundle.getString("Wiederholen"));
    jButtonRedo_E.setBorderPainted(false);
    jButtonRedo_E.setPreferredSize(new java.awt.Dimension(28, 25));
    jButtonRedo_E.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonRedo_EActionPerformed(evt);
          }
        });
    jToolBar_MainToolbar.add(jButtonRedo_E);

    jButtonRefreshVM_F.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/view-refresh.png")));
    jButtonRefreshVM_F.setToolTipText(bundle.getString("Reload VM"));
    jButtonRefreshVM_F.setBorderPainted(false);
    jButtonRefreshVM_F.setPreferredSize(new java.awt.Dimension(28, 25));
    jButtonRefreshVM_F.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonRefreshVM_FActionPerformed(evt);
          }
        });
    jToolBar_MainToolbar.add(jButtonRefreshVM_F);

    jButtonOptions_G.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/preferences.png")));
    jButtonOptions_G.setToolTipText(bundle.getString("Options"));
    jButtonOptions_G.setBorderPainted(false);
    jButtonOptions_G.setPreferredSize(new java.awt.Dimension(28, 25));
    jButtonOptions_G.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonOptions_GActionPerformed(evt);
          }
        });
    jToolBar_MainToolbar.add(jButtonOptions_G);

    jButtonVariables_H.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/variables.png")));
    jButtonVariables_H.setToolTipText(bundle.getString("variable_definition"));
    jButtonVariables_H.setBorderPainted(false);
    jButtonVariables_H.setPreferredSize(new java.awt.Dimension(28, 25));
    jButtonVariables_H.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonVariables_HActionPerformed(evt);
          }
        });
    jToolBar_MainToolbar.add(jButtonVariables_H);

    jButtonWireLegends_I.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/legend.png")));
    jButtonWireLegends_I.setToolTipText(bundle.getString("Datentyp-Legende"));
    jButtonWireLegends_I.setBorderPainted(false);
    jButtonWireLegends_I.setPreferredSize(new java.awt.Dimension(28, 25));
    jButtonWireLegends_I.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonWireLegends_IActionPerformed(evt);
          }
        });
    jToolBar_MainToolbar.add(jButtonWireLegends_I);

    jButtonStart_J.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/play.png")));
    jButtonStart_J.setToolTipText(bundle.getString("Start_VM"));
    jButtonStart_J.setBorderPainted(false);
    jButtonStart_J.setPreferredSize(new java.awt.Dimension(28, 25));
    jButtonStart_J.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonStart_JActionPerformed(evt);
          }
        });
    jToolBar_MainToolbar.add(jButtonStart_J);

    jButtonDebug_K.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/startx.png")));
    jButtonDebug_K.setToolTipText(bundle.getString("Start_VM_(Debug_Modus)"));
    jButtonDebug_K.setBorderPainted(false);
    jButtonDebug_K.setPreferredSize(new java.awt.Dimension(28, 25));
    jButtonDebug_K.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonDebug_KActionPerformed(evt);
          }
        });
    jToolBar_MainToolbar.add(jButtonDebug_K);

    jButtonStop_L.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/stop.png")));
    jButtonStop_L.setToolTipText(bundle.getString("Stop_VM"));
    jButtonStop_L.setBorderPainted(false);
    jButtonStop_L.setPreferredSize(new java.awt.Dimension(28, 25));
    jButtonStop_L.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonStop_LActionPerformed(evt);
          }
        });
    jToolBar_MainToolbar.add(jButtonStop_L);

    jButtonPause_M.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/pause.png")));
    jButtonPause_M.setToolTipText(bundle.getString("Pause_VM"));
    jButtonPause_M.setBorderPainted(false);
    jButtonPause_M.setPreferredSize(new java.awt.Dimension(28, 25));
    jButtonPause_M.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonPause_MActionPerformed(evt);
          }
        });
    jToolBar_MainToolbar.add(jButtonPause_M);

    jButtonResume_N.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/resume.png")));
    jButtonResume_N.setToolTipText(bundle.getString("Weiter_VM"));
    jButtonResume_N.setBorderPainted(false);
    jButtonResume_N.setPreferredSize(new java.awt.Dimension(28, 25));
    jButtonResume_N.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonResume_NActionPerformed(evt);
          }
        });
    jToolBar_MainToolbar.add(jButtonResume_N);

    jButtonStep_O.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/step.png")));
    jButtonStep_O.setToolTipText(bundle.getString("Schritt_VM"));
    jButtonStep_O.setBorderPainted(false);
    jButtonStep_O.setPreferredSize(new java.awt.Dimension(28, 25));
    jButtonStep_O.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonStep_OActionPerformed(evt);
          }
        });
    jToolBar_MainToolbar.add(jButtonStep_O);

    jButtonAnalogWindow_P.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/graph-analog.png")));
    jButtonAnalogWindow_P.setToolTipText(bundle.getString("NumerikGraphWindow"));
    jButtonAnalogWindow_P.setBorderPainted(false);
    jButtonAnalogWindow_P.setPreferredSize(new java.awt.Dimension(28, 25));
    jButtonAnalogWindow_P.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonAnalogWindow_PActionPerformed(evt);
          }
        });
    jToolBar_MainToolbar.add(jButtonAnalogWindow_P);

    jButtonDigitalWindow_Q.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/graph-digital.png")));
    jButtonDigitalWindow_Q.setToolTipText(bundle.getString("DigitalGraphWindow"));
    jButtonDigitalWindow_Q.setBorderPainted(false);
    jButtonDigitalWindow_Q.setPreferredSize(new java.awt.Dimension(28, 25));
    jButtonDigitalWindow_Q.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonDigitalWindow_QActionPerformed(evt);
          }
        });
    jToolBar_MainToolbar.add(jButtonDigitalWindow_Q);

    jButtonTestPointWin_R.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/testpoint.png")));
    jButtonTestPointWin_R.setToolTipText(bundle.getString("TestpointWindow"));
    jButtonTestPointWin_R.setBorderPainted(false);
    jButtonTestPointWin_R.setPreferredSize(new java.awt.Dimension(28, 25));
    jButtonTestPointWin_R.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonTestPointWin_RActionPerformed(evt);
          }
        });
    jToolBar_MainToolbar.add(jButtonTestPointWin_R);

    jButtonConsoleOut_S.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/terminal.png")));
    jButtonConsoleOut_S.setToolTipText(
        bundle.getString("FrameMain.jButtonConsoleOut_S.toolTipText"));
    jButtonConsoleOut_S.setBorderPainted(false);
    jButtonConsoleOut_S.setPreferredSize(new java.awt.Dimension(28, 25));
    jButtonConsoleOut_S.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonConsoleOut_SActionPerformed(evt);
          }
        });
    jToolBar_MainToolbar.add(jButtonConsoleOut_S);

    jButtonRepository_T.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/storage.png")));
    jButtonRepository_T.setText(bundle.getString("FrameMain.jButtonRepository_T.text"));
    jButtonRepository_T.setToolTipText(
        bundle.getString("FrameMain.jButtonRepository_T.toolTipText"));
    jButtonRepository_T.setBorderPainted(false);
    jButtonRepository_T.setEnabled(false);
    jButtonRepository_T.setFocusable(false);
    jButtonRepository_T.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButtonRepository_T.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButtonRepository_T.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonRepository_TActionPerformed(evt);
          }
        });
    jToolBar_MainToolbar.add(jButtonRepository_T);

    jButtonAbout_U.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/info.png")));
    jButtonAbout_U.setToolTipText(bundle.getString("Info"));
    jButtonAbout_U.setBorderPainted(false);
    jButtonAbout_U.setPreferredSize(new java.awt.Dimension(28, 25));
    jButtonAbout_U.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonAbout_UActionPerformed(evt);
          }
        });
    jToolBar_MainToolbar.add(jButtonAbout_U);

    jPanelDebugTimeSettings.setPreferredSize(new java.awt.Dimension(230, 30));

    jLabelDebugDelay.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabelDebugDelay.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabelDebugDelay.setText(bundle.getString("Delay"));
    jLabelDebugDelay.setToolTipText(bundle.getString("FrameMain.jLabelDebugDelay.toolTipText"));
    jLabelDebugDelay.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
    jLabelDebugDelay.setPreferredSize(new java.awt.Dimension(120, 15));

    jSpinnerDebugDelayTime.setToolTipText(
        bundle.getString("FrameMain.jSpinnerDebugDelayTime.toolTipText"));
    jSpinnerDebugDelayTime.setBorder(
        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
    jSpinnerDebugDelayTime.setMinimumSize(new java.awt.Dimension(41, 55));
    jSpinnerDebugDelayTime.setPreferredSize(new java.awt.Dimension(80, 27));
    jSpinnerDebugDelayTime.addChangeListener(
        new javax.swing.event.ChangeListener() {
          public void stateChanged(javax.swing.event.ChangeEvent evt) {
            jSpinnerDebugDelayTimeStateChanged(evt);
          }
        });
    jSpinnerDebugDelayTime.addPropertyChangeListener(
        new java.beans.PropertyChangeListener() {
          public void propertyChange(java.beans.PropertyChangeEvent evt) {
            jSpinnerDebugDelayTimePropertyChange(evt);
          }
        });

    org.jdesktop.layout.GroupLayout jPanelDebugTimeSettingsLayout =
        new org.jdesktop.layout.GroupLayout(jPanelDebugTimeSettings);
    jPanelDebugTimeSettings.setLayout(jPanelDebugTimeSettingsLayout);
    jPanelDebugTimeSettingsLayout.setHorizontalGroup(
        jPanelDebugTimeSettingsLayout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanelDebugTimeSettingsLayout
                    .createSequentialGroup()
                    .add(
                        jLabelDebugDelay,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        144,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(
                        jSpinnerDebugDelayTime,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        67,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()));
    jPanelDebugTimeSettingsLayout.setVerticalGroup(
        jPanelDebugTimeSettingsLayout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanelDebugTimeSettingsLayout
                    .createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(
                        jLabelDebugDelay,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        27,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(
                        jSpinnerDebugDelayTime,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)));

    org.jdesktop.layout.GroupLayout jPanelMainToolsMenuAndDebugTimeLayout =
        new org.jdesktop.layout.GroupLayout(jPanelMainToolsMenuAndDebugTime);
    jPanelMainToolsMenuAndDebugTime.setLayout(jPanelMainToolsMenuAndDebugTimeLayout);
    jPanelMainToolsMenuAndDebugTimeLayout.setHorizontalGroup(
        jPanelMainToolsMenuAndDebugTimeLayout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanelMainToolsMenuAndDebugTimeLayout
                    .createSequentialGroup()
                    .add(
                        jToolBar_MainToolbar,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        711,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 256, Short.MAX_VALUE)
                    .add(
                        jPanelDebugTimeSettings,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        225,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)));
    jPanelMainToolsMenuAndDebugTimeLayout.setVerticalGroup(
        jPanelMainToolsMenuAndDebugTimeLayout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanelMainToolsMenuAndDebugTimeLayout
                    .createSequentialGroup()
                    .add(
                        jPanelMainToolsMenuAndDebugTimeLayout
                            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(
                                jPanelDebugTimeSettings,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                38,
                                Short.MAX_VALUE)
                            .add(
                                jToolBar_MainToolbar,
                                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                                38,
                                Short.MAX_VALUE))
                    .addContainerGap(
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

    getContentPane().add(jPanelMainToolsMenuAndDebugTime, java.awt.BorderLayout.NORTH);

    jSplitPane3.setBorder(null);
    jSplitPane3.setDividerLocation(150);
    jSplitPane3.setContinuousLayout(true);
    jSplitPane3.setMinimumSize(new java.awt.Dimension(200, 66));
    jSplitPane3.setPreferredSize(new java.awt.Dimension(550, 62));

    jPanelLeft.setBackground(new java.awt.Color(214, 217, 223));
    jPanelLeft.setMinimumSize(new java.awt.Dimension(100, 12));
    jPanelLeft.setPreferredSize(new java.awt.Dimension(300, 257));
    jPanelLeft.setLayout(new java.awt.BorderLayout());

    jSplitPane1.setBorder(null);
    jSplitPane1.setDividerLocation(100);
    jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
    jSplitPane1.setContinuousLayout(true);
    jSplitPane1.setMinimumSize(new java.awt.Dimension(160, 12));
    jSplitPane1.setPreferredSize(new java.awt.Dimension(202, 257));
    jSplitPane1.addPropertyChangeListener(
        new java.beans.PropertyChangeListener() {
          public void propertyChange(java.beans.PropertyChangeEvent evt) {
            jSplitPane1PropertyChange(evt);
          }
        });

    jPanelPropertyEditor.setBackground(new java.awt.Color(214, 217, 223));
    jPanelPropertyEditor.setPreferredSize(new java.awt.Dimension(150, 5));
    jPanelPropertyEditor.setLayout(new java.awt.BorderLayout());

    jTabPropertyEditor.setBackground(new java.awt.Color(214, 217, 223));
    jTabPropertyEditor.setToolTipText(bundle.getString("FrameMain.jTabPropertyEditor.toolTipText"));
    jTabPropertyEditor.setPreferredSize(new java.awt.Dimension(100, 5));
    jPanelPropertyEditor.add(jTabPropertyEditor, java.awt.BorderLayout.CENTER);

    jPanelElementList.setBackground(new java.awt.Color(214, 217, 223));
    jPanelElementList.setMinimumSize(new java.awt.Dimension(112, 50));
    jPanelElementList.setPreferredSize(new java.awt.Dimension(100, 40));
    jPanelElementList.setLayout(new java.awt.BorderLayout());

    jComboBoxElementList.setToolTipText(
        bundle.getString("FrameMain.jComboBoxElementList.toolTipText"));
    jComboBoxElementList.setBorder(
        javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED),
            javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
    jComboBoxElementList.setMinimumSize(new java.awt.Dimension(100, 50));
    jComboBoxElementList.setPreferredSize(new java.awt.Dimension(100, 40));
    jComboBoxElementList.addItemListener(
        new java.awt.event.ItemListener() {
          public void itemStateChanged(java.awt.event.ItemEvent evt) {
            jComboBoxElementListItemStateChanged(evt);
          }
        });
    jComboBoxElementList.addMouseMotionListener(
        new java.awt.event.MouseMotionAdapter() {
          public void mouseDragged(java.awt.event.MouseEvent evt) {
            jComboBoxElementListMouseDragged(evt);
          }
        });
    jComboBoxElementList.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jComboBoxElementListActionPerformed(evt);
          }
        });
    jComboBoxElementList.addPropertyChangeListener(
        new java.beans.PropertyChangeListener() {
          public void propertyChange(java.beans.PropertyChangeEvent evt) {
            jComboBoxElementListPropertyChange(evt);
          }
        });
    jPanelElementList.add(jComboBoxElementList, java.awt.BorderLayout.CENTER);

    jPanelPropertyEditor.add(jPanelElementList, java.awt.BorderLayout.NORTH);

    jSplitPane1.setRightComponent(jPanelPropertyEditor);

    jPanelProjectExplorer.setBackground(new java.awt.Color(214, 217, 223));
    jPanelProjectExplorer.setToolTipText(
        bundle.getString("FrameMain.jPanelProjectExplorer.toolTipText"));
    jPanelProjectExplorer.setPreferredSize(new java.awt.Dimension(200, 0));
    jPanelProjectExplorer.addHierarchyBoundsListener(
        new java.awt.event.HierarchyBoundsListener() {
          public void ancestorMoved(java.awt.event.HierarchyEvent evt) {}

          public void ancestorResized(java.awt.event.HierarchyEvent evt) {
            jPanelProjectExplorerAncestorResized(evt);
          }
        });
    jPanelProjectExplorer.setLayout(new java.awt.BorderLayout());
    jSplitPane1.setTopComponent(jPanelProjectExplorer);

    jPanelLeft.add(jSplitPane1, java.awt.BorderLayout.CENTER);

    jSplitPane3.setLeftComponent(jPanelLeft);

    jPanelCenter.setBackground(new java.awt.Color(214, 217, 223));
    jPanelCenter.setLayout(new java.awt.BorderLayout());

    jPanelElementPalette.setBackground(new java.awt.Color(214, 217, 223));
    jPanelElementPalette.setToolTipText(
        bundle.getString("FrameMain.jPanelElementPalette.toolTipText"));
    jPanelElementPalette.setAutoscrolls(true);
    jPanelElementPalette.setFocusTraversalPolicyProvider(true);
    jPanelElementPalette.setMinimumSize(new java.awt.Dimension(50, 75));
    jPanelElementPalette.setPreferredSize(new java.awt.Dimension(150, 100));
    jPanelElementPalette.addMouseMotionListener(
        new java.awt.event.MouseMotionAdapter() {
          public void mouseMoved(java.awt.event.MouseEvent evt) {
            jPanelElementPaletteMouseMoved(evt);
          }
        });
    jPanelElementPalette.addMouseListener(
        new java.awt.event.MouseAdapter() {
          public void mousePressed(java.awt.event.MouseEvent evt) {
            jPanelElementPaletteMousePressed(evt);
          }
        });
    jPanelElementPalette.addComponentListener(
        new java.awt.event.ComponentAdapter() {
          public void componentResized(java.awt.event.ComponentEvent evt) {
            jPanelElementPaletteComponentResized(evt);
          }
        });
    jPanelElementPalette.setLayout(new java.awt.BorderLayout());
    jPanelCenter.add(jPanelElementPalette, java.awt.BorderLayout.NORTH);

    jPanelVMsWorkSpace.setLayout(new java.awt.BorderLayout());

    jPaneVMPanels.setBackground(new java.awt.Color(214, 217, 223));
    jPaneVMPanels.setPreferredSize(new java.awt.Dimension(500, 500));
    jPaneVMPanels.addChangeListener(
        new javax.swing.event.ChangeListener() {
          public void stateChanged(javax.swing.event.ChangeEvent evt) {
            jPaneVMPanelsStateChanged(evt);
          }
        });
    jPaneVMPanels.addMouseListener(
        new java.awt.event.MouseAdapter() {
          public void mousePressed(java.awt.event.MouseEvent evt) {
            jPaneVMPanelsMousePressed(evt);
          }
        });
    jPanelVMsWorkSpace.add(jPaneVMPanels, java.awt.BorderLayout.CENTER);

    jPanelHelpWindow.setFocusTraversalPolicyProvider(true);
    jPanelHelpWindow.setPreferredSize(new java.awt.Dimension(300, 100));
    jPanelHelpWindow.setRequestFocusEnabled(false);
    jPanelHelpWindow.setLayout(new java.awt.BorderLayout());

    jPanel8.setAutoscrolls(true);
    jPanel8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    jPanel8.setFocusable(false);
    jPanel8.setPreferredSize(new java.awt.Dimension(10, 518));
    jPanel8.addMouseMotionListener(
        new java.awt.event.MouseMotionAdapter() {
          public void mouseDragged(java.awt.event.MouseEvent evt) {
            jPanel8MouseDragged(evt);
          }
        });

    jButton13.setBackground(new java.awt.Color(204, 204, 204));
    jButton13.setText(bundle.getString("FrameMain.jButton13.text"));
    jButton13.setToolTipText(bundle.getString("FrameMain.jButton13.toolTipText"));
    jButton13.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    jButton13.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton13ActionPerformed(evt);
          }
        });

    org.jdesktop.layout.GroupLayout jPanel8Layout = new org.jdesktop.layout.GroupLayout(jPanel8);
    jPanel8.setLayout(jPanel8Layout);
    jPanel8Layout.setHorizontalGroup(
        jPanel8Layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanel8Layout
                    .createSequentialGroup()
                    .add(
                        jButton13,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        11,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(0, 0, Short.MAX_VALUE)));
    jPanel8Layout.setVerticalGroup(
        jPanel8Layout
            .createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(
                jPanel8Layout
                    .createSequentialGroup()
                    .add(43, 43, 43)
                    .add(
                        jButton13,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        43,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(439, Short.MAX_VALUE)));

    jPanelHelpWindow.add(jPanel8, java.awt.BorderLayout.WEST);

    jPanel9.setBackground(new java.awt.Color(214, 217, 223));
    jPanel9.setToolTipText(bundle.getString("FrameMain.jPanel9.toolTipText"));
    jPanel9.setEnabled(false);
    jPanel9.setPreferredSize(new java.awt.Dimension(100, 518));
    jPanel9.setLayout(new java.awt.BorderLayout());
    jPanelHelpWindow.add(jPanel9, java.awt.BorderLayout.CENTER);

    jPanelVMsWorkSpace.add(jPanelHelpWindow, java.awt.BorderLayout.EAST);

    jPanelCenter.add(jPanelVMsWorkSpace, java.awt.BorderLayout.CENTER);

    jSplitPane3.setRightComponent(jPanelCenter);

    getContentPane().add(jSplitPane3, java.awt.BorderLayout.CENTER);

    jMenuBar_MainMenuBar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
    jMenuBar_MainMenuBar.setFont(new java.awt.Font("Serif", 3, 36));

    jmnuDatei.setMnemonic('D');
    jmnuDatei.setText(bundle.getString("Datei"));
    jmnuDatei.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmnuDateiActionPerformed(evt);
          }
        });

    jmiNewProject.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/document-new16.png")));
    jmiNewProject.setText(bundle.getString("NewProject"));
    jmiNewProject.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiNewProjectActionPerformed(evt);
          }
        });
    jmnuDatei.add(jmiNewProject);

    jmiOpenProject.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/open-project16.png")));
    jmiOpenProject.setText(bundle.getString("openProject"));
    jmiOpenProject.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiOpenProjectActionPerformed(evt);
          }
        });
    jmnuDatei.add(jmiOpenProject);

    jmnuOpenSingleVM.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/open-vm16.png")));
    jmnuOpenSingleVM.setText(bundle.getString("VM_Oeffnen"));
    jmnuOpenSingleVM.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmnuOpenSingleVMActionPerformed(evt);
          }
        });
    jmnuDatei.add(jmnuOpenSingleVM);

    jmiSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/save16.png")));
    jmiSave.setText(bundle.getString("Speichern"));
    jmiSave.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiSaveActionPerformed(evt);
          }
        });
    jmnuDatei.add(jmiSave);

    jmniSaveAsSingleVM.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/save16.png")));
    jmniSaveAsSingleVM.setText(bundle.getString("Speichern_als..."));
    jmniSaveAsSingleVM.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmniSaveAsSingleVMActionPerformed(evt);
          }
        });
    jmnuDatei.add(jmniSaveAsSingleVM);

    jmiSaveAsModul.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/save16.png")));
    jmiSaveAsModul.setText(bundle.getString("Als_Modul_speichern"));
    jmiSaveAsModul.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiSaveAsModulActionPerformed(evt);
          }
        });
    jmnuDatei.add(jmiSaveAsModul);

    jmniPrintVM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/print16.png")));
    jmniPrintVM.setText(bundle.getString("Print VM"));
    jmniPrintVM.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmniPrintVMActionPerformed(evt);
          }
        });
    jmnuDatei.add(jmniPrintVM);

    jmiSaveAsJPG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/export16.png")));
    jmiSaveAsJPG.setText(bundle.getString("Exportieren_als_JPG"));
    jmiSaveAsJPG.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiSaveAsJPGActionPerformed(evt);
          }
        });
    jmnuDatei.add(jmiSaveAsJPG);
    jmnuDatei.add(jSeparator1);

    jmiCloseWindow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/exit16.png")));
    jmiCloseWindow.setText(bundle.getString("Schliessen"));
    jmiCloseWindow.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiCloseWindowActionPerformed(evt);
          }
        });
    jmnuDatei.add(jmiCloseWindow);

    jMenuBar_MainMenuBar.add(jmnuDatei);

    jmnuEdit.setText(bundle.getString("Bearbeiten"));

    jmiUndo.setAccelerator(
        javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
    jmiUndo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/edit-undo16.png")));
    jmiUndo.setText(bundle.getString("Rueckgaengig"));
    jmiUndo.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiUndoActionPerformed(evt);
          }
        });
    jmnuEdit.add(jmiUndo);

    jmiRedo.setAccelerator(
        javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
    jmiRedo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/edit-redo16.png")));
    jmiRedo.setText(bundle.getString("Wiederholen"));
    jmiRedo.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiRedoActionPerformed(evt);
          }
        });
    jmnuEdit.add(jmiRedo);
    jmnuEdit.add(jSeparator2);

    jmiCut.setAccelerator(
        javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
    jmiCut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/edit-cut16.png")));
    jmiCut.setText(bundle.getString("Ausschneiden"));
    jmiCut.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiCutActionPerformed(evt);
          }
        });
    jmnuEdit.add(jmiCut);

    jmiCopy.setAccelerator(
        javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
    jmiCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/edit-copy16.png")));
    jmiCopy.setText(bundle.getString("Kopieren"));
    jmiCopy.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiCopyActionPerformed(evt);
          }
        });
    jmnuEdit.add(jmiCopy);

    jmiPaste.setAccelerator(
        javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
    jmiPaste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/edit-paste16.png")));
    jmiPaste.setText(bundle.getString("Einfuegen"));
    jmiPaste.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiPasteActionPerformed(evt);
          }
        });
    jmnuEdit.add(jmiPaste);

    jmiSelectAny.setAccelerator(
        javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
    jmiSelectAny.setText(bundle.getString("Alles_markieren"));
    jmiSelectAny.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiSelectAnyActionPerformed(evt);
          }
        });
    jmnuEdit.add(jmiSelectAny);

    jMenuBar_MainMenuBar.add(jmnuEdit);

    jmnuVM.setText(bundle.getString("VM"));
    jmnuVM.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmnuVMActionPerformed(evt);
          }
        });

    jmiStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/play16.png")));
    jmiStart.setText(bundle.getString("Start"));
    jmiStart.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiStartActionPerformed(evt);
          }
        });
    jmnuVM.add(jmiStart);

    jmiStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/stop16.png")));
    jmiStop.setText(bundle.getString("Stop"));
    jmiStop.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiStopActionPerformed(evt);
          }
        });
    jmnuVM.add(jmiStop);

    jmiPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/pause16.png")));
    jmiPause.setText(bundle.getString("Pause"));
    jmiPause.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiPauseActionPerformed(evt);
          }
        });
    jmnuVM.add(jmiPause);

    jmiResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/resume16.png")));
    jmiResume.setMnemonic('F');
    jmiResume.setText(bundle.getString("Weiter"));
    jmiResume.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiResumeActionPerformed(evt);
          }
        });
    jmnuVM.add(jmiResume);

    jmiStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/step16.png")));
    jmiStep.setText(bundle.getString("Schritt"));
    jmiStep.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiStepActionPerformed(evt);
          }
        });
    jmnuVM.add(jmiStep);

    jmiEigenschaten.setText(bundle.getString("Eigenschaften"));
    jmiEigenschaten.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiEigenschatenActionPerformed(evt);
          }
        });
    jmnuVM.add(jmiEigenschaten);

    jmniDefineVariables.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/variables16.png")));
    jmniDefineVariables.setText(bundle.getString("variable_definition"));
    jmniDefineVariables.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmniDefineVariablesActionPerformed(evt);
          }
        });
    jmnuVM.add(jmniDefineVariables);

    jmniPasswordProtection.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/lock16.png")));
    jmniPasswordProtection.setText(bundle.getString("Passwort Protection"));
    jmniPasswordProtection.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmniPasswordProtectionActionPerformed(evt);
          }
        });
    jmnuVM.add(jmniPasswordProtection);

    jmniDeletePasswordProtection.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/unlock16.png")));
    jmniDeletePasswordProtection.setText(bundle.getString("Delete Password protection"));
    jmniDeletePasswordProtection.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmniDeletePasswordProtectionActionPerformed(evt);
          }
        });
    jmnuVM.add(jmniDeletePasswordProtection);

    jMenuBar_MainMenuBar.add(jmnuVM);

    jmnuExtras.setText(bundle.getString("FrameMain.jmnuExtras.text_1"));
    jmnuExtras.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmnuExtrasActionPerformed(evt);
          }
        });

    jmniOptions.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/preferences16.png")));
    jmniOptions.setText(bundle.getString("Options"));
    jmniOptions.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmniOptionsActionPerformed(evt);
          }
        });
    jmnuExtras.add(jmniOptions);

    jmniChooseLanguage.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/preferences-locale16.png")));
    jmniChooseLanguage.setText(bundle.getString("language"));
    jmniChooseLanguage.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmniChooseLanguageActionPerformed(evt);
          }
        });
    jmnuExtras.add(jmniChooseLanguage);

    jmniCreateSubVM.setText(bundle.getString("Create SubVM"));
    jmniCreateSubVM.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmniCreateSubVMActionPerformed(evt);
          }
        });
    jmnuExtras.add(jmniCreateSubVM);

    jmniCreateNewJavaComponent.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/document-script16.png")));
    jmniCreateNewJavaComponent.setText(bundle.getString("Create Java Component"));
    jmniCreateNewJavaComponent.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmniCreateNewJavaComponentActionPerformed(evt);
          }
        });
    jmnuExtras.add(jmniCreateNewJavaComponent);

    jmniUpdater.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/storage16.png")));
    jmniUpdater.setText(bundle.getString("FrameMain.jmniUpdater.text"));
    jmniUpdater.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmniUpdaterActionPerformed(evt);
          }
        });
    jmnuExtras.add(jmniUpdater);

    jMenuBar_MainMenuBar.add(jmnuExtras);

    jmnuWindow.setText(bundle.getString("Fenster"));

    jmiLegend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/list16.png")));
    jmiLegend.setText(bundle.getString("Datentyp-Legende"));
    jmiLegend.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiLegendActionPerformed(evt);
          }
        });
    jmnuWindow.add(jmiLegend);

    jmiVariableWatcher.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/watcher16.png")));
    jmiVariableWatcher.setText(bundle.getString("Variable Watcher"));
    jmiVariableWatcher.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiVariableWatcherActionPerformed(evt);
          }
        });
    jmnuWindow.add(jmiVariableWatcher);

    jmiShowAnalogWindow.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/graph-analog16.png")));
    jmiShowAnalogWindow.setText(bundle.getString("NumerikGraphWindow"));
    jmiShowAnalogWindow.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiShowAnalogWindowActionPerformed(evt);
          }
        });
    jmnuWindow.add(jmiShowAnalogWindow);

    jmiShowDigitalWindow.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/graph-digital16.png")));
    jmiShowDigitalWindow.setText(bundle.getString("DigitalGraphWindow"));
    jmiShowDigitalWindow.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiShowDigitalWindowActionPerformed(evt);
          }
        });
    jmnuWindow.add(jmiShowDigitalWindow);

    jmiShowTestpointWindow.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/testpoint16.png")));
    jmiShowTestpointWindow.setText(bundle.getString("TestpointWindow"));
    jmiShowTestpointWindow.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiShowTestpointWindowActionPerformed(evt);
          }
        });
    jmnuWindow.add(jmiShowTestpointWindow);

    jmniShowErrorsAndWarnings.setIcon(
        new javax.swing.ImageIcon(getClass().getResource("/Images/warning16.png")));
    jmniShowErrorsAndWarnings.setText(bundle.getString("Show Errors and Warnings"));
    jmniShowErrorsAndWarnings.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmniShowErrorsAndWarningsActionPerformed(evt);
          }
        });
    jmnuWindow.add(jmniShowErrorsAndWarnings);

    jmniCloseAllVms.setText(bundle.getString("Close All VMs"));
    jmniCloseAllVms.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmniCloseAllVmsActionPerformed(evt);
          }
        });
    jmnuWindow.add(jmniCloseAllVms);

    jMenuBar_MainMenuBar.add(jmnuWindow);

    jmnuHelp.setText(bundle.getString("Help"));

    // jmnuDocs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/book16.png")));
    // jmnuDocs.setText(bundle.getString("FrameMain.jmnuDocs.text"));
    // jmnuHelp.add(jmnuDocs);

    jmiInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/info16.png")));
    jmiInfo.setText(bundle.getString("Info"));
    jmiInfo.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jmiInfoActionPerformed(evt);
          }
        });
    jmnuHelp.add(jmiInfo);

    jMenuBar_MainMenuBar.add(jmnuHelp);

    setJMenuBar(jMenuBar_MainMenuBar);
  }

  private boolean unusedPinsExist() {
    Basis basis = getActualBasis();
    if (basis != null) {
      VMObject circuit = basis.getCircuitBasis();

      // Alle Element als noch nicht Compiliert markieren
      for (int i = 0; i < circuit.getElementCount(); i++) {
        Element element = circuit.getElement(i);

        for (int j = 0; j < element.getPinCount(); j++) {
          JPin pin = element.getPin(j);

          if (pin.draht == null) {
            return true;
          }
        }
      }
    }

    return false;
  }

  private String extraceNumber(String str, int startIndex) {
    String ch;
    for (int i = startIndex; i < str.length(); i++) {
      ch = str.substring(i, i + 1);
      if (ch.equalsIgnoreCase("%")) {
        String res = str.substring(startIndex, i);
        return res;
      }
    }

    return "";
  }

  private String eval(String str) {
    String result = "";
    Expression parser = new Expression();
    ParserCode.Expression.Scanner scanner =
        new ParserCode.Expression.Scanner(new java.io.StringReader(str));

    Expression.list.clear();

    while (scanner.ttype != ParserCode.Expression.Scanner.TT_EOF) {
      try {
        parser.yyparse(scanner, null);

        for (int i = 0; i < Expression.list.size(); i++) {
          result += Expression.list.get(i) + "\n";
        }

        break;
      } catch (Exception ye) {
        System.err.println(scanner + ": " + ye);
      }
    }

    return result;
  }

  private String substitude(String str, Element element) {
    int index;

    while (true) {
      index = str.indexOf("%eval-property%");

      if (index > -1) {
        int idx = str.indexOf("\n", index);

        System.out.println("idx=" + idx);
        String aa = "%eval-property%";
        String str2 = str.substring(index + aa.length(), idx);
        str2 = str2.trim();

        // Zeile entfernen das Wort aus dem String!
        String strOben = str.substring(0, index);

        ElementProperty prop = element.getProperty(str2);

        String strMitte = "";
        if (prop != null) {
          if (prop.referenz != null) {
            strMitte = "" + eval(prop.referenz.toString());
          }
        }
        String strUnten = str.substring(idx, str.length());

        str = strOben + strMitte + strUnten;
        System.out.println("" + str);
      }
      if (index == -1) {
        break;
      }
    }

    while (true) {
      index = str.indexOf("%get-property%");

      if (index > -1) {
        int idx = str.indexOf(" ", index);

        int idx2 = str.indexOf(" ", idx + 1);
        int idx3 = str.indexOf("\n", index);

        if (idx2 > -1) {
          idx = idx2;
        } else if (idx3 > -1) {
          idx = idx3;
        }

        System.out.println("idx=" + idx);
        String aa = "%get-property%";
        String str2 = str.substring(index + aa.length(), idx);
        str2 = str2.trim();

        // Zeile entfernen das Wort aus dem String!
        String strOben = str.substring(0, index);

        ElementProperty prop = element.getProperty(str2);

        String strMitte = "";
        if (prop != null) {
          if (prop.referenz != null) {
            strMitte = prop.referenz.toString();
          }
        }
        String strUnten = str.substring(idx, str.length());

        str = strOben + strMitte + strUnten;
        System.out.println("" + str);
      }
      if (index == -1) {
        break;
      }
    }

    while (true) {

      index = str.indexOf("%pin");

      if (index > -1) {
        String number = extraceNumber(str, index + 4);

        // L�sche das Wort aus dem String!
        String newString = str.substring(0, index);

        JPin pin = element.getPin(Integer.parseInt(number));

        if (pin.draht != null) {
          int id = pin.draht.getDestElementID();
          /*if (id==element.getID())
          {
          newString+="element_"+element.getID()+"_pin"+number;
          }else*/
          {
            int destPinNr = pin.draht.getDestPin();
            String elementPin = "ELEMENT" + id + "_PIN" + destPinNr;

            newString += elementPin;
            // int regNr=suchNachEntsprechenderRegisterNr(elementPin);
            // newString+="R"+regNr;
          }
        } else {
          newString += "R0";
        }

        newString += str.substring(index + 4 + number.length() + 1, str.length());
        str = newString;
      }

      if (index == -1) {
        break;
      }
    }

    while (true) {
      index = str.indexOf("%procedure");

      if (index > -1) {
        // L�sche das Wort aus dem String!
        String newString = str.substring(0, index);

        String xx = "ELEMENT" + element.getID();
        newString += xx;
        newString += str.substring(index + 11, str.length());
        str = newString;
      }

      if (index == -1) {
        break;
      }
    }

    while (true) {
      index = str.indexOf("%notify");

      if (index > -1) {
        String number = extraceNumber(str, index + 7);
        if (number.trim().equalsIgnoreCase("")) {
          Tools.showMessage(this, "Notify Number not found!");
          return str;
        }

        // L�sche das Wort aus dem String!
        String newString = str.substring(0, index);

        // newString+="NOTIFY";
        JPin pin = element.getPin(Integer.parseInt(number));
        if (number.trim().equalsIgnoreCase("")) {
          Tools.showMessage(this, "Notify Number not found!");
          return str;
        }

        if (pin.draht != null) {
          int id = pin.draht.getDestElementID();

          newString +=
              "PUSH_NEXTELEMENT ELEMENT"
                  + id
                  + "// PUSHT die Adresse des NextElements in den Stack\n";
        } else {
          newString += "";
        }
        newString += str.substring(index + 7 + number.length() + 1, str.length());
        str = newString;
      }

      if (index == -1) {
        break;
      }
    }

    while (true) {
      index = str.indexOf("%nextElement");

      if (index > -1) {
        String number = extraceNumber(str, index + 12);
        if (number.trim().equalsIgnoreCase("")) {
          Tools.showMessage(this, "nextElement-PinNr not found!");
          return str;
        }

        // L�sche das Wort aus dem String!
        String newString = str.substring(0, index);

        // newString+="NOTIFY";
        JPin pin = element.getPin(Integer.parseInt(number));
        if (number.trim().equalsIgnoreCase("")) {
          Tools.showMessage(this, "nextElement-PinNr not found!");
          return str;
        }

        if (pin.draht != null) {
          int id = pin.draht.getDestElementID();
          /*if (id==element.getID())
          {
          Tools.showMessage(this,"NotifyID==elementID!");
          return str;
          }else*/
          {
            int destPinNr = pin.draht.getDestPin();
            newString += "ELEMENT" + id;
            // newString += "ADD_NEXTELEMENT // Adress in NextElement Queue FIFO!\n";
          }
        } else {
          newString += "";
        }

        newString += str.substring(index + 12 + number.length() + 1, str.length());
        str = newString;
      }

      if (index == -1) {
        break;
      }
    }

    // Einheitliche Formatierung : Einr�cken
    String result = "";

    int i = 0;
    int old = 0;
    String ch = "";
    while (i < str.length()) {
      ch = str.substring(i, i + 1);
      if (ch.equalsIgnoreCase("\n")) {
        String aa = str.substring(old, i).trim() + "\n";
        result += "  " + aa;
        // System.out.println("aa="+aa);
        old = i;
      }
      i++;
    }

    return result;
  }

  // Ergebnis ist der Code der Linken, eigener und Rechten Elemente
  // PinNr ist der zu analysierende Pin des Elements
  private String recursion(VMObject circuit, int srcElementID, Element element) {
    // System.out.println(element.getCaption());
    element.isAlreadyCompiled = true;
    String code = "";
    // Alle Inputs
    for (int j = 0; j < element.getPinCount(); j++) {
      JPin pin = element.getPin(j);

      if (pin.draht != null && pin.pinIO == JPin.PIN_INPUT) {
        if (pin.draht.getSourceElementID() == srcElementID) {
        } else {
          Element el = circuit.getElementWithID(pin.draht.getSourceElementID());

          if (!el.isAlreadyCompiled) {
            code += recursion(circuit, element.getID(), el);
          }
        }
      }
    }
    String eigenerCode = (String) element.jGetTag(2);
    // code += eigenerCode;
    // code += substitudeMCUFlowChart(eigenerCode, element);
    code += substitude(eigenerCode, element);

    // Alle Outputs
    for (int j = 0; j < element.getPinCount(); j++) {
      JPin pin = element.getPin(j);

      if (pin.draht != null && pin.pinIO == JPin.PIN_OUTPUT) {
        if (pin.draht.getDestElementID() == srcElementID) {
        } else {
          Element el = circuit.getElementWithID(pin.draht.getDestElementID());
          if (!el.isAlreadyCompiled) {
            code += recursion(circuit, element.getID(), el);
          }
        }
      }
    }

    return code;
  }

  public String generateMCUCode() {
    Basis basis = getActualBasis();

    String code = "";
    code += "// *********************************************************\n";
    code += "// * Code generated from MyOpenLab (www.MyOpenLab.org)\n";
    code += "// * MyOpenLab is Freeware!\n";
    code +=
        "// * This Code is generated from MyOpenLab v."
            + Version.strApplicationVersion
            + " "
            + Version.strStatus
            + "\n";
    code += "// * Date : " + new java.util.Date() + " \n";
    code += "// *********************************************************\n\n";

    // code+="GOTO MAIN  // JUMP To Main Routine\n\n";
    if (basis != null) {
      VMObject circuit = basis.getCircuitBasis();

      // Globals
      code += "// GLOBALS\n";
      for (int i = 0; i < circuit.getElementCount(); i++) {
        Element element = circuit.getElement(i);
        String str = (String) element.jGetTag(4);
        if (str != null) {
          code += str;
        }
      }
      code += "\n";

      // Alle Element als noch nicht Compiliert markieren
      for (int i = 0; i < circuit.getElementCount(); i++) {
        Element element = circuit.getElement(i);
        element.isAlreadyCompiled = false;
      }

      code += "BEGIN:\n";

      for (int i = 0; i < circuit.getElementCount(); i++) {
        Element element = circuit.getElement(i);
        if (element.isAlreadyCompiled == false) {
          code += recursion(circuit, -1, element);
        }
      }
      code += "GOTO BEGIN\n";
    }

    return code;
  }

  /*private String substitudeMCUFlowChart(String str, Element element)
  {
  int index;
  while (true)
  {
  index = str.indexOf("%pin");
  if (index > -1)
  {
  String number = extraceNumber(str, index + 4);
  // L�sche das Wort aus dem String!
  String newString = str.substring(0, index);
  JPin pin = element.getPin(Integer.parseInt(number));
  if (pin.draht != null)
  {
  int id = pin.draht.getDestElementID();
  //int destPinNr = pin.draht.getDestPin();
  String elementPin = "ELEMENT" + id;
  newString += elementPin;
  }
  newString += str.substring(index + 4 + number.length() + 1, str.length());
  str = newString;
  }
  if (index == -1)
  {
  break;
  }
  }
  return str;
  }*/
  // Ergebnis ist der Code der Linken, eigener und Rechten Elemente
  // PinNr ist der zu analysierende Pin des Elements
  private String recursionMCUFlowChart(VMObject circuit, Element element) {
    // System.out.println(element.getCaption());
    element.isAlreadyCompiled = true;
    String code = "";

    String eigenerCode = (String) element.jGetTag(2);

    // code += substitudeMCUFlowChart(eigenerCode, element);
    code += substitude(eigenerCode, element);

    // Alle Outputs
    for (int i = 0; i < element.getPinCount(); i++) {
      JPin pin = element.getPin(i);
      if (pin.draht != null && pin.pinIO == JPin.PIN_OUTPUT) {
        int dstID = pin.draht.getDestElementID();
        // int dstPin = pin.draht.getDestPin();

        Element dstElement = circuit.getElementWithID(dstID);
        if (!dstElement.isAlreadyCompiled) {
          code += recursionMCUFlowChart(circuit, dstElement);
        }
      }
    }

    return code;
  }

  public String generateMCUFlowChartCode() {
    Basis basis = getActualBasis();

    String code = "";
    code += "// *********************************************************\n";
    code += "// * Code generated from MyOpenLab (www.MyOpenLab.org)\n";
    code += "// * MyOpenLab is Freeware!\n";
    code +=
        "// * This Code is generated from MyOpenLab v."
            + Version.strApplicationVersion
            + " "
            + Version.strStatus
            + "\n";
    code += "// * Date : " + new java.util.Date() + " \n";
    code += "// *********************************************************\n\n";

    // code+="GOTO MAIN  // JUMP To Main Routine\n\n";
    if (basis != null) {
      VMObject circuit = basis.getCircuitBasis();

      // Remove all Errors and Warnings
      errorWarnings.clearMessages();

      // Code generierung f�r jedes Element ausl�sen!
      for (int i = 0; i < circuit.getElementCount(); i++) {
        Element element = circuit.getElement(i);

        element.classRef.xonStart();
      }
      String errors = errorWarnings.getMessages();

      if (errors.trim().length() > 0) {
        return "";
      }

      // Variablen
      code += "// Variables\n";

      String[] vars = basis.vsGetVariablesNames();

      for (int j = 0; j < vars.length; j++) {
        code += "  DIM " + vars[j] + ", SSHORT\n";
      }

      // Globals
      code += "// GLOBALS\n";
      for (int i = 0; i < circuit.getElementCount(); i++) {
        Element element = circuit.getElement(i);
        String str = (String) element.jGetTag(4);
        if (str != null) {
          code += str;
        }
      }
      code += "\n";

      // code += "GOTO MAIN\n";
      // Alle Element als noch nicht Compiliert markieren
      for (int i = 0; i < circuit.getElementCount(); i++) {
        Element element = circuit.getElement(i);
        element.isAlreadyCompiled = false;
      }

      int startProcID = 0;

      for (int i = 0; i < circuit.getElementCount(); i++) {
        Element element = circuit.getElement(i);
        String str = element.getInternName();

        if (element.isAlreadyCompiled == false && str.equalsIgnoreCase("#MCU-FLOWCHART-START#")) {
          startProcID = element.getID();

          code += recursionMCUFlowChart(circuit, element);
        }
      }

      // code += "// MAIN\n";
      // code += "MAIN:\n";
      // code += "CALL ELEMENT"+startProcID+"\n";
    }

    return code;
  }

  private boolean isElementMCUFlowChartElement(Element element) {
    String str = element.getInternName().substring(0, 13);
    if (str.equalsIgnoreCase("#MCU-FLOWCHART")) {
      return true;
    }
    return false;
  }

  public String generateMCUVMCode() {
    Basis basis = getActualBasis();
    String code = "";
    code += "// *********************************************************\n";
    code += "// * Code generated from MyOpenLab (www.MyOpenLab.org)\n";
    code += "// * MyOpenLab is Freeware!\n";
    code +=
        "// * This is a nano VM for MyOpenLab v."
            + Version.strApplicationVersion
            + " "
            + Version.strStatus
            + "\n";
    code += "// * Date : " + new java.util.Date() + " \n";
    code += "// *********************************************************\n\n\n";
    // code+="GOTO MAIN  // JUMP To Main Routine\n\n";
    if (basis != null) {
      VMObject circuit = basis.getCircuitBasis();
      for (int i = 0; i < circuit.getElementCount(); i++) {
        Element element = circuit.getElement(i);
        String eventHandler = (String) element.jGetTag(3);
        if (eventHandler != null) {
          code += eventHandler;
        }
      }
      code += "\n";
      // code+=" // Register 0 is only for Operation Results (ADD, SUB, AND,...)\n";
      // Alles Element.Pins eine Variable zuweisen
      code += "  // Variables\n";
      for (int i = 0; i < circuit.getElementCount(); i++) {
        Element element = circuit.getElement(i);
        for (int j = 0; j < element.getPinCount(); j++) {
          if (element.getPin(j).draht != null) {
            if (element.getPin(j).dataType == ExternalIF.C_BOOLEAN) {
              code += "  DIM ELEMENT" + element.getID() + "_PIN" + j + ",BYTE\n";
            }
            if (element.getPin(j).dataType == ExternalIF.C_INTEGER) {
              code += "  DIM ELEMENT" + element.getID() + "_PIN" + j + ",WORD\n";
            }

            // code += "  DIM ELEMENT" + element.getID() + "_PIN" + j + "\n";
          }
        }
      }
      code += "\n";
      // Element Globals
      code += "  // Element globals\n";
      for (int i = 0; i < circuit.getElementCount(); i++) {
        Element element = circuit.getElement(i);
        String str = (String) element.jGetTag(4);
        if (str != null) {
          code += substitude(str, element);
        }
      }
      code += "\n";
      code += " GOTO MAIN\n";
      code += "\n";

      // Deklaration der Prozeduren
      for (int i = 0; i < circuit.getElementCount(); i++) {
        Element element = circuit.getElement(i);
        code += "//******************************************************\n";
        code += "//* FUNCTION " + element.getCaption() + "\n";
        code += "//******************************************************\n";
        code += "ELEMENT" + element.getID() + ":\n";
        code += "\n";
        String str = (String) element.jGetTag(2) + "\n";
        if (str != null) {
          code += substitude(str, element);
        }
        code += "RETURN\n\n";
      }
      // Initalisationen
      code += "MAIN:\n";
      code += "  //INIT-BLOCK\n";
      for (int i = 0; i < circuit.getElementCount(); i++) {
        Element element = circuit.getElement(i);
        String str = (String) element.jGetTag(5);
        if (str != null && str.trim().length() > 0) {
          code += "  //--INIT : ELEMENT_" + element.getID() + "-" + element.getCaption() + "\n";
          code += substitude(str, element);
          // System.out.println("code="+code);
        }
      }
      code += "MAIN_BEGIN:\n";
      // Main-Init Block
      code += "  //---BEGIN EVENT-Handler \n";
      for (int i = 0; i < circuit.getElementCount(); i++) {
        Element element = circuit.getElement(i);
        String eventHandler = (String) element.jGetTag(1);
        if (eventHandler != null && eventHandler.trim().length() > 0) {
          code += "  //--EVENT-H : ELEMENT_" + element.getID() + "-" + element.getCaption() + "\n";
          code += substitude(eventHandler, element);
        }
      }
      code += "  //---END EVENT-Handler \n";
      for (int i = 0; i < circuit.getElementCount(); i++) {
        Element element = circuit.getElement(i);
        String eventHandler = (String) element.jGetTag(0);
        if (eventHandler != null && eventHandler.equalsIgnoreCase("REGISTER_AS_EVENTHANDLER")) {
          code += "    element_" + element.getID() + "();\n";
        }
      }

      code += "  PROCESS_NEXTELEMENT\n";
      code += "  PROCESS_TIMERS\n";

      /*code += "  MAIN_LOOP:\n";
      code += "    POP NEXTELEMENT        // GET NEXTELEMENT\n";
      code += "    CMP NEXTELEMENT,0      // if (NEXTELEMENT==0) Stack=0 -> Stack is Empty!\n";
      code += "    JMP_IF_A=B MAIN_BEGIN  // then goto MAIN\n";
      code += "    CALL NEXTELEMENT       // CALLS NEXTELEMENT procedure\n";*/
      code += "  GOTO MAIN_BEGIN\n";
      code += "MAIN_EXIT:\n";
    }
    return code;
  }

  private void jmniSaveAsSingleVMActionPerformed(
      java.awt.event.ActionEvent evt) // GEN-FIRST:event_jmniSaveAsSingleVMActionPerformed
      { // GEN-HEADEREND:event_jmniSaveAsSingleVMActionPerformed
    Basis basis = getActualBasis();

    if (basis != null) {
      basis.saveAs();
    }
  } // GEN-LAST:event_jmniSaveAsSingleVMActionPerformed

  private void jmniUpdateActionPerformed(
      java.awt.event.ActionEvent evt) // GEN-FIRST:event_jmniUpdateActionPerformed
      { // GEN-HEADEREND:event_jmniUpdateActionPerformed

    if (Tools.appResult != 10) {
      DialogUpdate frm = new DialogUpdate(this, true);
      frm.setVisible(true);
    } else {
      Tools.showMessage(
          this,
          java.util.ResourceBundle.getBundle("VisualLogic/DialogUpdate")
              .getString("Please_restart_the_Application!"),
          JOptionPane.INFORMATION_MESSAGE);
    }
  } // GEN-LAST:event_jmniUpdateActionPerformed

  private boolean isInJDKMode() {
    JavaCompiler jc = null;
    StandardJavaFileManager sjfm = null;
    try {

      jc = ToolProvider.getSystemJavaCompiler();
      sjfm = jc.getStandardFileManager(null, null, null);
    } catch (Exception ex) {
      return false;
    }

    return true;
  }

  private void jmniCreateNewJavaComponentActionPerformed(
      java.awt.event.ActionEvent evt) // GEN-FIRST:event_jmniCreateNewJavaComponentActionPerformed
      { // GEN-HEADEREND:event_jmniCreateNewJavaComponentActionPerformed

    if (isInJDKMode()) {
      DialogNewJavaComponentAssistent frm = new DialogNewJavaComponentAssistent(this, true);
      frm.setVisible(true);

      if (frm.result) {
        String mappedFile = Tools.mapFile(elementPath + frm.group);
        String grp = new File(mappedFile).getAbsolutePath();

        // String circuitPath = new File(Tools.userElementPath +
        // "/CircuitElements").getAbsolutePath();
        String circuitPath =
            new File(Tools.userElementPath + File.separator + "CircuitElements").getAbsolutePath();
        // String frontPath = new File(Tools.userElementPath +
        // "/FrontElements").getAbsolutePath();
        String frontPath =
            new File(Tools.userElementPath + File.separator + "FrontElements").getAbsolutePath();

        /*if (grp.equalsIgnoreCase(circuitPath) || grp.equalsIgnoreCase(frontPath))
        {
        Tools.showMessage(this,java.util.ResourceBundle.getBundle("VisualLogic/Messages").getString("you can not put Element in Root Directory!"));
        return;
        }*/
        // String basisDir = grp + "/" + frm.compName;
        // String srcDir = grp + "/" + frm.compName + "/src";
        // String binDir = grp + "/" + frm.compName + "/bin";
        String basisDir = grp + File.separator + frm.compName;
        // String srcDir = grp + File.separator + frm.compName + "/src";
        String srcDir = grp + File.separator + frm.compName + File.separator + "src";
        // String binDir = grp + File.separator + frm.compName + "/bin";
        String binDir = grp + File.separator + frm.compName + File.separator + "bin";

        // String str = grp + "/" + frm.compName;
        String str = grp + File.separator + frm.compName;
        new File(str).mkdir();
        new File(srcDir).mkdir();
        new File(binDir).mkdir();
        try {
          copyFiles(elementPath + "/compTemplate/icon.gif", str + "/icon.gif", false);
          copyFiles(elementPath + "/compTemplate/bin/image.gif", str + "/bin/image.gif", false);

          copyFiles(elementPath + "/compTemplate/src/make.bat", str + "/src/make.bat", false);

          frm.generateSrcFiles(basisDir, srcDir, frm.type);

          if (frm.type == 0) {
            jPanelElementPalette.removeAll();
            jPanelElementPalette.add(elementPaletteCircuit, BorderLayout.CENTER);
            elementPaletteFront.setVisible(false);
            elementPaletteCircuit.setVisible(true);

            // elementPaletteCircuit.currentDirectory =
            // "/CircuitElements/2user-defined/";
            elementPaletteCircuit.currentDirectory =
                File.separator
                    + "CircuitElements"
                    + File.separator
                    + "2user-defined"
                    + File.separator;
            elementPaletteCircuit.loadFolder(elementPaletteCircuit.currentDirectory);
          } else {
            jPanelElementPalette.removeAll();
            jPanelElementPalette.add(elementPaletteFront, BorderLayout.CENTER);
            elementPaletteFront.setVisible(true);
            elementPaletteCircuit.setVisible(false);

            // elementPaletteFront.currentDirectory =
            // "/FrontElements/2user-defined/";
            elementPaletteFront.currentDirectory =
                File.separator
                    + "FrontElements"
                    + File.separator
                    + "2user-defined"
                    + File.separator;
            elementPaletteFront.loadFolder(elementPaletteFront.currentDirectory);
          }

          Tools.showMessage(
              this,
              java.util.ResourceBundle.getBundle("VisualLogic/Messages")
                  .getString("Don't forget to compile the new Element"));

          // FormCodeEditor frmCode = new FormCodeEditor(this);
          String strGrp = "";
          if (frm.type == 0) {
            // strGrp = "/CircuitElements/";
            strGrp = File.separator + "CircuitElements" + File.separator;
          } else {
            // strGrp = "/FrontElements/";
            strGrp = File.separator + "FrontElements" + File.separator;
          }

          openEditor(Tools.userElementPath + strGrp + frm.compName);

        } catch (IOException ex) {
          ex.printStackTrace();
        }

        elementPaletteCircuit.loadFolder(elementPaletteCircuit.currentDirectory);
        elementPaletteFront.loadFolder(elementPaletteFront.currentDirectory);
      }

      frm.dispose();

    } else {

      String message =
          java.util.ResourceBundle.getBundle("VisualLogic/FrameCircuit")
              .getString("TO_COMPILE_YOU_NEED_JDK");
      Tools.showMessage(this, message);
    }
  } // GEN-LAST:event_jmniCreateNewJavaComponentActionPerformed

  public void editModule(String filePath) {
    DialogSaveAsModul frmSave = new DialogSaveAsModul(this, this, true);

    frmSave.executeEdit(filePath);
  }

  private void jmniPrintVMActionPerformed(
      java.awt.event.ActionEvent evt) // GEN-FIRST:event_jmniPrintVMActionPerformed
      { // GEN-HEADEREND:event_jmniPrintVMActionPerformed

    if (getActualBasis() != null) {
      PrinterJob printJob = PrinterJob.getPrinterJob();
      printJob.setPrintable(getVMObject());
      if (printJob.printDialog()) {
        try {
          printJob.print();
        } catch (PrinterException pe) {
          System.out.println("Error printing: " + pe);
        }
      }
    }
  } // GEN-LAST:event_jmniPrintVMActionPerformed

  private void jComboBoxElementListMouseDragged(
      java.awt.event.MouseEvent evt) // GEN-FIRST:event_jComboBoxElementListMouseDragged
      { // GEN-HEADEREND:event_jComboBoxElementListMouseDragged
  } // GEN-LAST:event_jComboBoxElementListMouseDragged

  /**
   * generate a new subVM
   *
   * @param projectPath
   * @param pinsLeft count of input-pins
   * @param pinsRight count of output-pins
   * @param vmFilepath filepath for the new subVM
   */
  public void generateSubVM(String projectPath, int pinsLeft, int pinsRight, String vmFilepath) {
    Basis basis = createNewVM();

    String filename = vmFilepath;

    if (basis.isWriteable(new File(filename))) {
      addBasisToVMPanel(basis);
      basis.projectPath = projectPath;

      VMObject vm = basis.getCircuitBasis();
      if (basis.projectPath.length() == 0) {
        basis.save();
      } else {
        basis.saveToFile(filename, false);
        basis.vmFilename = filename;
        basis.fileName = filename;

        reloadProjectPanel();
      }

      int xPositionInPins = 50;
      int yPos = 50;
      for (int i = 0; i < pinsLeft; i++) {
        Element inputPin = Tools.addInputPin(vm, null);
        inputPin.setLocation(xPositionInPins, yPos);
        yPos += 50;
      }

      int xPositionOutPins = 500;
      yPos = 50;
      for (int i = 0; i < pinsRight; i++) {
        Element outputPin = Tools.addOutputPin(vm, null);
        outputPin.setLocation(xPositionOutPins, yPos);
        yPos += 50;
      }
    }
  }

  private void jmnuExtrasActionPerformed(
      java.awt.event.ActionEvent evt) // GEN-FIRST:event_jmnuExtrasActionPerformed
      { // GEN-HEADEREND:event_jmnuExtrasActionPerformed
    // TODO add your handling code here:
  } // GEN-LAST:event_jmnuExtrasActionPerformed

  private void jmniCreateSubVMActionPerformed(
      java.awt.event.ActionEvent evt) // GEN-FIRST:event_jmniCreateSubVMActionPerformed
      { // GEN-HEADEREND:event_jmniCreateSubVMActionPerformed
    if (getActualBasis() != null) {
      VMObject vm = getActualBasis().getCircuitBasis();
      vm.status = new StatusGummiBandX(vm, this);

      // ruft dann binFertig() auf!!
    }
  } // GEN-LAST:event_jmniCreateSubVMActionPerformed

  class BackupDrahtInput {

    int sourceElementID;
    int sourcePin;
    int pinIndex;
  }

  class BackupDrahtOutut {

    int destElementID;
    int destPin;
    int pinIndex;
  }

  public ArrayList<LineInfo> selectAllDrahtsWhereIntersectWithRectaqngle(
      ArrayList<Element> elements, int x1, int y1, int x2, int y2, int align) {
    ArrayList<LineInfo> result = new ArrayList<LineInfo>();

    Element element;
    JPin pin;
    int i, j;

    for (i = 0; i < elements.size(); i++) {
      element = elements.get(i);

      for (j = 0; j < element.getPinCount(); j++) {
        pin = element.getPin(j);

        if (pin != null && pin.draht != null && pin.getPinAlign() == align) {
          Line line = pin.draht.schneidetLinieDenDraht(x1, y1, x2, y2);
          if (line != null) {
            LineInfo info = new LineInfo();
            info.line = line;
            info.pin = pin;
            result.add(info);
          }
        }
      }
    }

    // Sort by, for LEFT Lines!
    // So sort by Y
    if (align == 1 || align == 3) {
      Collections.sort(
          result,
          new Comparator() {

            public int compare(Object o1, Object o2) {
              LineInfo info1 = ((LineInfo) o1);
              LineInfo info2 = ((LineInfo) o2);

              Line line1 = info1.line;
              Line line2 = info1.line;
              if (line1.getStartPoint().y < line2.getStartPoint().y) {
                return -1;
              }
              if (line1.getStartPoint().y > line2.getStartPoint().y) {
                return 1;
              }
              if (line1.getStartPoint().y == line2.getStartPoint().y) {
                return 0;
              }

              return 0;
            }
          });
    }

    if (align == 0 || align == 2) {
      Collections.sort(
          result,
          new Comparator() {

            public int compare(Object o1, Object o2) {
              LineInfo info1 = ((LineInfo) o1);
              LineInfo info2 = ((LineInfo) o2);

              Line line1 = info1.line;
              Line line2 = info1.line;

              if (line1.getStartPoint().x < line2.getStartPoint().x) {
                return -1;
              }
              if (line1.getStartPoint().x > line2.getStartPoint().x) {
                return 1;
              }
              if (line1.getStartPoint().x == line2.getStartPoint().x) {
                return 0;
              }

              return 0;
            }
          });
    }

    return result;
  }

  public void packBasis(Basis basis) {
    if (basis != null) {
      basis.getCircuitBasis().pack();
      basis.getFrontBasis().pack();
    }
  }

  public void processDraht(LineInfo info, VMObject vm) {
    Draht draht = info.line.getDraht();

    Element srcElement = vm.getElementWithID(draht.sourceElementID);
    Element dstElement = vm.getElementWithID(draht.destElementID);

    int srcPin = draht.sourcePin;
    int dstPin = draht.destPin;

    JPin srcPinX = srcElement.getPin(srcPin);
    JPin dstPinX = dstElement.getPin(dstPin);

    if (info.pin != null && info.pin.pinIO == JPin.PIN_INPUT) {
      System.out.println("X INPUT");

      BackupDrahtInput tmp = new BackupDrahtInput();
      tmp.sourceElementID = draht.sourceElementID;
      tmp.sourcePin = draht.sourcePin;

      tmp.pinIndex = IPindex++;
      backupDrahtsInputs.add(tmp);

      srcElement.getPin(srcPin).draht = null;

      String[] args = new String[1];
      args[0] = "" + info.pin.dataType;

      Element inputPin = Tools.addInputPin(vm, args);
      inputPin.setLocation(10, yx1);

      inputPin.getPin(0).draht = draht;

      draht.sourceElementID = inputPin.getID();
      draht.sourcePin = 0;

      draht.setSelected(true);
      inputPin.setSelected(true);
      yx1 += 50;
    } else if (info.pin != null && info.pin.pinIO == JPin.PIN_OUTPUT) {
      System.out.println("X OUTPUT");

      BackupDrahtOutut tmp = new BackupDrahtOutut();
      tmp.destElementID = draht.destElementID;
      tmp.destPin = draht.destPin;

      tmp.pinIndex = OPindex++;
      backupDrahtsOutputs.add(tmp);

      dstElement.getPin(dstPin).draht = null;

      String[] args = new String[1];
      args[0] = "" + info.pin.dataType;

      Element outputPin = Tools.addOutputPin(vm, args);

      outputPin.getPin(0).draht = draht;
      draht.destElementID = outputPin.getID();
      draht.destPin = 0;

      if (draht.getPolySize() < 4) {
        while (draht.getPolySize() > 0) {
          draht.deletePoint(0);
        }

        int dx =
            (srcElement.getLocation().x
                    + srcPinX.getLocation().x
                    + dstElement.getLocation().x
                    + dstPinX.getLocation().x)
                / 2;
        for (int k = 0; k < 4; k++) {
          draht.addPoint(dx, 100);
        }
      }

      draht.setSelected(true);
      outputPin.setSelected(true);

      outputPin.setLocation(500, yx2);
      yx2 += 50;
    }
  }

  int yx1 = 20;
  int yx2 = 20;
  int IPindex = 0;
  int OPindex = 0;

  ArrayList<BackupDrahtInput> backupDrahtsInputs;
  ArrayList<BackupDrahtOutut> backupDrahtsOutputs;

  public void createSubElementFromVM(VMObject vm, Rectangle rect, String vmFilename) {
    timer.stop();

    try {

      int x1 = rect.x;
      int y1 = rect.y;
      int x2 = rect.width;
      int y2 = rect.height;

      ArrayList<Element> elements = vm.getSelectedElements();

      Basis basis = createNewVM();

      addBasisToVMPanel(basis);

      Element element;
      JPin pin;
      int i, j;
      LineInfo info;
      Draht draht;

      yx1 = 20;
      yx2 = 20;
      IPindex = 0;
      OPindex = 0;

      backupDrahtsInputs = new ArrayList<BackupDrahtInput>();
      backupDrahtsOutputs = new ArrayList<BackupDrahtOutut>();

      for (i = 0; i < 4; i++) {
        ArrayList<LineInfo> lineInfos =
            selectAllDrahtsWhereIntersectWithRectaqngle(elements, x1, y1, x2, y2, i);

        for (j = 0; j < lineInfos.size(); j++) {
          info = lineInfos.get(j);

          processDraht(info, vm);
        }
      }

      vm.owner.cut();
      basis.paste();

      basis.projectPath = vm.owner.projectPath;

      if (basis.projectPath.length() == 0) {
        basis.save();
      } else {
        String fileName = "";

        fileName = basis.projectPath + File.separator + vmFilename + ".vlogic";

        basis.saveToFile(fileName, false);
        basis.vmFilename = fileName;
        basis.fileName = fileName;

        reloadProjectPanel();
      }

      Element subVM = null;

      try {
        String mainPath = basis.fileName;

        String frontClass = "VMPanel";

        if (basis.getFrontBasis().getSelectedElements().size() > 0) {
          frontClass = "VMPanel";
        } else {
          frontClass = "";
        }

        File f = new File(mainPath);

        String vmName = f.getName();

        String[] args = new String[3];

        args[0] = vmName;
        args[1] = vmName;
        args[2] = "";

        subVM = Tools.addSubVM(vm, frontClass, args);

        int dx = (x1 + x2) / 2;
        int dy = (y1 + y2) / 2;
        subVM.setLocation(dx, dy);
      } catch (Exception ex) {
        Tools.showMessage(this, "" + ex.toString());
      }

      // Connect the subVM to the wires!
      if (subVM != null) {
        // Inputs
        int outpintCount = subVM.getRightPins();
        BackupDrahtInput tmp;
        for (i = 0; i < backupDrahtsInputs.size(); i++) {
          tmp = backupDrahtsInputs.get(i);

          Element srcElement = vm.getElementWithID(tmp.sourceElementID);
          int srcPin = tmp.sourcePin;

          draht =
              vm.addDrahtIntoCanvas(
                  tmp.sourceElementID, tmp.sourcePin, subVM.getID(), outpintCount + tmp.pinIndex);

          Point p = srcElement.getPin(srcPin).getLocation();
          Point px = subVM.getPin(outpintCount + tmp.pinIndex).getLocation();

          while (draht.getPolySize() > 0) {
            draht.deletePoint(0);
          }

          draht.addPoint(srcElement.getLocation().x + p.x, srcElement.getLocation().y + p.y);

          int dx = (srcElement.getLocation().x + subVM.getLocation().x) / 2;
          int dy = 300; // is automatically set by reorderWireFrames ();

          draht.addPoint(dx, 10);
          draht.addPoint(dx, 10);
          draht.addPoint(10, 10);

          srcElement.getPin(srcPin).draht = draht;
          subVM.getPin(outpintCount + tmp.pinIndex).draht = draht;

          vm.reorderWireFrames();
        }

        // Outputs
        BackupDrahtOutut tmp2;
        for (i = 0; i < backupDrahtsOutputs.size(); i++) {
          tmp2 = backupDrahtsOutputs.get(i);

          Element dstElement = vm.getElementWithID(tmp2.destElementID);
          int dstPin = tmp2.destPin;

          draht =
              vm.addDrahtIntoCanvas(subVM.getID(), tmp2.pinIndex, tmp2.destElementID, tmp2.destPin);

          Point p = dstElement.getPin(dstPin).getLocation();

          // Point px = subVM.getPin(tmp.pinIndex).getLocation();

          int dx = (dstElement.getLocation().x + subVM.getLocation().x) / 2;
          int dy = 300; // is automatically set by reorderWireFrames();

          while (draht.getPolySize() > 0) {
            draht.deletePoint(0);
          }

          draht.addPoint(dstElement.getLocation().x + p.x, dstElement.getLocation().y + p.y);
          draht.addPoint(dx, 10);
          draht.addPoint(dx, 10);
          draht.addPoint(10, 10);

          dstElement.getPin(dstPin).draht = draht;
          subVM.getPin(tmp2.pinIndex).draht = draht;
          vm.reorderWireFrames();
        }
      }

      packBasis(basis);
    } catch (Exception ex) {
      timer.start();
      Tools.showMessage(this, "" + ex.toString());
    }
    timer.start();
  }

  public void binFertig(VMObject vm, Rectangle rect) {
    vm.setModusIdle();

    DialogVMName frm = new DialogVMName(this, true);

    frm.setVisible(true);

    if (frm.result && frm.newName.length() > 0) {
      String vmfilename = frm.newName;

      createSubElementFromVM(vm, rect, vmfilename);
    }
  }

  private void jmniShowErrorsAndWarningsActionPerformed(java.awt.event.ActionEvent evt) {
    if (errorWarnings != null) {
      errorWarnings.setVisible(true);
    }
  }

  private void jButtonConsoleOut_SActionPerformed(java.awt.event.ActionEvent evt) {
    if (getActualBasis() != null) {
      getActualBasis().console.setVisible(true);
    }
  }

  private void jButtonAbout_UActionPerformed(java.awt.event.ActionEvent evt) {
    showInfo(this);
  }

  public static void copyFile(String src, String dst) throws IOException {
    Tools.copyHighSpeed(new File(src), new File(dst));
  }

  public static void copyFiles(String strPath, String dstPath, boolean javaFilter)
      throws IOException {
    File src = new File(strPath);
    File dest = new File(dstPath);

    if (src.isDirectory()) {
      dest.mkdirs();
      String list[] = src.list();

      for (int i = 0; i < list.length; i++) {
        if (javaFilter) {
          if (list[i].endsWith("java") == false && list[i].endsWith("bat") == false) {
            String dest1 = dest.getAbsolutePath() + File.separator + list[i];
            String src1 = src.getAbsolutePath() + File.separator + list[i];

            copyFiles(src1, dest1, javaFilter);
          }
        } else {
          String dest1 = dest.getAbsolutePath() + File.separator + list[i];
          String src1 = src.getAbsolutePath() + File.separator + list[i];

          copyFiles(src1, dest1, javaFilter);
        }
      }
    } else {
      Tools.copyHighSpeed(src, dest);
    }
  }

  private boolean isVM(String path) {
    String p1 = new File(path).getAbsolutePath();
    String p2 = new File(elementPath + "/FrontElements/VMElement").getAbsolutePath();
    String p3 = new File(elementPath + "/FrontElements/VMElementUniversal").getAbsolutePath();

    if (p1.equalsIgnoreCase(p2) || p1.equalsIgnoreCase(p3)) {
      return true;
    }

    return false;
  }

  public void openEditor(String path) {
    if (isVM(elementPath + path)) {
      // Load VM
    } else {
      String javaeditor = settings.getJavaEditor();

      String str = Tools.mapFile(path);
      File file = new File(str);
      DFProperties definition_def = Tools.getPropertiesFromDefinitionFile(file);

      if (javaeditor == null
          || javaeditor.equalsIgnoreCase("")
          || new File(javaeditor).exists() == false) {
        CodeEditor.FormCodeEditor codeForm = new CodeEditor.FormCodeEditor(this);
        codeForm.execute(elementPath, path, definition_def.captionInternationalized);
      } else {
        String srcPath = elementPath + path + File.separator + "src" + File.separator;
        String strFileA = "";
        String strFileB = "";

        if (definition_def.classcircuit.length() > 0) {
          strFileA = "\"" + srcPath + definition_def.classcircuit + ".java\"";
        }

        if (definition_def.classfront.length() > 0) {
          strFileB = "\"" + srcPath + definition_def.classfront + ".java\"";
        }

        try {
          Runtime.getRuntime().exec(javaeditor + " " + strFileA + " " + strFileB);
        } catch (IOException ex) {
          Tools.showMessage(
              java.util.ResourceBundle.getBundle("BasisStatus/StatusIdle")
                  .getString("Javaeditor_not_found!"));
        }
      }
    }
  }

  public void openElement(String path) {
    DFProperties definition_def = Tools.getPropertiesFromDefinitionFile(new File(path));
    if (definition_def.vm.length() == 0) {
      // Open Code Editor
      String f1 = new File(Tools.userElementPath).getAbsolutePath();
      String f2 = new File(path).getAbsolutePath();

      String str = f2.substring(f1.length());
      openEditor(Tools.userElementPath + str);
    } else {
      addBasisToVMPanel(path + File.separator + definition_def.vm, "", true);
    }
  }

  public void openJavaEditor(Element element) {
    // openEditor(Tools.userElementPath, strGrp+frm.compName);
    String strX = elementPath + element.mainPath;

    String str = Tools.mapFile(strX);

    openEditor(str);
  }

  private void jmniDeletePasswordProtectionActionPerformed(java.awt.event.ActionEvent evt) {

    Basis basis = getActualBasis();

    if (basis != null) {
      if (basis.vmProtected) {
        return;
      }

      if (Tools.setQuestionDialog(
          this,
          java.util.ResourceBundle.getBundle("VisualLogic/Messages")
              .getString("Delete password protection"))) {
        basis.vmPassword = "";
        basis.vmProtected = false;
        basis.setChanged(true);
        Tools.showMessage(
            this,
            java.util.ResourceBundle.getBundle("VisualLogic/Messages")
                .getString("Password removed"));
      }
    }
  }

  private void jmniPasswordProtectionActionPerformed(java.awt.event.ActionEvent evt) {

    Basis basis = getActualBasis();

    if (basis != null) {
      if (basis.vmProtected) {
        return;
      }

      DialogPassword frm = new DialogPassword(this, true);
      frm.setVisible(true);

      if (frm.password.length() > 0) {
        basis.setPassword(frm.password);
        basis.setChanged(true);
      }
    }
  }

  public String generateUntiledFileName(String path) {
    String fn;
    for (int i = 0; i < 99999; i++) {
      fn = path + "Untiled" + i + ".vlogic";

      if (!(new File(fn).exists())) {
        return fn;
      }
    }

    return null;
  }

  private void createNewSingleVM() {
    Basis basis = createNewVM();

    addBasisToVMPanel(basis);
  }

  public Basis createNewVM() {
    Basis basis = new Basis(this, elementPath);
    String path = getUserURL().getFile() + System.getProperty("file.separator");

    basis.fileName = generateUntiledFileName(path);

    if (basis.fileName != null) {
      basis.saveToFile(basis.fileName, false);
      basis.loading = true;
      basis.isFileLoaded = false;
      basis.loading = false;

      return basis;
    } else {
      return null;
    }
  }

  private void saveAs() {
    Basis basis = getActualBasis();
    if (basis != null) {
      basis.saveAs();
    }
  }

  public int getTabIndex(VMEditorPanel panel) {
    for (int i = 0; i < jPaneVMPanels.getComponentCount(); i++) {
      Component comp = jPaneVMPanels.getComponentAt(i);

      if (comp.equals(panel)) {
        return i;
      }
    }
    return -1;
  }

  public String globalPath = "";
  public String globalProjectPath = "";

  private void jmnuOpenSingleVMActionPerformed(java.awt.event.ActionEvent evt) {

    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File(settings.getOldProjectPath()));
    chooser.setDialogTitle(
        java.util.ResourceBundle.getBundle("VisualLogic/FrameCircuit").getString("VM_Oeffnen"));

    chooser.addChoosableFileFilter(
        new javax.swing.filechooser.FileFilter() {

          public boolean accept(File f) {
            if (f.isDirectory()) {
              return true;
            }
            return f.getName().toLowerCase().endsWith(".vlogic");
          }

          public String getDescription() {
            return "vlogic Dateien (vlogic)";
          }
        });

    int value = chooser.showOpenDialog(this);

    if (value == JFileChooser.APPROVE_OPTION) {
      File file = chooser.getSelectedFile();
      settings.setCurrentDirectory(chooser.getCurrentDirectory().getPath());

      globalPath = file.getAbsolutePath();

      SwingWorker worker =
          new SwingWorker<Object, Object>() {

            DialogWait frm;

            public Object doInBackground() {
              Tools.dialogWait = new DialogWait();
              Tools.dialogWait.setVisible(true);

              addBasisToVMPanel(globalPath, "", true);
              return null;
            }

            protected void done() {
              Tools.dialogWait.dispose();
            }
          };

      worker.execute();
    }
  }

  private void jmiShowDigitalWindowActionPerformed(java.awt.event.ActionEvent evt) {
    jButtonDigitalWindow_QActionPerformed(evt);
  }

  private void jmiShowAnalogWindowActionPerformed(java.awt.event.ActionEvent evt) {
    jButtonAnalogWindow_PActionPerformed(evt);
  }

  private void jmniCloseAllVmsActionPerformed(java.awt.event.ActionEvent evt) {
    closeAllVms();
  }

  private void jPanelProjectExplorerAncestorResized(java.awt.event.HierarchyEvent evt) {}

  public boolean closeAllVms() {
    while (jPaneVMPanels.getTabCount() > 0) {
      Component comp = jPaneVMPanels.getComponentAt(0);

      if (comp instanceof VMEditorPanel) {
        VMEditorPanel panel = (VMEditorPanel) comp;

        if (closeVM(panel) == false) {
          return false;
        }
      }
    }

    return true;
  }

  private void jmniCloseAllVMsActionPerformed(java.awt.event.ActionEvent evt) {
    closeAllVms();
  }

  private void jPaneVMPanelsStateChanged(javax.swing.event.ChangeEvent evt) {

    Basis basis = getActualBasis();

    if (basis == null) {
      return;
    }

    vmEditorPanelTabChanged(getVMObject());
  }

  private void jButtonAnalogWindow_PActionPerformed(java.awt.event.ActionEvent evt) {

    if (getActualBasis() != null) {
      getActualBasis().frameDoubleGraph.setVisible(true);
    }
  }

  private void jButtonDigitalWindow_QActionPerformed(java.awt.event.ActionEvent evt) {
    if (getActualBasis() != null) {
      getActualBasis().frameBooleanGraph.setVisible(true);
    }
  }

  private void jButtonTestPointWin_RActionPerformed(java.awt.event.ActionEvent evt) {

    if (getActualBasis() != null) {
      getActualBasis().dialogTestpoint.setVisible(true);
    }
  }

  private void jButtonNewProject_AActionPerformed(java.awt.event.ActionEvent evt) {
    jmiNewProjectActionPerformed(evt);
  }

  public void choiceLanguage(JFrame frame) {
    DialogLanguage lan = new DialogLanguage(frame, true);

    lan.execute(settings.getLanguage());

    settings.setLanguage(lan.result);
  }

  private void jmniChooseLanguageActionPerformed(java.awt.event.ActionEvent evt) {

    String lan = settings.getLanguage();
    String old = lan;

    choiceLanguage(this);
    lan = settings.getLanguage();

    if (!old.equalsIgnoreCase(lan)) {
      Tools.showMessage(
          java.util.ResourceBundle.getBundle("VisualLogic/Messages")
              .getString("Please restart the Application!"));
    }
  }

  private void jmiShowTestpointWindowActionPerformed(java.awt.event.ActionEvent evt) {
    jButtonTestPointWin_RActionPerformed(evt);
  }

  private void jSpinnerDebugDelayTimePropertyChange(java.beans.PropertyChangeEvent evt) {}

  private void jSpinnerDebugDelayTimeStateChanged(javax.swing.event.ChangeEvent evt) {
    int value = (((Integer) jSpinnerDebugDelayTime.getValue()).intValue());

    Basis basis = getActualBasis();
    if (basis != null) {
      basis.setDebugDelay(value);
    }
  }

  private void jmiCopyToClipActionPerformed(java.awt.event.ActionEvent evt) {
    // txtMessages.selectAll();
    // txtMessages.copy();
  }

  private void jmiVariableWatcherActionPerformed(java.awt.event.ActionEvent evt) {
    Basis basis = getActualBasis();

    if (basis != null) {
      watcher = new DialogVariableWatcher(this, false);
      watcher.execute(basis.variablenListe);
      watcher.setVisible(true);
    }
  }

  public void reloadBasis() {
    Basis basis = getActualBasis();

    if (basis != null) {
      setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      basis.getCircuitBasis().selectAny(true);

      basis.cut();
      basis.paste();
      basis.getCircuitBasis().selectAny(false);

      setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
  }

  private void jButtonRefreshVM_FActionPerformed(java.awt.event.ActionEvent evt) {

    timer.stop();
    propertyEditor.locked = true;
    reloadBasis();
    timer.start();
    propertyEditor.locked = false;
  }

  private void jmniDefineVariablesActionPerformed(java.awt.event.ActionEvent evt) {
    jButtonVariables_HActionPerformed(evt);
  }

  private void jButtonVariables_HActionPerformed(java.awt.event.ActionEvent evt) {

    Basis basis = getActualBasis();

    if (basis != null) {
      if (basis.vmProtected) {
        return;
      }
      DialogVariables dialogVariables = new DialogVariables(this, true, basis);
      dialogVariables.setVisible(true);
      basis.generateAllVariabled();
    }
  }

  public ArrayList<String> listAllVMsFromProject(String projectPath) {
    ArrayList<String> result = new ArrayList<String>();

    File f = new File(projectPath);

    String[] files = f.list();

    for (int i = 0; i < files.length; i++) {
      if (files[i].endsWith("vlogic")) {
        result.add(files[i]);
      }
    }
    return result;
  }

  public void generateExecutableVM(String projectPath, String destPath, String vmName) {
    Basis basis = new Basis(this, this.elementPath);
    basis.projectPath = projectPath;

    String filename = projectPath + File.separator + vmName;

    basis.loadFromFile(filename, false);

    String ext = Tools.getExtension(new File(vmName));
    String onlyName = vmName.substring(0, vmName.length() - ext.length() - 1);

    String destFilename = destPath + File.separator + onlyName + ".vlogic";

    basis.saveAsExecutable(destFilename);
  }

  public void createDistribution(String projectPath, String destPath, String mainVM) {
    File destDir = new File(destPath);
    boolean Error = false;

    if (!destDir.exists()) {
      destDir.mkdir();
    }

    try {

      Tools.copy(
          new File(
              elementPath + File.separator + ".." + File.separator + "DistributionStarter.jar"),
          new File(destDir + File.separator + "DistributionStarter.jar"));

      Tools.saveText(
          new File(destDir + File.separator + "start.bat"),
          "start javaw -jar DistributionStarter.jar .");

      Tools.saveText(
          new File(destDir + File.separator + "start_linux_distribution"),
          "#!/bin/sh \njava -jar DistributionStarter.jar .");

    } catch (IOException ex) {
      JOptionPane.showMessageDialog(rootPane, "Distribution Could not be Created!");
      ex.printStackTrace();
      Error = true;
    }

    ArrayList<String> files = listAllVMsFromProject(projectPath);

    for (int i = 0; i < files.size(); i++) {
      String file = files.get(i);

      generateExecutableVM(projectPath, destPath, file);
    }

    File execFile = new File(destDir.getPath() + File.separator + "myopenlab.executable");

    try {
      execFile.createNewFile();
    } catch (IOException ex) {
      JOptionPane.showMessageDialog(rootPane, "Distribution Could not be Created!");
      ex.printStackTrace();
      Error = true;
    }

    ProjectProperties props = new ProjectProperties();

    props.mainVM = mainVM;

    Tools.saveProjectFile(destDir, props);

    if (Error == false) {
      JOptionPane.showMessageDialog(rootPane, "Distribution Successfully Created");
      JOptionPane.showMessageDialog(
          rootPane,
          "If your project contains subVMs located into folders you must copy that"
              + " folders to the Distribution root Folder");
    }
  }

  private void jmiSaveAsJPGActionPerformed(java.awt.event.ActionEvent evt) {
    Basis basis = getActualBasis();

    if (basis != null) {
      basis.ownerVMPanel.saveJpg();
    }
  }

  private void jButtonOptions_GActionPerformed(java.awt.event.ActionEvent evt) {
    jmniOptionsActionPerformed(evt);
  }

  private void jComboBoxElementListItemStateChanged(java.awt.event.ItemEvent evt) {}

  private void jComboBoxElementListPropertyChange(java.beans.PropertyChangeEvent evt) {}

  private void jComboBoxElementListActionPerformed(java.awt.event.ActionEvent evt) {

    Basis basis = getActualBasis();

    if (basis != null) {
      if (basis.vmProtected) {
        return;
      }

      if (comboIsEditing == false && evt.getSource() instanceof JComboBox) {
        JComboBox cb = (JComboBox) evt.getSource();

        int index = cb.getSelectedIndex();

        if (index >= 0 && index <= getVMObject().getElementCount()) {
          Element el = getVMObject().getElement(index);
          getVMObject().disableAllElements();

          if (el != null) {
            el.setSelected(true);
            el.processPropertyEditor();
          }
        }
      }
    }
  }

  private void jmiLegendActionPerformed(java.awt.event.ActionEvent evt) {
    jButtonWireLegends_IActionPerformed(evt);
  }

  private void refreshAllBasis() {
    for (int i = 0; i < jPaneVMPanels.getTabCount(); i++) {
      Component comp = jPaneVMPanels.getComponentAt(i);

      if (comp instanceof VMEditorPanel) {
        VMEditorPanel panel = (VMEditorPanel) comp;

        panel.basis.getCircuitBasis().setRasterOn(settings.isCircuitRasterOn());
        panel.basis.getCircuitBasis().setAlignToGrid(settings.isCircuittAlignToGrid());
        panel
            .basis
            .getCircuitBasis()
            .setRaster(settings.getCircuitRasterX(), settings.getCircuitRasterY());

        panel.basis.getFrontBasis().setRasterOn(settings.isFrontRasterOn());
        panel.basis.getFrontBasis().setAlignToGrid(settings.isFrontAlignToGrid());
        panel
            .basis
            .getFrontBasis()
            .setRaster(settings.getFrontRasterX(), settings.getFrontRasterY());
        panel.updateUI();
      }
    }
  }

  private void jmniOptionsActionPerformed(java.awt.event.ActionEvent evt) {
    DialogOptions frm = new DialogOptions(settings, this, true);

    frm.execute(settings);

    if (frm.result) {
      refreshAllBasis();
    }
  }

  public void showInfo(JFrame frame) {
    FrameInfo info = new FrameInfo(frame, true, elementPath);

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int mitteX = (int) screenSize.getWidth() / 2;
    int mitteY = (int) screenSize.getHeight() / 2;

    info.setLocation(mitteX - info.getWidth() / 2, mitteY - info.getHeight() / 2);
    info.setVisible(true);
  }

  public void showElementWindow() {}

  private void jPanelElementPaletteComponentResized(java.awt.event.ComponentEvent evt) {}

  private void jPanelElementPaletteMousePressed(java.awt.event.MouseEvent evt) {
    // TODO add your handling code here:
  }

  private void jPanelElementPaletteMouseMoved(java.awt.event.MouseEvent evt) {
    // TODO add your handling code here:
  }

  private void formComponentShown(java.awt.event.ComponentEvent evt) {
    // TODO add your handling code here:
  }

  private void jmiClearWindowActionPerformed(java.awt.event.ActionEvent evt) {
    // TODO add your handling code here:
  }

  private void jSplitPane1PropertyChange(java.beans.PropertyChangeEvent evt) {
    // TODO add your handling code here:
  }

  private void jButtonRedo_EActionPerformed(java.awt.event.ActionEvent evt) {
    if (getActualBasis() != null) {
      getActualBasis().redo();
    }
  }

  private void jButtonUndo_DActionPerformed(java.awt.event.ActionEvent evt) {

    if (getActualBasis() != null) {
      getActualBasis().undo();
    }
  }

  private void formWindowStateChanged(java.awt.event.WindowEvent evt) {

    if (getExtendedState() == this.MAXIMIZED_BOTH) {
      System.out.println("MAX");
    }

    if (getExtendedState() == this.NORMAL) {
      System.out.println("NORMAL");

      java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

      Dimension size = getSize();

      int x1 = (int) size.getWidth();
      int x2 = screenSize.width;

      if (Math.abs(x1 - x2) < 20) {
        setLocation(50, 50);
        setSize(screenSize.width - 100, screenSize.height - 100);
      }
    }
  }

  private void jmiRedoActionPerformed(java.awt.event.ActionEvent evt) {
    if (getActualBasis() != null) {
      getActualBasis().redo();
    }
  }

  private void jButtonWireLegends_IActionPerformed(java.awt.event.ActionEvent evt) {
    FormDTLengend frm = new FormDTLengend(this.getIconImage());
    frm.setVisible(true);
  }

  private void formComponentMoved(java.awt.event.ComponentEvent evt) {
    // TODO add your handling code here:
  }

  private void jButtonSave_CActionPerformed(java.awt.event.ActionEvent evt) {
    jmiSaveActionPerformed(evt);
  }

  private void jButtonOpenProject_BActionPerformed(java.awt.event.ActionEvent evt) {

    jmiOpenProjectActionPerformed(evt);
  }

  private void jButtonDebug_KActionPerformed(java.awt.event.ActionEvent evt) {

    Basis basis = getActualBasis();
    if (basis != null) {
      ProjectProperties props = Tools.openProjectFile(new File(basis.projectPath));
      if (!props.projectType.equalsIgnoreCase("SPS")) {
        basis.start(true);
      }
    }
  }

  private void formWindowActivated(java.awt.event.WindowEvent evt) {}

  private void jmiEigenschatenActionPerformed(java.awt.event.ActionEvent evt) {

    Basis basis = getActualBasis();
    if (basis != null) {
      if (basis.vmProtected) {
        return;
      }

      VMProperties.intVMWidth = basis.getCircuitBasis().getWidth();
      VMProperties.intVMHeight = basis.getCircuitBasis().getHeight();
      VMProperties.intElementWidth = basis.elementWidth;
      VMProperties.intElementHeight = basis.elementHeight;
      VMProperties.strName = basis.caption;
      VMProperties.strAutorName = basis.autorName;
      VMProperties.strAutorEmail = basis.autorMail;
      VMProperties.strAutorWWW = basis.autorWWW;

      String str =
          java.util.ResourceBundle.getBundle("VisualLogic/FrameCircuit").getString("VM_Properties");

      if (VMProperties.showDialog(this, str)) {
        basis.getCircuitBasis().setSize(VMProperties.intVMWidth, VMProperties.intVMHeight);
        basis.elementWidth = VMProperties.intElementWidth;
        basis.elementHeight = VMProperties.intElementHeight;
        basis.caption = VMProperties.strName;

        basis.autorName = VMProperties.strAutorName;
        basis.autorMail = VMProperties.strAutorEmail;
        basis.autorWWW = VMProperties.strAutorWWW;
        basis.setChanged(true);
      }
    }
  }

  private void jmnuVMActionPerformed(java.awt.event.ActionEvent evt) {
    // TODO add your handling code here:
  }

  private void formComponentResized(java.awt.event.ComponentEvent evt) {

    if (timer != null) {
      timer.stop();
    }

    if (timer != null) {
      timer.start();
    }
  }

  private void jmiCloseWindowActionPerformed(java.awt.event.ActionEvent evt) {

    closeApplication();
  }

  private void jmiStepActionPerformed(java.awt.event.ActionEvent evt) {
    Basis basis = getActualBasis();
    if (basis != null) {
      basis.step();
    }
  }

  private void jmiResumeActionPerformed(java.awt.event.ActionEvent evt) {
    Basis basis = getActualBasis();
    if (basis != null) {
      basis.resume();
    }
  }

  private void jmiPauseActionPerformed(java.awt.event.ActionEvent evt) {

    Basis basis = getActualBasis();
    if (basis != null) {
      basis.pause();
    }
  }

  private void jmiStopActionPerformed(java.awt.event.ActionEvent evt) {
    Basis basis = getActualBasis();
    if (basis != null) {
      basis.stop();
    }
  }

  private void jmiStartActionPerformed(java.awt.event.ActionEvent evt) {
    Basis basis = getActualBasis();
    if (basis != null) {
      basis.start(false);
    }
  }

  private void jmiSelectAnyActionPerformed(java.awt.event.ActionEvent evt) {

    if (getVMObject() != null) {
      getVMObject().selectAny(true);
    }
  }

  private void jmiPasteActionPerformed(java.awt.event.ActionEvent evt) {
    if (getVMObject() != null) {
      getVMObject().owner.paste();
    }
  }

  private void jmiCopyActionPerformed(java.awt.event.ActionEvent evt) {
    if (getVMObject() != null) {
      getVMObject().owner.copy();
    }
  }

  private void jmiCutActionPerformed(java.awt.event.ActionEvent evt) {
    if (getVMObject() != null) {
      getVMObject().owner.cut();
    }
  }

  private void jmiUndoActionPerformed(java.awt.event.ActionEvent evt) {
    Basis basis = getActualBasis();

    if (basis != null) {
      basis.undo();
    }
  }

  private void jmiSaveAsModulActionPerformed(java.awt.event.ActionEvent evt) {

    Basis basis = getActualBasis();

    if (basis != null) {
      if (basis.vmProtected) {
        return;
      }

      DialogSaveAsModul frmDialogSave = new DialogSaveAsModul(this, this, true);
      frmDialogSave.executeNew();
      reloadElementPalettes();
    }
  }

  private void jmnuDateiActionPerformed(java.awt.event.ActionEvent evt) {
    // TODO add your handling code here:
  }

  private void jmiSaveActionPerformed(java.awt.event.ActionEvent evt) {
    Basis basis = getActualBasis();

    if (basis != null) {
      basis.save();
    }
  }

  private void jmiInfoActionPerformed(java.awt.event.ActionEvent evt) {
    showInfo(this);
  }

  public void executeMainVM(String projectPath, String mainVM) {
    globalPath = projectPath + File.separator + mainVM;
    globalProjectPath = projectPath;

    SwingWorker worker =
        new SwingWorker<Object, Object>() {

          DialogWait frm;

          public Object doInBackground() {
            Tools.dialogWait = new DialogWait();
            Tools.dialogWait.setVisible(true);

            frontMode = true;

            Basis basis = addBasisToVMPanel(globalPath, globalProjectPath, false);

            basis.getFrontBasis().sortSubPanels();
            basis.getCircuitBasis().adjustAllElemnts();
            basis.getFrontBasis().adjustAllElemnts();
            basis.getCircuitBasis().checkPins();
            basis.getCircuitBasis().ProcessPinDataType();

            basis.start(false);

            return null;
          }

          protected void done() {
            Tools.dialogWait.dispose();
          }
        };

    worker.execute();
  }

  public void openProject(File project) {
    String[] children = project.list();

    if (children != null && Tools.isExecutableProject(children)) {
      ProjectProperties props = Tools.openProjectFile(project);

      if (props != null) {
        int pos = Collections.binarySearch(projects, project.getPath());
        if (pos < 0) {
          projects.add(project.getPath());
          reloadProjectPanel();
        }
        executeMainVM(project.getPath(), props.mainVM);
      }
    } else if (children != null && Tools.isProject(children)) {

      String tmp = project.getPath();

      boolean ok = false;

      for (String item : projects) {

        if (new File(item).equals(new File(tmp))) {
          ok = true;
          break;
        }
      }

      if (!ok) {
        projects.add(project.getPath());
        reloadProjectPanel();
      }
    } else {
      Tools.showMessage(
          this,
          "\""
              + project.getName()
              + "\""
              + java.util.ResourceBundle.getBundle("VisualLogic/Messages")
                  .getString("is not a project"));
    }
  }

  class LabelAccessory extends JLabel implements PropertyChangeListener {

    private ImageIcon iconOK =
        new javax.swing.ImageIcon(getClass().getResource("/Images/is-project.png"));
    private ImageIcon iconX =
        new javax.swing.ImageIcon(getClass().getResource("/Images/not-project.png"));

    public LabelAccessory(JFileChooser chooser) {
      chooser.addPropertyChangeListener(this);
      setPreferredSize(new Dimension(100, 100));

      setBorder(javax.swing.BorderFactory.createTitledBorder(""));
    }

    public void propertyChange(PropertyChangeEvent changeEvent) {
      String changeName = changeEvent.getPropertyName();
      if (changeName.equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
        File file = (File) changeEvent.getNewValue();
        if (file != null) {
          if (Tools.isProject(file.list())) {
            setIcon(iconOK);
          } else {
            setIcon(iconX);
          }
        }
      }
    }
  }

  private void jmiOpenProjectActionPerformed(java.awt.event.ActionEvent evt) {
    JFileChooser chooser = new JFileChooser();

    chooser.setCurrentDirectory(new File(settings.getOldProjectPath()));
    chooser.setDialogTitle(
        java.util.ResourceBundle.getBundle("VisualLogic/Messages").getString("Open Project"));
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    OnlyDirectoryFilter filter = new OnlyDirectoryFilter();

    chooser.setFileFilter(filter);

    FileView view = new JavaFileView();

    chooser.setFileView(view);

    int value = chooser.showOpenDialog(this);

    if (value == JFileChooser.APPROVE_OPTION) {
      File file = chooser.getSelectedFile();
      settings.setOldProjectPath(chooser.getCurrentDirectory().getPath());
      openProject(file);
    }
  }

  private void createNewProject(
      String projectName, boolean createMainVM, String mainVMFilename, String projectType) {
    if (!new File(projectName).exists()) {
      try {
        String str = "";

        File project = new File(projectName);
        project.mkdir();

        File projectFile =
            new File(project.getAbsolutePath() + File.separator + "project.myopenlab");

        projectFile.createNewFile();

        if (createMainVM) {
          String filename = projectName + File.separator + mainVMFilename + ".vlogic";
          Basis basis = new Basis(this, this.elementPath);
          basis.saveToFile(filename, false);

          String strX = new File(filename).getName();
          str += "MAINVM = " + strX + "\n";
        }

        if (projectType.equalsIgnoreCase("SPS")) {
          str += "PROJECTTYPE = SPS\n";
        }

        Tools.saveText(projectFile, str);
      } catch (Exception ex) {
        Tools.showMessage(
            java.util.ResourceBundle.getBundle("VisualLogic/Messages").getString("Could not create")
                + " "
                + projectName);
      }
    } else {
      Tools.showMessage(
          this,
          java.util.ResourceBundle.getBundle("VisualLogic/Messages")
                  .getString("Project already exist")
              + " : "
              + projectName);
    }
  }

  private void jmiNewProjectActionPerformed(java.awt.event.ActionEvent evt) {

    DialogNewProject frm = new DialogNewProject(this, true);
    frm.setVisible(true);
    if (frm.result) {
      String projectName = frm.projectName;

      File f = new File(projectName);
      if (!f.exists()) {

        projectName = projectName.replace("\\", File.separator);
        projectName = projectName.replace("/", File.separator);

        createNewProject(projectName, frm.createMainVM, frm.mainVMFilename, frm.projectType);
        projects.add(projectName);
        reloadProjectPanel();
      } else {
        Tools.showMessage(
            java.util.ResourceBundle.getBundle("VisualLogic/Messages")
                    .getString("Directory already exist")
                + " : "
                + f.getAbsolutePath());
      }
    }
  }

  private void formWindowClosing(java.awt.event.WindowEvent evt) {
    closeApplication();
  }

  private void formWindowLostFocus(java.awt.event.WindowEvent evt) {}

  private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {}

  private void formFocusGained(java.awt.event.FocusEvent evt) {}

  private void jButtonStep_OActionPerformed(java.awt.event.ActionEvent evt) {
    Basis basis = getActualBasis();

    if (basis != null) {
      basis.step();
    }
  }

  private void jButtonResume_NActionPerformed(java.awt.event.ActionEvent evt) {
    Basis basis = getActualBasis();
    if (basis != null) {
      basis.resume();
    }
  }

  private void jButtonPause_MActionPerformed(java.awt.event.ActionEvent evt) {
    Basis basis = getActualBasis();
    if (basis != null) {
      basis.pause();
    }
  }

  private void jButtonStop_LActionPerformed(java.awt.event.ActionEvent evt) {
    Basis basis = getActualBasis();
    if (basis != null) {
      basis.stop();
    }
  }

  public void ownerMessage(String message) {
    System.out.println("Message=" + message);
  }

  private void runPLCSimulator(String code) {
    // Save the ASM file to the MyOpenLab User Dir
    String fileNameDest_ASM = getUserURL().getFile() + "\\mcu_code.asm";

    Tools.saveText(new File(fileNameDest_ASM), code);

    String jarFileDir = elementPath + "\\..\\simulator";
    String jarFile = new File(jarFileDir + "\\Simulator.jar").getAbsolutePath();

    if (new File(jarFile).exists()) {
      ProcessBuilder builder =
          new ProcessBuilder("cmd", "/c", "start", "javaw", "-jar", jarFile, fileNameDest_ASM);

      builder.directory(new File(jarFileDir));

      try {
        Process simulatorProcess = builder.start();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    } else {
      Tools.showMessage(
          this,
          "Application \"Simulator\" not found.\n"
              + "Please start the Application \"Simulator\" for registering.");
      new FrameCode(this, code);
    }
  }

  private void execSPSProject() {
    String code = generateMCUFlowChartCode();

    if (code.length() > 0) {
      code = entferneReduntanteJMPs(code);
      runPLCSimulator(code);
    }
  }

  private void jButtonStart_JActionPerformed(java.awt.event.ActionEvent evt) {
    Basis basis = getActualBasis();
    if (basis != null) {

      if (basis.projectPath != null && basis.projectPath.length() > 0) {
        ProjectProperties props = Tools.openProjectFile(new File(basis.projectPath));

        if (props.projectType.equalsIgnoreCase("SPS")) {
          execSPSProject();
        } else {
          basis.start(false);
        }
      } else {
        // Start Single VM
        basis.start(false);
      }
    }
  }

  public void reinitPackage() {
    reloadElementPalettes();
    initDocs();
  }

  private void jmniUpdaterActionPerformed(java.awt.event.ActionEvent evt) {

    showRepository();
  }

  private void jButtonRepository_TActionPerformed(java.awt.event.ActionEvent evt) {
    showRepository();
  }

  private void jPaneVMPanelsMousePressed(java.awt.event.MouseEvent evt) {}

  private void jPanel8MouseDragged(java.awt.event.MouseEvent evt) {
    Rectangle rect = getBounds();
    int w = getWidth() + rect.x - evt.getXOnScreen() - 7;

    if (w < 5) {
      w = 5;
    }

    jPanelHelpWindow.setPreferredSize(new Dimension(w, 100));
    jPanelHelpWindow.updateUI();
  }

  private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {

    int xx = jPanelHelpWindow.getWidth();

    if (xx < 20) {

      jPanelHelpWindow.setPreferredSize(new Dimension(settings.getOldRightSplitterPos(), 100));
      jButton13.setIcon(
          new javax.swing.ImageIcon(getClass().getResource("/Images/arrow-black-right.png")));
    } else {
      settings.setOldRightSplitterPos(xx);
      jPanelHelpWindow.setPreferredSize(new Dimension(10, 100));
      jButton13.setIcon(
          new javax.swing.ImageIcon(getClass().getResource("/Images/arrow-black-left.png")));
    }

    jPanelHelpWindow.updateUI();
  }

  private void showRepository() {
    String tmp = new File(elementPath).getAbsolutePath();

    String distributiondir = tmp.substring(0, tmp.length() - "Elements".length());

    MyOpenLab.FormUpdate.myopenlabpath = distributiondir;

    MyOpenLab.FormUpdate frm2 = new FormUpdate();
    frm2.owner = this;

    frm2.setVisible(true);
  }

  int beginIndex = 0;
  int index = 0;

  private String removeJMPs(String code) {
    String ch = "";
    while (true) {
      ch = code.substring(index, index + 1);

      if (index >= code.length()) {
        return code;
      }

      if (ch.trim().length() == 0) {
        // End of the recognized label
        String label = code.substring(beginIndex, index);
        System.out.println("" + label);

        int lastIndex = index;

        while (true) {
          // Go until there are no blank lines/characters
          ch = code.substring(index, index + 1);
          index++;

          if (index >= code.length()) {
            return code;
          }

          if (ch.trim().length() > 0) {
            index--;
            int start2 = index;

            while (true) {
              ch = code.substring(index, index + 1);
              index++;

              if (index >= code.length()) {
                return code;
              }

              if (ch.trim().length() == 0) {
                String gg = code.substring(start2, index - 2);

                if (gg.equalsIgnoreCase(label)) {
                  String newCode = code.substring(0, beginIndex - 5);
                  newCode += code.substring(lastIndex, code.length());
                  code = newCode;
                }

                return code;
              }
            }
          }
        }
      }

      index++;
    }
  }

  private String entferneReduntanteJMPs(String code) {
    index = 0;
    while (true) {
      index = code.indexOf("JMP", index);

      if (index == -1) {
        break;
      }

      index += 4;
      System.out.println("" + code.length());

      if (index >= code.length()) {
        break;
      }

      String ch = "";
      beginIndex = index;

      code = removeJMPs(code);
    }
    return code;
  }

  public Image createImageOfElement(Element element) {

    File file = new File(element.elementPath + element.mainPath);
    DFProperties definition_def = Tools.getPropertiesFromDefinitionFile(file);

    ImageIcon icon =
        new ImageIcon(
            element.elementPath + element.mainPath + File.separator + definition_def.iconFilename);
    return icon.getImage();
  }

  private void loadDoc(Element element, String filename, JEditorPane pane) {
    URL url = null;
    String nope = element.elementPath + File.separator + "nope.html";

    if (!new File(filename).exists()) {
      filename = nope;
    }

    try {
      url = new URL("file:" + filename);
    } catch (Exception ex) {

    }

    try {
      pane.setContentType("text/html");
      pane.setPage(url);
    } catch (Exception e) {

    }
  }

  public void activate_DocFrame(Element element) {

    try {
      panelDoc.openElementDocFile(this, element);
    } catch (Exception ex) {
      Logger.getLogger(FrameMain.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private javax.swing.JButton jButton13;
  private javax.swing.JButton jButtonAbout_U;
  private javax.swing.JButton jButtonAnalogWindow_P;
  private javax.swing.JButton jButtonConsoleOut_S;
  private javax.swing.JButton jButtonDebug_K;
  private javax.swing.JButton jButtonDigitalWindow_Q;
  private javax.swing.JButton jButtonNewProject_A;
  private javax.swing.JButton jButtonOpenProject_B;
  private javax.swing.JButton jButtonOptions_G;
  private javax.swing.JButton jButtonPause_M;
  private javax.swing.JButton jButtonRedo_E;
  private javax.swing.JButton jButtonRefreshVM_F;
  private javax.swing.JButton jButtonRepository_T;
  private javax.swing.JButton jButtonResume_N;
  private javax.swing.JButton jButtonSave_C;
  private javax.swing.JButton jButtonStart_J;
  private javax.swing.JButton jButtonStep_O;
  private javax.swing.JButton jButtonStop_L;
  private javax.swing.JButton jButtonTestPointWin_R;
  private javax.swing.JButton jButtonUndo_D;
  private javax.swing.JButton jButtonVariables_H;
  private javax.swing.JButton jButtonWireLegends_I;
  private javax.swing.JComboBox jComboBoxElementList;
  private javax.swing.JLabel jLabelDebugDelay;
  private javax.swing.JMenuBar jMenuBar_MainMenuBar;
  public javax.swing.JTabbedPane jPaneVMPanels;
  private javax.swing.JPanel jPanel8;
  private javax.swing.JPanel jPanel9;
  private javax.swing.JPanel jPanelCenter;
  private javax.swing.JPanel jPanelDebugTimeSettings;
  private javax.swing.JPanel jPanelElementList;
  private javax.swing.JPanel jPanelElementPalette;
  private javax.swing.JPanel jPanelHelpWindow;
  private javax.swing.JPanel jPanelLeft;
  private javax.swing.JPanel jPanelMainToolsMenuAndDebugTime;
  private static javax.swing.JPanel jPanelProjectExplorer;
  private javax.swing.JPanel jPanelPropertyEditor;
  private javax.swing.JPanel jPanelVMsWorkSpace;
  private javax.swing.JPopupMenu jPopupMenu1;
  public javax.swing.JPopupMenu jPopupMenu2;
  private javax.swing.JSeparator jSeparator1;
  private javax.swing.JSeparator jSeparator2;
  private javax.swing.JSpinner jSpinnerDebugDelayTime;
  public javax.swing.JSplitPane jSplitPane1;
  public javax.swing.JSplitPane jSplitPane3;
  private javax.swing.JTabbedPane jTabPropertyEditor;
  private javax.swing.JToolBar jToolBar_MainToolbar;
  private javax.swing.JMenuItem jmiClearWindow;
  private javax.swing.JMenuItem jmiCloseWindow;
  private javax.swing.JMenuItem jmiCopy;
  private javax.swing.JMenuItem jmiCopyToClip;
  private javax.swing.JMenuItem jmiCut;
  private javax.swing.JMenuItem jmiEigenschaten;
  private javax.swing.JMenuItem jmiInfo;
  private javax.swing.JMenuItem jmiLegend;
  private javax.swing.JMenuItem jmiNewProject;
  private javax.swing.JMenuItem jmiOpenProject;
  private javax.swing.JMenuItem jmiPaste;
  private javax.swing.JMenuItem jmiPause;
  private javax.swing.JMenuItem jmiRedo;
  private javax.swing.JMenuItem jmiResume;
  private javax.swing.JMenuItem jmiSave;
  private javax.swing.JMenuItem jmiSaveAsJPG;
  private javax.swing.JMenuItem jmiSaveAsModul;
  private javax.swing.JMenuItem jmiSelectAny;
  private javax.swing.JMenuItem jmiShowAnalogWindow;
  private javax.swing.JMenuItem jmiShowDigitalWindow;
  private javax.swing.JMenuItem jmiShowTestpointWindow;
  private javax.swing.JMenuItem jmiStart;
  private javax.swing.JMenuItem jmiStep;
  private javax.swing.JMenuItem jmiStop;
  private javax.swing.JMenuItem jmiUndo;
  private javax.swing.JMenuItem jmiVariableWatcher;
  private javax.swing.JMenuItem jmniChooseLanguage;
  private javax.swing.JMenuItem jmniCloseAllVMs;
  private javax.swing.JMenuItem jmniCloseAllVms;
  private javax.swing.JMenuItem jmniCreateNewJavaComponent;
  private javax.swing.JMenuItem jmniCreateSubVM;
  private javax.swing.JMenuItem jmniDefineVariables;
  private javax.swing.JMenuItem jmniDeletePasswordProtection;
  private javax.swing.JMenuItem jmniOptions;
  private javax.swing.JMenuItem jmniPasswordProtection;
  private javax.swing.JMenuItem jmniPrintVM;
  private javax.swing.JMenuItem jmniSaveAsSingleVM;
  private javax.swing.JMenuItem jmniShowErrorsAndWarnings;
  private javax.swing.JMenuItem jmniUpdate;
  private javax.swing.JMenuItem jmniUpdater;
  private javax.swing.JMenu jmnuDatei;
  private javax.swing.JMenu jmnuDocs;
  private javax.swing.JMenu jmnuEdit;
  private javax.swing.JMenu jmnuExtras;
  private javax.swing.JMenu jmnuHelp;
  private javax.swing.JMenuItem jmnuOpenSingleVM;
  private javax.swing.JMenu jmnuVM;
  private javax.swing.JMenu jmnuWindow;
}
