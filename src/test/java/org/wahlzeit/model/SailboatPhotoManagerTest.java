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

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.wahlzeit.testEnvironmentProvider.LocalDatastoreServiceTestConfigProvider;
import org.wahlzeit.testEnvironmentProvider.RegisteredOfyEnvironmentProvider;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test for the {@link SailboatPhotoManager} class.
 */
public class SailboatPhotoManagerTest {

	@ClassRule
	public static RuleChain ruleChain = RuleChain.
			outerRule(new LocalDatastoreServiceTestConfigProvider()).
			around(new RegisteredOfyEnvironmentProvider());

	@Before
	public void setUp() {
		// Reset to a defined state as the (Sailboat)PhotoFactory could already be initialized by another tests
		SailboatPhotoFactory.instance = null;
		SailboatPhotoFactory.initialize();
		SailboatPhotoManager.getInstance().init();

	}

	@Test
	public void testGetInstance() {
		PhotoManager sailboatPhotoManager1 = SailboatPhotoManager.getInstance();
		PhotoManager sailboatPhotoManager2 = SailboatPhotoManager.getInstance();

		assertNotNull(sailboatPhotoManager1);
		assertTrue(sailboatPhotoManager1 == sailboatPhotoManager2);
		assertTrue(sailboatPhotoManager1 instanceof SailboatPhotoManager);
	}

	@Test
	public void testAddAndLoadSailboatPhoto() {
		PhotoManager photoManager = PhotoManager.getInstance();

		//store the photo
		PhotoId photoId = PhotoId.getNextId();
		Photo photo = PhotoFactory.getInstance().createPhoto(photoId);
		assertTrue(photo instanceof SailboatPhoto);

		try {
			photoManager.addPhoto(photo);
		} catch (Exception e) {
			fail();
		}

		assertTrue(photoManager.hasPhoto(photoId));
		assertTrue(photoManager.getPhoto(photoId) instanceof SailboatPhoto);
		assertEquals(photo, photoManager.getPhoto(photoId));
	}
}
