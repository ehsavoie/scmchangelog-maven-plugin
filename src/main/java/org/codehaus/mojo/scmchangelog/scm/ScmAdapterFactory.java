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
package org.codehaus.mojo.scmchangelog.scm;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.scm.manager.ScmManager;
import org.codehaus.mojo.scmchangelog.scm.util.ScmAdapter;
import org.apache.maven.scm.repository.ScmRepository;
import org.codehaus.mojo.scmchangelog.changelog.log.GrammarEnum;
import org.codehaus.mojo.scmchangelog.scm.hg.HgScmAdapter;
import org.codehaus.mojo.scmchangelog.scm.svn.SvnScmAdapter;
import org.codehaus.mojo.scmchangelog.scm.util.DefaultScmAdapter;

/**
 *
 * @author ehsavoie
 */
public class ScmAdapterFactory {

  public static final ScmAdapter getInstance( ScmManager currentManager, GrammarEnum currentGrammar , ScmRepository repository, Log logger )
  {
    ScmAdapter adapter;
    if ( "svn".equals( repository.getProvider() ) )
    {
      adapter = new SvnScmAdapter( currentManager, currentGrammar );
    }

    if ( "hg".equals( repository.getProvider() ) )
    {
      adapter = new HgScmAdapter( currentManager, currentGrammar );
    }

    else
    {
      adapter = new DefaultScmAdapter( currentManager, currentGrammar );
    }

    adapter.setLogger( logger );
    return adapter;
  }
}
