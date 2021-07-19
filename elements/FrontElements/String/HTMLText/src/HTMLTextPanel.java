// ********************************
// * Autor : Robinson Javier Velasquez
// * Date  : Ago-01-2016
// * Email : javiervelasquez@gmail.com
// * Description: Multiline Pane Text Elements with Horizontal Auto Scroll and enabled vertical
// mouse Scroll.
// * licence : non commercial use.
// ********************************

import VisualLogic.*;
import VisualLogic.variables.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import tools.*;

public class HTMLTextPanel extends JVSMain implements PanelIF {
  private int width = 250, height = 155;

  private JTextPane text = new JTextPane();

  StyledDocument Text_Style = text.getStyledDocument();
  SimpleAttributeSet Alin_Temp = new SimpleAttributeSet();

  private VSString strText = new VSString();
  private VSInteger orientation = new VSInteger();
  private VSColor Element_Color_Bakground = new VSColor(Color.WHITE);

  private VSFont font = new VSFont(new Font("Dialog", 0, 11));
  private VSColor fontColor = new VSColor(Color.BLACK);
  private VSBoolean Label_Use = new VSBoolean();

  public void processPanel(int pinIndex, double value, Object obj) {
    if (obj instanceof VSString) {
      String str = ((VSString) obj).getValue();

      Set_Text_Style(orientation.getValue());

      if (Label_Use.getValue()) {
        str = strText.getValue();
      }
      text.setContentType("text/html");

      text.setText(str);
      element.jRepaint();
    }
  }

  public void paint(java.awt.Graphics g) {}

  public void init() {
    initPins(0, 0, 0, 0);
    setSize(width, height);
    element.jSetInnerBorderVisibility(false);
    initPinVisibility(false, false, false, false);

    element.jSetResizable(true);
    text.setContentType("text/html");
    text.setText("<HTML> <HEAD> <TITLE>Example 1 JV</TITLE> </HEAD> <BODY> HTML Ausgabe Preview.<BR> <FONT"
            + " SIZE=\"1\">Text Size 1.</FONT><BR> <FONT SIZE=\"2\">Text Size 2.</FONT><BR> <FONT"
            + " SIZE=\"4\">Text Size 4.</FONT><BR> <FONT SIZE=\"+1\">Text Size +1 (Same Size"
            + " 4).</FONT><BR> <FONT FACE=\"Arial\" SIZE=\"5\" COLOR=\"F39C12\">Formated Text"
            + " JV.</FONT> </BODY> </HTML>");

    setName("HTML/Text_Indicator_JV");
    element.jSetCaptionVisible(false);
  }

  public void xOnInit() {
    try {
      JPanel panel = element.getFrontPanel();
      panel.setLayout(new java.awt.BorderLayout());

      // Create a text pane
      JScrollPane paneScrollPane = new JScrollPane(text);
      paneScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      paneScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      paneScrollPane.setPreferredSize(new Dimension(width, height));
      paneScrollPane.setMinimumSize(new Dimension(10, 10));

      element.setAlwaysOnTop(true);

      text.setForeground(fontColor.getValue());
      text.setBackground(Element_Color_Bakground.getValue());
      text.setEditable(false);
      text.setAutoscrolls(false);

      if (Label_Use.getValue()) {
        strText.setValue(text.getText());
      } else {
        text.setContentType("text/html");
        text.setText(
            "<HTML> <HEAD> <TITLE>Example 1 JV</TITLE> </HEAD> <BODY> HTML Ausgabe Preview.<BR>"
                + " <FONT SIZE=\"1\">Text Size 1.</FONT><BR> <FONT SIZE=\"2\">Text Size"
                + " 2.</FONT><BR> <FONT SIZE=\"4\">Text Size 4.</FONT><BR> <FONT SIZE=\"+1\">Text"
                + " Size +1 (Same Size 4).</FONT><BR> <FONT FACE=\"Arial\" SIZE=\"5\""
                + " COLOR=\"F39C12\">Formated Text JV.</FONT> </BODY> </HTML>");
      }
      panel.add(paneScrollPane, java.awt.BorderLayout.CENTER);

      // Code added to avoid JText Focus Lost Error
      text.setEditable(false);
      text.setDisabledTextColor(fontColor.getValue());

      FocusListener FocusAr[] = text.getFocusListeners();

      if (FocusAr != null) {
        for (int i = 0; i < FocusAr.length; i++) {
          text.removeFocusListener(FocusAr[i]);
        }
      }

      Set_Text_Style(1);
      orientation.setValue(1);

      element.jRepaint();
    } catch (Exception ex) {
      System.out.println(ex);
    }
  }

  public void Set_Text_Style(int Align) { // Aling 0=Left 1=Center (default) 2=Right 3=Justified

    StyleConstants.setAlignment(Alin_Temp, StyleConstants.ALIGN_CENTER);

    if (Align == 0) {
      StyleConstants.setAlignment(Alin_Temp, StyleConstants.ALIGN_LEFT);
    }
    if (Align == 2) {
      StyleConstants.setAlignment(Alin_Temp, StyleConstants.ALIGN_RIGHT);
    }
    if (Align == 3) {
      StyleConstants.setAlignment(Alin_Temp, StyleConstants.ALIGN_JUSTIFIED);
    }

    Text_Style.setParagraphAttributes(0, Text_Style.getLength(), Alin_Temp, false);

    text.setContentType("text/html");
  }

  public void stop() {
    if (Label_Use.getValue()) {
      text.setContentType("text/html");
      text.setText(strText.getValue());
    } else {
      text.setContentType("text/html");
      text.setText("");
    }
  }

  public void onDispose() {
    JPanel panel = element.getFrontPanel();
    panel.removeAll();
  }

  public void setPropertyEditor() {
    element.jAddPEItem("Schriftart", font, 0, 0);
    element.jAddPEItem("Beschreibung", strText, 0, 0);
    element.jAddPEItem("Farbe", fontColor, 0, 0);
    element.jAddPEItem("Ausrichtung Hoz", orientation, 0, 3);
    element.jAddPEItem("Farbe Fondo", Element_Color_Bakground, 0, 0);
    element.jAddPEItem("Als Etikett verwenden", Label_Use, 0, 1);
    localize();
  }

  private void localize() {
    int d = 6;
    String language;

    language = "en_US";

    element.jSetPEItemLocale(d + 0, language, "Font");
    element.jSetPEItemLocale(d + 1, language, "Text");
    element.jSetPEItemLocale(d + 2, language, "Text_Color");
    element.jSetPEItemLocale(d + 3, language, "Align Hoz");
    element.jSetPEItemLocale(d + 4, language, "BackGround Color");
    element.jSetPEItemLocale(d + 5, language, "Use as Label");

    language = "es_ES";

    element.jSetPEItemLocale(d + 0, language, "Fuente");
    element.jSetPEItemLocale(d + 1, language, "Texto_Inicial");
    element.jSetPEItemLocale(d + 2, language, "Color_Texto");
    element.jSetPEItemLocale(d + 3, language, "Alineacion_Texto");
    element.jSetPEItemLocale(d + 4, language, "Color_de_Fondo");
    element.jSetPEItemLocale(d + 5, language, "Usar_como_Label");

    language = "pt_BR";

    element.jSetPEItemLocale(d + 0, language, "Fonte");
    element.jSetPEItemLocale(d + 1, language, "Texto Inicial");
    element.jSetPEItemLocale(d + 2, language, "Cor do Texto");
    element.jSetPEItemLocale(d + 3, language, "Alinhamento");
    element.jSetPEItemLocale(d + 4, language, "Cor de Fundo");
    element.jSetPEItemLocale(d + 5, language, "Usar Texto Inicial");
  }

  public void propertyChanged(Object o) {

    if (o.equals(strText)) {
      text.setContentType("text/html");
      text.setText(strText.getValue());
    }
    if (o.equals(font)) {
      text.setFont(font.getValue());
    }
    if (o.equals(fontColor)) {
      text.setForeground(fontColor.getValue());
    }
    if (o.equals(orientation)) {
      int hali = orientation.getValue();

      Set_Text_Style(hali);
    }
    if (o.equals(Element_Color_Bakground)) {
      text.setBackground(Element_Color_Bakground.getValue());
    }
  }

  @Override
  public void loadFromStream(java.io.FileInputStream fis) {
    font.loadFromStream(fis);
    text.setFont(font.getValue());
    strText.loadFromStream(fis);
    fontColor.loadFromStream(fis);
    text.setForeground(fontColor.getValue());
    orientation.loadFromStream(fis);
    Set_Text_Style(orientation.getValue());
    Element_Color_Bakground.loadFromStream(fis);
    text.setBackground(Element_Color_Bakground.getValue());
    Label_Use.loadFromStream(fis);
    if (Label_Use.getValue()) {
      text.setContentType("text/html");
      text.setText(strText.getValue());
    }
  }

  @Override
  public void saveToStream(java.io.FileOutputStream fos) {
    font.setValue(text.getFont());
    font.saveToStream(fos);
    strText.setValue(text.getText());
    strText.saveToStream(fos);
    fontColor.setValue(text.getForeground());
    fontColor.saveToStream(fos);
    orientation.saveToStream(fos);
    Element_Color_Bakground.saveToStream(fos);
    Label_Use.saveToStream(fos);
  }
}
