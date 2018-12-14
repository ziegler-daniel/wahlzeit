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

import java.util.Objects;

/**
 * A SphericCoordinate represents a spheric coordinate in the 3D space.
 * The coordinate is represented with the following triple according to ISO standard 80000-2:2009:
 * <p>
 * Radius (radial distance): Must be greater or equal to 0
 * Theta (polar angle, inclination): Valid values [0, PI]
 * Phi (azimuthal angle, azimuth): Valid values [0, 2PI)
 * <p>
 * The angles for theta and phi are normalized. So theta and phi are 0.0 if the radius is zero.
 * And phi is set to zero if theta is 0.0 or PI.
 * <p>
 * This class is implemented according to the pattern of shared value objects.
 */
public class SphericCoordinate extends AbstractCoordinate {

	private static final ValueObjectManager<SphericCoordinate> valueObjectManager = new ValueObjectManager<>();

	/**
	 * The triple radius, theta and phi represent a spheric coordinate as defined above.
	 */
	private final double radius;
	private final double theta;
	private final double phi;

	/**
	 * @param radius must be greater or equal to 0
	 * @param theta  (polar angle) valid values [0, PI]. Must be normalized.
	 * @param phi    (azimuthal angle) valid values [0, 2PI). Must be normalized.
	 * @methodtype constructor
	 */
	private SphericCoordinate(double radius, double theta, double phi) {
		this.radius = radius;
		this.theta = theta;
		this.phi = phi;

		assertClassInvariants();
	}

	/**
	 * @param radius must be greater or equal to 0
	 * @param theta  (polar angle) valid values [0, PI]
	 * @param phi    (azimuthal angle) valid values [0, 2PI)
	 * @methodtype factory
	 */
	public static SphericCoordinate getInstance(double radius, double theta, double phi) {
		assertValidRadius(radius);
		assertValidTheta(theta);
		assertValidPhi(phi);

		double thetaNormalized = normalizeTheata(theta, radius);
		double phiNormalized = normalizePhi(phi, radius, theta);

		int key = DoubleUtil.computeHashCodeWithPrecision(radius, thetaNormalized, phiNormalized);
		return valueObjectManager.getValueObject(key, () -> new SphericCoordinate(radius, thetaNormalized, phiNormalized));
	}

	@Override
	public CartesianCoordinate asCartesianCoordinate() {
		double x = radius * Math.sin(theta) * Math.cos(phi);
		double y = radius * Math.sin(theta) * Math.sin(phi);
		double z = radius * Math.cos(theta);

		return CartesianCoordinate.getInstance(x, y, z);
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

		double centralAngle = Math.acos(Math.sin(thetaConverted1) * Math.sin(thetaConverted2)
				+ Math.cos(thetaConverted1) * Math.cos(thetaConverted2) * Math.cos(deltaPhi));

		assert Double.isFinite(centralAngle);
		assert centralAngle >= 0.0;
		return centralAngle;
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
		return this == coordinate;
	}

	@Override
	public boolean equals(Object object) {
		return this == object;
	}

	@Override
	public int hashCode() {
		return Objects.hash(radius, theta, phi);
	}

	@Override
	public SphericCoordinate clone() {
		return this;
	}

	/**
	 * @methodtype conversion
	 */
	protected static double normalizeTheata(double theta, double radius) {
		if (DoubleUtil.areEqual(radius, 0.0)) {
			return 0.0;
		}
		return theta;
	}

	/**
	 * @methodtype conversion
	 */
	protected static double normalizePhi(double phi, double radius, double theta) {
		if (DoubleUtil.areEqual(radius, 0.0)) {
			return 0.0;
		}

		if (DoubleUtil.areEqual(theta, 0.0) || DoubleUtil.areEqual(theta, Math.PI)) {
			return 0.0;
		}
		return phi;
	}

	/**
	 * @methodtype assert
	 */
	protected static void assertValidRadius(double radialDistance) {
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
	protected static void assertValidTheta(double angle) {
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
	protected static void assertValidPhi(double angle) {
		if (!Double.isFinite(angle)) {
			throw new IllegalArgumentException("Phi (the azimuthal angle) must be finite (radian measure).");
		}
		if (angle < 0 || angle >= 2 * Math.PI) {
			throw new IllegalArgumentException("Phi (the azimuthal angle) must be in the range [0, 2*PI) (radian measure). " + angle);
		}
	}

	/**
	 * @methodtype assert
	 */
	protected void assertClassInvariants() {
		assert Double.isFinite(radius) && radius >= 0.0;
		assert Double.isFinite(theta) && theta >= 0.0 && theta <= Math.PI;
		assert Double.isFinite(phi) && phi >= 0.0 && phi < 2 * Math.PI;

		if (DoubleUtil.areEqual(radius, 0.0)) {
			assert DoubleUtil.areEqual(theta, 0.0);
			assert DoubleUtil.areEqual(phi, 0.0);
		}

		if (DoubleUtil.areEqual(theta, 0.0) || DoubleUtil.areEqual(theta, Math.PI)) {
			assert DoubleUtil.areEqual(phi, 0.0);
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
