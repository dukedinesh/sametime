package com.aps.user.sametime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 3/6/2017.
 */

public class loginBean {

    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("usename")
    @Expose
    private String usename;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("status")
    @Expose
    private String status;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsename() {
        return usename;
    }

    public void setUsename(String usename) {
        this.usename = usename;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}