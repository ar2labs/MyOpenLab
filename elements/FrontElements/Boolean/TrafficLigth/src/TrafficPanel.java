// *****************************************************************************
// * Element of MyOpenLab Library                                              *
// *                                                                           *
// * Copyright (C) 2004 Carmelo Salafia (cswi@gmx.de)                         *
// * Copyright (C) 2017 Javier Velásquez (javiervelasquez125@gmail.com)
//                                               *
// * This library is free software; you can redistribute it and/or modify      *
// * it under the terms of the GNU Lesser General Public License as published  *
// * by the Free Software Foundation; either version 2.1 of the License,       *
// * or (at your option) any later version.                                    *
// * http://www.gnu.org/licenses/lgpl.html                                     *
// *                                                                           *
// * This library is distributed in the hope that it will be useful,           *
// * but WITHOUTANY WARRANTY; without even the implied warranty of             *
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.                      *
// * See the GNU Lesser General Public License for more details.               *
// *                                                                           *
// * You should have received a copy of the GNU Lesser General Public License  *
// * along with this library; if not, write to the Free Software Foundation,   *
// * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110, USA                  *
// *****************************************************************************

import VisualLogic.*;
import VisualLogic.variables.*;
import eu.hansolo.steelseries.extras.*;
import eu.hansolo.steelseries.tools.BackgroundColor;
import eu.hansolo.steelseries.tools.ColorDef;
import eu.hansolo.steelseries.tools.FrameDesign;
import eu.hansolo.steelseries.tools.SymbolType;
import java.awt.*;
import javax.swing.JPanel;
import tools.*;

public class TrafficPanel extends JVSMain implements PanelIF {
  private int width = 50, height = 150;
  private double value = 0.0;
  private double oldPin;
  private static final TrafficLight myGauge = new TrafficLight();

  private VSBoolean InitStateRed = new VSBoolean(true);
  private VSBoolean InitStateYellow = new VSBoolean(false);
  private VSBoolean InitStateGreen = new VSBoolean(false);
  private VSBoolean frameVisible = new VSBoolean(true);
  private VSComboBox frameDesign = new VSComboBox();
  private VSBoolean backGroundVisible = new VSBoolean(true);
  private VSComboBox backGroundColor = new VSComboBox();

  private VSComboBox onColor = new VSComboBox();
  private VSComboBox offColor = new VSComboBox();
  private VSComboBox icon = new VSComboBox();
  private VSBoolean glow = new VSBoolean(true);

  public void processPanel(int pinIndex, double value, Object obj) {
    try {
      this.value = value;

      if (this.value == 0.0) {
        if (pinIndex == 0) {
          myGauge.setRedOn(false);
          myGauge.setRedBlinking(false);
        }

        if (pinIndex == 1) {
          myGauge.setYellowOn(false);
          myGauge.setYellowBlinking(false);
        }

        if (pinIndex == 2) {
          myGauge.setGreenOn(false);
          myGauge.setGreenBlinking(false);
        }
      }

      if (this.value == 1.0) {
        if (pinIndex == 0) myGauge.setRedOn(true);
        if (pinIndex == 1) myGauge.setYellowOn(true);
        if (pinIndex == 2) myGauge.setGreenOn(true);
      }

      if (pinIndex == 3 && this.value == 1.0) {
        if (myGauge.isGreenOn()) myGauge.setGreenBlinking(true);
        if (myGauge.isRedOn()) myGauge.setRedBlinking(true);
        if (myGauge.isYellowOn()) myGauge.setYellowBlinking(true);
      }

      if (pinIndex == 3 && this.value == 0.0) {
        if (myGauge.isGreenOn()) myGauge.setGreenBlinking(false);
        if (myGauge.isRedOn()) myGauge.setRedBlinking(false);
        if (myGauge.isYellowOn()) myGauge.setYellowBlinking(false);
      }

      element.jRepaint();

    } catch (Exception e) {
      System.out.println("Traffic Light Error: " + e);
    }
  }

  public void paint(java.awt.Graphics g) {}

  public void GaugeSet() {

  }

  public void start() {
    super.start();
    if (element != null) {
      GaugeSet();
      myGauge.setRedOn(InitStateRed.getValue());
      myGauge.setYellowOn(InitStateYellow.getValue());
      myGauge.setGreenOn(InitStateGreen.getValue());
      element.jRepaint();
    }
  }

  public void stop() {
    super.stop();
    if (element != null) {
      GaugeSet();

      myGauge.setRedBlinking(false);
      myGauge.setYellowBlinking(false);
      myGauge.setGreenBlinking(false);
      myGauge.setRedOn(InitStateRed.getValue());
      myGauge.setYellowOn(InitStateYellow.getValue());
      myGauge.setGreenOn(InitStateGreen.getValue());

      element.jRepaint();
    }
  }

  public void init() {
    initPins(0, 0, 0, 0);

    setSize(100, 150);

    element.jSetResizeSynchron(true);

    initPinVisibility(false, false, false, false);
    element.jSetInnerBorderVisibility(false);

    element.jSetResizable(true);

    setName("TrafficLigth");

    element.jSetMinimumSize(30, 30);

    for (ColorDef colorTemp : ColorDef.values()) {
      if (colorTemp.name().equalsIgnoreCase("CUSTOM")) break;
      onColor.addItem(colorTemp.name());
      offColor.addItem(colorTemp.name());
    }

    onColor.selectedIndex = 3; // ORANGE
    offColor.selectedIndex = 9; // BLACK

    for (SymbolType symbolTemp : SymbolType.values()) {
      icon.addItem(symbolTemp.name());
    }

    icon.selectedIndex = 6;

    for (BackgroundColor colorTemp : BackgroundColor.values()) {
      if (colorTemp.name().equalsIgnoreCase("CUSTOM")) break;
      backGroundColor.addItem(colorTemp.name());
    }

    backGroundColor.selectedIndex = 18;

    for (FrameDesign colorTemp : FrameDesign.values()) {
      if (colorTemp.name().equalsIgnoreCase("CUSTOM")) break;
      frameDesign.addItem(colorTemp.name());
    }

    frameDesign.selectedIndex = 2;
  }

  public void xOnInit() {
    GaugeSet();
    JPanel panel = element.getFrontPanel();
    panel.setLayout(new java.awt.BorderLayout());
    panel.add(myGauge, BorderLayout.CENTER);
    element.setAlwaysOnTop(true);
    element.jRepaint();
  }

  public void setPropertyEditor() {
    element.jAddPEItem("Init_State_RED", InitStateRed, 0, 0);
    element.jAddPEItem("Init_State_YELLOW", InitStateYellow, 0, 0);
    element.jAddPEItem("Init_State_GREEN", InitStateGreen, 0, 0);
    localize();
  }

  private void localize() {
    int d = 6;
    String language;

    language = "en_US";

    element.jSetPEItemLocale(d + 0, language, "Init State RED");
    element.jSetPEItemLocale(d + 1, language, "Init State YELLOW");
    element.jSetPEItemLocale(d + 2, language, "Init State GREEN");

    language = "es_ES";

    element.jSetPEItemLocale(d + 0, language, "Estado Inicial ROJO");
    element.jSetPEItemLocale(d + 1, language, "Estado Inicial AMARILLO");
    element.jSetPEItemLocale(d + 2, language, "Estado Inicial VERDE");

    language = "pt_BR";

    element.jSetPEItemLocale(d + 0, language, "Estado Inicial VERMELHO");
    element.jSetPEItemLocale(d + 1, language, "Estado Inicial AMARELO");
    element.jSetPEItemLocale(d + 2, language, "Estado Inicial VERDE");
  }

  public void propertyChanged(Object o) {
    GaugeSet();
    element.jRepaint();
  }

  public void loadFromStream(java.io.FileInputStream fis) {
    InitStateRed.loadFromStream(fis);
    InitStateYellow.loadFromStream(fis);
    InitStateGreen.loadFromStream(fis);

    element.jRepaint();
  }

  public void saveToStream(java.io.FileOutputStream fos) {
    InitStateRed.saveToStream(fos);
    InitStateYellow.saveToStream(fos);
    InitStateGreen.saveToStream(fos);
  }
}
