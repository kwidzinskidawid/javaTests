package com.example.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.domain.Car;

@RepositoryRestResource
public interface CarRepository extends CrudRepository<Car, Long> {

}