package com.aps.user.sametime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class LoginActivity extends AppCompatActivity  {
    CardView cardView,cardView1;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        toolbar.setTitle("Same Time");
        toolbar.setTitleTextColor(0xFFFFFFFF);


        cardView=(CardView)findViewById(R.id.card_view);
        cardView1=(CardView)findViewById(R.id.card_view1);
cardView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        franchise();
    }
});
      cardView1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              customer();
          }
      });

    }

    private void franchise() {
        Intent intent=new Intent(LoginActivity.this,FranchiseLoginActivity.class);
        intent.putExtra("franchise_user_type", "Franchise");
        startActivity(intent);
        finish();
    }
    private void customer(){
        Intent intent=new Intent(LoginActivity.this,CustomerLoginActivity.class);
        intent.putExtra("customer_user_type", "Customer");
        startActivity(intent);
        finish();
    }

}
