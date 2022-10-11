package com.example.week4;

import com.example.week4.model.Contest;
import com.example.week4.model.Person;
import com.example.week4.model.Team;
import com.example.week4.services.SuperRepository;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;


import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class Week4ApplicationTests {

    @Autowired
    private TestEntityManager em;

    public void populate() {
        Contest contest = createContest(50, new Date("01/01/2022"), "45th World Finals",
                true, new Date("01/01/2021"), new Date("12/31/2021"), null);
        Contest contest1 = createContest(50, new Date("01/01/2022"), "2022 NAC Regionals",
                true, new Date("01/01/2021"), new Date("12/31/2021"), contest.getId());

        Person person1 = createPerson(new Date("1/1/1993"), "1bhagatpranish@gmail.com", "Pranish Bhagat", "Baylor University");
        Person person2 = createPerson(new Date("2/2/1994"), "2bhagatpranish@gmail.com", "2Pranish Bhagat", "2Baylor University");
        Person person3 = createPerson(new Date("3/3/1995"), "3bhagatpranish@gmail.com", "3Pranish Bhagat", "3Baylor University");
        Person person4 = createPerson(new Date("4/4/1996"), "4bhagatpranish@gmail.com", "4Pranish Bhagat", "4Baylor University");
        Person person5 = createPerson(new Date("5/5/1997"), "5bhagatpranish@gmail.com", "5Pranish Bhagat", "5Baylor University");
        Person person6 = createPerson(new Date("6/6/1998"), "6bhagatpranish@gmail.com", "6Pranish Bhagat", "6Baylor University");
        Person person7 = createPerson(new Date("7/7/1999"), "7bhagatpranish@gmail.com", "7Pranish Bhagat", "7Baylor University");
        Person person8 = createPerson(new Date("8/8/2000"), "8bhagatpranish@gmail.com", "8Pranish Bhagat", "8Baylor University");
        Person person9 = createPerson(new Date("9/9/2001"), "9bhagatpranish@gmail.com", "9Pranish Bhagat", "9Baylor University");
        Person person10 = createPerson(new Date("10/10/2002"), "10bhagatpranish@gmail.com", "10Pranish Bhagat", "10Baylor University");
        Person person11 = createPerson(new Date("11/11/2003"), "11bhagatpranish@gmail.com", "11Pranish Bhagat", "11Baylor University");
        Person person12 = createPerson(new Date("12/12/2004"), "12bhagatpranish@gmail.com", "12Pranish Bhagat", "12Baylor University");


        Person contestManager = createPerson(new Date("10/12/1993"), "contestmanager@gmail.com", "Contest Manager", "Contest Manager University");
        contest1.getContestManager().add(contestManager);
        contest.getContestManager().add(contestManager);
        Team team1 = createTeam("TeamA", null, Team.State.PENDING);
        team1.getContestant().add(person1);
        team1.getContestant().add(person2);
        team1.getContestant().add(person3);
        team1.setCoach(person4);
        team1.setContest(contest1);
        Team team2 = createTeam("TeamB", null, Team.State.PENDING);
        team2.getContestant().add(person5);
        team2.getContestant().add(person6);
        team2.getContestant().add(person7);
        team2.setCoach(person8);
        team2.setContest(contest1);
        Team team3 = createTeam("TeamC", null, Team.State.PENDING);
        team3.getContestant().add(person9);
        team3.getContestant().add(person10);
        team3.getContestant().add(person11);
        team3.setCoach(person12);
        team3.setContest(contest1);
//        Team team4 = createTeam("TeamD",null, Team.State.PENDING);
    }

    private Contest createContest(Integer capacity, Date date,
                                  String name, Boolean registration_allowed,
                                  Date registration_from, Date registration_to, Long superContestId) {
        Contest contest = new Contest();
        contest.setCapacity(capacity);
        contest.setDate(date);
        contest.setName(name);
        contest.setRegistration_allowed(registration_allowed);
        contest.setRegistration_from(registration_from);
        contest.setRegistration_to(registration_to);
        contest.setSuperContestId(superContestId);
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


//    @ParameterizedTest
//    @MethodSource("populate")
//    public void demoTest(){
//    }
    @Test
    public void firstTest() {
        populate();
//        List<String> expectedName = Arrays.asList(new String[]{"TeamA", "TeamB", "TeamC"});
        List<Team> dbTeam = em.getEntityManager().createQuery("SELECT t FROM Team t").getResultList();
        List<String> teamName = dbTeam.stream().map(i -> i.getName()).collect(Collectors.toList());
        System.out.println("2. Select all teams in the given contest and print them (JUnit)");
        System.out.println("Team Names are: ");
        System.out.println(teamName);
//        assertEquals(expectedName, teamName);
    }

    @Test
    public void secondTest() {
       populate();
//        List<Integer> expectedAge = Arrays.asList(new Integer[]{29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 29});
        List<Person> dbPerson = em.getEntityManager().createQuery("SELECT p FROM Person p").getResultList();
        List<Date> birthDate = dbPerson.stream().map(i -> i.getBirthDate()).collect(Collectors.toList());
        List<Integer> actualAge = new ArrayList();
        for (Date d : birthDate) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            actualAge.add(2022 - cal.get(Calendar.YEAR));
        }
//        assertEquals(expectedAge, actualAge);
        System.out.println("3.  provide a report stating how many students there are grouped by age (JUnit)");
        System.out.println("Age Count");
        Set<Integer> st = new HashSet<Integer>(actualAge);
        for (Integer i : st)
            System.out.println(i + "    " + Collections.frequency(actualAge, i));

    }

    @Test
    public void thirdTest() {
        populate();
        Contest dbContest = (Contest) em.getEntityManager().createQuery("SELECT c FROM Contest c").getResultList().get(1);
        List<Team> dbTeam = em.getEntityManager().createQuery("SELECT t FROM Team t").getResultList();
        List<Team> filteredTeam = new ArrayList<>();
        for (Team m : dbTeam) {
            if (m.getContest().getName() == "2022 NAC Regionals") {
                filteredTeam.add(m);
            }
        }
        List<String> teamName = dbTeam.stream().map(i -> i.getName()).collect(Collectors.toList());
        System.out.println("4.  calculate the current contest occupancy and compare it with the capacity of the contest (JUnit)");
        System.out.println("Expected capacity for Contest " + dbContest.getName() + " is " + dbContest.getCapacity());
        System.out.println("Actual capacity for Contest " + dbContest.getName() + " is " + teamName.size());
        Integer occupacy = dbContest.getCapacity() - teamName.size();
        System.out.println("Remaining Occupacy for the contest " + dbContest.getName() + " is " + occupacy);


    }
}
