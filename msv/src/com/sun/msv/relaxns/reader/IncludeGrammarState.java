/*
 * @(#)$Id$
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
package com.sun.tranquilo.relaxns.reader;

import com.sun.tranquilo.reader.ChildlessState;

/**
 * parses &lt;include&gt; element of RELAX Namespace.
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
public class IncludeGrammarState extends ChildlessState
{
	protected void startSelf()
	{
		super.startSelf();
	
		final String href = startTag.getAttribute("grammarLocation");

		if(href==null)
		{// name attribute is required.
			reader.reportError( reader.ERR_MISSING_ATTRIBUTE,
				"include","grammarLocation");
			// recover by ignoring this include element
		}
		else
			reader.switchSource(href,new RootGrammarMergeState());
	}
}