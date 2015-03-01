package com.hawk.main;

import java.util.List;

import org.opencv.core.Core;

import com.hawk.GA.EcoFeature;
import com.hawk.helper.DeepCopy;
import com.hawk.helper.ManageFeatures;

public class Test {
	private static String home = System.getProperty("user.home");
	
	static void loadFeaturesTest() {
		List<EcoFeature> features = ManageFeatures.load(home + "/FYP/Features/car/");
		for(EcoFeature e : features) {
			System.out.println(e);
		}
	}
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		loadFeaturesTest();
	}

	public static void testCopy() {
		EcoFeature e1 = new EcoFeature();
		System.out.println(e1);
		
		EcoFeature e2 = (EcoFeature)DeepCopy.copy(e1);
		System.out.println(e2);
	}
}
