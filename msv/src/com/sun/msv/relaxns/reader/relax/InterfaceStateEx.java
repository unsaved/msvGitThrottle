/*
 * @(#)$Id$
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
package com.sun.tranquilo.relaxns.reader.relax;

import com.sun.tranquilo.util.StartTagInfo;
import com.sun.tranquilo.reader.State;
import com.sun.tranquilo.reader.relax.core.InterfaceState;
import com.sun.tranquilo.grammar.relax.RELAXModule;
import com.sun.tranquilo.grammar.Expression;
import com.sun.tranquilo.reader.ChildlessState;

/**
 * parses &lt;interface&gt; element and &lt;div&gt; in interface.
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
class InterfaceStateEx extends InterfaceState
{
	protected State createChildState( StartTagInfo tag )
	{
		RELAXCoreIslandSchemaReader reader = (RELAXCoreIslandSchemaReader)this.reader;
		if(!tag.namespaceURI.equals(reader.RELAXCoreNamespace))	return null;
		
		if(tag.localName.equals("div"))		return new InterfaceStateEx();
		
		
		RELAXModule module = reader.getModule();
		
		if(tag.localName.equals("export"))
		{
			final String role = tag.getAttribute("role");
			
			if(role!=null)
			{
				module.attPools.getOrCreate(role).exported = true;
				return new ChildlessState();
			}
			// base class may process this element.
		}
		if(tag.localName.equals("hedgeExport"))
		{
			final String label = tag.getAttribute("label");
			if(label==null)
				reader.reportError(reader.ERR_MISSING_ATTRIBUTE,"hedgeExport","label");
				// recover by ignoring this hedgeExport
			else
				module.hedgeRules.getOrCreate(label).exported = true;
			
			return new ChildlessState();
		}
		
		return super.createChildState(tag);
	}
}