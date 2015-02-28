package com.hawk.transform;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import com.hawk.transform.constant.TransConstants;

public class NormalizeTransform extends Transform {
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
		// TODO Auto-generated constructor stub
		super();
	}

	public NormalizeTransform(Mat src, Mat dst, double alpha, int norm,
			int depth) {
		super(src, dst);
		this.alpha = alpha;
		this.norm = norm;
		this.depth = depth;
	}

	@Override
	public void initialize() {
		super.initialize();
		this.alpha = 50;
		this.norm = Core.NORM_MINMAX;
		this.depth = -1;
	}

	@Override
	public void makeTransform() {
		Core.normalize(src, dst, alpha, TransConstants.NORMALIZE_BETA, norm,
				depth);
		dst.convertTo(dst, CvType.CV_8UC1);
	}

}
