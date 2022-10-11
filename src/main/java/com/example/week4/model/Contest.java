package com.example.week4.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Contest implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean registration_allowed;

    @Column(nullable = false)
    private Date registration_from;

    @Column(nullable = false)
    private Date registration_to;
    @Size(max = 1)
    private Long superContestId;

    private Boolean writable;

    public Boolean getWritable() {
        return writable;
    }

    public void setWritable(Boolean writable) {
        this.writable = writable;
    }

    @NotNull
    @ManyToMany
    private Set<Person> contestManager = new HashSet();

    @NotNull
    @ManyToMany
    private Set<Team> teams = new HashSet();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSuperContestId() {
        return superContestId;
    }

    public void setSuperContestId(Long superContestId) {
        this.superContestId = superContestId;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRegistration_allowed() {
        return registration_allowed;
    }

    public void setRegistration_allowed(Boolean registration_allowed) {
        this.registration_allowed = registration_allowed;
    }

    public Date getRegistration_from() {
        return registration_from;
    }

    public void setRegistration_from(Date registration_from) {
        this.registration_from = registration_from;
    }

    public Date getRegistration_to() {
        return registration_to;
    }

    public void setRegistration_to(Date registration_to) {
        this.registration_to = registration_to;
    }

    public Set<Person> getContestManager() {
        return contestManager;
    }

    public void setContestManager(Set<Person> contestManager) {
        this.contestManager = contestManager;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }


}
