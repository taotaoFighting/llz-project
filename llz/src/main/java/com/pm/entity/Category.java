package com.pm.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Category entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "category", catalog = "project115")
public class Category implements java.io.Serializable {

	// Fields

	private String enumCode;
	private String enumName;
	private String descripution;
	private Timestamp createDate;
	private Timestamp updateDate;

	// Constructors

	/** default constructor */
	public Category() {
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ENUM_CODE_", unique = true, nullable = false, length = 64)
	public String getEnumCode() {
		return this.enumCode;
	}

	public void setEnumCode(String enumCode) {
		this.enumCode = enumCode;
	}

	@Column(name = "ENUM_NAME_", length = 100)
	public String getEnumName() {
		return this.enumName;
	}

	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}

	@Column(name = "DESCRIPUTION_")
	public String getDescripution() {
		return this.descripution;
	}

	public void setDescripution(String descripution) {
		this.descripution = descripution;
	}

	@Column(name = "CREATE_DATE_", length = 19)
	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Column(name = "UPDATE_DATE_", length = 19)
	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

}