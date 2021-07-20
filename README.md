# MyOpenLab BR Community

This is a hard fork of the parent project [MyOpenLab](http://myopenlab.org) to bring support to Brazilian students and teachers in their native language (other languages ​​are supported), as well as using modern development practices and tools. MyOpenLab is a graphical element-based development program aimed at simulating and modeling physical, electronic and control systems. It is a free tool whose project was started by Carmelo Daniel Salafia and transferred in 2017 to current programmer Javier Velásquez. This tool is available free of charge under a GNU General Public License in Spanish, English, German and Brazilian.

The original sources were downloaded from <https://sourceforge.net/p/myopenlab3/code/HEAD/tree/>.

MyOpenLab is a graphical element-based development program aimed at simulating and modeling physical, electronic and control systems.

## Improvements

- Migrated to GraalVM Community 21.1.0 Java 11 OpenJDK.
- Added support for Brazilian Portuguese.
- Added a modern cross-platform Look and Feel.
- Reformats Java source code to comply with [Google Java Style](https://github.com/google/google-java-format).
- New icons, many still with old icons.

## Cleaning

- All examples and documents have been removed.
- Removed repeated components that stopped working with these updates.
- Removed old hardware drivers.

## ToDo

- All documentation must be redone;
- Add support for integration with [PICSimLab](https://github.com/lcgamboa/picsimlab) (excellent support for many microcontrollers);
- Modern drivers for communication ports (USB);
- Improve communication with new hardwares (Arduino/Raspberry);
- Old icons must be improved.
- Improve this README ;)

## Quick start

1. Install [GraalVM Community 21.1.0](https://github.com/graalvm/graalvm-ce-builds/releases/tag/vm-21.1.0) Java 11 OpenJDK based

2. Build using gradle

  ```terminal
  gradlew shadowJar --warning-mode all
  ```

or on Windows

  ```terminal
  build.bat
  ```

3. Run

  ```terminal
  java -Xms256M -Xmx512M -Dnashorn.args="--no-deprecation-warning" -jar myOpenLab.jar "elements"
  ```

or on Windows

  ```terminal
  run.bat
  ```

## License

This work is licensed under the [GNU GENERAL PUBLIC LICENSE](LICENSE).
