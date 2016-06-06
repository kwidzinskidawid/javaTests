package com.example.restservicedemo.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.restservicedemo.domain.Car;
import com.example.restservicedemo.domain.Person;
import com.example.restservicedemo.service.CarManager;
import com.example.restservicedemo.service.PersonManager;

@Path("car")
public class CarRESTService {	
	
	private CarManager cm = new CarManager();
	private PersonManager pm = new PersonManager();
	
	@GET
	@Path("/{carId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Car getCar(@PathParam("carId") Long id){
		Car c = cm.getCar(id);
		return c;
	}
	
	@GET
	@Path("/byOwner/{ownerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Car> getCarsByOwner(@PathParam("ownerId") Long id) {
		Person owner = pm.getPerson(id);
		
		List<Car> cars = cm.getCarsByOwner(owner);
		return cars;
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCar(Car car) {
		cm.addCar(car);
		System.out.println("New car added.");
		return Response.status(201).entity("Car").build(); 
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Car> getPersons() {
		List<Car> cars = cm.getAllCars();
		return cars;
	}

	@DELETE
	public Response clearCars(){
		cm.clearCars();
		return Response.status(200).build();
	}
	
	@DELETE
	@Path("/delete/{carId}")
	public Response deletePerson(@PathParam("carId") Long id) {
		cm.deleteCar(id);
		return Response.status(200).build();
	}
	
	@DELETE
	@Path("/drop")
	public Response deleteCars(){
		cm.deleteCars();
		return Response.status(200).build();
	}
	
	
	@GET
	@Path("/init")
	public Response init(){
		return Response.status(200).build();
	}
	
}

