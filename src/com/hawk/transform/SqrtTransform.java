package com.hawk.transform;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

public class SqrtTransform extends Transform {

	public SqrtTransform() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SqrtTransform(Mat src, Mat dst) {
		super(src, dst);
	}

	@Override
	public void makeTransform() {
		// TODO Auto-generated method stub
		src.convertTo(dst, CvType.CV_32F);
		Core.sqrt(dst, dst);
		Core.multiply(dst, new Scalar(10), dst);
		// Core.convertScaleAbs(dst,dst);
		dst.convertTo(dst, CvType.CV_8UC1);
	}

}
