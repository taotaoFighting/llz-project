 package com.pm.entity;

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
 * Padvice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Padvice", catalog = "project115")
public class Padvice implements java.io.Serializable {

	// Fields

	private String id;
	private String idea;
	private String advice;
	private String name;
	private Date updateDate;


	/** default constructor */
	public Padvice() {
	}

	/** full constructor */
	public Padvice(String idea, String advice, String name) {
		this.idea = idea;
		this.advice = advice;
		this.name = name;
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

	@Column(name = "IDEA_")
	public String getIdea() {
		return this.idea;
	}

	public void setIdea(String idea) {
		this.idea = idea;
	}

	@Column(name = "ADVICE_")
	public String getAdvice() {
		return this.advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	@Column(name = "NAME_")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "UPDATETIME_")
	@Temporal(TemporalType.DATE)
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	

}