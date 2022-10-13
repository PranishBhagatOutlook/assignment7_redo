package com.example.week4.services;

import com.example.week4.errors.CustomException;
import com.example.week4.model.Contest;
import com.example.week4.model.Person;
import com.example.week4.model.Team;
import com.example.week4.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public Team findTeamById(Long teamId) {
        return checkTeamIdExists(teamId);
    }

    public Team checkTeamIdExists(Long teamId) {
        Team team = teamRepository.findTeamById(teamId);
        if (team == null) {
            throw new CustomException("The provided teamId in the api does not exist in the system." + "Please provide valid teamId");
        }
        return team;
    }

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    public List<Person> getAllMember(Team team) {
        List<Person> allMembers = new ArrayList<Person>(team.getContestant());
        allMembers.add(team.getCoach());
        return allMembers;
    }

    public Team contestRegister(Team team, Long contestId) {
        Contest contest = contestService.checkContestIdExists(contestId);
        if (contestService.checkWritableFalse(contest)) {

            throw new CustomException("This contest's writable flag is false. Registration is not allowed.");
        }
        ;
        contestService.checkAvailableCapacity(contest);

        Person coach = checkCoach(team);
        checkNullTeamMembers(team);
        checkThreeTeamMembers(team);
        List<Person> allTeamMembers = checkDistinctMembers(team);
        checkTeamMemberAge(team);
        checkTeamMembersInOtherTeam(team, contestId);

        Team registeredTeam = registerTeam(team, contest);
        //set coach
        if (coach == null) {
            registeredTeam.setCoach(personService.createPerson(team.getCoach()));
        } else {
            registeredTeam.setCoach(coach);
        }

        // set Contestant
        List<Person> personList = new ArrayList<>(team.getContestant());

        for (Person p : personList) {
            if (p.getId() != null) {
                for (Person p1 : allTeamMembers) {
                    if (p.getId().equals(p1.getId())) {
                        team.getContestant().remove(p);
                        team.getContestant().add(p1);
                    }
                }
            }
        }
        for (Person p : personList) {
            if (p.getId() == null) {
                Person p1 = personService.findPersonByEmail(p.getEmail());
                team.getContestant().remove(p);
                if (p1 == null) {
                    team.getContestant().add(personService.createPerson(p));
                } else {
                    team.getContestant().add(p1);
                }
            }
        }

        for (Person p : team.getContestant()) {
            registeredTeam.getContestant().add(p);
        }


        teamRepository.save(registeredTeam);
        contestService.saveContest(contest);
        return registeredTeam;
//        return null;
    }

    public Person checkCoach(Team team) {
        if (team.getCoach() == null) {
            throw new CustomException("There is no coach information in the request body");
        }
        if (team.getCoach().getId() == null) {
            if (team.getCoach().getEmail() == null) {
                throw new CustomException("The provided coach body must have an email");
            }
            Person coach = personService.findPersonByEmail(team.getCoach().getEmail());
            if (coach == null) {
                return null;
            }
            return coach;
        } else {
            Person coach = personService.findPersonById(team.getCoach().getId());
            if (coach == null) {
                throw new CustomException("The provided coachId does not exist in the system");
            } else {
                return coach;
            }
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

    public List<Person> checkDistinctMembers(Team team) {
        List<Person> allMembers = getAllTeamMember(team);
        List allMembersEmail = new ArrayList<>();
        for (Person p : allMembers) {
            allMembersEmail.add(p.getEmail());
        }
        Set uniqueMembers = new HashSet(allMembersEmail);
        if (allMembers.size() != uniqueMembers.size()) {
            throw new CustomException("Team must have 3 unique team members with 3 different email");
        }
        return allMembers;
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

//    public void checkTeamRegistration(Team team, Long contestId) {
//        Person coach = checkCoach(team);
//        System.out.println(coach);
//
//        checkThreeTeamMembers(team);
//        checkDistinctMembers(team);
//        checkTeamMemberAge(team);
//        checkTeamMembersInOtherTeam(team, contestId);
//    }

    public Team registerTeam(Team team, Contest contest) {
        Team registeredTeam = new Team();
        registeredTeam.setName(team.getName());
        registeredTeam.setRank(null);
        registeredTeam.setState(Team.State.PENDING);

        registeredTeam.setContest(contest);
        return registeredTeam;
    }

    public List<Person> getAllTeamMember(Team team) {
        List<Person> teamMembers = new ArrayList<Person>(team.getContestant());
        List<Person> teamMembers1 = new ArrayList<>();
        for (Person p : teamMembers) {
            if (p.getId() == null) {
                if (p.getEmail() == null) {
                    throw new CustomException("The provided team member body must have an email");
                }
                Person person = personService.findPersonByEmail(p.getEmail());
                if (person == null) {
                    teamMembers1.add(p);
                } else {
                    teamMembers1.add(person);
                }
            } else {
                Person person = personService.findPersonById(p.getId());
                if (person == null) {
                    throw new CustomException("The provided teamMemberId " + p.getId() + " does not exist in the system");
                } else {
                    teamMembers1.add(person);
                }
            }
        }
        return teamMembers1;
    }

    public Set<Team> findTeamsByContestId(Long contestId) {
        return teamRepository.findTeamsByContestId(contestId);
    }

    public void checkTeamPromotion(Team team, Contest contest) {
        checkNullRank(team);


    }

    public void checkNullRank(Team team) {
        if (team.getRank() == null) {
            throw new CustomException("Cannot promote. Null rank");
        }
    }

    public Team promoteTeam(Long teamId, Long contestId) throws Exception {
        Team team = checkTeamIdExists(teamId);
        Contest contest = contestService.checkContestIdExists(contestId);

        Team teamToPromote = teamRepository.findTeamByIdAndAndContest(teamId, contest);
        if (teamToPromote == null) {
            throw new CustomException("The provided teamId " + teamId + " is not in the given contest");
        }

        checkNullRank(team);
        checkRangeRank(team);
        Contest superContest = checkSuperContest(contest);

        if (superContest.getWritable() == false) {
            throw new CustomException("The supercontest is not editable. Please set the writable flag as true");
        }
        contestService.checkAvailableCapacity(superContest);
        checkTeamMembersInOtherTeam(team, contest.getSuperContestId());
        Team promotedTeam = promotedTeam(team, contest);
        teamRepository.save(promotedTeam);
        return promotedTeam;
    }

    public void checkRangeRank(Team team) {
        if (!(team.getRank() > 0 && team.getRank() < 6)) {
            throw new CustomException("Cannot promote. Rank is not in the range of 1-5." + " The teamrank is " + team.getRank());
        }
    }

    public Contest checkSuperContest(Contest contest) {
        if (contest.getSuperContestId() == null) {
            throw new CustomException("Cannot promote. No superContest associated with this contest.");
        }
        return contestService.findContestById(contest.getSuperContestId());
    }

    public Team promotedTeam(Team team, Contest contest) {
        Team promotedTeam = new Team();
        promotedTeam.setName(team.getName());
        promotedTeam.setRank(null);
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


    public Team updateRank(Long teamId, Long contestId, Integer rank) {
        Contest contest = contestService.checkContestIdExists(contestId);

        Team team = teamRepository.findTeamByIdAndAndContest(teamId, contest);
        if (team == null) {
            throw new CustomException("The provided teamId is not in the given contest with contestId");
        }

        if (contestService.checkWritableFalse(contest)) {
            throw new CustomException("The contest writable flag is false. Updating rank is not allowed");
        }
        ;

        team.setRank(rank);
        teamRepository.save(team);
        return team;
    }

    public void deleteTeam(Long teamId, Long contestId) {
        Team teamToDelete = checkTeamIdExists(teamId);
        Contest contestFromDelete = contestService.checkContestIdExists(contestId);
        contestService.checkWritableFalse(contestFromDelete);

        Team team = teamRepository.findTeamByIdAndAndContest(teamId, contestFromDelete);
        if (team == null) {
            throw new CustomException("The provided teamId is not in the contest. Please provide valid teamId");
        }
        teamRepository.delete(team);

    }


    public Team editTeam(Team teamRequestBody) {
        Team existingTeam = checkTeamIdExists(teamRequestBody.getId());
        Contest contest = contestService.findContestById(existingTeam.getContest().getId());
        if (contestService.checkWritableFalse(contest)) {
            throw new CustomException("The contestId associated with this team i.e. contestId=" +
                    contest.getId() + " writable flag is false." +
                    "You cannot edit any teams in this contest");
        }
        existingTeam.setId(teamRequestBody.getId());
        existingTeam.setName(teamRequestBody.getName());
        existingTeam.setRank(teamRequestBody.getRank());

        if (checkIfValidState(teamRequestBody.getState())) {
            existingTeam.setState(teamRequestBody.getState());
        } else {
            throw new CustomException("The provided State does not match any state type in the system. Please " +
                    "provide either PENDING,CANCELED,ACCEPTED ");
        }

        return existingTeam;
    }

    public Boolean checkIfValidState(Team.State state) {
        List stateList = Arrays.asList(Team.State.values());
        if (stateList.contains(state)) {
            return true;
        }
        return false;
    }
}


