# MyOpenLab Community

This is a fork of oficial [MyOpenLab](https://myopenlab.org/)
The original sources were downloaded from <https://sourceforge.net/p/myopenlab3/code/HEAD/tree/>.

MyOpenLab is a graphical element-based development program aimed at simulating and modeling physical, electronic and control systems.

## Improvements

- Migrated to GraalVM Community 21.1.0 Java 11 OpenJDK.
- Added a modern cross-platform Look and Feel.
- Reformats Java source code to comply with [Google Java Style](https://github.com/google/google-java-format).
- Added support for Brazilian Portuguese.
- New icons, many still with old icons.

## Cleaning

- All examples and documents have been removed.
- Removed repeated components that stopped working with these updates.
- Removed old hardware drivers.

## To Do

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
  gradlew.bat shadowJar --warning-mode all
  ```

3. Run

  ```terminal
  java -Xms256M -Xmx512M -Dnashorn.args="--no-deprecation-warning" -jar myOpenLab.jar "elements"
  ```

or

  ```terminal
  run.bat
  ```

## License

This work is licensed under the [GNU GENERAL PUBLIC LICENSE](LICENSE).
