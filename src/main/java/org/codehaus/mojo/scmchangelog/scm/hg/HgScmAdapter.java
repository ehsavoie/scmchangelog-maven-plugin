/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.codehaus.mojo.scmchangelog.scm.hg;

import java.util.ArrayList;
import java.util.Collections;
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
public class HgScmAdapter extends ScmAdapter {

  public HgScmAdapter( ScmManager currentManager, GrammarEnum currentGrammar )
  {
    super( currentManager, currentGrammar );
  }


  public List getListOfReleases(ScmRepository repository, ScmFileSet fileSet) throws ScmException, MojoExecutionException {
    ListScmResult result = this.manager.list(repository, fileSet, false,
            getScmVersion(SvnTargetEnum.TAG, ""));
    final List tags = result.getFiles();
    final List releases = new ArrayList(10);
    Iterator iter = tags.iterator();
    String startRevision = "0";

    while (iter.hasNext()) {
      Tag tag = (Tag) iter.next();
      getLogger().info(tag.toString());

      final ChangeLogScmResult logs = this.manager.changeLog(repository,
              fileSet, getScmVersion(SvnTargetEnum.TRUNK, startRevision),
              getScmVersion(SvnTargetEnum.TRUNK, tag.getEndRevision()), "");
      startRevision = tag.getEndRevision();
      getLogger().info(logs.getChangeLog().toString());
      tag.setDate(logs.getChangeLog().getEndDate());

      Release release = new Release(tag,
              getEntries(logs.getChangeLog().getChangeSets()));
      releases.add(release);
    }
    Collections.reverse(releases);
    return releases;
  }
}
