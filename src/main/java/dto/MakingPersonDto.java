package dto;

public class MakingPersonDto {
	private String person_img;
	private String person_name;
	private String job;
	private int person_idx;
	public MakingPersonDto(String person_img, String person_name, String job, int person_idx) {
		super();
		this.person_img = person_img;
		this.person_name = person_name;
		this.job = job;
		this.person_idx = person_idx;
	}
	public String getPerson_img() {
		return person_img;
	}
	public void setPerson_img(String person_img) {
		this.person_img = person_img;
	}
	public String getPerson_name() {
		return person_name;
	}
	public void setPerson_name(String person_name) {
		this.person_name = person_name;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public int getPerson_idx() {
		return person_idx;
	}
	public void setPerson_idx(int person_idx) {
		this.person_idx = person_idx;
	}
}
