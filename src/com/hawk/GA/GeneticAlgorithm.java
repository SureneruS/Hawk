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
	
	public void loadImages() {
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

	public void initializeFeatures(int n) {
		for (int i = 0; i < n; i++) {
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

	public void trainFeatures() {
		for (EcoFeature feature : features) {
			feature.printFeature();
			for(Mat trainingImage : positiveTrainingImages) {
				feature.trainWith(trainingImage, true);
			}
			
			for(Mat trainingImage : negativeTrainingImages) {
				feature.trainWith(trainingImage, false);
			}
		}

	}
}
