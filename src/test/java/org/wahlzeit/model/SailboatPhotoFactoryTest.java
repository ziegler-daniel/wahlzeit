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

import static org.junit.Assert.assertNotNull;

/**
 * Test for the {@link SailboatPhotoFactory} class.
 */
public class SailboatPhotoFactoryTest {

	@ClassRule
	public static RuleChain ruleChain = RuleChain.
			outerRule(new LocalDatastoreServiceTestConfigProvider()).
			around(new RegisteredOfyEnvironmentProvider());

	@Before
	public void setUp(){
		// Reset to a defined state as the (Sailboat)PhotoFactory could already be initialized by another tests
		SailboatPhotoFactory.instance = null;
		SailboatPhotoFactory.initialize();
	}

	@Test
	public void testGetInstance() {
		PhotoFactory photoFactory1 = SailboatPhotoFactory.getInstance();
		PhotoFactory photoFactory2 = SailboatPhotoFactory.getInstance();
		assertNotNull(photoFactory1);
		assert(photoFactory1 == photoFactory2);
	}

	@Test(expected = IllegalStateException.class)
	public void testSetInstance(){
		PhotoFactory.setInstance(new SailboatPhotoFactory());
	}

	@Test
	public void testCreatePhoto(){
		PhotoId photoId = new PhotoId(0x108);
		Photo photo = PhotoFactory.getInstance().createPhoto(photoId);
		assertNotNull(photo);
		assert(photo instanceof SailboatPhoto);
		assert(photo.getId().equals(photoId));
	}
}
