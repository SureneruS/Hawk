package com.hawk.transform;


import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class HistogramEquTranform extends Transform {
	public HistogramEquTranform() {
		// TODO Auto-generated constructor stub
		super();
	}
	public HistogramEquTranform(Mat src, Mat dst) {
		super(src, dst);
	}
	@Override
	public void makeTransform() {
		// TODO Auto-generated method stub
		Imgproc.equalizeHist(src, dst);
	}
	
}
