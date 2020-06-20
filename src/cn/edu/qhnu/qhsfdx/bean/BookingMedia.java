package cn.edu.qhnu.qhsfdx.bean;

public class BookingMedia {
	private Integer _id;
	private Integer main_id;
	private String title;
	private byte[] media;
	private String  introduction;
	public BookingMedia() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BookingMedia(Integer _id, Integer main_id, String title,
			byte[] media, String introduction) {
		super();
		this._id = _id;
		this.main_id = main_id;
		this.title = title;
		this.media = media;
		this.introduction = introduction;
	}
	public Integer get_id() {
		return _id;
	}
	public void set_id(Integer _id) {
		this._id = _id;
	}
	public Integer getMain_id() {
		return main_id;
	}
	public void setMain_id(Integer main_id) {
		this.main_id = main_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public byte[] getMedia() {
		return media;
	}
	public void setMedia(byte[] media) {
		this.media = media;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	

}
