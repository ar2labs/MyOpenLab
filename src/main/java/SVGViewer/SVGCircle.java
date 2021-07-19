package SVGViewer;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import org.w3c.dom.Element;

public class SVGCircle extends SVGObject {
  double cx;
  double cy;
  double r;

  public void loadFromXML(Element nodeElement) {
    super.loadFromXML(nodeElement);

    this.cx = Double.parseDouble(nodeElement.getAttribute("cx"));
    this.cy = Double.parseDouble(nodeElement.getAttribute("cy"));
    this.r = Double.parseDouble(nodeElement.getAttribute("r"));
  }

  public void paint(Graphics2D g, double zoomX, double zoomY) {
    int x1 = (int) ((this.cx - this.r) * zoomX);
    int y1 = (int) ((this.cy - this.r) * zoomX);
    int x2 = (int) (this.r * 2.0D * zoomX);
    int y2 = (int) (this.r * 2.0D * zoomX);

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
