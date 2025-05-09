package com.planner.team.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.planner.team.model.TeamInvitationVO;
import com.planner.team.model.TeamMemberVO;
import com.planner.team.model.TeamVO;

@Mapper
public interface TeamMapper {
	/* 팀 */
	TeamVO insTeam(int ownerId, String title);								//팀 등록
	int delTeam(int id);												//팀 삭제
	int delTeamByOwnerId(int ownerId);									//팀 삭제(작성자 기준)
	int uptTeam(String title, int id);									//팀명 수정
	ArrayList<TeamVO> slctTeam(int userId);								//참여중인 팀 출력
	TeamVO slctTeamById(int id);										//팀 출력(id)
	
	/* 팀 초대 */
	int insInvitation(int teamId, int userId, String userEmail);		//팀 초대
	int acptInvitation(int teamId, int userId);							//초대 수락
	int delInvitationById(int id);										//초대 기록 삭제
	int delInvitationByTeamId(int teamId);								//초대 기록 삭제(팀 삭제 시)
	TeamInvitationVO chkInvitation(int teamId, int userId);				//초대 수락 시 기록 확인
	ArrayList<TeamInvitationVO> waitInvitation(int teamId);				//초대 대기 중인 팀원
	
	/* 팀원 */
	int insTeamMbr(int teamId, int userId, int auth);					//팀원 등록
	int delTeamMbr(int id);												//팀원 삭제
	int uptTeamMbrAuth(int auth, int id);								//팀원 권한 수정
	int delTeamMbrByTeamId(int teamId);									//팀원 삭제(팀 삭제 시)
	TeamMemberVO slctTeamMbrByTeamIdAndUserId(int teamId, int userId);	//팀원 조회(팀 아이디, 유저 아이디 이용)
}
