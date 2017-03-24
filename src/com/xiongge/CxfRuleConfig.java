package com.xiongge;


//@Target({ TYPE })
//@Retention(RUNTIME)
public interface CxfRuleConfig<P> {
	String url();
	Class<?> cxfInterface();
	String decide(P param);

}
