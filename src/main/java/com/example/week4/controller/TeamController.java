package com.example.week4.controller;

import com.example.week4.model.Team;
import com.example.week4.repository.TeamRepository;
import com.example.week4.services.SuperRepository;
import com.example.week4.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private SuperRepository superRepository;
    //    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamService teamService;

    @Autowired
    public TeamController(SuperRepository superRepository, TeamRepository teamRepository) {
        this.superRepository = superRepository;
        this.teamRepository = teamRepository;
    }


    @RequestMapping(value = "/teams", method = RequestMethod.GET)
    public ResponseEntity<Team> getTeams() {
        return new ResponseEntity(
                superRepository.getTeams(),
                HttpStatus.OK);
    }

    @GetMapping("/teamId={teamId}")
    public Team findTeamById(@PathVariable("teamId") Long teamId) {
        return teamService.findTeamById(teamId);
    }


    @PostMapping("/createTeam")
    @ResponseStatus(HttpStatus.CREATED)
    public Team createTeam(@RequestBody Team team) {
        return teamService.createTeam(team);
    }


    @PostMapping("/contestRegister/contestId={contestId}")
    public Team contestRegister(@RequestBody Team team,
                                @PathVariable("contestId") Long contestId) {
        return teamService.contestRegister(team, contestId);

    }

    @PostMapping(path = "/promoteTeam/teamId={teamId}/contestId={contestId}")
    public Team setPromoteTeam(
            @PathVariable("teamId") Long teamId, @PathVariable("contestId") Long contestId) throws Exception {

        return teamService.promoteTeam(teamId, contestId);
    }

    @GetMapping(path = "/listTeamsByContestId/contestId={contestId}")
    public Set<Team> listTeamsByContestId(@PathVariable("contestId") Long contestId) {
        return teamService.findTeamsByContestId(contestId);
    }

    @PutMapping(path = "/updateRank/teamId={teamId}/contestId={contestId}/rank={rank}")
    public Team updateRank(@PathVariable("teamId") Long teamId,
                           @PathVariable("contestId") Long contestId,
                           @PathVariable("rank") Integer rank) {
        return teamService.updateRank(teamId, contestId, rank);
    }

    @DeleteMapping(path = "/deleteTeam/teamId={TeamId}/contestId={contestId}")
    public void deleteTeam(@PathVariable("teamId") Long teamId, @PathVariable("contestId") Long contestId) {
        teamService.deleteTeam(teamId, contestId);
    }


    @PutMapping(path = "/editTeam")
    public Team editTeam(@RequestBody Team team) {
        return teamService.editTeam(team);
    }
}
