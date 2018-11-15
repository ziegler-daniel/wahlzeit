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

package org.wahlzeit.utils;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for the {@link DoubleUtil} class.
 */
public class DoubleUtilTest {

	@Test
	public void testAreEqual(){
		assertTrue(DoubleUtil.areEqual(0.0, 0.0));
		assertTrue(DoubleUtil.areEqual(-99.9999999, -100));
		assertTrue(DoubleUtil.areEqual(1.00012, 1.000129));
		assertFalse(DoubleUtil.areEqual(2.00022, 2.00023));
		assertFalse(DoubleUtil.areEqual(12345.97800, 12345.9780123));

		assertTrue(DoubleUtil.areEqual(10, 10.9999, 1.0));
		assertTrue(DoubleUtil.areEqual(210.978333333, 210.978333334, 1E-7));
		assertFalse(DoubleUtil.areEqual(100, 99.9999999, 1.0E-10));
	}

	@Test
	public void testAreEqualTo(){
		assertTrue(DoubleUtil.areEqualTo(0.0, 0.0, 0.0));
		assertTrue(DoubleUtil.areEqualTo(99.999999, 99.9999996, 100.0));
		assertFalse(DoubleUtil.areEqualTo(1.0, 1.0001, 1.0));

		assertTrue(DoubleUtil.areEqualTo(1.0, 10.0, 5.0, 6.0));
		assertFalse(DoubleUtil.areEqualTo(0.99999, 1.0001, 1.0, 2.0E-7));
	}
}
