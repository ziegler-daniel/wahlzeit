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

package org.wahlzeit.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

/**
 * Test cases for the {@link ValueObjectManager} class.
 */
public class ValueObjectManagerTest {

	@Test
	public void testGetValueObjectReturnsSameObject() {
		List<String> callLog = new ArrayList<>();
		ValueObjectManager<TestItem> manager = new ValueObjectManager<>();

		TestItem item1 = getObjectFromManager("1", manager, callLog);
		TestItem item2 = getObjectFromManager("2", manager, callLog);

		assertNotNull(item1);
		assertNotNull(item2);
		assertTrue(item1 != item2);
		assertTrue(item1 == getObjectFromManager("1", manager, callLog));
		assertTrue(item2 == getObjectFromManager("2", manager, callLog));
	}

	@Test
	public void testGetValueObjectCallsConstructorOnlyOnce() {
		List<String> callLog = new ArrayList<>();
		ValueObjectManager<TestItem> manager = new ValueObjectManager<>();

		getObjectFromManager("1", manager, callLog);
		getObjectFromManager("1", manager, callLog);
		getObjectFromManager("1", manager, callLog);
		getObjectFromManager("2", manager, callLog);
		getObjectFromManager("2", manager, callLog);
		getObjectFromManager("21", manager, callLog);

		assertEquals(Arrays.asList("1", "2", "21"), callLog);
	}

	private TestItem getObjectFromManager(String key, ValueObjectManager<TestItem> manager, List<String> callLog) {
		return manager.getValueObject(key, () -> new TestItem(key, callLog));
	}
}

class TestItem {

	public TestItem(String key,  List<String> callLog) {
		callLog.add(key);
	}
}