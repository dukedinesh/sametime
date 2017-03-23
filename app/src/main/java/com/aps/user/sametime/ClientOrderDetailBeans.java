package com.aps.user.sametime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 3/12/2017.
 */

public class ClientOrderDetailBeans{

    @SerializedName("order_detail")
    @Expose
    private List<OrderDetail> orderDetail = null;

    public List<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }

}