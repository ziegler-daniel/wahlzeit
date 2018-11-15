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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test cases for the {@link CartesianCoordinate} class.
 */
public class CartesianCoordinateTest {

	/**
	 * If the difference between two double values is below this threshold they are assumed as equal.
	 */
	private static final double THRESHOLD_DOUBLE = 1.0E-5;

	private CartesianCoordinate c1;
	private CartesianCoordinate c2;
	private CartesianCoordinate c3;
	private CartesianCoordinate c4;
	private CartesianCoordinate c5;

	private SphericCoordinate s1;
	private SphericCoordinate s2;
	private SphericCoordinate s3;
	private SphericCoordinate s4;
	private SphericCoordinate s5;


	/**
	 *
	 */
	@Before
	public void setUp() {
		c1 = new CartesianCoordinate(0.0, 0.0, 0.0);
		c2 = new CartesianCoordinate(1.0, 1.0, 1.0);
		c3 = new CartesianCoordinate(3.0, 4.0, -6.5);
		c4 = new CartesianCoordinate(3.0, 4.0, -6.5);
		c5 = new CartesianCoordinate(1.0, 1.0, 0.0);

		s1 = new SphericCoordinate(0.0, 1.0, 3.234);
		s2 = new SphericCoordinate(Math.sqrt(3), 0.9553166181, 0.7853981634);
		s3 = new SphericCoordinate(8.200609733, 2.485897027, 0.927295218);
		s4 = new SphericCoordinate(8.200609733, 2.485897027, 0.927295218);
		s5 = new SphericCoordinate(Math.sqrt(2), Math.PI/2, Math.PI/4);
	}

	@Test
	public void testAsCartesianCoordinate() {
		assert (c1 == c1.asCartesianCoordinate());
		assert (c2 == c2.asCartesianCoordinate());
		assert (c3 == c3.asCartesianCoordinate());
		assert (c4 == c4.asCartesianCoordinate());
	}

	@Test
	public void testGetCartesianDistance() {
		assertEquals(0.0, c1.getCartesianDistance(c1), THRESHOLD_DOUBLE);
		assertEquals(Math.sqrt(3.0), c2.getCartesianDistance(c1), THRESHOLD_DOUBLE);
		assertEquals(Math.sqrt(67.25), c3.getCartesianDistance(c1), THRESHOLD_DOUBLE);
		assertEquals(Math.sqrt(69.25), c3.getCartesianDistance(c2), THRESHOLD_DOUBLE);
		assertEquals(1.0, c2.getCartesianDistance(c5), THRESHOLD_DOUBLE);
	}

	@Test
	public void testAsSphericCoordinate() {
		assertTrue(c1.asSphericCoordinate().isEqual(s1));
		assertTrue(c2.asSphericCoordinate().isEqual(s2));
		assertTrue(c3.asSphericCoordinate().isEqual(s3));
		assertTrue(c4.asSphericCoordinate().isEqual(s4));
		assertTrue(c5.asSphericCoordinate().isEqual(s5));
	}

	@Test
	public void testGetCentralAngle(){
		assertEquals(0.0, c2.getCentralAngle(s2), THRESHOLD_DOUBLE);
		assertEquals(0.0, c3.getCentralAngle(s3), THRESHOLD_DOUBLE);
		assertEquals(0.0, c3.getCentralAngle(s4), THRESHOLD_DOUBLE);
		assertEquals(Math.PI/2 - 0.9553166181, c5.getCentralAngle(c2), THRESHOLD_DOUBLE);
	}


	@Test(expected = IllegalArgumentException.class)
	public void testGetDistanceForNull() {
		c1.getCartesianDistance(null);
	}

	@Test
	public void testIsEqual() {
		assertFalse(c1.isEqual(c2));
		assertFalse(c2.isEqual(c3));
		assertTrue(c3.isEqual(c4));
		assertTrue(c2.isEqual(c2));
	}

	@Test
	public void testEquals() {
		assertFalse(c2.equals(c3));
		assertFalse(c1.equals(null));
		assertEquals(c3, c4);
		assertEquals(c1, c1);
	}

	@Test
	public void testAssertCoordinateValueValid(){
		c1.assertCoordinateValueValid(1.0);
		c1.assertCoordinateValueValid(-231431.2314);
		c1.assertCoordinateValueValid(2314.5679340);
		assert(true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertCoordinateValueValidNaN(){
		c1.assertCoordinateValueValid(Double.NaN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertCoordinateValueValidInfinite(){
		c1.assertCoordinateValueValid(Double.POSITIVE_INFINITY);
	}
}
