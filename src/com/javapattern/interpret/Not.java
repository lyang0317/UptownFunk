package com.javapattern.interpret;

public class Not implements Expression {
	
	private Expression exp;
    public Not(Expression exp){
        this.exp = exp;
    }
	    
    public boolean interpret(Context ctx) {
        return !exp.interpret(ctx);
    }
}
