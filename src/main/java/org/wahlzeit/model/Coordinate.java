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
 * A Coordinate represents a coordinate in the 3D space.
 */
public interface Coordinate {

	/**
	 * @methodtype conversion
	 */
	CartesianCoordinate asCartesianCoordinate();

	/**
	 * @param coordinate the coordinate to compute the distance to. Must not be null.
	 * @return the cartesian distance (return values >= 0.0).
	 * @throws IllegalArgumentException if the argument is not valid (as defined above).
	 * @methodtype get
	 */
	double getCartesianDistance(Coordinate coordinate);

	/**
	 * @methodtype conversion
	 */
	SphericCoordinate asSphericCoordinate();

	/**
	 * @param coordinate the coordinate to compute the central angle to. Must not be null.
	 *                   Both coordinates must not be the origin (0,0,0).
	 * @return the central angle (in radians, return values >= 0.0).
	 * @throws IllegalArgumentException if the argument is not valid (as defined above).
	 * @throws ArithmeticException      if one of the coordinates is the origin (0,0,0).
	 * @methodtype get
	 */
	double getCentralAngle(Coordinate coordinate);

	/**
	 * @param coordinate the coordinate to compare this coordinate to. Must not be null.
	 * @throws IllegalArgumentException if the argument is not valid (as defined above).
	 * @methodtype boolean-query
	 */
	boolean isEqual(Coordinate coordinate);

}
