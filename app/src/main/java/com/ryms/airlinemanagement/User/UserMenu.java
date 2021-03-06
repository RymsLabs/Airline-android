package com.ryms.airlinemanagement.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ryms.airlinemanagement.R;
import com.ryms.airlinemanagement.User.FlightSearch.FlightSearch;
import com.ryms.airlinemanagement.User.TicketHistory.TicketHistory;

public class UserMenu extends AppCompatActivity {

    int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        Bundle bundle = getIntent().getExtras();
        uid = bundle.getInt("uid");
    }

    public void goToBookTickets(View view){
        Intent intent = new Intent(this, FlightSearch.class);
        intent.putExtra("uid",uid);
        startActivity(intent);
    }

    public void goToTicketHistory(View view){
        Intent intent = new Intent(this, TicketHistory.class);
        intent.putExtra("uid",uid);
        startActivity(intent);
    }
}