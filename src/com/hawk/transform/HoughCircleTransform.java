package com.hawk.transform;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

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
		Imgproc.HoughCircles(src, circles, TransConstants.HOUGH_CIRCLES_METHOD,
				TransConstants.HOUGH_CIRCLES_DP, minDist);
		// makeDestImage();
		this.dst = circles;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		this.minDist = src.rows() / 8;
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
