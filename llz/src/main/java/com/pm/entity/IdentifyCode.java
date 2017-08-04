package com.pm.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * IdentifyCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "identifyCode", catalog = "project115")
public class IdentifyCode implements java.io.Serializable {

	// Fields

	private String id;
	private String mobileNumber;
	private String indentifyCode;
	private Integer used;
	private Date createTime;

	// Constructors

	/** default constructor */
	public IdentifyCode() {
	}

	/** full constructor */
	public IdentifyCode(String mobileNumber, String indentifyCode,
			Integer used, Timestamp createTime) {
		this.mobileNumber = mobileNumber;
		this.indentifyCode = indentifyCode;
		this.used = used;
		this.createTime = createTime;
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

	@Column(name = "MOBILE_NUMBER_", length = 100)
	public String getMobileNumber() {
		return this.mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Column(name = "INDENTIFY_CODE_", length = 100)
	public String getIndentifyCode() {
		return this.indentifyCode;
	}

	public void setIndentifyCode(String indentifyCode) {
		this.indentifyCode = indentifyCode;
	}

	@Column(name = "USED_")
	public Integer getUsed() {
		return this.used;
	}

	public void setUsed(Integer used) {
		this.used = used;
	}

	@Column(name = "CREATE_TIME_", length = 19)
	@Temporal(TemporalType.DATE)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}