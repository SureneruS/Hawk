package com.hawk.transform;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.hawk.GA.Helper;
import com.hawk.transform.constant.TransConstants;

public class LapcianTransform extends Transform {
	private static final long serialVersionUID = 4502348388144666886L;
	private int ksize;

	public int getKsize() {
		return ksize;
	}

	public void setKsize(int ksize) {
		this.ksize = ksize;
	}

	public LapcianTransform() {
		super();
	}

	public LapcianTransform(Mat src, Mat dst, int ksize) {
		super(src, dst);
		this.ksize = ksize;
	}
	
	public int setParam1() {
		int temp = Helper.getRandomInRange(1, 7);
		if(temp % 2 == 0)
			temp--;
		return temp;
	}

	@Override
	public void mutate() {
		int tempVal;
		do {
			tempVal = this.setParam1();
		}while(tempVal == this.ksize);
		this.ksize = tempVal;
	}

	@Override
	public void initialize() {
		this.noOfParameters = 1;
		this.ksize = this.setParam1();
	}

	@Override
	public void makeTransform() {
		Imgproc.Laplacian(src, dst, TransConstants.LAPCIAN_DDEPTH, ksize,
				TransConstants.LAPCIAN_SCALE, TransConstants.LAPCIAN_DELTA);
	}
}
