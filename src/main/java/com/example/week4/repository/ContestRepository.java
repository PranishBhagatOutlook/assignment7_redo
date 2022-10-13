package com.example.week4.repository;

import com.example.week4.model.Contest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ContestRepository extends CrudRepository<Contest, Long> {

    Contest findContestById(@Param("contestId") Long contestId);


}