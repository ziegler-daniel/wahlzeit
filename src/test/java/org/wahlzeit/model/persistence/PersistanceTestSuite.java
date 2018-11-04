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

package org.wahlzeit.model.persistence;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * TestSuite to run all test cases of the model.persistance package.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(value = {
		org.wahlzeit.model.persistence.DatastoreAdapterTest.class
})
public class PersistanceTestSuite {
	/*
	 * Do nothing.
	 */
}
