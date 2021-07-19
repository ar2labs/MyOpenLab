package SVGViewer;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import org.w3c.dom.Element;

public class SVGLinearGradient extends SVGObject {
  double x1;
  double y1;
  double x2;
  double y2;

  public void loadFromXML(Element nodeElement) {
    super.loadFromXML(nodeElement);
  }

  public void paint(Graphics2D g, double zoomX, double zoomY) {
    GradientPaint gp = new GradientPaint(50.0F, 50.0F, Color.blue, 50.0F, 250.0F, Color.green);
    g.setPaint(gp);
    g.fillOval(50, 50, 200, 200);
  }
}
