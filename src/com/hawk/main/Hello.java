package com.hawk.main;

import org.opencv.core.Core;

import com.hawk.GA.EcoFeature;
import com.hawk.GA.GeneticAlgorithm;
import com.hawk.helper.DeepCopy;

public class Hello {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		testGA();
		//testCopy();
	}

	public static void testGA() {
		GeneticAlgorithm ga = new GeneticAlgorithm(10, 50, 550, 2, 100);
//		for(EcoFeature f : ga.features) {
//			System.out.println(f);
//			//System.out.println((EcoFeature)DeepCopy.copy(f));
//		}
		ga.run();
		for(EcoFeature e : ga.savedFeatures) {
			System.out.println(e.calculateFitnessScore());
		}
		System.out.println(ga.positiveTrainingImages.size());
	}
	
	public static void testCopy() {
		EcoFeature e1 = new EcoFeature();
		System.out.println(e1);
		
		EcoFeature e2 = (EcoFeature)DeepCopy.copy(e1);
		System.out.println(e2);
	}
}
