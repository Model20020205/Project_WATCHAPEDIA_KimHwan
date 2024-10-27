package dto;

public class WriteByMemberCommentDto {
	private int movie_idx;
	private String profile_img;
	private String nickname;
	private String poster;
	private String movie_title;
	private String release_year;
	private String content;
	private int love_eva_idx;
	private int reply_idx;
	private int eva_idx;
	private double score;
	public WriteByMemberCommentDto(int movie_idx, String profile_img, String nickname, String poster,
			String movie_title, String release_year, String content, int love_eva_idx, int reply_idx, int eva_idx,
			double score) {
		super();
		this.movie_idx = movie_idx;
		this.profile_img = profile_img;
		this.nickname = nickname;
		this.poster = poster;
		this.movie_title = movie_title;
		this.release_year = release_year;
		this.content = content;
		this.love_eva_idx = love_eva_idx;
		this.reply_idx = reply_idx;
		this.eva_idx = eva_idx;
		this.score = score;
	}
	public int getMovie_idx() {
		return movie_idx;
	}
	public void setMovie_idx(int movie_idx) {
		this.movie_idx = movie_idx;
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
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getMovie_title() {
		return movie_title;
	}
	public void setMovie_title(String movie_title) {
		this.movie_title = movie_title;
	}
	public String getRelease_year() {
		return release_year;
	}
	public void setRelease_year(String release_year) {
		this.release_year = release_year;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public int getEva_idx() {
		return eva_idx;
	}
	public void setEva_idx(int eva_idx) {
		this.eva_idx = eva_idx;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
}
