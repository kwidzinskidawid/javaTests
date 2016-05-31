package com.example.restservicedemo;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.example.restservicedemo.domain.Car;
import com.example.restservicedemo.domain.Person;
import com.jayway.restassured.RestAssured;

public class CarServiceTest {
	
	private static final String CAR_MODEL = "Fiesta";
	
	private static Person testPerson;
	
	@BeforeClass
	public static void setUp(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/restservicedemo/api";
		
		testPerson = new Person(1, "David", 1993);
		
		delete("/car/drop").then().assertThat().statusCode(200);
		delete("/person/drop").then().assertThat().statusCode(200);
		
		given().
	       contentType("application/json").
	       body(testPerson).
		when().	     
			post("/person/").
		then().
			assertThat().statusCode(201);
			
	}
	
	@Test
	public void addCars(){
		
		delete("/car/").then().assertThat().statusCode(200);
		
		Car aCar = new Car(1, "Ford", CAR_MODEL, 2011, testPerson);
		given().
		       contentType("application/json").
		       body(aCar).
		when().	     
			post("/car/").
		then().
			assertThat().statusCode(201);
		
		Car car = get("/car/1").as(Car.class);
		
		assertThat(CAR_MODEL, equalToIgnoringCase(car.getModel()));
	}
	
	@Test
	public void getCarsByOwner() {
		delete("/car/").then().assertThat().statusCode(200);
		
		Car aCar = new Car(1, "Honda", "Civic", 2010, testPerson);
		Car aCar2 = new Car(2, "Honda", "Accord", 2011, testPerson);
		given().
		       contentType("application/json").
		       body(aCar).
		when().	     
			post("/car/").
		then().
			assertThat().statusCode(201);
		
		given().
	       contentType("application/json").
	       body(aCar2).
		when().	     
			post("/car/").
		then().
			assertThat().statusCode(201);
		
		
		List<Car> cars = Arrays.asList(get("/car/byOwner/1").as(Car[].class));
		
		assertEquals(2, cars.size());
		assertTrue(cars.get(0).getOwner().getFirstName().equals(testPerson.getFirstName()));
		assertTrue(cars.get(0).getMake().equals(aCar.getMake()));
	}
	
	
	@Test
	public void getCars() {
		delete("/car/").then().assertThat().statusCode(200);
		
		Car aCar = new Car(1, "Ford", "Focus", 2010, testPerson);
		Car aCar2 = new Car(2, "Ford", "Fiesta", 2011, testPerson);
		Car aCar3 = new Car(3, "Ford", "Escort", 2001, testPerson);
		given().
		       contentType("application/json").
		       body(aCar).
		when().	     
			post("/car/").
		then().
			assertThat().statusCode(201);
		
		given().
	       contentType("application/json").
	       body(aCar2).
		when().	     
			post("/car/").
		then().
			assertThat().statusCode(201);
		
		given().
		       contentType("application/json").
		       body(aCar3).
		when().	     
			post("/car/").
		then().
			assertThat().statusCode(201);
		
		List<Car> cars = Arrays.asList(get("/car/").as(Car[].class));
		assertEquals(3, cars.size());
		assertTrue(cars.get(2).getYop() == aCar3.getYop());
	}
	
}
