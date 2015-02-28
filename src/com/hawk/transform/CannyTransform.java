package com.hawk.transform;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.hawk.GA.Helper;

public class CannyTransform extends Transform {
	private static final long serialVersionUID = 1570407180983084308L;
	private double minTresh, maxTresh;
	private int kernel;
	private boolean norm;

	public boolean isNorm() {
		return norm;
	}

	public void setNorm(boolean norm) {
		this.norm = norm;
	}

	public double getMinTresh() {
		return minTresh;
	}

	public void setMinTresh(double minTresh) {
		this.minTresh = minTresh;
	}

	public double getMaxTresh() {
		return maxTresh;
	}

	public void setMaxTresh(double maxTresh) {
		this.maxTresh = maxTresh;
	}

	public int getKernel() {
		return kernel;
	}

	public void setKernel(int kernel) {
		this.kernel = kernel;
	}

	public CannyTransform() {
		super();

	}

	public CannyTransform(Mat src, Mat dst, double minTresh, double maxTresh,
			int kernel, boolean norm) {
		super(src, dst);
		this.minTresh = minTresh;
		this.maxTresh = maxTresh;
		this.kernel = kernel;
		this.norm = norm;
	}

	public int setParam1() {
		int temp = Helper.getRandomInRange(3, 7);
		if (temp % 2 == 0) {
			temp--;
		}
		return temp;
	}
	
	public void setParam2() {
		switch(Helper.getRandomInRange(0, 1)) {
		case 0:
			this.norm = true;
			break;
		case 1:
			this.norm = false;
		}
	}
	
	public void setParam3() {
		this.minTresh = Helper.getRandomInRange(10, 100);
		this.maxTresh = this.minTresh*Helper.getRandomInRange(2, 4);
	}
	
	@Override
	public void initialize() {
		this.noOfParameters=3;
		this.kernel = this.setParam1();
		this.setParam2();
		this.setParam3();
	}

	@Override
	public void mutate() {
		switch(Helper.getRandomInRange(1, this.noOfParameters)) {
		case 1:
			int tempVal;
			do {
				tempVal = this.setParam1();
			}while(tempVal == this.kernel);
			this.kernel = tempVal;
			break;
		case 2:
			this.norm = !(this.norm);
			break;
		case 3:
			this.setParam3();
		}
		
	}

	@Override
	public void makeTransform() {
		Imgproc.Canny(src, dst, minTresh, maxTresh, kernel, norm);
	}

}
