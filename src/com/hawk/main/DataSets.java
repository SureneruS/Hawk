package com.hawk.main;

import java.util.ArrayList;
import java.util.List;

public class DataSets {
	private static List<String> IDs;
	
	static {
		IDs = new ArrayList<String>();
		IDs.add("bike");
		IDs.add("car");
//		IDs.add("airplane");
//		IDs.add("human");
	}

	public static List<String> getIDs() {
		return IDs;
	}
	
}
