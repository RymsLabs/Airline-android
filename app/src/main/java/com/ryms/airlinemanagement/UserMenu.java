package com.ryms.airlinemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
    }

    public void goToBookTickets(View view){
        Intent intent = new Intent(this, BookTickets.class);
        startActivity(intent);
    }

    public void goToTicketHistory(View view){
        Intent intent = new Intent(this, TicketHistory.class);
        startActivity(intent);
    }
}