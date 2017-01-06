package com.test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class ForClass {

	public static void main(String[] args) {
		int a = (int) 1.4;
		System.out.println(a);
		System.out.println(new Object().hashCode());
		System.out.println(new Random().nextInt(3));
	}
	
	private static int testRandom(int n) {
		return Math.abs(new Random().nextInt()) % n;
	}
	
	private void testforeach() {
		Collection<Face> faces = Arrays.asList(Face.values());
		for (Iterator<Face> i = faces.iterator(); i.hasNext();) {
			for (Iterator<Face> j = faces.iterator(); j.hasNext();) {
				System.out.println(i.next()+" -- "+j.next());
			}
		}
		
		for (Face face1 : faces) {
			for (Face face2 : faces) {
				System.out.println(face1 + " -- " +face2);
			}
		}

	}
	
	enum Face {
		ONE,TWO,THREE,FOUR,FIVE,SIX
	}
}
