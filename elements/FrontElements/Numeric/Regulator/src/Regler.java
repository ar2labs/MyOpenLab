// *****************************************************************************
// * Element of MyOpenLab Library                                              *
// *                                                                           *
// * Copyright (C) 2004 Carmelo Salafia (cswi@gmx.de)                         *
// *                                                                           *
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
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.text.*;
import javax.swing.*;
import tools.*;

public class Regler extends JVSMain {
  private Image image;
  private VSDouble out = new VSDouble();
  private VSDouble val;
  private boolean changed = false;

  public void onDispose() {
    image.flush();
    image = null;
  }

  public void paint(java.awt.Graphics g) {
    drawImageCentred(g, image);
  }

  public void init() {
    initPins(0, 1, 0, 0);
    setSize(50, 45);
    initPinVisibility(false, true, false, false);

    element.jSetResizable(false);

    image = element.jLoadImage(element.jGetSourcePath() + "icon.gif");

    setPin(0, ExternalIF.C_DOUBLE, ExternalIF.PIN_OUTPUT);
    element.jSetPinDescription(0, "Out");

    setName("Regler JV");
  }

  public void changePin(int pinIndex, Object value) {
    changed = true;
    val = (VSDouble) value;
    out.setValue(val.getValue());
    element.notifyPin(0);
  }

  public void initInputPins() {}

  public void initOutputPins() {
    element.setPinOutputReference(0, out);
  }

  public void process() {}
}
