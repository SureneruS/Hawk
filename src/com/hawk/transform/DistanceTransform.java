package com.hawk.transform;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.hawk.GA.Helper;

public class DistanceTransform extends Transform {
	private int distance;
	private int maskSize;

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getMaskSize() {
		return maskSize;
	}

	public void setMaskSize(int maskSize) {
		this.maskSize = maskSize;
	}

	public DistanceTransform() {
		super();
	}

	public DistanceTransform(Mat src, Mat dst, int distance, int maskSize) {
		super(src, dst);
		this.distance = distance;
		this.maskSize = maskSize;
	}
	
	public void setParam1() {
		switch(Helper.getRandomInRange(0, 2)) {
		case 0:
			this.distance = Imgproc.CV_DIST_L1;
			this.maskSize = Imgproc.CV_DIST_MASK_3;
			break;
		case 1:
			this.distance = Imgproc.CV_DIST_C;
			this.maskSize = Imgproc.CV_DIST_MASK_3;
			break;
		case 2:
			this.distance = Imgproc.CV_DIST_L2;
			switch(Helper.getRandomInRange(0, 2)) {
			case 0:
				this.maskSize = Imgproc.CV_DIST_MASK_3;
				break;
			case 1:
				this.maskSize = Imgproc.CV_DIST_MASK_5;
				break;
			case 2:
				this.maskSize = Imgproc.CV_DIST_MASK_PRECISE;
			}
		}
	}
	
	@Override
	public void mutate() {
		if(this.distance == Imgproc.CV_DIST_L1)
			this.distance = Imgproc.CV_DIST_C;
		else if(this.distance == Imgproc.CV_DIST_C)
			this.distance = Imgproc.CV_DIST_L1;
		else
			this.distance = Imgproc.CV_DIST_L1;
			this.maskSize = Imgproc.CV_DIST_MASK_3;
	}

	@Override
	public void initialize() {
		this.noOfParameters = 1;
		this.setParam1();
	}

	@Override
	public void makeTransform() {
		Imgproc.distanceTransform(src, dst, distance, maskSize);
		dst.convertTo(dst, CvType.CV_8UC1);
	}

}
