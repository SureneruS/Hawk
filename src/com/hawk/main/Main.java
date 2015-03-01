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
		
	//	trainAdaboost("car");
		List<Mat> inputs = new ArrayList<Mat>();
		Helper.addImages(inputs, home + "/FYP/Input");

		for(Mat input : inputs) {
			System.out.println(predictImage(input));
		}  
	}

	public static void train(String dataSet) {

		GAControls.PositiveTrainingImageDirectory = home + "/FYP/" + dataSet + "_yes";
		GAControls.NegativeTrainingImageDirectory = home + "/FYP/" + dataSet + "_no";
		GeneticAlgorithm ga = new GeneticAlgorithm(20, 100, 600, 2, 100);
		ga.run();
		List<EcoFeature> resultFeatures = ga.getFeatures();	
		for(EcoFeature e : resultFeatures) {
			System.out.println(e.calculateFitnessScore());
		}

		ManageFeatures.store(resultFeatures, home + "/FYP/Features/" + dataSet + "/", false);
	}

	public static void trainAdaboost(String dataSet) {
		List<Mat> trainingImages = new ArrayList<Mat>();
		List<Integer> responses = new ArrayList<Integer>();
		Helper.addImages(trainingImages, home + "/FYP/" + dataSet + "_yes");
		for(int i = 0; i < trainingImages.size(); i++) {
			responses.add(1);
		}

		Helper.addImages(trainingImages, home + "/FYP/" + dataSet + "_no");
		for(int i = responses.size(); i < trainingImages.size(); i++) {
			responses.add(0);
		}
		System.out.println("Loading Features...");
		List<EcoFeature> features = ManageFeatures.load(home + "/FYP/Features/" + dataSet + "/");

		Classifier classifier = new Classifier();
		classifier.setTrainingImages(trainingImages);
		classifier.setResponses(responses);
		classifier.setWeakClassifiers(features);
		System.out.println("AdaBoost Training...");
		classifier.train();
		classifier.save(home + "/FYP/AdaBoost/" + dataSet + ".xml");

	}

	public static String predictImage(String imgPath) {

		Mat inputImage;
		try {
			inputImage = Highgui.imread(imgPath);
		}
		catch (Exception e) {
			e.printStackTrace();
			return "Image not found";
		}
		Helper.standardizeImage(inputImage);
		return predictImage(inputImage);
	}

	public static String predictImage(Mat inputImage) {

		for(String dataset : DataSets.getIDs()) {
			Classifier classifier = new Classifier();
			try {
				classifier.setWeakClassifiers(ManageFeatures.load(home + "/FYP/Features/" + dataset + "/"));
				classifier.load(home + "/FYP/AdaBoost/" + dataset + ".xml");
				if(classifier.predict(inputImage) == 1.0 ) {
					return dataset;
				}
			}
			catch (Exception e) {
			}
		} 
		return "None";
	}
}
