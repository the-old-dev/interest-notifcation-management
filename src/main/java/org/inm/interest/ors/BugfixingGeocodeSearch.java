package org.inm.interest.ors;

import java.util.HashMap;
import java.util.Map;

import org.inm.interest.Location;

public class BugfixingGeocodeSearch extends LinkedGeocodeSearch {

	private Map<String, String> bugfixes = new HashMap<String, String>();

	BugfixingGeocodeSearch() {

		bugfixes.put("23816", "23816 Groß Niendorf");
		bugfixes.put("45472", "45472 Mülheim an der Ruhr");
		bugfixes.put("14469", "14469 Potsdam");
		bugfixes.put("22946", "22946 Brunsbek");
		bugfixes.put("26487", "26487 Neuschoo");
		bugfixes.put("27777", "27777 Ganderkesee");
		bugfixes.put("45472", "45472 Mülheim an der Ruhr");
		bugfixes.put("46145", "46145 Oberhausen");
		bugfixes.put("48143", "48143 Münster");
		bugfixes.put("51149", "51149 Köln");
		bugfixes.put("66871", "66871 Oberalben");
		bugfixes.put("67245", "67245 Lambsheim");
		bugfixes.put("76703", "76703 Kraichtal");
		bugfixes.put("Darss", "18375 Born am Darß");
		bugfixes.put("Darß", "18375 Born am Darß");
		bugfixes.put("Garmisch", "Garmisch-Partenkirchen");
		bugfixes.put("Kinzigmündung", "Hanau an der Kinzigmündung");
		bugfixes.put("Landkirchen", "Landkirchen, Fehmarn");
		bugfixes.put("Mardorf", "Mardorf, Neustadt am Rübenberge");
		bugfixes.put("Ruhr", "Ruhrgebiet");

	}

	@Override
	protected Location getLocation(String name, String countryCode) {

		// Fix the name
		String fixedName = bugfixes.get(name);
		if (fixedName != null) {
			name = fixedName;
		}

		return getNextService().getLocation(name, countryCode);
	}

}
