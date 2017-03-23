package com.aps.user.sametime;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class projectDetailBean {

    @SerializedName("time_period")
    @Expose
    private String timePeriod;
    @SerializedName("security_fees")
    @Expose
    private String securityFees;
    @SerializedName("currency")
    @Expose
    private String currency;

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public String getSecurityFees() {
        return securityFees;
    }

    public void setSecurityFees(String securityFees) {
        this.securityFees = securityFees;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
