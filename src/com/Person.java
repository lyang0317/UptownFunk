package com;

import java.io.Serializable;

public class Person implements Serializable{

	private static final long serialVersionUID = 1L;
	private Lifter lifter;
	private int infloor;
	private int outfloor;
	
	public int getInfloor() {
		return infloor;
	}
	public void setInfloor(int infloor) {
		this.infloor = infloor;
	}
	public int getOutfloor() {
		return outfloor;
	}
	public void setOutfloor(int outfloor) {
		this.outfloor = outfloor;
	}
	public Lifter getLifter() {
		return lifter;
	}
	public void setLifter(Lifter lifter) {
		this.lifter = lifter;
	}
	
	public Person(Lifter lifter, int infloor, int outfloor) {
		this.lifter = lifter;
		this.infloor = infloor;
		this.outfloor = outfloor;
	}
	
	@Override
	public String toString() {
		return "Person [lifter=" + lifter + ", infloor=" + infloor
				+ ", outfloor=" + outfloor + "]";
	}

}
