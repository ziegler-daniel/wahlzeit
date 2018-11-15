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

/**
 * A set of utility functions for working with doubles. Especially for comparing them.
 */
public class DoubleUtil {

	/**
	 * If the difference between two double values is below this threshold they are assumed as equal.
	 */
	private static final double THRESHOLD_DOUBLE = 1.0E-5;

	public static boolean areEqual(double a, double b) {
		return areEqual(a, b, THRESHOLD_DOUBLE);
	}

	public static boolean areEqual(double a, double b, double threshold) {
		return Math.abs(a - b) < threshold;
	}

	public static boolean areEqualTo(double a, double b, double expected) {
		return areEqualTo(a, b, expected, THRESHOLD_DOUBLE);
	}

	public static boolean areEqualTo(double a, double b, double expected, double threshold) {
		return areEqual(a, expected, threshold) && areEqual(b, expected, threshold);
	}
}
