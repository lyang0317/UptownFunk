package com.javapattern.interpret;

public class And implements Expression {

	private Expression left,right;
    public And(Expression left , Expression right){
        this.left = left;
        this.right = right;
    }
    
	@Override
	public boolean interpret(Context context) {
		return left.interpret(context) && right.interpret(context);
	}

}
