package com.hawk.main;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import com.hawk.GA.EcoFeature;
import com.hawk.GA.GAControls;
import com.hawk.GA.GeneticAlgorithm;
import com.hawk.GA.Helper;
import com.hawk.classifier.Classifier;
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
	
	public static void trainAdaboostCar() {
		List<Mat> trainingImages = new ArrayList<Mat>();
		List<Integer> responses = new ArrayList<Integer>();
		Helper.addImages(trainingImages, home + "/FYP/cars_yes");
		for(int i = 0; i < trainingImages.size(); i++) {
			responses.add(1);
		}
		
		Helper.addImages(trainingImages, home + "/FYP/cars_no");
		for(int i = responses.size(); i < trainingImages.size(); i++) {
			responses.add(0);
		}
		
		List<EcoFeature> features = ManageFeatures.load(home + "/FYP/Features/car/");
		
		Classifier classifier = new Classifier();
		classifier.setTrainingImages(trainingImages);
		classifier.setResponses(responses);
		classifier.setWeakClassifiers(features);
		classifier.train();
		classifier.save(home + "FYP/AdaBoost/car.xml");
		
	}
	
	public static String predictImage(String imgPath) {
		
		try {
			Mat inputImage = Highgui.imread(imgPath);
			Helper.standardizeImage(inputImage);
			Classifier classifier = new Classifier();
			classifier.setWeakClassifiers(ManageFeatures.load(home + "/FYP/Features/car/"));
			classifier.load(home + "FYP/AdaBoost/car.xml");
			return ("Car  " + classifier.predict(inputImage));
			
		} catch (Exception e) {
			e.printStackTrace();
			return "Image not found";
		}
	}
}
