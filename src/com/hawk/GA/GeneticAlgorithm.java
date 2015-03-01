package com.hawk.GA;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import com.hawk.helper.DeepCopy;
import com.hawk.transform.Transform;

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
	
	public GeneticAlgorithm() {
		this(50, 1000, 700, 2, 100);
	}
	
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
		savedFeatures.add((EcoFeature)DeepCopy.copy(e));
	}
	
	private void saveFeatures() {
		for(EcoFeature feature : this.features) {
			if(feature.calculateFitnessScore() >= fitnessThreshold) {
				saveFeature(feature);
			}
		}
	}
	
	public EcoFeature randomGoodFeature() {
		List<EcoFeature> candidates = new ArrayList<EcoFeature>();
		for(int i = 0; i < selectionCount; i++) {
			int candidateNumber = Helper.getRandomInRange(0, populationSize - 1);
			candidates.add(features.get(candidateNumber));
		}
		
		EcoFeature goodFeature = candidates.get(0);
		for(int i = 1; i < selectionCount; i++) {
			if(goodFeature.compare(candidates.get(i)) < 0) {
				goodFeature = candidates.get(i);
			}
		}
		
		return (EcoFeature)DeepCopy.copy(goodFeature);
	}
	
	/*
	 * Crossover rate is 0.6
	 */
	public int findNewCount() {
		int newCount = 0;
		for(int i = 0; i < this.populationSize; i++) {
			int randomNumber = Helper.getRandomInRange(1, 1000);
			if(randomNumber <= 400) {
				newCount++;
			}
		}
		
		return ((newCount / 2) * 2);
	}
	
	@SuppressWarnings("unchecked")
	public List<EcoFeature> newFeaturesByCrossOver(List<EcoFeature> parentFeatures) {
		int n = parentFeatures.size();
		int parentOneIndex = Helper.getRandomInRange(0, n - 1);
		int parentTwoIndex = parentOneIndex;
		while(parentOneIndex == parentTwoIndex) {
			parentTwoIndex = Helper.getRandomInRange(0, n - 1);
		}
		
		EcoFeature parentOne = parentFeatures.get(parentOneIndex);
		EcoFeature parentTwo = parentFeatures.get(parentTwoIndex);
		
		Rect childOneRegion = parentOne.getRegion().clone();
		Rect childTwoREgion = parentTwo.getRegion().clone();

		List<Transform> parentOneTransforms = (List<Transform>) DeepCopy.copy(parentOne.getTransforms());
		List<Transform> parentTwoTransforms = (List<Transform>) DeepCopy.copy(parentTwo.getTransforms());
		
		int splitone = Helper.getRandomInRange(0, parentOneTransforms.size() - 1);
		int splitTwo = Helper.getRandomInRange(0, parentTwoTransforms.size() - 1);
		
		List<Transform> temp = parentOneTransforms.subList(splitone, parentOneTransforms.size());
		List<Transform> appendOne = new ArrayList<Transform>(temp);
		temp.clear();
		temp = parentTwoTransforms.subList(splitTwo, parentTwoTransforms.size());
		List<Transform> appendTwo = new ArrayList<Transform>(temp);
		temp.clear();
		
		EcoFeature childOne = new EcoFeature(childOneRegion, parentOneTransforms);
		childOne.appendTransforms(appendTwo);
		EcoFeature childTwo = new EcoFeature(childTwoREgion, parentTwoTransforms);
		childTwo.appendTransforms(appendOne);
		
		if(childOne.getTransforms().size() == 0 || childTwo.getTransforms().size() == 0) {
			return null;
		}
		
		List<EcoFeature> children = new ArrayList<EcoFeature>();
		children.add(childOne);
		children.add(childTwo);
		
		return children;
	}
	
	private void enhanceFeatures() {
		List<EcoFeature> newFeatures = new ArrayList<EcoFeature>();
		int newCount = findNewCount();
		int crossOverCount = this.populationSize - newCount;
		for(int i = 0; i < newCount; i++) { 
			newFeatures.add(randomGoodFeature());
		}
		
		List<EcoFeature> crossOver = new ArrayList<EcoFeature>();
		for(int i = 0; 2 * i < crossOverCount; i++) {
			List<EcoFeature> childrenFeatures = null;
			while(childrenFeatures == null) {
				childrenFeatures = newFeaturesByCrossOver(newFeatures);
			}
			crossOver.addAll(childrenFeatures);
		}
		
		// TODO Mutation
		
	}
	
	public void run() {
		this.initializeFeatures();
		this.loadImages();
		for(int generationNumber = 1; generationNumber <= numberOfGenerations && savedFeatures.size() < featuresLimit; generationNumber++) {
			this.trainFeatures();
			this.updateFitnessScores();
			this.saveFeatures();
			if(generationNumber + 1 <= numberOfGenerations && savedFeatures.size() < featuresLimit) {
				this.enhanceFeatures();
			}
		}
	}
}
