package com.hawk.transform;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class IntegralImgTransform extends Transform {
	private int sdepth;

	public int getSdepth() {
		return sdepth;
	}

	public void setSdepth(int sdepth) {
		this.sdepth = sdepth;
	}

	public IntegralImgTransform() {
		super();
	}

	public IntegralImgTransform(Mat src, Mat dst, int sdepth) {
		super(src, dst);
		this.sdepth = sdepth;
	}

	@Override
	public void initialize() {
		this.sdepth = -1;
	}

	@Override
	public void makeTransform() {
		Imgproc.integral(src, dst, sdepth);
	}
}
