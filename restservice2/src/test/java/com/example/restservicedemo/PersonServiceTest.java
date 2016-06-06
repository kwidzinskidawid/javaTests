package com.example.restservicedemo;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.example.restservicedemo.domain.Person;
import com.jayway.restassured.RestAssured;

public class PersonServiceTest {

	private static IDatabaseConnection connection ;
	private static IDatabaseTester databaseTester;
	
	@BeforeClass
    public static void setUp() throws Exception{
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/restservicedemo/api"; 
		
		get("/person/init");
		get("/car/init");
    }
	
	@Before
	public void cleanInsert() throws Exception{
		Connection jdbcConnection;
		jdbcConnection = DriverManager.getConnection(
				"jdbc:hsqldb:hsql://localhost/workdb", "sa", "");
		connection = new DatabaseConnection(jdbcConnection);
		
		databaseTester = new JdbcDatabaseTester(
				"org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://localhost/workdb", "sa", "");
		IDataSet dataSet = new FlatXmlDataSetBuilder().build(
				new FileInputStream(new File("src/test/resources/fullData.xml")));
		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		databaseTester.setDataSet(dataSet);
		databaseTester.onSetup();
	}
	
	@Test
	public void addPersons() throws Exception{		
		Person person = new Person(4, "Stasiek", 1921);
		Person person2 = new Person(5, "Henio", 1991);

		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(person).
	    when().	     
	    	post("/person/").
	    then().assertThat().statusCode(201);
		
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(person2).
	    when().	     
	    	post("/person/").
	    then().assertThat().statusCode(201);
				
		IDataSet dbDataSet = connection.createDataSet();
		ITable actualTable = dbDataSet.getTable("PERSON");
		
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
				new File("src/test/resources/addData.xml"));
		ITable expectedTable = expectedDataSet.getTable("PERSON");
		
		Assertion.assertEquals(expectedTable, actualTable);
	}
	
	@Test
	public void getCars() throws Exception {
				
		List<Person> persons = Arrays.asList(get("/person/").as(Person[].class));
		assertEquals(3, persons.size());
		
		IDataSet dbDataSet = connection.createDataSet();
		ITable actualTable = dbDataSet.getTable("PERSON");
		
		assertTrue(persons.get(2).getYob() == (int)actualTable.getValue(2, "yob"));
	}
	
	@Test
	public void deleteCar() throws Exception {
		delete("/person/delete/2").then().assertThat().statusCode(200);
		
		IDataSet dbDataSet = connection.createDataSet();
		ITable actualTable = dbDataSet.getTable("PERSON");
		
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
				new File("src/test/resources/removeData.xml"));
		ITable expectedTable = expectedDataSet.getTable("PERSON");
		
		Assertion.assertEquals(expectedTable, actualTable);
	}
	
	@Test
	public void getCar() throws Exception {
				
		Person person = get("/person/1").as(Person.class);
		
		IDataSet dbDataSet = connection.createDataSet();
		ITable actualTable = dbDataSet.getTable("PERSON");
		
		assertEquals(person.getFirstName(),(String)actualTable.getValue(0, "name"));
	}
	
	@AfterClass
	public static void tearDown() throws Exception{
		databaseTester.onTearDown();
	}

}
