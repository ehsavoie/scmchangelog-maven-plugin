/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.codehaus.mojo.scmchangelog.scm.svn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.scm.ScmException;
import org.apache.maven.scm.ScmFileSet;
import org.apache.maven.scm.command.changelog.ChangeLogScmResult;
import org.apache.maven.scm.command.list.ListScmResult;
import org.apache.maven.scm.manager.ScmManager;
import org.apache.maven.scm.repository.ScmRepository;
import org.codehaus.mojo.scmchangelog.SvnTargetEnum;
import org.codehaus.mojo.scmchangelog.changelog.Release;
import org.codehaus.mojo.scmchangelog.changelog.log.GrammarEnum;
import org.codehaus.mojo.scmchangelog.scm.util.ScmAdapter;
import org.codehaus.mojo.scmchangelog.tags.Tag;

/**
 *
 * @author ehsavoie
 */
public class SvnScmAdapter extends ScmAdapter
{
  public SvnScmAdapter( ScmManager currentManager, GrammarEnum currentGrammar )
  {
    super( currentManager, currentGrammar );
  }

  /**
   * Returns the list of releases defined in the subversion repository.
   * @param repository the SCM repository.
   * @param fileSet the base fileset.
   * @return the list of releases defined in the SCM. <code>List&lt;Release&gt;</code>
   * @throws org.apache.maven.scm.ScmException in case of an error with the SCM.
   * @throws org.apache.maven.plugin.MojoExecutionException in case of an error in executing the Mojo.
   */
  public List getListOfReleases( ScmRepository repository,
      ScmFileSet fileSet )
      throws MojoExecutionException, ScmException
  {
    ListScmResult result = this.manager.list( repository, fileSet, false,
        getScmVersion( SvnTargetEnum.TAG, "" ) );
    final List tags = result.getFiles();
    getLogger().info( tags.toString() );
    final List releases = new ArrayList( 10 );
    Iterator iter = tags.iterator();

    while ( iter.hasNext() )
    {
      Tag tag = ( Tag ) iter.next();
      final ChangeLogScmResult logs = this.manager.changeLog( repository,
          fileSet,
          getScmVersion( SvnTargetEnum.TRUNK, tag.getStartRevision() ),
          getScmVersion( SvnTargetEnum.TRUNK, tag.getEndRevision() ), "" );
      if ( logs.getChangeLog() != null )
      {
        Release release = new Release( tag,
            logs.getChangeLog().getChangeSets() );
        releases.add( release );
      }
    }
    String endRevision = "0";
    if ( !tags.isEmpty() )
    {
      endRevision = ( ( Tag ) tags.get( tags.size() - 1 ) ).getEndRevision();
    }
    getLogger().info( "End revision : " + endRevision );
    final Tag trunk = new Tag( "trunk" );
    trunk.setStartRevision( endRevision );
    trunk.setDate( new Date() );
    trunk.setEndRevision( null );

    final ChangeLogScmResult logs = this.manager.changeLog( repository,
        fileSet, getScmVersion( SvnTargetEnum.TRUNK, endRevision ), null, "" );
    if ( logs.getChangeLog() != null )
    {
      final Release release = new Release( trunk,  logs.getChangeLog().getChangeSets() );
      releases.add( release );
    }
    Collections.reverse( releases );
    return releases;
  }
}
