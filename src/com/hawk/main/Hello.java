package com.hawk.main;

import org.opencv.core.Core;

import com.hawk.GA.EcoFeature;
import com.hawk.GA.GeneticAlgorithm;
import com.hawk.GA.Perceptron;
//import org.opencv.imgproc.Imgproc;

public class Hello {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		// Mat mat = Mat.eye( 3, 3, CvType.CV_8UC1 );
		/*
		 * Mat test = Highgui.imread("/home/jerin/Pictures/1.png");
		 * 
		 * Imshow window = new Imshow("Test"); Transform t = new
		 * GaborTransform();
		 * 
		 * t.setSrc(test); t.setDst(test); t.initialize(); t.makeTransform();
		 * window.showImage(test);
		 */// System.out.println(test.dump());
			// System.out.println( "mat = " + mat.dump() );

		testGA();
		// testPerceptron();

	}

	public static void testGA() {
		GeneticAlgorithm ga = new GeneticAlgorithm(1, 1, 700);
		ga.run();
		for(EcoFeature f : ga.features) {
			f.printFeature();
			System.out.println(f.fitnessScore);;
		}
		System.out.println(ga.positiveTrainingImages.size());
	}

	public static void testPerceptron() {
		Perceptron p = new Perceptron(10);
		//System.out.println(p.weights.get(0));
	}
}