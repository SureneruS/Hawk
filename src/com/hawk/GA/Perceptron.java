package com.hawk.GA;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;

public class Perceptron {
	List<Double> weights;
	double learningRate;
	
	public Perceptron(int cols) {
		// TODO Auto-generated constructor stub
		this.weights = new ArrayList<Double>();
		// The first element is bias
		this.weights.add(new Double(1));
		
		// Initialize all weights to 1
		for(int i = 0; i < cols; i++) {
			this.weights.add(new Double(1));
		}
		
		this.learningRate = GAControls.PerceptronLearningRate;
	} 
	
	public void train(Mat featureVector, boolean expectedOutput) {
		boolean currentOutput = this.classify(featureVector);
	}

	public boolean classify(Mat featureVector) {
		
		return false;
	}
	
	
	
}
