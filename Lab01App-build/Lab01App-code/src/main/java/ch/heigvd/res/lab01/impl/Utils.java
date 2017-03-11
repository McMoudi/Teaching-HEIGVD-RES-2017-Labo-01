package ch.heigvd.res.lab01.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments. 
   * 
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines) {


    String [] tabLines = lines.split("[\n\r]+",2);

    if(tabLines.length >1){

      int offSet = tabLines[0].length();
      int delta = lines.length() - (tabLines[0].length() + tabLines[1].length());

      tabLines[0] += lines.substring(offSet,offSet+ delta);
    } else {
      tabLines = new String[2];
      tabLines[0] = "";
      tabLines[1] = lines;
    }
    return tabLines;

  }

}
