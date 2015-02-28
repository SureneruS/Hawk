package com.hawk.transform;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.hawk.GA.Helper;
import com.hawk.transform.constant.TransConstants;

public class DiffGaussianTransform extends Transform{
	private int ksize1;
	private int ksize2;
		
	
	public DiffGaussianTransform(Mat src, Mat dst, int ksize1, int ksize2) {
		super(src, dst);
		this.ksize1 = ksize1;
		this.ksize2 = ksize2;
	}

	public DiffGaussianTransform() {
		super();
	}

	public int getKsize1() {
		return ksize1;
	}
	
	public void setKsize1(int ksize1) {
		this.ksize1 = ksize1;
	}
	
	public int getKsize2() {
		return ksize2;
	}

	public void setKsize2(int ksize2) {
		this.ksize2 = ksize2;
	}
	
	public int setParam1() {
		int temp = Helper.getRandomInRange(1, 7);
		if(temp % 2 == 0)
			temp--;
		return temp;
	}
	
	@Override
	public void initialize() {
		this.noOfParameters = 2;
		this.ksize1 = this.setParam1();
		int tempVal;
		do {
			tempVal = this.setParam1();
		}while(tempVal == this.ksize1);
		this.ksize2 = tempVal;
	}
	
	@Override
	public void mutate() {
		int tempVal;
		switch(Helper.getRandomInRange(1, this.noOfParameters)) {
		case 1:
			do {
				tempVal = this.setParam1();
			}while(tempVal == this.ksize2 || tempVal == this.ksize1);
			this.ksize1 = tempVal;
			break;
		case 2:
			do {
				tempVal = this.setParam1();
			}while(tempVal == this.ksize2 || tempVal == this.ksize1);
			this.ksize2 = tempVal;
		}
	}
	
	@Override
	public void makeTransform() {
		Mat temp1 = new Mat();
		Mat temp2 = new Mat();
		Imgproc.GaussianBlur(src, temp1, new Size(ksize1, ksize1),
				TransConstants.GAUSSIAN_SIGMA);
		Imgproc.GaussianBlur(src, temp2, new Size(ksize2, ksize2),
				TransConstants.GAUSSIAN_SIGMA);
		Core.absdiff(temp1, temp2, dst);
		dst.convertTo(dst, CvType.CV_8UC1);
	}
	
}
