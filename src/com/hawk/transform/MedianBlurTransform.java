package com.hawk.transform;

import java.util.Random;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.hawk.GA.Helper;

public class MedianBlurTransform extends Transform {
	private int ksize;

	public int getKsize() {
		return ksize;
	}

	public void setKsize(int ksize) {
		this.ksize = ksize;
	}

	public MedianBlurTransform() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MedianBlurTransform(Mat src, Mat dst, int ksize) {
		super(src, dst);
		this.ksize = ksize;
	}

	@Override
	public void initialize() {
		this.ksize = Helper.getRandomInRange(1, 7);
		if (this.ksize % 2 == 0)
			this.ksize--;
	}

	@Override
	public void makeTransform() {
		// TODO Auto-generated method stub
		// super.makeTransform();
		Imgproc.medianBlur(src, dst, ksize);
	}

}
