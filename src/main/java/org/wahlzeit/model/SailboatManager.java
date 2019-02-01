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

import com.googlecode.objectify.ObjectifyService;
import org.wahlzeit.services.LogBuilder;
import org.wahlzeit.services.ObjectManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;

/**
 * Class to manage the sailboats.
 *
 * Bind the manager role in the manager-element-collaboration of {@link SailboatManager} and {@link Sailboat}.
 */
public class SailboatManager extends ObjectManager {

	private static SailboatManager instance = new SailboatManager();
	private static final Logger log = Logger.getLogger(SailboatManager.class.getName());

	private HashMap<String, SailboatType> sailboatTypes;
	private HashSet<Sailboat> sailboats;

	private SailboatManager() {
		sailboatTypes = new HashMap<>();
		sailboats = new HashSet<>();
	}

	public static SailboatManager getInstance() {
		return instance;
	}

	public void addSailboatType(String typename) {
		if (typename == null) {
			throw new IllegalArgumentException("The typename must not be null.");
		}
		if (sailboatTypes.containsKey(typename)) {
			return;
		}

		SailboatType type = new SailboatType(typename);
		sailboatTypes.put(typename, type);
		writeObject(type);
	}

	public SailboatType getSailboatType(String typename) {
		if (typename == null) {
			throw new IllegalArgumentException("The typename must not be null.");
		}

		if (!sailboatTypes.containsKey(typename)) {
			addSailboatType(typename);
		}

		return sailboatTypes.get(typename);
	}

	/**
	 * @methodtype factory
	 */
	public Sailboat createSailboat(String typename, String sailboatName, double length) {
		SailboatType type = getSailboatType(typename);
		return createSailboat(type, sailboatName, length);
	}

	/**
	 * @methodtype factory
	 */
	public Sailboat createSailboat(SailboatType type, String sailboatName, double length){
		Sailboat sailboat = type.createInstance(sailboatName, length);
		addSailboat(sailboat);
		return sailboat;
	}

	public void addSailboat(Sailboat sailboat) {
		if (sailboat == null) {
			throw new IllegalArgumentException("The sailboat must not be null.");
		}

		sailboats.add(sailboat);
		writeObject(sailboat);
	}

	/**
	 * @methodtype boolean-query
	 */
	public boolean hasSailboatType(String typename) {
		if (typename == null) {
			throw new IllegalArgumentException("The typename must not be null.");
		}
		return sailboatTypes.containsKey(typename);
	}

	/**
	 * @methodtype init Loads all SailboatTypes and Sailboats from the Datastore and holds them in the cache
	 */
	public void init() {
		loadSailboatTypes();
		loadSailboats();
	}

	/**
	 * @methodtype command
	 * <p>
	 * Load all persisted SailbaotTypes. Executed when Wahlzeit is restarted.
	 */
	public void loadSailboatTypes() {
		Collection<SailboatType> existingSailboatTypes = ObjectifyService.run(() -> {
			Collection<SailboatType> existingTypes = new ArrayList<SailboatType>();
			readObjects(existingTypes, SailboatType.class);
			return existingTypes;
		});

		for (SailboatType type : existingSailboatTypes) {
			if (!sailboatTypes.containsKey(type.getName())) {
				log.config(LogBuilder.createSystemMessage().
						addParameter("Load SailboatType with name", type.getName()).toString());
			} else {
				log.config(LogBuilder.createSystemMessage().
						addParameter("Already loaded SailboatType with name", type.getName()).toString());
			}
		}

		log.info(LogBuilder.createSystemMessage().addMessage("All SailboatTypes loaded.").toString());
	}

	/**
	 * @methodtype command
	 * <p>
	 * Load all persisted Sailbaots. Executed when Wahlzeit is restarted.
	 */
	public void loadSailboats() {
		Collection<Sailboat> existingSailboats = ObjectifyService.run(() -> {
			Collection<Sailboat> sailboats = new ArrayList<Sailboat>();
			readObjects(sailboats, Sailboat.class);
			return sailboats;
		});

		for (Sailboat sailboat : existingSailboats) {
			if (!sailboats.contains(sailboat)) {
				log.config(LogBuilder.createSystemMessage().
						addParameter("Load Sailboat with ID", sailboat.hashCode()).toString());
			} else {
				log.config(LogBuilder.createSystemMessage().
						addParameter("Already loaded Sailboat with ID", sailboat.hashCode()).toString());
			}
		}

		log.info(LogBuilder.createSystemMessage().addMessage("All Sailboats loaded.").toString());
	}

	/**
	 * @methodtype command
	 */
	public void saveAll() {
		saveSailboatTypes();
		saveSailboats();
	}

	/**
	 * @methodtype command
	 */
	public void saveSailboatTypes() {
		updateObjects(sailboatTypes.values());
	}

	/**
	 * @methodtype command
	 */
	public void saveSailboats() {
		updateObjects(sailboats);
	}

}
