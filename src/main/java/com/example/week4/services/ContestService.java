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
    TeamRepository teamRepository;
    @Autowired
    private ContestRepository contestRepository;

    public Contest createContest(Contest contest) {
        return contestRepository.save(contest);
    }

    public void checkAvailableCapacity(Long contestId) {
        Integer availableContestCapacity =
                findContestById(contestId).getCapacity() - exhaustedCapacity(contestId);
        if (availableContestCapacity == 0) {
            throw new CustomException("Contest capacity limit reached.");
        }
    }

    public Contest findContestById(Long contestId) {
        return checkContestIdExists(contestId);
    }

    public Integer exhaustedCapacity(Long contestId) {
        List<String> teamsInContest = teamRepository.findByContestId(contestId).stream().map(i -> i.getName()).collect(Collectors.toList());
        return teamsInContest.size();
    }

    public Contest checkContestIdExists(Long contestId) {
        Contest contest = contestRepository.findContestById(contestId);
        if (contest == null) {
            throw new CustomException("The provided contestId " + contestId + " in the api does not exist in the system." +
                    "Please provide valid contestId");
        }
        return contest;
    }

    public Contest editContest(Contest contestRequestBody) {
        Contest contest = checkContestExists(contestRequestBody);
        if (checkWritableFalse(contest) == true) {
            throw new CustomException("The contest with contestId " + contest.getId() + " is not writable. " +
                    "Please set the writable as true.");
        }
        contest = contestRequestBody;
        contestRepository.save(contest);
        return contest;
    }

    public Contest checkContestExists(Contest contest) {
        Contest oldContest = contestRepository.findContestById(contest.getId());
        if (oldContest == null) {
            throw new CustomException("The provided contestid " + contest.getId() + " in the request body does not exist in the system." +
                    "Please provide valid contestId");
        }
        return oldContest;
    }

    public Boolean checkWritableFalse(Contest contest) {
        if (contest.getWritable() == false) {
            return true;
        }
        return false;
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

    public Contest saveContest(Contest contest) {
        return contestRepository.save(contest);
    }


}
