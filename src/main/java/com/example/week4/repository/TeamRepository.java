package com.example.week4.repository;

import com.example.week4.model.Contest;
import com.example.week4.model.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@Repository
@Transactional
public interface TeamRepository extends CrudRepository<Team, Long> {

    List<Team> findByContestId(Long contestId);

    Team findTeamById(Long teamId);

    List<Team> findTeamsByContestantIdAndContestId(@Param("personId") Long personId,
                                                   @Param("contestId") Long contestId);

    Set<Team> findTeamsByContestId(Long contestId);

    Team findTeamByIdAndAndContest(Long teamId, Contest contest);

    List<Team> findTeamsByContest(Contest contest);

}
