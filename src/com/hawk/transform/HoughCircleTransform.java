package com.hawk.transform;

import java.util.Random;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import com.hawk.GA.Helper;
import com.hawk.transform.constant.TransConstants;

public class HoughCircleTransform extends Transform {
	private Mat circles;
	private int minDist;

	public HoughCircleTransform(Mat src, Mat dst, int minDist) {
		super(src, dst);
		this.minDist = src.rows() / minDist;
		this.circles = new Mat();
	}

	public Mat getCircles() {
		return circles;
	}

	public void setCircles(Mat circles) {
		this.circles = circles;
	}

	public int getMinDist() {
		return minDist;
	}

	public void setMinDist(int minDist) {
		this.minDist = minDist;
	}

	public HoughCircleTransform() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public void makeTransform() {
		// TODO Auto-generated method stub
		if(circles == null)
			circles = new Mat();
		Imgproc.HoughCircles(src, circles, TransConstants.HOUGH_CIRCLES_METHOD,TransConstants.HOUGH_CIRCLES_DP, minDist);
		// makeDestImage();
		this.dst = circles;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		Random randomGenerator = new Random();
		int a = Helper.getRandomInRange(2, 5, randomGenerator);
		a=(int) java.lang.Math.pow(2, a);
		this.minDist = src.rows() / a;
		// super.initialize();
	}
	/*
	 * private void makeDestImage(){
	 * 
	 * Imgproc.cvtColor(src, dst, Imgproc.COLOR_GRAY2BGR); for(int i=0; i <
	 * circles.cols();i++){
	 * 
	 * double x = circles.get(0, i)[0]; double y = circles.get(0, i)[1]; double
	 * rad = circles.get(0, i)[2]; int radius = (int) Math.round(rad);
	 * 
	 * Point center = new Point(x,y); Core.circle(dst, center, radius, new
	 * Scalar(0, 255, 0));
	 * 
	 * } }
	 */

}
