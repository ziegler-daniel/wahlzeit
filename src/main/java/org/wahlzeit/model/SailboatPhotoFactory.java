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
import org.wahlzeit.services.LogBuilder;

import java.util.logging.Logger;

/**
 * An Factory for creating SailboatPhotos and related objects.
 */
public class SailboatPhotoFactory extends PhotoFactory {

	private static final Logger log = Logger.getLogger(SailboatPhotoFactory.class.getName());

	/**
	 * @methodtype constructor
	 */
	protected SailboatPhotoFactory() {
		super();
	}

	/**
	 * Hidden singleton instance; needs to be initialized from the outside.
	 */
	public static void initialize() {
		getInstance(); // drops result due to getInstance() side-effects
	}

	/**
	 * Public singleton access method.
	 */
	public static synchronized PhotoFactory getInstance() {
		if (instance == null) {
			log.config(LogBuilder.createSystemMessage().addAction("setting generic SailboatPhotoFactory").toString());
			setInstance(new SailboatPhotoFactory());
		}

		return instance;
	}

	/**
	 * @methodtype factory
	 */
	@Override
	public SailboatPhoto createPhoto() {
		return new SailboatPhoto();
	}

	/**
	 * Creates a new photo with the specified id
	 */
	@Override
	public SailboatPhoto createPhoto(PhotoId id) {
		return new SailboatPhoto(id);
	}

	/**
	 * Loads a photo. The Java object is loaded from the Google Datastore, the Images in all sizes are loaded from the
	 * Google Cloud storage.
	 */
	@Override
	public SailboatPhoto loadPhoto(PhotoId id) {
		// Not implemented
		return null;
	}
}
