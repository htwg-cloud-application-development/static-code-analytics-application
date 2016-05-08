package de.htwg.konstanz.cloud.service;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {

	@Id 
	private String id;
	
	private String user;
	private String repositoryName;
	private Collection<Error>  errors = new LinkedHashSet<Error>();
	private String className;
	
	public User(){	
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getRepositoryName() {
		return repositoryName;
	}

	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}

	public Collection<Error> getErrors() {
		return errors;
	}

	public void setErrors(Collection<Error> errors) {
		this.errors = errors;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", user=" + user + ", repositoryName=" + repositoryName + ", errors=" + errors
				+ ", className=" + className + "]";
	}
	

	

}
