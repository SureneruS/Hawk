package com.hawk.main;

import java.awt.Window;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
//import org.opencv.imgproc.Imgproc;

import com.atul.JavaOpenCV.Imshow;
import com.hawk.GA.EcoFeature;
import com.hawk.GA.GAControls;
import com.hawk.GA.GeneticAlgorithm;
import com.hawk.GA.Perceptron;
import com.hawk.transform.DistanceTransform;
import com.hawk.transform.ErodeTransform;
import com.hawk.transform.GaborTransform;
import com.hawk.transform.GaussianTranform;
import com.hawk.transform.LogTransform;
import com.hawk.transform.SobelTransform;
import com.hawk.transform.Transform;

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
		GeneticAlgorithm ga = new GeneticAlgorithm();
		ga.initializeFeatures(2);
		ga.loadImages();
		ga.trainFeatures();

		Imshow window = new Imshow("Training");
		window.showImage(ga.positiveTrainingImages.get(0));

		/*
		 * Mat m = new Mat(new Size(3, 1), CvType.CV_8UC1);
		 * 
		 * for(int i = 0; i < 3; i++) { for(int j = 0; j < 1; j++) { m.put(i, j,
		 * i*3 + j); } }
		 * 
		 * System.out.println(m.dump());
		 * 
		 * System.out.println(Helper.linearize(m).dump());
		 */
		System.out.println(ga.positiveTrainingImages.size());
	}

	public static void testPerceptron() {
		Perceptron p = new Perceptron(10);
		System.out.println(p.weights.get(0));
	}
}