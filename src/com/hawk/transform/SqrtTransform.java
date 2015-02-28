package com.hawk.transform;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

public class SqrtTransform extends Transform {
	private static final long serialVersionUID = 4521003607164709789L;

	public SqrtTransform() {
		super();
	}

	public SqrtTransform(Mat src, Mat dst) {
		super(src, dst);
	}

	@Override
	public void initialize() {
		super.initialize();
	}

	@Override
	public void mutate() {
	}

	@Override
	public void makeTransform() {
		src.convertTo(dst, CvType.CV_32F);
		Core.sqrt(dst, dst);
		Core.multiply(dst, new Scalar(10), dst);
		// Core.convertScaleAbs(dst,dst);
		dst.convertTo(dst, CvType.CV_8UC1);
	}

}
