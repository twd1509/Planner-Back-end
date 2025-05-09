package com.planner.team.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planner.team.mapper.TeamMapper;
import com.planner.team.model.TeamInvitationVO;
import com.planner.team.model.TeamMemberVO;
import com.planner.team.model.TeamVO;

@Service
public class TeamService {
	@Autowired
	private TeamMapper mapper;
	
	/* 팀 */
	//팀 추가
	public TeamVO addTeam(int ownerId, String title) {
		return mapper.insTeam(ownerId, title);
	}
	//팀 삭제
	public int removeTeam(int id) {
		return mapper.delTeam(id);
	}
	//팀 삭제(작성자 기준)
	public int removeTeamById(int ownerId) {
		return mapper.delTeamByOwnerId(ownerId); 
	}
	//팀명 수정
	public int modifyTeam(String title, int id) {
		return mapper.uptTeam(title, id);
	}
	//참여중인 팀 출력
	public ArrayList<TeamVO> getTeam(int userId) {
		return mapper.slctTeam(userId);
	}
	//팀 출력(id)
	public TeamVO getTeamById(int id) {
		return mapper.slctTeamById(id);
	}
	
	/* 팀 초대 */
	//팀 초대 인원 추가
	public int addInvitation(int teamId, int userId, String userEmail) {
		return mapper.insInvitation(teamId, userId, userEmail);
	}
	//초대 수락
	public int acptInvitation(int teamId, int userId) {
		return mapper.acptInvitation(teamId, userId);
	}
	//초대 기록 삭제
	public int removeInvitationById(int id) {
		return mapper.delInvitationById(id);
	}
	//초대 기록 삭제(팀 삭제 시)
	public int removeInvitationByTeamId(int teamId) {
		return mapper.delInvitationByTeamId(teamId);
	}
	//초대 수락 시 기록 확인
	public TeamInvitationVO chkInvitation(int teamId, int userId) {
		return mapper.chkInvitation(teamId, userId);
	}
	//초대 대기 중인 팀원
	public ArrayList<TeamInvitationVO> waitInvitation(int teamId) {
		return mapper.waitInvitation(teamId);
	}
	
	/* 팀원 */
	//팀원 추가
	public int addTeamMbr(int teamId, int userId, int auth) {
		return mapper.insTeamMbr(teamId, userId, auth);
	}
	//팀원 삭제
	public int removeTeamMbr(int id) {
		return mapper.delTeamMbr(id);
	}
	//팀원 권한 수정
	public int modifyTeamMbrAuth(int auth, int id) {
		return mapper.uptTeamMbrAuth(auth, id);
	}
	//팀원 삭제(팀 삭제 시)
	public int removeTeamMbrByTeamId(int teamId) {
		return mapper.delTeamMbrByTeamId(teamId);
	}
	//팀원 조회(팀 아이디, 유저 아이디 이용)
	public TeamMemberVO getTeamMbr(int teamId, int userId) {
		return mapper.slctTeamMbrByTeamIdAndUserId(teamId, userId);
	}
}
