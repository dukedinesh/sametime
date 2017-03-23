package com.aps.user.sametime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Orders extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    String userid;
    private ArrayList<OrderBean> orderList;
    private Toolbar toolbar;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

         toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        orderList = new ArrayList<>();


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        toolbar.setTitle("Orders");
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        userid = getIntent().getStringExtra("userid");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view1);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        userid = getIntent().getStringExtra("user_type");
        pd = new ProgressDialog(Orders.this);
        pd.setMessage("");
        pd.show();


    }


    @Override
    protected void onResume() {
        super.onResume();

        loadJSON();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(Orders.this,FranchiseDashboard.class);
                startActivity(intent);
                return(true);
        }

        return super.onOptionsItemSelected(item);
    }
    public void doThis(MenuItem item){
        Intent intent=new Intent(Orders.this,AddOrder.class);
        startActivity(intent);
    }
    private void loadJSON(){
        Retrofit retro = new Retrofit.Builder().baseUrl("http://apsourcesolutions.in/").addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FranchiseAPI request = retro.create(FranchiseAPI.class);
        Call<OrderBean> call = request.orders(userid);

        call.enqueue(new Callback<OrderBean>() {
            @Override
            public void onResponse(Call<OrderBean> call, Response<OrderBean> response) {

                /*Toast.makeText(FranchiseDashboard.this,""+response.body()
                .getOrderDetail().get(0).getClientName(),
                        Toast.LENGTH_LONG).show();*/

                response.body().getOrderDetail();

                adapter = new AlbumsAdapter(response.body().getOrderDetail(),Orders.this);
                recyclerView.setAdapter(adapter);
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<OrderBean> call, Throwable t) {

            }
        });
    }



}
