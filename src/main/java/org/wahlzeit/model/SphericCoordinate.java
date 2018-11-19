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
 * A SphericCoordinate represents a spheric coordinate in the 3D space.
 * The coordinate is represented with the following triple according to ISO standard 80000-2:2009:
 * <p>
 * Radius (radial distance): Must be greater or equal to 0
 * Theta (polar angle, inclination): Valid values [0, PI]
 * Phi (azimuthal angle, azimuth): Valid values [0, 2PI)
 */
public class SphericCoordinate extends AbstractCoordinate {

	/**
	 * The triple radius, theta and phi represent a spheric coordinate as defined above.
	 */
	private final double radius;
	private final double theta;
	private final double phi;

	/**
	 * @methodtype constructor
	 */
	public SphericCoordinate(double radius, double theta, double phi) {
		assertValidRadius(radius);
		assertValidTheta(theta);
		assertValidPhi(phi);

		this.radius = radius;
		this.theta = theta;
		this.phi = phi;
	}

	@Override
	public CartesianCoordinate asCartesianCoordinate() {
		double x = radius * Math.sin(theta) * Math.cos(phi);
		double y = radius * Math.sin(theta) * Math.sin(phi);
		double z = radius * Math.cos(theta);

		return new CartesianCoordinate(x, y, z);
	}

	@Override
	public SphericCoordinate asSphericCoordinate() {
		return this;
	}

	@Override
	public double getCentralAngle(Coordinate coordinate) {
		if (coordinate == null) {
			throw new IllegalArgumentException("The coordinate must not be null.");
		}

		SphericCoordinate sphericCoordinate = coordinate.asSphericCoordinate();

		if (DoubleUtil.areEqual(radius, 0.0) || DoubleUtil.areEqual(sphericCoordinate.radius, 0.0)) {
			throw new ArithmeticException("The central angle is not defined if one point is the origin.");
		}

		double thetaConverted1 = Math.PI / 2 - theta;
		double thetaConverted2 = Math.PI / 2 - sphericCoordinate.theta;
		double deltaPhi = Math.abs(phi - sphericCoordinate.phi);

		return Math.acos(Math.sin(thetaConverted1) * Math.sin(thetaConverted2)
				+ Math.cos(thetaConverted1) * Math.cos(thetaConverted2) * Math.cos(deltaPhi));
	}

	@Override
	public boolean isEqual(Coordinate coordinate) {
		if (coordinate == null) {
			return false;
		}

		return isEqual(coordinate.asSphericCoordinate());
	}

	/**
	 * @methodtype boolean-query
	 */
	public boolean isEqual(SphericCoordinate coordinate) {
		if (coordinate == null) {
			return false;
		}

		// For the cartesian coordinate (0,0,0) theta and phi can be arbitrary
		if (DoubleUtil.areEqualTo(radius, coordinate.radius, 0.0)) {
			return true;
		}

		// For points on the z-axis phi can be arbitrary
		if (DoubleUtil.areEqualTo(theta, coordinate.theta, 0.0)
				|| DoubleUtil.areEqualTo(theta, coordinate.theta, Math.PI)) {
			return DoubleUtil.areEqual(radius, coordinate.radius);
		}

		return DoubleUtil.areEqual(coordinate.radius, radius)
				&& DoubleUtil.areEqual(coordinate.theta, theta)
				&& DoubleUtil.areEqual(coordinate.phi, phi);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof SphericCoordinate) {
			return isEqual((SphericCoordinate) object);
		}

		return false;
	}

	/**
	 * @methodtype assert
	 */
	protected void assertValidRadius(double radialDistance) {
		if (!Double.isFinite(radialDistance)) {
			throw new IllegalArgumentException("The radius must be finite.");
		}
		if (radialDistance < 0) {
			throw new IllegalArgumentException("The radius must be greater or equal to 0.");
		}
	}

	/**
	 * @methodtype assert
	 */
	protected void assertValidTheta(double angle) {
		if (!Double.isFinite(angle)) {
			throw new IllegalArgumentException("Theta (the polar angle) must be finite.");
		}
		if (angle < 0 || angle > Math.PI) {
			throw new IllegalArgumentException("Theta (the polar angle) must be in the range [0,PI]. " + angle);
		}
	}

	/**
	 * @methodtype assert
	 */
	protected void assertValidPhi(double angle) {
		if (!Double.isFinite(angle)) {
			throw new IllegalArgumentException("Phi (the azimuthal angle) must be finite (radian measure).");
		}
		if (angle < 0 || angle >= 2 * Math.PI) {
			throw new IllegalArgumentException("Phi (the azimuthal angle) must be in the range [0, 2*PI) (radian measure). " + angle);
		}
	}

	/**
	 * @methodtype get
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * @methodtype get
	 */
	public double getTheta() {
		return theta;
	}

	/**
	 * @methodtype get
	 */
	public double getPhi() {
		return phi;
	}
}
