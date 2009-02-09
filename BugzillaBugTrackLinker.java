package org.codehaus.mojo.scmchangelog.tracker;

import java.text.MessageFormat;

/**
 * An implementation for the Bugzilla bug tracker.
 * 
 * @author Matthew Beermann <matthew.beermann@cerner.com>
 */
public class BugzillaBugTrackLinker
    implements BugTrackLinker
{

    /**
     * The url as a pattern for the links.
     */
    private String pattern;

    /**
     * Creates a new instance of BugzillaBugTrackLinker
     */
    public BugzillaBugTrackLinker( String bugzillaUrl )
    {
        String url = bugzillaUrl;
        this.pattern = url.substring( 0, url.lastIndexOf( '/' ) ) + "/show_bug.cgi?id={0}";
    }

    /**
     * Computes the link to the description of the specified bug for Bugzilla.
     */
    public String getLinkUrlForBug( String bugNumber )
    {
        return MessageFormat.format( pattern, new Object[] { bugNumber } );
    }
}
