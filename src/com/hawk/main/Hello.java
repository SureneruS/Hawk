package com.hawk.main;

import org.opencv.core.Core;

import com.hawk.GA.EcoFeature;
import com.hawk.GA.GeneticAlgorithm;
import com.hawk.helper.DeepCopy;
//import org.opencv.imgproc.Imgproc;

public class Hello {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		testGA();
		//testCopy();
	}

	public static void testGA() {
		GeneticAlgorithm ga = new GeneticAlgorithm(1, 1, 700, 2, 1);
		ga.run();
		for(EcoFeature f : ga.features) {
			f.printFeature();
			System.out.println(f.fitnessScore);;
		}
		System.out.println(ga.positiveTrainingImages.size());
	}
	
	public static void testCopy() {
		EcoFeature e1 = new EcoFeature();
		e1.printFeature();
		
		EcoFeature e2 = (EcoFeature)DeepCopy.copy(e1);
		e2.printFeature();
	}
}