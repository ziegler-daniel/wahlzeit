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
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test cases for the {@link Coordinate} class.
 */
public class CoordinateTest {

	/**
	 * If the difference between two double values is below this threshold they are assumed as equal.
	 */
	private static final double THRESHOLD_DOUBLE = 10E-5;

	private Coordinate c1;
	private Coordinate c2;
	private Coordinate c3;
	private Coordinate c4;

	/**
	 *
	 */
	@Before
	public void setUp() {
		c1 = new Coordinate(0.0, 0.0, 0.0);
		c2 = new Coordinate(1.0, 1.0, 1.0);
		c3 = new Coordinate(3.0, 4.0, -6.5);
		c4 = new Coordinate(3.0, 4.0, -6.5);
	}

	/**
	 *
	 */
	@Test
	public void testGetDistance() {
		assertEquals(0.0, c1.getDistance(c1), THRESHOLD_DOUBLE);
		assertEquals(Math.sqrt(3.0), c2.getDistance(c1), THRESHOLD_DOUBLE);
		assertEquals(Math.sqrt(67.25), c3.getDistance(c1), THRESHOLD_DOUBLE);
		assertEquals(Math.sqrt(69.25), c3.getDistance(c2), THRESHOLD_DOUBLE);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testGetDistanceForNull() {
		c1.getDistance(null);
	}

	/**
	 *
	 */
	@Test
	public void testIsEqual() {
		assertTrue(!c1.isEqual(c2));
		assertTrue(!c2.isEqual(c3));
		assertTrue(c3.isEqual(c4));
		assertTrue(c2.isEqual(c2));
	}

	/**
	 *
	 */
	@Test
	public void testEquals() {
		assertTrue(!c2.equals(c3));
		assertTrue(!c1.equals(null));
		assertEquals(c3, c4);
		assertEquals(c1, c1);
	}
}
