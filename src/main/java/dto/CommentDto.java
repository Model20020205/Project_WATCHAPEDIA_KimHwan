package dto;

public class CommentDto {
	private String profile_img;
	private String nickname;
	private double score;
	private String content;
	private int eva_idx;
	private int love_eva_idx;
	private int reply_idx;
	public CommentDto(String profile_img, String nickname, double score, String content, int eva_idx, int love_eva_idx,
			int reply_idx) {
		super();
		this.profile_img = profile_img;
		this.nickname = nickname;
		this.score = score;
		this.content = content;
		this.eva_idx = eva_idx;
		this.love_eva_idx = love_eva_idx;
		this.reply_idx = reply_idx;
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
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getEva_idx() {
		return eva_idx;
	}
	public void setEva_idx(int eva_idx) {
		this.eva_idx = eva_idx;
	}
	public int getLove_eva_idx() {
		return love_eva_idx;
	}
	public void setLove_eva_idx(int love_eva_idx) {
		this.love_eva_idx = love_eva_idx;
	}
	public int getReply_idx() {
		return reply_idx;
	}
	public void setReply_idx(int reply_idx) {
		this.reply_idx = reply_idx;
	}
}
