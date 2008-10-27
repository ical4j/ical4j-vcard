/*
 * This file is part of Touchbase.
 *
 * Created: [27/10/2008]
 *
 * Copyright (c) 2008, Ben Fortuna
 *
 * Touchbase is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Touchbase is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Touchbase.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.fortuna.ical4j.vcard.property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.runners.Parameterized.Parameters;

import net.fortuna.ical4j.vcard.Parameter;
import net.fortuna.ical4j.vcard.Property;
import net.fortuna.ical4j.vcard.PropertyTest;
import net.fortuna.ical4j.vcard.Property.Name;


/**
 * @author fortuna
 *
 */
public class FnTest extends PropertyTest {

	/**
	 * @param property
	 * @param expectedName
	 * @param expectedValue
	 * @param expectedParams
	 */
	public FnTest(Property property, String expectedName, String expectedValue,
			Parameter[] expectedParams) {
		super(property, expectedName, expectedValue, expectedParams);
	}

    @Parameters
    public static Collection<Object[]> parameters() {
        List<Object[]> params = new ArrayList<Object[]>();
        params.add(new Object[] {new Fn("Jane Doe"), Name.FN.toString(), "Jane Doe", new Parameter[] {}});
        return params;
    }

}
