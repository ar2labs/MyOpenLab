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

import com.rm5248.serial.*;
import com.rm5248.serial.SerialPort.*;

import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataOutputStream;

import VisualLogic.MyOpenLabDriverOwnerIF;
import VisualLogic.variables.*;

public class Driver {

  public SerialPort serial;

  public InputStream input;
  public OutputStream output;
  public DataOutputStream data;

  public MyOpenLabDriverOwnerIF owner;

  public String port;

  public boolean useOwnInHandler = false;
  public boolean portConfigured = false;
  public boolean error;

  public int timeOut = 50;

  private VS1DByte outBytes = new VS1DByte(0);

  private SerialReader serialThread;

  /**
  * Returns an array of Strings containing the names of
  * serial ports present on the system
  *
  * @return array of Strings of serial port names
  */
  public String[] listSerialPorts() {
    String[] ports = new String[] {};

    try {
      // Get a list of serial port names
      ports = SerialPort.getSerialPorts();
    } catch (IOException e) {
      System.out.println("Driver listSerialPorts error: " + e);
    }

    return ports;
  }

  /**
   * Initializes a driver port.
   *
   * @param portName The port to open
   * @param baudRate The baud rate in the native code
   * @param dataBits
   * @param stopBits
   * @param parity
   */
  public Driver(String portName, BaudRate baudRate, DataBits dataBits, StopBits stopBits, Parity parity) {

    portConfigured = false;
    port = portName;

    System.out.println("Driver init...");

    if (portName.trim().equalsIgnoreCase("NULL")) {
      System.out.println("Driver port is NULL");
    } else {
      try {
        System.out.println("Driver port: " + portName);

        SerialPortBuilder builder = new SerialPortBuilder();

        if (portConfigured == false) {
          portConfigured = true;

          // This would be COM1, COM2, etc on Windows
          builder.setBaudRate(baudRate)
              .setPort(portName)
              .setStopBits(stopBits)
              .setParity(parity);
        }

        serial = builder.build();

        // Get data output stream
        data = new DataOutputStream(serial.getOutputStream());

        error = false;
      } catch (NoSuchPortException e) {
        error = true;
        System.err.println("Driver: That port doesn't exist!");
      } catch (NotASerialPortException e) {
        error = true;
        System.err.println("Driver: That's not a serial port!");
      } catch (IOException e) {
        error = true;
        System.err.println("Driver error: " + e);
      }
    }
  }

  public void start() {
    try {
      // Get serial streams
      input = serial.getInputStream();
      output = serial.getOutputStream();

      if (useOwnInHandler) {
        System.out.println("useOwnInHandler");
        // Get input stream and start the thread
        serialThread = new SerialReader();
        serialThread.in = input;
        serialThread.start();
      } else {
        System.out.println("No useOwnInHandler");
        // Get the state of the lines on the serial port
        serial.setSerialChangeListener(new SerialListener());
      }
    } catch (Exception e) {
      System.out.println("Serial Port error: " + e);
    }
  }

  public void setTimeOut(int millis) {
    timeOut = millis;
  }

  public void close() {

    if (serialThread != null) {
      serialThread.stop = true;
      System.out.println("Driver: Stoping...");
    }

    if (data != null) {
      try {
        data.close();
      } catch (IOException e) {
        System.out.println("Driver close data error: " + e);
      }
    }

    if (serial != null) {
      try {
        if (! serial.isClosed()) {
          serial.close();
          portConfigured = false;
        }

        System.out.println("Port closed OK");
      } catch (Exception e) {
        System.out.println("Driver close port error: " + e);
      }
    }
  }

  public void sendCommand(String command, Object value) {

    System.out.println("Serial Command: " + command);

    if (value instanceof VS1DByte) {
      VS1DByte values = (VS1DByte) value;

      byte[] dest = values.getValues();

      if (command.equals("SENDBYTES")) {
        sendBytes(dest);
      }
    }
  }

  public void registerOwner(MyOpenLabDriverOwnerIF owner) {
    this.owner = owner;
  }

  public void setRTS(boolean value) {
    try {
      SerialLineState state = serial.getSerialLineState();
      // This will set the RTS line
      state.requestToSend = value;
      serial.setSerialLineState(state);
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

  public void setDTR(boolean value) {
    try {
      SerialLineState state = serial.getSerialLineState();
      // This will set the DTR line
      state.dataTerminalReady = value;
      serial.setSerialLineState(state);
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

  public void sendBytes(byte[] bytes) {
    try {
      if (data != null) {
        data.write(bytes);
        data.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns a long that contains the same n bits as the given long,
   * with cleared upper rest.
   *
   * @param value the value which lowest bits should be copied.
   * @param bits the number of lowest bits that should be copied.
   * @return a long value that shares the same low bits as the given value.
   */
  private static long copyBits(final long value, byte bits) {
    final boolean logging = false; // Turn off logging here

    long converted = 0;
    long comp = 1L << bits;

    while (--bits != -1) {
      if (((comp >>= 1) & value) != 0) {
        converted |= comp;
      }

      if (logging) {
        System.out.print((comp & value) != 0 ? "1" : "0");
      }
    }

    if (logging) {
      System.out.println();
    }

    return converted;
  }

  /**
   * Converts an unsigned byte to a signed short.
   *
   * @param value an unsigned byte value
   * @return a signed short that represents the unsigned byte's value.
   */
  public static short toSigned(final byte value) {
    return (short) copyBits(value, (byte) 8);
  }

  class MyThread extends Thread {

    public void run() {
      try {
        System.out.println("Thread: Start");

        while (true) {
          long time2 = System.currentTimeMillis();
          long diff = time2 - time;

          if (diff > timeOut) {
            System.out.println("Thread: TimeOut");
            break;
          }

          Thread.sleep(2);
        }

        thread = null;

        byte buff[] = new byte[strBuffer.size()];

        for (int i = 0; i < strBuffer.size(); i++) {
          buff[i] = strBuffer.get(i).byteValue();
        }

        System.out.println("Thread: " + new String(strBuffer.toString()));

        strBuffer.clear();

        outBytes.setValues(buff);

        owner.getCommand("BYTESRECEIVED", (Object) outBytes);

      } catch (Exception ex) {
        System.out.println(ex);
      }
    }
  }

  MyThread thread;
  long time = 0;

  public void make() {
    if (thread == null) {
      thread = new MyThread();
      thread.start();
    }

    time = System.currentTimeMillis();
  }

  ArrayList<Byte> strBuffer = new ArrayList<>();

  public class SerialReader extends Thread {

    private InputStream in;
    public boolean stop = false;

    @Override
    public void run() {
      stop = false;
      try {
        while (true) {
          if (stop) {
            return;
          }

          while (in.available() > 0) {
            int cc = in.read();

            owner.getSingleByte(cc);

            if (stop) {
              return;
            }
          }
        }
      } catch (IOException e) {
        System.out.println("SerialReader run error: " + e);
      }
    }
  }

  class SerialListener implements SerialChangeListener {

    @Override
    public void serialStateChanged(SerialLineState state) {

      if (state.equals(state.dataTerminalReady)) {
        try {
          byte[] buffer = new byte[input.available()];

          input.read(buffer);

          for (int i = 0; i < buffer.length; i++) {
            strBuffer.add(buffer[i]);
          }

          make();

        } catch (IOException e) {
          System.out.println("Serial listener error: " + e);
        }
      }
    }
  }
}
