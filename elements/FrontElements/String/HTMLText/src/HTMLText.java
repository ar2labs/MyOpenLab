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
import java.text.*;
import tools.*;

public class HTMLText extends JVSMain {
  private ExternalIF panelElement;
  private Image image;
  private VSString in;
  private String oldValue;
  private boolean firstTime = true;

  public void paint(java.awt.Graphics g) {
    drawImageCentred(g, image);
  }

  public void init() {
    initPins(0, 0, 0, 1);
    setSize(45, 40);

    element.jSetInnerBorderVisibility(true);
    initPinVisibility(false, false, false, true);

    image = element.jLoadImage(element.jGetSourcePath() + "icon.gif");

    setPin(0, ExternalIF.C_STRING, element.PIN_INPUT);

    element.jSetCaptionVisible(false);
    element.jSetCaption("HTMLText");

    setName("HTMLText");
  }

  public void initInputPins() {
    in = (VSString) element.getPinInputReference(0);
  }

  public void initOutputPins() {
    firstTime = true;
  }

  public void process() {
    if (in != null) {
      if (in.getValue() != oldValue || firstTime) {
        oldValue = in.getValue();
        firstTime = false;
        panelElement = element.getPanelElement();
        if (panelElement != null) panelElement.jProcessPanel(0, 0.0, in);
      }
    }
  }
}
