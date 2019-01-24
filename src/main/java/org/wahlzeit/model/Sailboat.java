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

import com.google.appengine.api.datastore.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;
import org.wahlzeit.services.DataObject;
import org.wahlzeit.services.ObjectManager;
import org.wahlzeit.utils.StringUtil;

import java.util.Objects;

/**
 * Class to represent a real sailboat.
 */
@Entity
public class Sailboat extends DataObject {
	private SailboatType type;

	private String name;
	private double length;

	@Id
	long id;

	@Parent
	Key parent = ObjectManager.applicationRootKey;

	/**
	 * Default constructor is required by the google datastore.
	 */
	protected Sailboat() {

	}

	public Sailboat(SailboatType type, String name, double length) {
		if (type == null) {
			throw new IllegalArgumentException("The SailboatType must be not null.");
		}
		assertNameValid(name);
		assertLenghtValid(length);

		this.type = type;
		this.name = name;
		this.length = length;

		updateObject();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Sailboat sailboat = (Sailboat) o;
		return type.equals(sailboat.type) && name.equals(sailboat.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, name);
	}

	/**
	 * Updates this object in order to persist it in the datastore.
	 *
	 * @methodtype command
	 */
	private void updateObject() {
		id = hashCode();
		incWriteCount();
	}

	/**
	 * @methodtype get
	 */
	public double getLength() {
		return length;
	}

	/**
	 * @methodtype set
	 */
	public String getName() {
		return name;
	}

	/**
	 * @methodtype get
	 */
	public SailboatType getType() {
		return type;
	}

	/**
	 * @methodtype set
	 */
	public void setLength(double length) {
		assertLenghtValid(length);
		this.length = length;
		updateObject();
	}

	/**
	 * @methodtype set
	 */
	public void setName(String name) {
		assertNameValid(name);
		this.name = name;
		updateObject();
	}

	/**
	 * @methodtype set
	 */
	public void setType(SailboatType type) {
		if (type == null) {
			throw new IllegalArgumentException("The SailboatType must be not null.");
		}
		this.type = type;
		updateObject();
	}

	/**
	 * @methodtype assert
	 */
	private void assertNameValid(String name) {
		if (StringUtil.isNullOrEmptyString(name)) {
			throw new IllegalArgumentException("The name must be not empty.");
		}
	}

	/**
	 * @methodtype assert
	 */
	private void assertLenghtValid(double length) {
		if (!Double.isFinite(length) || length <= 0) {
			throw new IllegalArgumentException("The length must be a postive value.");
		}
	}
}
