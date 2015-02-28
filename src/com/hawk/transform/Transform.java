package com.hawk.transform;

import java.io.Serializable;

import org.opencv.core.Mat;

public abstract class Transform implements Serializable{
	private static final long serialVersionUID = 2660086647243884988L;
	protected transient Mat src;
	protected transient Mat dst;
	public int noOfParameters;

	public Mat getSrc() {
		return src;
	}

	public void setSrc(Mat src) {
		this.src = src;
	}

	public Mat getDst() {
		return dst;
	}

	public void setDst(Mat dst) {
		this.dst = dst;
	}

	public void initialize() {

	}
	public void mutate()	{

	}

	public Transform(Mat src, Mat dst) {
		this.src = src;
		this.dst = dst;
	}

	public Transform() {
	}

	public void makeTransform() {

	}

}
