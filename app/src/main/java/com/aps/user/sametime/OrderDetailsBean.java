package com.aps.user.sametime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 3/9/2017.
 */

public class OrderDetailsBean {

    @SerializedName("client_name")
    @Expose
    private Object clientName;
    @SerializedName("company_name")
    @Expose
    private Object companyName;
    @SerializedName("country")
    @Expose
    private Object country;
    @SerializedName("state")
    @Expose
    private Object state;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("location")
    @Expose
    private Object location;
    @SerializedName("mobile")
    @Expose
    private Object mobile;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("project_name")
    @Expose
    private Object projectName;
    @SerializedName("slot_date")
    @Expose
    private Object slotDate;
    @SerializedName("time_period")
    @Expose
    private Object timePeriod;
    @SerializedName("currency")
    @Expose
    private Object currency;
    @SerializedName("security_fees")
    @Expose
    private Object securityFees;
    @SerializedName("currncy_inr")
    @Expose
    private Object currncyInr;
    @SerializedName("total_amt")
    @Expose
    private Object totalAmt;
    @SerializedName("recept_amount")
    @Expose
    private Object receptAmount;
    @SerializedName("balance_amount")
    @Expose
    private Object balanceAmount;
    @SerializedName("second_amount")
    @Expose
    private Object secondAmount;
    @SerializedName("payment_mode")
    @Expose
    private Object paymentMode;
    @SerializedName("payment_details")
    @Expose
    private Object paymentDetails;
    @SerializedName("signature_img")
    @Expose
    private String signatureImg;

    public Object getClientName() {
        return clientName;
    }

    public void setClientName(Object clientName) {
        this.clientName = clientName;
    }

    public Object getCompanyName() {
        return companyName;
    }

    public void setCompanyName(Object companyName) {
        this.companyName = companyName;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public Object getMobile() {
        return mobile;
    }

    public void setMobile(Object mobile) {
        this.mobile = mobile;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getProjectName() {
        return projectName;
    }

    public void setProjectName(Object projectName) {
        this.projectName = projectName;
    }

    public Object getSlotDate() {
        return slotDate;
    }

    public void setSlotDate(Object slotDate) {
        this.slotDate = slotDate;
    }

    public Object getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(Object timePeriod) {
        this.timePeriod = timePeriod;
    }

    public Object getCurrency() {
        return currency;
    }

    public void setCurrency(Object currency) {
        this.currency = currency;
    }

    public Object getSecurityFees() {
        return securityFees;
    }

    public void setSecurityFees(Object securityFees) {
        this.securityFees = securityFees;
    }

    public Object getCurrncyInr() {
        return currncyInr;
    }

    public void setCurrncyInr(Object currncyInr) {
        this.currncyInr = currncyInr;
    }

    public Object getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(Object totalAmt) {
        this.totalAmt = totalAmt;
    }

    public Object getReceptAmount() {
        return receptAmount;
    }

    public void setReceptAmount(Object receptAmount) {
        this.receptAmount = receptAmount;
    }

    public Object getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Object balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Object getSecondAmount() {
        return secondAmount;
    }

    public void setSecondAmount(Object secondAmount) {
        this.secondAmount = secondAmount;
    }

    public Object getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Object paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Object getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(Object paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public String getSignatureImg() {
        return signatureImg;
    }

    public void setSignatureImg(String signatureImg) {
        this.signatureImg = signatureImg;
    }

}