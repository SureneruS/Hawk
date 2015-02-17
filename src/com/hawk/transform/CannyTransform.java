package com.hawk.transform;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class CannyTransform extends Transform {
	private double minTresh,maxTresh;
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
		//initialize();
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
		this.minTresh=30;
		this.maxTresh=90;
		this.kernel=3;
		this.norm=true;
	//	super.initialize();
	}
	@Override
	public void makeTransform() {
		// TODO Auto-generated method stub
		//super.makeTransform();
		Imgproc.Canny(src, dst, minTresh, maxTresh,kernel,norm);
	}
	
}
