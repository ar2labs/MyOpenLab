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
import java.awt.geom.Rectangle2D;
import tools.*;

public class Decision extends MainFlow {
  private Image image;
  private VSBasisIF basis;
  private VSFlowInfo in = null;
  private VSFlowInfo outY = new VSFlowInfo();
  private VSFlowInfo outN = new VSFlowInfo();
  private String[] strElements = null;

  @Override
  public void paint(java.awt.Graphics g) {
    if (element != null) {
      Rectangle bounds = element.jGetBounds();
      g2 = (Graphics2D) g;

      g2.setFont(font);

      int mitteX = bounds.x + (bounds.width) / 2;
      int mitteY = bounds.y + (bounds.height) / 2;

      int distanceY = 6;

      Polygon p2 = new Polygon();
      p2.addPoint(mitteX, bounds.y);
      p2.addPoint(bounds.x - 2 + bounds.width, mitteY - distanceY);
      p2.addPoint(bounds.x - 2 + bounds.width, mitteY + distanceY);
      p2.addPoint(mitteX, bounds.y + bounds.height);
      p2.addPoint(bounds.x, mitteY + distanceY);
      p2.addPoint(bounds.x, mitteY - distanceY);

      g2.setColor(new Color(204, 255, 204));
      g2.fillPolygon(p2);
      g2.setColor(Color.BLACK);
      g2.drawPolygon(p2);

      String caption = variable.getValue();

      FontMetrics fm = g2.getFontMetrics();
      Rectangle2D r = fm.getStringBounds(caption, g2);

      g2.setColor(Color.BLACK);
      g.drawString(
          caption, mitteX - (int) (r.getWidth() / 2), (int) (mitteY + fm.getHeight() / 2) - 3);

      g2.setColor(new Color(180, 0, 0));

      g.drawString("N", bounds.x + bounds.width, mitteY - 2);
      g2.setColor(new Color(0, 170, 0));
      g.drawString("Y", mitteX + 8, bounds.y + bounds.height + 9);
    }

    super.paint(g);
  }

  @Override
  public void init() {
    standardWidth = 130;
    width = standardWidth;
    height = 50;

    initPins(1, 1, 1, 0);
    setSize(width, height);
    initPinVisibility(true, true, true, true);

    element.jSetInnerBorderVisibility(false);

    image = element.jLoadImage(element.jGetSourcePath() + "icon.gif");

    setPin(0, ExternalIF.C_FLOWINFO, ExternalIF.PIN_INPUT);
    setPin(1, ExternalIF.C_FLOWINFO, ExternalIF.PIN_OUTPUT);
    setPin(2, ExternalIF.C_FLOWINFO, ExternalIF.PIN_OUTPUT);

    element.jSetResizable(false);
    element.jSetCaptionVisible(false);
    element.jSetCaption("DECISION");

    variable.setValue("i<10");

    setName("#FLOWCHART_DECISION#");
  }

  @Override
  public void xOnInit() {
    super.xOnInit();
  }

  @Override
  public void initInputPins() {
    in = (VSFlowInfo) element.getPinInputReference(0);
    basis = element.jGetBasis();
  }

  @Override
  public void initOutputPins() {
    element.setPinOutputReference(1, outN);
    element.setPinOutputReference(2, outY);
  }

  @Override
  public void start() {}

  @Override
  public void process() {
    if (basis != null) {
      if (basis.vsCompareExpression(in, variable.getValue()) == true) {
        outY.copyValueFrom(in);
        element.notifyPin(2);
      } else {
        outN.copyValueFrom(in);
        element.notifyPin(1);
      }
    }
  }
}
