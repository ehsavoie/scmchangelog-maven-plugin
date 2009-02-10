package org.codehaus.mojo.scmchangelog.changelog.log.grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.mojo.scmchangelog.changelog.log.Issue;
import org.codehaus.mojo.scmchangelog.changelog.log.Message;
import org.codehaus.mojo.scmchangelog.changelog.log.OperationTypeEnum;
import org.codehaus.mojo.scmchangelog.changelog.log.ScmGrammar;

/**
 * Simple grammar which recognizes bug references in the format: bug ####. For more information, refer to the
 * "Autolinkification" section of the Bugzilla manual: http://www.bugzilla.org/docs/3.2/en/html/hintsandtips.html
 * 
 * @author Matthew Beermann <matthew.beermann@cerner.com>
 */
public class BugzillaScmGrammar
    implements ScmGrammar
{
    private static final Pattern BUGZILLA_PATTERN =
        Pattern.compile( "bug\\s+#?([1-9][0-9]*)", Pattern.CASE_INSENSITIVE );

    public Message extractMessage( String content )
    {
        List issues = new ArrayList();
        Matcher matcher = BUGZILLA_PATTERN.matcher( content );
        while ( matcher.find() )
            issues.add( new Issue( matcher.group( 1 ), OperationTypeEnum.FIX ) );
        return new Message( content, issues );
    }

    public String getIssueSeparator()
    {
        return "\r\n";
    }

    public boolean hasMessage( String content )
    {
        Matcher matcher = BUGZILLA_PATTERN.matcher( content );
        return matcher.find();
    }
}
