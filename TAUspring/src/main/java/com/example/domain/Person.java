package com.example.domain;

import java.util.Set;

import javax.persistence.*;

@Entity
public class Person {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long p_id;
	
	private String firstName;
	private int yob;
	
	
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
	private Set<Car> cars;
	
	public Set<Car> getCars() {
		return cars;
	}

	public void setCars(Set<Car> cars) {
		this.cars = cars;
	}

	public Person() {
	}

	public Person(String firstName, int yob) {
		this.firstName = firstName;
		this.yob = yob;
	}
	
	public Person(Long id, String firstName, int yob) {
		super();
		this.p_id = id;
		this.firstName = firstName;
		this.yob = yob;
	}

	public Long getP_id() {
		return p_id;
	}

	public void setP_id(Long p_id) {
		this.p_id = p_id;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public int getYob() {
		return yob;
	}
	public void setYob(int yob) {
		this.yob = yob;
	}
}
