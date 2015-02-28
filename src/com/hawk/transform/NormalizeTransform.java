package com.hawk.transform;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import com.hawk.GA.Helper;
import com.hawk.transform.constant.TransConstants;

public class NormalizeTransform extends Transform {
	private static final long serialVersionUID = -1421366177369032237L;
	private double alpha;
	private int norm;
	private int depth;

	public int getNorm() {
		return norm;
	}

	public void setNorm(int norm) {
		this.norm = norm;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public NormalizeTransform() {
		super();
	}

	public NormalizeTransform(Mat src, Mat dst, double alpha, int norm,
			int depth) {
		super(src, dst);
		this.alpha = alpha;
		this.norm = norm;
		this.depth = depth;
	}
	
	public int setParam1() {
		return Helper.getRandomInRange(10, 256);
	}

	@Override
	public void initialize() {
		this.noOfParameters = 1;
		this.depth = -1;
		this.alpha = this.setParam1();
		this.norm = Core.NORM_MINMAX;
	}
	
	public void mutate() {
		int tempVal;
		do {
			tempVal = this.setParam1();
		}while(tempVal == this.alpha);
		this.alpha = tempVal;
	};

	@Override
	public void makeTransform() {
		Core.normalize(src, dst, alpha, TransConstants.NORMALIZE_BETA, norm,
				depth);
		System.out.println(dst.dump());
		dst.convertTo(dst, CvType.CV_8UC1);
	}

}
