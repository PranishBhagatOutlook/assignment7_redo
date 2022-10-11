package com.example.week4.services;


import com.example.week4.model.Contest;
import com.example.week4.model.Person;
import com.example.week4.model.Team;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
@Transactional
public class SuperRepository {
    @PersistenceContext
    private EntityManager em;

    public void populate() {
        Contest contest = createContest(2, new Date("01/01/2022"), "World Finals 1",
                true, new Date("01/01/2021"), new Date("12/31/2021"), null, true);
        Contest contest1 = createContest(1, new Date("01/01/2022"), "Regionals 1",
                true, new Date("01/01/2021"), new Date("12/31/2021"), contest.getId(), false);
        // check for contest register valid 1
        Contest contest2 = createContest(1, new Date("01/01/2022"), "Regionals 2",
                true, new Date("01/01/2021"), new Date("12/31/2021"), contest.getId(), true);
        // check for contest register valid 2
        Contest contest3 = createContest(2, new Date("01/01/2022"), "Regionals 3",
                true, new Date("01/01/2021"), new Date("12/31/2021"), contest.getId(), true);
        // check for edit contest, set readable, set writable
        Contest contest4 = createContest(1, new Date("01/01/2022"), "Regionals 4",
                true, new Date("01/01/2021"), new Date("12/31/2021"), contest.getId(), true);


        // promote Team check

        Contest contest5 = createContest(1, new Date("01/01/2022"), "Regionals 5",
                true, new Date("01/01/2021"), new Date("12/31/2021"), contest.getId(), true);
        Contest contest6 = createContest(1, new Date("01/01/2022"), "Regionals 6",
                true, new Date("01/01/2021"), new Date("12/31/2021"), contest.getId(), true);
        Contest contest7 = createContest(1, new Date("01/01/2022"), "Regionals 7",
                true, new Date("01/01/2021"), new Date("12/31/2021"), contest.getId(), true);
        Contest contest8 = createContest(1, new Date("01/01/2022"), "Regionals 8",
                true, new Date("01/01/2021"), new Date("12/31/2021"), contest.getId(), true);
        Contest contest9 = createContest(1, new Date("01/01/2022"), "Regionals 9",
                true, new Date("01/01/2021"), new Date("12/31/2021"), contest.getId(), true);
        Contest contest10 = createContest(1, new Date("01/01/2022"), "Regionals 10",
                true, new Date("01/01/2021"), new Date("12/31/2021"), contest.getId(), true);
//        Person person1 = createPerson(new Date("1/1/1999"), "1bhagatpranish@gmail.com", "Pranish Bhagat", "Baylor University");
//        Person person2 = createPerson(new Date("2/2/1999"), "2bhagatpranish@gmail.com", "2Pranish Bhagat", "2Baylor University");
//        Person person3 = createPerson(new Date("3/3/1999"), "3bhagatpranish@gmail.com", "3Pranish Bhagat", "3Baylor University");
//        Person person4 = createPerson(new Date("4/4/1996"), "4bhagatpranish@gmail.com", "4Pranish Bhagat", "4Baylor University");
//        Person person5 = createPerson(new Date("5/5/1997"), "5bhagatpranish@gmail.com", "5Pranish Bhagat", "5Baylor University");
//        Person person6 = createPerson(new Date("6/6/1998"), "6bhagatpranish@gmail.com", "6Pranish Bhagat", "6Baylor University");
//        Person person7 = createPerson(new Date("7/7/1999"), "7bhagatpranish@gmail.com", "7Pranish Bhagat", "7Baylor University");
//        Person person8 = createPerson(new Date("8/8/2000"), "8bhagatpranish@gmail.com", "8Pranish Bhagat", "8Baylor University");
//        Person person9 = createPerson(new Date("9/9/2001"), "9bhagatpranish@gmail.com", "9Pranish Bhagat", "9Baylor University");
//        Person person10 = createPerson(new Date("10/10/2002"), "10bhagatpranish@gmail.com", "10Pranish Bhagat", "10Baylor University");
//        Person person11 = createPerson(new Date("11/11/2003"), "11bhagatpranish@gmail.com", "11Pranish Bhagat", "11Baylor University");
//        Person person12 = createPerson(new Date("12/12/2004"), "12bhagatpranish@gmail.com", "12Pranish Bhagat", "12Baylor University");
//        Person person13 = createPerson(new Date("1/1/2003"), "13bhagatpranish@gmail.com", "13Pranish Bhagat", "13Baylor University");
//        Person person14 = createPerson(new Date("2/2/2002"), "14bhagatpranish@gmail.com", "14Pranish Bhagat", "14Baylor University");
//        Person person15 = createPerson(new Date("2/2/2002"), "15bhagatpranish@gmail.com", "15Pranish Bhagat", "15Baylor University");
//        Person person16 = createPerson(new Date("2/2/2002"), "16bhagatpranish@gmail.com", "16Pranish Bhagat", "16Baylor University");
//        Person person17 = createPerson(new Date("2/2/2002"), "17bhagatpranish@gmail.com", "17Pranish Bhagat", "17Baylor University");
//        Person person18 = createPerson(new Date("2/2/2002"), "18bhagatpranish@gmail.com", "18Pranish Bhagat", "18Baylor University");
//        Person person19 = createPerson(new Date("2/2/2002"), "19bhagatpranish@gmail.com", "19Pranish Bhagat", "19Baylor University");
//        Person person20 = createPerson(new Date("2/2/2002"), "20bhagatpranish@gmail.com", "20Pranish Bhagat", "20Baylor University");
//        Person person21 = createPerson(new Date("2/2/2002"), "21bhagatpranish@gmail.com", "21Pranish Bhagat", "21Baylor University");
//        Person person22 = createPerson(new Date("2/2/2002"), "22bhagatpranish@gmail.com", "22Pranish Bhagat", "22Baylor University");
//        Person person23 = createPerson(new Date("2/2/2002"), "23bhagatpranish@gmail.com", "23Pranish Bhagat", "23Baylor University");
//        Person person24 = createPerson(new Date("2/2/2002"), "24bhagatpranish@gmail.com", "24Pranish Bhagat", "24Baylor University");

//
//        Person contestManager = createPerson(new Date("10/12/1993"), "contestmanager@gmail.com", "Contest Manager", "Contest Manager University");
//        contest1.getContestManager().add(contestManager);
//        contest.getContestManager().add(contestManager);
//
        // check for Contest Register
        // check for null coach
        // 30
//        Team team1 = createTeam("Team1", 1, Team.State.PENDING);
//        team1.getContestant().add(person1);
//        team1.getContestant().add(person2);
//        team1.getContestant().add(person3);

        // check for age greater than 24
        // 31
//        Team team2 = createTeam("Team2", null, Team.State.PENDING);
//        team2.getContestant().add(person4);
//        team2.getContestant().add(person5);
//        team2.getContestant().add(person6);
//        team2.setCoach(person7);

        // check for distinct members
        // 32
//        Team team3 = createTeam("Team3", 6, Team.State.PENDING);
//        team3.getContestant().add(person8);
//        team3.getContestant().add(person9);
//        team3.getContestant().add(person10);
//        team3.setCoach(person10);
//        team3.setContest(contest);

        // valid Contest Register
        // 33
//        Team team4 = createTeam("Team4", 2, Team.State.PENDING);
//        team4.getContestant().add(person8);
//        team4.getContestant().add(person9);
//        team4.getContestant().add(person10);
//        team4.setCoach(person11);
//        team4.setContest(contest);

        // valid contest Register
        // 34
//        Team team8 = createTeam("Team5", 1, Team.State.PENDING);
//        team8.getContestant().add(person19);
//        team8.getContestant().add(person20);
//        team8.getContestant().add(person21);
//        team8.setCoach(person24);

        // valid contest Register
        // 35
//        Team team5 = createTeam("Team6", 6, Team.State.PENDING);
//        team5.getContestant().add(person8);
//        team5.getContestant().add(person9);
//        team5.getContestant().add(person10);
//        team5.setCoach(person11);

        // check for Team Promotion
        // team members in different team
        // 36
//        Team team6 = createTeam("Team7", 1, Team.State.PENDING);
//        team6.getContestant().add(person12);
//        team6.getContestant().add(person13);
//        team6.getContestant().add(person14);
//        team6.setCoach(person17);

        // valid contest Register
        // 37
        // superContest limit reached
//        Team team7 = createTeam("Team8", 1, Team.State.PENDING);
//        team7.getContestant().add(person15);
//        team7.getContestant().add(person16);
//        team7.getContestant().add(person18);
//        team7.setCoach(person17);
//

    }




    private Contest createContest(Integer capacity, Date date,
                                  String name, Boolean registration_allowed,
                                  Date registration_from, Date registration_to, Long superContestId, Boolean writable) {
        Contest contest = new Contest();
        contest.setCapacity(capacity);
        contest.setDate(date);
        contest.setName(name);
        contest.setRegistration_allowed(registration_allowed);
        contest.setRegistration_from(registration_from);
        contest.setRegistration_to(registration_to);
        contest.setSuperContestId(superContestId);
        contest.setWritable(writable);
        em.persist(contest);
        return contest;
    }

//    private Team createTeam(String name, Integer rank, Team.State state){}

    private Person createPerson(Date birthDate, String email, String name, String university) {
        Person person = new Person();
        person.setBirthDate(birthDate);
        person.setEmail(email);
        person.setName(name);
        person.setUniversity(university);
        em.persist(person);
        return person;
    }

    private Team createTeam(String name, Integer rank, Team.State state) {
        Team team = new Team();
        team.setName(name);
        team.setRank(rank);
        team.setState(state);
        em.persist(team);
        return team;
    }



    public List<Team> getTeams() {
        return em.createQuery("SELECT c FROM Team c").getResultList();
    }

    public List<Person> getPersons() {
        return em.createQuery("SELECT c FROM Person c").getResultList();
    }

    public List<Contest> getContests() {
        return em.createQuery("SELECT c FROM Contest c").getResultList();
    }



}
