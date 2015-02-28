package com.hawk.transform;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.hawk.GA.Helper;

public class HarrisCornStrenTransform extends Transform {
	private static final long serialVersionUID = 4291473411174554108L;
	private int blockSize;
	private int ksize;
	private double k;

	public int getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public int getKsize() {
		return ksize;
	}

	public void setKsize(int ksize) {
		this.ksize = ksize;
	}

	public double getK() {
		return k;
	}

	public void setK(double k) {
		this.k = k;
	}

	public HarrisCornStrenTransform() {
		super();
	}

	public HarrisCornStrenTransform(Mat src, Mat dst, int blockSize, int ksize,
			double k) {
		super(src, dst);
		this.blockSize = blockSize;
		this.ksize = ksize;
		this.k = k;
	}
	
	public int setParam1() {
		int temp = Helper.getRandomInRange(1, 7);
		if(temp % 2 == 0)
			temp = 3;
		return temp;
	}

	public int setParam2() {
		return Helper.getRandomInRange(2, 4);
	}
	
	public void mutate() {
		int tempVal;
		switch(Helper.getRandomInRange(1, this.noOfParameters)) {
		case 1:
			do {
				tempVal = this.setParam1();
			}while(tempVal == this.blockSize);
			this.blockSize = tempVal;
			break;
		case 2:
			do {
				tempVal = this.setParam1();
			}while(tempVal == this.ksize);
			this.ksize = tempVal;
		}
}
	
	@Override
	public void initialize() {
		super.initialize();
		this.noOfParameters = 2;
		this.k = 0.04;
		this.ksize = this.setParam1();
		this.blockSize = this.setParam2(); 
	}

	@Override
	public void makeTransform() {
		Imgproc.cornerHarris(src, dst, blockSize, ksize, k);
		dst.convertTo(dst, CvType.CV_8UC1);
	}

}
