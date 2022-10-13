package com.example.week4.controller;


import com.example.week4.model.Team;
import com.example.week4.services.SuperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    private SuperRepository superRepository;

    @Autowired
    public MyController(SuperRepository superRepository) {
        this.superRepository = superRepository;
    }

    @RequestMapping(value = "/populate", method = RequestMethod.GET)
    public ResponseEntity populate() {
        superRepository.populate();
        return new ResponseEntity(
//                superRepository.getTeams(),
                HttpStatus.OK);
    }


}
