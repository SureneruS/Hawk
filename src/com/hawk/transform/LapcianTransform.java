package com.hawk.transform;

import java.util.Random;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.hawk.GA.Helper;
import com.hawk.transform.constant.TransConstants;

public class LapcianTransform extends Transform {
	private int ksize;

	public int getKsize() {
		return ksize;
	}

	public void setKsize(int ksize) {
		this.ksize = ksize;
	}

	public LapcianTransform() {
		// TODO Auto-generated constructor stub
		super();
	}

	public LapcianTransform(Mat src, Mat dst, int ksize) {
		super(src, dst);
		this.ksize = ksize;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		Random randomGenerator = new Random();
		this.ksize = Helper.getRandomInRange(1, 7, randomGenerator);
		if (this.ksize % 2 == 0)
			this.ksize--;
		// super.initialize();
	}

	@Override
	public void makeTransform() {
		// TODO Auto-generated method stub
		Imgproc.Laplacian(src, dst, TransConstants.LAPCIAN_DDEPTH, ksize,
				TransConstants.LAPCIAN_SCALE, TransConstants.LAPCIAN_DELTA);
	}
}
