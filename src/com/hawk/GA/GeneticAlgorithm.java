package com.hawk.GA;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class GeneticAlgorithm {
	public List<Mat> trainingImages = new ArrayList<Mat>();
	public List<EcoFeature> features = new ArrayList<EcoFeature>();

	public void loadImages(int n) {
		// load first n images (or all if < n images in folder)
		// from GAControls.TrainingImageDirectory
		File directory = new File(GAControls.TrainingImageDirectory);
		int i = 0;
		File[] fileList = directory.listFiles();
		for (File file : fileList) {
			if (file.isFile()) {
				addImage(file.getAbsolutePath());
			}
			if (i++ == n) {
				break;
			}
		}
	}

	public void initializeFeatures(int n) {
		for (int i = 0; i < n; i++) {
			features.add(new EcoFeature());
		}
	}

	private void addImage(String path) {
		try {
			Mat inputImage = Highgui.imread(path);
			standardizeImage(inputImage);
			trainingImages.add(inputImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void standardizeImage(Mat inputImage) {
		Imgproc.cvtColor(inputImage, inputImage, Imgproc.COLOR_RGB2GRAY);
		Imgproc.resize(inputImage, inputImage, new Size(
				GAControls.TrainingImageWidth, GAControls.TrainingImageHeight));
	}
}
