package cn.edu.qhnu.qhsfdx.bean;

public class Scene_media {
	private Integer _id;
	private byte[] media;
	private Integer type;
	private Integer main_id;
	public Scene_media(Integer _id,  Integer main_id,Integer type, byte[] media) {
		super();
		this._id = _id;
		this.media = media;
		this.type = type;
		this.main_id = main_id;
	}
	public Scene_media() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer get_id() {
		return _id;
	}
	public void set_id(Integer _id) {
		this._id = _id;
	}
	public byte[] getMedia() {
		return media;
	}
	public void setMedia(byte[] media) {
		this.media = media;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getMain_id() {
		return main_id;
	}
	public void setMain_id(Integer main_id) {
		this.main_id = main_id;
	}
	

}
