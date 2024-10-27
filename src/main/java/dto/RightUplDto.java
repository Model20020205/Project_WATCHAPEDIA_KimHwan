package dto;

public class RightUplDto {
	private String movie_title;
	private int mm_idx;
	private String profile_img;
	private String nickname;
	private double score;
	private int eva_idx;
	private String content;
	private String poster;
	private int love_eva_idx;
	private int reply_idx;
	private int movie_idx;
	public RightUplDto(String movie_title, int mm_idx, String profile_img, String nickname, double score, int eva_idx,
			String content, String poster, int love_eva_idx, int reply_idx, int movie_idx) {
		super();
		this.movie_title = movie_title;
		this.mm_idx = mm_idx;
		this.profile_img = profile_img;
		this.nickname = nickname;
		this.score = score;
		this.eva_idx = eva_idx;
		this.content = content;
		this.poster = poster;
		this.love_eva_idx = love_eva_idx;
		this.reply_idx = reply_idx;
		this.movie_idx = movie_idx;
	}
	public String getMovie_title() {
		return movie_title;
	}
	public void setMovie_title(String movie_title) {
		this.movie_title = movie_title;
	}
	public int getMm_idx() {
		return mm_idx;
	}
	public void setMm_idx(int mm_idx) {
		this.mm_idx = mm_idx;
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
	public int getEva_idx() {
		return eva_idx;
	}
	public void setEva_idx(int eva_idx) {
		this.eva_idx = eva_idx;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
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
	public int getMovie_idx() {
		return movie_idx;
	}
	public void setMovie_idx(int movie_idx) {
		this.movie_idx = movie_idx;
	}
}
