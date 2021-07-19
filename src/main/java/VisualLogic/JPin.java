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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class JPin extends VisualObject implements MouseListener, MouseMotionListener {
  public static final byte PIN_UNDEFINIED = 0;
  public static final byte PIN_INPUT = 1;
  public static final byte PIN_OUTPUT = 2;
  public static final byte PIN_INPUT_OUTPUT = 3;

  private Font fnt = new Font("Arial", 0, 9);

  public Element element;
  public byte pinIO = PIN_UNDEFINIED;

  public PinIF
      pinIF; // Contains the element where the events / control commands go -> for the events
  public int pinIndex; // Each pin has an index
  public Draht draht; // Provides the wire reference that is connected to another pin
  public int pinAlign;

  private boolean highlighted = false;
  private boolean pointPin = false;
  private boolean visible = true;

  // is only used for grouping
  public int ownerElementID = -1;
  public int ownerSrcPinID = -1;

  // Contains the value for the I/O
  public double value;

  public int dataType = -1; // Only in connection with object
  public Object object = null;

  private Stroke strockeStandard = new BasicStroke(1);
  private String description = "";

  public void setDescription(String value) {
    description = value;
  }

  public void setPointPin() {
    pointPin = true;
  }

  public String getDescription() {
    return description;
  }

  /** Creates a new instance of JMainElement */
  // pinAlign delivers
  // 0 for Top
  // 1 for Right
  // 2 for Bottom
  // 3 for left
  public JPin(PinIF pinIF, int pinIndex, int pinAlign) {
    super();

    this.element = (Element) pinIF;
    this.pinIF = pinIF;
    this.pinIndex = pinIndex;
    this.pinAlign = pinAlign;

    setOpaque(false);
    setBackground(nonSelectedColor);

    value = 0;
  }

  private Color nonSelectedColor = new Color(150, 0, 0, 0);

  public boolean isHighlighted() {
    return highlighted;
  }

  public void setHighlighted(boolean value) {
    highlighted = value;
    repaint();
  }

  public void setVisible(boolean value) {
    if (visible != value) {
      visible = value;
    }
  }

  public int getPinAlign() {
    return pinAlign;
  }

  public void mouseClicked(MouseEvent e) {
    if (pinIF != null) pinIF.PinMouseClicked(e, this, pinIndex);
  }

  public void mouseEntered(MouseEvent e) {
    if (pinIF != null) pinIF.PinMouseEntered(e, this, pinIndex);
  }

  public void mouseExited(MouseEvent e) {
    if (pinIF != null) pinIF.PinMouseExited(e, this, pinIndex);
  }

  public void mousePressed(MouseEvent e) {
    if (pinIF != null) pinIF.PinMousePressed(e, this, pinIndex);
  }

  public void mouseReleased(MouseEvent e) {
    if (pinIF != null) pinIF.PinMouseReleased(e, this, pinIndex);
  }

  public void mouseDragged(MouseEvent e) {
    if (pinIF != null) pinIF.PinMouseDragged(e, this, pinIndex);
  }

  public void mouseMoved(MouseEvent e) {
    if (pinIF != null) pinIF.PinMouseMoved(e, this, pinIndex);
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (element != null) {
      if (element.owner.owner.vmProtected) {
        if (element.owner.owner.getCircuitBasis() == element.owner) {
          return;
        }
      }
    }

    Graphics2D g2 = (Graphics2D) g;

    int mitteX = getWidth() / 2;
    int mitteY = getHeight() / 2;

    if (isHighlighted()) {
      Color color = VSDataType.getCoorFromDataType(this.dataType);
      setBackground(color);
    } else {
      setBackground(nonSelectedColor);
    }
    g.setColor(getBackground());
    g.fillRect(0, 0, getWidth(), getHeight());

    if (isHighlighted()) {
      g.setColor(Color.BLACK);
      g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }

    int dis = 1;

    if (!visible) return;

    if (pinAlign == 1 || pinAlign == 3) // Horizontal
    {
      VSDataType.setColorStrokeFromDataType(g2, this.dataType);
      g.drawLine(1, mitteY, getWidth() - 1, mitteY);
    } else {
      VSDataType.setColorStrokeFromDataType(g2, this.dataType);
      g.drawLine(mitteX, 1, mitteX, getHeight() - 1);
    }

    g2.setStroke(strockeStandard);

    JPin bb = this;
    int x, y;

    if (bb.pinIO == JPin.PIN_INPUT) {
      int d = 2;

      switch (bb.pinAlign) {
        case 0:
          {
            x = getWidth() / 2;
            y = getHeight() - d;

            Polygon p = new Polygon();

            p.addPoint(x, y);
            p.addPoint(x - d, y - d);
            p.addPoint(x + d, y - d);

            g2.drawPolygon(p);
            g2.fillPolygon(p);
            break;
          }

        case 1:
          {
            x = d;
            y = getHeight() / 2;

            Polygon p = new Polygon();

            p.addPoint(x, y);
            p.addPoint(x + d, y - d);
            p.addPoint(x + d, y + d);

            g2.drawPolygon(p);
            g2.fillPolygon(p);
          }

        case 2:
          {
            x = getWidth() / 2;
            y = d;

            Polygon p = new Polygon();

            p.addPoint(x, y);
            p.addPoint(x + d, y + d);
            p.addPoint(x - d, y + d);

            g2.drawPolygon(p);
            g2.fillPolygon(p);
          }

        case 3:
          {
            x = getWidth() - d;
            y = getHeight() / 2;

            Polygon p = new Polygon();

            p.addPoint(x, y);
            p.addPoint(x - d, y - d);
            p.addPoint(x - d, y + d);

            g2.drawPolygon(p);
            g2.fillPolygon(p);
          }
      }
    }
  }
}
