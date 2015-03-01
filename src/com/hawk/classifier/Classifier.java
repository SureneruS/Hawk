package com.hawk.classifier;

import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.ml.CvBoost;

import com.hawk.GA.EcoFeature;
import com.hawk.GA.Helper;

public class Classifier {
	private CvBoost boost;
	private List<Mat> trainingImages;
	private List<EcoFeature> weakClassifiers;
	private List<Integer> responses;
	
	public Classifier(List<Mat> images, List<Integer> res, List<EcoFeature> weak) {
		this.trainingImages =images;
		this.responses = res;
		this.weakClassifiers = weak;
	}
	
	private Mat generateTrainingData() {
		Mat trainingData = new Mat(trainingImages.size(), weakClassifiers.size(), CvType.CV_32FC1);
		for(int i = 0; i < trainingImages.size(); i++) {
			Mat image = trainingImages.get(i);
			// Helper.standardizeImage(image);
			for(int j = 0; j < weakClassifiers.size(); j++) {
				EcoFeature f = weakClassifiers.get(j);
				Mat featureVector = f.applyFeature(image);
				int output = f.classify(featureVector);
				trainingData.put(i, j, output);
			}
		}
		
		return trainingData;
	}

	public void run() {
		// TODO Auto-generated method stub
		Mat trainingDate = generateTrainingData();
	}
	
}
