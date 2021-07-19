package VisualLogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;

public class SearchElement {

  private ArrayList<SearchFoundItem> items = new ArrayList<>();

  public Hashtable<String, String> load_definition_def(String filename) throws Exception {
    FileInputStream fstream = null;

    Hashtable<String, String> res = new Hashtable<>();

    // Open the file
    fstream = new FileInputStream(filename);

    BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
    String strLine;

    // Read File Line By Line
    while ((strLine = br.readLine()) != null) {
      String[] cols = strLine.split("=");
      if (cols.length == 2) {
        res.put(cols[0].trim().toLowerCase(), cols[1].trim());
      }
    }
    br.close();

    return res;
  }

  public ArrayList<SearchFoundItem> search(String path, String suchwort, String language) {
    items.clear();
    walk(path, suchwort, language);
    return items;
  }

  public void walk(String path, String suchwort, String language) {

    suchwort = suchwort.toLowerCase();

    File root = new File(path);
    File[] list = root.listFiles();

    if (list == null) {
      return;
    }

    for (File f : list) {
      if (f.isDirectory()) {

        String definition = f.getAbsolutePath() + "/definition.def";
        if (new File(definition).exists()) {
          try {
            Hashtable<String, String> def_def = load_definition_def(definition);

            String isdirectory = def_def.get("isdirectory");
            if (isdirectory != null && isdirectory.equalsIgnoreCase("true")) {
              walk(f.getAbsolutePath(), suchwort, language);
            } else {
              String gefunden = "";
              String caption = def_def.get("caption");
              String caption_en = def_def.get("caption_en");
              String caption_es = def_def.get("caption_es");
              String caption_pt = def_def.get("caption_pt");

              if (language.equalsIgnoreCase("de")) {
                if (caption != null && caption.toLowerCase().contains(suchwort)) {
                  gefunden = caption;
                }
              }

              if (language.equalsIgnoreCase("en")) {
                if (caption_en != null && caption_en.toLowerCase().contains(suchwort)) {
                  gefunden = caption_en;
                }
              }

              if (language.equalsIgnoreCase("es")) {
                if (caption_es != null && caption_es.toLowerCase().contains(suchwort)) {
                  gefunden = caption_es;
                }
              }

              if (language.equalsIgnoreCase("pt")) {
                if (caption_pt != null && caption_pt.toLowerCase().contains(suchwort)) {
                  gefunden = caption_pt;
                }
              }

              if (gefunden.trim().length() > 0) {
                SearchFoundItem item = new SearchFoundItem();
                item.found_word = gefunden;
                item.found_in_element_dir = f.getAbsolutePath();
                items.add(item);
              }
            }
          } catch (Exception ex) {

          }
        }
      }
    }
  }
}
