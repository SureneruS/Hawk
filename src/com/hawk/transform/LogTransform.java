package com.hawk.transform;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.hawk.GA.Helper;

public class LogTransform extends Transform {
	public LogTransform() {
		// TODO Auto-generated constructor stub
		super();
	}

	public LogTransform(Mat src, Mat dst) {
		super(src, dst);
	}

	@Override
	public void makeTransform() {
		// TODO Auto-generated method stub
		src.convertTo(dst, CvType.CV_32F);
		Core.log(dst, dst);
		Core.multiply(dst, new Scalar(20), dst);
		// Core.convertScaleAbs(dst,dst);
		dst.convertTo(dst, CvType.CV_8UC1);
	}

}
