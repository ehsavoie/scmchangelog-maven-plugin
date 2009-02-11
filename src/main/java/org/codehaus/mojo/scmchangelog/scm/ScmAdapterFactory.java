/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
