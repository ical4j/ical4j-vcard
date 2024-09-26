/**
 * Copyright (c) 2012, Ben Fortuna
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

import net.fortuna.ical4j.model.Prototype;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.stream.Collectors;

/**
 * vCard object.
 * <p>
 * $Id$
 * <p>
 * Created on 21/08/2008
 *
 * @author Ben
 */
public class VCard implements Serializable, Prototype<VCard>, EntityContainer {

    private EntityList entities;

    /**
     * Default constructor.
     */
    public VCard() {
        this(new EntityList());
    }

    /**
     * @param entities a list of properties
     */
    public VCard(EntityList entities) {
        this.entities = entities;
    }

    /**
     * Returns a reference to the list of entities for the VCard instance.
     *
     * @return the entities
     */
    @Override
    public EntityList getEntityList() {
        return entities;
    }

    @Override
    public void setEntityList(EntityList entities) {
        this.entities = entities;
    }

    /**
     * @throws ValidationException where validation fails
     */
    public ValidationResult validate() throws ValidationException {
        var result = new ValidationResult();

        getEntityList().getAll().forEach(e -> result.merge(e.validate()));
        return result;
    }

    @Override
    public VCard copy() {
        return new VCard(new EntityList(getEntityList().getAll().parallelStream()
                .map(Entity::copy).collect(Collectors.toList())));
    }

    /**
     * @return a vCard-compliant string representation of the vCard object
     */
    @Override
    public String toString() {
        return getEntityList().toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VCard) {
            var card = (VCard) obj;
            return new EqualsBuilder().append(getEntityList(), card.getEntityList()).isEquals();
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getEntityList()).toHashCode();
    }
}
