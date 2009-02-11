/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.codehaus.mojo.scmchangelog.scm.util;

import java.util.List;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.scm.ScmException;
import org.apache.maven.scm.ScmFileSet;
import org.apache.maven.scm.manager.ScmManager;
import org.apache.maven.scm.repository.ScmRepository;
import org.codehaus.mojo.scmchangelog.changelog.log.GrammarEnum;

/**
 *
 * @author ehsavoie
 */
public class DefaultScmAdapter extends ScmAdapter {
/**
   * Constructor of ScmAdapter.
   * @param currentManager the ScmManager to access SCM elements.
   * @param currentGrammar the grammar used to extract elements from the comments.
   */
  public DefaultScmAdapter( ScmManager currentManager, GrammarEnum currentGrammar )
  {
    super( currentManager, currentGrammar);
  }

   /**
   * Returns the list of releases defined in a SCM repository.
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
    throw new MojoExecutionException( "Unsupported SCM" );
  }
}
