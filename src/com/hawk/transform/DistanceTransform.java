package com.hawk.transform;

import java.util.Random;

import org.opencv.core.Core;
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
		// TODO Auto-generated constructor stub
	}

	public DistanceTransform(Mat src, Mat dst, int distance, int maskSize) {
		super(src, dst);
		this.distance = distance;
		this.maskSize = maskSize;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		// super.initialize();
		Random randomGenerator = new Random();
		int a = Helper.getRandomInRange(0, 2, randomGenerator);
		switch(a)
		{
		case 0:
			this.distance=Imgproc.CV_DIST_L1;
			this.maskSize = Imgproc.CV_DIST_MASK_3;
			break;
		case 1:
			this.distance = Imgproc.CV_DIST_C;
			this.maskSize = Imgproc.CV_DIST_MASK_3;
			break;
		case 2:
			this.distance = Imgproc.CV_DIST_L2;
			int b = Helper.getRandomInRange(0, 2, randomGenerator);
			switch(b)
			{
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
	public void makeTransform() {
		// Imgproc.cvtColor(src, dst,Imgproc.COLOR_RGB2GRAY);
		Imgproc.distanceTransform(src, dst, distance, maskSize);
		dst.convertTo(dst, CvType.CV_8UC1);

	}

}
