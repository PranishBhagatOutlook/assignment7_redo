package com.example.week4.services;

import com.example.week4.errors.CustomException;
import com.example.week4.model.Contest;
import com.example.week4.repository.ContestRepository;
import com.example.week4.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import java.util.stream.Collectors;


@Service
public class ContestService {

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    TeamRepository teamRepository;


    public Contest findContestById(@PathVariable("id") Long id) {
        Contest contest = contestRepository.findById(id).get();
        if (contest == null) {
          throw new CustomException("the provided contestId does not exist");
        }
        return contest;
    }


    public Contest createContest(Contest contest) {
        return contestRepository.save(contest);
    }

    public Integer exhaustedCapacity(Long contestId) {
        List<String> teamsInContest = teamRepository.findByContestId(contestId).stream().map(i -> i.getName()).collect(Collectors.toList());
        return teamsInContest.size();
    }

    public void checkAvailableCapacity(Long contestId) {
        Integer availableContestCapacity =
                findContestById(contestId).getCapacity() - exhaustedCapacity(contestId);
        if (availableContestCapacity == 0) {
            throw new CustomException("Contest capacity limit reached.");
        }
    }

    public void checkContestExists(Contest contest) {
        Contest oldContest = contestRepository.findContestById(contest.getId());
        if (oldContest == null) {
            throw new CustomException("The provided contest in the request body does not exist in the system." +
                    "Please provide valid contestId");
        }
    }

    public Contest checkContestIdExists(Long contestId) {
       Contest contest = contestRepository.findContestById(contestId);
        if (contest == null) {
            throw new CustomException("The provided contestId in the api does not exist in the system." +
                    "Please provide valid contestId");
        }
        return contest;
    }

    public void checkWritableFalse(Contest contest) {
        if (contest.getWritable() == false) {
            throw new CustomException("The already existing contest's writable flag is set to false. " +
                    "You cannot edit this contest");
        }
    }

    public Contest editContest(Contest contest) {
        checkContestExists(contest);

        Contest oldContest = findContestById(contest.getId());

        checkWritableFalse(oldContest);

        Contest newContest = new Contest();
        if (oldContest.getWritable() == true) {
            newContest.setId(contest.getId());
            newContest = contestRepository.save(contest);
        } else {
            return null;
        }
        return newContest;
    }


    public Contest setEditable(@PathVariable("contestId") Long contestId) {
        checkContestIdExists(contestId);
        Contest contest = findContestById(contestId);
        contest.setWritable(true);
        contestRepository.save(contest);
        return contest;
    }

    public Contest setReadOnly(@PathVariable("contestId") Long contestId) {
        checkContestIdExists(contestId);
        Contest contest = findContestById(contestId);
        contest.setWritable(false);
        contestRepository.save(contest);
        return contest;
    }

    public Contest editName(@PathVariable("contestId") Long contestId,
                            @RequestParam("name") String name) {
        Contest contest = findContestById(contestId);
        if (contest.getName() == null) {
            throw new CustomException("The contest does not exist");
        }
        if (contest.getWritable() == false) {
            throw new CustomException("The contest is not editable. Please set the writable flag to true");
        }
        contest.setName(name);
        contestRepository.save(contest);
        return contest;
    }

    public Contest saveContest(Contest contest){
        return contestRepository.save(contest);
    }


}
