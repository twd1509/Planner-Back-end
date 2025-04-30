package com.planner.team.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.planner.team.model.TeamInvitationVO;
import com.planner.team.model.TeamMemberVO;
import com.planner.team.model.TeamVO;
import com.planner.team.service.TeamService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class TeamController {
	private TeamService service;
	
	//팀 생성
	public ResponseEntity<?> addTeam(@RequestBody TeamVO vo) {
		int result = 0;
		
		try {
			result = service.addTeam(vo.getOwnerId(), vo.getTitle());
			
			if(result > 0) {
				return ResponseEntity.ok().body("팀 등록 완료");
			} else {
				return ResponseEntity.ok().body("팀 등록 실패");
			} 
		} catch (Exception e) {
			return ResponseEntity.ok().body("오류 발생");
		}
	}
	
	//팀 초대
	public ResponseEntity<?> addTeamInvitation(@RequestBody TeamInvitationVO vo) {
		int result = 0;
		
		try {
			result = service.addTeamInvitation(vo.getTeamId(), vo.getUserId(), vo.getUserEmail());
			
			if(result > 0) {
				return ResponseEntity.ok().body("팀 초대 완료");
			} else {
				return ResponseEntity.ok().body("팀 초대 실패");
			} 
		} catch (Exception e) {
			return ResponseEntity.ok().body("오류 발생");
		}
	}
	
	//팀원 등록
	public ResponseEntity<?> addTeamMember(@RequestBody TeamMemberVO vo) {
		int result = 0;
		
		try {
			result = service.addTeamMember(vo.getTeamId(), vo.getUserId(), vo.getAuth());
			
			if(result > 0) {
				return ResponseEntity.ok().body("팀원 등록 완료");
			} else {
				return ResponseEntity.ok().body("팀원 등록 실패");
			} 
		} catch (Exception e) {
			return ResponseEntity.ok().body("오류 발생");
		}
	}
}
