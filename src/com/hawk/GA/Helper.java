package com.hawk.GA;

import java.util.Random;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;

public class Helper {
	static Random random = new Random();
	
	/*
	 * returns a number in range [start, end]
	 */
	public static int getRandomInRange(int start, int end) {
		if (start > end) {
			throw new IllegalArgumentException("start > end: not possible");
		}

		long range = (long) end - (long) start + 1;
		long fraction = (long) (range * random.nextDouble());

		return (int) (fraction + start);
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
}
