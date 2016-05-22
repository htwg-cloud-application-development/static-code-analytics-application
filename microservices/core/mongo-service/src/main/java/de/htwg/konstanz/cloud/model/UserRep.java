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
    private String repositoryName;
    private Collection<File> files = new LinkedHashSet<File>();

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

    public Collection<File> getFiles() {
        return files;
    }

    public void setFiles(Collection<File> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", groupId=" + groupId + ", repositoryName=" + repositoryName
                + ", files=" + files + "]";
    }

}
