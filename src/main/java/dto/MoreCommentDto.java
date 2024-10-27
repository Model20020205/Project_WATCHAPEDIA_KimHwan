package dto;

public class MoreCommentDto {
	private String profile_img;
	private String nickname;
	private double score;
	private String conetnt;
	private int love_eva_idx;
	private int reply_eva_idx;
	private int eva_idx;
	public MoreCommentDto(String profile_img, String nickname, double score, String conetnt, int love_eva_idx,
			int reply_eva_idx, int eva_idx) {
		super();
		this.profile_img = profile_img;
		this.nickname = nickname;
		this.score = score;
		this.conetnt = conetnt;
		this.love_eva_idx = love_eva_idx;
		this.reply_eva_idx = reply_eva_idx;
		this.eva_idx = eva_idx;
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
	public String getConetnt() {
		return conetnt;
	}
	public void setConetnt(String conetnt) {
		this.conetnt = conetnt;
	}
	public int getLove_eva_idx() {
		return love_eva_idx;
	}
	public void setLove_eva_idx(int love_eva_idx) {
		this.love_eva_idx = love_eva_idx;
	}
	public int getReply_eva_idx() {
		return reply_eva_idx;
	}
	public void setReply_eva_idx(int reply_eva_idx) {
		this.reply_eva_idx = reply_eva_idx;
	}
	public int getEva_idx() {
		return eva_idx;
	}
	public void setEva_idx(int eva_idx) {
		this.eva_idx = eva_idx;
	}
}
