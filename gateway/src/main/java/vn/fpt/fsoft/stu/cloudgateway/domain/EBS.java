package vn.fpt.fsoft.stu.cloudgateway.domain;

import java.io.Serializable;

public class EBS implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8050744707796298327L;
	private String id;
	private String type;
	private String state;
	private Integer iops;
	private Integer size;
	private String az;
	private Double price;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getIops() {
		return iops;
	}

	public void setIops(Integer iops) {
		this.iops = iops;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getAz() {
		return az;
	}

	public void setAz(String az) {
		this.az = az;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
