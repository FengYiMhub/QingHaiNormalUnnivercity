package cn.edu.qhnu.qhsfdx.bean;

public class Traval {
	private Integer _id;
	private String name;
	private byte[] icon;
	private Integer tag;

	public Traval() {
		super();
	}

	public Traval(Integer _id, String name, byte[] icon,
			Integer tag) {
		super();
		this._id = _id;
		this.name = name;
		this.icon = icon;
		this.tag = tag;
	}

	public Integer get_id() {
		return _id;
	}

	public void set_id(Integer _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getIcon() {
		return icon;
	}

	public void setIcon(byte[] icon) {
		this.icon = icon;
	}

	public Integer getTag() {
		return tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}
}
