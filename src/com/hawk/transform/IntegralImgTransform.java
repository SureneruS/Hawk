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
		// TODO Auto-generated constructor stub
	}
	public IntegralImgTransform(Mat src, Mat dst, int sdepth) {
		super(src, dst);
		this.sdepth = sdepth;
	}
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
	//	super.initialize();
		this.sdepth=-1;
	}
	@Override
	public void makeTransform() {
		// TODO Auto-generated method stub
		//super.makeTransform();
		Imgproc.integral(src, dst,sdepth);
	}
}
