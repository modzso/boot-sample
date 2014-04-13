package org.core.boot.sample;

import java.util.HashMap;
import java.util.Map;

public class LocationStore {

	private Map<String, String> locations = new HashMap<String, String>();

	public String relocate(String name, String location) {
		String old = this.locations.put(name, location);
		return old;
	}

	public String getLocation(String name) {
		return this.locations.get(name);
	}

	public Map<String, String> getAllLocations() {
		return this.locations;
	}
}
