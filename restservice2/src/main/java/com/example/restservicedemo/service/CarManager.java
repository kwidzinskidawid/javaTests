package com.example.restservicedemo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.restservicedemo.domain.Car;
import com.example.restservicedemo.domain.Person;

public class CarManager {
	private Connection connection;

	private static final String URL = "jdbc:hsqldb:hsql://localhost/workdb";
	private static final String CREATE_TABLE_CAR = "CREATE TABLE Car(id bigint GENERATED BY DEFAULT AS IDENTITY,"
			+ " make varchar(20), model varchar(20), yop integer, o_id bigint,"
			+ "FOREIGN KEY (o_id) REFERENCES Person (id), PRIMARY KEY (id))";

	private PreparedStatement addCarStmt;
	private PreparedStatement deleteAllCarsStmt;
	private PreparedStatement getAllCarsStmt;
	private PreparedStatement getCarByIdStmt;
	private PreparedStatement getCarsByOwnerIdStmt;

	private Statement statement;
	
	
	public CarManager() {

		try {
			connection = DriverManager.getConnection(URL);
			statement = connection.createStatement();

			ResultSet rs = connection.getMetaData().getTables(null, null, null,
					null);
			boolean tableExists = false;
			while (rs.next()) {
				if ("Car".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;
				}
			}

			if (!tableExists)
				statement.executeUpdate(CREATE_TABLE_CAR);

			addCarStmt = connection
					.prepareStatement("INSERT INTO Car (id, make, model, yop, o_id) VALUES (?, ?, ?, ?, ?)");
			deleteAllCarsStmt = connection
					.prepareStatement("DELETE FROM Car");
			getAllCarsStmt = connection
					.prepareStatement("SELECT id, make, model, yop, o_id FROM Car");
			getCarByIdStmt = connection
					.prepareStatement("SELECT id, make, model, yop, o_id FROM Car where id = ?");
			getCarsByOwnerIdStmt = connection
					.prepareStatement("SELECT id, make, model, yop, o_id FROM Car where o_id = ?");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	Connection getConnection() {
		return connection;
	}

	public void clearCars() {
		try {
			deleteAllCarsStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Car> getCarsByOwner(Person owner) {
		List<Car> cars = new ArrayList<Car>();
		try {
			getCarsByOwnerIdStmt.setLong(1, owner.getId());
			
			ResultSet rs = getCarsByOwnerIdStmt.executeQuery();

			while (rs.next()) {
				Car c = new Car();
				c.setId(rs.getLong("id"));
				c.setMake(rs.getString("make"));
				c.setModel(rs.getString("model"));
				c.setYop(rs.getInt("yop"));	
				c.setOwner(owner);
				cars.add(c);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cars;
	}

	public int addCar(Car car) {
		int count = 0;
		try {
			addCarStmt.setLong(1, car.getId());
			addCarStmt.setString(2, car.getMake());
			addCarStmt.setString(3, car.getModel());
			addCarStmt.setInt(4, car.getYop());
			addCarStmt.setLong(5, car.getOwner().getId());

			count = addCarStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<Car> getAllCars() {
		List<Car> cars = new ArrayList<Car>();

		try {
			ResultSet rs = getAllCarsStmt.executeQuery();

			while (rs.next()) {
				Car c = new Car();
				c.setId(rs.getLong("id"));
				c.setMake(rs.getString("make"));
				c.setModel(rs.getString("model"));
				c.setYop(rs.getInt("yop"));
				Person owner = new Person();
				owner.setId(rs.getLong("o_id"));	
				c.setOwner(owner);
				cars.add(c);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cars;
	}

	public Car getCar(Long id) {
		Car c = new Car();
		try {
			getCarByIdStmt.setLong(1, id);
			ResultSet rs = getCarByIdStmt.executeQuery();

			while (rs.next()) {
				c.setId(rs.getLong("id"));
				c.setMake(rs.getString("make"));
				c.setModel(rs.getString("model"));
				c.setYop(rs.getInt("yop"));
				Person owner = new Person();
				owner.setId(rs.getLong("o_id"));	
				c.setOwner(owner);

				break;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return c;
	}
}