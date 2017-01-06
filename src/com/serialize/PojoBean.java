package com.serialize;

import org.msgpack.annotation.Message;

@Message
public class PojoBean {

	private String id;
	private String name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "PojoBean [id=" + id + ", name=" + name + "]";
	}
	
}
