package com.pm.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Dishes entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "dishes", catalog = "project115")
public class Dishes implements java.io.Serializable {

	// Fields

	private String id;
	private String cuisineId;
	private String title;
	private String describution;
	private String img;
	private Timestamp createTime;
	private Timestamp updateTime;

	// Constructors

	/** default constructor */
	public Dishes() {
	}

	/** minimal constructor */
	public Dishes(String cuisineId) {
		this.cuisineId = cuisineId;
	}

	/** full constructor */
	public Dishes(String cuisineId, String title, String describution,
			String img, Timestamp createTime, Timestamp updateTime) {
		this.cuisineId = cuisineId;
		this.title = title;
		this.describution = describution;
		this.img = img;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "ID_", unique = true, nullable = false, length = 100)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "CUISINE_ID_", nullable = false, length = 100)
	public String getCuisineId() {
		return this.cuisineId;
	}

	public void setCuisineId(String cuisineId) {
		this.cuisineId = cuisineId;
	}

	@Column(name = "TITLE_")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "DESCRIBUTION_")
	public String getDescribution() {
		return this.describution;
	}

	public void setDescribution(String describution) {
		this.describution = describution;
	}

	@Column(name = "IMG_")
	public String getImg() {
		return this.img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Column(name = "CREATE_TIME_", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_TIME_", length = 19)
	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}