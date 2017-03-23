package com.aps.user.sametime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 3/7/2017.
 */

public class OrderDetail {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("order_no")
    @Expose
    private String orderNo;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("client_name")
    @Expose
    private String clientName;
    @SerializedName("company_name")
    @Expose
    private String companyName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}

