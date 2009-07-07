/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.codehaus.mojo.scmchangelog.scm.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;
import org.apache.maven.plugin.logging.Log;

/**
 *
 * @author ehugonnet
 */
public class ColorConsoleLogger implements Log {

  private static final int ATTR_DIM = 2;
  private static final int FG_RED = 31;
  private static final int FG_GREEN = 32;
  private static final int FG_BLUE = 34;
  private static final int FG_MAGENTA = 35;
  private static final int FG_CYAN = 36;
  private static final String PREFIX = "\u001b[";
  private static final String SUFFIX = "m";
  private static final char SEPARATOR = ';';
  private static final String END_COLOR = PREFIX + SUFFIX;
  private String errColor = PREFIX + ATTR_DIM + SEPARATOR + FG_RED + SUFFIX;
  private String warnColor = PREFIX + ATTR_DIM + SEPARATOR + FG_MAGENTA + SUFFIX;
  private String infoColor = PREFIX + ATTR_DIM + SEPARATOR + FG_CYAN + SUFFIX;
  private String verboseColor = PREFIX + ATTR_DIM + SEPARATOR + FG_GREEN + SUFFIX;
  private String debugColor = PREFIX + ATTR_DIM + SEPARATOR + FG_BLUE + SUFFIX;
  private Log realLogger;
  private boolean colorsSet = false;

  /**
   * Set the colors to use from a property file specified by the
   * special ant property ant.logger.defaults
   */
  private void setColors() {
    String userColorFile = System.getProperty("ant.logger.defaults");
    String systemColorFile =
        "/org/codehaus/mojo/defaults.properties";

    InputStream in = null;

    try {
      Properties prop = new Properties();

      if (userColorFile != null) {
        in = new FileInputStream(userColorFile);
      } else {
        in = getClass().getResourceAsStream(systemColorFile);
      }

      if (in != null) {
        prop.load(in);
      }

      String errC = prop.getProperty("AnsiColorLogger.ERROR_COLOR");
      String warn = prop.getProperty("AnsiColorLogger.WARNING_COLOR");
      String info = prop.getProperty("AnsiColorLogger.INFO_COLOR");
      String verbose = prop.getProperty("AnsiColorLogger.VERBOSE_COLOR");
      String debug = prop.getProperty("AnsiColorLogger.DEBUG_COLOR");
      if (errC != null) {
        errColor = PREFIX + errC + SUFFIX;
      }
      if (warn != null) {
        warnColor = PREFIX + warn + SUFFIX;
      }
      if (info != null) {
        infoColor = PREFIX + info + SUFFIX;
      }
      if (verbose != null) {
        verboseColor = PREFIX + verbose + SUFFIX;
      }
      if (debug != null) {
        debugColor = PREFIX + debug + SUFFIX;
      }
    } catch (IOException ioe) {
      //Ignore - we will use the defaults.
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          //Ignore - We do not want this to stop the build.
        }
      }
    }
  }

  public ColorConsoleLogger(Log realLogger) {
    this.realLogger = realLogger;
  }

  public boolean isDebugEnabled() {
    return this.realLogger.isDebugEnabled();
  }

  public void debug(CharSequence content) {
    this.realLogger.debug( debugColor + content + END_COLOR);
  }

  public void debug(CharSequence content, Throwable error) {
    this.debug( content  + "\n\n" + formatError( error) );
  }

  public void debug( Throwable error )
  {
    this.debug( formatError( error ) );
  }

  public boolean isInfoEnabled()
  {
    return this.realLogger.isInfoEnabled();
  }

  public void info(CharSequence content)
  {
    this.realLogger.info( infoColor + content + END_COLOR);
  }

  public void info(CharSequence content, Throwable error)
  {
    this.info( content  + "\n\n" + formatError( error) );
  }

  public void info(Throwable error)
  {
    this.info( formatError( error) );
  }

  public boolean isWarnEnabled()
  {
    return this.realLogger.isWarnEnabled();
  }

  public void warn(CharSequence content)
  {
    this.realLogger.warn( warnColor + content + END_COLOR);
  }

  public void warn(CharSequence content, Throwable error)
  {
    this.warn( content + "\n\n" + formatError(error) );
  }

  public void warn(Throwable error)
  {
    this.warn( formatError(error) );
  }

  public boolean isErrorEnabled()
  {
    return this.realLogger.isErrorEnabled();
  }

  public void error(CharSequence content)
  {
    this.realLogger.error( errColor +  content + END_COLOR );
  }

  public void error(CharSequence content, Throwable error)
  {
    this.error( content + "\n\n" + formatError( error ) );
  }

  public void error(Throwable error)
  {
    this.error( formatError( error ) );
  }

  private String formatError( Throwable error )
  {
    StringWriter sWriter = new StringWriter();
    PrintWriter pWriter = new PrintWriter( sWriter );
    error.printStackTrace( pWriter );
    return sWriter.toString();
  }
}
