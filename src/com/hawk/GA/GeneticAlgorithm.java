package com.hawk.GA;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class GeneticAlgorithm {
	public List<Mat> positiveTrainingImages = new ArrayList<Mat>();
	public List<Mat> negativeTrainingImages = new ArrayList<Mat>();
	public List<EcoFeature> features = new ArrayList<EcoFeature>();
	public List<EcoFeature> savedFeatures = new ArrayList<EcoFeature>();
	
	private int populationSize;
	private int numberOfGenerations;
	private int fitnessThreshold;
	private int selectionCount;
	private int featuresLimit;
	
	public GeneticAlgorithm(int popSize, int GenNo, int thres, int sel, int lim) {
		this.populationSize = popSize;
		this.numberOfGenerations = GenNo;
		this.fitnessThreshold = thres;
		this.selectionCount = sel;
		this.featuresLimit = lim;
	}
	
	private void loadImages() {
		addImages(positiveTrainingImages, GAControls.PositiveTrainingImageDirectory);
		addImages(negativeTrainingImages, GAControls.NegativeTrainingImageDirectory);
	}

	private void addImages(List<Mat> imageList, String trainingimagedirectory) {
		File directory = new File(trainingimagedirectory);;
		File[] fileList = directory.listFiles();
		for (File file : fileList) {
			if (file.isFile()) {
				addImage(file.getAbsolutePath(), imageList);
			}
		}
	}

	private void initializeFeatures() {
		for (int i = 0; i < populationSize; i++) {
			features.add(new EcoFeature());
		}
	}

	private void addImage(String path, List<Mat> imageList) {
		try {
			Mat inputImage = Highgui.imread(path);
			standardizeImage(inputImage);
			imageList.add(inputImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void standardizeImage(Mat inputImage) {
		Imgproc.cvtColor(inputImage, inputImage, Imgproc.COLOR_RGB2GRAY);
		Imgproc.resize(inputImage, inputImage, new Size(
				GAControls.TrainingImageWidth, GAControls.TrainingImageHeight));
	}

	private void trainFeatures() {
		for (EcoFeature feature : features) {
			//feature.printFeature();
			for(Mat trainingImage : positiveTrainingImages) {
				feature.trainWith(trainingImage, true);
			}
			
			for(Mat trainingImage : negativeTrainingImages) {
				feature.trainWith(trainingImage, false);
			}
		}

	}

	private void updateFitnessScores() {
		for(EcoFeature feature : this.features) {
			System.out.println("Training feature...");
			for(Mat trainingImage : positiveTrainingImages) {
				//System.out.println("Positive Image input...");
				feature.updateErrorWith(trainingImage, true);
			}
			
			for(Mat trainingImage : negativeTrainingImages) {
				//System.out.println("Negative Image input...");
				feature.updateErrorWith(trainingImage, false);
			}
			feature.calculateFitnessScore();
		}
	}
	
	private void saveFeature(EcoFeature e) {
		// TODO Auto-generated method stub
	}
	
	private void saveFeatures() {
		for(EcoFeature feature : this.features) {
			if(feature.fitnessScore >= fitnessThreshold) {
				saveFeature(feature);
			}
		}
	}
	
	private EcoFeature randomGoodFeature() {
		// TODO
		return new EcoFeature();
	}
	
	private void enhanceFeatures() {
		// TODO Auto-generated method stub
		List<EcoFeature> newFeatures = new ArrayList<EcoFeature>();
		// cross over rate = 0.6
		for(int i = 0; i < (0.4 * this.populationSize); i++) { 
			newFeatures.add(randomGoodFeature());
		}
		
		for(int i = 0; i < (0.6 * this.populationSize); i++) {
			//newFeatures.add(e);
		}
		
	}
	
	public void run() {
		this.initializeFeatures();
		this.loadImages();
		for(int generationNumber = 1; generationNumber <= numberOfGenerations && savedFeatures.size() < featuresLimit; generationNumber++) {
			this.trainFeatures();
			this.updateFitnessScores();
			this.saveFeatures();
			this.enhanceFeatures();
		}
	}
}
