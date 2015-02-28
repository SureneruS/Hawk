package com.hawk.transform;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class GaborTransform extends Transform {
	private static final long serialVersionUID = -6363224392897660681L;
	private int ddepth;
	private Size ksize;
	private double sigma;
	private double theta;
	private double gamma;
	private double lambda;
	private double psi;
	private int ktype;

	public Size getKsize() {
		return ksize;
	}

	public void setKsize(Size ksize) {
		this.ksize = ksize;
	}

	public double getSigma() {
		return sigma;
	}

	public void setSigma(double sigma) {
		this.sigma = sigma;
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}

	public double getGamma() {
		return gamma;
	}

	public void setGamma(double gamma) {
		this.gamma = gamma;
	}

	public double getLambda() {
		return lambda;
	}

	public void setLambda(double lambda) {
		this.lambda = lambda;
	}

	public double getPsi() {
		return psi;
	}

	public void setPsi(double psi) {
		this.psi = psi;
	}

	public int getKtype() {
		return ktype;
	}

	public void setKtype(int ktype) {
		this.ktype = ktype;
	}

	public int getDdepth() {
		return ddepth;
	}

	public void setDdepth(int ddepth) {
		this.ddepth = ddepth;
	}

	public GaborTransform() {
		super();
	}

	public GaborTransform(Mat src, Mat dst, int ddepth, Size ksize,
			double sigma, double theta, double lambd, double gamma, double psi,
			int ktype) {
		super(src, dst);
		this.ddepth = ddepth;
		this.ksize = ksize;
		this.sigma = sigma;
		this.theta = theta;
		this.lambda = lambd;
		this.psi = psi;
		this.ktype = ktype;
	}

	@Override
	public void initialize() {
		this.ddepth = CvType.CV_32F;
		Size s = new Size(3, 3);
		this.ksize = s;
		this.sigma = 1;
		this.theta = 0;
		this.lambda = 8;
		this.gamma = 0.5;
		this.psi = Math.toRadians(89);
		this.ktype = CvType.CV_32F;
	}

	@Override
	public void makeTransform() {
		Mat kernel = Imgproc.getGaborKernel(ksize, sigma, theta, lambda, gamma,
				psi, ktype);
		Imgproc.filter2D(src, dst, ddepth, kernel);
		dst.convertTo(dst, CvType.CV_8UC1, 25 / 255.0);
	}
}
