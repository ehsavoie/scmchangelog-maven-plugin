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
package org.codehaus.mojo.scmchangelog.scm.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.scm.ScmBranch;
import org.apache.maven.scm.ScmException;
import org.apache.maven.scm.ScmFileSet;
import org.apache.maven.scm.ScmRevision;
import org.apache.maven.scm.ScmTag;
import org.apache.maven.scm.ScmVersion;
import org.apache.maven.scm.manager.ScmManager;
import org.apache.maven.scm.repository.ScmRepository;
import org.codehaus.mojo.scmchangelog.SvnTargetEnum;
import org.codehaus.mojo.scmchangelog.changelog.log.GrammarEnum;
import org.codehaus.mojo.scmchangelog.changelog.log.SvnLogEntry;
import org.codehaus.mojo.scmchangelog.scm.hg.command.changelog.BetterChangeSet;


/**
 * Abstract Adapter to be used to wrap the scm implementation.
 * @author ehsavoie
 * @version $Id: ScmAdapter.java 8964 2009-01-31 22:26:07Z ehsavoie $
 */
public abstract class ScmAdapter {
 /**
  * The ScmManager to access SCM elements.
  */
  protected ScmManager manager;
  /**
   * The grammar used to extract elements from the comments.
   */
  protected GrammarEnum grammar;
  /**
   * The maven logger.
   */
  protected Log logger;

  /**
   * Constructor of ScmAdapter.
   * @param currentManager the ScmManager to access SCM elements.
   * @param currentGrammar the grammar used to extract elements from the comments.
   */
  public ScmAdapter( ScmManager currentManager, GrammarEnum currentGrammar )
  {
    this.manager = currentManager;
    this.grammar = currentGrammar;
  }

   /**
   * The currentlogger.
   * @return the logger
   */
  public Log getLogger()
  {
    return logger;
  }

  /**
   * The current logger to be used.
   * @param logger the logger to set
   */
  public void setLogger( Log logger )
  {
    this.logger = logger;
  }

  /**
   * Returns the Scm version.
   * @param versionType the type of version (tag, trunk, branch).
   * @param version the tag/branche name.
   * @return the corresponding ScmVersion.
   * @throws org.apache.maven.plugin.MojoExecutionException in case of an error in executing the Mojo.
   */
  public ScmVersion getScmVersion( SvnTargetEnum versionType, String version )
      throws MojoExecutionException
  {
    if ( SvnTargetEnum.TAG.equals( versionType ) )
    {
      return new ScmTag( version );
    }
    else if ( SvnTargetEnum.BRANCH.equals( versionType ) )
    {
      return new ScmBranch( version );
    }
    else if ( SvnTargetEnum.TRUNK.equals( versionType ) )
    {
      return new ScmRevision( version );
    }
    throw new MojoExecutionException( "Unknown version type : "
        + versionType );
  }

    /**
   * Returns the list of log entries defined in the list of ChangeSet.
   * @param changeSets the list of ChangeSet.
   * @return the list of log entries defined in the list of ChangeSet. <code>List&lt;SvnLogEntry&gt;</code>
   */
  protected List getEntries( List changeSets )
  {
    List elements = new ArrayList( changeSets.size() );
    Iterator iter = changeSets.iterator();
    while ( iter.hasNext() )
    {
      BetterChangeSet changeSet = ( BetterChangeSet ) iter.next();
      SvnLogEntry entry = new SvnLogEntry();
      entry.setAuthor( changeSet.getAuthor() );
      entry.setDate( changeSet.getDate() );
      getLogger().info( changeSet.getComment() );
      entry.setMessage( grammar.extractMessage( changeSet.getComment() ) );
      entry.setRevision( changeSet.getRevision() );
      elements.add( entry );
    }
    return elements;
  }

  /**
   * Returns the list of releases defined in the SCM.
   * @param repository the SCM repository.
   * @param fileSet the base fileset.
   * @return the list of releases defined in the SCM. <code>List&lt;Release&gt;</code>
   * @throws org.apache.maven.scm.ScmException in case of an error with the SCM.
   * @throws org.apache.maven.plugin.MojoExecutionException in case of an error in executing the Mojo.
   */
  public abstract List getListOfReleases( ScmRepository repository, ScmFileSet fileSet )
      throws ScmException, MojoExecutionException;
}