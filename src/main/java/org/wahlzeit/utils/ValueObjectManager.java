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

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * This class manages the instances of a class that implements the pattern of shared value objects.
 *
 * @param <T> The value object class.
 */
public class ValueObjectManager<T> {

	private HashMap<String, T> valueObjects;

	public ValueObjectManager() {
		valueObjects = new HashMap<>();
	}

	/**
	 * Returns the instance of the value object for the given key. This method is thread-safe.
	 *
	 * @param key      The key of the instance that should be retrieved.
	 * @param supplier A function to create the corresponding instance of the value object class T.
	 * @methodtype get
	 */
	public T getValueObject(String key, Supplier<T> supplier) {
		T object = valueObjects.get(key);
		if (object == null) {
			synchronized (this) {
				object = valueObjects.get(key);
				if (object == null) {
					object = supplier.get();
					valueObjects.put(key, object);
				}
			}
		}
		return object;
	}
}
