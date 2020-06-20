package cn.edu.qhnu.qhsfdx.bean;

import java.io.Serializable;

public class TravalDetail implements Serializable{
	private Integer _id;
	private String name;
	private Integer main_id;
	private String  intro_short;
	private byte[] icon;
	private Integer ord;
	public TravalDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TravalDetail(Integer _id, String name, Integer main_id,
			String intro_short, byte[] icon, Integer ord) {
		super();
		this._id = _id;
		this.name = name;
		this.main_id = main_id;
		this.intro_short = intro_short;
		this.icon = icon;
		this.ord = ord;
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
	public Integer getMain_id() {
		return main_id;
	}
	public void setMain_id(Integer main_id) {
		this.main_id = main_id;
	}
	public String getIntro_short() {
		return intro_short;
	}
	public void setIntro_short(String intro_short) {
		this.intro_short = intro_short;
	}
	public byte[] getIcon() {
		return icon;
	}
	public void setIcon(byte[] icon) {
		this.icon = icon;
	}
	public Integer getOrd() {
		return ord;
	}
	public void setOrd(Integer ord) {
		this.ord = ord;
	}
	

	
}
