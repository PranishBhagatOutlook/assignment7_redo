package com.example.week4.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Team implements Serializable {
    @Id
    @GeneratedValue
    private Long id;


    @Column(nullable = false)
    private String name;
    @Column(nullable = true)
    private Integer rank;

    @Column(nullable = false)
    private State state;
    //    @Size(min = 1, max =3)

    @Column(nullable = true)
    private Long promotedTeamId;

    @Column(nullable = true)
    private Long promotedFromContestId;


    public Long getPromotedFromContestId() {
        return promotedFromContestId;
    }

    public void setPromotedFromContestId(Long promotedFromContestId) {
        this.promotedFromContestId = promotedFromContestId;
    }

    @Size(min = 1, max = 3)
    @ManyToMany
    private Set<Person> contestant = new HashSet();
    //    @Column(nullable = false)
    @NotNull
    @ManyToOne
    private Contest contest;
    @Size(min = 0, max = 1)
    @OneToOne
    private Person coach;

    public static enum State {
        ACCEPTED, PENDING, CANCELED
    }


    public Set<Person> getContestant() {
        return contestant;
    }

    public void setContestant(Set<Person> contestant) {
        this.contestant = contestant;
    }

    public Person getCoach() {
        return coach;
    }

    public void setCoach(Person coach) {
        this.coach = coach;
    }


//    @Column(nullable = false)

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Long getPromotedTeamId() {
        return promotedTeamId;
    }

    public void setPromotedTeamId(Long promotedTeamId) {
        this.promotedTeamId = promotedTeamId;
    }


}
