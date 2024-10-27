package dto;

public class MovieEvaStarDto {
	private String poster;
	private String movie_title;
	private int movie_idx;
	private double score;
	public MovieEvaStarDto(String poster, String movie_title, int movie_idx, double score) {
		super();
		this.poster = poster;
		this.movie_title = movie_title;
		this.movie_idx = movie_idx;
		this.score = score;
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
	public int getMovie_idx() {
		return movie_idx;
	}
	public void setMovie_idx(int movie_idx) {
		this.movie_idx = movie_idx;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
}
