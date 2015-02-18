package com.hawk.transform;

import java.util.Random;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.hawk.GA.Helper;
import com.hawk.transform.constant.TransConstants;

public class GaussianTranform extends Transform {
	private int size;

	public GaussianTranform() {
		// TODO Auto-generated constructor stub
		super();
	}

	public GaussianTranform(Mat src, Mat dst, int size) {
		super(src, dst);
		this.size = size;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		// super.initialize();
		Random randomGenerator = new Random();
		this.size = Helper.getRandomInRange(1, 9, randomGenerator);
		if (this.size % 2 == 0)
			this.size--;
	}

	@Override
	public void makeTransform() {
		// TODO Auto-generated method stub

		Imgproc.GaussianBlur(src, dst, new Size(size, size),
				TransConstants.GAUSSIAN_SIGMA);

	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
