package com.hawk.transform;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

public class LogTransform extends Transform {
	private static final long serialVersionUID = 1303934558767793027L;

	public LogTransform() {
		super();
	}

	public LogTransform(Mat src, Mat dst) {
		super(src, dst);
	}

	@Override
	public void initialize() {
		super.initialize();
	}

	@Override
	public void mutate() {
		super.mutate();
	}

	@Override
	public void makeTransform() {
		src.convertTo(dst, CvType.CV_32F);
		Core.log(dst, dst);
		Core.multiply(dst, new Scalar(20), dst);
		dst.convertTo(dst, CvType.CV_8UC1);
	}

}
