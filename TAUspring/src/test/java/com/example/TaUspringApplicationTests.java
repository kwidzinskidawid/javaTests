package com.example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.example.domain.Person;
import com.example.service.PersonRepository;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TaUspringApplication.class)
@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class})
@DatabaseSetup("dataSetup.xml")
public class TaUspringApplicationTests {

	@Autowired
	private PersonRepository personRepository;
	
	@Test
	public void testGetAll() {
		List<Person> persons = (List<Person>) personRepository.findAll();
		assertThat(persons).hasSize(3);
	}
	
	@Test
	@ExpectedDatabase(value="addData.xml", table="person")
	public void addPersons() throws Exception{		
		Person person = new Person(4L, "Stasiek", 1921);
		Person person2 = new Person(5L, "Henio", 1991);

		personRepository.save(person);
		personRepository.save(person2);
	}
	
	@Test
	public void getPersons() throws Exception {
				
		List<Person> persons = (List<Person>) personRepository.findAll();
		assertEquals(3, persons.size());

		assertTrue(persons.get(2).getYob() == 1981);
	}
	
	@Test
	@ExpectedDatabase(value="removeData.xml", table="person")
	public void deletePersons() throws Exception {
		personRepository.delete((long) 2);
		personRepository.deleteAll();

	}
	
	@Test
	public void getPerson() throws Exception {
				
		Person person = personRepository.findOne(1L);
		
		assertEquals("David", person.getFirstName());
	}
	


}
