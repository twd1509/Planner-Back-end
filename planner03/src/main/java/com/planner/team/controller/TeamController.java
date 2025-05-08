package com.planner.team.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.planner.team.model.TeamInvitationVO;
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
			//세션 확인
			
			//팀 생성
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
	
	//팀 삭제
	public ResponseEntity<?> removeTeam(@PathVariable int userId, @PathVariable int id) {
		int result = 0;
		
		try {
			//세션 확인
			
			//팀 삭제
			result = service.removeTeam(id);
			
			if(result > 0) {
				return ResponseEntity.ok().body("팀 삭제 완료");
			} else {
				return ResponseEntity.ok().body("팀 삭제 실패");
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
		String email = vo.getUserEmail();
		
		try {
			//세션 확인
			
			//초대 여부 확인
			TeamInvitationVO teamInvitationVo = null;
			teamInvitationVo = service.chkInvitation(teamId, userId);
			
			if (teamInvitationVo == null) /* 초대 이력 없음 */ {
				//초대 이력 추가
				result = service.addInvitation(teamId, userId, email);
				
				//초대 이력 추가 결과
				if(result > 0) {
					//초대 메일 전송
					String body = "초대 링크 : ";
					body += "localhost:8083/addTeamMbr/" + teamId  + "/" + userId; 
					sendEmail(email, "일정 관리 팀 초대 메일입니다.", body);
					
					return ResponseEntity.ok().body("팀 초대 완료");
				} else {
					return ResponseEntity.ok().body("팀 초대 실패");
				}
			} else /* 초대 이력 있음 */ {
				return ResponseEntity.ok().body("기존에 초대한 회원입니다.");
			}
		} catch (Exception e) {
			return ResponseEntity.ok().body("오류 발생");
		}
	}
	
	//팀원 등록
	@GetMapping("/addTeamMbr")
	public ResponseEntity<?> addTeamMbr(@PathVariable int teamId, @PathVariable int userId) {
		int result = 0;
		
		try {
			//1. 세션 아이디와 초대 아이디가 동일한 지 확인
			
			
			//2. 초대된 회원인지 확인
			TeamInvitationVO invitationVo = null; 
			invitationVo = service.chkInvitation(teamId, userId);
			
			if(invitationVo != null) {
				return ResponseEntity.ok().body("초대받지 않은 회원이거나 이미 팀원에 추가된 회원입니다.");
			}
			
			//3. 초대 수락
			result = service.acptInvitation(teamId, userId);
			if(result > 0) {
				//결과값 초기화
				result = 0;
				
				//4. 팀원 등록
				result = service.addTeamMbr(teamId, userId, 3);
				
				if(result > 0) {
					return ResponseEntity.ok().body("팀원 등록 완료");
				} else {
					return ResponseEntity.ok().body("팀원 등록 실패");
				}
			} else {
				return ResponseEntity.ok().body("초대 수락 변경 실패");
			}
			 
		} catch (Exception e) {
			return ResponseEntity.ok().body("오류 발생");
		}
	}
	
	//이메일 전송
	public void sendEmail(String to, String subject, String body) {
		emailService.sendSimpleEmail(to, subject, body);
	}
	
	//세션 확인
	public int chkLoginUserId(int userId) {
		//return service.chkUserId(userId);
		return 1;
	}
}
