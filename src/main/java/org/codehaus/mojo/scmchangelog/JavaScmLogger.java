/*
The MIT License

Copyright (c) 2004, The Codehaus

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
of the Software, and to permit persons to whom the Software is furnished to do
so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package org.codehaus.mojo.scmchangelog;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.maven.scm.log.ScmLogger;

/**
 * Implementation of ScmLogger using java.util.logging API.
 * @author ehsavoie
 * @version $Id$
 * @see org.apache.maven.scm.log.ScmLogger
 */
public class JavaScmLogger
    implements ScmLogger
{

  private Logger logger = Logger.getLogger( JavaScmLogger.class.getName() );
  private Level currentLevel;

  public JavaScmLogger( Level level )
  {
    this.currentLevel = level;
  }

  public boolean isDebugEnabled()
  {
    return this.currentLevel.intValue() <= Level.FINE.intValue();
  }

  public void debug( String content )
  {
    if ( isDebugEnabled() )
    {
      logger.log( Level.FINE, content );
    }
  }

  public void debug( String content, Throwable error )
  {
    if ( isDebugEnabled() )
    {
      logger.log( Level.FINE, content, error );
    }
  }

  public void debug( Throwable error )
  {
    if ( isDebugEnabled() )
    {
      logger.log( Level.FINE, "", error );
    }
  }

  public boolean isInfoEnabled()
  {
    return this.currentLevel.intValue() <= Level.INFO.intValue();
  }

  public void info( String content )
  {
    if ( isInfoEnabled() )
    {
      logger.log( Level.INFO, content );
    }
  }

  public void info( String content, Throwable error )
  {
    if ( isInfoEnabled() )
    {
      logger.log( Level.INFO, content, error );
    }
  }

  public void info( Throwable error )
  {
    if ( isInfoEnabled() )
    {
      logger.log( Level.INFO, "", error );
    }
  }

  public boolean isWarnEnabled()
  {
    return this.currentLevel.intValue() <= Level.WARNING.intValue();
  }

  public void warn( String content )
  {
    if ( isWarnEnabled() )
    {
      logger.log( Level.WARNING, content );
    }
  }

  public void warn( String content, Throwable error )
  {
    if ( isWarnEnabled() )
    {
      logger.log( Level.WARNING, content, error );
    }
  }

  public void warn( Throwable error )
  {
    if ( isWarnEnabled() )
    {
      logger.log( Level.WARNING, "", error );
    }
  }

  public boolean isErrorEnabled()
  {
    return this.currentLevel.intValue() <= Level.SEVERE.intValue();
  }

  public void error( String content )
  {
    if ( isErrorEnabled() )
    {
      logger.log( Level.SEVERE, content );
    }
  }

  public void error( String content, Throwable error )
  {
    if ( isErrorEnabled() )
    {
      logger.log( Level.SEVERE, content, error );
    }
  }

  public void error( Throwable error )
  {
    if ( isErrorEnabled() )
    {
      logger.log( Level.SEVERE, "", error );
    }
  }
}
