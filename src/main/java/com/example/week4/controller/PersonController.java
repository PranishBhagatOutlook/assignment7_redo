package com.example.week4.controller;

import com.example.week4.model.Person;
import com.example.week4.repository.PersonRepository;
import com.example.week4.services.PersonService;
import com.example.week4.services.SuperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private SuperRepository superRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public ResponseEntity<Person> getPersons() {
        return new ResponseEntity(
                superRepository.getPersons(),
                HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public Person findContestById(@PathVariable("id") Long id) {
        return personService.findPersonById(id);
    }

    @PostMapping("/createPerson")
    @ResponseStatus(HttpStatus.CREATED)
    public Person createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }



}
