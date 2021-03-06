// *****************************************************************************
// * Element of MyOpenLab Library                                              *
// *                                                                           *
// * Copyright (C) 2004 Carmelo Salafia (cswi@gmx.de)                         *
// *                                                                           *
// * This library is free software; you can redistribute it and/or modify      *
// * it under the terms of the GNU Lesser General Public License as published  *
// * by the Free Software Foundation; either version 2.1 of the License,       *
// * or (at your option) any later version.                                    *
// * http://www.gnu.org/licenses/lgpl.html                                     *
// *                                                                           *
// * This library is distributed in the hope that it will be useful,           *
// * but WITHOUTANY WARRANTY; without even the implied warranty of             *
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.                      *
// * See the GNU Lesser General Public License for more details.               *
// *                                                                           *
// * You should have received a copy of the GNU Lesser General Public License  *
// * along with this library; if not, write to the Free Software Foundation,   *
// * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110, USA                  *
// *****************************************************************************

import java.util.logging.Level;
import java.util.logging.Logger;

class MyTimer extends Thread {

  public boolean stop = false;
  public long sendenEnde;
  public RS232v2 owner;
  public int delay = 200;
  public boolean gesendet = false;

  @Override
  public void run() {

    System.out.println("Timer started");

    stop = false;
    gesendet = true;
    sendenEnde = System.currentTimeMillis();

    long millis = 0;

    while (!stop) {
      try {
        millis = System.currentTimeMillis();

        if (!gesendet && Math.abs(sendenEnde - millis) > owner.message_timeout.getValue()) {
          owner.sendBytesNow();
          gesendet = true;
        }

        Thread.sleep(5);
      } catch (InterruptedException ex) {
        Logger.getLogger(MyTimer.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

    System.out.println("Timer end");
  }
}
