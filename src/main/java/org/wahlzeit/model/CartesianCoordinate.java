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
import org.wahlzeit.utils.ValueObjectManager;

/**
 * A CartesianCoordinate represents a cartesian coordinate in the 3D.
 * <p>
 * This class is implemented according to the pattern of shared value objects.
 */
public class CartesianCoordinate extends AbstractCoordinate {

	private static final ValueObjectManager<CartesianCoordinate> valueObjectManager = new ValueObjectManager<>();

	/**
	 * x, y, z values representing the cartesian coordinate
	 */
	private final double x;
	private final double y;
	private final double z;

	/**
	 * @methodtype constructor
	 */
	private CartesianCoordinate(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;

		assertClassInvariants();
	}

	/**
	 * @param x The x-coordinate. Must be a finite double value.
	 * @param y The y-coordinate. Must be a finite double value.
	 * @param z The z-coordinate. Must be a finite double value.
	 * @return The value object representing the coordinate.
	 * @methodtype factory
	 */
	public static CartesianCoordinate getInstance(double x, double y, double z) {
		assertCoordinateValueValid(x);
		assertCoordinateValueValid(y);
		assertCoordinateValueValid(z);

		int key = DoubleUtil.computeHashCodeWithPrecision(x, y, z);

		return valueObjectManager.getValueObject(key, () -> new CartesianCoordinate(x, y, z));
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

		double distance = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY) + (deltaZ * deltaZ));

		assert Double.isFinite(distance);
		assert distance >= 0.0;
		return distance;
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

		return SphericCoordinate.getInstance(radius, theta, phi);
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
		return this == coordinate;
	}

	/**
	 * @methodtype boolean-query
	 */
	public boolean equals(Object object) {
		return this == object;
	}

	@Override
	public int hashCode() {
		return DoubleUtil.computeHashCodeWithPrecision(x, y, z);
	}

	@Override
	public CartesianCoordinate clone(){
		return this;
	}

	/**
	 * @methodtype assert
	 */
	protected static void assertCoordinateValueValid(double value) {
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("The coordiante values must be finite.");
		}
	}

	/**
	 * @methodtype assert
	 */
	protected void assertClassInvariants() {
		assert Double.isFinite(x);
		assert Double.isFinite(y);
		assert Double.isFinite(z);
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
