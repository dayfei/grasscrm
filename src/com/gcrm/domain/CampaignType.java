package com.gcrm.domain;

import java.io.Serializable;

/**
 * @hibernate.class table = "campaignType" dynamic-update = "true" dynamic-insert =
 *                  "true" lazy="false"
 */
public class CampaignType implements Serializable {

	private static final long serialVersionUID = 8250950813769457556L;

	private Integer id; 
	private String name;
	private Integer sequence;

	/**
	 * @hibernate.id column="id" generator-class="native" unsaved-value="null"
	 */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @hibernate.property length="50" not-null="true"
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @hibernate.property type="int"
	 */	
	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	  @Override
	  public String toString()
	  {
	    StringBuilder builder = new StringBuilder();
	    builder.append("AccountCategory [id=");
	    builder.append(id);
	    builder.append(", name=");
	    builder.append(name);
	    builder.append(", sequence=");
	    builder.append(sequence);
	    builder.append("]");
	    return builder.toString();
	  }	
}
