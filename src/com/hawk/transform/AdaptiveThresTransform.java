package com.hawk.transform;

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
		super();
	}

	public AdaptiveThresTransform(Mat src, Mat dst, int adaptiveMethod,
			int thresholdType, int blockSize) {
		super(src, dst);
		this.adaptiveMethod = adaptiveMethod;
		this.thresholdType = thresholdType;
		this.blockSize = blockSize;
	}
	public int setParam1() {
		int temp = Helper.getRandomInRange(3, 7);
		if(temp % 2 == 0)
			temp--;
		return temp;
	}
	
	public void setParam2() {
		switch(Helper.getRandomInRange(0, 1))
		{
		case 0:
			this.adaptiveMethod = Imgproc.ADAPTIVE_THRESH_MEAN_C;
			break;
		case 1:
			this.adaptiveMethod = Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C;
		}
	}
	
	public void setParam3() {
		switch(Helper.getRandomInRange(0, 1))
		{
		case 0:
			this.thresholdType = Imgproc.THRESH_BINARY_INV;
			break;
		case 1:
			this.thresholdType = Imgproc.THRESH_BINARY;
		}
	}
	
	@Override
	public void initialize() {
		super.initialize();
		this.noOfParameters = 3;
		this.blockSize = this.setParam1();
		this.setParam2();
		this.setParam3();
	}

	@Override
	public void mutate() {
		switch(Helper.getRandomInRange(1, this.noOfParameters)) {
		case 1:
			int tempVal;
			do {
				tempVal = this.setParam1();
			}while(tempVal == this.blockSize);
			this.blockSize = tempVal;
			break;
		case 2:
			if(this.adaptiveMethod == Imgproc.ADAPTIVE_THRESH_MEAN_C)
				this.adaptiveMethod = Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C;
			else
				this.adaptiveMethod = Imgproc.ADAPTIVE_THRESH_MEAN_C;
			break;
		case 3:
			if(this.thresholdType == Imgproc.THRESH_BINARY)
				this.thresholdType = Imgproc.THRESH_BINARY_INV;
			else
				this.thresholdType = Imgproc.THRESH_BINARY;
		}
	}

	@Override
	public void makeTransform() {
		Imgproc.adaptiveThreshold(src, dst, maxValue, adaptiveMethod,
				thresholdType, blockSize, mean);
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