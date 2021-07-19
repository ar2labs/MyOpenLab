package SVGViewer;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import org.w3c.dom.Element;

public class SVGLines extends SVGObject {
  double x1;
  double y1;
  double x2;
  double y2;

  public void loadFromXML(Element nodeElement) {
    super.loadFromXML(nodeElement);

    this.x1 = Double.parseDouble(nodeElement.getAttribute("x1"));
    this.y1 = Double.parseDouble(nodeElement.getAttribute("y1"));
    this.x2 = Double.parseDouble(nodeElement.getAttribute("x2"));
    this.y2 = Double.parseDouble(nodeElement.getAttribute("y2"));
  }

  public void paint(Graphics2D g, double zoomX, double zoomY) {
    int a1 = (int) (this.x1 * zoomX);
    int a2 = (int) (this.y1 * zoomX);
    int a3 = (int) (this.x2 * zoomX);
    int a4 = (int) (this.y2 * zoomX);

    if (this.getStrokeColor() != null) {
      g.setStroke(new BasicStroke(this.getStrokeWidth()));
      g.setColor(this.getStrokeColor());
      g.drawLine(a1, a2, a3, a4);
    }
  }
}
