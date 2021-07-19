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

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class FileSystemInput {

  private ArrayList liste = new ArrayList();
  private FileInputStream fis = null;

  /**
   * Reads the index list from the file in which all
   * data record descriptions are located
   */
  public FileSystemInput(String filename) {
    try {
      fis = new FileInputStream(new File(filename));
      DataInputStream dis = new DataInputStream(fis);

      long indexPos = dis.readLong();
      fis.getChannel().position(indexPos);

      long indexSize = dis.readLong();

      for (int i = 0; i < indexSize; i++) {
        SFileDescriptor dt = new SFileDescriptor();
        byte strLen = dis.readByte();

        dt.filename = "";

        for (int j = 0; j < strLen; j++) {
          dt.filename += dis.readChar();
        }

        dt.position = dis.readLong();
        dt.size = dis.readLong();

        liste.add(dt);
      }
    } catch (Exception ex) {
      System.out.println("Error in Methode loadIndexList()" + ex);
    }
  }

  /**
   * Returns the size of the index list
   */
  public long indexListSize() {
    return liste.size();
  }

  public SFileDescriptor[] getAllBeginsWith(String str) {
    ArrayList lst = new ArrayList();
    for (int i = 0; i < liste.size(); i++) {
      SFileDescriptor dt = getFileDescriptor(i);
      if (dt.filename.indexOf(str) != -1) {
        lst.add(dt);
      }
    }

    SFileDescriptor[] descriptoren = new SFileDescriptor[lst.size()];

    for (int i = 0; i < lst.size(); i++) {
      descriptoren[i] = (SFileDescriptor) lst.get(i);
    }

    return descriptoren;
  }

  /**
   * Jumps to the respective data record using the
   * FileDescriptor index within the file
   */
  public FileInputStream gotoItem(int index) {
    SFileDescriptor dt = getFileDescriptor(index);
    try {
      fis.getChannel().position(dt.position);
    } catch (Exception ex) {

    }
    return fis;
  }

  /**
   * Delivers the associated FileDescriptor based on the index
   */
  public SFileDescriptor getFileDescriptor(int index) {
    return (SFileDescriptor) liste.get(index);
  }

  /**
   * Ensures that the streams are closed
   */
  public void close() {
    try {
      fis.close();
    } catch (Exception ex) {
      System.out.println("Error in Methode close()" + ex.toString());
    }
  }
}
