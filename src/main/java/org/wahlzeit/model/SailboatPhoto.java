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

import com.googlecode.objectify.annotation.Subclass;

/**
 * A photo represents a user-provided (uploaded) photo of a sailboat.
 */
@Subclass(index = true)
public class SailboatPhoto extends Photo {

	/**
	 * The name of the sailboat.
	 */
	private String sailboatName;

	/**
	 * @methodtype constructor
	 */
	public SailboatPhoto() {
		super();
	}

	/**
	 * @methodtype constructor
	 */
	public SailboatPhoto(String sailboatName) {
		super();
		this.sailboatName = sailboatName;

	}

	/**
	 * @methodtype constructor
	 */
	public SailboatPhoto(PhotoId myId) {
		super(myId);

	}

	/**
	 * @methodtype constructor
	 */
	public SailboatPhoto(PhotoId myId, String sailboatName) {
		super(myId);
		this.sailboatName = sailboatName;
	}

	/**
	 * @methodtype get
	 */
	public String getSailboatName() {
		return sailboatName;
	}

	/**
	 * @methodtype set
	 */
	public void setSailboatName(String sailboatName) {
		this.sailboatName = sailboatName;
	}

}
