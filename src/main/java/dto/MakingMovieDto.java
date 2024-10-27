package dto;

public class MakingMovieDto {
	private int movie_idx;
	private String release_year;
	private String poster;
	private String movie_title;
	private String job;
	private double score;
	public MakingMovieDto(int movie_idx, String release_year, String poster, String movie_title, String job,
			double score) {
		super();
		this.movie_idx = movie_idx;
		this.release_year = release_year;
		this.poster = poster;
		this.movie_title = movie_title;
		this.job = job;
		this.score = score;
	}
	public int getMovie_idx() {
		return movie_idx;
	}
	public void setMovie_idx(int movie_idx) {
		this.movie_idx = movie_idx;
	}
	public String getRelease_year() {
		return release_year;
	}
	public void setRelease_year(String release_year) {
		this.release_year = release_year;
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
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
}
