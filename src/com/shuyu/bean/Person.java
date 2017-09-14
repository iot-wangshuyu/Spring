package com.shuyu.bean;

public class Person {
	private Position position;
	private Address address;
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Person [position=" + position + ", City=" + address.getCity()+", Street=" + address.getStreet() + "]";
	}
	
	

}
