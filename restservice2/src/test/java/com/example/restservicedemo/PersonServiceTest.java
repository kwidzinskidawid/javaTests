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

import javax.ws.rs.core.MediaType;

import org.junit.BeforeClass;
import org.junit.Test;

import com.example.restservicedemo.domain.Person;
import com.jayway.restassured.RestAssured;

public class PersonServiceTest {
	
	private static final String PERSON_FIRST_NAME = "David";
	
	@BeforeClass
    public static void setUp(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/restservicedemo/api";   	
		
		delete("/car/drop").then().assertThat().statusCode(200);
		delete("/person/drop").then().assertThat().statusCode(200);
		
    }
	
	@Test
	public void addPersons(){		
		Person person = new Person(1, PERSON_FIRST_NAME, 1976);
		
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(person).
	    when().	     
	    	post("/person/").
	    then().assertThat().statusCode(201);
				
		Person rPerson = get("/person/1").as(Person.class);
		
		assertThat(PERSON_FIRST_NAME, equalToIgnoringCase(rPerson.getFirstName()));
		
	}
	
	@Test
	public void getCars() {
		Person person = new Person(1, PERSON_FIRST_NAME, 1976);
		Person person2 = new Person(2, "Andrzej", 1979);
		Person person3 = new Person(3, "Mark", 1981);
		given().
		       contentType("application/json").
		       body(person).
		when().	     
			post("/person/").
		then().
			assertThat().statusCode(201);
		
		given().
	       contentType("application/json").
	       body(person2).
		when().	     
			post("/person/").
		then().
			assertThat().statusCode(201);
		
		given().
		       contentType("application/json").
		       body(person3).
		when().	     
			post("/person/").
		then().
			assertThat().statusCode(201);
		
		List<Person> persons = Arrays.asList(get("/person/").as(Person[].class));
		assertEquals(3, persons.size());
		assertTrue(persons.get(2).getYob() == person3.getYob());
	}
	


}
