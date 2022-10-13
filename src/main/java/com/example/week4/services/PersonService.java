package com.example.week4.services;

import com.example.week4.model.Person;
import com.example.week4.model.Team;
import com.example.week4.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person findPersonById(@PathVariable("id") Long id) {
        Person person = personRepository.findPersonById(id);
        if (person != null) {
            return person;
        }
        return null;
    }


    public Person findPersonByEmail(String email) {
        return personRepository.findPersonByEmail(email);

    }


    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    public List<Person> getPersonbyEmail(String email) {
        return personRepository.getPersonByEmail(email);
    }

    public List<Person> findPersonByTeamsIn(Set<Team> teams) {
        return personRepository.findPersonByTeamsIn(teams);
    }
}
