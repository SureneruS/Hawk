package com.hawk.transform;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.hawk.transform.constant.TransConstants;

public class HoughLineTransform extends Transform {
	private static final long serialVersionUID = -2510864209479802961L;
	private double rho;
	private double theta;
	private int threshold = TransConstants.HOUGH_LINE_THRESHLD;

	public HoughLineTransform() {
		super();
	}

	public HoughLineTransform(Mat src, Mat dst, double rho, double theta) {
		super(src, dst);
		this.rho = rho;
		this.theta = theta;
	}

	@Override
	public void initialize() {
		this.rho = 1;
		this.theta = Math.PI / 180;
	}

	@Override
	public void makeTransform() {
		Imgproc.HoughLines(src, dst, rho, theta, threshold);
	}

	public double getRho() {
		return rho;
	}

	public void setRho(double rho) {
		this.rho = rho;
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}

	public int getThreshold() {
		return threshold;
	}

}
