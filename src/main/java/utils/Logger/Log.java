package utils.Logger;

public class Log {
  public static boolean debug = true;

  public static void debug(String message) {
    if(debug) {
      System.out.println(message);
    }
  }
}
