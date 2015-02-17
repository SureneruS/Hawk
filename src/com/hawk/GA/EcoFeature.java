package com.hawk.GA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import com.atul.JavaOpenCV.Imshow;
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

public class EcoFeature {
	public Rect region;
	public List<Transform> transforms = new ArrayList<Transform>();
	public Perceptron perceptron;

	boolean isWorking;

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
		// int vectorSize = t.get(t.size() - 1).
		// this.perceptron = new Perceptron();
		this.isWorking = true;
	}

	private static List<Transform> generateTransforms() {
		List<Transform> transformList = new ArrayList<Transform>();
		Random randomGenerator = new Random();
		int numberOfTransforms = Helper.getRandomInRange(
				GAControls.MinimumTransforms, GAControls.MaximumTransforms,
				randomGenerator);

		for (int i = 0; i < numberOfTransforms; i++) {
			try {

				boolean isFinalTransform = addTransform(transformList,
						randomGenerator);
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
	private static boolean addTransform(List<Transform> transformList,
			Random randomGenerator) throws Exception {
		/*
		 * transformList.add(new ErodeTransform()); transformList.add(new
		 * LapcianTransform()); transformList.add(new ErodeTransform()); return
		 * true;
		 */
		int randomTransform = Helper.getRandomInRange(0,
				TransID.values().length - 1, randomGenerator);
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
		case HarisCorner:
			// transformList.add(new HarisCornStrenTransform());
			break;
		case HistEqu:
			transformList.add(new HistogramEquTranform());
			break;
		case HoughCircle:
			// transformList.add(new HoughCircleTransform());
			return true;
		case HoughLine:
			// transformList.add(new HoughLineTransform());
			return true;
		case Integral:
			// transformList.add(new IntegralImgTransform());
			return true;
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
		Random randomGenerator = new Random();
		boolean ok = false;
		while (!ok) {
			try {
				int startX = Helper.getRandomInRange(0,
						GAControls.TrainingImageWidth, randomGenerator);
				int startY = Helper.getRandomInRange(0,
						GAControls.TrainingImageHeight, randomGenerator);
				int width = Helper
						.getRandomInRange(GAControls.MinimumRegionWidth,
								GAControls.TrainingImageWidth - startX,
								randomGenerator);
				int height = Helper.getRandomInRange(
						GAControls.MinimumRegionHeight,
						GAControls.TrainingImageHeight - startY,
						randomGenerator);
				ok = true;
				return new Rect(startX, startY, width, height);
			} catch (Exception e) {
				ok = false;
				e.printStackTrace();
				System.out.println("Trying again...");
			}
		}

		return null;
	}

	public Mat applyFeature(Mat image) {
		Mat roi = new Mat();
		new Mat(image, region).copyTo(roi);
		Imshow window1 = new Imshow("Test");
		window1.showImage(roi);
		int i = 0;
		try {
			for (Transform transform : transforms) {
				transform.setSrc(roi);
				transform.setDst(roi);
				transform.makeTransform();

				Imshow window = new Imshow("Test" + i++);
				window.showImage(roi);

			}

			roi = Helper.linearize(roi);

			if (perceptron == null) {
				perceptron = new Perceptron(roi.cols());
			}
		} catch (Exception e) {
			e.printStackTrace();
			isWorking = false;
		}

		return roi;
	}

	public void printFeature() {
		System.out.println(this.region.x);
		System.out.println(this.region.y);
		System.out.println(this.region.width);
		System.out.println(this.region.height);
		System.out.println();

		for (Transform t : transforms) {
			System.out.println(t.getClass().toString());
		}
	}
}
