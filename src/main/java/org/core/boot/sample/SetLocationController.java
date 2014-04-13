package org.core.boot.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SetLocationController {

	@Autowired
	private LocationStore locationStore;

	@Autowired
	private CounterService counter;

	@RequestMapping("/set")
	public String setLocation(@RequestParam("name") String name, @RequestParam("location") String location) {
		String oldLocation = locationStore.relocate(name, location);
		counter.increment("location");
		return String.format("%s relocated from %s to: %s", name, oldLocation, location);
	}

	@RequestMapping("/get")
	public String getLocation(@RequestParam("name") String name) {
		String location = locationStore.getLocation(name);
		counter.increment("setLocation");
		return String.format("%s location is: %s", name, location);
	}

	@RequestMapping("/showall")
	public String showall() {
		counter.increment("showAll");
		StringBuilder sb = new StringBuilder("<ul>");
		for (String name : locationStore.getAllLocations().keySet()) {
			String location = locationStore.getLocation(name);
			sb.append(String.format("<li>%s location is %s</li>", name, location));
		}
		sb.append("</ul>");
		return sb.toString();
	}

	@RequestMapping("/names")
	public String names() {
		StringBuilder sb = new StringBuilder("<ul>");
		for (String name : locationStore.getAllLocations().keySet()) {
			String location = String.format("/get?name=%s", name);
			sb.append(String.format("<li><a href=\"%s\">%s</a></li>", location, name));
		}
		sb.append("</ul>");
		return sb.toString();
	}
}
