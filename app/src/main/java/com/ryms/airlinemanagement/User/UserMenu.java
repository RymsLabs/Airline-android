package com.ryms.airlinemanagement.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ryms.airlinemanagement.R;
import com.ryms.airlinemanagement.User.TicketHistory.TicketHistory;

public class UserMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
    }

    public void goToBookTickets(View view){
        Intent intent = new Intent(this, FlightSearch.class);
        startActivity(intent);
    }

    public void goToTicketHistory(View view){
        Intent intent = new Intent(this, TicketHistory.class);
        startActivity(intent);
    }
}