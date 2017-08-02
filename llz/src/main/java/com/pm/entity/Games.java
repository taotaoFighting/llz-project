package com.pm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Games entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "games", catalog = "project115")
public class Games implements java.io.Serializable {

	// Fields

	private String id;
	private String titie;
	private String describution;
	private Integer hotNumber;
	private String path;
	private String backgroundimagePath;

	// Constructors

	/** default constructor */
	public Games() {
	}

	/** full constructor */
	public Games(String titie, String describution, Integer hotNumber,
			String path, String backgroundimagePath) {
		this.titie = titie;
		this.describution = describution;
		this.hotNumber = hotNumber;
		this.path = path;
		this.backgroundimagePath = backgroundimagePath;
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

	@Column(name = "TITIE_")
	public String getTitie() {
		return this.titie;
	}

	public void setTitie(String titie) {
		this.titie = titie;
	}

	@Column(name = "DESCRIBUTION_")
	public String getDescribution() {
		return this.describution;
	}

	public void setDescribution(String describution) {
		this.describution = describution;
	}

	@Column(name = "HOT_NUMBER")
	public Integer getHotNumber() {
		return this.hotNumber;
	}

	public void setHotNumber(Integer hotNumber) {
		this.hotNumber = hotNumber;
	}

	@Column(name = "PATH_")
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "BACKGROUNDIMAGE_PATH_")
	public String getBackgroundimagePath() {
		return this.backgroundimagePath;
	}

	public void setBackgroundimagePath(String backgroundimagePath) {
		this.backgroundimagePath = backgroundimagePath;
	}

}