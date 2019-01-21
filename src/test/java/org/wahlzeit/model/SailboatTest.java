/*
 * Copyright (c) 2018-2019 by Daniel Ziegler
 *
 * This file is part of the Wahlzeit photo rating application.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package org.wahlzeit.model;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.wahlzeit.testEnvironmentProvider.LocalDatastoreServiceTestConfigProvider;
import org.wahlzeit.testEnvironmentProvider.RegisteredOfyEnvironmentProvider;

import static org.junit.Assert.assertEquals;

/**
 * Test for the {@link Sailboat} class.
 */
public class SailboatTest {
	private SailboatType sailboatType;
	private Sailboat sailboat;

	@ClassRule
	public static RuleChain ruleChain = RuleChain.
			outerRule(new LocalDatastoreServiceTestConfigProvider()).
			around(new RegisteredOfyEnvironmentProvider());

	@Before
	public void setup() {
		sailboatType = new SailboatType("Dschunke");
		sailboat = new Sailboat(sailboatType, "Dschunke", 12.0);
	}

	@Test
	public void testCreation() {
		assertEquals(sailboatType, sailboat.getType());
		assertEquals("Dschunke", sailboat.getName());
		assertEquals(12.0, sailboat.getLength(), 1.0e-10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetInvalidName() {
		sailboat.setName(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetInvalidLengthZero() {
		sailboat.setLength(0.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetInvalidLengthNaN() {
		sailboat.setLength(Double.NaN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetInvalidLengthNegative() {
		sailboat.setLength(-42.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetInvalidType() {
		sailboat.setType(null);
	}
}
