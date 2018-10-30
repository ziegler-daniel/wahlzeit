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

/**
 * A coordiante represents a cartesian coordinate in the 3D.
 */
public class Coordinate {
	/**
	 * If the difference between two double values is below this threshold they are assumed as equal.
	 */
	private static final double THRESHOLD_DOUBLE = 10E-5;

	/**
	 * x, y, z values representing the cartesian coordinate
	 */
	private final double x;
	private final double y;
	private final double z;

	/**
	 * @methodtype constructor
	 */
	public Coordinate(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 *
	 */
	public double getDistance(Coordinate coordinate) {
		if (coordinate == null) {
			throw new IllegalArgumentException("The coordinate must not be null.");
		}

		double distX = coordinate.x - x;
		double distY = coordinate.y - y;
		double distZ = coordinate.z - z;

		return Math.sqrt((distX * distX) + (distY * distY) + (distZ * distZ));
	}

	/**
	 * @methodtype boolean-query
	 */
	public boolean isEqual(Coordinate coordinate) {
		if (coordinate == null) {
			return false;
		}

		return (Math.abs(coordinate.x - x) < THRESHOLD_DOUBLE)
				&& (Math.abs(coordinate.y - y) < THRESHOLD_DOUBLE)
				&& Math.abs(coordinate.z - z) < THRESHOLD_DOUBLE;
	}

	/**
	 * @methodtype boolean-query
	 */
	public boolean equals(Object object) {
		if (object instanceof Coordinate) {
			return isEqual((Coordinate) object);
		}

		return false;
	}

	/**
	 * @methodtype get
	 */
	public double getX() {
		return x;
	}

	/**
	 * @methodtype get
	 */
	public double getY() {
		return y;
	}

	/**
	 * @methodtype get
	 */
	public double getZ() {
		return z;
	}
}
