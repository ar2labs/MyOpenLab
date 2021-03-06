package MyTerminal;

import java.util.Arrays;

/**
 * StringUtils
 *
 * @author hexprobe <hexprobe@nbug.net>
 * @license This code is hereby placed in the public domain.
 */
public class StringUtils {
  private StringUtils() {}

  public static int getPhisicalWidth(char hi, char lo) {
    if (hi <= 127) {
      if (' ' <= hi && hi <= '~') {
        return 1;
      } else {
        return 0;
      }
    } else {
      // toHexString: '｡' <= hi && hi <= 'ﾟ'
      if ('\uff61' <= hi && hi <= '\uff9f') {
        return 1;
      } else {
        return 2;
      }
    }
  }

  public static String join(String delimiter, Iterable<String> strings) {
    StringBuilder sb = new StringBuilder();
    boolean first = true;

    for (String str : strings) {
      if (first) {
        first = false;
      } else {
        sb.append(delimiter);
      }
      sb.append(str);
    }

    return sb.toString();
  }

  public static String join(String delimiter, String... strings) {
    return join(delimiter, Arrays.asList(strings));
  }
}
