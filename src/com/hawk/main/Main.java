package com.hawk.main;

import java.util.List;

import org.opencv.core.Core;

import com.hawk.GA.EcoFeature;
import com.hawk.GA.GAControls;
import com.hawk.GA.GeneticAlgorithm;
import com.hawk.helper.ManageFeatures;

public class Main {

	private static String home = System.getProperty("user.home");
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		trainCar();
	}

	public static void trainCar() {
		GAControls.PositiveTrainingImageDirectory = home + "/FYP/cars_yes";
		GAControls.NegativeTrainingImageDirectory = home + "/FYP/cars_no";
		GeneticAlgorithm ga = new GeneticAlgorithm(20, 100, 600, 2, 100);
		ga.run();
		List<EcoFeature> resultFeatures = ga.getFeatures();	
		for(EcoFeature e : resultFeatures) {
			System.out.println(e.calculateFitnessScore());
		}
		
		ManageFeatures.store(resultFeatures, home + "/FYP/Features/car/", false);
	}
}
