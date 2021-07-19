package SVGViewer;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.StringTokenizer;
import org.w3c.dom.Element;

public class SVGPolygone extends SVGObject {
  private Polygon poly = new Polygon();
  private int i = 0;

  private void geheBisZumLeerZeichen() {}

  private void fillPolygon(String points) {
    StringTokenizer t = new StringTokenizer(points, " ,");

    while (t.hasMoreTokens()) {
      String tempBuffer = t.nextToken();
      int x = (short) (Double.valueOf(tempBuffer)).intValue();

      tempBuffer = t.nextToken();

      int y = (short) (Double.valueOf(tempBuffer)).intValue();

      this.poly.addPoint(x, y);
    }
  }

  public void loadFromXML(Element nodeElement) {
    super.loadFromXML(nodeElement);
    String points = nodeElement.getAttribute("points");
    this.fillPolygon(points);
  }

  public void paint(Graphics2D g, double zoomX, double zoomY) {
    if (this.getFillColor() != null) {
      g.setColor(this.getFillColor());
      g.fillPolygon(this.poly);
    }

    if (this.getStrokeColor() != null) {
      g.setStroke(new BasicStroke(this.getStrokeWidth()));
      g.setColor(this.getStrokeColor());
      g.drawPolygon(this.poly);
    }
  }
}
