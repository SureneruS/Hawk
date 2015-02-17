package com.hawk.transform.constant;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class TransConstants {

	public static final double SOBEL_DELTA = 0;
	public static final double SOBEL_SCALE = 1;
	//structured elem,Anchor - used in Morphological trans
	public static final Mat STRUCTURED_ELEMENT = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
	public static final Point MORPH_ANCHOR = new Point(-1,-1);
	public static final double GAUSSIAN_SIGMA = 0;
	public static final int ADAPTIVE_MAX_VAL = 255;
	public static final int ADAPTIVE_MEAN = 5;
	public static final int HOUGH_LINE_THRESHLD = 100;
	public static final double NORMALIZE_BETA = 0;
	public static final int LAPCIAN_DDEPTH = -1;
	public static final double LAPCIAN_SCALE = 1;
	public static final double LAPCIAN_DELTA = 0;
	public static final int HOUGH_CIRCLES_METHOD = Imgproc.CV_HOUGH_GRADIENT;
	public static final int HOUGH_CIRCLES_DP = 1;
	
}
