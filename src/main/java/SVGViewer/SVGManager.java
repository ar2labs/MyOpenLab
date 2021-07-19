package SVGViewer;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SVGManager {
  private ArrayList svgListe = new ArrayList();
  private float width;
  private float height;

  public float extractFloatValue(String strokeW) {
    if (strokeW.length() > 1) {
      strokeW = strokeW.substring(0, strokeW.length() - 2);

      try {
        return Float.parseFloat(strokeW);
      } catch (Exception ex) {
        System.out.println(ex);
      }
    }

    return 1.0F;
  }

  public void extract(Document doc) throws IOException {
    this.svgListe.clear();

    NodeList svg = doc.getElementsByTagName("svg");
    NodeList nodeElements = null;

    Element nodeElement = doc.getDocumentElement();

    this.width = this.extractFloatValue(nodeElement.getAttribute("width"));
    this.height = this.extractFloatValue(nodeElement.getAttribute("height"));

    for (int i = 0; i < svg.getLength(); ++i) {
      Element chapter = (Element) svg.item(i);

      nodeElements = chapter.getElementsByTagName("ellipse");

      int j;

      for (j = 0; j < nodeElements.getLength(); ++j) {
        nodeElement = (Element) nodeElements.item(j);

        SVGEllipse ellipse = new SVGEllipse();
        ellipse.loadFromXML(nodeElement);

        this.svgListe.add(ellipse);
      }

      nodeElements = chapter.getElementsByTagName("polygon");

      for (j = 0; j < nodeElements.getLength(); ++j) {
        nodeElement = (Element) nodeElements.item(j);

        SVGPolygone poly = new SVGPolygone();
        poly.loadFromXML(nodeElement);

        this.svgListe.add(poly);
      }

      nodeElements = chapter.getElementsByTagName("line");

      for (j = 0; j < nodeElements.getLength(); ++j) {
        nodeElement = (Element) nodeElements.item(j);

        SVGLines line = new SVGLines();
        line.loadFromXML(nodeElement);

        this.svgListe.add(line);
      }

      nodeElements = chapter.getElementsByTagName("linearGradient");

      for (j = 0; j < nodeElements.getLength(); ++j) {
        nodeElement = (Element) nodeElements.item(j);

        SVGLinearGradient linGr = new SVGLinearGradient();
        linGr.loadFromXML(nodeElement);

        this.svgListe.add(linGr);
      }

      nodeElements = chapter.getElementsByTagName("path");

      for (j = 0; j < nodeElements.getLength(); ++j) {
        nodeElement = (Element) nodeElements.item(j);

        SVGPath path = new SVGPath();
        path.loadFromXML(nodeElement);

        this.svgListe.add(path);
      }

      nodeElements = chapter.getElementsByTagName("circle");

      for (j = 0; j < nodeElements.getLength(); ++j) {
        nodeElement = (Element) nodeElements.item(j);

        SVGCircle circle = new SVGCircle();
        circle.loadFromXML(nodeElement);

        this.svgListe.add(circle);
      }

      nodeElements = chapter.getElementsByTagName("rect");

      for (j = 0; j < nodeElements.getLength(); ++j) {
        nodeElement = (Element) nodeElements.item(j);

        SVGRect rect = new SVGRect();
        rect.loadFromXML(nodeElement);

        this.svgListe.add(rect);
      }
    }
  }

  public void loadFromFile(String fileName) {
    try {
      BufferedReader reader =
          new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));

      String text = "";

      for (String line = ""; (line = reader.readLine()) != null; text = text + line) {}

      this.loadFromString(text);
    } catch (Exception ex) {
      System.out.println("SVG Error: " + ex);
    }
  }

  public void loadFromString(String xmlText) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder parser = factory.newDocumentBuilder();
      Document document = parser.parse(new InputSource(new StringReader(xmlText)));

      factory.setValidating(false);

      this.extract(document);
    } catch (SAXException ex) {
      System.out.println("XML TEXT is not in order: " + ex);
    } catch (IOException ex) {
      System.out.println("Due to an IOException, the parser could not check: " + ex);
    } catch (FactoryConfigurationError ex) {
      System.out.println("Could not locate a factory class: " + ex);
    } catch (ParserConfigurationException ex) {
      System.out.println("Could not locate a JAXP parser: " + ex);
    }
  }

  public SVGObject getSVGObject(String id) {
    for (int i = 0; i < this.svgListe.size(); ++i) {
      SVGObject svg = (SVGObject) this.svgListe.get(i);

      if (svg.getID().equalsIgnoreCase(id)) {
        return svg;
      }
    }

    return null;
  }

  public void paint(Graphics2D g, int width, int height) {
    Graphics2D g2 = g;

    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    double zoomX = (double) ((float) width / this.width);
    double zoomY = (double) ((float) height / this.height);

    AffineTransform saveAt = g.getTransform();
    AffineTransform at = new AffineTransform();

    at.scale(zoomX, zoomY);
    g.translate(0, 0);
    g.transform(at);

    for (int i = 0; i < this.svgListe.size(); ++i) {
      SVGObject svg = (SVGObject) this.svgListe.get(i);
      svg.paint(g2, 1.0D, 1.0D);
    }

    g2.setTransform(saveAt);
  }
}
