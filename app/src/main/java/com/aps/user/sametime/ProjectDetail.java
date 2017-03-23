package com.aps.user.sametime;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectDetail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("project_name")
    @Expose
    private String projectName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

}
