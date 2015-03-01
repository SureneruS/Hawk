package com.hawk.GA;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;

public class Perceptron implements Serializable{
	private static final long serialVersionUID = -3175503085945192829L;
	private Double bias;
	private List<Double> weights;
	private double learningRate;
	private int falsePositive, falseNegative, truePositive, trueNegative;
	private int fitness;

	public Perceptron(int cols, double rate) {
		this.weights = new ArrayList<Double>();
		this.bias = new Double(0);

		// Initialize all weights to 1
		for (int i = 0; i < cols; i++) {
			this.weights.add(new Double(0));
		}

		this.learningRate = rate;
		this.falsePositive = this.falseNegative = this.truePositive = this.trueNegative = 0;
	}

	public void train(Mat featureVector, int expectedOutput) {
		int currentOutput = this.classify(featureVector);
		int error = expectedOutput - currentOutput;
		//System.out.println(error);
		this.bias += learningRate * error;
		int i = 0;
		for (Double weight : this.weights) {
			///System.out.print(weight + " " );//+ featureVector.get(0, i)[0] + "--");
			Double newWeight = weight + learningRate * error * featureVector.get(0, i)[0];
			weights.set(i, newWeight);
			i++;
		}
		//System.out.println();
	}

	public int classify(Mat featureVector) {
		//System.out.println(featureVector.channels());
		Double output = bias;
		for (int i = 0; i < this.weights.size(); i++) {
			output += this.weights.get(i) * featureVector.get(0, i)[0];
		}

		return (output > 0 ? +1 : 0);
	}

	public void updateErrorRate(Mat featureVector, int expectedOutput) {
		int currentOutput = this.classify(featureVector);
		// int type = (currentOutput ? 1 : 0) + (expectedOutput ? 2 : 0);
		int type = currentOutput * 1 + expectedOutput * 2;
		/*
		 * type will have four values 0, 1, 2, 3. Each value will correspond to
		 * one of the four types fp, fn, tp and tn
		 */
		switch (type) {
		case 0:
			this.trueNegative++;
			break;
		case 1:
			falsePositive++;
			break;
		case 2:
			falseNegative++;
			break;
		case 3:
			truePositive++;
			break;
		}
	}

	public int getFitness() {
		updateFitness();
		return fitness;
	}

	private void updateFitness() {
		fitness = (int) (((truePositive * 500.0) / (truePositive + falseNegative)) +
						 ((trueNegative * 500.0) / (trueNegative + falsePositive)));
	}

	@Override
	public String toString() {
		StringBuilder strBuff = new StringBuilder();
		strBuff.append("\nweights: " + weights.size());
		strBuff.append("\nfitness: " + fitness);
		strBuff.append("\n" + truePositive + " " + falseNegative + " " + trueNegative + " " + falsePositive + "\n");
		return strBuff.toString();
	}
}
