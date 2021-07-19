package SVGViewer;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import org.w3c.dom.Element;

public class SVGEllipse extends SVGObject {
  double x;
  double y;
  double width;
  double height;

  public void loadFromXML(Element nodeElement) {
    super.loadFromXML(nodeElement);

    this.x = Double.parseDouble(nodeElement.getAttribute("x"));
    this.y = Double.parseDouble(nodeElement.getAttribute("y"));
    this.width = Double.parseDouble(nodeElement.getAttribute("width"));
    this.height = Double.parseDouble(nodeElement.getAttribute("height"));
  }

  public void paint(Graphics2D g, double zoomX, double zoomY) {
    int x1 = (int) (this.x * zoomX);
    int y1 = (int) (this.y * zoomX);
    int x2 = (int) (this.width * zoomX);
    int y2 = (int) (this.height * zoomX);

    if (this.getFillColor() != null) {
      g.setColor(this.getFillColor());
      g.fillOval(x1, y1, x2, y2);
    }

    if (this.getStrokeColor() != null) {
      g.setStroke(new BasicStroke(this.getStrokeWidth()));
      g.setColor(this.getStrokeColor());
      g.drawOval(x1, y1, x2, y2);
    }
  }
}
