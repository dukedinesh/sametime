package com.aps.user.sametime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class FranchiseDashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<OrderDetail> orderList;
    String username,userid,userType,UserId;
    TextView tv_username;
    ProgressDialog pd;
    CardView cv;
    SharedPreferences pref;
    SessionManager session;
    SharedPreferences.Editor edit;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchise_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cv=(CardView)findViewById(R.id.cv);
        pref = getSharedPreferences("pree" , MODE_PRIVATE);
        edit = pref.edit();
        session = new SessionManager(getApplicationContext());


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        HashMap<String, String> user = session.getUserDetails();
        userType=user.get(SessionManager.KEY_TYPE);
        username=user.get(SessionManager.KEY_NAME);

        userid = getIntent().getStringExtra("userid");
        UserId = getIntent().getStringExtra("UserId");

        orderList = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

       pd = new ProgressDialog(FranchiseDashboard.this);
        pd.setMessage("");
        pd.show();

        adapter = new AlbumsAdapter(orderList , FranchiseDashboard.this);
        recyclerView.setAdapter(adapter);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FranchiseDashboard.this, AddOrder.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        tv_username=(TextView)header.findViewById(R.id.username);
        tv_username.setText("WELCOME: "+username);

    }

  @Override
    public void onBackPressed() {
      DrawerLayout  drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadJSON();

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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.order) {
           Intent intent = new Intent(FranchiseDashboard.this, Orders.class);
            intent.putExtra("user_type", UserId);
            startActivity(intent);

        } else if (id == R.id.signature) {
            Intent intent = new Intent(FranchiseDashboard.this, Signatures.class);
            intent.putExtra("user_type", UserId);
            startActivity(intent);

        } else if (id == R.id.logout) {
            session.logoutUser();
                    Intent i = new Intent(FranchiseDashboard.this , LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

                    edit.remove("username");
                    edit.apply();
                    startActivity(i);
                    finish();

                }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadJSON(){
        Retrofit retro = new Retrofit.Builder().baseUrl("http://apsourcesolutions.in/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

         FranchiseAPI request = retro.create(FranchiseAPI.class);
        Call<OrderBean> call = request.orders(UserId);

        call.enqueue(new Callback<OrderBean>() {
            @Override
            public void onResponse(Call<OrderBean> call, Response<OrderBean> response) {

                response.body().getOrderDetail();
                orderList = response.body().getOrderDetail();
                adapter.setGridData(orderList);
                pd.dismiss();
            }

            @Override
            public   void onFailure(Call<OrderBean> call, Throwable t) {

            }
        });
    }
}

