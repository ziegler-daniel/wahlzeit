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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * TestSuite to run all test cases of the model package.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(value = {
		org.wahlzeit.model.persistence.PersistanceTestSuite.class,
		org.wahlzeit.model.AccessRightsTest.class,
		org.wahlzeit.model.CoordinateTest.class,
		org.wahlzeit.model.FlagReasonTest.class,
		org.wahlzeit.model.GenderTest.class,
		org.wahlzeit.model.GuestTest.class,
		org.wahlzeit.model.LocationTest.class,
		org.wahlzeit.model.PhotoFilterTest.class,
		org.wahlzeit.model.SailboatPhotoFactoryTest.class,
		org.wahlzeit.model.SailboatPhotoManagerTest.class,
		org.wahlzeit.model.SailboatPhotoTest.class,
		org.wahlzeit.model.TagsTest.class,
		org.wahlzeit.model.UserStatusTest.class,
		org.wahlzeit.model.ValueTest.class
})
public class ModelTestSuite {
	/*
	 * Do nothing.
	 */
}