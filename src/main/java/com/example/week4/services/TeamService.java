package com.example.week4.services;

import com.example.week4.errors.CustomException;
import com.example.week4.model.Contest;
import com.example.week4.model.Person;
import com.example.week4.model.Team;
import com.example.week4.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ContestService contestService;

    @Autowired
    private PersonService personService;

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    public void checkCoach(Team team) {
        if (team.getCoach() == null) {
            throw new CustomException("There is no coach information in the request body");
        }
    }

    public void checkNullTeamMembers(Team team) {
        if (team.getContestant() == null) {
            throw new CustomException("There is no contestant information in the request body");
        }
    }

    public void checkThreeTeamMembers(Team team) {
        if (team.getContestant().size() != 3) {
            throw new CustomException("There must be 3 team members in the request body");
        }
    }

    public void checkTeamMemberAge(Team team) {
        List<Person> teamMembers = getAllTeamMember(team);
        List<Date> birthDate = teamMembers.stream().map(i -> i.getBirthDate()).collect(Collectors.toList());
        List<Integer> actualAge = new ArrayList();
        for (Date d : birthDate) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            actualAge.add(2022 - cal.get(Calendar.YEAR));
        }
        for (int i = 0; i < actualAge.size(); i++) {
            if (actualAge.get(i) > 24) {
                throw new CustomException("Cannot register. Team member has age greater than 24.");
            }
        }
    }

    public List<Person> getAllTeamMember(Team team) {
        List<Person> teamMembers = new ArrayList<Person>(team.getContestant());
        return teamMembers;
    }

    public List<Person> getAllMember(Team team) {
        List<Person> allMembers = new ArrayList<Person>(team.getContestant());
        allMembers.add(team.getCoach());
        return allMembers;
    }


    public void checkDistinctMembers(Team team) {
        List<Person> allMembers = getAllMember(team);
        List allMembersEmail = new ArrayList<>();
        for (Person p : allMembers) {
            allMembersEmail.add(p.getEmail());
        }
        Set uniqueMembers = new HashSet(allMembersEmail);
        if (allMembers.size() != uniqueMembers.size()) {
            throw new CustomException("Team must have 3 unique team members with 3 different email");
        }
    }

    public Set<Team> findTeamsByContestId(Long contestId) {
        return teamRepository.findTeamsByContestId(contestId);
    }

    public void checkTeamMembersInOtherTeam(Team team, Long contestId) {
        List<Person> contestants = getAllTeamMember(team);
        Set<Team> teamList = findTeamsByContestId(contestId);

        for (Team t : teamList) {
            for (Person personInContest : t.getContestant()) {
                for (Person personInTeam : contestants) {
                    if (personInContest.getEmail().equals(personInTeam.getEmail())) {
                        throw new CustomException("Same person cannot be in multiple teams in the same contest");
                    }
                }
            }
        }
    }

    public void checkTeamRegistration(Team team, Long contestId) {
        checkCoach(team);
        checkNullTeamMembers(team);
        checkThreeTeamMembers(team);
        checkDistinctMembers(team);
        checkTeamMemberAge(team);
        checkTeamMembersInOtherTeam(team, contestId);
    }

    public Team registerTeam(Team team, Contest contest) {
        Team registeredTeam = new Team();
        registeredTeam.setName(team.getName());
        registeredTeam.setRank(team.getRank());
        registeredTeam.setState(Team.State.PENDING);
        for (Person p : team.getContestant()) {
            personService.createPerson(p); // create person entity
            registeredTeam.getContestant().add(p); // add person to the team
        }
        personService.createPerson(team.getCoach()); // create coach entity
        registeredTeam.setCoach(team.getCoach());
        registeredTeam.setContest(contest);
        return registeredTeam;
    }

    public Team contestRegister(Team team, Long contestId) {
        Contest contest = contestService.checkContestIdExists(contestId);
        contestService.checkWritableFalse(contest);
        contestService.checkAvailableCapacity(contestId);

        checkTeamRegistration(team, contestId);

        Team registeredTeam = registerTeam(team, contest);
        teamRepository.save(registeredTeam);
        contestService.saveContest(contest);
        return registeredTeam;
    }



    public Team checkTeamIdExists(Long teamId) {
        Team team = teamRepository.findTeamById(teamId);
        if (team == null) {
            throw new CustomException("The provided teamId in the api does not exist in the system." +
                    "Please provide valid teamId");
        }
        return team;
    }

    public void checkNullRank(Team team) {
        if (team.getRank() == null) {
            throw new CustomException("Cannot promote. Null rank");
        }
    }

    public void checkRangeRank(Team team) {
        if (!(team.getRank() > 0 && team.getRank() < 6)) {
            throw new CustomException("Cannot promote. Rank is not in the range of 1-5." + " The teamrank is " + team.getRank());
        }
    }

    public void checkSuperContest(Contest contest) {
        if (contest.getSuperContestId() == null) {
            throw new CustomException("Cannot promote. No superContest associated with this contest.");
        }
    }

    public void checkTeamPromotion(Team team, Contest contest) {
        checkNullRank(team);
        checkRangeRank(team);
        checkSuperContest(contest);
        contestService.checkAvailableCapacity(contest.getSuperContestId());
    }

    public Team promoteTeam(Long teamId, Long contestId) throws Exception {
        Team team = checkTeamIdExists(teamId);
        Contest contest = contestService.checkContestIdExists(contestId);

        checkTeamPromotion(team, contest);
        checkTeamRegistration(team, contest.getSuperContestId());

        Team promotedTeam = promotedTeam(team, contest);
        teamRepository.save(promotedTeam);
        return promotedTeam;
    }

    public Team promotedTeam(Team team, Contest contest) {
        Team promotedTeam = new Team();
        promotedTeam.setName(team.getName());
        promotedTeam.setRank(team.getRank());
        promotedTeam.setState(team.getState());
        promotedTeam.setPromotedTeamId(team.getId());
        promotedTeam.setCoach(team.getCoach());
        promotedTeam.setContest(contestService.findContestById(contest.getSuperContestId()));
        promotedTeam.setPromotedFromContestId(contest.getId());
        for (Person p : team.getContestant()) {
            promotedTeam.getContestant().add(p);
        }
        return promotedTeam;
    }
}


