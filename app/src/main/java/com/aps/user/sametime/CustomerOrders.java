package com.aps.user.sametime;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CustomerOrders extends AppCompatActivity {
    private RecyclerView recyclerView;
    private albumAdapter adapter;
    String userid;
    private ArrayList<ClientOrderDetailBeans> orderList;
    private Toolbar toolbar;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_orders);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        orderList = new ArrayList<>();


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        toolbar.setTitle("Order");
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        userid = getIntent().getStringExtra("user_type");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view1);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        pd = new ProgressDialog(CustomerOrders.this);
        pd.setMessage("");
        pd.show();
        loadJSON();

    }




    private void loadJSON(){
        Retrofit retro = new Retrofit.Builder().baseUrl("http://apsourcesolutions.in/").addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FranchiseAPI request = retro.create(FranchiseAPI.class);
        Call<ClientOrderDetailBeans> call = request.clientOrder(userid);

        call.enqueue(new Callback<ClientOrderDetailBeans>() {
            @Override
            public void onResponse(Call<ClientOrderDetailBeans> call, Response<ClientOrderDetailBeans> response) {

                response.body().getOrderDetail();

                adapter = new albumAdapter(response.body().getOrderDetail(),CustomerOrders.this);
                recyclerView.setAdapter(adapter);
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<ClientOrderDetailBeans> call, Throwable t) {

            }
        });
    }



}
