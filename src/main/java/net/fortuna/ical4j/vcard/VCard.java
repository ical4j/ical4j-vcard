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

import net.fortuna.ical4j.model.ConstraintViolationException;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.Prototype;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.validate.ValidationResult;
import net.fortuna.ical4j.vcard.property.Uid;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiFunction;
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

    public static final BiFunction<VCard, List<Entity>, VCard> MERGE_ENTITIES = (c, list) -> {
        list.forEach(entity -> {
            if (!c.getEntities().contains(entity)) {
                c.add(entity.copy());
            }
        });
        return c;
    };

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
     * Merge all entities from the specified vCard with this instance.
     *
     * @param c2 the second card to merge
     * @return a vCard object containing all entities from both of the cards
     */
    public VCard merge(VCard c2) {
        VCard copy = this.copy();
        copy.with(MERGE_ENTITIES, c2.getEntities());
        return copy;
    }

    /**
     * Splits a vCard object into distinct vCard objects for unique identifiers (UID).
     *
     * @return an array of vCard objects
     */
    public VCard[] split() {
        if (getEntities().size() <= 1) {
            return new VCard[]{this};
        }

        final Map<Uid, VCard> cards = new HashMap<Uid, VCard>();
        for (final var c : getEntities()) {
            final Optional<Uid> uid = c.getUid();
            if (uid.isPresent()) {
                var uidCal = cards.get(uid.get());
                if (uidCal == null) {
                    cards.put(uid.get(), new VCard(new EntityList(Collections.singletonList(c))));
                } else {
                    uidCal.add(c);
                }
            }
        }
        return cards.values().toArray(VCard[]::new);
    }

    /**
     * Returns a unique identifier as specified by components in the vCard object.
     *
     * @return the UID property
     * @throws ConstraintViolationException if zero or more than one unique identifier(s) is
     *                                      found in the specified vCard
     */
    public Uid getUid() throws ConstraintViolationException {
        Uid uid = null;
        for (final var c : entities.getAll()) {
            for (final var foundUid : c.getProperties(Property.UID)) {
                if (uid != null && !uid.equals(foundUid)) {
                    throw new ConstraintViolationException("More than one UID found in card");
                }
                uid = (Uid) foundUid;
            }
        }
        if (uid == null) {
            throw new ConstraintViolationException("Card must specify a single unique identifier (UID)");
        }
        return uid;
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
