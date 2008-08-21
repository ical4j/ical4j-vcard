/*
 * $Id$
 *
 * Created on 21/08/2008
 *
 * Copyright (c) 2008, Ben Fortuna
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
package net.fortuna.ical4j.vcard.parameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.fortuna.ical4j.vcard.Parameter;

/**
 * @author Ben
 *
 */
public final class Type extends Parameter {

    /**
     * 
     */
    private static final long serialVersionUID = -3644362129355908795L;
    
    public static final Type HOME = new Type("home");
    
    public static final Type WORK = new Type("work");
    
    public static final Type PREF = new Type("pref");
    
    private String[] types;
    
    /**
     * @param types
     */
    public Type(String...types) {
        super(Name.TYPE);
        this.types = types;
    }
    
    /**
     * @param types
     */
    public Type(Type...types) {
        super(Name.TYPE);
        List<String> typeList = new ArrayList<String>();
        for (Type type : types) {
            typeList.addAll(Arrays.asList(type.getTypes()));
        }
        this.types = typeList.toArray(new String[typeList.size()]);
    }
    
    /**
     * @return the types
     */
    public String[] getTypes() {
        return types;
    }

    /**
     * @param types the types to set
     */
    public void setTypes(String[] types) {
        this.types = types;
    }

    /* (non-Javadoc)
     * @see net.fortuna.ical4j.vcard.Parameter#getValue()
     */
    @Override
    public String getValue() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < types.length; i++) {
            b.append(types[i]);
            if (i < types.length - 1) {
                b.append(',');
            }
        }
        return b.toString();
    }

}
