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
package org.codehaus.mojo.scmchangelog.tracker;

/**
 * Manager for bug trackers.
 * @author ehsavoie
 * @version $Id$
 */
public class BugTrackers
{

  public static final BugTrackers CODEX = new BugTrackers( "sourceforge" );
  public static final BugTrackers JIRA = new BugTrackers( "jira" );
  private String name;

  private BugTrackers( String name )
  {
    this.name = name;
  }

  public int hashCode()
  {
    return this.name.hashCode();
  }

  public boolean equals( Object object )
  {
    if ( object instanceof BugTrackers )
    {
      BugTrackers operation = ( BugTrackers ) object;

      return this.name.equals( operation.name );
    }

    return false;
  }

  public static BugTrackers valueOf( String name )
  {
    if ( JIRA.name.equals( name ) )
    {
      return JIRA;
    }

    return CODEX;
  }
}
