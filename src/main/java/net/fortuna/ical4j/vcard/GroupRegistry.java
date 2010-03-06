/**
 * Copyright (c) 2010, Ben Fortuna
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  o Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  o Neither the name of Ben Fortuna nor the names of any other contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.fortuna.ical4j.vcard;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.fortuna.ical4j.vcard.Group.Id;

/**
 * A registry for standard and non-standard property groups.
 * 
 * $Id$
 *
 * Created on: 05/01/2009
 *
 * @author Ben
 *
 */
public class GroupRegistry {

    private static final Log LOG = LogFactory.getLog(GroupRegistry.class);
    
    private final Map<Id, Group> defaultGroups;
    
    private final Map<String, Group> extendedGroups;
    
    /**
     * 
     */
    public GroupRegistry() {
        defaultGroups = new HashMap<Id, Group>();
        defaultGroups.put(Id.HOME, Group.HOME);
        defaultGroups.put(Id.WORK, Group.WORK);
        extendedGroups = new ConcurrentHashMap<String, Group>();
    }
    
    /**
     * @param value a string representation of a group identifier
     * @return a registered group with the specified identifier. If no such group
     * is found null is returned
     */
    public Group getGroup(final String value) {
        try {
            return defaultGroups.get(Id.valueOf(value));
        }
        catch (Exception e) {
            LOG.info("Not a default group: [" + value + "]");
        }
        return extendedGroups.get(value);
    }
    
    /**
     * Registers a non-standard group.
     * @param extendedName the extended name of the group
     * @param group the group
     */
    public void register(final String extendedName, final Group group) {
        extendedGroups.put(extendedName, group);
    }
}
