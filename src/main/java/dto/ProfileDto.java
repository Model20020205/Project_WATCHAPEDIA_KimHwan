package dto;

public class ProfileDto {
	private String profile_img;
	private String nickname;
	private String id;
	private int love_eva_idx;
	private int star_idx;
	public ProfileDto(String profile_img, String nickname, String id, int love_eva_idx, int star_idx) {
		super();
		this.profile_img = profile_img;
		this.nickname = nickname;
		this.id = id;
		this.love_eva_idx = love_eva_idx;
		this.star_idx = star_idx;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getLove_eva_idx() {
		return love_eva_idx;
	}
	public void setLove_eva_idx(int love_eva_idx) {
		this.love_eva_idx = love_eva_idx;
	}
	public int getStar_idx() {
		return star_idx;
	}
	public void setStar_idx(int star_idx) {
		this.star_idx = star_idx;
	}
}
