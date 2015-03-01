package com.hawk.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.hawk.GA.EcoFeature;

public class ManageFeatures {

	public static void store(List<EcoFeature> resultFeatures, String path) {
		// TODO Auto-generated method stub
		int count = 0;
		for(EcoFeature e : resultFeatures) {
			try {
				count++;
				FileOutputStream fos = new FileOutputStream(path + count + ".ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(e);
				oos.close();
				System.out.println("Feature Saved to local storage");
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	public List<EcoFeature> load(String path) {
		File directory = new File(path);
		List<EcoFeature> EcoFeatures = new ArrayList<EcoFeature>();
		File[] fileList = directory.listFiles();
		for (File file : fileList) {
			try { 
				if (file.isFile()) {
					String filePath = file.getAbsolutePath();
					FileInputStream fin = new FileInputStream(filePath);
					ObjectInputStream ois = new ObjectInputStream(fin);
					EcoFeature feature = (EcoFeature) ois.readObject();
					EcoFeatures.add(feature);
					ois.close();
				}
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		return EcoFeatures;
	}
}