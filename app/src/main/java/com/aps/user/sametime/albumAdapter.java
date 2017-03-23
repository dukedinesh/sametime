package com.aps.user.sametime;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 3/3/2017.
 */

public class albumAdapter extends RecyclerView.Adapter<albumAdapter.MyViewHolder> {

    private List<OrderDetail> orderList = new ArrayList<>();
    Context ctx;




    public albumAdapter(List<OrderDetail> albumList, Context ctx) {

        this.orderList = albumList;
        this.ctx=ctx;

    }


    public void setGridData(List<OrderDetail> list)
    {
        this.orderList = list;
        notifyDataSetChanged();
    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orderlist, parent, false);
        MyViewHolder myViewHolder=new MyViewHolder(itemView,ctx,orderList);
        return myViewHolder ;
    }

    @Override
    public void onBindViewHolder(albumAdapter.MyViewHolder holder, int position) {
        OrderDetail order = orderList.get(position);

        holder.client_name .setText(order.getClientName());
        holder.company_name.setText(order.getCompanyName());
        holder.order_id.setText(order.getOrderNo());
        holder.order_date.setText(order.getOrderDate());
        Log.d("sdsfc",""+order.getClientName());

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView order_id, order_date, client_name, company_name;
        List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
        Context ctx;


        public MyViewHolder(View view, Context ctx, List<OrderDetail> orderDetails) {

            super(view);
            this.orderDetails = orderDetails;
            this.ctx = ctx;
            view.setOnClickListener(this);

            order_date = (TextView) view.findViewById(R.id.order_date);
            order_id = (TextView) view.findViewById(R.id.order_id);
            client_name = (TextView) view.findViewById(R.id.client_name);
            company_name = (TextView) view.findViewById(R.id.company_name);

        }

        @Override
        public void onClick(View view) {
            OrderDetail item = orderDetails.get(getLayoutPosition());
            Intent intent = new Intent(ctx, CustomerShowOrders.class);
            intent.putExtra("userid",item.getId() );
            ctx.startActivity(intent);


        }

    }
}


