package io.hastentech.ppmtool.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Project Name is required")
	private String projectName;
	
	@NotBlank(message = "Project Identifier is required")
	@Size(min = 4, max=5, message = "length shoud be 4-5 character" )
	@Column(updatable = false, unique = true)
	private String projectIdentifier;
	
	@NotBlank(message = "description is required")
	private String description;
	
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date start_date;
	
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date end_date;
	
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date create_At;
	
	@JsonFormat(pattern = "yyyy-mm-dd")
	private Date update_At;
	
	public Project() {
		
	}
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getProjectName() {
		return projectName;
	}



	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}



	public String getProjectIdentifier() {
		return projectIdentifier;
	}



	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public Date getStart_date() {
		return start_date;
	}



	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}



	public Date getEnd_date() {
		return end_date;
	}



	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}



	public Date getCreate_At() {
		return create_At;
	}



	public void setCreate_At(Date create_At) {
		this.create_At = create_At;
	}



	public Date getUpdate_At() {
		return update_At;
	}



	public void setUpdate_At(Date update_At) {
		this.update_At = update_At;
	}



	@PrePersist
	protected void onCreate() {
		this.create_At = new Date();
		
	}
	
	@PostPersist
	protected void onUpdate() {
		this.update_At = new Date();
	}
	
	
}