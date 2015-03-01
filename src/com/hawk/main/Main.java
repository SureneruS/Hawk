package com.hawk.main;

import java.util.List;

import org.opencv.core.Core;

import com.hawk.GA.EcoFeature;
import com.hawk.GA.GAControls;
import com.hawk.GA.GeneticAlgorithm;
import com.hawk.helper.DeepCopy;
import com.hawk.helper.ManageFeatures;

public class Main {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		trainCar();
		//testCopy();
	}

	public static void trainCar() {
		GAControls.PositiveTrainingImageDirectory = "/home/jerin/Documents/FYP/cars_brad";
		GAControls.NegativeTrainingImageDirectory = "/home/jerin/Documents/FYP/cars_brad_bg";
		GeneticAlgorithm ga = new GeneticAlgorithm(5, 5, 505, 2, 10);
//		for(EcoFeature f : ga.features) {
//			System.out.println(f);
//			//System.out.println((EcoFeature)DeepCopy.copy(f));
//		}
		ga.run();
		List<EcoFeature> resultFeatures = ga.getFeatures();	
		for(EcoFeature e : resultFeatures) {
			System.out.println(e.calculateFitnessScore());
		}
		
		ManageFeatures.store(resultFeatures, "/home/jerin/Documents/FYP/Features/car/");
	}
	
	public static void testCopy() {
		EcoFeature e1 = new EcoFeature();
		System.out.println(e1);
		
		EcoFeature e2 = (EcoFeature)DeepCopy.copy(e1);
		System.out.println(e2);
	}
}
