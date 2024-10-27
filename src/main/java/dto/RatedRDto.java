package dto;

public class RatedRDto {
	private String poster;
	private String movie_title;
	private String release_year;
	private double score;
	private int movie_idx;
	private int age_idx;
	public RatedRDto(String poster, String movie_title, String release_year, double score, int movie_idx, int age_idx) {
		super();
		this.poster = poster;
		this.movie_title = movie_title;
		this.release_year = release_year;
		this.score = score;
		this.movie_idx = movie_idx;
		this.age_idx = age_idx;
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
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public int getMovie_idx() {
		return movie_idx;
	}
	public void setMovie_idx(int movie_idx) {
		this.movie_idx = movie_idx;
	}
	public int getAge_idx() {
		return age_idx;
	}
	public void setAge_idx(int age_idx) {
		this.age_idx = age_idx;
	}
}
