/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.codehaus.mojo.scmchangelog.changelog.log.grammar;

import java.util.ArrayList;
import org.codehaus.mojo.scmchangelog.changelog.log.Message;
import org.codehaus.mojo.scmchangelog.changelog.log.ScmGrammar;

/**
 *
 * @author ehsavoie
 */
public class AcceptAllGrammar implements ScmGrammar {

    public Message extractMessage(String content)
    {
        return new Message( content, new ArrayList() );
    }

    public boolean hasMessage(String content)
    {
        return true;
    }

    public String getIssueSeparator()
    {
        return NEW_LINE;
    }

}
