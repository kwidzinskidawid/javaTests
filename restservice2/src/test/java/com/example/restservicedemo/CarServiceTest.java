package com.example.restservicedemo;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;

import javax.ws.rs.core.MediaType;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.example.restservicedemo.domain.Car;
import com.example.restservicedemo.domain.Person;
import com.jayway.restassured.RestAssured;

public class CarServiceTest {
	
	private static final String CAR_MODEL = "Corsa";
	
	private static Person testPerson;
	
	@BeforeClass
	public static void setUp(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/restservicedemo/api";
		
		testPerson = new Person(1, "David", 1993);
	}
	
	@Ignore
	@Test
	public void addCars(){
		
		delete("/car/").then().assertThat().statusCode(200);
		
		Car aCar = new Car(1, "Ford", "Fiesta", 2011, testPerson);
		given().
		       contentType(MediaType.APPLICATION_JSON).
		       body(aCar).
		when().	     
			post("/car/").
		then().
			assertThat().statusCode(201);
		
		Car car = get("/car/1").as(Car.class);
		
		assertThat(CAR_MODEL, equalToIgnoringCase(car.getModel()));
	}
	

}
