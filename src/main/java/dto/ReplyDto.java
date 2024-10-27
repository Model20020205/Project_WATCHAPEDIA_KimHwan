package dto;

public class ReplyDto {
	private int reply_idx;
	private int mm_idx;
	private String profile_img;
	private String nickname;
	private String reply_content;
	private String reply_date;
	private int reply_like_idx;
	public ReplyDto(int reply_idx, int mm_idx, String profile_img, String nickname, String reply_content,
			String reply_date, int reply_like_idx) {
		super();
		this.reply_idx = reply_idx;
		this.mm_idx = mm_idx;
		this.profile_img = profile_img;
		this.nickname = nickname;
		this.reply_content = reply_content;
		this.reply_date = reply_date;
		this.reply_like_idx = reply_like_idx;
	}
	public int getReply_idx() {
		return reply_idx;
	}
	public void setReply_idx(int reply_idx) {
		this.reply_idx = reply_idx;
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
	public String getReply_content() {
		return reply_content;
	}
	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}
	public String getReply_date() {
		return reply_date;
	}
	public void setReply_date(String reply_date) {
		this.reply_date = reply_date;
	}
	public int getReply_like_idx() {
		return reply_like_idx;
	}
	public void setReply_like_idx(int reply_like_idx) {
		this.reply_like_idx = reply_like_idx;
	}
}
