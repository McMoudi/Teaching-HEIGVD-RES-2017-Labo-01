package ch.heigvd.res.lab01.impl.filters;

import org.apache.commons.io.IOExceptionWithCause;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 * <p>
 * Hello\n\World -> 1\tHello\n2\tWorld
 *
 * @author Olivier Liechti
 * @author Yann Mahmoudi
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  private boolean isFirstLine, isRNewLine;
  private int lineNumber;


  public FileNumberingFilterWriter(Writer out) {
    super(out);
    isFirstLine = true;
    isRNewLine = false;
    lineNumber = 0;
  }

  /**
   * writes `str`  to the output while prepending every lines with its number and a tab
   * @param str the string to write into the output
   * @param off the index of the first char in the subsequence
   * @param len the length of the subsequence
   * @throws IOException If an I/O error occurs
   */
  @Override
  public void write(String str, int off, int len) throws IOException {

    for (int i = 0; i < len; ++i) write(str.charAt(off + i));

  }
  /**
   * writes cbuf  to the output while prepending every lines with its number and a tab
   * @param cbuf the char table to write into the output
   * @param off the index of the first char in the subsequence
   * @param len the length of the subsequence
   * @throws IOException If an I/O error occurs
   */
  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {

    if (len + off >= cbuf.length) throw new ArrayIndexOutOfBoundsException("waddup");

    for (int i = 0; i < len; ++i) write(cbuf[off + i]);
  }

  /**
   * write the char c to the output while prepending every new lines with its number and a tab
   * @param c the char to write
   * @throws IOException
   */
  @Override
  public void write(int c) throws IOException {

    //we write the line number if it's the first line of the file
    if (isFirstLine) {
      lineNumberWrite();
      isFirstLine = false;
    }

    //if '\r' has been used and we are not on windows; then the char right after isn't '\n'. we can safely write the line number
    if (isRNewLine && c != '\n') {
      lineNumberWrite();
    }

    //in both cases (on macOS and windows) we've got to reset the flag right after it has been set to true
    if (isRNewLine) isRNewLine = false;

    super.write(c);

    // we cannot write a line number at the end of the file for macOS as we do not know whether it's windows or not.
    // yet, we can write the line number right after any '\n'
    if (c == '\n') {
      lineNumberWrite();
    } else if (c == '\r') {
      isRNewLine = true;
    }
  }


  private void lineNumberWrite() throws IOException {
    String out = ++lineNumber + "\t";
    super.write(out, 0, out.length());
  }

}
