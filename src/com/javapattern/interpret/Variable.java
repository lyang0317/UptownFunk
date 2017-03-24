package com.javapattern.interpret;

public class Variable implements Expression {

	String name;
	public Variable(String name) {
		this.name = name;
	}
	
	@Override
	public boolean interpret(Context context) {
		return context.lookup(this);
	}

}
