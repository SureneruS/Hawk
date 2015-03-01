package com.hawk.GA;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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
	//public List<EcoFeature> features = new ArrayList<EcoFeature>();
	public List<EcoFeature> savedFeatures = new ArrayList<EcoFeature>();
	
	/*
	 * populationSize must be always even
	 */
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

	private List<EcoFeature> initializeFeatures() {
		List <EcoFeature> initPopulation = new ArrayList<EcoFeature>();
		for (int i = 0; i < populationSize; i++) {
			initPopulation.add(new EcoFeature());
		}
		
		return initPopulation;
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

	private void trainFeatures(List<EcoFeature> features) {
		System.out.println("Training");
		for (EcoFeature feature : features) {
			System.out.print(".");
			for(Mat trainingImage : positiveTrainingImages) {
				feature.trainWith(trainingImage, true);
			}
			
			for(Mat trainingImage : negativeTrainingImages) {
				feature.trainWith(trainingImage, false);
			}
		}
		System.out.println();

	}

	private void updateFitnessScores(List<EcoFeature> features) {
		System.out.print("Evaluating");
		for(EcoFeature feature : features) {
			System.out.print(".");
			for(Mat trainingImage : positiveTrainingImages) {
				//System.out.println("Positive Image input...");
				feature.updateErrorWith(trainingImage, true);
			}
			
			for(Mat trainingImage : negativeTrainingImages) {
				//System.out.println("Negative Image input...");
				feature.updateErrorWith(trainingImage, false);
			}
			System.out.println();
			feature.calculateFitnessScore();
		}
	}
	
	private void saveFeature(EcoFeature e) {
		savedFeatures.add((EcoFeature)DeepCopy.copy(e));
	}
	
	private void saveFeatures(List<EcoFeature> features) {
		for(EcoFeature feature : features) {
			if(feature.calculateFitnessScore() >= fitnessThreshold) {
				System.out.println("Found a good feature!");
				System.out.println(feature);
				saveFeature(feature);
			}
		}
	}
	
	private EcoFeature randomGoodFeature(List<EcoFeature> features) {
		List<EcoFeature> candidates = new ArrayList<EcoFeature>();
		for(int i = 0; i < selectionCount; i++) {
			int candidateNumber = Helper.getRandomInRange(0, populationSize - 1);
			candidates.add(features.get(candidateNumber));
		}
		
		EcoFeature goodFeature = candidates.get(0);
		for(int i = 1; i < selectionCount; i++) {
			if(goodFeature.compareTo(candidates.get(i)) < 0) {
				goodFeature = candidates.get(i);
			}
		}
		
		return (EcoFeature)DeepCopy.copy(goodFeature);
	}
	
//	private int findNewCount() {
//		int newCount = 0;
//		for(int i = 0; i < this.populationSize; i++) {
//			int randomNumber = Helper.getRandomInRange(1, 1000);
//			if(randomNumber <= 400) {
//				newCount++;
//			}
//		}
//		
//		return ((newCount / 2) * 2);
//	}
	
	@SuppressWarnings("unchecked")
	public List<EcoFeature> newFeaturesByCrossOver(EcoFeature parentOne, EcoFeature parentTwo) {
		
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
	
	private List<EcoFeature> enhanceFeatures(List<EcoFeature> features) {
		List<EcoFeature> newFeatures = new ArrayList<EcoFeature>();
		for(int i = 0; i < this.populationSize; i++) { 
			newFeatures.add(randomGoodFeature(features));
		}
		
		List<EcoFeature> crossOverFeatures = new ArrayList<EcoFeature>();
		for(int i = 0; 2 * i < populationSize; i++) {
			// Cross over rate is 0.6
			if(Math.random() < 0.6) {
				List<EcoFeature> childrenFeatures = null;
				EcoFeature parentOne = selectFeatureForCrossOver(newFeatures);
				EcoFeature parentTwo = selectFeatureForCrossOver(newFeatures);
				while(childrenFeatures == null) {
					childrenFeatures = newFeaturesByCrossOver(parentOne, parentTwo);
				}
				// System.out.println("cxx: " + childrenFeatures.size());
				crossOverFeatures.addAll(childrenFeatures);
			}
		}
		
		trainFeatures(crossOverFeatures);
		updateFitnessScores(crossOverFeatures);
		saveFeatures(crossOverFeatures);
		newFeatures.addAll(crossOverFeatures);
		Collections.sort(newFeatures);
		newFeatures = newFeatures.subList(0, populationSize);
		
		// TODO Mutation
		
		
		return newFeatures;
	}
	
	private EcoFeature selectFeatureForCrossOver(List<EcoFeature> newFeatures) {
		// TODO Improvise
		return newFeatures.get(Helper.getRandomInRange(0, newFeatures.size() - 1));
	}

	public void run() {
		this.loadImages();
		List<EcoFeature> features = this.initializeFeatures();
		this.trainFeatures(features);
		this.updateFitnessScores(features);
		this.saveFeatures(features);
		int generationNumber = 1;
		for(generationNumber = 1; generationNumber <= numberOfGenerations && savedFeatures.size() < featuresLimit; generationNumber++) {
			System.out.println("Current Size: " + features.size());
			System.out.println("Initiating generation " + generationNumber);
//			System.out.println("Training features complete.");
//			System.out.println("Evaluating features complete");
//			System.out.println("Filetering features complete");
			if(generationNumber + 1 <= numberOfGenerations && savedFeatures.size() < featuresLimit) {
				System.out.println("Preparing for next generation");
				features = this.enhanceFeatures(features);
			}
		}
		
		System.out.println("Genetic Algorithm successfully terminated at generation: " + (generationNumber - 1));
		System.out.println("Number of ECOFeatures found: " + savedFeatures.size());
	}
}
