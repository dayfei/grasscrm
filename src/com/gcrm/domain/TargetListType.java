package com.gcrm.domain;

import java.io.Serializable;


public class TargetListType implements Serializable {

	private static final long serialVersionUID = 8250950813769457556L;

	private Integer id; 
	private String name;
	private Integer sequence;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
