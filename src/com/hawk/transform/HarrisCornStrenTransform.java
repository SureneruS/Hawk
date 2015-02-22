package com.hawk.transform;

import java.util.Random;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.hawk.GA.Helper;

public class HarrisCornStrenTransform extends Transform {
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
		// TODO Auto-generated constructor stub
	}

	public HarrisCornStrenTransform(Mat src, Mat dst, int blockSize, int ksize,
			double k) {
		super(src, dst);
		this.blockSize = blockSize;
		this.ksize = ksize;
		this.k = k;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		super.initialize();
		Random randomGenerator = new Random();
		this.ksize = Helper.getRandomInRange(1, 7, randomGenerator);
		if (this.ksize % 2 == 0)
			this.ksize=3;
		this.blockSize = Helper.getRandomInRange(2, 4, randomGenerator);
		this.k = 0.04;
	}

	@Override
	public void makeTransform() {
		// TODO Auto-generated method stub
		// super.makeTransform();
		Imgproc.cornerHarris(src, dst, blockSize, ksize, k);
		dst.convertTo(dst, CvType.CV_8UC1);
	}

}
