package com.hawk.transform;

import java.util.Random;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.hawk.GA.Helper;
import com.hawk.transform.constant.TransConstants;

public class SobelTransform extends Transform {

	private int ddepth;
	private int dx;
	private int dy;
	private int ksize;

	public SobelTransform() {
		super();
	}

	public SobelTransform(Mat src, Mat dst, int ddepth, int dx, int dy,
			int ksize) {
		super(src, dst);
		this.ddepth = ddepth;
		this.dx = dx;
		this.dy = dy;
		this.ksize = ksize;

	}

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	public int getKsize() {
		return ksize;
	}

	public void setKsize(int ksize) {
		this.ksize = ksize;
	}

	public int getDdepth() {
		return ddepth;
	}

	public void setDdepth(int ddepth) {
		this.ddepth = ddepth;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		super.initialize();
		Random randomGenerator = new Random();
		this.dx = Helper.getRandomInRange(0, 2, randomGenerator);
		this.dy = Helper.getRandomInRange(0, 2, randomGenerator);
		this.ksize = Helper.getRandomInRange(1, 7, randomGenerator);
		if(this.dx + this.dy == 0)
			this.dx=1;
		if (this.ksize % 2 == 0)
			this.ksize=3;
	}

	@Override
	public void makeTransform() {
		// TODO Auto-generated method stub
		this.ddepth = src.depth();
		Imgproc.Sobel(src, dst, ddepth, dx, dy, ksize,
				TransConstants.SOBEL_SCALE, TransConstants.SOBEL_DELTA);
		// dst.convertTo(dst, CvType.CV_8U,1.0/255.0);
	}
}
