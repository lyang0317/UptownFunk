package com.javapattern.interpret;

public class Constant implements Expression {

	private boolean value;
	public Constant(boolean value){
        this.value = value;
    }
	
	@Override
	public boolean interpret(Context context) {
		return value;
	}

}
