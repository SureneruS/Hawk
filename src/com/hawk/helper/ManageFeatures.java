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

	public static void store(List<EcoFeature> resultFeatures, String path, boolean flag) {
		System.out.println(path);
		File file = new File(path);
		if(!file.exists()) {
			if(file.mkdirs()) {
				System.out.println("ok");
			}
			else {
				System.out.println("no");
			}
		}
		int count;
		if(flag) {
			count = 0;
			for(File f : file.listFiles()) {
				f.delete();
			}
		}
		else {
			count = file.listFiles().length;
		}
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

	public static void store(EcoFeature resultFeature, String path) {
		System.out.println(path);
		File file = new File(path);
		if(!file.exists()) {
			if(file.mkdirs()) {
				System.out.println("ok");
			}
			else {
				System.out.println("no");
			}
		}	
		int count = file.listFiles().length;
		try {
			count++;
			FileOutputStream fos = new FileOutputStream(path + count + ".ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(resultFeature);
			oos.close();
			System.out.println("Feature Saved to local storage.!");
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public static List<EcoFeature> load(String path) {
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