package com.hawk.transform;

import java.util.Random;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.hawk.GA.Helper;

public class CannyTransform extends Transform {
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
		// TODO Auto-generated constructor stub
		super();
		// initialize();
	}

	public CannyTransform(Mat src, Mat dst, double minTresh, double maxTresh,
			int kernel, boolean norm) {
		super(src, dst);
		this.minTresh = minTresh;
		this.maxTresh = maxTresh;
		this.kernel = kernel;
		this.norm = norm;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		this.kernel = Helper.getRandomInRange(3, 7);
		this.norm=true;
		if (this.kernel % 2 == 0)
		{
			this.kernel--;
			this.norm=false;
		}
		this.minTresh=Helper.getRandomInRange(10, 100);
		this.maxTresh = this.minTresh*Helper.getRandomInRange(2, 4);

		// super.initialize();
	}

	@Override
	public void makeTransform() {
		// TODO Auto-generated method stub
		// super.makeTransform();
		Imgproc.Canny(src, dst, minTresh, maxTresh, kernel, norm);
	}

}
