package com.planner.team.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeamMapper {
	int insertTeam(int ownerId, String title);								//팀 등록
	int insertTeamInvitation(int teamId, int userId, String userEmail);		//팀 초대
	int insertTeamMember(int teamId, int userId, int auth);					//팀원 등록
}
