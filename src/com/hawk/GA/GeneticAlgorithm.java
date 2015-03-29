package com.hawk.GA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

import com.hawk.helper.DeepCopy;
import com.hawk.helper.ManageFeatures;
import com.hawk.transform.Transform;
import com.sun.org.apache.xalan.internal.utils.FeatureManager.Feature;

public class GeneticAlgorithm {
	private List<Mat> positiveTrainingImages = new ArrayList<Mat>();
	private List<Mat> negativeTrainingImages = new ArrayList<Mat>();
	//public List<EcoFeature> features = new ArrayList<EcoFeature>();
	private List<EcoFeature> savedFeatures = new ArrayList<EcoFeature>();

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
		Helper.addImages(positiveTrainingImages, GAControls.PositiveTrainingImageDirectory);
		Helper.addImages(negativeTrainingImages, GAControls.NegativeTrainingImageDirectory);
	}

	private List<EcoFeature> initializeFeatures() {
		List <EcoFeature> initPopulation = new ArrayList<EcoFeature>();
		for (int i = 0; i < populationSize; i++) {
			initPopulation.add(new EcoFeature());
		}

		return initPopulation;
	}

	private void trainFeatures(List<EcoFeature> features) {
		System.out.print("Training");
		for (EcoFeature feature : features) {
			System.out.print(".");
			int i = 0, j = 0;
			int n = positiveTrainingImages.size();
			int m = negativeTrainingImages.size();
			while(i < n && j < m) {
				double randomDouble = Helper.random();
				if(randomDouble < 0.5) {
					feature.trainWith(positiveTrainingImages.get(i++), 1);
				}
				else {
					feature.trainWith(negativeTrainingImages.get(j++), 0);
				}
			}
			
			while(i < n) {
				feature.trainWith(positiveTrainingImages.get(i++), 1);
			}
			
			while(j < m) {
				feature.trainWith(negativeTrainingImages.get(j++), 0);
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
				feature.updateErrorWith(trainingImage, 1);
			}

			for(Mat trainingImage : negativeTrainingImages) {
				//System.out.println("Negative Image input...");
				feature.updateErrorWith(trainingImage, 0);
			}
			feature.calculateFitnessScore();
		}
		System.out.println();
	}

	private void saveFeature(EcoFeature e) {
		savedFeatures.add((EcoFeature)DeepCopy.copy(e));
		ManageFeatures.store(e, GAControls.home + "/FYP/Features/" + GAControls.dataset + "/");
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

	private List<EcoFeature> evolveFeatures(List<EcoFeature> features) {
		List<EcoFeature> newFeatures = new ArrayList<EcoFeature>();
		for(int i = 0; i < this.populationSize; i++) { 
			newFeatures.add(randomGoodFeature(features));
		}

		List<EcoFeature> crossOverFeatures = new ArrayList<EcoFeature>();
		for(int i = 0; 2 * i < populationSize; i++) {
			// Cross over rate is 0.6
			if(Math.random() < 0.6) {
				List<EcoFeature> childrenFeatures = null;
				EcoFeature parentOne = selectFeatureForCrossOver(features);
				EcoFeature parentTwo = selectFeatureForCrossOver(features);
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

		// Mutation rate is 0.02 per transform parameter
		List<EcoFeature> mutatedFeature = new ArrayList<EcoFeature>();
		for(EcoFeature f : newFeatures) {
			boolean mutated = false;
			for(Transform transform : f.getTransforms()) {
				if(transform.noOfParameters * 0.02 > Math.random() ) {
					System.out.println("Mutation hapenning");
					transform.mutate();
					mutated = true;
				}
			}
			if(mutated) {
				mutatedFeature.add(f);
			}
		}
		trainFeatures(mutatedFeature);
		updateFitnessScores(mutatedFeature);
		saveFeatures(mutatedFeature);
		Collections.sort(newFeatures);
		newFeatures = newFeatures.subList(0, populationSize);
		return newFeatures;
	}

	private EcoFeature selectFeatureForCrossOver(List<EcoFeature> newFeatures) {
		double fitnessSum = 0;
		for(EcoFeature f : newFeatures) {
			fitnessSum += f.calculateFitnessScore();
		}
		
		List<Double> prob = new ArrayList<Double>();
		double probSum = 0;
		for(EcoFeature f : newFeatures) {
			double probability = (f.calculateFitnessScore()/fitnessSum);  
			prob.add(probSum + probability);
			probSum += probability;
		}
		
		System.out.println("ProbSum: " + probSum);
		double random = Helper.random();
		for(int i = 0, n = prob.size(); i < n; i++) {
			if(random > prob.get(i)) {
				return newFeatures.get(i);
			}
		}
		
		return newFeatures.get(newFeatures.size() - 1);
	}

	public void run() {
		this.loadImages();
		List<EcoFeature> features = this.initializeFeatures();
		this.trainFeatures(features);
		this.updateFitnessScores(features);
		this.saveFeatures(features);
		int generationNumber;
		for(generationNumber = 1; generationNumber < numberOfGenerations && savedFeatures.size() < featuresLimit; generationNumber++) {
			System.out.println("Current Size: " + features.size());
			System.out.println("Initiating generation " + generationNumber);
			System.out.println("Preparing for next generation");
			features = this.evolveFeatures(features);
		}

		System.out.println("Genetic Algorithm successfully terminated at generation: " + (generationNumber - 1));
		System.out.println("Number of ECOFeatures found: " + savedFeatures.size());
	}

	public List<EcoFeature> getFeatures() {
		return this.savedFeatures;
	}
}
