package SVGViewer;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import org.w3c.dom.Element;

public class SVGRect extends SVGObject {
  double x;
  double y;
  double width;
  double height;
  int rx;
  int ry;

  public void loadFromXML(Element nodeElement) {
    super.loadFromXML(nodeElement);

    this.x = Double.parseDouble(nodeElement.getAttribute("x"));
    this.y = Double.parseDouble(nodeElement.getAttribute("y"));
    this.width = Double.parseDouble(nodeElement.getAttribute("width"));
    this.height = Double.parseDouble(nodeElement.getAttribute("height"));

    try {
      this.rx = Integer.parseInt(nodeElement.getAttribute("rx"));
    } catch (Exception var4) {
    }

    try {
      this.ry = Integer.parseInt(nodeElement.getAttribute("ry"));
    } catch (Exception var3) {
    }
  }

  public void paint(Graphics2D g, double zoomX, double zoomY) {
    AffineTransform saveAt = g.getTransform();
    AffineTransform at = new AffineTransform();

    this.extractTranform(at, this.getTransformString());

    g.transform(at);

    int x1 = (int) (this.x * zoomX);
    int y1 = (int) (this.y * zoomX);
    int x2 = (int) (this.width * zoomX);
    int y2 = (int) (this.height * zoomX);
    int rx1 = (int) ((double) this.rx * zoomX);
    int ry1 = (int) ((double) this.ry * zoomY);

    if (this.getFillColor() != null) {
      g.setColor(this.getFillColor());
      g.fillRoundRect(x1, y1, x2, y2, rx1, ry1);
    }

    if (this.getStrokeColor() != null) {
      g.setStroke(new BasicStroke(this.getStrokeWidth()));
      g.setColor(this.getStrokeColor());
      g.drawRoundRect(x1, y1, x2, y2, rx1, ry1);
    }

    g.setTransform(saveAt);
  }
}
