package com.aps.user.sametime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Signatures extends AppCompatActivity {
    private Toolbar toolbar;
    String userid,sigId;
    TextView text,tvText;
     int total1;
    ImageButton edit;

    ProgressDialog pd;
    ImageView signature1;

    List<SignatureDetail> sig;
    List<String> da;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signatures);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        toolbar.setTitle("Signature");
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sig=new ArrayList<>();
        da=new ArrayList<>();


        signature1=(ImageView)findViewById(R.id.image);
        text=(TextView)findViewById(R.id.text);
        tvText=(TextView)findViewById(R.id.tvText);
        /*edit = (ImageButton)findViewById(R.id.edit);*/

        userid = getIntent().getStringExtra("user_type");




/*
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Signatures.this , UpdateSignature.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id" ,sigId);
                startActivity(intent);

            }
        });*/



    }


    @Override
    protected void onResume() {
        super.onResume();






    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        pd = new ProgressDialog(Signatures.this);
        pd.setMessage("");
        pd.show();

        Retrofit retro = new Retrofit.Builder().baseUrl("http://apsourcesolutions.in/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FranchiseAPI request = retro.create(FranchiseAPI.class);
        Call<SignatureBean> call = request.getsignature(userid);

        call.enqueue(new Callback<SignatureBean>() {
            @Override
            public void onResponse(Call<SignatureBean> call, Response<SignatureBean> response) {

                sig=response.body().getSignatureDetail();
                if (sig.size()>0) {
                    ImageLoader loader = ImageLoader.getInstance();
                    loader.displayImage(sig.get(0).getSignatureImg(), signature1);
                    sigId=sig.get(0).getId();
                    final String total=response.body().getTotal();
                    total1=Integer.parseInt(total);


                    Log.d("llll",""+total1);
                    if (total1>0){
                        getMenuInflater().inflate(R.menu.update, menu);

                    }
                    else
                    {
                        getMenuInflater().inflate(R.menu.main, menu);
                        /*edit.setVisibility(View.GONE);*/
                        tvText.setText("No Signature found. ");
                    }

                    pd.dismiss();
                    Log.d("aaaa",""+total1);
                }

            }

            @Override
            public   void onFailure(Call<SignatureBean> call, Throwable t) {

            }
        });






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
        else if (id == R.id.edit) {

            return true;
        }



        return super.onOptionsItemSelected(item);
    }


    public void doThis(MenuItem item){
       Intent intent=new Intent(Signatures.this,AddSignature.class);
        intent.putExtra("total",total1);
        startActivity(intent);
    }
    public void doEdit(MenuItem item){
        Intent intent = new Intent(Signatures.this , UpdateSignature.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("id" ,sigId);
        startActivity(intent);;
    }
}
