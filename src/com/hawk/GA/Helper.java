package com.hawk.GA;

import java.io.File;
import java.util.List;
import java.util.Random;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Helper {
	static Random random = new Random();
	
	/*
	 * returns a number in range [start, end]
	 */
	public static int getRandomInRange(int start, int end) {
		if (start > end) {
			throw new IllegalArgumentException("start > end ("+ start + " > " + end + "): not possible");
		}

		long range = (long) end - (long) start + 1;
		long fraction = (long) (range * random.nextDouble());

		return (int) (fraction + start);
	}

	public static double random() {
		return random.nextDouble();
	}
	public static Mat linearize(Mat roi) {
		int rows = roi.rows();
		int cols = roi.cols();
		// TODO Explore on what type is exactly
		Mat linearMat = new Mat(new Size(rows * cols, 1), CvType.CV_8UC1);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				linearMat.put(0, i * rows + j, roi.get(i, j));
			}
		}
		return linearMat;
	}

	public static void log(Mat image) {
		System.out.println("Image dump:\n " + image.dump());
	}
	
	public static int Int(boolean b) {
		return b ? 1 : 0;
	}

	public static void standardizeImage(Mat inputImage) {
		Imgproc.cvtColor(inputImage, inputImage, Imgproc.COLOR_RGB2GRAY);
		Imgproc.resize(inputImage, inputImage, new Size(
				GAControls.TrainingImageWidth, GAControls.TrainingImageHeight));
	}

	public static void addImage(String path, List<Mat> imageList) {
		try {
			Mat inputImage = Highgui.imread(path);
			standardizeImage(inputImage);
			imageList.add(inputImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addImages(List<Mat> imageList, String trainingimagedirectory) {
		File directory = new File(trainingimagedirectory);
		File[] fileList = directory.listFiles();
		for (File file : fileList) {
			if (file.isFile()) {
				addImage(file.getAbsolutePath(), imageList);
			}
		}
	}
}
