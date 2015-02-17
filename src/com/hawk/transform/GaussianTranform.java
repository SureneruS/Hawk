package com.hawk.transform;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

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
		this.size = 3;
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
