package com.hawk.transform;

import java.util.Random;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.hawk.GA.Helper;
import com.hawk.transform.constant.TransConstants;

public class DilateTransform extends Transform {
	private Mat kernel = TransConstants.STRUCTURED_ELEMENT;
	private int iteration;

	public DilateTransform() {
		// TODO Auto-generated constructor stub
		super();
	}

	public DilateTransform(Mat src, Mat dst, int iteration) {
		super(src, dst);
		this.iteration = iteration;

	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		// super.initialize();
		this.iteration = Helper.getRandomInRange(1, 3);
	}

	@Override
	public void makeTransform() {
		// TODO Auto-generated method stub
		Imgproc.dilate(src, dst, kernel, TransConstants.MORPH_ANCHOR, iteration);
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
