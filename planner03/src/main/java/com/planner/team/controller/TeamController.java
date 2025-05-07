package com.planner.team.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.planner.team.model.TeamInvitationVO;
import com.planner.team.model.TeamMemberVO;
import com.planner.team.model.TeamVO;
import com.planner.team.service.EmailService;
import com.planner.team.service.TeamService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class TeamController {
	@Autowired
	private TeamService service;
	@Autowired
	private EmailService emailService;
	
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
		int teamId = vo.getTeamId();
		int userId = vo.getUserId();
		
		try {
			//
			TeamInvitationVO teamInvitationVo = null;
			teamInvitationVo = service.chkInvitation(teamId, userId);
			result = service.addInvitation(vo.getTeamId(), vo.getUserId(), vo.getUserEmail());
			
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
	public ResponseEntity<?> addTeamMbr(@RequestBody TeamMemberVO vo) {
		int result = 0;
		
		try {
			result = service.addTeamMbr(vo.getTeamId(), vo.getUserId(), vo.getAuth());
			
			if(result > 0) {
				return ResponseEntity.ok().body("팀원 등록 완료");
			} else {
				return ResponseEntity.ok().body("팀원 등록 실패");
			} 
		} catch (Exception e) {
			return ResponseEntity.ok().body("오류 발생");
		}
	}
	
	//이메일 전송
	public String sendEmail() {
		emailService.sendSimpleEmail("twd1509@naver.com", "테스트 제목", "테스트 이메일 본문입니다.");
		
		return "이메일 전송 완료!!";
	}
}
