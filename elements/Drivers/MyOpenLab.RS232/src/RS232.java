// *****************************************************************************
// * Element of MyOpenLab Library                                              *
// *                                                                           *
// * Copyright (C) 2004 Carmelo Salafia (cswi@gmx.de)                          *
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

import com.rm5248.serial.SerialPort.*;

import java.util.ArrayList;
import javax.swing.JOptionPane;

import VisualLogic.MyOpenLabDriverIF;
import VisualLogic.MyOpenLabDriverOwnerIF;
import VisualLogic.variables.*;

public class RS232 implements MyOpenLabDriverIF {

  private VS1DByte outBytes = new VS1DByte(0);
  private ArrayList<Driver> listDriver = new ArrayList<Driver>();

  private MyOpenLabDriverOwnerIF owner;

  public RS232() {
    System.out.println("*************** PORTS ---------------- ");
  }

  public Driver getDriver(String port) {
    for (int i = 0; i < listDriver.size(); i++) {
      Driver driver = listDriver.get(i);

      if (driver.port.equalsIgnoreCase(port)) {
        return driver;
      }
    }

    return null;
  }

  public void sendCommand(String command, Object value) {

    String[] strings = command.split(";");
    String strPort = strings[0];

    try {

      command = strings[1];

      if (command.equals("GETPORTS")) {
        Driver driver = getDriver(strPort);
        String[] list = driver.listSerialPorts();

        System.out.println("--------> GETPORTS");

        // Add to ArrayList<String>
        for (int i = 0; i < list.length; i++) {
          ((ArrayList<String>) value).add(list[i]);
        }
      }

      if (command.equals("TIMEOUT")) {
        if (value instanceof VSInteger) {
          VSInteger tmp = (VSInteger) value;

          Driver driver = getDriver(strPort);
          System.out.println("TimeOut = " + tmp.getValue());

          driver.setTimeOut(tmp.getValue());
        }
      }

      if (command.equals("CLOSE")) {
        Driver driver = getDriver(strPort);

        if (driver != null) {
          driver.close();
          listDriver.remove(driver);
        }
      }

      if (value instanceof VS1DByte) {
        VS1DByte values = (VS1DByte) value;

        int len = values.getLength();

        byte[] dest = values.getValues();

        if (command.equals("SENDBYTES")) {
          Driver driver = getDriver(strPort);

          if (driver != null) {
            driver.sendBytes(dest);
          }
        }

        if (command.equals("RTSON")) {
          Driver driver = getDriver(strPort);
          driver.setRTS(true);
        }

        if (command.equals("RTSOFF")) {
          Driver driver = getDriver(strPort);
          driver.setRTS(false);
        }

        if (command.equals("DTRON")) {
          Driver driver = getDriver(strPort);
          driver.setDTR(false);
        }

        if (command.equals("DTROFF")) {
          Driver driver = getDriver(strPort);
          driver.setDTR(false);
        }
      }

      Thread.sleep(20);
    } catch (Exception ex) {
      System.out.println("Error Sending Command: " + ex);
    }
  }

  public String lastPort = "";

  public void registerOwner(MyOpenLabDriverOwnerIF owner) {
    Driver driver = getDriver(lastPort);
    driver.owner = owner;
  }

  public void driverStart(ArrayList args) {

    if (args instanceof ArrayList && args.size() >= 5) {
      boolean useOwnInHandler = false;

      String strPort = (String) args.get(0);

      int intBaud = (Integer) args.get(1);
      int intBits = (Integer) args.get(2);
      int intStopBits = (Integer) args.get(3);
      int intParity = (Integer) args.get(4);

      if (args.size() == 6) {
        useOwnInHandler = (Boolean) args.get(5);
      }

      lastPort = strPort;

      if (getDriver(strPort) == null) {

        BaudRate baudRate;
        DataBits dataBits;
        StopBits stopBits;
        Parity parity;

        switch (intBaud) {
          case 2400: baudRate = BaudRate.B2400; break;
          case 4800: baudRate = BaudRate.B4800; break;
          case 9600: baudRate = BaudRate.B9600; break;
          case 19200: baudRate = BaudRate.B19200; break;
          case 38400: baudRate = BaudRate.B38400; break;
          // case 57600: baudRate = BaudRate(57600); break;
          case 115200: baudRate = BaudRate.B115200; break;
          default: baudRate = BaudRate.B9600;
        }

        switch (intBits) {
          case 5: dataBits = DataBits.DATABITS_5; break;
          case 6: dataBits = DataBits.DATABITS_6; break;
          case 7: dataBits = DataBits.DATABITS_7; break;
          default: dataBits = DataBits.DATABITS_8;
        }

        switch (intStopBits) {
          case 1: stopBits = StopBits.STOPBITS_1; break;
          default: stopBits = StopBits.STOPBITS_2;
        }

        switch (intParity) {
          case 1: parity = Parity.EVEN; break;
          case 3: parity = Parity.ODD; break;
          default: parity = Parity.NONE;
        }

        Driver driver = new Driver(strPort, baudRate, dataBits, stopBits, parity);
        driver.useOwnInHandler = useOwnInHandler;
        driver.start();

        listDriver.add(driver);
      }
    }
  }

  public void driverStop() {}

  public static void showMessage(String message) {
    JOptionPane.showMessageDialog(null, message, "Attention!", JOptionPane.ERROR_MESSAGE);
  }
}
