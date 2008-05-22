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
package org.codehaus.mojo.scmchangelog.changelog.log;

import org.codehaus.mojo.scmchangelog.changelog.log.grammar.ManuSvnGrammar;
import org.codehaus.mojo.scmchangelog.changelog.log.grammar.RemySvnGrammar;

/**
 * The currently supported grammars.
 * @author ehsavoie
 * @version $Id$
 */
public class GrammarEnum
{

  public static final GrammarEnum MANU = new GrammarEnum( new ManuSvnGrammar(),
      "MANU" );
  public static final GrammarEnum REMY = new GrammarEnum( new RemySvnGrammar(),
      "REMY" );
  private SvnGrammar grammar;
  private String name;

  private GrammarEnum( SvnGrammar grammar, String name )
  {
    this.grammar = grammar;
    this.name = name;
  }

  public Message extractMessage( final String content )
  {
    return this.grammar.extractMessage( content );
  }

  public boolean hasMessage( final String content )
  {
    return this.grammar.hasMessage( content );
  }

  /**
   * Returns the String to be inserted between each issue comment. It may be replaced
   * when generating the report.
   * @return the String to be inserted between each issue comment.
   */
  public String getIssueSeparator()
  {
    return this.grammar.getIssueSeparator();
  }

  public static GrammarEnum valueOf( String name )
  {
    if ( REMY.name.equalsIgnoreCase( name ) )
    {
      return REMY;
    }

    return MANU;
  }
}
