package com.example.domain;

import javax.persistence.*;

@Entity
public class Car {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long c_id;
	
	private String make;
	private String model;	
	private int yop;
	
	@ManyToOne
    @JoinColumn(name = "owner_id")
	private Person owner;
	
	public Car(Long c_id, String make, String model, int yop, Person person) {
		super();
		this.c_id = c_id;
		this.make = make;
		this.model = model;
		this.yop = yop;
		this.owner = person;
	}
	
	public Car() {
	}


	public Long getC_id() {
		return c_id;
	}
	public void setC_id(Long c_id) {
		this.c_id = c_id;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getYop() {
		return yop;
	}
	public void setYop(int yop) {
		this.yop = yop;
	}

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}
	
}
