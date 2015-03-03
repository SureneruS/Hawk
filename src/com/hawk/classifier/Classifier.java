package com.hawk.classifier;

import java.io.File;
import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.ml.CvBoost;
import org.opencv.ml.CvBoostParams;

import com.hawk.GA.EcoFeature;

public class Classifier {
	private CvBoost boost;
	private List<Mat> trainingImages;
	private List<EcoFeature> weakClassifiers;
	private List<Integer> responses;
	
	public Classifier() {
		
	}

	public CvBoost getBoost() {
		return boost;
	}

	public void setBoost(CvBoost boost) {
		this.boost = boost;
	}

	public List<Mat> getTrainingImages() {
		return trainingImages;
	}

	public void setTrainingImages(List<Mat> trainingImages) {
		this.trainingImages = trainingImages;
	}

	public List<EcoFeature> getWeakClassifiers() {
		return weakClassifiers;
	}

	public void setWeakClassifiers(List<EcoFeature> weakClassifiers) {
		this.weakClassifiers = weakClassifiers;
	}

	public List<Integer> getResponses() {
		return responses;
	}

	public void setResponses(List<Integer> responses) {
		this.responses = responses;
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
	
	private Mat generateResponse() {
		Mat response = new Mat(responses.size(), 1, CvType.CV_32FC1);
		for(int i = 0; i < responses.size(); i++) {
			response.put(i, 0, responses.get(i));
		}		
		return response;
	}
	
	private CvBoostParams generateParam() {
		CvBoostParams params = new CvBoostParams();
		params.set_boost_type(CvBoost.DISCRETE);
		params.set_weak_count(this.weakClassifiers.size());
		params.set_weight_trim_rate(0);
		
		return params;
	}

	public void train() {
		Mat trainingData = generateTrainingData();
		Mat response = generateResponse();
		CvBoostParams params = generateParam();
		Mat varType = new Mat(trainingData.width() + 1, 1, CvType.CV_8U );
		varType.setTo(new Scalar(0)); // 0 = CV_VAR_NUMERICAL.
		varType.put(trainingData.width(), 0, 1); // 1 = CV_VAR_CATEGORICAL;
		boost = new CvBoost();
		boost.train(trainingData, 1, response, new Mat(), new Mat(), varType, new Mat(), params, false);
	}

	public float predict(Mat inputImage) {
		Mat sample = generateTestData(inputImage);
		return boost.predict(sample);
	}
	
	private Mat generateTestData(Mat inputImage) {
		Mat testData = new Mat(1, weakClassifiers.size(), CvType.CV_32FC1);
		for(int j = 0; j < weakClassifiers.size(); j++) {
			EcoFeature f = weakClassifiers.get(j);
			Mat featureVector = f.applyFeature(inputImage);
			int output = f.classify(featureVector);
			testData.put(0, j, output);
		}
		return testData;
	}

	public void save(String path) {
		File file = new File(path).getParentFile();
		if(!file.exists()) {
			if(file.mkdirs()) {
				System.out.println("ok");
			}
			else {
				System.out.println("no");
			}
		}
		file = new File(path);
		boost.save(path);
	}
	
	public void load(String path) {
		boost = new CvBoost();
		boost.load(path);
	}
}
