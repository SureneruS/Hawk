package com.hawk.GA.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.opencv.core.Core;

import com.hawk.GA.EcoFeature;
import com.hawk.GA.GeneticAlgorithm;

public class GeneticAlgorithmTest {

	@Test
	public void findNewCountTest() {
		GeneticAlgorithm ga = new GeneticAlgorithm(100, 100, 700, 10, 100);
		for(int i = 0; i < 100; i++) {
			int x = ga.findNewCount();
			assertTrue(x % 2 == 0);
		}		
	}
	
	@Test
	public void findFeaturesByCrossoverTest() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		GeneticAlgorithm ga = new GeneticAlgorithm(100, 100, 700, 10, 100);
		EcoFeature e1 = new EcoFeature();
		EcoFeature e2 = new EcoFeature();
		List<EcoFeature> p = new ArrayList<EcoFeature>();
		p.add(e1); p.add(e2);
		
		for(EcoFeature e : p) {
			System.out.println(e);
		}
		
		List<EcoFeature> c = ga.newFeaturesByCrossOver(p);
		
		for(EcoFeature e : c) {
			System.out.println(e);
		}
	}

}
