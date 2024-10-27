package dto;

public class ComProDto {
	private String profile_img;
	private String nickname;
	private int score;
	private String content;
	public ComProDto(String profile_img, String nickname, int score, String content) {
		super();
		this.profile_img = profile_img;
		this.nickname = nickname;
		this.score = score;
		this.content = content;
	}
	public String getProfile_img() {
		return profile_img;
	}
	public void setProfile_img(String profile_img) {
		this.profile_img = profile_img;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
