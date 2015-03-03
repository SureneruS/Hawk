package com.hawk.transform;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.hawk.GA.Helper;
import com.hawk.transform.constant.TransConstants;

public class HoughCircleTransform extends Transform {
	private static final long serialVersionUID = 439870533613354675L;
	private int minDist;

	public HoughCircleTransform(Mat src, Mat dst, int minDist) {
		super(src, dst);
		this.minDist = src.rows() / minDist;
	}


	public int getMinDist() {
		return minDist;
	}

	public void setMinDist(int minDist) {
		this.minDist = minDist;
	}

	public HoughCircleTransform() {
		super();
	}

	@Override
	public void makeTransform() {
		int minDist = src.rows() / this.minDist;
		Imgproc.HoughCircles(src, dst, TransConstants.HOUGH_CIRCLES_METHOD,TransConstants.HOUGH_CIRCLES_DP, minDist);
	}
	
	public int setParam1() {
		int temp = Helper.getRandomInRange(2, 5);
		temp = (int) java.lang.Math.pow(2, temp);
		return temp;
	}
	
	@Override
	public void mutate() {
		int tempVal;
		do {
			tempVal = this.setParam1();
		}while(tempVal == this.minDist);
		this.minDist = tempVal;
	}

	@Override
	public void initialize() {
		this.noOfParameters = 1;
		this.minDist = this.setParam1();
	}

}
