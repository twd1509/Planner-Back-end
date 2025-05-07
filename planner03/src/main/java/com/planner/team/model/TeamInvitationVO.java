package com.planner.team.model;

public class TeamInvitationVO {
	private int id;					//아이디
	private int teamId;				//팀 아이디
	private int userId;				//초대 받은 사용자 아이디
	private String userEmail;		//초대 받은 사용자 이메일
	private String status;			//초대 상태(수락 대기 중 : N, 수락 : Y)
	private String regDt;			//초대일자
	private String updateDt;		//수정일자
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getUpdateDt() {
		return updateDt;
	}
	public void setUpdateDt(String updateDt) {
		this.updateDt = updateDt;
	}
}
