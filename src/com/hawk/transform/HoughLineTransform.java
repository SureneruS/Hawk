package com.hawk.transform;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.hawk.transform.constant.TransConstants;

public class HoughLineTransform extends Transform {
	private Mat lines;
	private double rho;
	private double theta;
	private int threshold = TransConstants.HOUGH_LINE_THRESHLD;

	public HoughLineTransform() {
		// TODO Auto-generated constructor stub
		super();
	}

	public HoughLineTransform(Mat src, Mat dst, double rho, double theta) {
		super(src, dst);
		this.rho = rho;
		this.theta = theta;
		this.lines = new Mat();

	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		// super.initialize();
		this.rho = 1;
		this.theta = Math.PI / 180;
	}

	@Override
	public void makeTransform() {
		// TODO Auto-generated method stub
		if (lines == null)
			lines = new Mat();
		Imgproc.HoughLines(src, lines, rho, theta, threshold);
		// makeDestImage();
		this.dst = lines;
	}

	/*
	 * private void makeDestImage(){
	 * 
	 * Imgproc.cvtColor(src, dst, Imgproc.COLOR_GRAY2BGR); for(int i=0; i<
	 * lines.cols();i++){
	 * 
	 * double[] s = lines.get(0, i); double rho = s[0]; double theta= s[1];
	 * double a = Math.cos(theta); double b = Math.sin(theta); double x0 =
	 * a*rho, y0 = b*rho; Point pt1 = new Point(Math.round(x0 + 1000*(-b)),
	 * Math.round(y0 + 1000*(a))); Point pt2 = new Point(Math.round(x0 -
	 * 1000*(-b)), Math.round(y0 - 1000*(a))); Core.line(dst, pt1, pt2, new
	 * Scalar(0,0,255), 1);
	 * 
	 * } }
	 */

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

	public Mat getLines() {
		return lines;
	}

	public int getThreshold() {
		return threshold;
	}

}
