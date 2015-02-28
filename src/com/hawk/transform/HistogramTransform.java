package com.hawk.transform;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgproc.Imgproc;

import java.util.List;


public class HistogramTransform extends Transform {
	private static final long serialVersionUID = 8580683169702093400L;

	@Override
	public void makeTransform() {
		List<Mat> imagesList = new ArrayList<>();
        imagesList.add(src);
        int channelArray[] = {0,1};
        MatOfInt channels = new MatOfInt(channelArray);
        MatOfInt histSize = new MatOfInt(256,256);
        MatOfFloat ranges = new MatOfFloat(0.0f,255.0f, 0.0f, 255.0f);
		Imgproc.calcHist(imagesList, channels, new Mat(), dst, histSize, ranges);
		System.out.println(dst.dump());
	}
}
