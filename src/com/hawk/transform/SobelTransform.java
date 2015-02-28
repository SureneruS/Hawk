package com.hawk.transform;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.hawk.GA.Helper;
import com.hawk.transform.constant.TransConstants;

public class SobelTransform extends Transform {
	private static final long serialVersionUID = -8062793946856949877L;
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
	
	public int setParam1() {
		int temp = Helper.getRandomInRange(1, 7);
		if(temp % 2 == 0)
			temp = 3;
		return temp;
	}
	
	public int setParam2() {
		return Helper.getRandomInRange(0, 2);
		
		
	}

	@Override
	public void initialize() {
		this.noOfParameters = 3;
		this.ksize = this.setParam1();
		this.dx = this.setParam2();
		this.dy = this.setParam2();
		if(this.dx + this.dy == 0)
			this.dx = 1;
	}
	
	public void mutuate() {
		switch(Helper.getRandomInRange(1, this.noOfParameters)) {
		case 1:
			int tempVal;
			do {
				tempVal = this.setParam1();
			}while(tempVal == this.ksize);
			this.ksize = tempVal;
			break;
		case 2:
			this.dx = (this.dx + 1) % 3;
			if(this.dx + this.dy == 0)
				this.dx = 1;
			break;
		case 3:
			this.dy = (this.dy + 1) % 3;
			if(this.dx + this.dy == 0)
				this.dy = 1;
			break;
		}
	}

	@Override
	public void makeTransform() {
		this.ddepth = src.depth();
		Imgproc.Sobel(src, dst, ddepth, dx, dy, ksize,
				TransConstants.SOBEL_SCALE, TransConstants.SOBEL_DELTA);
		// dst.convertTo(dst, CvType.CV_8U,1.0/255.0);
	}
}
