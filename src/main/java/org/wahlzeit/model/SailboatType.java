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
 * Class to represent the type of a sailboat.
 *
 * Binds the type-object role in the type-object-collaboration of {@link SailboatType} and {@link Sailboat}.
 */
@Entity
public class SailboatType extends DataObject {

	@Id
	private String name;

	@Parent
	Key parent = ObjectManager.applicationRootKey;


	/**
	 * Default constructor is required by the google datastore.
	 */
	protected SailboatType() {

	}

	public SailboatType(String name) {
		if (StringUtil.isNullOrEmptyString(name)) {
			throw new IllegalArgumentException("The name of the type must be not null.");
		}
		this.name = name;

		incWriteCount();
	}

	/**
	 * @methodtype factory
	 */
	public Sailboat createInstance(String sailboatName, double length) {
		return new Sailboat(this, sailboatName, length);
	}

	/**
	 * @methodtype get
	 */
	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		SailboatType that = (SailboatType) o;
		return name.equals(that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	/**
	 * As we use flat type hierarchy for sailboat types this returns always false.
	 * @methodtype get
	 */
	public boolean isSubtype(){
		return false;
	}
}
