package com.hawk.transform;


import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class HistogramTransform extends Transform {

	private Mat src;
	private Mat dst;
	
	@Override
	public void makeTransform() {
		// TODO Auto-generated method stub
	//	Imgproc.calcHist(images, channels, mask, hist, histSize, ranges, accumulate);
	}
}
