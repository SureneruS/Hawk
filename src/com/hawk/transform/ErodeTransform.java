package com.hawk.transform;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.hawk.GA.Helper;
import com.hawk.transform.constant.TransConstants;

public class ErodeTransform extends Transform {
	private static final long serialVersionUID = 8460462680410583483L;
	private static Mat kernel = TransConstants.STRUCTURED_ELEMENT;
	private int iteration;

	public ErodeTransform() {
		super();
	}

	public ErodeTransform(Mat src, Mat dst, int iteration) {
		super(src, dst);
		this.iteration = iteration;
	}

	public int setParam1() {
		return Helper.getRandomInRange(1, 5);
	}

	@Override
	public void mutate() {
		int tempVal;
		do {
			tempVal = this.setParam1();
		}while(tempVal == this.iteration);
		this.iteration = tempVal;
	}

	@Override
	public void initialize() {
		this.noOfParameters = 1;
		this.iteration = this.setParam1();
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
