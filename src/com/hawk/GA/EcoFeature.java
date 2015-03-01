package com.hawk.GA;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

import com.hawk.transform.AdaptiveThresTransform;
import com.hawk.transform.CannyTransform;
import com.hawk.transform.DilateTransform;
import com.hawk.transform.DistanceTransform;
import com.hawk.transform.ErodeTransform;
import com.hawk.transform.GaborTransform;
import com.hawk.transform.GaussianTranform;
import com.hawk.transform.HistogramEquTranform;
import com.hawk.transform.LapcianTransform;
import com.hawk.transform.LogTransform;
import com.hawk.transform.MedianBlurTransform;
import com.hawk.transform.NormalizeTransform;
import com.hawk.transform.SobelTransform;
import com.hawk.transform.SqrtTransform;
import com.hawk.transform.TransID;
import com.hawk.transform.Transform;

public class EcoFeature implements Serializable, Comparable<EcoFeature>{
	private static final long serialVersionUID = 7673222297785642456L;
	boolean isWorking;
	boolean isTrained;
	private transient Rect region;
	private List<Transform> transforms = new ArrayList<Transform>();
	private Perceptron perceptron;
	
	public Rect getRegion() {
		return region;
	}

	public void setRegion(Rect region) {
		this.region = region;
	}

	public List<Transform> getTransforms() {
		return transforms;
	}

	public void setTransforms(List<Transform> transforms) {
		this.transforms = transforms;
	}
	
	public void appendTransforms(List<Transform> transforms) {
		this.transforms.addAll(transforms);
	}


	public EcoFeature() {
		this(generateRegion());
	}

	public EcoFeature(Rect r) {
		this(r, generateTransforms());
	}

	public EcoFeature(List<Transform> t) {
		this(generateRegion(), t);
	}

	public EcoFeature(Rect r, List<Transform> t) {
		this.region = r;
		this.transforms = t;
		this.isWorking = true;
		this.isTrained = false;
	}

	public void resetPerceptron() {
		this.isTrained = false;
		this.perceptron = null;
	}
	
	private static List<Transform> generateTransforms() {
		List<Transform> transformList = new ArrayList<Transform>();
		int numberOfTransforms = Helper.getRandomInRange(
				GAControls.MinimumTransforms, GAControls.MaximumTransforms);

		for (int i = 0; i < numberOfTransforms; i++) {
			try {

				boolean isFinalTransform = addTransform(transformList);
				if (isFinalTransform) {
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				i--;
			}
		}

		for (Transform t : transformList) {
			t.initialize();
		}
		return transformList;
	}

	/*
	 * Returns if the chosen transform must be final or not
	 */
	private static boolean addTransform(List<Transform> transformList) throws Exception {
		/*
		 * transformList.add(new ErodeTransform()); transformList.add(new
		 * LapcianTransform()); transformList.add(new ErodeTransform()); return
		 * true;
		 */
		int randomTransform = Helper.getRandomInRange(0,
				TransID.values().length - 1);
		// System.out.println("randT: " + randomTransform + " len: " +
		// TransID.values().length);
		TransID transId = TransID.values()[randomTransform];
		switch (transId) {
		case AdaptiveThres:
			transformList.add(new AdaptiveThresTransform());
			break;
		case Canny:
			transformList.add(new CannyTransform());
			break;
		case Dilate:
			transformList.add(new DilateTransform());
			break;
		case Distance:
			transformList.add(new DistanceTransform());
			break;
		case Erode:
			transformList.add(new ErodeTransform());
			break;
		case Gabor:
			transformList.add(new GaborTransform());
			break;
		case Gaussian:
			transformList.add(new GaussianTranform());
			break;
//		case HarisCorner:
//			 transformList.add(new HarisCornStrenTransform());
//			break;
		case HistEqu:
			transformList.add(new HistogramEquTranform());
			break;
//		case HoughCircle:
//			 transformList.add(new HoughCircleTransform());
//			return true;
//		case HoughLine:
//			 transformList.add(new HoughLineTransform());
//			return true;
//		case Integral:
//			 transformList.add(new IntegralImgTransform());
//			return true;
		case Lapcian:
			transformList.add(new LapcianTransform());
			break;
		case Log:
			transformList.add(new LogTransform());
			break;
		case MedianBlur:
			transformList.add(new MedianBlurTransform());
			break;
		case Normalize:
			transformList.add(new NormalizeTransform());
			break;
		case Sobel:
			transformList.add(new SobelTransform());
			break;
		case Sqrt:
			transformList.add(new SqrtTransform());
			break;
		default:
			throw new Exception("How is this even possible!");
		}
		return false;
	}

	private static Rect generateRegion() {
		boolean ok = false;
		while (!ok) {
			try {
				int startX = Helper.getRandomInRange(0,
						GAControls.TrainingImageWidth);
				int startY = Helper.getRandomInRange(0,
						GAControls.TrainingImageHeight);
				int width = Helper
						.getRandomInRange(GAControls.MinimumRegionWidth,
								GAControls.TrainingImageWidth - startX);
				int height = Helper.getRandomInRange(
						GAControls.MinimumRegionHeight,
						GAControls.TrainingImageHeight - startY);
				ok = true;
				return new Rect(startX, startY, width, height);
			} catch (Exception e) {
				ok = false;
				//e.printStackTrace();
				System.out.println("Trying again...");
			}
		}

		return null;
	}

	public Mat applyFeature(Mat image) {
		Mat roi = new Mat();
		new Mat(image, region).copyTo(roi);
		try {
			for (Transform transform : transforms) {
				transform.setSrc(roi);
				transform.setDst(roi);
				transform.makeTransform();
			}

			roi = Helper.linearize(roi);

			if (perceptron == null) {
				perceptron = new Perceptron(roi.cols(), 0.1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			isWorking = false;
		}

		return roi;
	}

	public void trainWith(Mat trainingImage, boolean expectedOutput) {
		Mat featureVector = applyFeature(trainingImage);
		if(isWorking) {
			perceptron.train(featureVector, expectedOutput);
		}
	}

	public int calculateFitnessScore() {
		return perceptron.getFitness();
	}

	public void updateErrorWith(Mat trainingImage, boolean expectedOutput) {
		Mat featureVector = applyFeature(trainingImage);
		perceptron.updateErrorRate(featureVector, expectedOutput);
	}
	
	@Override
	public String toString() {
		StringBuilder strBuff = new StringBuilder();
		strBuff.append("\nisWorking: " + isWorking);
		strBuff.append("\n\nSubregion:");
		strBuff.append("\n--------- ");
		strBuff.append("\n     x     : " + this.region.x);
		strBuff.append("\n     y     : " + this.region.y);
		strBuff.append("\n     width : " + this.region.width);
		strBuff.append("\n     height: " + this.region.height);
		strBuff.append("\n\nTransforms:");
		strBuff.append("\n---------- ");
		if(transforms != null) {
			for (Transform t : transforms) {
				strBuff.append("\n    " + t.getClass().toString());
			}
		}
		
		strBuff.append("\n\nPerceptron: ");
		strBuff.append("\n--------- ");
		if(perceptron != null) {
			strBuff.append(perceptron.toString());
		}
		strBuff.append("\n");
		return strBuff.toString();
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
		out.writeBoolean(this.isWorking);
        out.writeInt(this.region.x);
        out.writeInt(this.region.y);
        out.writeInt(this.region.width);
        out.writeInt(this.region.height);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    	this.isWorking = in.readBoolean();
        this.region = new Rect(in.readInt(), in.readInt(), in.readInt(), in.readInt());
    }

    @Override
	public int compareTo(EcoFeature ecoFeature) {
		int thisFitness = this.calculateFitnessScore();
		int otherFitness = ecoFeature.calculateFitnessScore();
		
		return (thisFitness - otherFitness);
	}
}
