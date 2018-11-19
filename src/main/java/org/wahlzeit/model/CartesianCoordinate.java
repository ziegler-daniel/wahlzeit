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

import org.wahlzeit.utils.DoubleUtil;

/**
 * A CartesianCoordinate represents a cartesian coordinate in the 3D.
 */
public class CartesianCoordinate extends AbstractCoordinate {

	/**
	 * x, y, z values representing the cartesian coordinate
	 */
	private final double x;
	private final double y;
	private final double z;

	/**
	 * @methodtype constructor
	 */
	public CartesianCoordinate(double x, double y, double z) {
		assertCoordinateValueValid(x);
		assertCoordinateValueValid(y);
		assertCoordinateValueValid(z);

		this.x = x;
		this.y = y;
		this.z = z;
	}


	@Override
	public CartesianCoordinate asCartesianCoordinate() {
		return this;
	}

	@Override
	public double getCartesianDistance(Coordinate coordinate) {
		if (coordinate == null) {
			throw new IllegalArgumentException("The coordinate must not be null.");
		}

		CartesianCoordinate cartesianCoordinate = coordinate.asCartesianCoordinate();

		double deltaX = cartesianCoordinate.x - x;
		double deltaY = cartesianCoordinate.y - y;
		double deltaZ = cartesianCoordinate.z - z;

		return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY) + (deltaZ * deltaZ));
	}

	@Override
	public SphericCoordinate asSphericCoordinate() {
		double radius = Math.sqrt(x * x + y * y + z * z);
		double theta = 0.0;
		double phi = 0.0;

		if (radius != 0.0) {
			theta = Math.acos(z / radius);
		}

		if (x != 0.0) {
			phi = Math.atan2(y, x);

			if (phi < 0) {
				phi = 2 * Math.PI + phi;
			}
		}

		return new SphericCoordinate(radius, theta, phi);
	}

	@Override
	public boolean isEqual(Coordinate coordinate) {
		if (coordinate == null) {
			return false;
		}

		return isEqual(coordinate.asCartesianCoordinate());
	}

	/**
	 * @methodtype boolean-query
	 */
	public boolean isEqual(CartesianCoordinate coordinate) {
		if (coordinate == null) {
			return false;
		}

		return DoubleUtil.areEqual(coordinate.x, x)
				&& DoubleUtil.areEqual(coordinate.y, y)
				&& DoubleUtil.areEqual(coordinate.z, z);
	}

	/**
	 * @methodtype boolean-query
	 */
	public boolean equals(Object object) {
		if (object instanceof CartesianCoordinate) {
			return isEqual((CartesianCoordinate) object);
		}

		return false;
	}

	/**
	 * @methodtype assert
	 */
	protected void assertCoordinateValueValid(double value) {
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("The coordiante values must be finite.");
		}
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
