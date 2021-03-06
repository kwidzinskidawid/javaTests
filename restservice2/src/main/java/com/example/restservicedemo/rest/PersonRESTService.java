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

import com.example.restservicedemo.domain.Person;
import com.example.restservicedemo.service.PersonManager;

@Path("person")
public class PersonRESTService {	
	
	private PersonManager pm = new PersonManager();
	
	@GET
	@Path("/{personId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Person getPerson(@PathParam("personId") Long id){
		Person p = pm.getPerson(id);
		return p;
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Person> getPersons() {
		List<Person> persons = pm.getAllPersons();
		return persons;
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addPerson(Person person){
		pm.addPerson(person);
		System.out.println("New person added.");
		return Response.status(201).entity("Person").build(); 
	}
	
	
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_HTML)
	public String test(){
		return "REST API /person is running";
	}
	
	@DELETE
	public Response clearPersons(){
		pm.clearPersons();
		return Response.status(200).build();
	}
	
	@DELETE
	@Path("/delete/{personId}")
	public Response deletePerson(@PathParam("personId") Long id) {
		pm.deletePerson(id);
		return Response.status(200).build();
	}
	
	@DELETE
	@Path("/drop")
	public Response deletePersons(){
		pm.deletePersons();
		return Response.status(200).build();
	}

	@GET
	@Path("/init")
	public Response init(){
		return Response.status(200).build();
	}
}
