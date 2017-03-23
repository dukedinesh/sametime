package com.aps.user.sametime;

import android.app.ProgressDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CustomerShowOrders extends AppCompatActivity {

    TextView clientName,companyName,country,state,city,projectName,location,mobile,email,timePeriod,currency,securityFee,rateInINR,totalAmount,slotDate,advanceAmount,balanceAmount,secondAmount, paymentMode, payment_details;
    private Toolbar toolbar;
    String orderid;
    private ArrayList<OrderDetailsBean> orderList;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_show_orders);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        toolbar.setTitle("Order Details");
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        orderList = new ArrayList<>();

        companyName=(TextView) findViewById(R.id.et_companyName);
        clientName=(TextView)findViewById(R.id.et_clientName);
        country=(TextView)findViewById(R.id.country);
        state=(TextView)findViewById(R.id.state);
        city=(TextView)findViewById(R.id.city);
        location=(TextView)findViewById(R.id.et_location);
        mobile=(TextView)findViewById(R.id.et_mobile);
        email=(TextView)findViewById(R.id.et_email);
        projectName=(TextView)findViewById(R.id.projectName);
        timePeriod=(TextView)findViewById(R.id.et_timePeriod);
        currency=(TextView)findViewById(R.id.et_currency);
        securityFee=(TextView)findViewById(R.id.et_securityFee);
        rateInINR=(TextView)findViewById(R.id.et_rateInINR);
        totalAmount=(TextView)findViewById(R.id.et_totalAmount);
        slotDate=(TextView)findViewById(R.id.et_slotDate);
        payment_details = (TextView) findViewById(R.id.payment_details);
        paymentMode = (TextView) findViewById(R.id.paymentMode);
        advanceAmount=(TextView)findViewById(R.id.et_advanceAmount);
        balanceAmount=(TextView)findViewById(R.id.et_balanceAmount);
        secondAmount=(TextView)findViewById(R.id.et_secondAmount);



        orderid=getIntent().getStringExtra("userid");

        pd = new ProgressDialog(CustomerShowOrders.this);
        pd.setMessage("");
        pd.show();

        loadJSON();


    }

    private void loadJSON(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
        Retrofit retro = new Retrofit.Builder().baseUrl("http://apsourcesolutions.in/").client(client).addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        FranchiseAPI request = retro.create(FranchiseAPI.class);

        Call<OrderDetailsBean> call1 = request.orderDetails(orderid);


        call1.enqueue(new Callback<OrderDetailsBean>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<OrderDetailsBean> call, Response<OrderDetailsBean> response) {
                pd.dismiss();

                clientName.setText(response.body().getClientName().toString());
                companyName.setText(response.body().getCompanyName().toString());
                country.setText(response.body().getCountry().toString());
                state.setText(response.body().getState().toString());
                city.setText(response.body().getCity().toString());
                location.setText(response.body().getLocation().toString());
                mobile.setText(response.body().getMobile().toString());
                email.setText(response.body().getEmail().toString());
                timePeriod.setText(response.body().getTimePeriod().toString());
                currency.setText(response.body().getCurrency().toString());
                securityFee.setText(response.body().getSecurityFees().toString());
                rateInINR.setText(response.body().getCurrncyInr().toString());
                totalAmount.setText(response.body().getTotalAmt().toString());
                slotDate.setText(response.body().getSlotDate().toString());
                advanceAmount.setText(response.body().getReceptAmount().toString());
                balanceAmount.setText(response.body().getBalanceAmount().toString());
                secondAmount.setText(response.body().getSecondAmount().toString());
                payment_details.setText(response.body().getPaymentDetails().toString());
                paymentMode.setText(response.body().getPaymentMode().toString());


            }

            @Override
            public void onFailure(Call<OrderDetailsBean> call, Throwable t) {
                Log.d("abc",""+t);
            }
        });
    }

}
