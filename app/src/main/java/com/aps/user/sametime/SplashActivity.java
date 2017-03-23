package com.aps.user.sametime;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SplashActivity extends AppCompatActivity {
    SessionManager sessionManager;
    String userid, username, userType;
    ProgressDialog pd;
    ProgressBar progressBar;
    Toast toast;
    Timer t;
    String[] PERMISSIONS = { Manifest.permission.WRITE_EXTERNAL_STORAGE };
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        toast = Toast.makeText(this, null, Toast.LENGTH_LONG);
        pd = new ProgressDialog(SplashActivity.this);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        t = new Timer();


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int hasLocationPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
                //request permission
                Log.d("awa", "1");
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
            } else {

                t.schedule(new TimerTask() {
                               @Override
                               public void run() {
                                   Log.d("aaaaaaaaaaa", "aaaaaaaa");

                                   runOnUiThread(new Runnable() {
                                       @Override
                                       public void run() {

                                           sessionManager = new SessionManager(getApplicationContext());
                                           HashMap<String, String> user = sessionManager.getUserDetails();
                                           userType = user.get(SessionManager.KEY_TYPE);
                                           username = user.get(SessionManager.KEY_NAME);
                                           userid = user.get(SessionManager.KEY_PASSWORD);

                                           if (!sessionManager.isLoggedIn())

                                           {
                                               Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                               startActivity(intent);
                                               finish();
                                           } else if (Objects.equals(userType, "Franchise")) {


                                               progressBar.setVisibility(View.VISIBLE);
                                               isNetworkConnectionAvailable();
                                               Retrofit retro = new Retrofit.Builder().baseUrl("http://apsourcesolutions.in/").addConverterFactory(ScalarsConverterFactory.create())
                                                       .addConverterFactory(GsonConverterFactory.create())
                                                       .build();

                                               final FranchiseAPI request = retro.create(FranchiseAPI.class);
                                               Call<loginBean> call = request.login(username, userid, userType);

                                               call.enqueue(new Callback<loginBean>() {
                                                   @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                                   @Override
                                                   public void onResponse(Call<loginBean> call, Response<loginBean> response) {

                                                       if (Objects.equals(response.body().getStatus(), "Login Successfull.")) {

                                                           User b = (User) getApplicationContext();
                                                           b.name = response.body().getUsename();
                                                           b.id = response.body().getUserid();
                                                           progressBar.setVisibility(View.GONE);
                                                           Intent intent = new Intent(SplashActivity.this, FranchiseDashboard.class);
                                                           intent.putExtra("user_type", userType);
                                                           intent.putExtra("username", username);
                                                           intent.putExtra("UserId", response.body().getUserid());
                                                           startActivity(intent);

                                                           finish();

                                                       } else if (Objects.equals(response.body().getStatus(), "Invalid Login Detail.")) {
                                                           toast.setText("Invalid details");
                                                           toast.show();

                                                       }

                                                   }

                                                   @Override
                                                   public void onFailure(Call<loginBean> call, Throwable t) {

                                                   }


                                               });


                                           } else if (Objects.equals(userType, "Customer")) {
                                               progressBar.setVisibility(View.VISIBLE);
                                               Retrofit retro = new Retrofit.Builder().baseUrl("http://apsourcesolutions.in/").addConverterFactory(ScalarsConverterFactory.create())
                                                       .addConverterFactory(GsonConverterFactory.create())
                                                       .build();

                                               final FranchiseAPI request = retro.create(FranchiseAPI.class);

                                               Call<loginBean> call = request.login(username, userid, userType);

                                               call.enqueue(new Callback<loginBean>() {
                                                   @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                                   @Override
                                                   public void onResponse(Call<loginBean> call, Response<loginBean> response) {

                                                       if (Objects.equals(response.body().getStatus(), "Login Successfull.")) {

                                                           progressBar.setVisibility(View.GONE);
                                                           User b = (User) getApplicationContext();
                                                           b.name = response.body().getUsename();
                                                           b.id = response.body().getUserid();

                                                           Intent intent = new Intent(SplashActivity.this, CustomerDashboard.class);
                                                           intent.putExtra("user_type", userType);
                                                           intent.putExtra("username", username);
                                                           intent.putExtra("UserId", response.body().getUserid());
                                                           startActivity(intent);
                                                           finish();

                                                       } else if (Objects.equals(response.body().getStatus(), "Invalid Login Detail.")) {
                                                           toast.setText("Invalid details");
                                                           toast.show();

                                                       }

                                                   }

                                                   @Override
                                                   public void onFailure(Call<loginBean> call, Throwable t) {

                                                   }


                                               });
                                           }


                                       }
                                   });
                               }
                           }
                        , 1500);
            }
        }

        else {

            t.schedule(new TimerTask() {
                           @Override
                           public void run() {


                               runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {

                                       sessionManager = new SessionManager(getApplicationContext());
                                       HashMap<String, String> user = sessionManager.getUserDetails();
                                       userType = user.get(SessionManager.KEY_TYPE);
                                       username = user.get(SessionManager.KEY_NAME);
                                       userid = user.get(SessionManager.KEY_PASSWORD);

                                       if (!sessionManager.isLoggedIn())

                                       {
                                           Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                           startActivity(intent);
                                           finish();
                                       } else if (Objects.equals(userType, "Franchise")) {


                                           progressBar.setVisibility(View.VISIBLE);
                                           isNetworkConnectionAvailable();

                                           Retrofit retro = new Retrofit.Builder().baseUrl("http://apsourcesolutions.in/").addConverterFactory(ScalarsConverterFactory.create())
                                                   .addConverterFactory(GsonConverterFactory.create())
                                                   .build();

                                           final FranchiseAPI request = retro.create(FranchiseAPI.class);
                                           Call<loginBean> call = request.login(username, userid, userType);

                                           call.enqueue(new Callback<loginBean>() {
                                               @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                               @Override
                                               public void onResponse(Call<loginBean> call, Response<loginBean> response) {

                                                   if (Objects.equals(response.body().getStatus(), "Login Successfull.")) {

                                                       User b = (User) getApplicationContext();
                                                       b.name = response.body().getUsename();
                                                       b.id = response.body().getUserid();
                                                       progressBar.setVisibility(View.GONE);
                                                       Intent intent = new Intent(SplashActivity.this, FranchiseDashboard.class);
                                                       intent.putExtra("user_type", userType);
                                                       intent.putExtra("username", username);
                                                       intent.putExtra("UserId", response.body().getUserid());
                                                       startActivity(intent);
                                                       finish();

                                                   } else if (Objects.equals(response.body().getStatus(), "Invalid Login Detail.")) {
                                                       toast.setText("Invalid details");
                                                       toast.show();

                                                   }

                                               }

                                               @Override
                                               public void onFailure(Call<loginBean> call, Throwable t) {

                                               }


                                           });


                                       } else if (Objects.equals(userType, "Customer")) {
                                           progressBar.setVisibility(View.VISIBLE);
                                           Retrofit retro = new Retrofit.Builder().baseUrl("http://apsourcesolutions.in/").addConverterFactory(ScalarsConverterFactory.create())
                                                   .addConverterFactory(GsonConverterFactory.create())
                                                   .build();

                                           final FranchiseAPI request = retro.create(FranchiseAPI.class);

                                           Call<loginBean> call = request.login(username, userid, userType);

                                           call.enqueue(new Callback<loginBean>() {
                                               @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                               @Override
                                               public void onResponse(Call<loginBean> call, Response<loginBean> response) {

                                                   if (Objects.equals(response.body().getStatus(), "Login Successfull.")) {

                                                       User b = (User) getApplicationContext();
                                                       b.name = response.body().getUsename();
                                                       b.id = response.body().getUserid();
                                                       progressBar.setVisibility(View.GONE);
                                                       Intent intent = new Intent(SplashActivity.this, CustomerDashboard.class);
                                                       intent.putExtra("user_type", userType);
                                                       intent.putExtra("username", username);
                                                       intent.putExtra("UserId", response.body().getUserid());
                                                       startActivity(intent);
                                                       finish();

                                                   } else if (Objects.equals(response.body().getStatus(), "Invalid Login Detail.")) {
                                                       toast.setText("Invalid details");
                                                       toast.show();

                                                   }

                                               }

                                               @Override
                                               public void onFailure(Call<loginBean> call, Throwable t) {

                                               }


                                           });
                                       }


                                   }
                               });
                           }
                       }
                    , 1500);
        }




    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d("asdasdasd2", "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                //perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);

                //perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE , PackageManager.PERMISSION_GRANTED);
                //perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++) {
                        Log.d("aasdloop", String.valueOf(i));
                        perms.put(permissions[i], grantResults[i]);
                    }

                    // Check for both permissions
                    if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d("asdasdasd3", "sms & location services permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted


                        t.schedule(new TimerTask() {
                                       @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                       @Override
                                       public void run() {
Log.d("ddddddddddd","dddddddddddddddddd");

                                           runOnUiThread(new Runnable() {
                                                             @Override
                                                             public void run() {


                                           sessionManager = new SessionManager(getApplicationContext());
                                           HashMap<String, String> user = sessionManager.getUserDetails();
                                           userType = user.get(SessionManager.KEY_TYPE);
                                           username = user.get(SessionManager.KEY_NAME);
                                           userid = user.get(SessionManager.KEY_PASSWORD);
                                           if (!sessionManager.isLoggedIn())

                                           {
                                               Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                               startActivity(intent);
                                               finish();
                                           } else if (Objects.equals(userType, "Franchise")) {


                                               progressBar.setVisibility(View.VISIBLE);
                                               isNetworkConnectionAvailable();
                                               Retrofit retro = new Retrofit.Builder().baseUrl("http://apsourcesolutions.in/").addConverterFactory(ScalarsConverterFactory.create())
                                                       .addConverterFactory(GsonConverterFactory.create())
                                                       .build();

                                               final FranchiseAPI request = retro.create(FranchiseAPI.class);
                                               Call<loginBean> call = request.login(username, userid, userType);

                                               call.enqueue(new Callback<loginBean>() {
                                                   @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                                   @Override
                                                   public void onResponse(Call<loginBean> call, Response<loginBean> response) {

                                                       if (Objects.equals(response.body().getStatus(), "Login Successfull.")) {

                                                           User b = (User) getApplicationContext();
                                                           b.name = response.body().getUsename();
                                                           b.id = response.body().getUserid();


                                                           Intent intent = new Intent(SplashActivity.this, FranchiseDashboard.class);
                                                           intent.putExtra("user_type", userType);
                                                           intent.putExtra("username", username);
                                                           intent.putExtra("UserId", response.body().getUserid());
                                                           startActivity(intent);
                                                           finish();
                                                           progressBar.setVisibility(View.GONE);

                                                       } else if (Objects.equals(response.body().getStatus(), "Invalid Login Detail.")) {
                                                           toast.setText("Invalid details");
                                                           toast.show();

                                                       }

                                                   }

                                                   @Override
                                                   public void onFailure(Call<loginBean> call, Throwable t) {

                                                   }


                                               });


                                           } else if (Objects.equals(userType, "Customer")) {
                                               progressBar.setVisibility(View.VISIBLE);
                                               isNetworkConnectionAvailable();
                                               Retrofit retro = new Retrofit.Builder().baseUrl("http://apsourcesolutions.in/").addConverterFactory(ScalarsConverterFactory.create())
                                                       .addConverterFactory(GsonConverterFactory.create())
                                                       .build();

                                               final FranchiseAPI request = retro.create(FranchiseAPI.class);

                                               Call<loginBean> call = request.login(username, userid, userType);

                                               call.enqueue(new Callback<loginBean>() {
                                                   @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                                   @Override
                                                   public void onResponse(Call<loginBean> call, Response<loginBean> response) {

                                                       if (Objects.equals(response.body().getStatus(), "Login Successfull.")) {

                                                           User b = (User) getApplicationContext();
                                                           b.name = response.body().getUsename();
                                                           b.id = response.body().getUserid();

                                                           Intent intent = new Intent(SplashActivity.this, CustomerDashboard.class);
                                                           intent.putExtra("user_type", userType);
                                                           intent.putExtra("username", username);
                                                           intent.putExtra("UserId", response.body().getUserid());
                                                           startActivity(intent);
                                                           finish();
                                                           progressBar.setVisibility(View.GONE);

                                                       } else if (Objects.equals(response.body().getStatus(), "Invalid Login Detail.")) {
                                                           toast.setText("Invalid details");
                                                           toast.show();

                                                       }

                                                   }

                                                   @Override
                                                   public void onFailure(Call<loginBean> call, Throwable t) {
                                                   }


                                               });
                                           }


                                                             }
                                           });
                                       }
                                   }
                                , 1500);
                    }
                else {
                        Log.d("asdasdas4", "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this , Manifest.permission.WRITE_EXTERNAL_STORAGE) ) {

                            Toast.makeText(getApplicationContext() , "Permissions are required for this app" , Toast.LENGTH_SHORT).show();
                            finish();

                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            finish();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
                break;
            }


        }

    }

    public void checkNetworkConnection(){
        android.app.AlertDialog.Builder builder =new android.app.AlertDialog.Builder(this);
        builder.setTitle("No internet Connection");
        builder.setMessage("Please turn on internet connection to continue");
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(SplashActivity.this,"No Internet Connection!",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        android.app.AlertDialog alertDialog = builder.create();
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