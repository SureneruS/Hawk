package com.hawk.transform;

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

	public int setParam1() {
		int temp = Helper.getRandomInRange(1, 9);
		if(temp % 2 == 0)
			temp--;
		return temp;
	}

	@Override
	public void mutate() {
		int tempVal;
		do {
			tempVal = this.setParam1();
		}while(tempVal == this.size);
		this.size = tempVal;
	}

	@Override
	public void initialize() {
		this.noOfParameters = 1;
		this.size = this.setParam1();
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
