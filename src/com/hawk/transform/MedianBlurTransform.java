package com.hawk.transform;

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
	}

	public MedianBlurTransform(Mat src, Mat dst, int ksize) {
		super(src, dst);
		this.ksize = ksize;
	}

	public int setParam1() {
		int temp = Helper.getRandomInRange(1, 7);
		if(temp % 2 == 0)
			temp--;
		return temp;
	}

	@Override
	public void mutate() {
		int tempVal;
		do {
			tempVal = this.setParam1();
		}while(tempVal == this.ksize);
		this.ksize = tempVal;
	}

	@Override
	public void initialize() {
		this.noOfParameters = 1;
		this.ksize = this.setParam1();
	}

	@Override
	public void makeTransform() {
		Imgproc.medianBlur(src, dst, ksize);
	}

}
