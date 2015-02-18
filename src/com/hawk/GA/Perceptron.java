package com.hawk.GA;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;

public class Perceptron {
	public List<Double> weights;
	double learningRate;

	public Perceptron(int cols) {
		this.weights = new ArrayList<Double>();
		// The first element is bias
		this.weights.add(new Double(1));

		// Initialize all weights to 1
		for (int i = 0; i < cols; i++) {
			this.weights.add(new Double(1));
		}

		this.learningRate = GAControls.PerceptronLearningRate;
	}

	public void train(Mat featureVector, boolean expectedOutput) {
		boolean currentOutput = this.classify(featureVector);
	}

	public boolean classify(Mat featureVector) {
		System.out.println(featureVector.channels());
		Double output = weights.get(0);
		for(int i = 1; i <= weights.size(); i++) {
			output += weights.get(i) * featureVector.get(0, i)[0];
		}
		
		return false;
	}

}
