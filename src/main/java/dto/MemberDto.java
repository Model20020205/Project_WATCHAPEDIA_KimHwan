package dto;

public class MemberDto {
	private String nickname;
	private String id;	// <- 이메일
	private String password;
	public MemberDto(String nickname, String id, String password) {
		super();
		this.nickname = nickname;
		this.id = id;
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
