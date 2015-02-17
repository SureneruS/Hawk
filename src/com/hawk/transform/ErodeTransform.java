package com.hawk.transform;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.hawk.transform.constant.TransConstants;

public class ErodeTransform extends Transform{
	private Mat kernel = TransConstants.STRUCTURED_ELEMENT;
	private int iteration;
	
	public ErodeTransform() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public ErodeTransform(Mat src,Mat dst,int iteration){
		super(src, dst);
		this.iteration = iteration;
	}
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		this.iteration=3;
	}
	@Override
	public void makeTransform() {
		
		Imgproc.erode(src, dst, kernel, TransConstants.MORPH_ANCHOR, iteration);
	}

	public int getIteration() {
		return iteration;
	}

	public void setIteration(int iteration) {
		this.iteration = iteration;
	}

	public Mat getKernel() {
		return kernel;
	}

}
