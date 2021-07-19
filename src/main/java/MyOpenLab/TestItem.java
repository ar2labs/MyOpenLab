package MyOpenLab;

public class TestItem {
  public String name = "";
  public String type = "";
  public String caption_de = "";
  public String caption_en = "";
  public String caption_es = "";
  public String caption_pt = "";

  public TestItem(
      String name,
      String type,
      String caption_de,
      String caption_en,
      String caption_es,
      String caption_pt) {
    this.name = name;
    this.type = type;
    this.caption_de = caption_de;
    this.caption_en = caption_en;
    this.caption_es = caption_es;
    this.caption_pt = caption_pt;
  }
}
