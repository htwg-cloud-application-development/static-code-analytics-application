package de.htwg.konstanz.cloud.model;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserRep {

    @Id
    private String id;

    private String name;
    private String groupId;
    private String courseId;
    private String duration;
    private String repositoryName;
    private Collection<Assignment> assignments = new LinkedHashSet<Assignment>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getCourseId() {
        return this.courseId;
    }

    public void setCourseId(String courseId) {
        this.groupId = courseId;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Collection<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(Collection<Assignment> assignments) {
		this.assignments = assignments;
	}

	@Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", groupId=" + groupId + ", repositoryName=" + repositoryName
                + ", files=" + assignments + "]";
    }

}
