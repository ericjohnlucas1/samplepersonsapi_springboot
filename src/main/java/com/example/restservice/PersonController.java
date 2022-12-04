package com.example.restservice;

import java.util.concurrent.atomic.AtomicLong;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

	private final AtomicLong counter = new AtomicLong();
	private HashMap<Long, Person> mockDatabase = new HashMap<>();

	@PostMapping("/persons")
	public ResponseEntity<Object> newPerson(@RequestBody Person newPerson) {
		long id = counter.incrementAndGet();
		newPerson.setId(id);
		if (newPerson.getName()==null){
			Error e = new Error("A valid name is required in the request body", 400);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
		Person person = new Person(id, newPerson.getName(), newPerson.getAge());
		mockDatabase.put(id,person);
		return ResponseEntity.status(HttpStatus.CREATED).body(newPerson);
	}

	@GetMapping("/persons/{id}")
	public ResponseEntity<Object> fetchPerson(@PathVariable Long id){
		if (mockDatabase.containsKey((id))){
			return ResponseEntity.status(HttpStatus.OK).body(mockDatabase.get(id));
		}
		Error e = new Error("No such person with the specified ID exists", 404);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);	}

	@GetMapping("/persons")
	public ResponseEntity<Object> listPerson(@RequestParam("youngerthan") Optional<Integer> youngerthan){
		ArrayList<Person> persons =  new ArrayList<>(mockDatabase.values());
		if (youngerthan.isPresent()) persons.removeIf(person -> person.getAge()>=youngerthan.get());
		return ResponseEntity.status(HttpStatus.OK).body(persons);
	}
}
