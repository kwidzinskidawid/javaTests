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

import com.example.restservicedemo.domain.Car;
import com.example.restservicedemo.domain.Person;
import com.jayway.restassured.RestAssured;

public class CarServiceTest {

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
	public void addCars() throws Exception{
		Person owner = new Person();
		owner.setId(1);
		
		Car aCar = new Car(3, "Opel", "Astra", 2011, owner);
		given().
		       contentType("application/json").
		       body(aCar).
		when().	     
			post("/car/").
		then().
			assertThat().statusCode(201);
		
		IDataSet dbDataSet = connection.createDataSet();
		ITable actualTable = dbDataSet.getTable("CAR");
		
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
				new File("src/test/resources/addData.xml"));
		ITable expectedTable = expectedDataSet.getTable("CAR");
		
		Assertion.assertEquals(expectedTable, actualTable);
	}
	
	@Test
	public void getCarsByOwner() throws Exception{
		
		List<Car> cars = Arrays.asList(get("/car/byOwner/3").as(Car[].class));
		

		IDataSet dataSet = new FlatXmlDataSetBuilder().build(
				new FileInputStream(new File("src/test/resources/fullData.xml")));
		ITable carTable = dataSet.getTable("CAR");
		ITable personTable = dataSet.getTable("PERSON");
		
		assertEquals(1, cars.size());
		
		int owner_id = Integer.parseInt((String) carTable.getValue(0, "o_id" )) - 1;
		assertEquals(cars.get(0).getOwner().getFirstName(), (String) personTable.getValue(owner_id, "name") );
		assertTrue(cars.get(0).getOwner().getYob() == Integer.parseInt((String) personTable.getValue(owner_id, "yob")));
	}
	
	
	@Test
	public void getCars() throws Exception{

		
		List<Car> cars = Arrays.asList(get("/car/").as(Car[].class));
		assertEquals(2, cars.size());
		
		IDataSet dbDataSet = connection.createDataSet();
		ITable actualTable = dbDataSet.getTable("CAR");
		
		assertEquals(cars.get(0).getModel(),(String)actualTable.getValue(0, "model"));
	}
	
	@Test
	public void deleteCar() throws Exception {
		delete("/car/delete/2").then().assertThat().statusCode(200);
		
		IDataSet dbDataSet = connection.createDataSet();
		ITable actualTable = dbDataSet.getTable("CAR");
		
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
				new File("src/test/resources/removeData.xml"));
		ITable expectedTable = expectedDataSet.getTable("CAR");
		
		Assertion.assertEquals(expectedTable, actualTable);
	}
	
	@AfterClass
	public static void tearDown() throws Exception{
		databaseTester.onTearDown();
	}
}
