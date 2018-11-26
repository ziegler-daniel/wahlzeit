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
 * Test cases for the {@link SphericCoordinate} class.
 */
public class SphericCoordinateTest {

	/**
	 * If the difference between two double values is below this threshold they are assumed as equal.
	 */
	private static final double THRESHOLD_DOUBLE = 1.0E-5;

	private SphericCoordinate s1;
	private SphericCoordinate s2;
	private SphericCoordinate s3;
	private SphericCoordinate s4;
	private SphericCoordinate s5;
	private SphericCoordinate s6;

	private CartesianCoordinate c1;
	private CartesianCoordinate c2;
	private CartesianCoordinate c3;
	private CartesianCoordinate c4;
	private CartesianCoordinate c5;
	private CartesianCoordinate c6;

	@Before
	public void setUp() {
		s1 = new SphericCoordinate(0.0, 0.0, 0.0);
		s2 = new SphericCoordinate(6000.0, Math.PI / 2, 0);
		s3 = new SphericCoordinate(500.0, Math.PI, Math.PI);
		s4 = new SphericCoordinate(Math.sqrt(3.0), 0.95531661812, Math.PI / 4);
		s5 = new SphericCoordinate(Math.sqrt(12), 2.1862760355, 1.75 * Math.PI);
		s6 = new SphericCoordinate(45234.34, 2.342, 4.42);

		c1 = new CartesianCoordinate(0.0, 0.0, 0.0);
		c2 = new CartesianCoordinate(6000.0, 0.0, 0.0);
		c3 = new CartesianCoordinate(0.0, 0.0, -500.0);
		c4 = new CartesianCoordinate(1.0, 1.0, 1.0);
		c5 = new CartesianCoordinate(2.0, -2.0, -2.0);
		c6 = new CartesianCoordinate(-9349.45648717036547, -31059.628335828437, -31528.2835922019581);
	}

	@Test
	public void testAsCartesianCoordinate() {
		assertTrue(s1.asCartesianCoordinate().isEqual(c1));
		assertTrue(s2.asCartesianCoordinate().isEqual(c2));
		assertTrue(s3.asCartesianCoordinate().isEqual(c3));
		assertTrue(s4.asCartesianCoordinate().isEqual(c4));
		assertTrue(s5.asCartesianCoordinate().isEqual(c5));
		assertTrue(s6.asCartesianCoordinate().isEqual(c6));

		assertTrue(s1.asCartesianCoordinate().asSphericCoordinate().isEqual(s1));
		assertTrue(s2.asCartesianCoordinate().asSphericCoordinate().isEqual(s2));
		assertTrue(s3.asCartesianCoordinate().asSphericCoordinate().isEqual(s3));
		assertTrue(s4.asCartesianCoordinate().asSphericCoordinate().isEqual(s4));
		assertTrue(s5.asCartesianCoordinate().asSphericCoordinate().isEqual(s5));
		assertTrue(s6.asCartesianCoordinate().asSphericCoordinate().isEqual(s6));
	}

	@Test
	public void testGetCartesianDistance() {
		assertEquals(0.0, s1.getCartesianDistance(s1), THRESHOLD_DOUBLE);
		assertEquals(6000.0, s1.getCartesianDistance(s2), THRESHOLD_DOUBLE);
		assertEquals(Math.sqrt(12.0), s1.getCartesianDistance(s5), THRESHOLD_DOUBLE);
		assertEquals(Math.sqrt(19.0), s4.getCartesianDistance(s5), THRESHOLD_DOUBLE);
		assertEquals(6020.797289, s2.getCartesianDistance(s3), THRESHOLD_DOUBLE);
	}

	@Test
	public void testAsSphericCoordinate() {
		assertTrue(s1.asSphericCoordinate() == s1);
		assertTrue(s2.asSphericCoordinate() == s2);
		assertTrue(s3.asSphericCoordinate() == s3);
		assertTrue(s4.asSphericCoordinate() == s4);
		assertTrue(s5.asSphericCoordinate() == s5);
	}

	@Test
	public void testGetCentralAngle() {
		assertEquals(Math.PI / 2, s2.getCentralAngle(s3), THRESHOLD_DOUBLE);
		assertEquals(0.955317, s2.getCentralAngle(s5), THRESHOLD_DOUBLE);
		assertEquals(2.18628, s3.getCentralAngle(s4), THRESHOLD_DOUBLE);
		assertEquals(1.91063, s4.getCentralAngle(s5), THRESHOLD_DOUBLE);
		assertEquals(0.955317, s3.getCentralAngle(s5), THRESHOLD_DOUBLE);
		assertEquals(1.7789863, s2.getCentralAngle(s6), THRESHOLD_DOUBLE);
		assertEquals(2.7342464, s4.getCentralAngle(s6), THRESHOLD_DOUBLE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetCentralAngleForNull() {
		s1.getCentralAngle(null);
	}

	@Test(expected = ArithmeticException.class)
	public void testGetCentralAngleForOrigin() {
		s1.getCentralAngle(s3);
	}

	@Test
	public void testIsEqual() {
		assertFalse(s1.isEqual(s2));
		assertFalse(s3.isEqual(s4));
		assertFalse(s2.isEqual(null));
		assertTrue(s6.isEqual(s6));
		assertTrue(s2.isEqual(c2));
		assertTrue(s4.isEqual(c4));
		assertTrue(s1.isEqual(new SphericCoordinate(0.0, 1.134, 2.75)));
	}

	@Test
	public void testEquals() {
		assertFalse(s1.equals(null));
		assertFalse(s1.equals(s3));
		assertFalse(s3.equals(c4));
		assertFalse(s6.equals(c6));
		assertTrue(s4.equals(s4));
	}


	@Test
	public void testAssertValidRadius() {
		s1.assertValidRadius(0.0);
		s1.assertValidRadius(100.2314);
		assertTrue (true); // pass test case if there was no exception
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertValidRadiusNaN() {
		s1.assertValidRadius(Double.NaN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertValidRadiusInfinite() {
		s1.assertValidRadius(Double.NEGATIVE_INFINITY);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertValidRadiusNegative() {
		s1.assertValidRadius(-1.0);
	}

	@Test
	public void testAssertValidTheta() {
		s1.assertValidTheta(0.0);
		s1.assertValidTheta(Math.PI);
		s1.assertValidTheta(1.4653);
		assertTrue (true); // pass test case if there was no exception
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertValidThetaTooSmall() {
		s1.assertValidTheta(-1.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertValidThetaTooBig() {
		s1.assertValidTheta(Math.PI + 0.0001);
	}

	@Test
	public void testAssertValidPhi() {
		s1.assertValidPhi(0.0);
		s1.assertValidPhi(3.423);
		s1.assertValidPhi(2 * Math.PI - 0.0001);
		assertTrue (true); // pass test case if there was no exception
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertValidPhiTooSmall() {
		s1.assertValidPhi(-0.00134);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertValidPhiTooBig() {
		s1.assertValidPhi(2 * Math.PI);
	}
}
