package com.pm.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * EnumCuisine entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "enumCuisine", catalog = "project115")
public class EnumCuisine implements java.io.Serializable {

	// Fields

	private String id;
	private String enumCode;
	private String enumName;
	private Timestamp updateTime;

	// Constructors

	/** default constructor */
	public EnumCuisine() {
	}

	/** full constructor */
	public EnumCuisine(String enumCode, String enumName, Timestamp updateTime) {
		this.enumCode = enumCode;
		this.enumName = enumName;
		this.updateTime = updateTime;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID_", unique = true, nullable = false)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "ENUM_CODE_", length = 100)
	public String getEnumCode() {
		return this.enumCode;
	}

	public void setEnumCode(String enumCode) {
		this.enumCode = enumCode;
	}

	@Column(name = "ENUM_NAME", length = 100)
	public String getEnumName() {
		return this.enumName;
	}

	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}

	@Column(name = "UPDATE_TIME_", length = 19)
	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}