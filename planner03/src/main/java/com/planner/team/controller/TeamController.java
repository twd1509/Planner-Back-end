package com.planner.team.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	String pathUrl = "localhost:8083";
	//팀 생성
	@PostMapping("/addteam")
	public ResponseEntity<?> addTeam(@RequestBody TeamVO vo) {
		try {
			//세션 확인
			
			
			//팀 생성
			service.addTeam(vo.getOwnerId(), vo.getTitle());
			
			//방금 생성한 id값 조회
			TeamVO teamVo = new TeamVO();
			int newId = teamVo.getId();
			
			if(newId != 0) {
				//팀원 등록
				int result = 0;
				result = service.addTeamMbr(newId, vo.getOwnerId(), 1);
				
				if(result > 0) {
					return ResponseEntity.ok().body("팀 등록 완료");
				} else {
					return ResponseEntity.ok().body("팀 등록 실패");
				}
			} else {
				return ResponseEntity.ok().body("팀 등록 실패");
			}
		} catch (Exception e) {
			return ResponseEntity.ok().body("오류 발생");
		}
	}
	
	//팀 삭제
	@PostMapping("/removeteam")
	public ResponseEntity<?> removeTeam(@PathVariable int userId, @PathVariable int id) {
		int result = 0;
		
		try {
			//세션 확인
			
			
			//팀 삭제
			result = service.removeTeam(id);
			
			if(result > 0) {
				//팀 멤버 삭제
				int delMbrResult = service.removeTeamMbrByTeamId(id);
				if(delMbrResult > 0) {
					//팀 초대 내역 삭제
					int delInvitationResult = service.removeInvitationByTeamId(id);
					
					if(delInvitationResult > 0) {
						return ResponseEntity.ok().body("팀 삭제 완료");
					} else {
						return ResponseEntity.ok().body("팀 초대 내역 삭제 실패");
					} 
				} else {
					return ResponseEntity.ok().body("팀 멤버 삭제 실패");
				}
			} else {
				return ResponseEntity.ok().body("팀 삭제 실패");
			}
		} catch (Exception e) {
			return ResponseEntity.ok().body("오류 발생");
		}
	}
	
	//팀 정보 수정
	public ResponseEntity<?> modifyTeam(@RequestBody TeamVO vo) {
		int result = 0;
		
		try {
			//권한 확인
			TeamMemberVO memVo = service.getTeamMbr(vo.getId(), vo.getOwnerId());
			if(memVo.getAuth() == 1) {
				//팀 정보 수정
				result = service.modifyTeam(vo.getTitle(), vo.getId());
				
				if(result > 0) {
					return ResponseEntity.ok().body("팀 정보 수정 완료");
				} else {
					return ResponseEntity.ok().body("팀 정보 수정 실패");
				}			
			} else {
				return ResponseEntity.ok().body("권한이 없습니다.");
			}
		} catch (Exception e) {
			return ResponseEntity.ok().body("오류 발생");
		}
	}
	
	//현재 소속되어 있는 팀 확인
	public ResponseEntity<?> getTeam(@RequestBody int userId) {
		//세션 확인
		
		//소속되어 있는 팀 확인
		try {
			List<TeamVO> result = new ArrayList<TeamVO>();
			result = service.getTeam(userId);
			
			return ResponseEntity.ok(result); 
		} catch (Exception e) {
			return ResponseEntity.ok().body("오류 발생");
		}
	}
	
	//팀 초대
	@PostMapping("/addinvitation")
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
					body += pathUrl + "/addTeamMbr/" + teamId  + "/" + userId; 
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
	
	//초대 기록 삭제
	public ResponseEntity<?> removeInvitation(@RequestBody TeamInvitationVO vo) {
		int result = 0;
		
		try {
			//세션 확인
			
			//권한 확인
			TeamMemberVO memVo = service.getTeamMbr(vo.getTeamId(), vo.getUserId());
			if(memVo.getAuth() == 1) {
				result = service.removeInvitationById(vo.getId());
				
				if(result > 0) {
					return ResponseEntity.ok().body("초대 기록 삭제 완료");
				} else {
					return ResponseEntity.ok().body("초대 기록 삭제 실패");
				}
			} else {
				return ResponseEntity.ok().body("권한이 없습니다.");
			}
			
		} catch (Exception e) {
			return ResponseEntity.ok().body("오류 발생");
		}
	}
	
	//초대 대기 중인 팀원
	public ResponseEntity<?> waitInvitation(@PathVariable int teamId) {
		List<TeamInvitationVO> result = service.waitInvitation(teamId);
		
		if(result != null) {
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.ok().body("초대 기록 삭제 실패");
		}
	}
	
	//팀원 등록
	@GetMapping("/addteammbr")
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
	
	//팀원 삭제
	public ResponseEntity<?> removeTeamMbr(@RequestBody TeamMemberVO vo) {
		int result = 0;
		
		try {
			//권한 확인
			
			//팀원 삭제
			result = service.removeTeamMbr(vo.getId());
			
			if(result > 0) {
				return ResponseEntity.ok().body("팀원 삭제 완료");
			} else {
				return ResponseEntity.ok().body("팀원 삭제 실패");
			}
		} catch (Exception e) {
			return ResponseEntity.ok().body("오류 발생");
		}
	}
	
	//팀원 수정
	public ResponseEntity<?> modifyTeamMbr(@RequestBody TeamMemberVO vo) {
		int result = 0;
		
		try {
			//권한 확인
			
			//팀원 수정
			result = service.modifyTeamMbrAuth(vo.getAuth(), vo.getId());
			
			if(result > 0) {
				return ResponseEntity.ok().body("팀원 수정 완료");
			} else {
				return ResponseEntity.ok().body("팀원 수정 실패");
			}
		} catch (Exception e) {
			return ResponseEntity.ok().body("오류 발생");
		}
	}
	
	//이메일 전송
	private void sendEmail(String to, String subject, String body) {
		emailService.sendSimpleEmail(to, subject, body);
	}
	
	//세션 확인
	private int chkLoginUserId(int userId) {
		//return service.chkUserId(userId);
		return 1;
	}
}
