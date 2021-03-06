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
 *
 * Binds the client role in the client-service-collaboration of {@link SailboatPhoto} and {@link Sailboat}.
 */
@Subclass(index = true)
public class SailboatPhoto extends Photo {

	/**
	 * The sailboat that is shown on the photo.
	 */
	private Sailboat sailboat;

	/**
	 * @methodtype constructor
	 */
	public SailboatPhoto() {
		super();
	}

	/**
	 * @methodtype constructor
	 */
	public SailboatPhoto(Sailboat sailboat) {
		super();
		this.sailboat = sailboat;

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
	public SailboatPhoto(PhotoId myId, Sailboat sailboat) {
		super(myId);
		this.sailboat = sailboat;
	}

	/**
	 * @methodtype get
	 */
	public Sailboat getSailboat() {
		return sailboat;
	}

	/**
	 * @methodtype set
	 */
	public void setSailboat(Sailboat sailboat) {
		this.sailboat = sailboat;
	}

}
