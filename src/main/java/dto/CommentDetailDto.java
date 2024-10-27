package dto;

public class CommentDetailDto {
	private String profile_img;
	private String nickname;
	private String release_year;
	private String write_date;
	private String movie_title;
	private String poster;
	private double score;
	private String content;
	private int love_eva_idx;
	private int reply_eva_idx;
	public CommentDetailDto(String profile_img, String nickname, String release_year, String write_date,
			String movie_title, String poster, double score, String content, int love_eva_idx, int reply_eva_idx) {
		super();
		this.profile_img = profile_img;
		this.nickname = nickname;
		this.release_year = release_year;
		this.write_date = write_date;
		this.movie_title = movie_title;
		this.poster = poster;
		this.score = score;
		this.content = content;
		this.love_eva_idx = love_eva_idx;
		this.reply_eva_idx = reply_eva_idx;
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
	public String getRelease_year() {
		return release_year;
	}
	public void setRelease_year(String release_year) {
		this.release_year = release_year;
	}
	public String getWrite_date() {
		return write_date;
	}
	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}
	public String getMovie_title() {
		return movie_title;
	}
	public void setMovie_title(String movie_title) {
		this.movie_title = movie_title;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
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
}
