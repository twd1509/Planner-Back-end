package com.planner.team.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planner.team.mapper.TeamMapper;

@Service
public class TeamService {
	@Autowired
	private TeamMapper mapper;
	
	public int addTeam(int ownerId, String title) {
		return mapper.insertTeam(ownerId, title);
	}
	
	public int addTeamInvitation(int teamId, int userId, String userEmail) {
		return mapper.insertTeamInvitation(teamId, userId, userEmail);
	}
	
	public int addTeamMember(int teamId, int userId, int auth) {
		return mapper.insertTeamMember(teamId, userId, auth);
	}
}
