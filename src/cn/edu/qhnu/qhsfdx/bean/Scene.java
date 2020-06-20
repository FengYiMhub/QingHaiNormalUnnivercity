package cn.edu.qhnu.qhsfdx.bean;

import java.io.Serializable;

import android.graphics.Bitmap;

@SuppressWarnings({ "unused", "serial" })
public  class Scene implements Serializable {
	private Integer _id;
	private String name;
	private Integer x;
	private Integer y;
	private float   lon;
	private float   lat;
	private String  intro_short;
	private byte[] icon;
	private Integer type;
	private Integer spot;
	private Integer ord;
	
	
	
	public Scene(Integer _id, String name, Integer x, Integer y, float lon,
			float lat, String intro_short, byte[] icon, Integer type,
			Integer spot, Integer ord) {
		super();
		this._id = _id;
		this.name = name;
		this.x = x;
		this.y = y;
		this.lon = lon;
		this.lat = lat;
		this.intro_short = intro_short;
		this.icon = icon;
		this.type = type;
		this.spot = spot;
		this.ord = ord;
	}
	
	public Scene(){
		super();
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
	public Integer getX() {
		return x;
	}
	public void setX(Integer x) {
		this.x = x;
	}
	public Integer getY() {
		return y;
	}
	public void setY(Integer y) {
		this.y = y;
	}
	public float getLon() {
		return lon;
	}
	public void setLon(float lon) {
		this.lon = lon;
	}
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSpot() {
		return spot;
	}
	public void setSpot(Integer spot) {
		this.spot = spot;
	}
	public Integer getOrd() {
		return ord;
	}
	public void setOrd(Integer ord) {
		this.ord = ord;
	}
	
}
