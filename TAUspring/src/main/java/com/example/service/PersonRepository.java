package com.example.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.domain.Person;

@RepositoryRestResource
public interface PersonRepository extends CrudRepository<Person, Long> {

}
