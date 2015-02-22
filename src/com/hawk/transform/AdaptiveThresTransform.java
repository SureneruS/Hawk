package com.hawk.transform;

import java.util.Random;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.hawk.GA.Helper;
import com.hawk.transform.constant.TransConstants;

public class AdaptiveThresTransform extends Transform {
	private int maxValue = TransConstants.ADAPTIVE_MAX_VAL;
	private int adaptiveMethod;
	private int thresholdType;
	private int blockSize;
	private int mean = TransConstants.ADAPTIVE_MEAN;

	public AdaptiveThresTransform() {
		// TODO Auto-generated constructor stub
		super();
	}

	public AdaptiveThresTransform(Mat src, Mat dst, int adaptiveMethod,
			int thresholdType, int blockSize) {
		super(src, dst);
		this.adaptiveMethod = adaptiveMethod;
		this.thresholdType = thresholdType;
		this.blockSize = blockSize;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		super.initialize();
		Random randomGenerator = new Random();
		this.blockSize = Helper.getRandomInRange(3, 7, randomGenerator);
		if(this.blockSize % 2 == 0)
			this.blockSize--;
		int a=Helper.getRandomInRange(0, 3, randomGenerator);
		switch(a)
		{
		case 0:
			this.adaptiveMethod=Imgproc.ADAPTIVE_THRESH_MEAN_C;
			this.thresholdType=Imgproc.THRESH_BINARY;
			break;
		case 1:
			this.adaptiveMethod=Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C;
			this.thresholdType=Imgproc.THRESH_BINARY;
			break;
		case 2:
			this.adaptiveMethod=Imgproc.ADAPTIVE_THRESH_MEAN_C;
			this.thresholdType=Imgproc.THRESH_BINARY_INV;
			break;
		case 3:
			this.adaptiveMethod=Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C;
			this.thresholdType=Imgproc.THRESH_BINARY_INV;
			break;
		}
	}

	@Override
	public void makeTransform() {
		Imgproc.adaptiveThreshold(src, dst, maxValue, adaptiveMethod,thresholdType, blockSize, mean);
	}

	public int getAdaptiveMethod() {
		return adaptiveMethod;
	}

	public void setAdaptiveMethod(int adaptiveMethod) {
		this.adaptiveMethod = adaptiveMethod;
	}

	public int getThresholdType() {
		return thresholdType;
	}

	public void setThresholdType(int thresholdType) {
		this.thresholdType = thresholdType;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public int getMean() {
		return mean;
	}

}
