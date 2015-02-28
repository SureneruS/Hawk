package com.hawk.transform;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class HistogramEquTranform extends Transform {
	public HistogramEquTranform() {
		super();
	}

	public HistogramEquTranform(Mat src, Mat dst) {
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
		Imgproc.equalizeHist(src, dst);
	}

}
