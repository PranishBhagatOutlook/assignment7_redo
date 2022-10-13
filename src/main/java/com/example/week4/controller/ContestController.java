package com.example.week4.controller;

import com.example.week4.model.Contest;
import com.example.week4.services.ContestService;
import com.example.week4.services.SuperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contest")
public class ContestController {
    @Autowired
    private SuperRepository superRepository;

    @Autowired
    private ContestService contestService;

    @RequestMapping(value = "/contests", method = RequestMethod.GET)
    public ResponseEntity<Contest> getContests() {
        return new ResponseEntity(
                superRepository.getContests(),
                HttpStatus.OK);
    }

    @GetMapping("/contestId={contestId}")
    public Contest findContestById(@PathVariable("contestId") Long contestId) {
        return contestService.findContestById(contestId);
    }


    @PostMapping("/createContest")
    @ResponseStatus(HttpStatus.CREATED)
    public Contest createContest(@RequestBody Contest contest) {
        return contestService.createContest(contest);
    }


    @PutMapping(path = "/editContest")
    public Contest editContest( @RequestBody Contest contest) {
        return contestService.editContest(contest);
    }

    @PutMapping(path = "/setEditable/contestId={contestId}")
    public Contest setEditable(
            @PathVariable("contestId") Long contestId
    ) {
        return contestService.setEditable(contestId);
    }


    @PutMapping(path = "/setReadOnly/contestId={contestId}")
    public Contest setReadOnly(
            @PathVariable("contestId") Long contestId) {
        return contestService.setReadOnly(contestId);
    }




}
