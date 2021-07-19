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

package SimpleFileSystem;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class FileSystemOutput {
  private ArrayList liste = new ArrayList();
  private FileOutputStream fos = null;
  private SFileDescriptor oldItem;

  /**
   * Creates a new instance of SimpleFileSystem Generates
   * its own file system based on the filename
   */
  public FileSystemOutput(String filename) {
    try {
      fos = new FileOutputStream(new File(filename));

      DataOutputStream dos = new DataOutputStream(fos);

      dos.writeLong(123479); // platzhalter f�r die Position der IndexListe!
    } catch (Exception ex) {
      System.out.println("Error in Methode createFile()" + ex.toString());
    }
  }

  /**
   * Adds a stream dataset and delivers the associated stream
   */
  public FileOutputStream addItem(String filename) {
    try {
      SFileDescriptor dt = new SFileDescriptor();

      dt.filename = filename;
      dt.position = fos.getChannel().position();
      dt.size = 0;
      liste.add(dt);

      oldItem = dt;
    } catch (Exception ex) {
      System.out.println("Error in Methode addItem()" + ex.toString());
    }
    return fos;
  }

  /**
   * PostItem ensures that the data record is completed
   * correctly and must be called after each new data record
   */
  public void postItem() {
    if (oldItem != null) {
      try {
        long position = fos.getChannel().position();
        oldItem.size = position - oldItem.position;
      } catch (Exception ex) {

      }
    }
  }

  /**
   * Writes the list of FileDiscriptors at the end of the file
   */
  public void addIndexList() {
    DataOutputStream dos = new DataOutputStream(fos);

    try {

      long pos = fos.getChannel().position();
      dos.writeLong(liste.size()); // IndexSize!
      for (int i = 0; i < liste.size(); i++) {
        SFileDescriptor dt = (SFileDescriptor) liste.get(i);

        dos.writeByte(dt.filename.length());
        for (int j = 0; j < dt.filename.length(); j++) dos.writeChar(dt.filename.charAt(j));

        dos.writeLong(dt.position);
        dos.writeLong(dt.size);
      }

      fos.getChannel().position(0);
      dos.writeLong(pos);
    } catch (Exception ex) {
      System.out.println("Error in Methode addIndexList()" + ex.toString());
    }
  }

  /**
   * Ensures that the file is completed successfully! and
   * must be called after all data records have been written!
   */
  public void close() {
    addIndexList();
    try {
      fos.flush();
      fos.close();
    } catch (Exception ex) {
      System.out.println("Error in Methode close()" + ex.toString());
    }
  }
}
