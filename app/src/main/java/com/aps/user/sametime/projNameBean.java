package com.aps.user.sametime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class projNameBean {

    @SerializedName("project_detail")
    @Expose
    private List<ProjectDetail> projectDetail = null;

    public List<ProjectDetail> getProjectDetail() {
        return projectDetail;
    }

    public void setProjectDetail(List<ProjectDetail> projectDetail) {
        this.projectDetail = projectDetail;
    }

}
