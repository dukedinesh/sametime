package com.aps.user.sametime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by user on 3/7/2017.
 */

public class OrderBean{

        @SerializedName("order_detail")
        @Expose
        private ArrayList<OrderDetail> orderDetail = null;

        public ArrayList<OrderDetail> getOrderDetail() {
            return orderDetail;
        }

        public void setOrderDetail(ArrayList<OrderDetail> orderDetail) {
            this.orderDetail = orderDetail;
        }
}
