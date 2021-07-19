package CreateNewGroup;

import VisualLogic.DFProperties;
import VisualLogic.DialogSaveAsModul;
import VisualLogic.FrameMain;
import VisualLogic.Tools;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DialogCreateNewGroup extends javax.swing.JDialog {

  private ImageComponent image32 = null;
  public String path = "";
  private String mode = "";
  public String directory = "";
  public boolean resultcode = false;

  /** Creates new form NewJDialog */
  public DialogCreateNewGroup(java.awt.Frame parent, boolean modal, String mode, String path) {

    super(parent, modal);

    this.mode = mode;
    this.path = path;
    this.mode = mode;

    initComponents();

    jButton4.setVisible(false);
    jTextField1.setText("");

    if (mode.equalsIgnoreCase("EDIT")) {
      java.util.ResourceBundle bundle =
          java.util.ResourceBundle.getBundle("CreateNewGroup/DialogCreateNewGroup");
      jButton1.setText(bundle.getString("TO_SAVE"));

      setTitle(bundle.getString("TITLE"));
    }

    String type = getElementType();
    jLabel15.setText(type);

    image32 = new ImageComponent();

    String icon32 = new File(FrameMain.elementPath + "/stdlib32.png").getAbsolutePath();

    image32.setFilename(icon32);
    image32.setLocation(0, 0);
    image32.setPreferredSize(new Dimension(32, 32));

    jPanel1.add(image32, BorderLayout.CENTER);

    // Show window in the middle
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    this.setLocation(
        dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    Date date = new Date();

    txtDate.setText(dateFormat.format(date));

    load(path);
  }

  private String getElementType() {
    String type = "";

    if (path.contains("CircuitElements")) {
      type = "CircuitElements";
    }

    if (path.contains("FrontElements")) {
      type = "FrontElements";
    }

    if (path.contains("Documentations")) {
      type = "Documentations";
    }

    if (path.contains("VirtualMachines")) {
      type = "VirtualMachines";
    }

    return type;
  }

  public boolean load(String directory) {
    this.directory = directory;

    if (mode.equalsIgnoreCase("EDIT")) {

      if (new File(directory + "/description_de.html").exists()) {
        txtShortDescriptionDE.setText(Tools.readFile(directory + "/description_de.html").trim());
      }

      if (new File(directory + "/description_en.html").exists()) {
        txtShortDescriptionEN.setText(Tools.readFile(directory + "/description_en.html").trim());
      }

      if (new File(directory + "/description_es.html").exists()) {
        txtShortDescriptionES.setText(Tools.readFile(directory + "/description_es.html").trim());
      }

      if (new File(directory + "/description_pt.html").exists()) {
        txtShortDescriptionPT.setText(Tools.readFile(directory + "/description_pt.html").trim());
      }

      try {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document;

        File info_xml = new File(directory + "/info.xml");

        jTextField1.setText(directory);

        if (!info_xml.exists()) {
          return false;
        } else {

          String icon_fn = "";

          if (new File(directory + "/definition.def").exists()) {
            DFProperties definition_def =
                Tools.getPropertiesFromDefinitionFile(new File(directory));
            icon_fn = directory + "/" + definition_def.iconFilename;
          } else {
            icon_fn = directory + "/icon32.png";
          }

          if (new File(icon_fn).exists()) {
            image32.setFilename(icon_fn);
            image32.repaint();

            jButton4.setVisible(true);
          }

          document = builder.parse(info_xml);

          NodeList nodes = document.getFirstChild().getChildNodes();

          for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            String name = node.getNodeName();
            String value = node.getTextContent();

            if (name.equalsIgnoreCase("caption")) {
              txtTitleDE.setText(value);
            }

            if (name.equalsIgnoreCase("caption_en")) {
              txtTitleEN.setText(value);
            }

            if (name.equalsIgnoreCase("caption_es")) {
              txtTitleES.setText(value);
            }

            if (name.equalsIgnoreCase("caption_pt")) {
              txtTitlePT.setText(value);
            }

            if (name.equalsIgnoreCase("author")) {
              txtAuthor.setText(value);
            }

            if (name.equalsIgnoreCase("email")) {
              txtEmail.setText(value);
            }

            if (name.equalsIgnoreCase("date")) {
              txtDate.setText(value);
            }

            if (name.equalsIgnoreCase("web")) {
              txtWebsite.setText(value);
            }

            if (name.equalsIgnoreCase("version")) {
              String[] vers = value.split("\\.");

              cboVersion1.setSelectedItem(vers[0]);
              cboVersion2.setSelectedItem(vers[1]);
              cboVersion3.setSelectedItem(vers[2]);
              cboVersion4.setSelectedItem(vers[3]);
            }
          }
        }
      } catch (SAXException | IOException | ParserConfigurationException ex) {
        Logger.getLogger(DialogCreateNewGroup.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

    return true;
  }

  @SuppressWarnings("unchecked")
  private void initComponents() {

    jButton1 = new javax.swing.JButton();
    jScrollPane1 = new javax.swing.JScrollPane();
    txtShortDescriptionDE = new javax.swing.JTextArea();
    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    txtTitleDE = new javax.swing.JTextField();
    jLabel4 = new javax.swing.JLabel();
    jLabel5 = new javax.swing.JLabel();
    jLabel6 = new javax.swing.JLabel();
    jLabel7 = new javax.swing.JLabel();
    txtAuthor = new javax.swing.JTextField();
    txtWebsite = new javax.swing.JTextField();
    txtDate = new javax.swing.JTextField();
    jLabel8 = new javax.swing.JLabel();
    txtTitleEN = new javax.swing.JTextField();
    txtTitleES = new javax.swing.JTextField();
    txtTitlePT = new javax.swing.JTextField();
    jLabel9 = new javax.swing.JLabel();
    jLabel10 = new javax.swing.JLabel();
    cboVersion1 = new javax.swing.JComboBox();
    cboVersion2 = new javax.swing.JComboBox();
    cboVersion3 = new javax.swing.JComboBox();
    cboVersion4 = new javax.swing.JComboBox();
    jLabel12 = new javax.swing.JLabel();
    txtEmail = new javax.swing.JTextField();
    jLabel14 = new javax.swing.JLabel();
    cboCategorie = new javax.swing.JComboBox();
    jButton3 = new javax.swing.JButton();
    jTextField1 = new javax.swing.JTextField();
    jLabel13 = new javax.swing.JLabel();
    jPanel2 = new javax.swing.JPanel();
    jButton4 = new javax.swing.JButton();
    jButton5 = new javax.swing.JButton();
    jLabel3 = new javax.swing.JLabel();
    jPanel1 = new javax.swing.JPanel();
    jButton2 = new javax.swing.JButton();
    jLabel11 = new javax.swing.JLabel();
    jLabel15 = new javax.swing.JLabel();
    jLabel16 = new javax.swing.JLabel();
    jScrollPane2 = new javax.swing.JScrollPane();
    txtShortDescriptionES = new javax.swing.JTextArea();
    jScrollPane3 = new javax.swing.JScrollPane();
    txtShortDescriptionEN = new javax.swing.JTextArea();
    txtShortDescriptionPT = new javax.swing.JTextArea();
    jLabel17 = new javax.swing.JLabel();
    jLabel18 = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    java.util.ResourceBundle bundle =
        java.util.ResourceBundle.getBundle("CreateNewGroup/DialogCreateNewGroup");
    setTitle(bundle.getString("CREATE_NEW_PACKAGE"));
    setResizable(false);

    jButton1.setText(bundle.getString("CREATE_PACKAGE"));
    jButton1.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
          }
        });

    jLabel1.setText(bundle.getString("TITLE_DEUTSCH"));

    jLabel2.setText(bundle.getString("VERSION"));

    jLabel4.setText(bundle.getString("EMAIL"));

    jLabel5.setText(bundle.getString("WEBSITE"));

    jLabel6.setText(bundle.getString("AUTOR"));

    jLabel7.setText(bundle.getString("REQUIRED"));

    jLabel8.setText(bundle.getString("DESCRIPTION_PORTUGUESE"));

    jLabel9.setText(bundle.getString("TITLE_ENGLISH"));

    jLabel10.setText(bundle.getString("TITLE_SPANISH"));

    jLabel14.setText(bundle.getString("TITLE_PORTUGUESE"));

    txtWebsite.setToolTipText("");

    txtTitleEN.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtTitleENActionPerformed(evt);
          }
        });

    cboVersion1.setModel(
        new javax.swing.DefaultComboBoxModel(
            new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}));
    cboVersion1.setSelectedIndex(1);

    cboVersion2.setModel(
        new javax.swing.DefaultComboBoxModel(
            new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}));

    cboVersion3.setModel(
        new javax.swing.DefaultComboBoxModel(
            new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}));

    cboVersion4.setModel(
        new javax.swing.DefaultComboBoxModel(
            new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}));

    jLabel12.setText(bundle.getString("DATE"));

    cboCategorie.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Education"}));
    cboCategorie.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            cboCategorieActionPerformed(evt);
          }
        });

    jButton3.setText(bundle.getString("CANCEL"));
    jButton3.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton3ActionPerformed(evt);
          }
        });

    jTextField1.setEditable(false);
    jTextField1.setText("jTextField1");

    jLabel13.setText(bundle.getString("PATH"));

    jButton4.setText(bundle.getString("EDIT_ICON"));
    jButton4.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton4ActionPerformed(evt);
          }
        });

    jButton5.setText(bundle.getString("RELOAD"));
    jButton5.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton5ActionPerformed(evt);
          }
        });

    jLabel3.setText(bundle.getString("ICON_32"));

    jPanel1.setBackground(new java.awt.Color(255, 255, 153));
    jPanel1.setBorder(
        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
    jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    jPanel1.setPreferredSize(new java.awt.Dimension(38, 38));
    jPanel1.setLayout(new java.awt.BorderLayout());

    jButton2.setText(bundle.getString("SEARCH"));
    jButton2.addActionListener(
        new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
          }
        });

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                jPanel2Layout
                    .createSequentialGroup()
                    .addGap(2, 2, 2)
                    .addComponent(
                        jLabel3,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        94,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(
                        jPanel1,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(14, 14, 14)
                    .addComponent(jButton2)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButton5)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButton4)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                jPanel2Layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addGroup(
                        jPanel2Layout
                            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(
                                jPanel2Layout
                                    .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton2)
                                    .addComponent(jButton5)
                                    .addComponent(jButton4))
                            .addComponent(jLabel3)
                            .addComponent(
                                jPanel1,
                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

    jLabel11.setText(bundle.getString("TYPE"));

    jLabel15.setText("jLabel15");

    txtShortDescriptionPT.setColumns(20);
    txtShortDescriptionPT.setRows(5);
    jScrollPane1.setViewportView(txtShortDescriptionPT);

    txtShortDescriptionEN.setColumns(20);
    txtShortDescriptionEN.setRows(5);
    jScrollPane3.setViewportView(txtShortDescriptionEN);

    jLabel16.setText(bundle.getString("DESCRIPTION_ENGLISCH"));

    txtShortDescriptionES.setColumns(20);
    txtShortDescriptionES.setRows(5);
    jScrollPane2.setViewportView(txtShortDescriptionES);

    jLabel17.setText(bundle.getString("DESCRIPTION_SPANISCH"));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addGroup(
                        layout
                            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(
                                jPanel2,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                            .addComponent(jTextField1)
                            .addComponent(jScrollPane1)
                            .addGroup(
                                layout
                                    .createSequentialGroup()
                                    .addGroup(
                                        layout
                                            .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(
                                                layout
                                                    .createSequentialGroup()
                                                    .addGap(15, 15, 15)
                                                    .addComponent(
                                                        jLabel1,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(25, 25, 25)
                                                    .addComponent(
                                                        txtTitleDE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        185,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(
                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                layout
                                                    .createSequentialGroup()
                                                    .addComponent(
                                                        jLabel10,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(25, 25, 25)
                                                    .addComponent(
                                                        txtTitleES,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        185,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(
                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                layout
                                                    .createSequentialGroup()
                                                    .addComponent(
                                                        jLabel9,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(25, 25, 25)
                                                    .addComponent(
                                                        txtTitleEN,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        185,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(
                                                layout
                                                    .createSequentialGroup()
                                                    .addGroup(
                                                        layout
                                                            .createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment
                                                                    .LEADING)
                                                            .addComponent(jLabel14)
                                                            .addComponent(jLabel11))
                                                    .addGap(25, 25, 25)
                                                    .addGroup(
                                                        layout
                                                            .createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment
                                                                    .LEADING)
                                                            .addComponent(jLabel15)
                                                            .addComponent(
                                                                txtTitlePT,
                                                                javax.swing.GroupLayout
                                                                    .PREFERRED_SIZE,
                                                                185,
                                                                javax.swing.GroupLayout
                                                                    .PREFERRED_SIZE))))
                                    .addGap(18, 18, 18)
                                    .addGroup(
                                        layout
                                            .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel5))
                                    .addGap(18, 18, 18)
                                    .addGroup(
                                        layout
                                            .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtEmail)
                                            .addComponent(
                                                txtAuthor,
                                                javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtDate)
                                            .addComponent(txtWebsite)
                                            .addGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                layout
                                                    .createSequentialGroup()
                                                    .addComponent(
                                                        cboVersion1,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(
                                                        javax.swing.LayoutStyle.ComponentPlacement
                                                            .RELATED)
                                                    .addComponent(
                                                        cboVersion2,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(
                                                        javax.swing.LayoutStyle.ComponentPlacement
                                                            .RELATED)
                                                    .addComponent(
                                                        cboVersion3,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(
                                                        javax.swing.LayoutStyle.ComponentPlacement
                                                            .RELATED)
                                                    .addComponent(
                                                        cboVersion4,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(0, 98, Short.MAX_VALUE))))
                            .addGroup(
                                layout
                                    .createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)
                                    .addComponent(jButton1)
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton3))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(
                                layout
                                    .createSequentialGroup()
                                    .addGroup(
                                        layout
                                            .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel16)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel17))
                                    .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane3))
                    .addContainerGap()));
    layout.setVerticalGroup(
        layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel13)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(
                        jTextField1,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(
                        jPanel2,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(
                        layout
                            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(
                                layout
                                    .createSequentialGroup()
                                    .addComponent(
                                        txtAuthor,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(
                                        txtEmail,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(
                                        txtWebsite,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(36, 36, 36)
                                    .addGroup(
                                        layout
                                            .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(
                                                txtDate,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel15)))
                            .addComponent(jLabel6)
                            .addGroup(
                                layout
                                    .createParallelGroup(
                                        javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(
                                        javax.swing.GroupLayout.Alignment.LEADING,
                                        layout
                                            .createSequentialGroup()
                                            .addGroup(
                                                layout
                                                    .createParallelGroup(
                                                        javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(
                                                        txtTitleDE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel1))
                                            .addPreferredGap(
                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(
                                                layout
                                                    .createParallelGroup(
                                                        javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(
                                                        txtTitleEN,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel9)
                                                    .addComponent(jLabel4))
                                            .addPreferredGap(
                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(
                                                layout
                                                    .createParallelGroup(
                                                        javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(
                                                        txtTitleES,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel10)
                                                    .addComponent(jLabel5))
                                            .addPreferredGap(
                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                            .addGroup(
                                                layout
                                                    .createParallelGroup(
                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(
                                                        layout
                                                            .createSequentialGroup()
                                                            .addComponent(jLabel14))
                                                    .addComponent(
                                                        txtTitlePT,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(
                                        javax.swing.GroupLayout.Alignment.LEADING,
                                        layout
                                            .createSequentialGroup()
                                            .addPreferredGap(
                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                            .addGroup(
                                                layout
                                                    .createParallelGroup(
                                                        javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jLabel2)
                                                    .addComponent(
                                                        cboVersion1,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(
                                                        cboVersion2,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(
                                                        cboVersion3,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(
                                                        cboVersion4,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel8)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(
                        jScrollPane1,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        68,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel16)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(
                        jScrollPane3,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        68,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel17)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(
                        jScrollPane2,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        68,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(
                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE)
                    .addGroup(
                        layout
                            .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(
                                layout
                                    .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton1)
                                    .addComponent(jButton3))
                            .addComponent(jLabel7))
                    .addContainerGap()));

    pack();
  }

  private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {

    final JFileChooser fc = new JFileChooser();

    FileNameExtensionFilter png =
        new FileNameExtensionFilter("Portable Network Graphics (.png)", "png");
    fc.setFileFilter(png);

    int returnVal = fc.showOpenDialog(this);

    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = fc.getSelectedFile();
      image32.setFilename(file.getAbsolutePath());
      // This is where a real application would open the file.
      jPanel1.updateUI();
    }
  }

  public static boolean isDateValid(String date) {
    String DATE_FORMAT = "dd.MM.yyyy";
    try {
      DateFormat df = new SimpleDateFormat(DATE_FORMAT);
      df.setLenient(false);
      df.parse(date);
      return true;
    } catch (ParseException e) {
      return false;
    }
  }

  private String replaceAllIgnoreCase(
      final String text, final String search, final String replacement) {
    if (search.equals(replacement)) {
      return text;
    }
    final StringBuffer buffer = new StringBuffer(text);
    final String lowerSearch = search.toLowerCase(Locale.CANADA);
    int i = 0;
    int prev = 0;
    while ((i = buffer.toString().toLowerCase(Locale.CANADA).indexOf(lowerSearch, prev)) > -1) {
      buffer.replace(i, i + search.length(), replacement);
      prev = i + replacement.length();
    }
    return buffer.toString();
  }

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {

    String titleDE = txtTitleDE.getText();
    String titleEN = txtTitleEN.getText();
    String titleES = txtTitleES.getText();
    String titlePT = txtTitlePT.getText();
    String author = txtAuthor.getText();
    String web = txtWebsite.getText();
    String email = txtEmail.getText();
    String date = txtDate.getText();

    if (titleDE.trim().length() == 0) {
      JOptionPane.showMessageDialog(
          this,
          java.util.ResourceBundle.getBundle("CreateNewGroup/DialogCreateNewGroup")
              .getString("ENTER_TITLE_DE"));
      return;
    }

    if (titleEN.trim().length() == 0) {
      JOptionPane.showMessageDialog(
          this,
          java.util.ResourceBundle.getBundle("CreateNewGroup/DialogCreateNewGroup")
              .getString("ENTER_TITLE_EN"));
      return;
    }

    if (titleES.trim().length() == 0) {
      JOptionPane.showMessageDialog(
          this,
          java.util.ResourceBundle.getBundle("CreateNewGroup/DialogCreateNewGroup")
              .getString("ENTER_TITLE_ES"));
      return;
    }

    if (titlePT.trim().length() == 0) {
      JOptionPane.showMessageDialog(
          this,
          java.util.ResourceBundle.getBundle("CreateNewGroup/DialogCreateNewGroup")
              .getString("ENTER_TITLE_PT"));
      return;
    }

    if (author.trim().length() == 0) {
      JOptionPane.showMessageDialog(
          this,
          java.util.ResourceBundle.getBundle("CreateNewGroup/DialogCreateNewGroup")
              .getString("ENTER_AUTHOR"));
      return;
    }

    if (!isDateValid(date)) {
      JOptionPane.showMessageDialog(
          this,
          java.util.ResourceBundle.getBundle("CreateNewGroup/DialogCreateNewGroup")
              .getString("DATE_INVALID"));
      return;
    }

    // Datum Normieren!
    // Also Str->Date und Date->Str
    DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
    try {
      Date datex = format.parse(date);
      // date = datex.toString();

      DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
      date = df.format(datex);
    } catch (ParseException ex) {
      Logger.getLogger(DialogCreateNewGroup.class.getName()).log(Level.SEVERE, null, ex);
    }

    String type = getElementType();

    String uuid = "";
    String x = "";

    if (mode.equalsIgnoreCase("add")) {

      if (type.equalsIgnoreCase("CircuitElements")) {
        x = "ce";
      }

      if (type.equalsIgnoreCase("FrontElements")) {
        x = "fe";
      }

      if (type.equalsIgnoreCase("Documentations")) {
        x = "dc";
      }

      if (type.equalsIgnoreCase("VirtualMachines")) {
        x = "vm";
      }

      uuid = x + "-" + (UUID.randomUUID()).toString();
    } else {
      uuid = new File(directory).getName();
    }

    String xml = "";

    xml += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
    xml += "<root>\n";
    xml += "  <caption>" + titleDE + "</caption>\n";
    xml += "  <caption_en>" + titleEN + "</caption_en>\n";
    xml += "  <caption_es>" + titleES + "</caption_es>\n";
    xml += "  <caption_pt>" + titlePT + "</caption_pt>\n";
    xml += "  <categorie>" + cboCategorie.getSelectedItem().toString() + "</categorie>\n";
    xml += "  <author>" + author + "</author>\n";
    xml += "  <email>" + email + "</email>\n";
    xml += "  <web>" + web + "</web>\n";

    String v1 = cboVersion1.getSelectedItem().toString();
    String v2 = cboVersion2.getSelectedItem().toString();
    String v3 = cboVersion3.getSelectedItem().toString();
    String v4 = cboVersion4.getSelectedItem().toString();

    String version = v1 + "." + v2 + "." + v3 + "." + v4;

    xml += "  <version>" + version + "</version>\n";
    xml += "  <date>" + date + "</date>\n";

    String dest_path = "/" + type + "/" + uuid;

    xml += "  <dest_path>" + dest_path + "</dest_path>\n";
    xml += "  <type>" + type + "</type>\n";
    xml += "  <content>\n";
    xml += "  </content>\n";
    xml += "</root>";

    String definition_def = "";

    definition_def += "ISDIRECTORY     = TRUE\n";
    definition_def += "CAPTION         = " + txtTitleDE.getText() + "\n";
    definition_def += "CAPTION_EN      = " + txtTitleEN.getText() + "\n";
    definition_def += "CAPTION_ES      = " + txtTitleES.getText() + "\n";
    definition_def += "CAPTION_PT      = " + txtTitlePT.getText() + "\n";
    definition_def += "ICON            = icon32.png\n";
    definition_def += "VM_DIR_EDITABLE = TRUE";

    String newPath = path;

    Boolean success = false;

    if (mode.equalsIgnoreCase("add")) {
      success = (new File(newPath)).mkdirs();
    } else {
      newPath = path;
      success = true;
    }

    System.out.println("Created by " + newPath);

    if (!success) {
      System.out.println("Error creating " + newPath);
    } else {
      PrintWriter writer = null;
      try {
        String info_xml_filename = newPath + "/info.xml";

        writer = new PrintWriter(info_xml_filename, "UTF-8");
        writer.println(xml);
        writer.close();

        String definition_def_filename = newPath + "/definition.def";

        writer = new PrintWriter(definition_def_filename, "UTF-8");
        writer.println(definition_def);
        writer.close();

        String icon_filename = newPath + "/icon32.png";

        if (mode.equalsIgnoreCase("add")) {
          try {
            Tools.copyFileUsingStream(new File(image32.getFilename()), new File(icon_filename));
          } catch (IOException ex) {
            Logger.getLogger(DialogCreateNewGroup.class.getName()).log(Level.SEVERE, null, ex);
          }
        } else if (new File(image32.getFilename()).equals(new File(icon_filename))) {

        } else {
          try {
            Tools.copyFileUsingStream(new File(image32.getFilename()), new File(icon_filename));
          } catch (IOException ex) {
            Logger.getLogger(DialogCreateNewGroup.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
      } catch (FileNotFoundException | UnsupportedEncodingException ex) {
        Logger.getLogger(DialogCreateNewGroup.class.getName()).log(Level.SEVERE, null, ex);
      } finally {
        writer.close();
      }
    }

    String shortDescriptionDE = txtShortDescriptionDE.getText().trim();
    String shortDescriptionEN = txtShortDescriptionEN.getText().trim();
    String shortDescriptionES = txtShortDescriptionES.getText().trim();
    String shortDescriptionPT = txtShortDescriptionPT.getText().trim();

    try (PrintWriter out = new PrintWriter(newPath + "/description_de.html")) {
      out.println(shortDescriptionDE);
    } catch (FileNotFoundException ex) {
      Logger.getLogger(DialogSaveAsModul.class.getName()).log(Level.SEVERE, null, ex);
    }

    try (PrintWriter out = new PrintWriter(newPath + "/description_en.html")) {
      out.println(shortDescriptionEN);
    } catch (FileNotFoundException ex) {
      Logger.getLogger(DialogSaveAsModul.class.getName()).log(Level.SEVERE, null, ex);
    }

    try (PrintWriter out = new PrintWriter(newPath + "/description_es.html")) {
      out.println(shortDescriptionES);
    } catch (FileNotFoundException ex) {
      Logger.getLogger(DialogSaveAsModul.class.getName()).log(Level.SEVERE, null, ex);
    }

    try (PrintWriter out = new PrintWriter(newPath + "/description_pt.html")) {
      out.println(shortDescriptionPT);
    } catch (FileNotFoundException ex) {
      Logger.getLogger(DialogSaveAsModul.class.getName()).log(Level.SEVERE, null, ex);
    }

    resultcode = true;
    dispose();
  }

  private void txtTitleENActionPerformed(java.awt.event.ActionEvent evt) {
    // TODO add your handling code here:
  }

  private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {

    dispose();
  }

  private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {

    File file = new File(directory + "/icon32.png");

    Tools.openPaint(file);
  }

  private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
    image32.setFilename(directory + "/icon32.png");
    image32.repaint();
  }

  private void cboCategorieActionPerformed(java.awt.event.ActionEvent evt) {
    // TODO add your handling code here:
  }

  private javax.swing.JComboBox cboCategorie;
  private javax.swing.JComboBox cboVersion1;
  private javax.swing.JComboBox cboVersion2;
  private javax.swing.JComboBox cboVersion3;
  private javax.swing.JComboBox cboVersion4;
  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private javax.swing.JButton jButton3;
  private javax.swing.JButton jButton4;
  private javax.swing.JButton jButton5;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel10;
  private javax.swing.JLabel jLabel11;
  private javax.swing.JLabel jLabel12;
  private javax.swing.JLabel jLabel13;
  private javax.swing.JLabel jLabel14;
  private javax.swing.JLabel jLabel15;
  private javax.swing.JLabel jLabel16;
  private javax.swing.JLabel jLabel17;
  private javax.swing.JLabel jLabel18;
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
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JScrollPane jScrollPane3;
  private javax.swing.JTextField jTextField1;
  private javax.swing.JTextField txtAuthor;
  private javax.swing.JTextField txtDate;
  private javax.swing.JTextField txtEmail;
  private javax.swing.JTextArea txtShortDescriptionDE;
  private javax.swing.JTextArea txtShortDescriptionEN;
  private javax.swing.JTextArea txtShortDescriptionES;
  private javax.swing.JTextArea txtShortDescriptionPT;
  private javax.swing.JTextField txtTitleDE;
  private javax.swing.JTextField txtTitleEN;
  private javax.swing.JTextField txtTitleES;
  private javax.swing.JTextField txtTitlePT;
  private javax.swing.JTextField txtWebsite;
}
