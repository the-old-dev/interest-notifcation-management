package org.inm.interest;

public class LocationFixer {

	public static void main(String[] args) {
		
		LocationStore store = new LocationStore(false);
		OpenRouteService service = new OpenRouteService(store, args[0]);
		
		for (Location unlocated : store.findUnlocated()) {
			System.out.println(unlocated);
		}
		
		// store.insert(new Location("Garmisch-München",null, null));
		
		Location unlocated = store.findByIdField("St.Peter-Ording");
		System.out.println(unlocated);
		store.delete(unlocated);
		Location relocated = service.getLocation(unlocated.getName());
		
		if (!store.exists(unlocated)) {
			store.insert(unlocated);
		}
		
		System.out.println(relocated+"\n");
		
	}

}
