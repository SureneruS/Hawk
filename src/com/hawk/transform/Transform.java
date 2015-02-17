package com.hawk.transform;

import org.opencv.core.Mat;

public abstract class Transform {
	protected Mat src;
	protected Mat dst;
	
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
	public void initialize()
	{
		//System.out.println("XXX");
	}
	
	public Transform(Mat src, Mat dst) {
		this.src = src;
		this.dst = dst;
	}
	public Transform() {
	}
	public void makeTransform(){
		
	}

}
