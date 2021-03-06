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
import java.awt.geom.Rectangle2D;
import java.util.*;
import javax.swing.*;
import tools.*;

public class Return extends MainFlow {
  private Image image;
  private VSBasisIF basis;
  private VSFlowInfo in;

  public void paint(java.awt.Graphics g) {
    if (element != null) {
      Rectangle bounds = element.jGetBounds();
      Graphics2D g2 = (Graphics2D) g;

      g2.setFont(font);

      int mitteX = bounds.x + (bounds.width) / 2;
      int mitteY = bounds.y + (bounds.height) / 2;

      int distanceY = 10;

      g2.setColor(new Color(150, 255, 150));
      g2.fillRoundRect(bounds.x, mitteY - distanceY, bounds.width, 2 * distanceY, 20, 20);
      g2.setColor(Color.BLACK);
      g2.drawRoundRect(bounds.x, mitteY - distanceY, bounds.width, 2 * distanceY, 20, 20);

      String caption = "RETURN(" + variable.getValue() + ")";

      FontMetrics fm = g2.getFontMetrics();
      Rectangle2D r = fm.getStringBounds(caption, g2);

      g2.setColor(Color.BLACK);
      g.drawString(
          caption, mitteX - (int) (r.getWidth() / 2), (int) (mitteY + fm.getHeight() / 2) - 3);
    }
    super.paint(g);
  }

  public void init() {
    standardWidth = 130;
    width = standardWidth;
    height = 40;
    toInclude = "RETURN()";

    initPins(1, 0, 0, 0);
    setSize(width, height);
    initPinVisibility(true, true, true, true);

    element.jSetInnerBorderVisibility(false);

    image = element.jLoadImage(element.jGetSourcePath() + "icon.gif");

    setPin(0, element.C_FLOWINFO, element.PIN_INPUT);

    element.jSetResizable(false);
    element.jSetCaptionVisible(false);
    element.jSetCaption("RETURN");

    setName("#FLOWCHART_RETURN#");
    variable.setValue("0");
  }

  public void xOnInit() {
    super.xOnInit();
  }

  public void initInputPins() {
    in = (VSFlowInfo) element.getPinInputReference(0);
    if (in == null) in = new VSFlowInfo();
  }

  public void start() {
    basis = element.jGetBasis();
  }

  public void process() {
    Object result = null;
    // System.out.println("Result variable.getValue().length()>="+variable.getValue().length());
    if (variable.getValue().length() > 0) {
      result = basis.vsEvaluate(in, variable.getValue());
    }
    in.returnValue = result;

    System.out.println("RETURN=" + result);

    basis.vsReturnSubProcedure(in);
  }

  public void loadFromStream(java.io.FileInputStream fis) {
    variable.loadFromStream(fis);
  }

  public void saveToStream(java.io.FileOutputStream fos) {
    variable.saveToStream(fos);
  }
}
