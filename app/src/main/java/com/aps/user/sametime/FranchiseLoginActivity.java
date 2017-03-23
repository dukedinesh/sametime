package com.aps.user.sametime;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class FranchiseLoginActivity extends AppCompatActivity {
    EditText username, password;
    Button btn_login;
    private Toolbar toolbar;
    ProgressBar progressBar;
    String user_type,s1,s2;
    Toast toast;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchise_login);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        toolbar.setTitle("Franchise Login");
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }});

        username = (EditText) findViewById(R.id.fuser_name);
        password = (EditText) findViewById(R.id.f_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        toast = Toast.makeText(this , null , Toast.LENGTH_SHORT);
        user_type = getIntent().getStringExtra("franchise_user_type");
        pref = getSharedPreferences("pree" , MODE_PRIVATE);
        edit = pref.edit();
        session = new SessionManager(getApplicationContext());



      /*  username.setText("gurgaon");
        password.setText("info@1234");*/
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar = (ProgressBar) findViewById(R.id.progress);

                login();

            }
        });


    }

    public void login(){


        s1 = username.getText().toString();
        s2 = password.getText().toString();
        if (s1.length()>0)
        {

            if (s2.length()>0) {
                progressBar.setVisibility(View.VISIBLE);

                isNetworkConnectionAvailable();

                Retrofit retro = new Retrofit.Builder().baseUrl("http://apsourcesolutions.in/").addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                final FranchiseAPI request = retro.create(FranchiseAPI.class);

                Log.d("asdasd", s1);
                Log.d("asdasd", s2);
                Log.d("asdasd", user_type);

                Call<loginBean> call = request.login(s1, s2, user_type);

                call.enqueue(new Callback<loginBean>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(Call<loginBean> call, Response<loginBean> response) {

                        if (Objects.equals(response.body().getStatus(), "Login Successfull.")) {



                            User b = (User) getApplicationContext();
                            b.name = response.body().getUsename();
                            b.id = response.body().getUserid();

                            progressBar.setVisibility(View.INVISIBLE);
                            session.createLoginSession(response.body().getUsename(),s2,user_type);
                            Intent intent = new Intent(FranchiseLoginActivity.this, FranchiseDashboard.class);
                            intent.putExtra("UserId", response.body().getUserid());
                            startActivity(intent);
                            finish();

                        } else if (Objects.equals(response.body().getStatus(), "Invalid Login Detail.")) {
                            toast.setText("Invalid details");
                            toast.show();
                            username.setError("Invalid details");
                            password.setError("Invalid details");
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<loginBean> call, Throwable t) {
                        progressBar.setVisibility(View.INVISIBLE);

                    }


                });
            }
            else
            {
                password.setError("This field is required");
                toast.show();
            }

        }else
        {
            username.setError("This field is required");
            toast.show();
        }

    }
    public void checkNetworkConnection(){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("No internet Connection");
        builder.setMessage("Please turn on internet connection to continue");
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public boolean isNetworkConnectionAvailable(){
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if(isConnected) {
            Log.d("Network", "Connected");
            return true;
        }
        else{
            checkNetworkConnection();
            Log.d("Network","Not Connected");
            return false;
        }
    }
        }