package com.javapattern.interpret;

public class Or implements Expression {

	private Expression left,right;
    public Or(Expression left , Expression right){
        this.left = left;
        this.right = right;
    }
    
    public boolean interpret(Context ctx) {
        return left.interpret(ctx) || right.interpret(ctx);
    }
    
}
