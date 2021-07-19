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

import VisualLogic.*;
import VisualLogic.variables.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import tools.*;

public class PlayFromFile extends JVSMain {
  public VSBoolean inPlay;
  public VSBoolean inStop;
  public VSString inFilename;
  private AudioClip clip = null;

  private Image image;

  public void onDispose() {
    if (image != null) {
      image.flush();
      image = null;
    }
  }

  public void paint(java.awt.Graphics g) {
    drawImageCentred(g, image);
  }

  public void init() {
    initPins(0, 0, 0, 3);
    setSize(35 + 20, 35 + 10);
    initPinVisibility(false, false, false, true);

    image = element.jLoadImage(element.jGetSourcePath() + "icon.png");

    setPin(0, ExternalIF.C_BOOLEAN, element.PIN_INPUT);
    setPin(1, ExternalIF.C_BOOLEAN, element.PIN_INPUT);
    setPin(2, ExternalIF.C_STRING, element.PIN_INPUT);

    String strLocale = Locale.getDefault().toString();

    if (strLocale.equalsIgnoreCase("de_DE")) {
      element.jSetPinDescription(0, "Abspielen");
      element.jSetPinDescription(1, "Stop");
      element.jSetPinDescription(2, "Sound Pfad");
    }

    if (strLocale.equalsIgnoreCase("en_US")) {
      element.jSetPinDescription(0, "Play");
      element.jSetPinDescription(1, "Stop");
      element.jSetPinDescription(2, "Sound Path");
    }

    if (strLocale.equalsIgnoreCase("es_ES")) {
      element.jSetPinDescription(0, "Comienzo");
      element.jSetPinDescription(1, "Parada");
      element.jSetPinDescription(2, "Archivo de los sonidos");
    }

    if (strLocale.equalsIgnoreCase("pt_BR")) {
      element.jSetPinDescription(0, "Iniciar");
      element.jSetPinDescription(1, "Parar");
      element.jSetPinDescription(2, "Arquivo de Audio");
    }

    element.jSetCaptionVisible(true);
    element.jSetCaption("PlayFromFile");

    setName("PlayFromFile");
  }

  public void setPropertyEditor() {}

  public void propertyChanged(Object o) {}

  public void initInputPins() {
    inPlay = (VSBoolean) element.getPinInputReference(0);
    inStop = (VSBoolean) element.getPinInputReference(1);
    inFilename = (VSString) element.getPinInputReference(2);

    if (inPlay == null) {
      inPlay = new VSBoolean(false);
    }

    if (inStop == null) {
      inStop = new VSBoolean(false);
    }

    if (inFilename == null) {
      inFilename = new VSString("");
    }
  }

  public void initOutputPins() {}

  public void start() {}

  public void stop() {
    if (clip != null) clip.stop();
    clip = null;
  }

  public void process() {
    if (inPlay.getValue() == true)
      try {
        URL url = new URL("file:" + inFilename.getValue());

        if (clip != null) clip.stop();

        clip = Applet.newAudioClip(url);
        clip.play();
      } catch (Exception e) {
      }

    if (inStop.getValue() == true) {
      if (clip != null) clip.stop();
    }
  }
}
